package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "VIEWS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class ViewsEntity {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String viewDefinition;
    private String checkOption;
    private String isUpdatable;
    private String status;
    private String remarks;
    private Integer id;

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
    @Column(name = "VIEW_DEFINITION", nullable = true, length = 2147483647)
    public String getViewDefinition() {
        return viewDefinition;
    }

    public void setViewDefinition(String viewDefinition) {
        this.viewDefinition = viewDefinition;
    }

    @Basic
    @Column(name = "CHECK_OPTION", nullable = true, length = 2147483647)
    public String getCheckOption() {
        return checkOption;
    }

    public void setCheckOption(String checkOption) {
        this.checkOption = checkOption;
    }

    @Basic
    @Column(name = "IS_UPDATABLE", nullable = true, length = 2147483647)
    public String getIsUpdatable() {
        return isUpdatable;
    }

    public void setIsUpdatable(String isUpdatable) {
        this.isUpdatable = isUpdatable;
    }

    @Basic
    @Column(name = "STATUS", nullable = true, length = 2147483647)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewsEntity that = (ViewsEntity) o;

        if (tableCatalog != null ? !tableCatalog.equals(that.tableCatalog) : that.tableCatalog != null) return false;
        if (tableSchema != null ? !tableSchema.equals(that.tableSchema) : that.tableSchema != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (viewDefinition != null ? !viewDefinition.equals(that.viewDefinition) : that.viewDefinition != null)
            return false;
        if (checkOption != null ? !checkOption.equals(that.checkOption) : that.checkOption != null) return false;
        if (isUpdatable != null ? !isUpdatable.equals(that.isUpdatable) : that.isUpdatable != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableCatalog != null ? tableCatalog.hashCode() : 0;
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (viewDefinition != null ? viewDefinition.hashCode() : 0);
        result = 31 * result + (checkOption != null ? checkOption.hashCode() : 0);
        result = 31 * result + (isUpdatable != null ? isUpdatable.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
