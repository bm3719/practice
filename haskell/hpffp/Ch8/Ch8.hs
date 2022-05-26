-- Chapter 8: Recursion
module Ch8 where
import Data.List (intersperse)

-- Intermission

-- Skipping writing all evaluation steps.

fib :: Integer -> Integer
fib n
  | (n <= 2)   = 1
  | otherwise = (fib (n - 1)) + (fib (n - 2))

-- Exercises

-- Review of types

-- 1. d
-- 2. b
-- 3. d
-- 4. b

-- Reviewing currying

-- 1. "woops mrow woohoo!"
-- 2. "1 mrow haha"
-- 3. "woops mrow 2 mrow haha"
-- 4. "woops mrow blue mrow haha"
-- 5. "pink mrow haha mrow green mrow woops mrow blue"
-- 6. "are mrow Pugs mrow awesome"

-- Recursion

-- 1. Skipping.

-- 2.
mySum :: (Eq a, Num a) => a -> a
mySum n
  | (n == 0) = 0
  | otherwise = n + (mySum (n - 1))

-- 3.
myProd :: (Integral a) => a -> a -> a
myProd x y
  | (y == 1) = x
  | otherwise = x + (myProd x (y - 1))

-- Fixing dividedBy
-- Skipping

-- McCarthy 91 function

mc91 :: (Ord a, Num a) => a -> a
mc91 n
  | (n > 100) = n - 10
  | otherwise = mc91 $ mc91 (n + 11)

-- Numbers into words

digitToWord :: Int -> String
digitToWord n =
  ["zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"] !! n

digits :: Int -> [Int]
digits n
  | (n < 10)  = [n]
  | otherwise = (digits (div n 10)) ++ [mod n 10]

wordNumber :: Int -> String
wordNumber n =
  concat $ intersperse "-" $ map digitToWord $ digits n
