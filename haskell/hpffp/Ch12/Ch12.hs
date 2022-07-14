module Ch12 where -- Signaling Adversity

import Data.List

-- Lifted and unlifted types: *s in a kind apply to lifted types, which are any
-- types which bottom can inhabit.  Unlifted types include native machine types
-- and pointers.  These have # in their kind signature.  The only exception is
-- newtypes which are kind * but are unlifted.

-- Data constructors can be used like higher order functions:
-- fmap Just [1, 2, 3]

-- Chapter Exercises

-- Determine the kinds

-- 1. *
-- 2. *, and * -> *

-- String processing

-- 1.

theToA :: String -> String
theToA = unwords . map (\x -> if x == "the" then "a" else x) . words

-- Oh, they wanted to use Maybe for this...  Next time read all the
-- instructions before solving.  This one's pretty trivial, so will stick with
-- my solution.

-- 2.

notThe :: String -> Maybe String
notThe s = if (s == "the") then Nothing else Just s

startsWithVowel :: String -> Bool
startsWithVowel s = head s `elem` ['a','e','i','o','u']

checkAfterThe :: [String] -> Integer
checkAfterThe []         = 0
checkAfterThe [_:[]]     = 0
checkAfterThe ("the":ws) = (if (startsWithVowel (head ws)) then 1 else 0) +
                           (checkAfterThe ws)
checkAfterThe (_:ws)     = checkAfterThe ws

countTheBeforeVowel :: String -> Integer
countTheBeforeVowel s =
  checkAfterThe (words s)

-- 3.

isVowel :: Char -> Bool
isVowel c = c `elem` ['a','e','i','o','u']

sumVowels :: String -> Integer
sumVowels = foldr (\x y -> (if (isVowel x) then 1 else 0) + y) 0

-- Validate the word

newtype Word' = Word' String deriving (Eq, Show)
vowels = "aeiou"

mkWord :: String -> Maybe Word'
mkWord s = if (vowelCount > (toInteger (length s) - vowelCount))
  then Nothing
  else Just (Word' s)
  where vowelCount = sumVowels s

-- It's only Natural

data Nat = Zero
         | Succ Nat
         deriving (Eq, Show)

natToInteger :: Nat -> Integer
natToInteger (Succ n) = 1 + (natToInteger n)
natToInteger _        = 0

integerToNat :: Integer -> Nat
integerToNat 0 = Zero
integerToNat n = Succ (integerToNat (n - 1))

-- Small library for Maybe

-- 1.

isJust :: Maybe a -> Bool
isJust (Just _) = True
isJust Nothing  = False

isNothing :: Maybe a -> Bool
isNothing Nothing = True
isNothing _       = False

-- 2.

mayybee :: b -> (a -> b) -> Maybe a -> b
mayybee x f Nothing  = x
mayybee x f (Just y) = (f y)

-- 3.

fromMaybe :: a -> Maybe a -> a
fromMaybe x m = mayybee x id m

-- 4.

listToMaybe :: [a] -> Maybe a
listToMaybe []    = Nothing
listToMaybe (x:_) = Just x

maybeToList :: Maybe a -> [a]
maybeToList Nothing  = []
maybeToList (Just x) = [x]

-- 5.

catMaybes :: [Maybe a] -> [a]
catMaybes = concat . (map maybeToList)

-- 6.

-- Probably a cleaner way to do this.
flipMaybe :: [Maybe a] -> Maybe [a]
flipMaybe lst = if ((length lst) /= (length as))
  then Nothing
  else Just as
  where as = catMaybes lst

-- Small library for Either

-- 1.

fromLeft :: Either a b -> [a]
fromLeft (Left x) = [x]
fromLeft _        = []

lefts' :: [Either a b] -> [a]
lefts' = foldr (\x y -> (fromLeft x) ++ y) []

-- 2.

fromRight :: Either a b -> [b]
fromRight (Right x) = [x]
fromRight _         = []

rights' :: [Either a b] -> [b]
rights' = foldr (\x y -> (fromRight x) ++ y) []

-- 3.

partitionEithers' :: [Either a b] -> ([a], [b])
partitionEithers' lst = ((lefts' lst), (rights' lst))

-- 4.

eitherMaybe' :: (b -> c) -> Either a b -> Maybe c
eitherMaybe' f (Right x) = Just (f x)
eitherMaybe' _ _         = Nothing

-- 5.

either' :: (a -> c) -> (b -> c) -> Either a b -> c
either' f _ (Left x)  = f x
either' _ g (Right x) = g x

-- 6.

-- either' isn't exactly the right fn for the job here, but whatever.
eitherMaybe'' :: (b -> c) -> Either a b -> Maybe c
eitherMaybe'' f (Right x) = Just (either' id f (Right x))
eitherMaybe'' _ (Left _)  = Nothing

-- Unfolds

-- Note: unfoldr is in Data.List now.
-- take 10 $ unfoldr (\b -> Just (b, b+1)) 0

-- unfoldr :: (b -> Maybe (a, b)) -> b -> [a]
-- a is : to the list and b is used as the next element in the recursive call.

-- Write your own iterate and unfoldr

-- 1.

myIterate :: (a -> a) -> a -> [a]
myIterate f x = let e = f x in
  e : myIterate f e

-- 2.

myUnfoldr :: (b -> Maybe (a, b)) -> b -> [a]
myUnfoldr f b = case f b of
  Just (x, y) -> x : myUnfoldr f y
  Nothing     -> []

-- 3.

-- This is taking forever and not learning anything new, so bailing on the last
-- few exercises to keep things moving.
