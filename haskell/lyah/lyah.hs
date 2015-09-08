{-# LANGUAGE UnicodeSyntax #-}
-- This is a test project for notes gathered while reading Learn You a Haskell
-- For Great Good.  http://learnyouahaskell.com/

import Data.List
import Data.Function (on)
import Data.Char
import qualified Data.Map as Map
import qualified Data.Set as Set
import Geometry
import Control.Monad
import System.IO           -- For openFile, withFile, etc.
import System.Environment  -- For getArgs.
import System.Directory    -- For doesFileExist.
import System.IO.Error
import Control.Exception   -- New location of catch.

main :: IO ()
main = do
  putStr "What is your first name? "
  first <- getLine
  putStr "What is your last name? "
  last <- getLine
  let full = first ++ " " ++ last
  putStrLn ("Pleased to meet you, " ++ full ++ "!")


--- Chapter 2: Starting Out

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


--- Chapter 3: Types and Typeclasses

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


--- Chapter 4: Syntax in Functions

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
  | bmi <= skinny = "You're underweight, you emo, you!"
  | bmi <= normal = "You're supposedly normal. Pffft, I bet you're ugly!"
  | bmi <= fat    = "You're fat! Lose some weight, fatty!"
  | otherwise    = "You're a whale, congratulations!"
  where bmi = weight / height ^ 2
        (skinny, normal, fat) = (18.5, 25.0, 30.0)

initials :: String -> String -> String
initials first last = [f] ++ ". " ++ [l] ++ "."
  where (f:_) = first
        (l:_) = last

-- A better version of the above.
initials' :: String -> String -> String
initials' (f:_) (l:_) = [f] ++ ". " ++ [l] ++ "."

-- Take a list of weight-height pairs, and return a list of BMIs.
calcBmis :: (RealFloat a) => [(a, a)] -> [a]
calcBmis xs = map bmi xs
  where bmi (weight, height) = weight / height ^ 2

--- Let bindings: Full expressions, versus where bindings, which are just
--- syntactic constructs.  This means you can use let anywhere, like with if
--- statements.

-- Volume of a cylinder.
cylinder :: (RealFloat a) => a -> a -> a
cylinder r h =
  let topArea  = pi * r ^ 2
      sideArea = 2 * pi * r * h
  in  sideArea + 2 * topArea

-- Example of inline let, introducing locally scoped functions:
-- [let square x = x * x in (square 5, square 3, square 2)]

-- Semicolons are used for multiple value inline let bindings.
-- (let a = 100; b = 200; c = 300 in a * b * c,
--  let foo = "Hey "; bar = "there!" in foo ++ bar)

-- let can be used in list comprehensions, as one would a predicate.
calcBmis' :: (RealFloat a) => [(a, a)] -> [a]
calcBmis' xs = [bmi | (w, h) <- xs, let bmi = w / h ^ 2, bmi >= 25.0]

-- Note that let statements can't be used across guards, hence the need for
-- where.

--- case expressions: Equivalent to pattern matching function parameters, but
--- can be used anywhere.

-- Equivalent to head' above.
head'' :: [a] -> a
head'' xs = case xs of [] -> error "List empty."
                       (x:_) -> x

--- FizzBuzz in Haskell interlude

-- First attempt.
fizzBuzz :: (Show a, Integral a) => [a] -> [[Char]]
fizzBuzz xs = map (\x -> case () of
                      _ | mod3 && mod5 -> "FizzBuzz"
                        | mod3 -> "Fizz"
                        | mod5 -> "Buzz"
                        | otherwise -> show x
                        where mod3 = mod x 3 == 0
                              mod5 = mod x 5 == 0) xs

-- Probably nicer to just write a function that converts one number and apply
-- it.
fizzBuzz' :: Integer -> String
fizzBuzz' x | mod x 15 == 0 = "FizzBuzz"
            | mod x 5 == 0  = "Buzz"
            | mod x 3 == 0  = "Fizz"
            | otherwise    = show x
-- map fizzBuzz' [1..100]


--- Chapter 5: Recursion

maximum' :: (Ord a) => [a] -> a
maximum' [] = error "Empty list."
maximum' [x] = x
maximum' (x:xs) | x > maxTail = x
                | otherwise   = maxTail
  where maxTail = maximum' xs

-- Same as above, but using the max function.
maximum'' :: (Ord a) => [a] -> a
maximum'' [] = error "Empty list."
maximum'' [x] = x
maximum'' (x:xs) = max x (maximum'' xs)

-- Implement replicate using recursion.  A better solution uses take and
-- repeat.
replicate' :: (Num a, Ord a) => a -> b -> [b]
replicate' x e
  | x <= 0     = []
  | otherwise = e:replicate' (x - 1) e

-- Implement take using recursion.
take' :: (Num a, Ord a) => a -> [b] -> [b]
take' n _
  | n <= 0      = []
take' _ []     = []
take' n (x:xs) = x:take' (n - 1) xs

-- Implement reverse.
reverse' :: [a] -> [a]
reverse' [] = []
reverse' (x:xs) = reverse' xs ++ [x]

-- Implement repeat.
repeat' :: a -> [a]
repeat' x = x:repeat' x

-- Implement zip.
zip' :: [a] -> [b] -> [(a, b)]
zip' _ [] = []
zip' [] _ = []
zip' (x:xs) (y:ys) = (x,y):zip' xs ys

-- Implement elem.
elem' :: (Eq a) => a -> [a] -> Bool
elem' e [] = False
elem' e (x:xs) = if e == x then True else elem' e xs

-- Implement quicksort (my attempt).
qsort :: (Ord a) => [a] -> [a]
qsort [] = []
qsort (x:xs) = qsort [e | e <- xs, e < x] ++ [x] ++
               qsort [e | e <- xs, e >= x]

-- Book's version of the above.
quicksort :: (Ord a) => [a] -> [a]
quicksort [] = []
quicksort (x:xs) =
  let smallerSorted = quicksort [a | a <- xs, a <= x]
      biggerSorted = quicksort [a | a <- xs, a > x]
  in  smallerSorted ++ [x] ++ biggerSorted


--- Chapter 6: Higher order functions

-- (Some talk about curried functions and partial application is here.)

-- Sectioning an infix function: Surround it with parentheses and only supply
-- the parameter on one side.
divideByTen :: Double -> Double
divideByTen = (/10)

-- Take a function and apply it twice.
applyTwice :: (a -> a) -> a -> a
applyTwice f a = f (f a)

-- Implement zipWith using higher order programming.
zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith' _ _ [] = []
zipWith' _ [] _ = []
zipWith' f (x:xs) (y:ys) = f x y : zipWith' f xs ys

-- Implement flip.
flip' :: (a -> b -> c) -> (b -> a -> c)
flip' f = g
  where g x y = f y x

flip'' :: (a -> b -> c) -> b -> a -> c
flip'' f x y = f y x

-- λ> zip [1,2] "hi"
-- [(1,'h'),(2,'i')]
-- λ> flip zip [1,2] "hi"
-- [('h',1),('i',2)]

-- Quicksort using filter.
qsort' :: (Ord a) => [a] -> [a]
qsort' [] = []
qsort' (x:xs) = qsort' (filter (< x) xs) ++ [x] ++
                qsort' (filter (>= x) xs)

-- Find the largest number under 100000 divisible by 3829 (my attempt).
-- foldl max 0 (filter (\n -> 0 == n `mod` 3829) [1..100000])

-- Book's version of the above.  Definitely better, since it doesn't filter a
-- whole range, instead just finding the first one that satisfies the
-- predicate.
largestDivisible :: (Integral a) => a
largestDivisible = head (filter p [100000,99999..])
  where p x = x `mod` 3829 == 0

-- Rewriting my version.
-- head (filter (\n -> 0 == mod n 3829) [100000,99999..])

-- Find the sum of all odd squares that are smaller than 10000 using takeWhile.
-- foldl (+) 0 (takeWhile (<10000) (filter odd (map (^2) [1..])))

-- The sum function could have replaced foldl above.

-- Collatz sequences: Find how many numbers between 1 and 100 have Collatz
-- sequences greater than 15.
collatzSeq :: (Integral a) => a -> [a]
collatzSeq 1 = [1]
collatzSeq n | even n = n:collatzSeq (div n 2)
             | otherwise = n:collatzSeq (n * 3 + 1)

-- length [chain | chain <- map collatzSeq [1..100], 15 < length chain]

-- Note: !! == .indexOf in Clojure.
-- ((map (*) [0..10]) !! 4) 5

-- Same as the above, but using a lambda function.
-- length (filter (\xs -> 15 < length xs) (map collatzSeq [1..100]))

-- Pattern matching in lambdas also works.
-- map (\(a,b) -> a + b) [(1,2),(3,5),(6,3)]

-- flip redefined using lambda syntax.
flip''' :: (a -> b -> c) -> b -> a -> c
flip''' f = \x y -> f y x

-- Implement sum using foldl.
sum'' :: (Num a) => [a] -> a
sum'' (x:xs) = foldl (+) x xs

-- Better implementation of the above.
sum''' :: (Num a) => [a] -> a
sum''' = foldl (+) 0

-- Implement elem using foldl.
elem'' :: (Eq a) => a -> [a] -> Bool
elem'' x xs = foldl (\acc y -> if (y == x) then True else False) False xs

-- Implement map using foldr.  Note that the accumulator's position is reversed
-- for foldr.
map' :: (a -> b) -> [a] -> [b]
map' f = foldr (\x acc -> f x : acc) []

-- foldl1 and foldr1: Use these to skip initialization of the accumulator (it's
-- initialized to the first element in the input list).  Will throw an
-- exception on an empty list, however.

-- Try implementing a bunch of functions using folds.
maximum''' :: (Ord a) => [a] -> a
maximum''' = foldl1 (\acc x -> if acc > x then acc else x)

reverse'' :: [a] -> [a]
reverse'' = foldl (\acc x -> x:acc) []

-- Book uses foldr1 (*) for this, but that's not how product behaves.
product' :: (Num a) => [a] -> a
product' = foldl (\acc x -> acc * x) 1

filter' :: (a -> Bool) -> [a] -> [a]
filter' f = foldr (\x acc -> (if f x then [x] else []) ++ acc) []

head''' :: [a] -> a
head''' = foldr1 (\x _ -> x)

last' :: [a] -> a
last' = foldl1 (\_ x -> x)

-- scanl, scanl1, scanr, and scanr2 work like their fold counterparts, but
-- return a list of intermediate results.

-- How many elements does it take for the sum of the roots of ℕ to exceed 1000.
sumAnswer :: Int
sumAnswer = length (takeWhile (<1000) (scanl1 (+) (map sqrt [1..]))) + 1

--- Function application

-- The $ operator is function application.  Has lowest precedence and is
-- right-associative.

-- Map a function application over a list of functions.
-- map ($ 3) [(4+), (10*), (^2), sqrt]

-- sumAnswer using ($).
sumAnswer' :: Int
sumAnswer' = (+1) $ length $ takeWhile (<1000) $ scanl1 (+) $ (map sqrt [1..])

--- Function composition

-- Same as Clojure's comp.

-- map ((+1) . (/2)) [1..10]

-- Function composition + partial application: Useful for multi-argument
-- functions.

-- sum (replicate 5 (max 6.7 8.9))
-- sum . replicated 5 . max 6.7 $ 8.9

-- Write this in point-free style:
-- fn x = ceiling (negate (tan (cos (max 50 x))))
pF = ceiling . negate . tan . cos . max 50

-- sumAnswer using (.)
sumAnswer'' :: Int
sumAnswer'' = (+1) . length . takeWhile (<1000) . scanl1 (+) . map sqrt $ [1..]


-- Chapter 7: Modules

-- import: At the REPL, use `:m + Data.List Data.Map'.

-- Import only nub and sort.
-- import Data.List (nub, sort)

-- Import all except nub.
-- import Data.List hiding (nub)

-- For modules imports that would result in name collisions, use qualified:
-- import qualified Data.Map

-- To alias qualified imports, use the `as' syntax.
-- import qualified Data.Map as M

-- List of everything in the standard library:
-- https://downloads.haskell.org/~ghc/latest/docs/html/libraries/

--- Data.List

-- intersperse: Same as Clojure's interpose.
-- intercalate: Intersperses list between a list of lists and flattens.
-- transpose: Rotates a matrix (list of lists).

-- Add the polynomials 3x² + 5x + 9, 10x³ + 9, and 8x³ + 5x² + x - 1.
polySum = map sum $ transpose [[0, 3, 5, 9], [10, 0, 0, 9], [8, 5, 1, -1]]

-- foldl' and foldr': Stricter versions of the lazy folds.  Avoids stack
-- overflows due to many thunks.

-- concat: Same as Clojure's flatten.
-- concatMap: Same as concat + map.
-- and: Ands boolean lists.
-- or: Ors boolean lists.
-- all: Same as Clojure's every?.
-- any: Same as Clojure's some, except returns Bool.

-- iterate: Takes function f and start value and returns lazy list of f
-- continuously applied.

-- splitAt: Splits a list at specified index, returning two lists in a tuple.
-- takeWhile: Same as Clojure's take-while.
-- dropWhile: Same as Clojure's drop-while.

-- span: Like takeWhile, except it returns a tuple with the first list
-- containing the takeWhile results and the second containing the remainder.
-- let (fw, rest) = span (/= ' ') "This is a sentence"
--   in "First word:" ++ fw ++ ", the rest:" ++ rest

-- break: Same as `span (not . p)'.
-- sort: Sorts list of Ord elements.
-- sortBy: sort, with a specified predicate function.
-- group: Groups adjacent elements into sublists if equal.

-- Find out how many times each element appears in this list.
-- map (\lst -> (head lst, length lst)) $ group $ sort [1,1,1,1,2,2,2,2,3,3,2,2,2,5,6,7]

-- Book's version of the above:
-- map (\l@(x:xs) -> (x,length l)) . group . sort $ [1,1,1,1,2,2,2,2,3,3,2,2,2,5,6,7]

-- Note: Use id@(..) syntax to both alias and destructure a value.

-- inits and tails: Recursively apply init and tail, returning a list.

-- Use tails and a fold to search for a sublist in a list.
search :: (Eq a) => [a] -> [a] -> Bool
search xs ss = foldl (\acc s -> if ss == take (length ss) s
                               then True
                               else acc)
               False (tails xs)

-- isInfixOf: Basically the same as the above exercise.
-- isPrefixOf and isSuffixOf: Search for sublist at begin and end of list.
-- elem and notElem: ...

-- partition: Return a tuple containing list of things that satisfy the
-- predicate and a list of things that don't.

-- find: Returns first element of a list that satisfies a predicate, wrapped in
-- a Maybe ADT.  A good example of when to use find is to replace `head
-- . dropWhile'.  head is not safe for empty lists, but find would just return
-- Nothing.

-- elemIndex: Like elem, but returns the index of the thing searched for.  Also
-- returns a Maybe.

-- elemIndices: Same as above but returns a list for multiple matches.
-- findIndex: Like find, but returns the index.
-- zip3..zip7, zipWith3..zipWith7: Same as zip, zipWith, but for more params.
-- lines: Splits strings on CRLF.
-- unlines: Joins strings with CRLFs.
-- words, unwords: Splits/joins on whitespace.
-- delete: Deletes first occurrence of element in a list.
-- \\: Set difference, for lists.
-- union: Set union, for lists (removes duplicates from second list).
-- intersect: Set intersection, for lists.

-- insert: Inserts element into a list at position where it's greater or equal
-- to an element.  Good way to add stuff to a sorted list and keep it sorted.

-- genericLength, genericTake, genericDrop, genericSplitAt, genericIndex,
-- genericReplace: Generic versions of their respective functions that take Num
-- or Integral instead of Int.

-- numBy, deleteBy, unionBy, intersectBy, groupBy: More general versions of
-- their respective functions, that take specified equality tests.

-- Note: groupBy = Clojure's partition-by.

-- Group these numbers based on whether they've changed to negative or not.
values = [-4.3, -2.4, -1.2, 0.4, 2.3, 5.9, 10.5, 29.1, 5.3, -2.4, -14.5, 2.9, 2.3]
-- groupBy (\x y -> (x<0) == (y<0)) values

-- In Clojure, this would be: (partition-by #(< % 0) values)

-- Data.Function.on: f `on` g = \x y -> f (g x) (g y)

groupValues = groupBy ((==) `on` (<0)) values

-- sortBy, insertBy, maximumBy, minimumBy: General versions of their respective
-- functions that take an Ordering-returning function.

-- Sort these lists by length instead of the default lexicographic comparison.
listOfLists = [[5,4,5,4,4],[1,2,3],[3,5,4,3],[],[2],[2,2]]
sortListsByLength = sortBy (\l1 l2 -> compare (length l1) (length l2)) listOfLists

-- A Clojure version of the above would be: (sort-by count values).

-- Book's version.  I guess I forgot about `on` already. :(
sortListsByLength' = sortBy (compare `on` length) listOfLists

--- Data.Char: A module for dealing with Chars and Strings (which are just
--- lists of Chars).

-- Char Predicates of type Char -> Bool:
-- isControl: Is Char a control Char.
-- isSpace: Is Char whitespace (space, tab, newline).
-- isLower, isUpper: Case check.
-- isAlphaNum: Is Char alphanumeric.
-- isPrint: Is Char printable (control Chars are not).
-- isDigit, isOctDigit, isHexDigit: Digit check.
-- isLetter: Is Char a letter.
-- isMark: Is Char a Unicode mark (Chars that combine with preceding ones).
-- isNumber: Is Char numeric.
-- isPunctuation: Is Char punctuation.
-- isSymbol: Is Char a mathematical or currency symbol.
-- isSeparator: Is Char a Unicode separator.
-- isAscii: Is Char in the first 128 Unicode character set.
-- isLatini: Is Char in the first 256 Unicode character set.
-- isAsciiLower, isAsciiUpper: Is Char ASCII lower/upper.

-- To filter strings with these, use the function Data.List.all.
-- all isAlphaNum "bobby123"

-- Use isSpace to simulate Data.List.words.
words' = filter (not . all isSpace) $ groupBy ((==) `on` isSpace) "hey guys its me"

-- GeneralCategory: An enumeration type that classifies Chars.  The function
-- generalCategory will return this category for a given Char.  GeneralCategory
-- is part of the EQ typeclass.

-- toUpper, toLower, toTitle, digitToInt, intToDigit: Char conversions.
-- ord, chr: Covert Chars to their ASCII numbers and vice versa.

-- Implement a Caesar cipher.
encode :: Int -> String -> String
encode n msg = map chr . map (+n) . map ord $ msg

-- I'd probably solve this like this in Clojure:
-- (defn encode [n msg]
--   (->> msg (map int) (map #(+ n %)) (map char) (apply str)))

--- Data.Map: Some functions herein clash with Prelude ones, so use a qualified
--- import.

-- Building a map system from lists of pairs.
phoneBook =
    [ ("betty","555-2938")
    , ("bonnie","452-2928")
    , ("patsy","493-2928")
    , ("lucille","205-2928")
    , ("wendy","939-8282")
    , ("penny","853-2492")
    ]

-- Look up the value of a key.  Same as Data.List.lookup.
findKey :: (Eq k) => k -> [(k, v)] -> v
findKey k (x:xs) | k == (fst x) = snd x
                 | otherwise   = findKey k xs

-- Book's solution, which I like better.
findKey' :: (Eq k) => k -> [(k, v)] -> v
findKey' key xs = snd . head . filter (\(k,v) -> key == k) $ xs

-- Implement the above with Maybe to handle missing keys.
findKeyMaybe :: (Eq k) => k -> [(k, v)] -> Maybe v
findKeyMaybe key ((k,v):xs) = if k == key
                              then Just v
                              else Nothing

-- Implement the above, using a fold.
findKeyMaybe' :: (Eq k) => k -> [(k, v)] -> Maybe v
findKeyMaybe' key xs = foldl (\acc (k,v) -> if k == key
                                           then Just v
                                           else acc) Nothing xs

-- fromList: Take an association list (like above) and return a map of it.
phoneMap = Map.fromList phoneBook

-- empty: Returns empty map.
-- insert: Inserts key and value into a map.
-- Map.insert 3 100 Map.empty

-- Implement fromList using insert and a fold.
fromList' :: Ord k => [(k, v)] -> Map.Map k v
fromList' = foldl (\acc (k,v) -> Map.insert k v acc) Map.empty

-- null: Empty map predicate.
-- size: Return size of map.
-- singleton: Create a map with one entry.
-- lookup: Same as Data.List.lookup, but for maps.
-- member: Key existence predicate.
-- map, filter: Same as list versions (operates on values).
-- toList: Inverse of fromList.
-- keys, elems: Same as Clojure's keys and vals functions.

-- fromListWith: Same as fromList, but uses supplied function to decide what to
-- do with duplicate keys.

phoneBookDupes =
  [ ("betty","555-2938")
  , ("betty","342-2492")
  , ("bonnie","452-2928")
  , ("patsy","493-2928")
  , ("patsy","943-2929")
  , ("patsy","827-9162")
  , ("lucille","205-2928")
  , ("wendy","939-8282")
  , ("penny","853-2492")
  , ("penny","555-2111")
  ]

-- Note: Since the types of the values of the output map need to match the
-- input values in the list form, it isn't possible to use this function to
-- transform the values of the output into something else, like a list.
-- Map.fromListWith (\x y -> [x] ++ [y]) phoneBookDupes  -- Doesn't work.

-- This works, but just concats strings.
-- Map.fromListWith (\x y -> x ++ ", " ++ y) phoneBookDupes

-- This does what I wanted to do above by first converting the values to
-- lists.
phoneBookToMap :: (Ord k) => [(k, v)] -> Map.Map k [v]
phoneBookToMap xs = Map.fromListWith (++) $ map (\(k, v) -> (k, [v])) xs

-- insertWith: Inserts key/value pair, but accepts a function to handle
-- pre-existence of the key.
-- Map.insertWith (+) 2 4 $ (Map.fromList [(1,2),(2,3),(3,4)])

-- Data.Set: Sets, with built-in ordering.

-- Find out which characters were used in both of these strings.
text1 = "I just had an anime dream. Anime... Reality... Are they so different?"
text2 = "The old man left his garbage can out and now his trash is all over my lawn!"

textIntersection = Set.intersection (Set.fromList text1) (Set.fromList text2)

-- fromList: Same as Data.Map.fromList, but for sets.
-- intersection: Set intersection.
-- difference: Set difference.
-- union: Set union.
-- null, size, member, empty, singleton, insert, delete: Set operations.
-- isSubsetof, isProperSubsetOf: Subset predicates.
-- map, filter: Also work on sets.

-- toList: Covert set into list.  (list->set and set->list is faster than nub
-- for large lists, provided they are type Ord).  nub also preserves ordering.

-- Making modules: See Geometry.hs.

-- Hierarchical structures: For example, to section off Geometry, create a
-- folder called that with other modules within it.  Then name them stuff like
-- Geometry.Sphere.  Not doing that here since it's pretty obvious.


--- Chapter 8: Making Our Own Types and Typeclasses

--- Algebraic data types: Define with the `data' keyword and its value
--- constructors, separated by `|' (or).  Value constructors are actually
--- functions.

-- Make a data type for a shape.  A circle is represented by the coordinates of
-- its center and its radius.  A rectangle is represented by the coordinates of
-- its upper left and lower right corner.
data Shape = Circle Float Float Float | Rectangle Float Float Float Float
           deriving (Show)

-- Make a function that takes a Shape and returns its surface.
surface :: Shape -> Float
surface (Circle _ _ r) = pi * (r ^ 2)
surface (Rectangle x1 y1 x2 y2) = abs ((x1 - x2) * (y1 - y2))

-- show functions: The string representation of a value.  In order to make the
-- Shape type part of the Show typeclass, using `deriving (Show)'.

-- Concentric circles with different radii.
-- map (Circle 10 20) [4, 5, 6, 7]

-- Create a better shape that uses an intermediate type.  Using the same name
-- as the type is idiomatic if there's only one value constructor.
data Point = Point Float Float deriving (Show)
data Shape' = Circle' Point Float | Rectangle' Point Point
            deriving (Show)

-- Create new surface function for the new shape type.
surface' :: Shape' -> Float
surface' (Circle' _ r) = pi * (r ^ 2)
surface' (Rectangle' (Point x1 y1) (Point x2 y2)) =
  abs $ (x1 - x2) * (y1 - y2)

-- Create function that moves a shape on the x and y axis.
nudge :: Shape' -> Float -> Float -> Shape'
nudge (Circle' (Point x y) r) dx dy = Circle' (Point (x + dx) (y + dy)) r
nudge (Rectangle' (Point x1 y1) (Point x2 y2)) dx dy =
  Rectangle' (Point (x1 + dx) (y1 + dy)) (Point (x2 + dx) (y2 + dy))

-- Exporting types: In the module block, use the syntax `(..)' to export all
-- value constructors for a type.
-- module Shapes
--        ( Point(..)
--        , Shape(..)
--        , ...

-- One could also omit the `(..)' to force the use of other functions to call
-- value constructors.  Data.Map does this.

--- Record syntax

-- Make a record for a person containing: first name, last name, age, height,
-- phone number, and favorite ice cream flavor.
-- data Person = Person String String Int Float String String
--             deriving (Show)

-- Make one better than the above using record syntax.  Automatically creates
-- lookup functions for each field, with their field name being the function
-- name.
data Person = Person { firstName :: String
                     , lastName :: String
                     , age :: Int
                     , height :: Float
                     , phoneNumber :: String
                     , flavor :: String
                     } deriving (Show)

-- Instantiating a type allows for either the normal syntax (parameters in order), or record syntax where they are in any order, specified by name.
data Car = Car {company :: String, model :: String, year :: Int}
         deriving (Show)

aCar = Car {company="Ford", year=1967, model="Mustang"}

--- Type parameters: Type constructors can take types as parameters and produce
--- new types, unlike data constructors which just produce new values of a
--- type.

-- Maybe is a type constructor (a is a type parameter):
-- data Maybe a = Nothing | Just a

-- Maybe by itself is not a type, whereas `Maybe Num' is.

-- Don't use typeclass constraints in data declarations, as it will
-- unnecessarily constrain functions against it.

-- Implement a 3D vector type and some operations.
data Vector a = Vector a a a deriving (Show)

vplus :: (Num t) => Vector t -> Vector t -> Vector t
vplus (Vector i j k) (Vector l m n) = Vector (i+l) (j+m) (k+n)

-- Multiply Vector with a scalar.
vectMult :: (Num t) => Vector t -> t -> Vector t
vectMult (Vector i j k) m = Vector (i*m) (j*m) (k*m)

-- Multiply two Vectors, returning a scalar.
scalarMult :: (Num t) => Vector t -> Vector t -> t
scalarMult (Vector i j k) (Vector l m n) = i*l + j*m + k*n

-- Type constructors vs. value constructor: Type constructors are before the =,
-- value constructors after.

--- Derived instances: Automatically making our type an instance of a
--- typeclass.

-- Custom person data type.
data Person' = Person' { pFirstName :: String
                       , pLastName :: String
                       , pAge :: Int
                       } deriving (Eq, Show, Read)

-- Eq: Since all fields are of typeclass Eq, this can derive from Eq.  ==,
-- elem, and other functions that have a class constraint of `Eq a' will then
-- work.

-- Show, Read: Show allows the instance to be rendered as a string. Read does
-- the opposite.

-- read "Person' {pFirstName=\"Bric\", pLastName=\"Mahr\", pAge=33}" :: Person'

-- Make an Enum and Bounded algebraic data type.  Since all value constructors
-- are nullary, making it Enum is possible.  Implementing the Bounded typeclass
-- means it has a lowest and highest possible value.
data Day = Monday | Tuesday | Wednesday | Thursday | Friday | Saturday | Sunday
         deriving (Eq, Ord, Show, Read, Bounded, Enum)

-- Being derived from Bounded, minBound and MaxBound work.
-- minBound :: Day

-- Enum gives us the succ and pred functions, and allows for ranges.
-- succ Monday
-- [Monday .. Sunday]
-- [minBound .. maxBound] :: [Day]

--- Type synonyms: Give types different names.

-- String is an example:
-- type String = [Char]

-- This is the only use of the `type' keyword in the language.

phoneBook' :: [(String, String)]
phoneBook' =
  [ ("Fred", "555-1200")
  , ("Gary", "555-1212")
  ]

type Name = String
type PhoneNumber = String
type PhoneBook = [(Name, PhoneNumber)]

inPhoneBook :: Name -> PhoneNumber -> PhoneBook -> Bool
inPhoneBook name pnumber pbook = (name, pnumber) `elem` pbook

-- inPhoneBook "Gary" "555-1212" phoneBook'

-- Like with data types, type synonyms can be parameterized.
type AssocList k v = [(k, v)]

-- Type synonyms can be partially applied.  Here's one that makes a Map of Ints
-- to something.
type IntMap v = Map.Map Int v
-- This would also work:
-- type IntMap = Map.Map Int

-- Either: Has two value constructors.  If Left is used, its contents are type
-- a, if Right is used, its contents are type b.  It's defined something like
-- this:
-- data Either a b = Left a | Right b deriving (Eq, Ord, Read, Show)

-- Either can be useful in the same way Maybe is, except that Either can convey
-- more information than Nothing (which is just a signal that something
-- failed).  Typically errors, use the Left value constructor and results use
-- the Right.

-- Example: Assign locker codes when a locker number is requested.  If the
-- locker is already used, don't tell the code.
data LockerState = Taken | Free deriving (Show, Eq)
type Code = String
type LockerMap = Map.Map Int (LockerState, Code)

-- Failure can happen in one of two ways: The locker is taken or the locker
-- doesn't exist.  Create a lookup function using Either to represent this.
lockerLookup :: Int -> LockerMap -> Either String Code
lockerLookup lockerNumber map =
  case Map.lookup lockerNumber map of
    Nothing -> Left $ "Locker number " ++ show lockerNumber ++ " doesn't exist."
    Just (state, code) -> if state /= Taken
                         then Right code
                         else Left $ "Locker " ++ show lockerNumber ++ " is already taken."

lockers :: LockerMap
lockers = Map.fromList
    [ (100,(Taken,"ZD39I"))
    , (101,(Free,"JAH3I"))
    , (103,(Free,"IQSA9"))
    , (105,(Free,"QOTSA"))
    , (109,(Taken,"893JJ"))
    , (110,(Taken,"99292"))
    ]

-- Recursive data structures

-- List is a recursive data type.  Here's a custom one.
-- data List a = Empty | Cons a (List a) deriving (Show, Read, Eq, Ord)
-- Cons 4 (Cons 5 (Cons 6 Empty))

-- Defining functions as infix: Fixity declarations can be used to define
-- left/right-associativity and how tightly the operator binds.  For example,
-- (*) is `infixl 7 *', (+) is `infixl 6'.
infixr 5 :-:
data List a = Empty | a :-: (List a) deriving (Show, Read, Eq, Ord)
-- 1 :-: 4 :-: Empty

-- Make a function analogous to (++).
infixr 5 .++
(.++) :: List a -> List a -> List a
Empty      .++ ys = ys
(x :-: xs) .++ ys = x :-: (xs .++ ys)
-- (1 :-: 4 :-: Empty) .++ (2 :-: Empty)

-- Implement a binary search tree.
data Tree a = EmptyTree | Node a (Tree a) (Tree a)
            deriving (Show, Read, Eq)

-- Make singleton and insert functions.
singleton :: a -> Tree a
singleton x = Node x EmptyTree EmptyTree

insertTree :: (Ord a) => a -> Tree a -> Tree a
insertTree x EmptyTree = singleton x
insertTree x (Node a left right)
  | x == a = Node x left right
  | x < a = Node a (insertTree x left) right
  | x > a = Node a left (insertTree x right)
-- foldr insertTree EmptyTree [9,4,3,2,3,5,1,3]

-- Implement elem for Trees.
elemTree :: (Ord a) => a -> Tree a -> Bool
elemTree x EmptyTree = False
elemTree x (Node a left right)
  | x == a = True
  | x < a = elemTree x left
  | x > a = elemTree x right
-- elemTree 5 $ foldr insertTree EmptyTree [9,4,3,2,3,5,1,3]

--- Typeclasses 102: Making typeclasses.

-- Example: The Eq typeclass is defined as:
-- class Eq a where
--   (==) :: a -> a -> Bool
--   (/=) :: a -> a -> Bool
--   x == y = not (x /= y)
--   x /= y = not (x == y)

-- Make a new type and make it an instance of Eq.
data TrafficLight = Red | Yellow | Green

-- Since == and /= are defined as mutually recursive, we only have to overwrite
-- one in the instance declaration.  If neither were defined in the typeclass,
-- we would have to implement both.
instance Eq TrafficLight where
  Red == Red = True
  Green == Green = True
  Yellow == Yellow = True
  _ == _ = False

-- Implement Show by hand.  By default, directly deriving Show will convert the
-- value constructors to strings.  This one adds the word "light".
instance Show TrafficLight where
  show Red = "Red light"
  show Green = "Green light"
  show Yellow = "Yellow light"

-- Subclasses: A class constraint on a class declaration.  Here, a has to be an
-- instance of Eq before it can be an instance of Num.
-- class (Eq a) => Num a where ...

-- For non-concrete types (types that are defined as type constructors), like
-- Maybe, instead of writing out their instance declarations for all
-- combinations (e.g. Maybe Int, Maybe String, etc), do it like this:
-- instance (Eq m) => Eq (Maybe m) where
--   Just x == Just y = x == y
--   Nothing == Nothing = True
--   _ == _ = False

-- Note: To check the instances of a typeclass, use `:info <class>' in the
-- REPL.  This also works for type constructors.  Super useful.

-- Implement a YesNo system like JavaScript's booleans using the class system.
class YesNo a where
  yesno :: a -> Bool

-- In JavaScript, 0 is false and everything else true.
instance YesNo Int where
  yesno 0 = False
  yesno _ = True

-- In JavaScript, empty lists/strings are false and everything else true.
instance YesNo [a] where
  yesno [] = False
  yesno _  = True

-- Booleans are the same.
instance YesNo Bool where
  yesno = id

-- Maybe instance
instance YesNo (Maybe m) where
  yesno (Just _) = True
  yesno Nothing  = False

-- Maybe an empty Tree (defined earlier) false and others true.
instance YesNo (Tree t) where
  yesno EmptyTree = False
  yesno _         = True

-- For TrafficeLights, make just Red false.
instance YesNo TrafficLight where
  yesno Red = False
  yesno _   = True

-- Make a function that mimics a JavaScript if-else statement with the first
-- parameter being the predicate.
yesNoIf :: (YesNo y) => y -> a -> a -> a
yesNoIf y a b = if yesno y then a else b

--- The Functor typeclass: Represents things that can be mapped over (or a
--- homomorphism between categories).

-- Functor's typeclass defines fmap but no implementation.
-- class Functor f where
--   fmap :: (a -> b) -> f a -> f b

-- Here, f is not a concrete type, but a type constructor that takes one type
-- parameter.

-- List is actually an instance of the Functor typeclass.  [] is a type
-- constructor that can produce types like [Char] and [Int].
-- instance Functor [] where
--   fmap = map

-- Types that act like a box can be functors.  Maybe is also a box and is a
-- functor.

-- instance Functor Maybe where
--   fmap f (Just x) = Just (f x)
--   fmap f Nothing  = Nothing

-- fmap (+1) (Just 1)

instance Functor Tree where
  fmap _ EmptyTree = EmptyTree
  fmap f (Node x left right) = Node (f x) (fmap f left) (fmap f right)
-- fmap (+1) $ foldr insertTree EmptyTree [9,2,4,6,1,3,2,4]

-- Type constructors with multiple type parameters.  Either takes two, but can
-- still be made a functor.

-- This uses currying on the type constructor, which for Either, makes sense
-- considering the Left value constructor can be of a different type than
-- Right.  Also the way Either is used is such that Left is used for errors.
-- instance Functor (Either a) where
--   fmap f (Right x) = Right (f x)
--   fmap f (Left x)  = Left x

-- Figure out how Map k is made a functor.  I think this is right.
-- instance (Ord k) => Functor (Map.Map k) where
--    fmap f m = Map.fromList $ map (\(k, v) -> (k, f v)) $ Map.toList m

-- fmap (+1) $ Map.fromList [("a",1), ("b",2)]

--- Kinds and some type-foo: (Not sure I completely understand this stuff, so
--- give it another read or read it more carefully in another book).

-- In kind notation, a * means that the type is a concrete type.  * -> * means
-- that a type constructor takes one concrete type and returns another concrete
-- type.  Kind checking can occur on partially applied type constructors, like
-- with `:k Either String'.

-- Explaining Functor's kind: Functor has kind `Functor :: (* -> *) ->
-- Constraint'.  This means that it is a typeclass that requires types that
-- are to be made instances of it to be of kind `* -> *'.

-- How to make a type that is an instance of this?
class Tofu t where
  tofu :: j a -> t a j

-- Tofu has a kind of `Tofu :: (* -> (* -> *) -> *) -> Constraint'.  `j a' has
-- to have a kind of *.  If we assume * for a, then j has to have a kind of `*
-- -> *'.  t has to produce a concrete value, so we know that t has a kind of
-- `* -> (* -> *) -> *'.

-- Let's make a type with a kind of `* -> (* -> *) -> *'.
data Frank a b = Frank { frankField :: b a }
               deriving (Show)

-- λ> :t Frank {frankField = Nothing}
-- Frank {frankField = Nothing} :: Frank a Maybe

-- Make Frank an instance of Tofu.
instance Tofu Frank where
  tofu x = Frank x

-- λ> tofu (Just 'a') :: Frank Char Maybe
-- Frank {frankField = Just 'a'}

-- Make a type to make an instance of Functor.
data Barry t k p = Barry { yabba :: p, dabba :: t k }

instance Functor (Barry a b) where
  fmap f (Barry {yabba = x, dabba = y}) = Barry {yabba = f x, dabba = y}


--- Chapter 9: Input and Output

-- Note: Putting main functions here instead of external files.

-- SOP for normal stand-alone file compilation (of a file called
-- helloworld.hs): ghc --make hellworld

-- To interpret a .hs file on the fly: runhaskell helloword.hs

-- The <- operator binds a value inside an I/O action to a name.

-- return: Makes an I/O action that doesn't do anything (like end execution)
-- out of a pure value.

-- putStr: Like putStrLn except without the CRLF.
-- putChar: Takes a Char and returns an I/O action that prints.

-- getChar: Reads a Char from input.  Note that this only reads when the user
-- hits the return key.

-- when: Takes a boolean, and if true, returns the I/O action supplied.
-- Otherwise it will return ().  A useful abstraction of a typical "if
-- something then do some I/O action else return ()" idiom.

main' = do
  c <- getChar
  when (c /= ' ') $ do
    putChar c
    main'

-- sequence: Takes a list of I/O actions and returns an I/O action that will
-- perform them sequentially.

main'' = do
  rs <- sequence [getLine, getLine, getLine]
  print rs

-- mapM: Map a function over a list, then sequence it.
-- mapM_: Same, except it discards the result of the sequence.
-- forever: Takes an I/O actions and returns an I/O action that repeats forever.

-- forM: Like mapM, except the parameters are reversed.

-- forM is good for situations like this:
main''' = do
  colors <- forM [1,2,3,4] (\a -> do
                              putStrLn $ "Which color do you associate with the number " ++ show a ++ "?"
                              color <- getLine
                              return color)
  putStrLn "The colors you associate with 1, 2, 3, and 4 are: "
  mapM putStrLn colors

-- Note: The last line would probably be better served with a mapM_.

--- Files and streams

-- getContents: An I/O action that lazily reads everything from stdio until EOF.

-- Use getContents to upcase input as long as there is some.
main'''' = do
  contents <- getContents
  putStr (map toUpper contents)

-- interact: Replaces a common idiom of getting an input string, transforming
-- it with a function, then writing output.

main''''' = interact shortLinesOnly

shortLinesOnly :: String -> String
shortLinesOnly = unlines . filter (\line -> (length line) < 10) . lines

-- Book has an even better version:
-- main''''' = interact $ unlines . filter ((<10) . length) . lines

-- Make a program that detects palindromes using interact.
main'''''' = interact $ detectPalindromes

detectPalindromes :: String -> String
detectPalindromes xs = unlines (map (\line -> if (line == reverse line)
                                             then "Palindrome"
                                             else "Not palindrome") (lines xs))

-- Reading files (requires System.IO):
main1' = do
  handle <- openFile "girlfriend.txt" ReadMode
  contents <- hGetContents handle
  putStr contents
  hClose handle

-- openFile: Takes a FilePath (a string) and an IOMode (type defined as
-- ReadMode, WriteMode, AppendMode, or ReadWriteMode) and returns an IO Handle.

-- hGetContents: Takes a Handle and returns IO String, similar to getContents.
-- hClose: Close an open file.

-- withFile: Does all of the above.

-- The previous function using withFile.
main1'' = do
  withFile "girlfriend.txt" ReadMode (\h -> do
                                         contents <- hGetContents h
                                         putStr contents)

-- hGetLine, hPutStr, hPutStrLn, hGetChar: More handler functions.

-- TODO: Skipping ahead to Exceptions.  Come back and read this content later.

--- Exceptions: Haskell supports these for I/O contexts and for pure code like
--- div and head.  Pure code exceptions can only be caught in the impure parts
--- of code.

-- Dealing with missing files without exceptions:
main2' = do (fileName:_) <- getArgs
            fileExists <- doesFileExist fileName
            if fileExists
              then do contents <- readFile fileName
                      putStrLn $ "The file has " ++ show (length (lines contents)) ++ " lines!"
              else do putStrLn "The file doesn't exist!"

-- catch: From System.IO.Error.  Takes an I/O action (like opening a file), the
-- exception handler of type (IOError -> IO a).  Returns an I/O action that
-- will either act the same as the first parameter, or it will do what the
-- exception handler tells it to if the first throws an exception.

-- Note: catch has been moved to Control.Exception.

-- The same, but using exceptions:
main2'' = toTry `catch` handler

toTry :: IO ()
toTry = do (fileName:_) <- getArgs
           contents <- readFile fileName
           putStrLn $ "The file has " ++ show (length (lines contents)) ++ " lines!"

handler :: IOError -> IO ()
handler e = putStrLn "Whoops, had some trouble!"

-- The above catches all IOError types.  Change the above to see what kind of
-- error occurred.
main2''' = toTry `catch` handler'

handler' :: IOError -> IO ()
handler' e
  | isDoesNotExistError e = putStrLn "The file doesn't exist!"
  | otherwise = ioError e

-- isDoesNotExistError: A predicate over IOError types.
-- ioError: Takes an IOError and produces an I/O action that will throw it.

-- isAlreadyExistError, isAlreadyInUseError, isFullError, isEOFError,
-- isIllegalOperation, isPermissionError, isUserError: Other IOError
-- predicates.

-- isUserError is unique in that it evaluates to True when the userError
-- function is used to produce a custom exception with accompanying string.

-- ioe functions: System.IO.Error exports functions that allow interrogating
-- exceptions for attributes.  For example, to print the filename that caused
-- an error, ioeGetFileName can do that.

handler'' :: IOError -> IO ()
handler'' e
  | isDoesNotExistError e =
    case ioeGetFileName e of Just path -> putStrLn $ "Whoops! File does not exist at: " ++ path
                             Nothing -> putStrLn "Whoops! File does not exist at unknown location!"
  | otherwise = ioError e

-- Cover parts of code with multiple handlers if it makes sense.
-- main = do toTry `catch` handler1
--        thenTryThis `catch` hander2
--        launchRockets


--- Chapter 10: Functionally Solving Problems

-- Make an RPN calculator that can take a bunch of operators (skipping the
-- version with just 3).
solveRPN :: String -> Float
solveRPN = head . foldl foldingFunction [] . words
  where foldingFunction (x:y:ys) "*" = (x * y):ys
        foldingFunction (x:y:ys) "+" = (x + y):ys
        foldingFunction (x:y:ys) "-" = (y - x):ys
        foldingFunction (x:y:ys) "/" = (y / x):ys
        foldingFunction (x:y:ys) "^" = (y ** x):ys
        foldingFunction (x:ys) "ln" = log x:ys
        foldingFunction xs "sum" = [sum xs]
        foldingFunction xs numberString = read numberString:xs

-- reads: Test if a read was successful.

-- Make a path optimizer for the Heathrow to London problem.  Input can look
-- like this:
-- 50
-- 10
-- 30
-- 5
-- 90
-- 20
-- 40
-- 2
-- 25
-- 10
-- 8
-- 0

data Node' = Node' Road Road | EndNode Road
data Road  = Road Int Node'
