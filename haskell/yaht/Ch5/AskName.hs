-- 5.1
module Main where

import IO

main = do
  hSetBuffering stdin LineBuffering
  putStr "Input name: "
  name <- getLine
  if name == "Simon" || name == "John" || name == "Phil"
    then putStrLn ("Hello, " ++ name ++ ". Haskell is a great programming language.")
    else if name == "Koen"
         then putStrLn ("Hello, " ++ name ++ ".  Debugging Haskell is fun.")
         else putStrLn "I don't know who you are."
  -- case name of
  --   "Simon" -> ...
  --   ...
  --   _       -> putStrLn "I don't know who you are."
