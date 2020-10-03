// generated with ast extension for cup
// version 0.8
// 22/5/2020 16:3:19


package rs.ac.bg.etf.pp1.ast;

public class SingleAtFactor extends Term {

    private FactorAt FactorAt;

    public SingleAtFactor (FactorAt FactorAt) {
        this.FactorAt=FactorAt;
        if(FactorAt!=null) FactorAt.setParent(this);
    }

    public FactorAt getFactorAt() {
        return FactorAt;
    }

    public void setFactorAt(FactorAt FactorAt) {
        this.FactorAt=FactorAt;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FactorAt!=null) FactorAt.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FactorAt!=null) FactorAt.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FactorAt!=null) FactorAt.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleAtFactor(\n");

        if(FactorAt!=null)
            buffer.append(FactorAt.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleAtFactor]");
        return buffer.toString();
    }
}
