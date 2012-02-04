-- 3.10
module Main where

import IO

main = do
  hSetBuffering stdin LineBuffering
  lst <- askNum
  putStrLn ("The sum is " ++ (show $ foldr (+) 0 lst))
  putStrLn ("The product is " ++ (show $ foldr (*) 1 lst))
  facShow lst

facShow lst = do
  putStrLn ((show $ head lst) ++ " factorial is " ++ (show $ fac $ head $ lst))
  case (length $ tail lst) of
    0 -> putStrLn ""
    _ -> facShow $ tail lst

askNum = do
  putStrLn "Give me a number (or 0 to stop):"
  num <- getLine
  let numRead = read num
  if numRead /= 0
    then do
    rest <- askNum
    return (numRead:rest)
    else do return []

fac :: Integer -> Integer
fac 0 = 1
fac n | n > 0 = n * fac (n-1)
