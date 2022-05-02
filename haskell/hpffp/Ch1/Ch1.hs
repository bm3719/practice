-- Chapter 1

-- Intermission

-- 1. λxy.xz
-- b. λmn.mz
-- 2. λxy.xxy
-- c. λa(λb).aab
-- 3. λxyz.zx
-- b. λtos.st

-- Exercises

-- Combinators:
-- 1. λx.xxx
-- true
-- 2. λxy.zx
-- false
-- 3. λxyz.xy(zx)
-- true
-- 4. λxyz.xy(zxy)
-- true
-- 5. λxy.xy(zxy)
-- false

-- Normal form or diverge?
-- 1. λx.xxx
-- NF
-- 2. (λz.zz)(λy.yy)
-- diverge
-- 3. (λx.xxx)z
-- NF

-- Beta reduce:

-- 1. (λabc.cba)zz(λwv.w)
-- (λb.λc.cbz)z(λw.λv.w)
-- (λc.czz)(λw.λv.w)
-- (λw.λv.w)zz
-- (λv.z)z
-- z

-- 2. (λx.λy.xyy)(λa.a)b
-- (λy.(λa.a)yy)b
-- (λa.a)bb
-- bb

-- 3. (λy.y)(λx.xx)(λz.zq)
-- (λx.xx)(λz.zq)
-- (λz.zq)(λz.zq)
-- (λz.zq)q
-- qq

-- 4. (λz.z)(λz.zz)(λz.zy)
-- (λz.zz)(λz.zy)
-- (λz.zy)(λz.zy)
-- yy

-- 5. (λx.λy.xyy)(λy.y)y
-- (λy.(λy.y)yy)y
-- (λy.y)yy
-- yy

-- 6. (λa.aa)(λb.ba)c
-- (λb.ba)(λb.ba)c
-- ((λb.ba)a)c
-- aac

-- 7. (λxyz.xz(yz))(λx.z)(λx.a)
-- (λx.λy.λz.xz(yz))(λx.z)(λx.a)
-- λy.λz.(λx.z)z(yz)(λx.a)
-- λz.(λx.z)z((λx.a)z)
-- λz.za
-- a
