/** 
 * Practice problems from: http://javabat.com/java/Recursion-1
 * @version 1.0
 * @author Bruce C. Miller
 */
class recursion_1
{
    public static void main(String[] args)
    {
        System.out.println("Testing factorial...");
        test(factorial(1) == 1);
        test(factorial(2) == 2);
        test(factorial(3) == 6);
        System.out.println("Testing bunnyEars...");
        test(bunnyEars(0) == 0);
        test(bunnyEars(1) == 2);
        test(bunnyEars(2) == 4);
        System.out.println("Testing fibonacci...");
        test(fibonacci(0) == 0);
        test(fibonacci(1) == 1);
        test(fibonacci(2) == 1);
        System.out.println("Testing bunnyEars2...");
        test(bunnyEars2(0) == 0);
        test(bunnyEars2(1) == 2);
        test(bunnyEars2(2) == 5);
        System.out.println("Testing triangle...");
        test(triangle(0) == 0);
        test(triangle(1) == 1);
        test(triangle(2) == 3);
        System.out.println("Testing sumDigits...");
        test(sumDigits(126) == 9);
        test(sumDigits(49) == 13);
        test(sumDigits(12) == 3);
        System.out.println("Testing count7...");
        test(count7(717) == 2);
        test(count7(7) == 1);
        test(count7(123) == 0);
        System.out.println("Testing count8...");
        test(count8(8) == 1);
        test(count8(818) == 2);
        test(count8(8818) == 4);
        System.out.println("Testing powerN...");
        test(powerN(3, 1) == 3);
        test(powerN(3, 2) == 9);
        test(powerN(3, 3) == 27);
        System.out.println("Testing countX...");
        test(countX("xxhixx") == 4);
        test(countX("xhixhix") == 3);
        test(countX("hi") == 0);
        System.out.println("Testing countHi...");
        test(countHi("xxhixx") == 1);
        test(countHi("xhixhix") == 2);
        test(countHi("hi") == 1);
        System.out.println("Testing changeXY...");
        test(changeXY("codex").equals("codey"));
        test(changeXY("xxhixx").equals("yyhiyy"));
        test(changeXY("xhixhix").equals("yhiyhiy"));
        System.out.println("Testing changePi...");
        test(changePi("xpix").equals("x3.14x"));
        test(changePi("pipi").equals("3.143.14"));
        test(changePi("pip").equals("3.14p"));
        System.out.println("Testing noX...");
        test(noX("xaxb").equals("ab"));
        test(noX("abc").equals("abc"));
        test(noX("xx").equals(""));
        System.out.println("Testing array6...");
        test(array6(new int[] {1, 6, 4}, 0) == true);
        test(array6(new int[] {1, 4}, 0) == false);
        test(array6(new int[] {6}, 0) == true);
        System.out.println("Testing array11...");
        test(array11(new int[] {1, 2, 11}, 0) == 1);
        test(array11(new int[] {11, 11}, 0) == 2);
        test(array11(new int[] {1, 2, 3, 4}, 0) == 0);
        System.out.println("Testing array220...");
        test(array220(new int[] {1, 2, 20}, 0) == true);
        test(array220(new int[] {3, 30}, 0) == true);
        test(array220(new int[] {3}, 0) == false);
        System.out.println("Testing allStar...");
        test(allStar("hello").equals("h*e*l*l*o"));
        test(allStar("abc").equals("a*b*c"));
        test(allStar("ab").equals("a*b"));
        System.out.println("Testing pairStar...");
        test(pairStar("hello").equals("hel*lo"));
        test(pairStar("xxyy").equals("x*xy*y"));
        test(pairStar("aaaa").equals("a*a*a*a"));
        System.out.println("Testing endX...");
        test(endX("xxre").equals("rexx"));
        test(endX("xxhixx").equals("hixxxx"));
        test(endX("xhixhix").equals("hihixxx"));
        System.out.println("Testing countPairs...");
        test(countPairs("axa") == 1);
        test(countPairs("axax") == 2);
        test(countPairs("axbx") == 1);
        System.out.println("Testing countAbc...");
        test(countAbc("abc") == 1);
        test(countAbc("abcxxabc") == 2);
        test(countAbc("abaxxaba") == 2);
        System.out.println("Testing count11...");
        test(count11("11abc11") == 2);
        test(count11("abc11x11x11") == 3);
        test(count11("111") == 1);
        System.out.println("Testing stringClean...");
        test(stringClean("yyzzza").equals("yza"));
        test(stringClean("abbbcdd").equals("abcd"));
        test(stringClean("Hello").equals("Helo"));
        System.out.println("Testing countHi2...");
        test(countHi2("ahixhi") == 1);
        test(countHi2("ahibhi") == 2);
        test(countHi2("xhixhi") == 0);
        System.out.println("Testing parenBit...");
        test(parenBit("xyz(abc)123").equals("(abc)"));
        test(parenBit("x(hello)").equals("(hello)"));
        test(parenBit("(xy)1").equals("(xy)"));
        System.out.println("Testing nestParen...");
        test(nestParen("(())") == true);
        test(nestParen("((()))") == true);
        test(nestParen("(((x)))") == false);
        System.out.println("Testing strCount...");
        test(strCount("catcowcat", "cat") == 2);
        test(strCount("catcowcat", "cow") == 1);
        test(strCount("catcowcat", "dog") == 0);
        System.out.println("Testing strCopies...");
        test(strCopies("catcowcat", "cat", 2) == true);
        test(strCopies("catcowcat", "cow", 2) == false);
        test(strCopies("catcowcat", "cow", 1) == true);
        System.out.println("Testing strDist...");
        test(strDist("catcowcat", "cat") == 9);
        test(strDist("catcowcat", "cow") == 3);
        test(strDist("cccatcowcatxx", "cat") == 9);
    }

