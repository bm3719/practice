package singleton;

/** 
 * Singleton Pattern.  Note that there's also a "Bill Pugh" singleton solution
 * that is different than what's implemented here.
 * @version 1.0
 * @author Bruce C. Miller
 */
public class Singleton
{
    public static final Singleton INSTANCE = new Singleton();
    public int someProperty;
    
    private Singleton()
    {
        someProperty = 0;
    }

    public static Singleton getInstance()
    {
        return INSTANCE;
    }
}
