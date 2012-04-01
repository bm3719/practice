module Ch7 where

import Char

-- where: Provide local bindings within functions, like let/in, but the
-- semantic order is reversed.
roots a b c =
  ((-b + det) / twice_a, (-b - det)/twice_a)
  where det = sqrt (b*b - 4*a*c)
        twice_a = 2*a

-- η-reduction is partial application, part of point-free conversion.
lcaseLetters1 s = map toLower (filter isAlpha s)
-- Rewritten with functional composition.
lcaseLetters2 s = (map toLower . filter isAlpha) s
-- η-reduction.
lcaseLetters3 = map toLower . filter isAlpha

-- Another example.
fstGt01 l = filter (\ (a,b) -> a>0) l
-- η-reduction.
fstGt02 = filter (\ (a,b) -> a>0)
-- λ function rewritten with fst.
fstGt03 = filter (\x -> fst x > 0)
-- Functional composition between fst and >.
fstGt04 = filter (\x -> ((>0) . fst) x)
-- η-reduction.
fstGt05 = filter ((>0) . fst)

-- uncurry: Convert a function of type a -> b -> c to (a,b) -> c.
-- map (uncurry (*)) [(1,2),(3,4),(5,6)]

-- Self-task: Create a const function that returns an infinite list of pairs of
-- the form [(1,2),(3,4),(5,6)..], the uncurry (*) them.
odds = 1 : map (+2) odds
evens = 2 : map (+2) evens
lst = map (uncurry (*)) $ zip odds evens

-- curry: Convert a function of type (a,b) -> c to a -> b -> c.
-- curry (\ (x1,x2) -> x1 * x2) 2 3

-- flip: Reverse the order of function arguments.  E.g., `flip compare' can
-- sort a list in reverse order.
-- sortBy compare [5,1,8,3]
-- [1,3,5,8]
-- sortBy (flip compare) [5,1,8,3]
-- [8,5,3,1]

-- 7.1
-- func1 x l = map (\y -> y * x) l
func1 x = map (*x)
-- func2 f g l = filter f (map g l)
func2 f g = filter f . map g
-- func3 f l = l ++ map f l
func3 f l = ((l++) . map f) l
-- func4 l = map (\y -> y + 2) (filter (\z -> `elem` [1..10]) (5:l)
func4 l = (map (+2) . filter (`elem` [1..10])) (5:l)
-- func5 f l = foldr (\x y -> f (y,x)) 0 l
func5 f = foldr (curry f) 0

-- Pattern matching.

-- Color and colorToRGB from Ch4.hs.
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

isCustomColor (Custom _ _ _) = True
isCustomColor _              = False

isBright = isBright' . colorToRGB
  where isBright' (255, _, _) = True
        isBright' (_, 255, _) = True
        isBright' (_, _, 255) = True
        isBright' _           = False

--Convert three Ints to Colors.
rgbToColor 255 0 0     = Just Red
rgbToColor 255 128 0   = Just Orange
rgbToColor 255 255 0   = Just Yellow
rgbToColor 0 255 0     = Just Green
rgbToColor 0 0 255     = Just Blue
rgbToColor 255 0 255   = Just Purple
rgbToColor 255 255 255 = Just White
rgbToColor 0 0 0       = Just Black
rgbToColor r g b       =
  if 0 >= r && r <= 255 &&
     0 >= g && g <= 255 &&
     0 >= b && b <= 255
  then Just (Custom r g b)
  else Nothing -- Invalid RGB value.

-- A function to check if RGB values are valid Colors.  This is one way to get
-- rid of the Maybe typeclass.
rgbIsValid r g b = rgbIsValid' (rgbToColor r g b)
  where rgbIsValid' (Just _) = True
        rgbIsValid' _        = False

-- Pattern matching doesn't work against functions.  This doesn't work:
-- f x = x + 1
-- g (f x) = x

-- Guards: An extension of pattern matching, allowing piecewise function
-- definitions according to arbitrary boolean expressions.  `|' should be read
-- as `such that'.
comparison x y | x < y     = "The first is less."
               | y < x     = "The second is less."
               | otherwise = "They are equal."

-- `where' clauses are common to all guards, which is handy.  This function is
-- equivalent to the much uglier previous version.
isBright2 c | r == 255  = True
            | g == 255  = True
            | b == 255  = True
            | otherwise = False
  where (r,g,b) = colorToRGB c

-- Instance declarations: To declare a type to be a class, instance
-- declarations are required.  Classes provide a "minimal complete definition",
-- which lists functions that must be implemented for the class definition to
-- be satisfied.

-- Eq class: This has two member functions.  Eq requires that at least one of
-- these is implemented (the other is auto-implemented by negating the defined
-- one).
-- (==) :: (Eq a) => a -> a -> Bool
-- (/=) :: (Eq a) => a -> a -> Bool