    // To save some typing.
    public static void test(boolean result)
    {
        System.out.println("Test: " + (result ? "Passed" : "Failed"));
    }

    // Given n of 1 or more, return the factorial of n, which is n * (n-1) *
    // (n-2) ... 1. Compute the result recursively (without loops).
    public static int factorial(int n)
    {
        // Note: Non-optimal solution.
        if (n == 1)
        {
            return n;
        }
        else
        {
            return n * factorial(n - 1);
        }
    }

    // We have a number of bunnies and each bunny has two big floppy ears. We
    // want to compute the total number of ears across all the bunnies
    // recursively (without loops or multiplication).
    public static int bunnyEars(int bunnies)
    {
        if (bunnies == 0)
        {
            return 0;
        }
        else if (bunnies == 1)
        {
            return 2;
        }
        else
        {
            return bunnyEars(1) + bunnyEars(bunnies - 1);
        }
    }

    // The fibonacci sequence is a famous bit of mathematics, and it happens to
    // have a recursive definition. The first two values in the sequence are 0
    // and 1 (essentially 2 base cases). Each subsequent value is the sum of
    // the previous two values, so the whole sequence is: 0, 1, 1, 2, 3, 5, 8,
    // 13, 21 and so on. Define a recursive fibonacci(n) method that returns
    // the nth fibonacci number, with n=0 representing the start of the
    // sequence.
    public static int fibonacci(int n)
    {
        if (n < 2)
        {
            return n;
        }
        else
        {
            return fibonacci(n - 1) + fibonacci(n - 2);
        }
    }

    // We have bunnies standing in a line, numbered 1, 2, ... The odd bunnies
    // (1, 3, ..) have the normal 2 ears. The even bunnies (2, 4, ..) we'll say
    // have 3 ears, because they each have a raised foot. Recursively return
    // the number of "ears" in the bunny line 1, 2, ... n (without loops or
    // multiplication).
    public static int bunnyEars2(int bunnies)
    {
        if (bunnies == 0)
        {
            return 0;
        }
        else if (bunnies == 1)
        {
            return 2;
        }
        else
        {
            return ((bunnies % 2 == 0) ? 3 : 2) + bunnyEars2(bunnies - 1);
        }
    }

