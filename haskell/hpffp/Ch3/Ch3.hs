-- Chapter 3: Strings

sss = "ssss"

-- Intermission

-- 1. Yes.
-- 2. No.
-- 3. No.
-- 4. Yes.

-- Intermission

-- 1. No.  (++) [1,2,3] [4,5,6]
-- 2. No.  "<3" ++ " Haskell"
-- 3. Yes.

-- Exercises

-- 1.
-- a) Yes.
-- b) No.  (++) [1, 2, 3] [4, 5, 6]
-- c) Yes.
-- d) No.  "hello" ++ " world"
-- e) No.  "hello" !! 4
-- f) Yes.
-- g) No.  take 4 "lovely"
-- h) Yes.

-- 2.
-- a) d
-- b) c
-- c) e
-- d) a
-- e) b

-- 1.
-- a)
-- "Curry is awesome" ++ "!"
-- b)
-- head $ drop 4 $ "Curry is awesome!"
-- c)
-- tail $ drop 8 $ "Curry is awesome!"

-- 2.
-- a)
a s = s ++ "!"
-- b)
b s = head $ drop 4 $ s
-- c)
c s = tail $ drop 8 $ s

-- 3
thirdLetter :: String -> Char
thirdLetter s = s !! 2

-- 4
letterIndex :: Int -> Char
letterIndex x = "Curry is awesome!" !! x

-- 5
rvrs :: String -> String
rvrs []     = []
rvrs (c:cs) = (rvrs cs) ++ [c]

-- 6 Skipping.
