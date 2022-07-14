{-# LANGUAGE FlexibleInstances #-}
{-# LANGUAGE GeneralizedNewtypeDeriving #-}
module Ch11 where -- Algebraic datatypes

import Data.Int
import Data.List
import Data.Char

-- Nullary constructors are data constructors that take zero arguments.  Unary
-- constructors take 1.

-- Kinds:

-- The kind of a fully applied, concrete type is *.  A kind of * -> * is
-- waiting to be applied, before it can become a concrete type.  E.g.,
-- :k [] is * -> * because [] is a data constructor not yet applied to a type.

-- Type constants are type constructors that take no arguments.  On the data
-- constructor side, analogous are the constant values, which likewise take no
-- arguments.

data TestT a = TestV
  deriving Show
f :: Int -> TestT Int
f x = TestV

-- Need to check, but seems a type constructor parameter (a type variable) is
-- needed when a type variable is present in one or more of the data
-- constructors.  If a concrete type is used in the data constructor, then a
-- parameter in the type constructor isn't needed.

-- Intermission: Exercises

-- 1. type constructor
-- 2. * -> *
-- 3. *
-- 4. Num a => Doggies a
-- 5. Doggies Integer
-- 6. Doggies String
-- 7. both
-- 8. DogueDeBordeaux a
-- 9. DogueDeBordeaux String

-- Intermission: Exercises

data Price = Price Integer
           deriving (Eq, Show)

data Manufacturer = Mini | Mazda | Tata
                  deriving (Eq, Show)

data Airline = PapuAir | CatapultsR'Us | TakeYourChancesUnited
             deriving (Eq, Show)

data Vehicle = Car Manufacturer Price
             | Plane Airline
             deriving (Eq, Show)

myCar    = Car Mini  (Price 14000)
urCar    = Car Mazda (Price 20000)
clownCar = Car Tata  (Price 7000)
doge     = Plane PapuAir

-- 1. myCar :: Vehicle

-- 2.
isCar :: Vehicle -> Bool
isCar (Car _ _) = True
isCar _         = False

isPlane :: Vehicle -> Bool
isPlane (Plane _) = True
isPlane _         = False

areCars :: [Vehicle] -> [Bool]
areCars = map isCar

-- 3.
getManu :: Vehicle -> Manufacturer
getManu (Car m _) = m

-- 4. Error

-- 5. Skipping, since it'll break other answers.

-- A product is a data constructor that takes 2 or more arguments.

-- The cardinality of a datatype is the number of possible values it defines,
-- 1 to infinity.  E.g., Bool = 2, Int = (maxBound :: Int) * 2 + 2.

-- Intermission: Exercises

-- 1. 1
-- 2. 3
-- 3. 2^16
-- 4. Integer is unbounded, therefore of infinite cardinality.
-- 5. 2^8, and represented with 8 bits.

-- Intermission: Exercises

-- 1. Example.  Error, since Example isn't a data constructor and doesn't have
-- a type.

-- 2. Yes.

-- 3.
data Example' = MakeExample' Int deriving Show
-- The fact that the data constructor takes an argument is shown in the type.

-- MakeExample' has the same cardinality as Int.

-- type: Keyword that defines type synonyms.

-- newtype: Defines a type with a single unary data constructor.  Cardinality
-- always equals the contained type.  Benefit is no runtime overhead, as it
-- reuses the contained type at runtime.  Basically a means to wrap types to
-- ensure correctness during compile-time and communicate intent.  The other
-- main benefit is the ability to declare typeclass instances.

newtype Goats = Goats Int deriving (Eq, Show)
newtype Cows = Cows Int deriving (Eq, Show)

tooManyGoats :: Goats -> Bool
tooManyGoats (Goats n) = n > 42

class TooMany a where
  tooMany :: a -> Bool

instance TooMany Int where
  tooMany n = n > 42
-- tooMany (42 :: Int)

instance TooMany Goats where
  tooMany (Goats n) = n > 43

-- The GeneralizedNewtypeDeriving pragma lets a newtype inherit the typeclass
-- implementation of the type contained (e.g., Int in this case) by including
-- that typeclass in the deriving clause.  Pretty sure this is unsafe though.

-- Intermission: Exercises

-- 1.
instance TooMany (Int, String) where
  tooMany (n, _) = n > 42

-- 2.
instance TooMany (Int, Int) where
  tooMany (n1, n2) = n1 + n2 > 42

-- 3.
instance (Num a, TooMany a) => TooMany (a, a) where
  tooMany (x,y) = tooMany y && tooMany x

-- The cardinality of sum types is the sum of the cardinality of the data
-- constructors.  In logic, this corresponds to `or'.

-- Intermission: Exercises

-- 1. 4
-- 2. 258

-- The cardinality of product types is the product of the cardinality of the
-- contained types.  In logic, this corresponds to `and'.

-- Records: Product types with accessors.

data Person =
  Person { name :: String
         , age  :: Int }
  deriving (Eq, Show)
-- λ> Person {name = "sdf", age = 4}
-- Person {name = "sdf", age = 4}
-- λ> let p = Person "sddd" 5
-- λ> name p
-- "sddd"

-- Intermission: jammin exercises

data Fruit =
    Peach
  | Plum
  | Apple
  | Blackberry
  deriving (Eq, Show, Ord)

data JamJars =
  Jam Fruit Int
  deriving (Eq, Show)

-- 1. Skipping extra file.

-- 2.
data JamJars' =
  Jam' { fruit :: Fruit
       , count :: Int }
  deriving (Eq, Show)

-- 3. 4 * ((maxBound :: Int) * 2 + 2)

-- 4. Added above.

-- 5.
row1 = Jam' Peach 3
row2 = Jam' Plum 2
row3 = Jam' Peach 4
allJam = [row1, row2, row3]
-- map count allJam

-- 6.
totalJam = foldr (\x y -> (count x) + y) 0 allJam

-- 7.
maxJam :: [JamJars'] -> JamJars'
maxJam = foldr1 (\x y -> if (count x) > (count y) then x else y)

-- 8. Done.

-- 9. Added Ord Fruit.
sortJam :: [JamJars'] -> [JamJars']
sortJam = sortBy (\x y -> compare (fruit x) (fruit y))

-- 10.
groupJam :: [JamJars'] -> [[JamJars']]
groupJam = (groupBy (\x y -> (fruit x) == (fruit y))) . sortJam

-- Normal forms

data Fiction = Fiction deriving Show
data Nonfiction = Nonfiction deriving Show
data BookType = FictionBook Fiction
              | NonfictionBook Nonfiction
              deriving Show
type AuthorName = String  -- type synonym
-- Not a sum of products, so not in normal form, but more like: (a * (b + c))
data Author = Author (AuthorName, BookType)
-- Normal form version of the same, in this form: (a * b) + (c * d)
data Author' = Fiction' AuthorName
             | Nonfiction' AuthorName
             deriving (Eq, Show)

-- Exercises

-- 1.
data FlowerType = Gardenia
                | Daisy
                | Rose
                | Lilac
                deriving Show
type Gardener = String
data Garden = Garden Gardener FlowerType
            deriving Show
-- Normal form of Garden.
data GardenNF = Gardenia' Gardener
              | Daisy' Gardener
              | Rose' Gardener
              | Lilac' Gardener
              deriving Show

-- Exercise

data OperatingSystem =  GnuPlusLinux
                     | OpenBSDPlusNevermindJustBSDStill
                     | Mac
                     | Windows
                     deriving (Eq, Show)
data ProgrammingLanguage = Haskell
                         | Agda
                         | Idris
                         | PureScript
                         deriving (Eq, Show)
data Programmer = Programmer { os :: OperatingSystem
                             , lang :: ProgrammingLanguage }
                  deriving (Eq, Show)

allOperatingSystems :: [OperatingSystem]
allOperatingSystems = [ GnuPlusLinux
                      , OpenBSDPlusNevermindJustBSDStill
                      , Mac
                      , Windows ]
allLanguages :: [ProgrammingLanguage]
allLanguages = [Haskell, Agda, Idris, PureScript]

allProgrammers :: [Programmer]
allProgrammers = [ Programmer { os = o, lang = l }
                 | o <- allOperatingSystems
                 , l <- allLanguages ]

-- Side note: Data.List.nub removes duplicates.

-- Partial application of data constructors is possible.

-- In the algebra of types, functions are regarding as exponential, e.g.:
-- a -> b -> c = (c ^ b) ^ a = c ^ (b * a)

-- Intermission: Exercises

-- 1. 4 + 4 = 8
-- 2. 4 * 4 = 16
-- 3. 4 ^ 4 = 256
-- 4. 2 * 2 * 2 = 8
-- 5. 2 ^ 2 ^ 2 = 16
-- 6. 2 ^ (4 * 4) = 65536

-- Higher-kinded types: a type not of kind *, meaning it doesn't yet exist at
-- the term level.  That includes polymorphic types not yet applied to concrete
-- types, as well as partially applied versions of the same.

data BinaryTree a = Leaf
                  | Node (BinaryTree a) a (BinaryTree a)
                  deriving (Eq, Ord, Show)
insert' :: Ord a => a -> BinaryTree a -> BinaryTree a
insert' b Leaf = Node Leaf b Leaf
insert' b (Node left a right)
  | b == a = Node left a right
  | b < a = Node (insert' b left) a right
  | b > a = Node left a (insert' b right)
-- Build test tree
tree = insert' 5 $ insert' 3 $ insert' 0 Leaf

-- Exercises

-- Write map for BinaryTree
treeMap :: (a -> b) -> BinaryTree a -> BinaryTree b
treeMap _ Leaf = Leaf
treeMap f (Node left x right) = Node (treeMap f left) (f x) (treeMap f right)

-- Convert binary tree to list: Skipping order variations, since these are just
-- different arrangements of the list concatenation.
treeToList :: BinaryTree a -> [a]
treeToList Leaf = []
treeToList (Node left x right) = (treeToList left) ++ [x] ++ (treeToList right)

-- Write catamorphism for tree
foldTree :: (a -> b -> b) -> b -> BinaryTree a -> b
foldTree f x = foldr f x . treeToList

-- Rewrite map using foldTree: Doing this the way intended would require a
-- refactor of the above.  Decided not to waste the time on that.
treeMap' :: Ord b => (a -> b) -> BinaryTree a -> BinaryTree b
treeMap' f = foldTree (\x y -> insert' (f x) y) Leaf

-- Chapter Exercises

-- Multiple choice
-- 1. a
-- 2. c
-- 3. b
-- 4. c

-- Ciphers
-- Copied Cipher.hs to this directory and extended with new cipher.

-- as-patterns: A way to have destructuring and a binding for the value as a
-- whole.
asEx :: Show a => (a, b) -> IO (a, b)
asEx t@(a, _) = do
  print a
  return t

-- Exercises

-- 1.
isSubsequenceOf' :: (Eq a) => [a] -> [a] -> Bool
isSubsequenceOf' [] _ = True
isSubsequenceOf' _ [] = False
isSubsequenceOf' search@(x:xs) (y:ys) =
  if x == y
  then (isSubsequenceOf' xs ys)
  else (isSubsequenceOf' search ys)

-- 2.
capitalizeWords :: String -> [(String, String)]
capitalizeWords s = map (\x -> (x, toUpper (head x) : (tail x)))
                          (words s)

-- Language exercises

-- 1.
capitalizeWord :: String -> String
capitalizeWord ""     = ""
capitalizeWord (c:cs) = toUpper c : cs

-- 2. Probably a better way to do this, but this isn't too bad, so leaving it.
sentenceify [] = []
sentenceify (w:[]) = [w]
sentenceify (w:ws) =
  if (w !! ((length w) - 1)) == '.'
  then w : capitalizeWord (head ws) : sentenceify (tail ws)
  else w : sentenceify ws

capitalizeParagraph :: String -> String
capitalizeParagraph s =
  unwords $
  sentenceify $
  (\(w:ws) -> capitalizeWord w : ws) $
  words s

-- Skipping Phone exercise and Hutton's Razor, since there's already enough
-- huge timesinks in this chapter.