    // We have triangle made of blocks. The topmost row has 1 block, the next
    // row down has 2 blocks, the next row has 3 blocks, and so on. Compute
    // recursively (no loops or multiplication) the total number of blocks in
    // such a triangle with the given number of rows.
    public static int triangle(int rows)
    {
        if (rows < 2)
        {
            return rows;
        }
        else
        {
            return rows + triangle(rows - 1);
        }
    }

    // Given a non-negative int n, return the sum of its digits recursively (no
    // loops). Note that mod (%) by 10 yields the rightmost digit (126 % 10 is
    // 6), while divide (/) by 10 removes the rightmost digit (126 / 10 is 12).
    public static int sumDigits(int n)
    {
        if (n / 10 == 0)
        {
            return n;
        }
        else
        {
            return (n % 10) + sumDigits(n / 10);
        }
    }

    // Given a non-negative int n, return the count of the occurrences of 7 as
    // a digit, so for example 717 yields 2. (no loops). Note that mod (%) by
    // 10 yields the rightmost digit (126 % 10 is 6), while divide (/) by 10
    // removes the rightmost digit (126 / 10 is 12).
    public static int count7(int n)
    {
        if (n / 10 == 0)
        {
            return ((n == 7) ? 1 : 0);
        }
        else
        {
            return (((n % 10) == 7) ? 1 : 0) + count7(n / 10);
        }
    }

    // Given a non-negative int n, compute recursively (no loops) the count of
    // the occurrences of 8 as a digit, except that an 8 with another 8
    // immediately to its left counts double, so 8818 yields 4. Note that mod
    // (%) by 10 yields the rightmost digit (126 % 10 is 6), while divide (/)
    // by 10 removes the rightmost digit (126 / 10 is 12).
    public static int count8(int n)
    {
        if (n / 10 == 0)
        {
            return ((n == 8) ? 1 : 0);
        }
        else if (n % 100 == 88)
        {
            return 3 + count8(n / 100);
        }
        else
        {
            return (((n % 10) == 8) ? 1 : 0) + count8(n / 10);
        }
    }

    // Given base and n that are both 1 or more, compute recursively (no loops)
    // the value of base to the n power, so powerN(3, 2) is 9 (3 squared).
    public static int powerN(int base, int n)
    {
        if (n == 1)
        {
            return base;
        }
        else
        {
            return base * powerN(base, n - 1);
        }
    }

    // Given a string, compute recursively (no loops) the number of lowercase
    // 'x' chars in the string.
    public static int countX(String str)
    {
        if (str.length() == 1)
        {
            return ((str.charAt(0) == 'x') ? 1 : 0);
        }
        else
        {
            return ((str.charAt(0) == 'x') ? 1 : 0) + countX(str.substring(1, str.length()));
        }
    }

    // Given a string, compute recursively (no loops) the number of times
    // lowercase "hi" appears in the string.
    public static int countHi(String str)
    {
        if (str.length() < 2)
        {
            return 0;
        }
        else
        {
            return ((str.substring(0, 2).equals("hi")) ? 1 : 0)
                + countHi(str.substring(1, str.length()));
        }
    }

    // Given a string, compute recursively (no loops) a new string where all
    // the lowercase 'x' chars have been changed to 'y' chars.
    public static String changeXY(String str)
    {
        if (str.length() < 1)
        {
            return "";
        }
        else
        {
            return ((str.charAt(0) == 'x') ? "y" : Character.toString(str.charAt(0)))
                + changeXY(str.substring(1, str.length()));
        }
    }

    // Given a string, compute recursively (no loops) a new string where all
    // appearances of "pi" have been replaced by "3.14".
    public static String changePi(String str)
    {
        if (str.length() < 1)
        {
            return "";
        }
        else
        {
            if ((str.length() >= 2) && (str.substring(0, 2).equals("pi")))
            {
                return "3.14" + changePi(str.substring(2, str.length()));
            }
            else
            {
                return Character.toString(str.charAt(0))
                    + changePi(str.substring(1, str.length()));
            }
        }
    }

