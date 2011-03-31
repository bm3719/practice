import singleton.*;
import strategy.*;
import visitor.*;

/** 
 * Design patterns tests.
 * @version 1.0
 * @author Bruce C. Miller
 */
public class Demo
{
    public static void main(String[] args)
    {
        System.out.println("Singleton Demo...");
        Singleton inst = Singleton.getInstance();
        System.out.println(Integer.toString(inst.someProperty));
        inst.someProperty = 9;
        Singleton inst2 = Singleton.getInstance();
        System.out.println(Integer.toString(inst2.someProperty));

        System.out.println("Strategy Demo...");
        Context context = new Context(new ConcreteStrategyAdd());
        context.executeStrategy(3, 2);
        context = new Context(new ConcreteStrategyMultiply());
        context.executeStrategy(3, 2);

        System.out.println("Visitor Demo...");
        Hand hand = new Hand();
        hand.accept(new BodyPartPrintVisitor());
        hand.accept(new BodyPartFlexVisitor());
    }
}
