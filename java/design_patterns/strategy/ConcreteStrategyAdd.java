package strategy;

public class ConcreteStrategyAdd implements Strategy
{
    public int execute(int a, int b)
    {
        System.out.println("Calling ConcreteStrategyAdd.");
        return a + b;
    }
}
