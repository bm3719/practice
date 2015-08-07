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
