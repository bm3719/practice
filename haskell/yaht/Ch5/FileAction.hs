-- 5.2
module Main where

import IO

main = do
  hSetBuffering stdin LineBuffering
  doLoop

doLoop = do
  putStrLn "Do you want to [read] a file, [write] a file or [quit]?"
  command <- getLine
  case command of
    "quit" -> return ()
    "read" -> do filename <- prompt command
                 doRead filename
                 doLoop
    "write" -> do filename <- prompt command
                  doWrite filename
                  doLoop

prompt command = do
  putStrLn ("Enter a filename to " ++ command ++ ":")
  getLine

doRead filename =
  bracket (openFile filename ReadMode) hClose
  (\h -> do contents <- hGetContents h
            putStr contents)

doWrite filename = do
  putStrLn "Enter text (dot on a line by itself to end):"
  contents <- getContent
  bracket (openFile filename WriteMode) hClose
    (\h -> hPutStrLn h contents)

getContent = do
  line <- getLine
  case line of
    "." -> return ""
    _   -> do otherLines <- getContent
              return (if otherLines == "" then line
                      else (line ++ "\n" ++ otherLines))
