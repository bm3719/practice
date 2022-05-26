module Ch9 where -- Lists

import Data.Bool
import Data.Char

safeTail :: [a] -> Maybe [a]
safeTail []     = Nothing
safeTail (_:[]) = Nothing
safetail (_:xs) = Just xs

-- Exercise

myEnumFromTo :: Enum a => a -> a -> [a]
myEnumFromTo x y
  | ((fromEnum x) > (fromEnum y)) = []
  | otherwise                     = x : (myEnumFromTo (succ x) y)


eftBool :: Bool -> Bool -> [Bool]
eftBool True True = [True]
eftBool True _    = []
eftBool _    _    = [False, True]

eftOrd :: Ordering -> Ordering -> [Ordering]
eftOrd o1 o2
  | o1 > o2   = []
  | o1 == o2   = [o1]
  | otherwise = o1 : (eftOrd (succ o1) o2)

eftInt :: Int -> Int -> [Int]
eftInt x y
  | x > y     = []
  | x == y     = [y]
  | otherwise = x : (eftInt (succ x) y)

eftChar :: Char -> Char -> [Char]
eftChar x y
  | x > y     = []
  | x == y     = [y]
  | otherwise = x : (eftChar (succ x) y)

-- Exercises

-- 1.
myWords :: String -> [String]
myWords s
  | s == "" = []
  | otherwise = (takeWhile (/= ' ') s) :
                myWords (dropWhile (== ' ') $ dropWhile (/= ' ') s)

-- 2.
firstSen = "Tyger Tyger, burning bright\n"
secondSen = "In the forests of the night\n"
thirdSen = "What immortal hand or eye\n"
fourthSen = "Could frame thy fearful symmetry?"
sentences = firstSen ++ secondSen ++ thirdSen ++ fourthSen

myLines :: String -> [String]
myLines [] = []
myLines s  = (takeWhile (/= '\n') s) :
             myLines (dropWhile (== '\n') $ dropWhile (/= '\n') s)

-- 3.
mySplit :: Char -> String -> [String]
mySplit _ [] = []
mySplit c s  = (takeWhile (/= c) s) :
               mySplit c (dropWhile (== c) $ dropWhile (/= c) s)

-- List comprehensions
lst = [x ^ 2 | x <- [1..10]]
lst2 = [x ^ 2 | x <- [1..10], rem x 2 == 0]
lst3 = [x ^ 2 | x <- [1..3], y <- [2..3]]
lst4 = [(x, y) | x <- [1, 2], y <- ['a', 'b']]
lst5 = [x | x <- "Three Letter Acronym", elem x ['A'..'Z']]

-- Intermission

mySqr = [x^2 | x <- [1..5]]
myCube = [y^3 | y <- [1..5]]

-- 1.
sqrCubes = [(x, y) | x <- mySqr, y <- myCube]

-- 2.
sqrCubes' = [(x, y) | x <- mySqr, y <- myCube, x < 50, y < 50]

-- 3.
sqrCubesLen' = length sqrCubes'

