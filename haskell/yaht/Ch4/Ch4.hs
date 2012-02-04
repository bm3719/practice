module Ch4 where

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

-- The `data' keyword defines a datatype (data structure).  The definition
-- includes constructors.
data Pair a b = Pair a b
-- This says: Create a new data structure called `Pair' that takes two
-- different types (a and b).  Only type variables can be used here.  This data
-- structure has one constructor (which doesn't have to be the same name, but
-- is here) that takes two parameters of the specified types.
pairFst (Pair x y) = x
pairSnd (Pair x y) = y

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

-- Multiple constructors.

-- Maybe: A prelude-included datatype.  This definition says there are two ways
-- to define something of type `Maybe a'.  One is a nullary constructor
-- `Nothing'.  The other is to use the constructor `Just'.
-- data Maybe a = Nothing
--              | Just a

-- Define a function like `head' that returns the first element.  Unlike
-- `head', which throws an exception is passed [], this returns Nothing.
-- `firstElement [1,2]' returns `Just 1'.
firstElement :: [a] -> Maybe a
firstElement []     = Nothing
firstElement (x:xs) = Just x

-- Another example.
-- findElement (\x -> if x > 3 then True else False) [1, 2, 4, 6]
findElement :: (a -> Bool) -> [a] -> Maybe a
findElement p []     = Nothing
findElement p (x:xs) = if p x then Just x
                       else findElement p xs

-- Either: Allows expression of alteration, defined as:
-- data Either a b = Left a
--                 | Right b

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

-- Custom list datatype, illustrating recursive datatypes.
data List a = Nil
            | Cons a (List a)

listLength :: List t -> Int
listLength Nil         = 0
listLength (Cons w ws) = 1 + listLength ws

-- 4.8
listHead :: List t -> Maybe t
listHead Nil         = Nothing
listHead (Cons w ws) = Just w

listTail :: List t -> Maybe (List t)
listTail Nil         = Nothing
listTail (Cons w ws) = Just ws

-- Rewrite of foldl.
-- mFoldl :: (a -> b -> a) -> a -> [b] -> a
-- mFoldl f x []     = x
-- mFoldl f x (w:ws) = f (mFoldl f x ws) w
listFoldl :: (a -> b -> a) -> a -> List b -> a
listFoldl f x Nil         = x
listFoldl f x (Cons w ws) = f (listFoldl f x ws) w

-- mFoldr :: (a -> b -> b) -> b -> [a] -> b
-- mFoldr f x []     = x
-- mFoldr f x (w:ws) = f w (mFoldr f x ws)
listFoldr :: (a -> b -> b) -> b -> List a -> b
listFoldr f x Nil         = x
listFoldr f x (Cons w ws) = f w (listFoldr f x ws)

-- Binary trees.
data BinaryTree a = Leaf a
                  | Branch (BinaryTree a) a (BinaryTree a)

treeSize :: BinaryTree t -> Int
treeSize (Leaf x)       = 1
treeSize (Branch l x r) = 1 + treeSize l + treeSize r

-- Test data.
bt = Branch (Leaf 5) 4 (Branch (Leaf 1) 3 (Leaf 2))

-- 4.9
elements :: BinaryTree t -> [t]
elements (Leaf x)       = [x]
elements (Branch l x r) = elements l ++ [x] ++ elements r

-- 4.10
treeFold :: (a -> b -> b) -> b -> BinaryTree a -> b
treeFold f z (Leaf x)       = f x z
treeFold f z (Branch l x r) = treeFold f (f x (treeFold f z r)) l

-- Enumerated sets: Constrain the datatype Color to specific values and include
-- a custom type.
data Color = Red | Orange | Yellow | Green | Blue | Purple | White | Black
           | Custom Int Int Int
colorToRGB Red            = (255, 0, 0)
colorToRGB Orange         = (255, 128, 0)
colorToRGB Yellow         = (255, 255, 0)
colorToRGB Green          = (0, 255, 0)
colorToRGB Blue           = (0, 0, 255)
colorToRGB Purple         = (255, 0, 255)
colorToRGB White          = (255, 255, 255)
colorToRGB Black          = (0, 0, 0)
colorToRGB (Custom r g b) = (r, g, b)

-- Unit type from Prelude, analogous to void.
-- data () = ()

-- Continuation passing style: A function idiom where function arguments are
-- passed that represent what to do next.

-- CPS fold.  Function f takes 3 arguments, the last being a continuation.
cfold' :: (a -> b -> (b -> b) -> b) -> b -> [a] -> b
cfold' f z []     = z
cfold' f z (w:ws) = f w z (\x -> cfold' f x ws)

-- Wrapper for cfold', emulating a standard fold.
cfold :: (a -> b -> b) -> b -> [a] -> b
cfold f z l = cfold' (\x t g -> f x (g t)) z l

-- One thing the CPS fold gives is the ability to change the helper function
-- directly (.e.g, changing the order of the fold).
-- cfold' (\x t g -> (x : g t)) [] [1..10]
-- [1,2,3,4,5,6,7,8,9,10]
-- cfold' (\x t g -> g (x : t)) [] [1..10]
-- [10,9,8,7,6,5,4,3,2,1]

-- 4.11
-- foldl (-) 1 [1,2,3]
-- -5
-- foldr (-) 1 [1,2,3]
-- 1
-- cfold' (\x y f -> f (x - y)) 1 [1,2,3]
-- 1
-- cfold' (\x y f -> f (y - x)) 1 [1,2,3]
-- -5

-- 4.12
cmap :: (a -> [b] -> [b]) -> [a] -> [b]
cmap f []     = []
cmap f (w:ws) = f w (cmap f ws)
-- cmap (\x y -> x + 1 : y) [1..10]
-- cmap (\x y -> x + 1 : [x] ++ y) [1..10]
-- This is more correct, I think.
cmap2 :: (a -> [a] -> ([a] -> [b]) -> [b]) -> [a] -> [b]
cmap2 f []     = []
cmap2 f (w:ws) = f w ws (\x -> cmap2 f x)
-- cmap2 (\x y f -> x + 1 : f y) [1..10]

-- Not sure about this one.
cfilter :: ([a] -> (b -> [c]) -> [c]) -> (a -> Bool) -> [a] -> [c]
cfilter f g []     = []
cfilter f g (w:ws) = f (if g w then [w] else []) (\x -> cfilter f g ws)
-- cfilter (\x y f g -> x ++ f y) (\x -> x `mod` 2 == 0) [1..10]