--Defining Color to be an instance of Eq.
instance Eq Color where
  Red == Red       = True
  Orange == Orange = True
  Yellow == Yellow = True
  Green == Green   = True
  Blue == Blue     = True
  Purple == Purple = True
  White == White   = True
  Black == Black   = True
  (Custom r g b) == (Custom r' g' b') =
    r == r' && g == g' && b == b'
  _ == _ = False

-- Show class: Displays arbitrary values as strings.  Minimal completeness
-- requires either show or showsPrec.
-- show :: Show a => a -> String
-- showsPrec :: Show a => Int -> a -> String -> String
-- showList :: Show a => [a] -> String -> String

-- Defining Color to be an instance of Show.
instance Show Color where
  show Red    = "Red"
  show Orange = "Orange"
  show Yellow = "Yellow"
  show Green  = "Green"
  show Blue   = "Blue"
  show Purple = "Purple"
  show White  = "White"
  show Black  = "Black"
  show (Custom r g b) =
    "Custom " ++ show r ++ " " ++ show g ++ " " ++ show b

-- Ord class: Ordering.  Any of these satisfy minimal completeness, but
-- implementing `compare' is best.
-- compare :: Ord a => a -> a -> Ordering
-- (<=) :: Ord a => a -> a -> Bool
-- (>) :: Ord a => a -> a -> Bool
-- (>=) :: Ord a => a -> a -> Bool
-- (<) :: Ord a => a -> a -> Bool
-- min :: Ord a => a -> a -> a
-- max :: Ord a => a -> a -> a

-- Enum class: Enumerated types, where each element has a successor and
-- predecessor.  This requires at least `toFrom' and `fromElem'.  `fromElem x'
-- lists all elements after x.  `enumFromThe x step' lists all elements
-- starting as x in steps of size step.  The `To' functions end the enumeration
-- at the given element.
-- pred :: Enum a => a -> a
-- succ :: Enum a => a -> a
-- fromEnum :: Enum a => a -> a
-- enumFrom :: Enum a => a -> a -> [a]
-- enumFromThen :: Enum a => a -> a -> [a]
-- enumFromTo :: Enum a => a -> a -> [a]
-- enumFromThenTo :: Enum a => a -> a -> a -> [a]

-- Num class: Provides standard arithmetic operations..  `negate x' means -x,
-- the unary minus.
-- (-) :: Num a => a -> a -> a
-- (*) :: Num a => a -> a -> a
-- (+) :: Num a => a -> a -> a
-- negate :: Num a => a -> a
-- signum :: Num a => a -> a
-- abs :: Num a => a -> a
-- fromInteger :: Num a => Integer -> a

-- Read class: Opposite of the show class, taking a string and reading in from
-- it a value of arbitrary type.  Requires at least `readsPrec'.
-- readPrec :: Read a => Int -> String -> [(a, String)]
-- readList : String -> [(a, String)]

-- The `read' function uses readsPrec, like this:
-- read s = fst (head (readsPrec 0 s))

-- A Maybe-using version of `read' could look like this:
maybeRead s =
  case readsPrec 0 s of
    [(a, _)] -> Just a
    _        -> Nothing

-- Class contexts.

-- If Maybe was being defined from scratch, we would write the Eq instance
-- declaration like this:
-- instance Eq a => Eq (Maybe a) where
--   Nothing == Nothing = True
--   (Just x) == (Just x') = x == x'

-- Deriving classes: Automates common instance declaration for Eq, Ord, Read,
-- Show, Bounded, Enum.  Unless creating datatypes using fixed point types or
-- needing something particular, this saves a lot of typing.
-- data Color = Red | .. | Custom Int Int Int
--     deriving (Eq, Ord, Show, Read)

-- It's possible to create derivations of instance declarations for arbitrary
-- types.  This method is called "polytypic programming" or "generic
-- programming".  Will have to investigate this method elsewhere.

-- Named fields: Instead of the OO-like practice of creating a datatype (really
-- an ADT, since OO classes include methods internally usually), then writing
-- functions that are equivalent to accessors/mutators, we can just use named
-- fields, which automatically generates these functions.
data Configuration =
  Configuration { username      :: String,
                  localhost     :: String,
                  remotehost    :: String,
                  isguest       :: Bool,
                  issuperuser   :: Bool,
                  currentdir    :: String,
                  homedir       :: String,
                  timeconnected :: Integer }

-- This will auto-generate accessor functions like:
-- username :: Configuration -> String
-- localhost :: Configuration -> String
-- ...

-- For mutators, this gives a convenient syntax.  To update more than one field
-- at the same time, use syntax like `d{x=y, z=q}'.
changeDir :: Configuration -> String -> Configuration
changeDir cfg newDir =
  if directoryExists newDir
     then cfg{currentdir = newDir} -- Change directory.
  else error "Director does not exist."
  where directoryExists d = True -- Just so this compiles.

postWorkingDir :: Configuration -> String
postWorkingDir cfg = currentdir cfg

-- Pattern matching against a named field datatype is still valid, though
-- unnecessary, as naming syntax is just syntactic sugar.
getUserName (Configuration un _ _ _ _ _ _ __) = un

-- Named fields themselves can also be pattern matched against.
getHostData (Configuration {localhost=lh, remotehost=rh}) = (lh,rh)

--For values in named field datatypes, these constructor calls are equivalent:
initCFG = Configuration "nobody" "nowhere" "nowhere"
          False False "/" "/" 0
initCFG' = Configuration { username = "nobody",
                           localhost = "nowhere",
                           remotehost = "nowhere",
                           isguest = False,
                           issuperuser = False,
                           currentdir = "/",
                           homedir = "/",
                           timeconnected = 0 }

