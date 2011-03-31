package strategy;

public class ConcreteStrategyMultiply implements Strategy
{
    public int execute(int a, int b)
    {
        System.out.println("Calling ConcreteStrategyMultiply.");
        return a * b;
    }
}
