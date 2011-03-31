package visitor;

public class Hand implements BodyPart
{
    private BodyPart[] elements;

    public BodyPart[] getElements()
    {
        return elements.clone();
    }

    public Hand()
    {
        this.elements = new BodyPart[] {
            new Finger(), new Finger(), new Finger(), new Finger(), new Thumb() };
    }
    
    public void accept(BodyPartVisitor visitor)
    {
        for(BodyPart part : this.getElements())
        {
            part.accept(visitor);
        }
            
        visitor.visit(this);
    }
}
