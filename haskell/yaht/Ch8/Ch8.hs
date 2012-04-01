module Ch8 where

-- Advanced types

-- Type synonyms: Defines shortcuts for arbitrary types.  Self-referential type
-- synonyms are not allowed.
type List3d = [(Double, Double, Double)]

-- Type parameters are allowed.  `GList3d Double' would be equivalent to the
-- above.
type GList3d a = [(a, a, a)]

-- Isomorphic types: Types that are structurally identical, but differ only in
-- naming themselves and/or components thereof.
-- data MyInt = MyInt Int -- Not an isomorphic type to Int.

-- _|_: The bottom type, used to represent erroneous or undefined computations.
-- In the above example, values like MyInt 1 map to Int 1, but MyInt _|_ does
-- not.

-- newtype: Solves the above problem as well as efficiency issues (extra
-- pointers).  The newtype construction is a cross between type synonyms and
-- datatypes.  It can have only one constructor, which can have only one
-- argument.
newtype MyInt = MyInt Int deriving (Eq, Show)

-- While we can't have multiple constructor arguments, the argument can be a
-- pair.
-- newtype MyInt = MyInt (Int, Double)

-- Ord instance for MyInt that orders evens before odds.  Note: I think there's
-- a problem here.  `(MyInt 1) < (MyInt 2)' barfs for me.
instance Ord MyInt where
  MyInt i < MyInt j | odd i && odd j   = i < j
                    | even i && even j = i < j
                    | even i           = True
                    | otherwise        = False
    where odd x = (x `mod` 2) == 0
          even  = not . odd

-- Datatypes: Strict fields Unit is the simplest datatype that can defined.
-- There are two values of type Unit, `Unit' and bottom (_|_).  Bottom can also
-- be thought of as a computation that won't halt, like `foo = foo'.
data Unit = Unit

-- Type `Maybe Unit' actually has 4 possible values: _|_, Nothing, Just _|_,
-- and Just Unit.  If we wanted to only ever use 3 of these, we could have the
-- argument to Just be strict.
-- data SMaybe a = Nothing | SJust !a

-- Classes

-- Define a type class with 5 methods and their corresponding types.
class Entity a where
  getPosition :: a -> (Int, Int)
  getVelocity :: a -> (Int, Int)
  getAcceleration :: a -> (Int, Int)
  getColor :: a -> Color
  getShape :: a -> Shape
-- Just to get this example to compile.
data Color = Color Int
data Shape = Shape Int

-- Define a datatype (with some modifications to keep it self-contained).
data Paddle = Paddle {
  paddlePosX, paddlePosY, paddleVelX, paddleVelY,
  paddleAccX, paddleAccY :: Int, paddleColor :: Color,
  paddleShape :: Shape, playerNumber :: Int }

-- Now we can define Paddle to be an instance of Entity.
instance Entity Paddle where
  getPosition p     = (paddlePosX p, paddlePosY p)
  getVelocity p     = (paddleVelX p, paddleVelY p)
  getAcceleration p = (paddleAccX p, paddleAccY p)
  getColor          = paddleColor
  getShape          = paddleShape

-- Subclasses: To make Entity a subclass of Eq, use this syntax:
-- class Eq a = Entity a where ...

-- Computations
-- A small graph library.  Constructor takes a list of vertices (ID,label) and
-- edges (VID1,VID2,label).  Assumptions: lists are sorted, each vertex has a
-- unique ID, and there is at most one edge per vertex pair.
data Graph v e = Graph [(Int,v)] [(Int,Int,e)]

-- Naïve path finding between 2 vertices, if one exists.
search :: Graph v e -> Int -> Int -> Maybe [Int]
search g@(Graph vl el) src dst
    | src == dst = Just [src]
    | otherwise  = search' el
      where search' []           = Nothing
            search' ((u,v,_):es)
                | src == u  = case search g v dst of
                              Just p  -> Just (u:p)
                              Nothing -> search' es
                | otherwise = search' es

