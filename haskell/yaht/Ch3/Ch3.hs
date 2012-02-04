module Ch3
       where

-- 3.1
-- 3 * sqrt 9 * 2

-- 3.2
-- snd $ fst ((1, 'n'), "foo")

-- foldr (-) 1 [4, 8, 5]
-- Decomposes to: (4 - (8 - (5 - 1)))
-- foldl (-) 1 [4, 8, 5]
-- Decomposes to: (((1 - 4) - 8) - 5)

-- 3.3
-- map Char.isLower "aBCde"

-- 3.4
-- length $ filter Char.isLower "aBCde"

-- 3.5
listMax :: [Int] -> Int
listMax lst = foldr max 0 lst

-- 3.6
firstSecond :: [(a, b)] -> a
firstSecond lst = fst $ head $ tail lst

-- Conditional example.
signum2 x =
  if x < 0
     then -1
  else if x > 0
       then 1
       else 0

-- Case with wildcard example.
caseTest x =
  case x of
    0 -> 1
    1 -> 5
    _ -> -1

-- Semicolon version of the above.
caseTest2 x = case x of { 0 -> 1 ; 1 -> 5; _ -> -1 }

-- Using (.), the following are equivalent (and also analogous to functional composition).
-- caseTest $ square 4
-- (caseTest . square) 4

-- let/in provides local bindings within functions.
roots a b c =
  let det = sqrt (b * b - 4 * a * c)
      twice_a = 2 * a
  in ((-b + det) / twice_a, (-b + det)/twice_a)

-- Recursive base cases.
exponentiate x 1 = x
exponentiate x y = x * exponentiate x (y - 1)

-- 3.7
fib :: Int -> Int
fib 1 = 1
fib 2 = 2
fib n = fib (n - 2) + fib (n - 1)
-- or
fib2 :: Int -> Int
fib2 n | ((n == 1) || (n == 2)) = 1
       | otherwise              = fib2 (n - 2) + fib2 (n - 1)

-- 3.8
mult :: Int -> Int -> Int
mult a 0 = 0
mult a b = a + mult a (b - 1)

-- 3.9
my_map :: (a -> b) -> [a] -> [b]
my_map f []     = []
my_map f (w:ws) = f w : my_map f ws
