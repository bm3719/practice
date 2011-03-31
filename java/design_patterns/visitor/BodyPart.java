package visitor;

public interface BodyPart
{
    void accept(BodyPartVisitor visitor);
}
