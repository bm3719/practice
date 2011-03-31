import java.lang.Math;

/** 
 * Practice problems from: http://javabat.com/java/Warmup-1
 * @version 1.0
 * @author Bruce C. Miller
 */
class warmup_1
{
    public static void main (String[] args)
    {
        System.out.println("Testing sleepIn...");
        test(sleepIn(false, false) == true);
        test(sleepIn(true, false) == false);
        test(sleepIn(false, true) == true);
        System.out.println("Testing monkeyTrouble...");
        test(monkeyTrouble(true, true) == true);
        test(monkeyTrouble(false, false) == true);
        test(monkeyTrouble(true, false) == false);
        System.out.println("Testing sumDouble...");
        test(sumDouble(1, 2) == 3);
        test(sumDouble(3, 2) == 5);
        test(sumDouble(2, 2) == 8);
        System.out.println("Testing diff21...");
        test(diff21(19) == 2);
        test(diff21(10) == 11);
        test(diff21(21) == 0);
        System.out.println("Testing parrotTrouble...");
        test(parrotTrouble(true, 6) == true);
        test(parrotTrouble(true, 7) == false);
        test(parrotTrouble(false, 6) == false);
        System.out.println("Testing makes10...");
        test(makes10(9, 10) == true);
        test(makes10(9, 9) == false);
        test(makes10(1, 9) == true);
        System.out.println("Testing nearHundred...");
        test(nearHundred(93) == true);
        test(nearHundred(90) == true);
        test(nearHundred(89) == false);
        System.out.println("Testing posNeg...");
        test(posNeg(1, -1, false) == true);
        test(posNeg(-1, 1, false) == true);
        test(posNeg(1, 1, false) == false);
        System.out.println("Testing notString...");
        test(notString("candy").equals("not candy"));
        test(notString("x").equals("not x"));
        test(notString("not bad").equals("not bad"));
        System.out.println("Testing missingChar...");
        test(missingChar("kitten", 1).equals("ktten"));
        test(missingChar("kitten", 0).equals("itten"));
        test(missingChar("kitten", 4).equals("kittn"));
        System.out.println("Testing frontBack...");
        test(frontBack("code").equals("eodc"));
        test(frontBack("a").equals("a"));
        test(frontBack("ab").equals("ba"));
        System.out.println("Testing front3...");
        test(front3("Java").equals("JavJavJav"));
        test(front3("Chocolate").equals("ChoChoCho"));
        test(front3("abc").equals("abcabcabc"));
        System.out.println("Testing backAround");
        test(backAround("cat").equals("tcatt"));
        test(backAround("Hello").equals("oHelloo"));
        test(backAround("a").equals("aaa"));
        System.out.println("Testing front22...");
        test(front22("kitten").equals("kikittenki"));
        test(front22("Ha").equals("HaHaHa"));
        test(front22("abc").equals("ababcab"));
        System.out.println("Testing startHi...");
        test(startHi("hi there") == true);
        test(startHi("hi") == true);
        test(startHi("hello hi") == false);
        System.out.println("Testing icyHot...");
        test(icyHot(120, -1) == true);
        test(icyHot(-1, 120) == true);
        test(icyHot(2, 120) == false);
        System.out.println("Testing in1020...");
        test(in1020(12, 99) == true);
        test(in1020(21, 12) == true);
        test(in1020(9, 99) == false);
        System.out.println("Testing hasTeen...");
        test(hasTeen(12, 20, 10) == true);
        test(hasTeen(20, 19, 10) == true);
        test(hasTeen(20, 10, 13) == true);
        System.out.println("Testing loneTeen...");
        test(loneTeen(13, 99) == true);
        test(loneTeen(21, 19) == true);
        test(loneTeen(13, 13) == false);
        System.out.println("Testing delDel...");
        test(delDel("adelbc").equals("abc"));
        test(delDel("adelHello").equals("aHello"));
        test(delDel("adebdc").equals("adedbc"));
        System.out.println("Testing mixStart...");
        test(mixStart("mix snacks") == true);
        test(mixStart("pix snacks") == true);
        test(mixStart("piz snacks") == false);
        System.out.println("Testing startOz...");
        test(startOz("ozymandias").equals("oz"));
        test(startOz("bzoo").equals("z"));
        test(startOz("oxx").equals("o"));
        System.out.println("Testing intMax...");
        test(intMax(1, 2, 3) == 3);
        test(intMax(1, 3, 2) == 3);
        test(intMax(3, 2, 1) == 3);
        System.out.println("Testing close10...");
        test(close10(8, 13) == 8);
        test(close10(13, 8) == 8);
        test(close10(13, 7) == 0);
        System.out.println("Testing in3050...");
        test(in3050(30, 31) == true);
        test(in3050(30, 41) == false);
        test(in3050(40, 50) == true);
        System.out.println("Testing max1020...");
        test(max1020(11, 19) == 19);
        test(max1020(19, 11) == 19);
        test(max1020(11, 9) == 11);
        System.out.println("Testing stringE...");
        test(stringE("Hello") == true);
        test(stringE("Heelle") == true);
        test(stringE("Heelele") == false);
        System.out.println("Testing lastDigit...");
        test(lastDigit(7, 17) == true);
        test(lastDigit(6, 17) == false);
        test(lastDigit(3, 113) == true);
        System.out.println("Testing endUp...");
        test(endUp("Hello").equals("HeLLO"));
        test(endUp("hi there").equals("hi thERE"));
        test(endUp("hi").equals("HI"));
        System.out.println("Testing everyNth...");
        test(everyNth("Miracle", 2).equals("Mrce"));
        test(everyNth("abcdefg", 2).equals("aceg"));
        test(everyNth("abcdefg", 3).equals("adg"));
    }

