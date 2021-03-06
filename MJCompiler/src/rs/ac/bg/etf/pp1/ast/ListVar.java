// generated with ast extension for cup
// version 0.8
// 22/5/2020 16:3:19


package rs.ac.bg.etf.pp1.ast;

public class ListVar extends VarsElementsList {

    private VarsElementsList VarsElementsList;
    private OneVar OneVar;

    public ListVar (VarsElementsList VarsElementsList, OneVar OneVar) {
        this.VarsElementsList=VarsElementsList;
        if(VarsElementsList!=null) VarsElementsList.setParent(this);
        this.OneVar=OneVar;
        if(OneVar!=null) OneVar.setParent(this);
    }

    public VarsElementsList getVarsElementsList() {
        return VarsElementsList;
    }

    public void setVarsElementsList(VarsElementsList VarsElementsList) {
        this.VarsElementsList=VarsElementsList;
    }

    public OneVar getOneVar() {
        return OneVar;
    }

    public void setOneVar(OneVar OneVar) {
        this.OneVar=OneVar;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarsElementsList!=null) VarsElementsList.accept(visitor);
        if(OneVar!=null) OneVar.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarsElementsList!=null) VarsElementsList.traverseTopDown(visitor);
        if(OneVar!=null) OneVar.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarsElementsList!=null) VarsElementsList.traverseBottomUp(visitor);
        if(OneVar!=null) OneVar.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("ListVar(\n");

        if(VarsElementsList!=null)
            buffer.append(VarsElementsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(OneVar!=null)
            buffer.append(OneVar.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [ListVar]");
        return buffer.toString();
    }
}
