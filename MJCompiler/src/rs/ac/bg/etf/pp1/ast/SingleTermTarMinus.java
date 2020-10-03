// generated with ast extension for cup
// version 0.8
// 22/5/2020 16:3:19


package rs.ac.bg.etf.pp1.ast;

public class SingleTermTarMinus extends Expression {

    private TermTar TermTar;

    public SingleTermTarMinus (TermTar TermTar) {
        this.TermTar=TermTar;
        if(TermTar!=null) TermTar.setParent(this);
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
        if(TermTar!=null) TermTar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TermTar!=null) TermTar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TermTar!=null) TermTar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("SingleTermTarMinus(\n");

        if(TermTar!=null)
            buffer.append(TermTar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [SingleTermTarMinus]");
        return buffer.toString();
    }
}
