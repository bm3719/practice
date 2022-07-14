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

-- Support for Vigenère ciphers: Using a static keyword and still sticking with
-- lower case only.

keyword = "ally"

-- Generate the repeated keyword version of the message.
keywordInf = concat . repeat $ keyword

wordReplace :: [String] -> String -> [String]
wordReplace [] _        = []
wordReplace (w:ws) keys =
  (take len keys) : (wordReplace ws (drop len keys))
  where len = length w

-- Replacing the version above
convertChar' :: Int -> Char -> Char
convertChar' n c =
  chr $ (+96) $ mod (((ord c) + n) - 96) 26

-- Words-split version of keyword rotation.
keywordWords :: String -> [String]
keywordWords s = wordReplace (words s) keywordInf

-- Note: An 'a' means shift of 0.
vigenereChar :: Char -> Char -> Char
vigenereChar c1 c2 = convertChar' ((ord c2) - 97) c1

unVigenereChar :: Char -> Char -> Char
unVigenereChar c1 c2 = convertChar' (negate ((ord c2) - 97)) c1

vigenereHelper :: String -> String -> String
vigenereHelper "" _          = ""
vigenereHelper (c:cs) (k:ks) = (vigenereChar c k) : (vigenereHelper cs ks)

vigenere :: String -> String
vigenere msg = unwords $ [vigenereHelper (fst wk) (snd wk) | wk <- (zip ws ks)]
  where ws = words msg
        ks = keywordWords msg

unVigenereHelper :: String -> String -> String
unVigenereHelper "" _          = ""
unVigenereHelper (c:cs) (k:ks) = (unVigenereChar c k) : (unVigenereHelper cs ks)

unVigenere :: String -> String
unVigenere msg = unwords $ [unVigenereHelper (fst wk) (snd wk) | wk <- (zip ws ks)]
  where ws = words msg
        ks = keywordWords msg