    // Given a string, compute recursively a new string where all the 'x' chars
    // have been removed.
    public static String noX(String str)
    {
        if (str.length() < 1)
        {
            return "";
        }
        else
        {
            return ((str.charAt(0) == 'x') ? "" : Character.toString(str.charAt(0)))
                + noX(str.substring(1, str.length()));
        }
    }

    // Given an array of ints, compute recursively if the array contains a
    // 6. We'll use the convention of considering only the part of the array
    // that begins at the given index. In this way, a recursive call can pass
    // index+1 to move down the array. The initial call will pass in index as
    // 0.
    public static boolean array6(int[] nums, int index)
    {
        if (index >= nums.length)
        {
            return false; 
        }
        else
        {
            return nums[index] == 6 || array6(nums, index + 1);
        }
    }

    // Given an array of ints, compute recursively the number of times that the
    // value 11 appears in the array. We'll use the convention of considering
    // only the part of the array that begins at the given index. In this way,
    // a recursive call can pass index+1 to move down the array. The initial
    // call will pass in index as 0.
    public static int array11(int[] nums, int index)
    {
        if (index >= nums.length)
        {
            return 0;
        }
        else
        {
            return ((nums[index] == 11) ? 1 : 0) + array11(nums, index + 1);
        }
    }

    // Given an array of ints, compute recursively if the array contains
    // somewhere a value followed in the array by that value times 10. We'll
    // use the convention of considering only the part of the array that begins
    // at the given index. In this way, a recursive call can pass index+1 to
    // move down the array. The initial call will pass in index as 0.
    public static boolean array220(int[] nums, int index)
    {
        if (index >= nums.length - 1)
        {
            return false;
        }
        else
        {
            return (nums[index] * 10 == nums[index + 1])
                || array220(nums, index + 1);
        }
    }

    // Given a string, compute recursively a new string where all the adjacent
    // chars are now separated by a "*".
    public static String allStar(String str)
    {
        if (str.length() < 1)
        {
            return "";
        }
        else
        {
            return Character.toString(str.charAt(0))
                + ((str.length() > 1) ? "*" : "")
                + allStar(str.substring(1, str.length()));
        }
    }

    // Given a string, compute recursively a new string where identical chars
    // that are adjacent in the original string are separated from each other
    // by a "*".
    public static String pairStar(String str)
    {
        if (str.length() < 1)
        {
            return "";
        }
        else
        {
            return Character.toString(str.charAt(0))
                + (((str.length() > 1) && (str.charAt(1) == str.charAt(0))) ? "*" : "")
                + pairStar(str.substring(1, str.length()));
        }
    }

    // Given a string, compute recursively a new string where all the lowercase
    // 'x' chars have been moved to the end of the string.
    public static String endX(String str)
    {
        if (str.length() < 1)
        {
            return "";
        }
        else
        {
            if (str.charAt(0) == 'x')
            {
                return endX(str.substring(1, str.length())) + "x";
            }
            else
            {
                return Character.toString(str.charAt(0))
                    + endX(str.substring(1, str.length()));
            }
        }
    }

    // We'll say that a "pair" in a string is two instances of a char separated
    // by a char. So "AxA" the A's make a pair. Pair's can overlap, so "AxAxA"
    // contains 3 pairs -- 2 for A and 1 for x. Recursively compute the number
    // of pairs in the given string.
    public static int countPairs(String str)
    {
        if (str.length() < 2)
        {
            return 0;
        }
        else
        {
            return ((str.substring(1, str.length()).indexOf(str.charAt(0)) != -1) ? 1 : 0)
                + countPairs(str.substring(1, str.length()));
        }
    }

