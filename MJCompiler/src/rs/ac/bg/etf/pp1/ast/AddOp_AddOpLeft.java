// generated with ast extension for cup
// version 0.8
// 22/5/2020 16:3:19


package rs.ac.bg.etf.pp1.ast;

public class AddOp_AddOpLeft extends AddOp {

    private AddOpLeft AddOpLeft;

    public AddOp_AddOpLeft (AddOpLeft AddOpLeft) {
        this.AddOpLeft=AddOpLeft;
        if(AddOpLeft!=null) AddOpLeft.setParent(this);
    }

    public AddOpLeft getAddOpLeft() {
        return AddOpLeft;
    }

    public void setAddOpLeft(AddOpLeft AddOpLeft) {
        this.AddOpLeft=AddOpLeft;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(AddOpLeft!=null) AddOpLeft.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(AddOpLeft!=null) AddOpLeft.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(AddOpLeft!=null) AddOpLeft.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("AddOp_AddOpLeft(\n");

        if(AddOpLeft!=null)
            buffer.append(AddOpLeft.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [AddOp_AddOpLeft]");
        return buffer.toString();
    }
}
