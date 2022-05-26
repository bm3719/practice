-- Chapter 7: Functions

-- Intermission

-- 1. a, b, c, d

-- 2. d

-- 3.

--   a)
addOneIfOdd n = case odd n of
  True  -> f n
  False -> n
  where f = (\ n -> n + 1)

--   b)
addFix = \x -> \y -> (if x > y then y else x) + 5

--   c)
mflip f y x = f y x

-- Side note: Use :{ :} syntax in GHCi to input multi-line expressions.

-- Side note: Use `set -Wall' in GHCi to turn on non-exhaustive pattern
-- warnings (among other things).  Rather intrusive though.  Use `:set -w' to
-- turn off.

-- Side note: Use `:browse' GHCi command to list definitions in current module.

-- Intermission

-- 1.
--   a) k :: (a, b) -> a
--   b) k2 :: [Char]
--   c) k3

-- 2
f :: (a, b, c) -> (d, e, f) -> ((a, d), (c, f))
f (x1, _, x3) (y1, _, y3) = ((x1, y1), (x3, y3))

-- Intermission

-- 1.
functionC x y =
  case x > y of
    True -> x
    False -> y

-- 2.
ifEvenAdd2 n =
  case even n of
    True  -> n + 2
    False -> n

-- 1.
nums x =
  case compare x 0 of
    LT -> -1
    GT -> 1
    EQ -> 0

-- Intermission

-- 1. 1
-- 2. 11
-- 3. 22
-- 4. 21
-- 5. 12
-- 6. 11
-- 7. 21
-- 8. 21
-- 9. 22
-- 10. 31
-- 11. 23

-- Intermission

avgGrade :: (Fractional a, Ord a) => a -> Char
avgGrade x
  | y >= 0.9  = 'A'
  | y >= 0.8  = 'B'
  | y >= 0.7  = 'C'
  | y >= 0.59 = 'D'
  | y < 0.59 = 'F'
  where y = x / 100

-- 1. -
-- 2. It evals guard conditions in sequence, so branch into the first True.
-- 3. b
-- 4. [a]
-- 5. [a] -> Bool
-- 6. c
-- 7. Ord a, Num a
-- 8. (Ord a, Num a, Num b) => a -> b

-- Exercises

-- Multiple choice
-- 1. d
-- 2. b
-- 3. d
-- 4. b
-- 5. a

-- Let's write code

-- 1.
--   a)

tensDigit :: Integral a => a -> a
tensDigit x = d
  where xLast = fst $ divMod x 10
        d     = snd $ divMod xLast 10

--   b) Yes
--   c)

hunsD x = mod (fst $ divMod x 100) 10

-- 2.
foldBool3 :: a -> a -> Bool -> a
foldBool3 x y True  = x
foldBool3 x y False = y

-- Guard matching
foldBool2 :: a -> a -> Bool -> a
foldBool2 x y t
  | t         = x
  | otherwise = y

-- Case version
foldBool1 :: a -> a -> Bool -> a
foldBool1 x y t =
  case t of
    True -> x
    False -> y

-- 3.
g :: (a -> b) -> (a, c) -> (b, c)
g f (x, y) = ((f x), y)

-- 4. -

-- 5.
roundTripPF :: (Show a, Read a) => a -> a
roundTripPF = read . show

-- 6.
roundTrip2PF :: (Show a, Read b) => a -> b
roundTrip2PF = read . show
