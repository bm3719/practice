-- Chapter 2

sayHello :: String -> IO ()
sayHello s = putStrLn ("Hello, " ++ s ++ "!")

-- Intermission

-- 1.

-- let half x = x / 2
-- let square x = x * x

-- 2.

circle :: Fractional a => a -> a
circle r = 3.14 * (r * r)

-- Intermission

-- 1. Yes.
-- 2. No.
-- 3. Yes.

-- Intermission

-- 1. let area x = 3.14 * (x * x)

-- 2. let double x = x * 2

-- 3.
-- x = 7
-- y = 10
-- f = x + y

printInc n = (\x -> print x) (n + 2)

-- Intermission

-- 1. 5
-- 2. 25
-- 3. 30
-- 4. 6

-- Intermission

-- 1.
-- ans1 = x * 3 + y
--   where x = 3
--         y = 1000

-- 2.
-- ans2 = x * 5
--   where y = 10
--         x = 10 * 5 + y

-- 3.
-- ans3 = z / x + y
--   where x = 7
--         y = negate x
--         z = y * 10

-- Exercises

-- 1. 2 + (2 * 3) - 1
-- 2. (^10) $ (1 + 1)
-- 3. (2 ^ 2) * (4 ^ 5) + 1

-- 1. Yes.
-- 2. Yes.
-- 3. No.
-- 4. No.
-- 5. No.

z = 7
y = z + 8
x = y ^ 2
waxOn = x * 5

-- 1.
-- 1135
-- 1135
-- (-110)
-- 110

-- 2. Ok.

-- 3. 1125 * 3 = 3375

-- 4.
waxOn' = x * 5
  where z = 7
        y = z + 8
        x = y ^ 2

-- 5.
triple x = x * 3

-- 6.
waxOff x = triple x

-- 7. Ok.
