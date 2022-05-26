module Ch10 where -- Folding Lists

import Data.Time

-- Reminder: foldr decomposition:
-- foldr (+) 0 [1..3] = 1 + (2 + (3 + 0))
-- foldl (+) 0 [1..3] = ((0 + 1) + 2) + 3

-- Among the consequences of the above, foldl shouldn't be used on infinite
-- lists.  foldl also accumulates unevaluated values.  Data.List.foldl' solves
-- the latter problem.

-- scanr/scanl: Return a list of the return values for each step of a fold.

-- Intermission: Exercises

-- 1. c
-- 2. Skipping
-- 3. c
-- 4. a
-- 5.
--   a) foldr (++) [] ["woot", "WOOT", "woot"]
--   b) foldr max [] (words "fear is the little death")
--   c) foldr (&&) True [False, True]
--   d) foldl (||) True [False, True]
--   e) foldr ((++) . show) "" [1..5]
--   f) foldr const 'a' ['a'..'e']
--   g) foldr const 0 [1..5]
--   h) foldl (flip const) 0 [1..5]
--   i) foldl (flip const) 'z' ['a'..'e']

-- Intermission: Exercises

data DatabaseItem = DbString String
                  | DbNumber Integer
                  | DbDate   UTCTime
                  deriving (Eq, Ord, Show)

theDatabase :: [DatabaseItem]
theDatabase =
  [ DbDate (UTCTime (fromGregorian 1911 5 1)
                    (secondsToDiffTime 34123))
  , DbNumber 9001
  , DbString "Hello, world!"
  , DbDate (UTCTime (fromGregorian 1921 5 1)
                    (secondsToDiffTime 34123))
  ]

-- 1.
getDate :: DatabaseItem -> [UTCTime]
getDate (DbDate t) = [t]
getDate _          = []

filterDbDate :: [DatabaseItem] -> [UTCTime]
filterDbDate = foldr ((++) . getDate) []

-- 2.
getNumber :: DatabaseItem -> [Integer]
getNumber (DbNumber x) = [x]
getNumber _            = []

filterDbNumber :: [DatabaseItem] -> [Integer]
filterDbNumber = foldr ((++) . getNumber) []

-- 3.
mostRecent :: [DatabaseItem] -> UTCTime
mostRecent = maximum . filterDbDate

-- 4.
sumDb :: [DatabaseItem] -> Integer
sumDb = sum . filterDbNumber

-- 5.
avgDb :: [DatabaseItem] -> Double
avgDb ds = let dbNums = filterDbNumber ds in
  (fromIntegral . sum $ dbNums) / (fromIntegral . length $ dbNums)

-- Infinite Fibonacci sequence, using scanl.
fibs = 1 : scanl (+) 1 fibs

-- Scans Exercises

-- 1.
fib20 = take 20 fibs

-- 2.
fibLT100 = takeWhile (< 100) fibs

-- 3.
facts = scanl (*) 1 [1..]
factsN n = facts !! n

-- Chapter Exercises

-- Warm-up and review

-- 1.
stops = "pbtdkg"
vowels = "aeiou"

--   a)
combos = [(s1, v, s2) | s1 <- stops, v <- vowels, s2 <- stops]

--   b)
pCombos = filter (\(x, y, z) -> x == 'p') combos

--   c)
-- Repetitive, so skipping.

-- 2.
-- Find average length of words in a string.
-- String -> Integer

-- 3.
-- Replace div with / and add fromIntegral calls.

-- Rewriting functions using folds

-- 1.
myOr :: [Bool] -> Bool
myOr = foldr (||) False

-- 2.
myAny :: (a -> Bool) -> [a] -> Bool
myAny f = foldr (\x y -> (f x) || y) False

-- 3.
myElem :: Eq a => a -> [a] -> Bool
myElem e = foldr (\x y -> (x == e) || y) False

-- 4.
myReverse :: [a] -> [a]
myReverse = foldl (flip (:)) []

-- 5.
myMap :: (a -> b) -> [a] -> [b]
myMap f = foldr (\x y -> (f x) : y) []

-- 6.
myFilter :: (a -> Bool) -> [a] -> [a]
myFilter f = foldr (\x y -> if (f x) then x : y else y) []

-- 7.
squish :: [[a]] -> [a]
squish = foldr (\x y -> x ++ y) []

-- 8.
squishMap :: (a -> [b]) -> [a] -> [b]
squishMap f = foldr (\x y -> (f x) ++ y) []

-- 9.
squishAgain :: [[a]] -> [a]
squishAgain = squishMap id

-- 10.
myMaximumBy :: (a -> a -> Ordering) -> [a] -> a
myMaximumBy f = foldr1 (\x y -> if (f x y) == LT then y else x)

-- 11.
myMinimumBy :: (a -> a -> Ordering) -> [a] -> a
myMinimumBy f = myMaximumBy (flip f)
