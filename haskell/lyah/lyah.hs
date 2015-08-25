{-# LANGUAGE UnicodeSyntax #-}
-- This is a test project for notes gathered while reading Learn You a Haskell
-- For Great Good.  http://learnyouahaskell.com/

main :: IO ()
main = do
  putStr "What is your first name? "
  first <- getLine
  putStr "What is your last name? "
  last <- getLine
  let full = first ++ " " ++ last
  putStrLn ("Pleased to meet you, " ++ full ++ "!")

-- Chapter 2: Starting Out

doubleMe x = x * x

doubleSmallNumber x = if x > 100
                      then x
                      else x * 2

-- ++ concatenates lists, including strings (which are just lists of Chars).

-- The cons operator (:), conses stuff to lists.
-- 'A':" SMALL CAT"

-- Use !! to access ∈s of a list.
-- [1,2,3,4,5] !! 3

-- Comparison operators can be used on lists, where they are applied in
-- lexicographic order.  Subsequent elements are compared when the previous
-- comparison are equal.
-- [3,2,1] > [2,1,0]

-- List operations: head, tail, last, init (same as but-last in Clojure),
-- length, null (same as empty?), reverse, take, drop, maximum, minimum, sum,
-- product, elem (member predicate).

-- Ranges: Possible to specify a step, like [2,4..20].  To make decrementing
-- lists, a comma is needed, like [20,19..1].

-- List-producing functions:
-- cycle: Takes a list and cycles it infinitely.
-- repeat: Takes an element and creates infinite list of it.
-- replicate: Produces specified number of elements in a list.

-- List comprehensions

-- S = {2 ⋅ x | x ∈ ℕ, x ≤ 10 }
s = [x * 2 | x <- [1..10]]

-- Adding a predicate.
s' = [x * 2 | x <- [1..10], x * 2 >= 12]

-- All number from 50 to 100 whose remainder when divided with 7 is 3.
s'' = [x | x <- [50..100], x `mod` 7 == 3]

-- With a parameter
boomBangs xs = [if x < 10 then "Boom!" else "Bang!" | x <- xs, odd x]
-- boomBangs [7..13]

-- Multiple variables.
s''' = [x * y | x <- [2, 5, 10], y <- [8, 10, 11], x * y > 50]

-- Length implementation.
length' xs = sum [1 | _ <- xs]

-- Strings
removeNonUppercase st = [c | c <- st, c `elem` ['A'..'Z']]

-- Sublists: Remove odd numbers.
xxs = [[1, 3, 5, 2, 3, 1, 2, 4, 5],
       [1, 2, 3, 4, 5, 6, 7, 8, 9],
       [1, 2, 4, 2, 1, 6, 3, 1, 2, 3, 6]]
removeOdd xxs = [[x | x <- xs, even x] | xs <- xxs]

-- Tuples: Used when exact number of elements known and for grouping different
-- types.

-- Tuple functions: fst (first from a pair), snd (second from a pair), zip
-- (create list of pairs).

-- A list of all right triangles.
rts = [(a, b, c) | c <- [1..10], b <- [1..c], a <- [1..b], a^2 + b^2 == c^2]

-- Chapter 3: Types and Typeclasses

-- Types: Int (bounded), Integer (unbounded), Float, Double, Bool, Char.

-- Typeclasses: In a type signature, => is a "class constraint".  In the case
-- of `:t (==)', the types of the two parameter values must be a member of the
-- Eq class.

-- Some typeclasses: Eq (equality testing), Ord (ordering, must also be a
-- member of Eq), Show (presentable as strings, e.g. using show function), Read
-- (opposite of show, e.g. using the read function), Enum (sequential ordering,
-- e.g. list ranges, succ, pred), Bounded (upper/lower bounded, use minBound
-- and maxBound), Num (numeric, polymorphic), Integral (Int and Integer),
-- Floating (Float and Double).

-- Ordering: A type that can be GT, LT, or EQ.  See `:t compare'.

-- fromIntegral: Promote an Integral type to the more general Num.
-- fromIntegral (length [1,2,3]) + 3.4

-- Chapter 4: Syntax in Functions

-- Pattern matching: Specifying patterns to which some data should conform,
-- then deconstructing data and checking it against those forms.

factorial :: (Integral a) => a -> a
factorial 0 = 1
factorial n = n * factorial (n - 1)

fac :: (Integral a) => a -> a
fac 0 = 1
fac n = foldr (*) 1 [1..n]

-- Add three tuples.
addVectors :: Num a => (a, a) -> (a, a) -> (a, a)
addVectors a b = (fst a + fst b, snd a + snd b)

addVectors' :: Num a => (a, a) -> (a, a) -> (a, a)
addVectors' (x1, x2) (y1, y2) = (x1 + y1, x2 + y2)

-- List decomposition using `:'.  Parens are necessary when binding multiple
-- values.
head' :: [a] -> a
head' [] = error "List empty."
head' (x:_) = x

length'' :: Num b => [a] -> b
length'' [] = 0
length'' (_:xs) = 1 + length'' xs

sum' :: Num a => [a] -> a
sum' [] = 0
sum' (x:xs) = x + sum' xs

capital :: String -> String
capital "" = "Empty string."
capital all@(x:xs) = "The first letter of " ++ all ++ " is " ++ [x]

-- Guards: Test some property of a value or values are true or false.

-- Note: RealFloat is a subclass of Floating.  Float and Double fall into the
-- class RealFloat.
bmiTell :: (RealFloat a) => a -> String
bmiTell bmi
  | bmi <= 18.5 = "You're underweight, you emo, you!"
  | bmi <= 25.0 = "You're supposedly normal. Pffft, I bet you're ugly!"
  | bmi <= 30.0 = "You're fat! Lose some weight, fatty!"
  | otherwise  = "You're a whale, congratulations!"

max' :: (Ord a) => a -> a -> a
max' a b | a > b     = a
         | otherwise = b

compare' :: (Ord a) => a -> a -> Ordering
compare' a b | a > b     = GT
             | a < b     = LT
             | otherwise = EQ

-- BMI calculator using `where'.
bmiTell' :: (RealFloat a) => a -> a -> String
bmiTell' weight height
  | bmi <= 18.5 = "You're underweight, you emo, you!"
  | bmi <= 25.0 = "You're supposedly normal. Pffft, I bet you're ugly!"
  | bmi <= 30.0 = "You're fat! Lose some weight, fatty!"
  | otherwise  = "You're a whale, congratulations!"
  where bmi = weight / height ^ 2
