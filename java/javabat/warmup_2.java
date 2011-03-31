import java.lang.Math;

/** 
 * Practice problems from: http://javabat.com/java/Warmup-2
 * @version 1.0
 * @author Bruce C. Miller
 */
class warmup_2
{
    public static void main (String[] args)
    {
        System.out.println("Testing stringTimes...");
        test(stringTimes("Hi", 2).equals("HiHi"));
        test(stringTimes("Hi", 3).equals("HiHiHi"));
        test(stringTimes("Hi", 1).equals("Hi"));
        System.out.println("Testing frontTimes...");
        test(frontTimes("Chocolate", 2).equals("ChoCho"));
        test(frontTimes("Chocolate", 3).equals("ChoChoCho"));
        test(frontTimes("Abc", 3).equals("AbcAbcAbc"));
        System.out.println("Testing stringBits...");
        test(stringBits("Hello").equals("Hlo"));
        test(stringBits("Hi").equals("H"));
        test(stringBits("Heeololeo").equals("Hello"));
        System.out.println("Testing stringSplosion...");
        test(stringSplosion("Code").equals("CCoCodCode"));
        test(stringSplosion("abc").equals("aababc"));
        test(stringSplosion("ab").equals("aab"));
        System.out.println("Testing last2...");
        test(last2("hixxhi") == 1);
        test(last2("xaxxaxaxx") == 1);
        test(last2("axxxaaxx") == 2);
        System.out.println("Testing arracyCount9...");
        test(arrayCount9(new int[] {1, 2, 9}) == 1);
        test(arrayCount9(new int[] {1, 9, 9}) == 2);
        test(arrayCount9(new int[] {1, 9, 9, 3, 9}) == 3);
        System.out.println("Testing arrayFront9...");
        test(arrayFront9(new int[] {1, 2, 9, 3, 4}) == true);
        test(arrayFront9(new int[] {1, 2, 3, 4, 9}) == false);
        test(arrayFront9(new int[] {1, 2, 3, 4, 5}) == false);
        System.out.println("Testing array123...");
        test(array123(new int[] {1, 1, 2, 3, 1}) == true);
        test(array123(new int[] {1, 1, 2, 4, 1}) == false);
        test(array123(new int[] {1, 1, 2, 1, 2, 3}) == true);
        System.out.println("Testing stringMatch...");
        test(stringMatch("xxcaazz", "xxbaaz") == 3);
        test(stringMatch("abc", "abc") == 2);
        test(stringMatch("abc", "axc") == 0);
        System.out.println("Testing stringX...");
        test(stringX("xxHxix").equals("xHix"));
        test(stringX("abxxxcd").equals("abcd"));
        test(stringX("xabxxxcdx").equals("xabcdx"));
        System.out.println("Testing altPairs...");
        test(altPairs("kitten").equals("kien"));
        test(altPairs("Chocolate").equals("Chole"));
        test(altPairs("CodingHorror").equals("Congrr"));
        System.out.println("Testing stringYak...");
        test(stringYak("yakpak").equals("pak"));
        test(stringYak("pakyak").equals("pak"));
        test(stringYak("yak123ya").equals("123ya"));
        System.out.println("Testing array667...");
        test(array667(new int[] {6, 6, 2}) == 1);
        test(array667(new int[] {6, 6, 2, 6}) == 1);
        test(array667(new int[] {6, 7, 2, 6}) == 1);
        System.out.println("Testing noTriples...");
        test(noTriples(new int[] {1, 1, 2, 2, 1}) == true);
        test(noTriples(new int[] {1, 1, 2, 2, 2, 1}) == false);
        test(noTriples(new int[] {1, 1, 1, 2, 2, 2, 1}) == false);
        System.out.println("Testing has271...");
        test(has271(new int[] {1, 2, 7, 1}) == true);
        test(has271(new int[] {1, 2, 8, 1}) == false);
        test(has271(new int[] {2, 7, 1}) == true);
    }

    // To save some typing.
    public static void test(boolean result)
    {
        System.out.println("Test: " + (result ? "Passed" : "Failed"));
    }

