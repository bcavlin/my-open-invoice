package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "CONSTRAINTS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class ConstraintsEntity {
    private String constraintCatalog;
    private String constraintSchema;
    private String constraintName;
    private String constraintType;
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String uniqueIndexName;
    private String checkExpression;
    private String columnList;
    private String remarks;
    private String sql;
    private Integer id;

    @Basic
    @Column(name = "CONSTRAINT_CATALOG", nullable = true, length = 2147483647)
    public String getConstraintCatalog() {
        return constraintCatalog;
    }

    public void setConstraintCatalog(String constraintCatalog) {
        this.constraintCatalog = constraintCatalog;
    }

    @Basic
    @Column(name = "CONSTRAINT_SCHEMA", nullable = true, length = 2147483647)
    public String getConstraintSchema() {
        return constraintSchema;
    }

    public void setConstraintSchema(String constraintSchema) {
        this.constraintSchema = constraintSchema;
    }

    @Basic
    @Column(name = "CONSTRAINT_NAME", nullable = true, length = 2147483647)
    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    @Basic
    @Column(name = "CONSTRAINT_TYPE", nullable = true, length = 2147483647)
    public String getConstraintType() {
        return constraintType;
    }

    public void setConstraintType(String constraintType) {
        this.constraintType = constraintType;
    }

    @Basic
    @Column(name = "TABLE_CATALOG", nullable = true, length = 2147483647)
    public String getTableCatalog() {
        return tableCatalog;
    }

    public void setTableCatalog(String tableCatalog) {
        this.tableCatalog = tableCatalog;
    }

    @Basic
    @Column(name = "TABLE_SCHEMA", nullable = true, length = 2147483647)
    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    @Basic
    @Column(name = "TABLE_NAME", nullable = true, length = 2147483647)
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @Column(name = "UNIQUE_INDEX_NAME", nullable = true, length = 2147483647)
    public String getUniqueIndexName() {
        return uniqueIndexName;
    }

    public void setUniqueIndexName(String uniqueIndexName) {
        this.uniqueIndexName = uniqueIndexName;
    }

    @Basic
    @Column(name = "CHECK_EXPRESSION", nullable = true, length = 2147483647)
    public String getCheckExpression() {
        return checkExpression;
    }

    public void setCheckExpression(String checkExpression) {
        this.checkExpression = checkExpression;
    }

    @Basic
    @Column(name = "COLUMN_LIST", nullable = true, length = 2147483647)
    public String getColumnList() {
        return columnList;
    }

    public void setColumnList(String columnList) {
        this.columnList = columnList;
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
    @Column(name = "SQL", nullable = true, length = 2147483647)
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Basic
    @Column(name = "ID", nullable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConstraintsEntity that = (ConstraintsEntity) o;

        if (constraintCatalog != null ? !constraintCatalog.equals(that.constraintCatalog) : that.constraintCatalog != null)
            return false;
        if (constraintSchema != null ? !constraintSchema.equals(that.constraintSchema) : that.constraintSchema != null)
            return false;
        if (constraintName != null ? !constraintName.equals(that.constraintName) : that.constraintName != null)
            return false;
        if (constraintType != null ? !constraintType.equals(that.constraintType) : that.constraintType != null)
            return false;
        if (tableCatalog != null ? !tableCatalog.equals(that.tableCatalog) : that.tableCatalog != null) return false;
        if (tableSchema != null ? !tableSchema.equals(that.tableSchema) : that.tableSchema != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (uniqueIndexName != null ? !uniqueIndexName.equals(that.uniqueIndexName) : that.uniqueIndexName != null)
            return false;
        if (checkExpression != null ? !checkExpression.equals(that.checkExpression) : that.checkExpression != null)
            return false;
        if (columnList != null ? !columnList.equals(that.columnList) : that.columnList != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = constraintCatalog != null ? constraintCatalog.hashCode() : 0;
        result = 31 * result + (constraintSchema != null ? constraintSchema.hashCode() : 0);
        result = 31 * result + (constraintName != null ? constraintName.hashCode() : 0);
        result = 31 * result + (constraintType != null ? constraintType.hashCode() : 0);
        result = 31 * result + (tableCatalog != null ? tableCatalog.hashCode() : 0);
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (uniqueIndexName != null ? uniqueIndexName.hashCode() : 0);
        result = 31 * result + (checkExpression != null ? checkExpression.hashCode() : 0);
        result = 31 * result + (columnList != null ? columnList.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
