{-# LANGUAGE OverloadedStrings #-}
module Cipher where

import Data.Bool
import Data.Char

-- 'a' = 97
-- 'z' = 122
-- ' ' = 32

convertChar :: Int -> Char -> Char
convertChar n ' ' = ' '
convertChar n c   = chr $
  (\x -> bool x ((mod x 122) + 96) (x > 122)) $
  (+n) $ ord c

caesar :: Int -> String -> String
caesar _ []     = []
caesar n (c:cs) = convertChar n c : caesar n cs

unCaesar :: Int -> String -> String
unCaesar n s = caesar (-n) s