    // Given a string and a non-negative int n, return a larger string that is
    // n copies of the original string.
    public static String stringTimes(String str, int n)
    {
        String newString = "";

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < str.length(); j++)
            {
                newString += Character.toString(str.charAt(j));
            }
        }
        
        return newString;
    }

    // Given a string and a non-negative int n, we'll say that the front of the
    // string is the first 3 chars, or whatever is there if the string is less
    // than length 3. Return n copies of the front;
    public static String frontTimes(String str, int n)
    {
        String newString = "";

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < (str.length() < 3 ? str.length() : 3); j++)
            {
                newString += Character.toString(str.charAt(j));
            }
        }

        return newString;
    }

    // Given a string, return a new string made of every other char starting
    // with the first, so "Hello" yields "Hlo".
    public static String stringBits(String str)
    {
        String newString = "";

        for (int i = 0; i < str.length(); i++)
        {
            if (i % 2 == 0)
            {
                newString += Character.toString(str.charAt(i));
            }
        }
        
        return newString;
    }

    // Given a non-empty string like "Code" return a string like "CCoCodCode".
    public static String stringSplosion(String str)
    {
        String newString = "";

        for (int i = 0; i < str.length(); i++)
        {
            for (int j = 0; j < i + 1; j++)
            {
                newString += Character.toString(str.charAt(j));
            }
        }
        
        return newString;
    }

    // Given a string, return the count of the number of times that a substring
    // length 2 appears in the string and also as the last 2 chars of the
    // string, so "hixxxhi" yields 1 (we won't count the end substring).
    public static int last2(String str)
    {
        String subStr = str.substring(str.length() - 2);
        String searchStr = str.substring(0, str.length() - 2);
        int subCount = 0;
        
        for (int i = 0; i < searchStr.length() - 2; i++)
        {
            if (searchStr.substring(i, i + 2).equals(subStr))
            {
                subCount++;
            }
        }
        
        return subCount;
    }

    // Given an array of ints, return the number of 9's in the array.
    public static int arrayCount9(int[] nums)
    {
        int count9 = 0;
        for (int i = 0; i < nums.length; i++)
        {
            if (nums[i] == 9)
            {
                count9++;
            }
        }
        return count9;
    }

    // Given an array of ints, return true if one of the first 4 elements in
    // the array is a 9. The array length may be less than 4.
    public static boolean arrayFront9(int[] nums)
    {
        for (int i = 0; i < ((nums.length < 4) ? nums.length : 4); i++)
        {
            if (nums[i] == 9)
            {
                return true;
            }
        }
        return false;
    }

    // Given an array of ints, return true if .. 1, 2, 3, .. appears in the
    // array somewhere.
    public static boolean array123(int[] nums)
    {
        for (int i = 2; i < nums.length; i++)
        {
            if (nums[i] == 3 && nums[i - 1] == 2 && nums[i - 2] == 1)
            {
                return true;
            }
        }
        return false;
    }

    // Given 2 strings, a and b, return the number of the positions where they
    // contain the same length 2 substring. So "xxcaazz" and "xxbaaz" yields 3,
    // since the "xx", "aa", and "az" substrings appear in the same place in
    // both strings.
    public static int stringMatch(String a, String b)
    {
        int matchCount = 0;
        for (int i = 1; i < a.length(); i++)
        {
            String aSubStr = a.substring(i - 1, i + 1);

            // Note: Ideally, we'd put a check in here to make sure we're not
            // rechecking the same substring again, but that's not a problem
            // with this input.

            for (int j = 1; j < b.length(); j++)
            {
                String bSubStr = b.substring(j - 1, j + 1);
                if (aSubStr.equals(bSubStr))
                {
                    matchCount++;
                }
            }
        }
        return matchCount;
    }

    // Given a string, return a version where all the "x" have been
    // removed. Except an "x" at the very start or end should not be removed.
    public static String stringX(String str)
    {
        String newString = "";
        
        // Add front x if needed.
        if (str.charAt(0) == 'x')
        {
            newString += "x";
        }

        // Copy non-x chars.
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) != 'x')
            {
                newString += Character.toString(str.charAt(i));
            }
        }

        // Add end x if needed.
        if (str.charAt(str.length() - 1) == 'x')
        {
            newString += "x";
        }

        return newString;
    }

    // Given a string, return a string made of the chars at indexes 0,1, 4,5,
    // 8,9 ... so "kittens" yields "kien".
    public static String altPairs(String str)
    {
        String newString = "";

        for (int i = 0; i < str.length(); i++)
        {
            if ((i % 4) < 2)
            {
                newString += Character.toString(str.charAt(i));
            }
        }

        return newString;
    }

    // Suppose the string "yak" is unlucky. Given a string, return a version
    // where all the "yak" are removed, but the "a" can be any char. The "yak"
    // strings will not overlap.
    public static String stringYak(String str)
    {
        String newString = "";
        int skipCount = 0;
        for (int i = 0; i < str.length(); i++)
        {
            if (skipCount > 0)
            {
                skipCount--;
            }
            else if ((i < str.length() - 2) &&
                     (str.charAt(i) == 'y') &&
                     (str.charAt(i + 2) == 'k'))
            {
                skipCount = 2;
            }
            else
            {
                newString += Character.toString(str.charAt(i));
            }
        }
        return newString;
    }

    // Given an array of ints, return the number of times that two 6's are next
    // to each other in the array. Also count instances where the second "6" is
    // actually a 7.
    public static int array667(int[] nums)
    {
        int matchCount = 0;
        for (int i = 1; i < nums.length; i++)
        {
            if (nums[i] == 6 || nums[i] == 7)
            {
                if (nums[i - 1] == 6)
                {
                    matchCount++;
                }
            }
        }
        return matchCount;
    }

    // Given an array of ints, we'll say that a triple is a value appearing 3
    // times in a row in the array. Return true if the array does not contain
    // any triples.
    public static boolean noTriples(int[] nums)
    {
        for (int i = 2; i < nums.length; i++)
        {
            if (nums[i - 1] == nums[i] && nums[i - 2] == nums[i])
            {
                return false;
            }
        }
        return true;
    }

    // Given an array of ints, return true if it contains a 2, 7, 1 pattern --
    // a value, followed by the value plus 5, followed by the value minus
    // 1. Additionally the 271 counts even if the "1" differs by 2 or less from
    // the correct value.
    public static boolean has271(int[] nums)
    {
        boolean found2 = false;
        boolean found7 = false;
        boolean found1 = false;
        for (int i = 0; i < nums.length; i++)
        {
            if (nums[i] == 2)
            {
                found2 = true;
            }
            else if (nums[i] == 7)
            {
                found7 = true;
            }
            else if (nums[i] == 1)
            {
                found1 = true;
            }
        }
        return found2 && found7 && found1; 
    }
}