    // To save some typing.
    public static void test(boolean result)
    {
        System.out.println("Test: " + (result ? "Passed" : "Failed"));
    }

    // The parameter weekday is true if it is a weekday, and the parameter
    // vacation is true if we are on vacation. We sleep in if it is not a
    // weekday or we're on vacation. Return true if we sleep in.
    public static boolean sleepIn(boolean weekday, boolean vacation)
    {
        return (!weekday || vacation);
    }

    // We have two monkeys, a and b, and the parameters aSmile and bSmile
    // indicate if each is smiling. We are in trouble if they are both smiling
    // or if neither of them is smiling. Return true if we are in trouble.
    public static boolean monkeyTrouble(boolean aSmile, boolean bSmile)
    {
        return (aSmile && bSmile) || (!aSmile && !bSmile);
    }

    // Given two int values, return their sum. Unless the two values are the
    // same, then return double their sum.
    public static int sumDouble(int a, int b)
    {
        if (a == b)
        {
            return 2 * (a + b);
        }
        else
        {
            return a + b;
        }
    }

    // Given an int n, return the absolute difference between n and 21, except
    // return double the absolute difference if n is over 21.
    public static int diff21(int n)
    {
        if (n > 21)
        {
            return (n - 21) * 2;
        }
        else
        {
            return (21 - n);
        }
    }

    // We have a loud talking parrot. The "hour" parameter is the current hour
    // time in the range 0..23. We are in trouble if the parrot is talking and
    // the hour is before 7 or after 20. Return true if we are in trouble.
    public static boolean parrotTrouble(boolean talking, int hour)
    {
        return talking && (hour < 7 || hour > 20);
    }

    // Given 2 ints, a and b, return true if one if them is 10 or if their sum
    // is 10.
    public static boolean makes10(int a, int b)
    {
        return (a == 10 || b == 10 || (a + b) == 10);
    }

    // Given an int n, return true if it is within 10 of 100 or 200. Note:
    // Math.abs(num) computes the absolute value of a number.
    public static boolean nearHundred(int n)
    {
        return (Math.abs(n - 100) <= 10) || (Math.abs(n - 200) <= 10);
    }

    // Given 2 int values, return true if one is negative and one is
    // positive. Unless the parameter "negative" is true, then they both must
    // be negative.
    public static boolean posNeg(int a, int b, boolean negative)
    {
        if (negative)
        {
            return (a < 0) && (b < 0);
        }
        else
        {
            return ((a < 0) && (b > 0)) || ((a > 0) && (b < 0));
        }
    }

