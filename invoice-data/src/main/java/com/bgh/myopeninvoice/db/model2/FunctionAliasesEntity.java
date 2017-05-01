package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "FUNCTION_ALIASES", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class FunctionAliasesEntity {
    private String aliasCatalog;
    private String aliasSchema;
    private String aliasName;
    private String javaClass;
    private String javaMethod;
    private Integer dataType;
    private String typeName;
    private Integer columnCount;
    private Short returnsResult;
    private String remarks;
    private Integer id;
    private String source;

    @Basic
    @Column(name = "ALIAS_CATALOG", nullable = true, length = 2147483647)
    public String getAliasCatalog() {
        return aliasCatalog;
    }

    public void setAliasCatalog(String aliasCatalog) {
        this.aliasCatalog = aliasCatalog;
    }

    @Basic
    @Column(name = "ALIAS_SCHEMA", nullable = true, length = 2147483647)
    public String getAliasSchema() {
        return aliasSchema;
    }

    public void setAliasSchema(String aliasSchema) {
        this.aliasSchema = aliasSchema;
    }

    @Basic
    @Column(name = "ALIAS_NAME", nullable = true, length = 2147483647)
    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    @Basic
    @Column(name = "JAVA_CLASS", nullable = true, length = 2147483647)
    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    @Basic
    @Column(name = "JAVA_METHOD", nullable = true, length = 2147483647)
    public String getJavaMethod() {
        return javaMethod;
    }

    public void setJavaMethod(String javaMethod) {
        this.javaMethod = javaMethod;
    }

    @Basic
    @Column(name = "DATA_TYPE", nullable = true)
    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    @Basic
    @Column(name = "TYPE_NAME", nullable = true, length = 2147483647)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "COLUMN_COUNT", nullable = true)
    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    @Basic
    @Column(name = "RETURNS_RESULT", nullable = true)
    public Short getReturnsResult() {
        return returnsResult;
    }

    public void setReturnsResult(Short returnsResult) {
        this.returnsResult = returnsResult;
    }

    @Basic
    @Column(name = "REMARKS", nullable = true, length = 2147483647)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "ID", nullable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SOURCE", nullable = true, length = 2147483647)
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionAliasesEntity that = (FunctionAliasesEntity) o;

        if (aliasCatalog != null ? !aliasCatalog.equals(that.aliasCatalog) : that.aliasCatalog != null) return false;
        if (aliasSchema != null ? !aliasSchema.equals(that.aliasSchema) : that.aliasSchema != null) return false;
        if (aliasName != null ? !aliasName.equals(that.aliasName) : that.aliasName != null) return false;
        if (javaClass != null ? !javaClass.equals(that.javaClass) : that.javaClass != null) return false;
        if (javaMethod != null ? !javaMethod.equals(that.javaMethod) : that.javaMethod != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (columnCount != null ? !columnCount.equals(that.columnCount) : that.columnCount != null) return false;
        if (returnsResult != null ? !returnsResult.equals(that.returnsResult) : that.returnsResult != null)
            return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = aliasCatalog != null ? aliasCatalog.hashCode() : 0;
        result = 31 * result + (aliasSchema != null ? aliasSchema.hashCode() : 0);
        result = 31 * result + (aliasName != null ? aliasName.hashCode() : 0);
        result = 31 * result + (javaClass != null ? javaClass.hashCode() : 0);
        result = 31 * result + (javaMethod != null ? javaMethod.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (columnCount != null ? columnCount.hashCode() : 0);
        result = 31 * result + (returnsResult != null ? returnsResult.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        return result;
    }
}