-- GHCi command `:sprint' prints variables in a way that shows what's already
-- evaled, with _ representing non-evaluation.  E.g.,

-- λ> :sprint sqrCubes
-- sqrCubes = _
-- λ> take 2 sqrCubes
-- [(1,1),(1,8)]
-- λ> :sprint sqrCubes
-- sqrCubes = (1,1) : (1,8) : _

-- Normal form: an expression fully evaluated.

-- Weak head normal form: an expression evaluated as far as necessary to reach
-- a data constructor.  Values in Haskell are reduced to WHNF.

-- The "spine" of a data structure list a list composed of cons cells can be
-- walked without eval-ing the member values.  E.g.,

-- λ> let ls = [1, undefined, undefined, 10]
-- λ> length ls
-- 4

-- In function definitions, use of _ in pattern matching precludes binding a
-- value to that argument.

-- Intermission: Exercises

-- Will it blow up?

-- 1. Doesn't work.
-- 2. Works.
-- 3. Doesn't work.
-- 4. Works.
-- 5. Doesn't work.
-- 6. Works.
-- 7. Doesn't work.
-- 8. Works.
-- 9. Works.
-- 10. Doesn't work.

-- Is it in normal form?

-- 1. NF
-- 2. WHNF
-- 3. Neither
-- 4. Neither
-- 5. Neither
-- 6. Neither
-- 7. WHNF

-- Intermission: Exercises

-- 1. Doesn't work.
-- 2. Works.
-- 3. Doesn't work.
-- 4. Detects vowels Chars in Strings
-- 5.
--   a) [1,4,9,16,25,36,49,64,81,100]
--   b) [1, 10, 20]
--   c) [15, 15, 15]
-- 6.
lst6 = map (\x -> bool x (-x) (x == 3)) [1..10]

-- Intermission: Exercises

-- 1.
lst7 = filter (\x -> (rem x 3) == 0) [1..30]

-- 2.
lst7Len = length lst7

-- 3.
articles = ["the", "a", "an"]
myFilter :: String -> [String]
myFilter s = filter (\x -> notElem x articles) $ words s

-- Zipping exercises

-- 1.
myZip :: [a] -> [b] -> [(a, b)]
myZip [] _ = []
myZip _ [] = []
myZip (x:xs) (y:ys) = (x,y) : myZip xs ys

-- 2.
myZipWith :: (a -> b -> c) -> [a] -> [b] -> [c]
myZipWith _ [] _ = []
myZipWith _ _ [] = []
myZipWith f (x:xs) (y:ys) = (f x y) : myZipWith f xs ys

-- 3.
myZip' :: [a] -> [b] -> [(a, b)]
myZip' xs ys = myZipWith (,) xs ys

-- Chapter Exercises

-- Data.Char

-- 1.
-- λ> :t isUpper
-- isUpper :: Char -> Bool
-- λ> :t toUpper
-- toUpper :: Char -> Char

-- 2. isUpper

-- 3.
capitalize :: String -> String
capitalize [] = ""
capitalize (c:cs) = toUpper c : cs

-- 4.
capitalize' :: String -> String
capitalize' [] = []
capitalize' (c:cs) = toUpper c : (capitalize' cs)

-- 5.
capFirst :: String -> Char
capFirst s = toUpper $ head s

-- 6.
-- Point-free version
capFirst' :: String -> Char
capFirst' = toUpper . head

-- Ciphers

-- See Cipher.hs

-- Writing your own standard functions

-- 1.
myOr :: [Bool] -> Bool
myOr []     = False
myOr (x:xs) = x || myOr xs

-- 2.
myAny :: (a -> Bool) -> [a] -> Bool
myAny _ []     = False
myAny f (x:xs) = (f x) || myAny f xs

-- 3.
myElem :: Eq a => a -> [a] -> Bool
myElem e []     = False
myElem e (x:xs) = (e == x) || myElem e xs

-- 4.
myReverse :: [a] -> [a]
myReverse []     = []
myReverse (x:xs) = (myReverse xs) ++ [x]

myReverse' :: [a] -> [a]
myReverse' = foldl (flip (:)) []

-- 5.
squish :: [[a]] -> [a]
squish []     = []
squish (x:xs) = x ++ squish xs

-- 6.
squishMap :: (a -> [b]) -> [a] -> [b]
squishMap _ []     = []
squishMap f (x:xs) = f x ++ (squishMap f xs)

-- 7.
squishAgain :: [[a]] -> [a]
squishAgain = squishMap id

-- 8.
myMaximumBy :: (a -> a -> Ordering) -> [a] -> a
myMaximumBy f = foldr1 (\a b -> if f a b == GT then a else b)

-- 9.
myMinimumBy :: (a -> a -> Ordering) -> [a] -> a
myMinimumBy f = foldr1 (\a b -> if f a b == LT then a else b)

myMaximum :: (Ord a) => [a] -> a
myMaximum = myMaximumBy compare

myMinimum :: (Ord a) => [a] -> a
myMinimum = myMinimumBy compare