    // Count recursively the total number of "abc" and "aba" substrings that
    // appear in the given string.
    public static int countAbc(String str)
    {
        if (str.length() < 3)
        {
            return 0;
        }
        else
        {
            return ((str.substring(0, 3).equals("abc") || str.substring(0, 3).equals("aba")) ? 1 : 0)
                + countAbc(str.substring(1, str.length()));
        }
    }

    // Given a string, compute recursively (no loops) the number of "11"
    // substrings in the string. The "11" substrings should not overlap.
    public static int count11(String str)
    {
        if (str.length() < 2)
        {
            return 0;
        }
        else
        {
            if (str.substring(0, 2).equals("11"))
            {
                return 1 + count11(str.substring(2, str.length()));
            }
            else
            {
                return count11(str.substring(1, str.length()));
            }
        }
    }

    // Given a string, return recursively a "cleaned" string where adjacent
    // chars that are the same have been reduced to a single char. So "yyzzza"
    // yields "yza".
    public static String stringClean(String str)
    {
        if (str.length() < 1)
        {
            return "";
        }
        else if (str.length() == 1)
        {
            return Character.toString(str.charAt(0));
        }
        else
        {
            return ((str.charAt(0) == str.charAt(1)) ? "" : Character.toString(str.charAt(0)))
                + stringClean(str.substring(1, str.length()));
        }
    }

    // Given a string, compute recursively the number of times lowercase "hi"
    // appears in the string, however do not count "hi" that have an 'x'
    // immedately before them.
    public static int countHi2(String str)
    {
        if (str.length() < 2)
        {
            return 0;
        }
        else
        {
            if (str.length() >= 3 && str.substring(1, 3).equals("hi") && str.charAt(0) == 'x')
            {
                return countHi2(str.substring(3, str.length()));
            }
            else
            {
                return ((str.substring(0, 2).equals("hi")) ? 1 : 0)
                    + countHi2(str.substring(1, str.length()));
            }
        }
    }

    // Given a string that contains a single pair of parenthesis, compute
    // recursively a new string made of only of the parenthesis and their
    // contents, so "xyz(abc)123" yields "(abc)".
    public static String parenBit(String str)
    {
        // Might as well do this the easy way, since we're already told the
        // input contains a single pair of parens.
        return str.substring(str.indexOf('('), str.indexOf(')') + 1);
    }

    // Given a string, return true if it is a nesting of zero or more pairs of
    // parenthesis, like "(())" or "((()))". Suggestion: check the first and
    // last chars, and then recur on what's inside them.
    public static boolean nestParen(String str)
    {
        if (str.length() < 1)
        {
            return true;
        }
        else
        {
            return str.length() >= 2
                && ((str.charAt(0) == '(' && str.charAt(str.length() - 1) == ')') ? true : false)
                && nestParen(str.substring(1, str.length() - 1));
        }
    }

    // Given a string and a non-empty substring sub, compute recursively the
    // number of times that sub appears in the string, without the sub strings
    // overlapping.
    public static int strCount(String str, String sub)
    {
        if (str.length() < sub.length())
        {
            return 0;
        }
        else
        {
            if (str.indexOf(sub) == 0)
            {
                return 1 + strCount(str.substring(sub.length() - 1, str.length()), sub);
            }
            else
            {
                return strCount(str.substring(1, str.length()), sub);
            }
        }
    }

    // Given a string and a non-empty substring sub, compute recursively if at
    // least n copies of sub appear in the string somewere, possibly with
    // overlapping. N will be non-negative.
    public static boolean strCopies(String str, String sub, int n)
    {
        if (n == 0)
        {
            return true;
        }
        else if (n > 0 && str.indexOf(sub) == -1)
        {
            return false;
        }
        else
        {
            return strCopies(str.substring(1, str.length()), sub, ((str.indexOf(sub) == 0) ? n - 1 : n));
        }
    }

    // Given a string and a non-empty substring sub, compute recursively the
    // largest substring which starts and ends with sub and return its length.
    public static int strDist(String str, String sub)
    {
        return (str.lastIndexOf(sub) + sub.length()) - str.indexOf(sub);
    }
}
