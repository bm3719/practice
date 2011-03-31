package visitor;

public class Thumb implements BodyPart
{
    public void accept(BodyPartVisitor visitor)
    {
        visitor.visit(this);
    }
}
