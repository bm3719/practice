/** 
 * Practice problems from: http://javabat.com/java/String-1
 * @version 1.0
 * @author Bruce C. Miller
 */
class string_1
{
    public static void main (String[] args)
    {
        System.out.println("Testing helloName...");
        test(helloName("Bob").equals("Hello Bob!"));
        test(helloName("Alice").equals("Hello Alice!"));
        test(helloName("X").equals("Hello X!"));
        System.out.println("Testing makeAbba...");
        test(makeAbba("Hi", "Bye").equals("HiByeByeHi"));
        test(makeAbba("Yo", "Alice").equals("YoAliceAliceYo"));
        test(makeAbba("x", "y").equals("xyyx"));
        System.out.println("Testing makeTags...");
        test(makeTags("i", "Yay").equals("<i>Yay</i>"));
        test(makeTags("i", "Hello").equals("<i>Hello</i>"));
        test(makeTags("cite", "Yay").equals("<cite>Yay</cite>"));
        System.out.println("Testing makeOutWord...");
        test(makeOutWord("<<>>", "Yay").equals("<<Yay>>"));
        test(makeOutWord("<<>>", "WooHoo").equals("<<WooHoo>>"));
        test(makeOutWord("[[]]", "word").equals("[[word]]"));
        System.out.println("Testing extraEnd");
        test(extraEnd("Hello").equals("lololo"));
        test(extraEnd("ab").equals("ababab"));
        test(extraEnd("Hi").equals("HiHiHi"));
        // Finish these later... switching over to the harder problems for now.
    }

    // To save some typing.
    public static void test(boolean result)
    {
        System.out.println("Test: " + (result ? "Passed" : "Failed"));
    }

    // Given a string name, e.g. "Bob", return a greeting of the form "Hello
    // Bob!".
    public static String helloName(String str)
    {
        return "Hello " + str + "!";
    }

    // Given two strings, a and b, return the result of putting them together
    // in the order abba, e.g. "Hi" and "Bye" returns "HiByeByeHi".
    public static String makeAbba(String a, String b)
    {
        return a + b + b + a;
    }

    // The web is built with HTML strings like "<i>Yay</i>" which draws Yay as
    // italic text. In this example, the "i" tag makes <i> and </i> which
    // surround the word "Yay". Given tag and word strings, create the HTML
    // string with tags around the word, e.g. "<i>Yay</i>".
    public static String makeTags(String tag, String word)
    {
        return "<" + tag + ">" + word + "</" + tag + ">";
    }

    // Given an "out" string length 4, such as "<<>>", and a word, return a new
    // string where the word is in the middle of the out string,
    // e.g. "<<word>>". Note: use str.substring(i, j) to extract the String
    // starting at index i and going up to but not including index j.
    public static String makeOutWord(String out, String word)
    {
        return out.substring(0, 2) + word + out.substring(2, 4);
    }

    // Given a string, return a new string made of 3 copies of the last 2 chars
    // of the original string. The string length will be at least 2.
    public static String extraEnd(String str)
    {
        return str.substring(str.length() - 2, str.length()) +
            str.substring(str.length() - 2, str.length()) +
            str.substring(str.length() - 2, str.length());
    }
}
