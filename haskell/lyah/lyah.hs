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

-- Reimplement replicate using recursion.  A better solution uses take and
-- repeat.
replicate' :: (Num a, Ord a) => a -> b -> [b]
replicate' x e
  | x <= 0     = []
  | otherwise = e:replicate' (x - 1) e

-- Reimplement take using recursion.
take' :: (Num a, Ord a) => a -> [b] -> [b]
take' n _
  | n <= 0      = []
take' _ []     = []
take' n (x:xs) = x:take' (n - 1) xs

-- Reimplement reverse.
reverse' :: [a] -> [a]
reverse' [] = []
reverse' (x:xs) = reverse' xs ++ [x]

-- Reimplement repeat.
repeat' :: a -> [a]
repeat' x = x:repeat' x

-- Reimplement zip.
zip' :: [a] -> [b] -> [(a, b)]
zip' _ [] = []
zip' [] _ = []
zip' (x:xs) (y:ys) = (x,y):zip' xs ys

-- Reimplement elem.
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

-- Chapter 6: Higher order functions

-- (Some talk about curried functions and partial application is here.)

-- Sectioning an infix function: Surround it with parentheses and only supply
-- the parameter on one side.
divideByTen :: Double -> Double
divideByTen = (/10)

-- Take a function and apply it twice.
applyTwice :: (a -> a) -> a -> a
applyTwice f a = f (f a)

-- Reimplement zipWith using higher order programming.
zipWith' :: (a -> b -> c) -> [a] -> [b] -> [c]
zipWith' _ _ [] = []
zipWith' _ [] _ = []
zipWith' f (x:xs) (y:ys) = f x y : zipWith' f xs ys

-- Reimplement flip.
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
