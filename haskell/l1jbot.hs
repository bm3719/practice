{-# OPTIONS_GHC -Wno-missing-signatures #-}

import Control.Exception             -- base
import Control.Monad.IO.Class        --
import Data.List                     --
import System.Exit                   --
import System.IO                     --
import qualified Network.Socket as N -- network
import Control.Monad.Trans.Reader    -- transformers

-- Config
myServer = "irc.freenode.org" :: N.HostName
myPort   = 6667 :: N.PortNumber
myChan   = "#l1j" :: String
myNick   = "l1jbot" :: String

-- Top level program
main :: IO ()
main = bracket connect disconnect loop
  where
    disconnect = hClose . botSocket
    loop st = runReaderT run st

-- The Net monad, a wrapper over IO, carrying the bot's immutable state.
data Bot = Bot { botSocket :: Handle }
type Net = ReaderT Bot IO

-- Connect to server and return initial bot state
connect :: IO Bot
connect = notify $ do
    h <- connectTo myServer myPort
    return (Bot h)
  where
    notify a = bracket_
      (putStrLn ("Connecting to " ++ myServer ++ " ...") >> hFlush stdout)
      (putStrLn "done.")
      a

-- Connect to a server given its name and port number
connectTo :: N.HostName -> N.PortNumber -> IO Handle
connectTo host port = do
    addr : _ <- N.getAddrInfo Nothing (Just host) (Just (show port))
    sock <- N.socket (N.addrFamily addr) (N.addrSocketType addr) (N.addrProtocol addr)
    N.connect sock (N.addrAddress addr)
    N.socketToHandle sock ReadWriteMode

-- In the Net monad, so we've successfully connected.  Join a channel and start
-- processing commands.
run :: Net ()
run = do
    write "NICK" myNick
    write "USER" (myNick ++ " 0 * :tutorial bot")
    write "JOIN" myChan
    listen

-- Send a message to a handle
write :: String -> String -> Net ()
write cmd args = do
    h <- asks botSocket
    let msg = cmd ++ " " ++ args ++ "\r\n"
    liftIO $ hPutStr h msg          -- Send message on the wire
    liftIO $ putStr ("> " ++ msg)   -- Show sent message on the command line

-- Process each line from the server
listen :: Net ()
listen = forever $ do
    h <- asks botSocket
    line <- liftIO $ hGetLine h
    liftIO (putStrLn line)
    let s = init line
    if isPing s then pong s else eval (clean s)
  where
    forever :: Net () -> Net ()
    forever a = do a; forever a

    clean :: String -> String
    clean = drop 1 . dropWhile (/= ':') . drop 1

    isPing :: String -> Bool
    isPing x = "PING :" `isPrefixOf` x

    pong :: String -> Net ()
    pong x = write "PONG" (':' : drop 6 x)

-- Dispatch a command
eval :: String -> Net ()
eval "!quit" = write "QUIT" ":Exiting" >> liftIO exitSuccess
eval x | "!slap " `isPrefixOf` x =
         if ((secondWord x) == "bcmiller")
         then privmsg ("\001ACTION slaps sisface around with a large trout! \001")
         else privmsg ("\001ACTION slaps " ++ (secondWord x) ++ " around with a large trout! \001")
eval _ = return ()  -- ignore everything else

-- Send a privmsg to the channel
privmsg :: String -> Net ()
privmsg msg = write "PRIVMSG" (myChan ++ " :" ++ msg)

split :: Eq a => a -> [a] -> [[a]]
split d [] = []
split d s = x : split d (drop 1 y) where (x,y) = span (/= d) s

-- Get the second word of a string.
secondWord :: String -> String
secondWord s = (split ' ' s) !! 1
