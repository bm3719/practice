import Data.Char
import IO
import Random

fac :: Integer -> Integer
fac 0 = 1
fac n | n > 0 = n * fac (n-1)

test1 :: Integer -> Integer
test1 n = n + 2

test2 :: Integer -> Integer
test2 n = n * 3

-- ttest :: Integer -> Float
-- ttest n = (n `mod` 2) / 2
 
tup = ((1, 'a'), "foo")

-- pattern matching version of fibonacci
fib :: Integer -> Integer
fib n | n < 2     = n
      | otherwise = fib(n - 1) + fib(n - 2) 

-- create a list of n fibonacci numbers
fibList :: Integer -> [Integer]
fibList n | n < 1 = []
          | otherwise = fibList(n - 1) ++ [fib(n - 1)]

-- a better version of the above
fibList2 :: Integer -> [Integer]
fibList2 n = map fib [0..(n - 1)]

-- add commas to the ends of all but the last elem in a 
-- list of strings
commaDelim :: [String] -> [String]
commaDelim []       = []
commaDelim (s : []) = [s]
commaDelim (s : a)  = (s ++ ",") : commaDelim a 

-- tie it all together 
makeList :: Integer -> String
makeList n = (concat . commaDelim . (map show) . fibList2) n

-- answers for yaht, minus questions that required external files

-- 3.3
charListCase :: String -> [Bool]
charListCase []       = []
charListCase (c : ws) = (isLower c) : charListCase ws

-- 3.4
countLowerChars :: String -> Int
countLowerChars s = length (filter isLower s)

-- 3.5
listMax :: [Integer] -> Integer
listMax []        = 0
listMax (m1 : ms) = max m1 (listMax ms) 

-- 3.6
listPairsCheck :: [(a1, b1)] -> a1
listPairsCheck a = fst (head (tail a))

-- layout test
layout1 x = 
  case x of 
    0 -> 1
    1 -> 5
    _ -> -1

-- semicolon version
layout2 x = case x of { 0 -> 1 ; 1 -> 5; _ -> -1 }

-- 3.8
-- mult (a, b) = 0                    b = 0
--             | a + mult (a, b - 1)  otherwise
mult :: Integer -> Integer -> Integer
mult n 0  = 0
mult n m  = n + mult n (m - 1) 

-- 3.9
my_map :: (a -> b) -> [a] -> [b]
my_map _ []        = []
my_map f (w : ws)  = f w : my_map f ws 

-- lambda expression with foldl
--foldl (\ x -> (+) x) 0 [1, 3, 4]

-- generate random single digit number
genRandDigit :: IO Int
genRandDigit = getStdRandom (randomR (0, 9))
--genRandDigit = RandomRIO (0::Int, 10) 

-- 4.1
-- 1. [Char]
-- 2. Err
-- 3. (Num, Char)
-- 4. Int
-- 5. Err
--
-- 4.2
-- 1. snd :: (a, b) -> b
-- 2. head :: [a] -> a
-- 3. null :: [a] -> Bool
-- 4. head . tail :: [a] -> a
-- 5. head . head :: [[a]] -> a

-- 4.3
-- 1. a -> [a]
-- 2. a -> b -> b -> (a, [b])
-- 3. Num a => a -> a
-- 4. a -> [Char]
-- 5. (Char a) -> a 
-- 6. Err
-- 7. Num a => a -> a

-- 4.4
data Triple a b c = Triple a b c
tripleFst (Triple a b c) = a 
tripleSnd (Triple a b c) = b
tripleThr (Triple a b c) = c

-- 4.5
data Quadruple a b = Quadruple a a b b
firstTwo :: Quadruple a b -> [a]
firstTwo (Quadruple a b c d) = [a, b]
lastTwo :: Quadruple a b -> [b]
lastTwo (Quadruple a b c d) = [c, d]

-- Maybe
firstElement :: [a] -> Maybe a
firstElement []     = Nothing
firstElement (x:xs) = Just x

