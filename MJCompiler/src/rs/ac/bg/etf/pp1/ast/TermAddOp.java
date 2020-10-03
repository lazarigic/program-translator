// generated with ast extension for cup
// version 0.8
// 22/5/2020 16:3:19


package rs.ac.bg.etf.pp1.ast;

public class TermAddOp extends Expression {

    private Expression Expression;
    private AddOp AddOp;
    private TermTar TermTar;

    public TermAddOp (Expression Expression, AddOp AddOp, TermTar TermTar) {
        this.Expression=Expression;
        if(Expression!=null) Expression.setParent(this);
        this.AddOp=AddOp;
        if(AddOp!=null) AddOp.setParent(this);
        this.TermTar=TermTar;
        if(TermTar!=null) TermTar.setParent(this);
    }

    public Expression getExpression() {
        return Expression;
    }

    public void setExpression(Expression Expression) {
        this.Expression=Expression;
    }

    public AddOp getAddOp() {
        return AddOp;
    }

    public void setAddOp(AddOp AddOp) {
        this.AddOp=AddOp;
    }

    public TermTar getTermTar() {
        return TermTar;
    }

    public void setTermTar(TermTar TermTar) {
        this.TermTar=TermTar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Expression!=null) Expression.accept(visitor);
        if(AddOp!=null) AddOp.accept(visitor);
        if(TermTar!=null) TermTar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Expression!=null) Expression.traverseTopDown(visitor);
        if(AddOp!=null) AddOp.traverseTopDown(visitor);
        if(TermTar!=null) TermTar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Expression!=null) Expression.traverseBottomUp(visitor);
        if(AddOp!=null) AddOp.traverseBottomUp(visitor);
        if(TermTar!=null) TermTar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TermAddOp(\n");

        if(Expression!=null)
            buffer.append(Expression.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(AddOp!=null)
            buffer.append(AddOp.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TermTar!=null)
            buffer.append(TermTar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TermAddOp]");
        return buffer.toString();
    }
}
