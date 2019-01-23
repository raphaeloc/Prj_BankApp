package com.example.prj_bankapp.model.listmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statement {

    @SerializedName("statementList")
    @Expose
    private java.util.List<StatementList> statementList = null;

    public java.util.List<StatementList> getStatementList() {
        return statementList;
    }

    public void setStatementList(java.util.List<StatementList> statementList) {
        this.statementList = statementList;
    }

}