-- findElem (\ x -> if x > 3 then True else False) [1, 2, 4, 6]
findElem :: (a -> Bool) -> [a] -> Maybe a
findElem p []     = Nothing
findElem p (x:xs) = if p x then Just x
                    else findElem p xs

-- 4.6 Multiple constructors
data Tuple a b c d = One a
                   | Two a b
                   | Three a b c
                   | Four a b c d

tuple1 (One   a      ) = Just a
tuple1 (Two   a b    ) = Just a
tuple1 (Three a b c  ) = Just a
tuple1 (Four  a b c d) = Just a

tuple2 (One   a      ) = Nothing
tuple2 (Two   a b    ) = Just b
tuple2 (Three a b c  ) = Just b
tuple2 (Four  a b c d) = Just b

tuple3 (One   a      ) = Nothing
tuple3 (Two   a b    ) = Nothing
tuple3 (Three a b c  ) = Just c
tuple3 (Four  a b c d) = Just c

tuple4 (One   a      ) = Nothing
tuple4 (Two   a b    ) = Nothing
tuple4 (Three a b c  ) = Nothing
tuple4 (Four  a b c d) = Just d

-- 4.7
fromTuple :: Tuple a b c d -> Either (Either a (a, b)) (Either (a, b, c) (a, b, c, d))
fromTuple (One   a      ) = Left  (Left  a        )
fromTuple (Two   a b    ) = Left  (Right (a,b)    ) 
fromTuple (Three a b c  ) = Right (Left  (a,b,c)  )
fromTuple (Four  a b c d) = Right (Right (a,b,c,d))

-- Tuple type testing
--testTuple :: Tuple sdf
--testTuple = Four 4 5 6 7

-- 4.8
data List a = Nil
            | Cons a (List a)

listLength Nil = 0
listLength (Cons x xs) = 1 + listLength xs

listHead :: List a -> Maybe a
listHead Nil         = Nothing
listHead (Cons x xs) = Just x

--listTail :: List a -> Maybe List a
--listTail Nil          = Nothing
--listTail (Cons x Nil) = Just x
--listTail (Cons x xs)  = Just xs
listTail (Cons x xs) = xs

listFoldl :: (a -> b -> a) -> a -> List b -> a
listFoldl p k Nil         = k
listFoldl p k (Cons x xs) = listFoldl p (p k x) xs 

listFoldr :: (a -> b -> b) -> b -> List a -> b
listFoldr p k Nil         = k
listFoldr p k (Cons x xs) = p x (listFoldr p k xs)

-- binary trees
data BinaryTree a = Leaf a
                  | Branch (BinaryTree a) a (BinaryTree a)

-- treeSize
treeSize :: BinaryTree a -> Integer
treeSize (Leaf a)              = 1
treeSize (Branch left a right) = (treeSize left) + 1 + (treeSize right)

-- 4.9
elements :: BinaryTree a -> [a]
elements (Leaf x)              = [x]
elements (Branch left x right) = (elements left) ++ [x] ++ (elements right)

-- 4.10
foldTree :: (a -> b -> b) -> b -> BinaryTree a -> b
foldTree f z (Leaf x)           = f x z
foldTree f z (Branch lhs x rhs) = foldTree f (f x (foldTree f z rhs)) lhs 

elements2 = foldTree (:) []

-- fizzbuzz
fbarr = [1..100]

fizzLook :: Integer -> String
fizzLook x | ((x `mod` 3) == 0) && ((x `mod` 5) == 0) = "FizzBuzz"
           | ((x `mod` 3) == 0)                       = "Fizz"
           | ((x `mod` 5) == 0)                       = "Buzz"
           | otherwise                                = show x

fizzBuzz :: [Integer] -> [String]
fizzBuzz x = map fizzLook x

-- Return last element of a list (from LtU)
myLast :: [a] -> a
myLast xs = head $ reverse xs
