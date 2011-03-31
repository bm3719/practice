package visitor;

public class Finger implements BodyPart
{
    public void accept(BodyPartVisitor visitor)
    {
        visitor.visit(this);
    }
}
