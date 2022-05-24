-- Chapter 5: Types

-- Intermission

-- 1. Functions
-- a) c
-- b) d
-- c) b
-- d) a
-- e) e

-- `undefined' is a keyword for bottom (the type inhabiting all types).  Useful
-- for binding to type variables to check the types of expressions, without
-- assigning concrete types to them.

-- Example of the above given:
-- λ> let f :: a -> a -> a -> a ; f = undefined
-- λ> let x :: Char; x = undefined
-- λ> :t f x

-- In Haskell, uncurrying a function means nesting its arguments into a tuple.

-- Intermission

-- 1. a
-- 2. d
-- 3. d
-- 4. c
-- 5. a
-- 6. e
-- 7. d
-- 8. a
-- 9. a

-- Intermission

-- 1. -

-- 2.
ignoreSecond :: a -> a -> a
ignoreSecond x y = x
ignoreFirst :: a -> a -> a
ignoreFirst x y = y

-- 3.
ignoreFirst' :: a -> b -> b
ignoreFirst' x y = y

-- Intermission

-- 1. myConcat :: [Char] -> [Char]
-- 2. myMult :: Fractional a => a -> a
-- 3. myTake :: Int -> [Char]
-- 4. myCom :: Int -> Bool
-- 5. myAlph :: Char -> Bool

-- Exercises

-- Multiple choice
-- 1. c
-- 2. a
-- 3. b
-- 4. c

-- Determine the type
-- 1.
--   a. Num a => a
--   b. Num a => (a, [Char])
--   c. (Integer, [Char])
--   d. Bool
--   e. Int
--   f. Bool
-- 2. Num a => a
-- 3. Num a => a -> a
-- 4. Fractional a => a
-- 5. [Char]

-- Does it compile?
-- 1. wahoo: bigNum isn't a function
-- 2. Okay.
-- 3. c and d: b isn't a function
-- 4. a and b: c not defined.

-- Type variable or specific type constructor?
-- 1. [0]: constrained polymorphic type variable
--    [1]: fully polymorphic type variable
--    [2]: concrete type constructor
--    [3]: concrete type constructor
-- 2. Zed: concrete type constructor
--    Blah: concrete type constructor
-- 3. a: fully polymorphic type variable
--    b: constrained polymorphic type variable
--    C: concrete type constructor
-- 4. f: fully polymorphic type variable
--    g: fully polymorphic type variable
--    C: concrete type constructor

-- Write a type signature
-- 1. [a] -> a
-- 2. (Ord a, Ord b) => a -> b -> Bool
-- 3. (a, b) -> b

-- Given a type, write the function
-- 1. i = id
-- 2. c x y = x
-- 3. c'' x y = x
-- 4. c' x y = y
-- 5. r = tail
-- 6.
co :: (b -> c) -> (a -> b) -> (a -> c)
co f g = f . g
-- 7.
a :: (a -> c) -> a -> a
a f x = x
-- 8.
a' :: (a -> b) -> a -> b
a' f x = f x

-- Fix it

-- 1.

fstString :: [Char] -> [Char]
fstString x = x ++ " in the rain"

sndString :: [Char] -> [Char]
sndString x = x ++ " over the rainbow"

sing = if (x > y) then fstString x else sndString y
  where x = "Singing"
        y = "Somewhere"

-- 2. -

-- 3.

main = do
  print (1 + 2)
  putStrLn (show 10)
  print (negate (-1))
  print ((+) 0 blah)
  where blah = negate 1

-- Type-Kwon-Do

-- 1.

f :: Int -> String
f = undefined

g :: String -> Char
g = undefined

h :: Int -> Char
h = g . f

-- 2.

data A
data B
data C

q :: A -> B
q = undefined

w :: B -> C
w = undefined

e :: A -> C
e = w . q

-- 3

data X
data Y
data Z

xz :: X -> Z
xz = undefined

yz :: Y -> Z
yz = undefined

xform :: (X, Y) -> (Z, Z)
xform (x, y) = (xz x, yz y)

-- 4.

munge :: (x -> y) -> (y -> (w, z)) -> x -> w
munge f g x = fst $ g $ f x