    // Given a string, return a new string where "not " has been added to the
    // front. However, if the string already begins with "not", return the
    // string unchanged. Note: use .equals() to compare 2 strings.
    public static String notString(String str)
    {
        if ((str.length() > 5) && str.substring(0, 4).equals("not "))
        {
            return str;
        }
        else
        {
            return "not " + str;
        }
    }

    // Given a non-empty string and an int n, return a new string where the
    // char at index n has been removed. The value of n will be a valid index
    // of a char in the original string (i.e. n will be in the range
    // 0..str.length()-1 inclusive).
    public static String missingChar(String str, int n)
    {
        String newString = "";
        for (int i = 0; i < str.length(); i++)
        {
            if (i != n)
            {
                newString += Character.toString(str.charAt(i));
            }
        }
        return newString;
    }

    // Given a string, return a new string where the first and last chars have
    // been exchanged.
    public static String frontBack(String str)
    {
        String newString = "";
        for (int i = 0; i < str.length(); i++)
        {
            if (i == 0)
            {
                newString += Character.toString(str.charAt(str.length() - 1));
            }
            else if (i == (str.length() - 1))
            {
                newString += Character.toString(str.charAt(0));
            }
            else
            {
                newString += Character.toString(str.charAt(i));
            }
        }
        return newString;
    }

