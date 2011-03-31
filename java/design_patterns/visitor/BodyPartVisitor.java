package visitor;

interface BodyPartVisitor
{
     void visit(Finger finger);
     void visit(Hand hand);
     void visit(Thumb thumb);
}
