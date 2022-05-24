-- Chapter 6: Typeclasses

-- Intermission

-- 1. Int
-- 2. Ordering
-- 3. Failure
-- 4. Bool

-- λ> fromEnum 'b'
-- 98
-- λ> (toEnum 97) :: Char
-- 'a'

-- () is an empty tuple, a value and type with a single value, called `unit'.

-- In the :info for a typeclass, this syntax informs what minimal
-- implementation is necessary for a new datatype.  In this case, only one of
-- the functions need be implemented.

-- {-# MINIMAL showsPrec | show #-}

-- data Mood = Blah
-- instance Show Mood where
--   show _ = "Blah"

-- Alternatively:
-- data Mood = Blah deriving Show

-- Typeclass instances can include further typeclass constraints, e.g.:
data Identity a = Identity a
instance Eq a => Eq (Identity a) where
  (==) (Identity v) (Identity v') = v == v'

-- Intermission

-- 1.
data TisAnInteger = TisAn Integer
instance Eq TisAnInteger where
  (==) (TisAn i) (TisAn i') = i == i'

-- 2.
data TwoIntegers = Two Integer Integer
instance Eq TwoIntegers where
  (==) (Two x y) (Two x' y') = x == x' && y == y'

-- 3.
data StringOrInt =
    TisAnInt   Int
  | TisAString String
instance Eq StringOrInt where
  (==) (TisAnInt i) (TisAnInt i') = i == i'
  (==) (TisAString s) (TisAString s') = s == s'
  (==) _ _ = False

-- 4.
data Pair a = Pair a a
instance Eq a => Eq (Pair a) where
  (==) (Pair x y) (Pair x' y') = x == x' && y == y'

-- 5.
data Tuple a b = Tuple a b
instance (Eq a, Eq b) => Eq (Tuple a b) where
  (==) (Tuple x y) (Tuple x' y') = x == x' && y == y'

-- 6.
data Where a =
    ThisOne a
  | ThatOne a
instance Eq a => Eq (Where a) where
  (==) (ThisOne x) (ThisOne x') = x == x'
  (==) (ThatOne x) (ThatOne x') = x == x'
  (==) (ThisOne x) (ThatOne x') = x == x'
  (==) (ThatOne x) (ThisOne x') = x == x'

-- 7.
data EitherOr a b =
    Hello a
  | Goodbye b
instance (Eq a, Eq b) => Eq (EitherOr a b) where
  (==) (Hello x) (Hello x') = x == x'
  (==) (Goodbye x) (Goodbye x') = x == x'

-- Exercises

-- 1. c
-- 2. b
-- 3. a
-- 4. c
-- 5. b

-- Does it typecheck?

-- 1.
data Person = Person Bool deriving Show

printPerson :: Person -> IO ()
printPerson person = putStrLn (show person)

-- 2.
data Mood = Blah
          | Woot deriving (Show, Eq)

settleDown x = if x == Woot
                 then Blah
                 else x

-- 3.
--   a) Blah, Woot
--   b) Error, due to settleDown :: Mood -> Mood
--   c) Error, due to no instance for Ord Mood

-- 4.
type Subject = String
type Verb = String
type Object = String

data Sentence =
  Sentence Subject Verb Object
  deriving (Eq, Show)

s1 = Sentence "dogs" "drool"
s2 = Sentence "Julie" "loves" "dogs"

-- Yes, due to s1 being a function of type Object -> Sentence.

-- Given a datatype declaration, what can we do?

-- 1. No, should be:
-- phew = Papu (Rocks "chases") (Yeah True)

-- 2. Yes.

-- 3. Yes.

-- 4. No, no instance for Ord Papu.

-- Match the types

-- 1. No, 1 requies a Num typeclass.
-- 2. No, Num not specific enough.
-- 3. No.  Can't :: Fractional on an explicit value, I guess.
-- 4. Yes.
-- 5. Yes.
-- 6. Yes.
-- 7. No, would need at least Num a => a -> a
-- 8. No, Num a would allow for non-Int args.
-- 9. Yes, Int is instance of Ord.
-- 10. Yes, Data.List.sort only requires Ord.
-- 11. No, would allow args of not [Char] go to mySort.

-- Type-Kwon-Do

-- 1.
chk :: Eq b => (a -> b) -> a -> b -> Bool
chk f x y = (f x) == y

-- 2.
arith :: Num b => (a -> b) -> Integer -> a -> b
arith f _ x = f x

