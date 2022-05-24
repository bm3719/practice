-- Chapter 4: Basic Datatypes

-- Data declaration: Type constructor for Bool, with 2 data constructors:
-- data Bool = False | True

-- Intermission

data Mood = Blah | Woot deriving Show

-- 1. Mood
-- 2. Blah or Woot
-- 3. Should be: changeMood :: Mood -> Mood
-- 4.
changeMood :: Mood -> Mood
changeMood Woot = Blah
changeMood _    = Woot
-- 5. -

-- Integer is unbounded.  Always use over Int.

-- Determine numeric type bounds:

-- import GHC.Int
-- minBound :: Int8
-- maxBound :: Int16
-- etc...

-- Use Scientific (from library) instead of Double.  Never use Float.

-- Term-level: Code, values, and execution level.
-- Type-level: Type variables, type constructors, typeclasses.

-- Intermission

-- 1. (not True) && True
-- 2. not (x == 6)
-- 3. Ok.
-- 4. "Merry" > "Happy"
-- 5. "1, 2, 3 " ++ "look at me!"

-- Data.Tuple.swap operates on pair tuples.

-- Exercises

awesome = ["Papuchon", "curry", ":)"]
alsoAwesome = ["Quake", "The Simons"]
allAwesome = [awesome, alsoAwesome]

-- 1. length :: [a] -> Integer

-- 2.
-- a) 5
-- b) 3
-- c) 2
-- d) 5

-- 3. 2nd fails.

-- 4. 6 `div` (length [1,2,3])

-- 5. Bool, True

-- 6. Bool, False

-- 7.
-- True
-- 4
-- 5
-- False
-- Sytax error

-- 8.
isPalindrome :: (Eq a) => [a] -> Bool
isPalindrome s = s == (reverse s)

-- 9.
myAbs :: Integer -> Integer
myAbs x =
  if (x < 0)
  then (0 - x)
  else x

-- 10.
f :: (a, b) -> (c, d) -> ((b, d), (a, c))
f t1 t2 = ((snd t1, snd t2), (fst t1, fst t2))

-- Reading syntax

-- 1.
addOneLength s = w + 1
  where w = length s

-- 2.
-- (\x -> x)

-- 3.
-- (\ (x:xs) -> x)

-- 4.
myFst (a, b) = a

-- Matching functions to types

-- 1. a
-- 2. b
-- 3. a
-- 4. d
