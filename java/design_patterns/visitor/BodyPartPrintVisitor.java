package visitor;

public class BodyPartPrintVisitor implements BodyPartVisitor
{
    public void visit(Hand hand)
    {
        System.out.println("Visiting hand.");
    }

    public void visit(Finger finger)
    {
        System.out.println("Visiting finger.");
    }

    public void visit(Thumb thumb)
    {
        System.out.println("Visiting thumb.");
    }
}