-- A possible definition for `zip'.
-- zip _ []          = []
-- zip [] _          = []
-- zip (x:xs) (y:ys) = (x,y) : zip xs ys

-- List comprehensions: Syntactic sugar like [1..10] and [1,4..10].  These are
-- shorthand for enumFrom and enumFromTo.

-- This is useful for reducing list traversals.  Here, the list is traverse
-- twice.
assignID1 l = zip l [1..(length l)]
-- This only traverses once.
assignID2 l = zip l [1..]

-- map/filter syntactic sugar: In set notation, we would write something like
-- {f(x) | x . s . p(x)}.  This describes the set of all values of f applied to
-- elements of s which satisfy predicate p.  This is equivalent to `map f
-- (filter p s)'.  Instead of this, we can write the more math-like notation of
-- `[f x | x <- s, p x]'.  These expressions are equivalent:
-- map toLower (filter isUpper "Sdfss FBCXs")
-- [toLower x | x <- "Sdfss FBCXs", isUpper x]

-- Example: Generate all points below the diagonal between (0,0) and (5,7).
-- [(x,y) | x <- [0..5], y <- [x..7]]

-- The number of classes isn't limited.
-- [(x,y,z) | x <- [0..5], y <- [x..7], z <- [y..4]]

-- Array module: Great for random acces, unlike lists which are O(n), but have
-- slow insertions and deletions.

-- array: Takes a pair which bounds the array and an association list which
-- specifies initial values.
-- array (1,5) [(i,2*i) | i <- [1..5]]

-- listArray: Takes a bounding pair and a list of values.
-- listArray (1,5) [1..5]

-- accumArray: Takes an accumulation function, an initial value, bounds, and an
-- association list.  Accumulates values from the list into the array.
-- accumArray (+) 2 (1,5) [(i,i) | i <- [1..5]]

-- !: Extracts values from an array at the specified index.
-- listArray (1,5) [1..5] ! 3

-- //: Updates elements in an array.
-- listArray (1,5) [1..5] // [(3,9), (4,0)]

-- Other useful functions: bounds, indices, elems, assocs.

-- Finite Maps: An alternative to lists and arrays that provides fast
-- implementation of all access and modification functions.

-- Primitive recursive functions: A function that can be computed only by
-- for-loops, but not while-loops.  `foldr' can compute these.

-- `map' implemented by `foldr'.
frmap1 :: (a -> b) -> [a] -> [b]
frmap1 f = foldr (\a b -> f a : b) []
-- This can be further simplified:
frmap2 = foldr (\a b -> (:) (f a) b) []
frmap3 = foldr (\a -> (:) (f a)) []
frmap4 = foldr (\a -> ((:) . f) a) []
frmap5 = foldr ((:) . f) []

-- `foldr (:) []' is the identity function on lists.

-- Any function of this form can be converted to a foldr:
-- myFunc1 []     = z
-- myFunc1 (x:xs) = f x (myFunc1 xs)
-- Of of this infix form of the same:
-- myFunc2 []     = z
-- myFunc2 (x:xs) = x `f` (myFunc2 xs)

-- Another example, using `filter'.  `filter' is normally defined as something
-- like:
filter2 f []     = []
filter2 f (x:xs) = if f x then x : filter f xs
                   else filter f xs

-- Now we can counvert this to a foldr form.
filter3 f = foldr (\a b -> if f a then a:b else b) []

-- 7.2
-- and1 []     = True
-- and1 (x:xs) = x && and1 xs
-- and2 = foldr (a b -> a && b) True
mAnd = foldr (&&) True

-- 7.3
-- These are equivalent:
-- (concat . map (++"s")) ["aa","bb"]
-- concatMap (++"s") ["aa","bb"]
-- Functional composition implementation:
-- mConcatMap f = (concat . map f)
mConcatMap f = foldr ((++) . f) []

