package visitor;

public class BodyPartFlexVisitor implements BodyPartVisitor
{
    public void visit(Hand hand)
    {
        System.out.println("Flexing hand.");
    }

    public void visit(Finger finger)
    {
        System.out.println("Flexing finger.");
    }

    public void visit(Thumb thumb)
    {
        System.out.println("Flexing thumb.");
    }
}
