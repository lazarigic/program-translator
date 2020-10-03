// generated with ast extension for cup
// version 0.8
// 22/5/2020 16:3:19


package rs.ac.bg.etf.pp1.ast;

public class FormParametersList extends FormParamsList {

    private FormParamsList FormParamsList;
    private FormParam FormParam;

    public FormParametersList (FormParamsList FormParamsList, FormParam FormParam) {
        this.FormParamsList=FormParamsList;
        if(FormParamsList!=null) FormParamsList.setParent(this);
        this.FormParam=FormParam;
        if(FormParam!=null) FormParam.setParent(this);
    }

    public FormParamsList getFormParamsList() {
        return FormParamsList;
    }

    public void setFormParamsList(FormParamsList FormParamsList) {
        this.FormParamsList=FormParamsList;
    }

    public FormParam getFormParam() {
        return FormParam;
    }

    public void setFormParam(FormParam FormParam) {
        this.FormParam=FormParam;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(FormParamsList!=null) FormParamsList.accept(visitor);
        if(FormParam!=null) FormParam.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(FormParamsList!=null) FormParamsList.traverseTopDown(visitor);
        if(FormParam!=null) FormParam.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(FormParamsList!=null) FormParamsList.traverseBottomUp(visitor);
        if(FormParam!=null) FormParam.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("FormParametersList(\n");

        if(FormParamsList!=null)
            buffer.append(FormParamsList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(FormParam!=null)
            buffer.append(FormParam.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [FormParametersList]");
        return buffer.toString();
    }
}