    // Given a string, we'll say that the front is the first 3 chars of the
    // string. If the string length is less than 3, the front is whatever is
    // there. Return a new string which is 3 copies of the front.
    public static String front3(String str)
    {
        String newString = "";
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                newString += Character.toString(str.charAt(j));
            }
        }
        return newString;
    }

    // Given a string, take the last char and return a new string with the last
    // char added at the front and back, so "cat" yields "tcatt". The original
    // string will be length 1 or more.
    public static String backAround(String str)
    {
        String newString = "";
        // Add last char to front.
        newString += Character.toString(str.charAt(str.length() - 1));

        // Copy contents
        for (int i = 0; i < str.length(); i++)
        {
            newString += Character.toString(str.charAt(i));
        }
        
        // Add last char to back.
        newString += Character.toString(str.charAt(str.length() - 1));
        return newString;
    }

    // Given a string, take the first 2 chars and return the string with the 2
    // chars added at both the front and back, so "kitten"
    // yields"kikittenki". If the string length is less than 2, use whatever
    // chars are there.
    public static String front22(String str)
    {
        String newString = "";
        int frontLen = (str.length() < 2 ? str.length() : 2);
            
        // Add first two chars to front.
        for (int i = 0; i < frontLen; i++)
        {
            newString += Character.toString(str.charAt(i));
        }

        // Copy contents.
        for (int i = 0; i < str.length(); i++)
        {
            newString += Character.toString(str.charAt(i));
        }
        
        // Add first two chars to end.
        for (int i = 0; i < frontLen; i++)
        {
            newString += Character.toString(str.charAt(i));
        }
                
        return newString;
    }

    // Given a string, return true if the string starts with "hi" and false
    // otherwise.
    public static boolean startHi(String str)
    {
        return (str.indexOf("hi") == 0);
    }

    // Given two temperatures, return true if one is less than 0 and the other
    // is greater than 100.
    public static boolean icyHot(int temp1, int temp2)
    {
        return ((temp1 < 0 || temp2 < 0) && (temp1 > 100 || temp2 > 100));
    }

    // Given 2 int values, return true if either of them is in the range 10..20
    // inclusive.
    public static boolean in1020(int a, int b)
    {
        return ((a <= 20) && (a >= 10)) || ((b <= 20) && (b >= 10));
    }

    // We'll say that a number is "teen" if it is in the range 13..19
    // inclusive. Given 3 int values, return true if 1 or more of them are
    // teen.
    public static boolean hasTeen(int a, int b, int c)
    {  
        return isTeen(a) || isTeen(b) || isTeen(c);
    }
    // Helper method
    private static boolean isTeen(int n)
    {
        return (n >= 13) && (n <= 19);
    }

    // We'll say that a number is "teen" if it is in the range 13..19
    // inclusive. Given 2 int values, return true if one or the other is teen,
    // but not both.
    public static boolean loneTeen(int a, int b)
    {
        return ((isTeen(a) && !isTeen(b)) || (!isTeen(a) && isTeen(b)));
    }

    // Given a string, if the string "del" appears starting at index 1, return
    // a string where that "del" has been deleted. Otherwise, return the string
    // unchanged.
    public static String delDel(String str)
    {
        String newString = "";
        boolean hasDel = str.indexOf("del", 1) == 1;

        // Copy contents.
        for (int i = 0; i < str.length(); i++)
        {
            if (!(hasDel && ((i >= 1) && (i <= 3))))
            {
                newString += Character.toString(str.charAt(i));
            }
        }
                
        return newString;
    }

    // Return true if the given string begins with "mix", except the 'm' can be
    // anything, so "pix", "9ix" .. all count.
    public static boolean mixStart(String str)
    {
        return (str.indexOf("ix", 1) == 1);
    }

    // Given a string, return a string made of the first 2 chars (if present),
    // however include first char only if it is 'o' and include the second only
    // if it is 'z', so "ozymandias" yields "oz".
    public static String startOz(String str)
    {
        String newString = "";

        newString += (str.charAt(0) == 'o' ? "o" : "");
        newString += (str.charAt(1) == 'z' ? "z" : "");
        
        return newString;
    }

    // Given three int values, A B C, return the largest.
    public static int intMax(int a, int b, int c)
    {
        int max = (a > b) ? a : b;
        max = (max > c) ? max : c;
        return max;
    }

    // Given 2 int values, return whichever value is nearest to the value 10,
    // or return 0 in the event of a tie. Note that Math.abs(n) returns the
    // absolute value of a number.
    public static int close10(int a, int b)
    {
        int adiff = Math.abs(a - 10);
        int bdiff = Math.abs(b - 10);
        if (adiff < bdiff)
        {
            return a;
        }
        else if (adiff > bdiff)
        {
            return b;
        }
        else
        {
            return 0;
        }
    }

    // Given 2 int values, return true if they are both in the range 30..40
    // inclusive, or they are both in the range 40..50 inclusive.
    public static boolean in3050(int a, int b)
    {
        return ((in3040(a) && in3040(b)) || (in4050(a) && in4050(b)));
    }
    // Helper methods.
    private static boolean in3040(int n)
    {
        return ((n >= 30) && (n <= 40));
    }
    private static boolean in4050(int n)
    {
        return ((n >= 40) && (n <= 50));
    }

    // Given 2 positive int values, return the larger value that is in the
    // range 10..20 inclusive, or return 0 if neither is in that range.
    public static int max1020(int a, int b)
    {
        int max = ((a >= 10) && (a <= 20)) ? a : 0;
        max = ((b >= 10) && (b < 20)) ? ((max > b) ? max : b) : max;
        return max;
    }

    // Return true if the given string contains between 1 and 3 'e' chars.
    public static boolean stringE(String str)
    {
        int eCount = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == 'e')
            {
                eCount++;
            }
        }
        return (eCount >= 1) && (eCount <= 3);
    }

    // Given two non-negative int values, return true if they have the same
    // last digit, such as with 27 and 57. Note that the % "mod" operator
    // computes remainders, so 17 % 10 is 7.
    public static boolean lastDigit(int a, int b)
    {
        return (a % 10) == (b % 10);
    }

    // Given a string, return a new string where the last 3 chars are now in
    // upper case. If the string has less than 3 chars, uppercase whatever is
    // there. Note that str.toUpperCase() returns the uppercase version of a
    // string.
    public static String endUp(String str)
    {
        String newString = "";

        for (int i = 0; i < str.length(); i++)
        {
            if (i >= (str.length() - 3))
            {
                newString += Character.toString(str.charAt(i)).toUpperCase();
            }
            else
            {
                newString += Character.toString(str.charAt(i));
            }
        }
        
        return newString;
    }

    // Given a non-empty string and an int N, return the string made starting
    // with char 0, and then every Nth char of the string. So if N is 3, use
    // char 0, 3, 6, ... and so on. N is 1 or more.
    public static String everyNth(String str, int n)
    {
        String newString = "";

        for (int i = 0; i < str.length(); i++)
        {
            if (((i) % n) == 0)
            {
                newString += Character.toString(str.charAt(i));
            }
        }

        return newString;
    }
}
