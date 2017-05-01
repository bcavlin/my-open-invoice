package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "TABLES", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class TablesEntity {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String tableType;
    private String storageType;
    private String sql;
    private String remarks;
    private Long lastModification;
    private Integer id;
    private String typeName;
    private String tableClass;
    private Long rowCountEstimate;

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
    @Column(name = "TABLE_TYPE", nullable = true, length = 2147483647)
    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    @Basic
    @Column(name = "STORAGE_TYPE", nullable = true, length = 2147483647)
    public String getStorageType() {
        return storageType;
    }

    public void setStorageType(String storageType) {
        this.storageType = storageType;
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
    @Column(name = "REMARKS", nullable = true, length = 2147483647)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "LAST_MODIFICATION", nullable = true)
    public Long getLastModification() {
        return lastModification;
    }

    public void setLastModification(Long lastModification) {
        this.lastModification = lastModification;
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
    @Column(name = "TYPE_NAME", nullable = true, length = 2147483647)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "TABLE_CLASS", nullable = true, length = 2147483647)
    public String getTableClass() {
        return tableClass;
    }

    public void setTableClass(String tableClass) {
        this.tableClass = tableClass;
    }

    @Basic
    @Column(name = "ROW_COUNT_ESTIMATE", nullable = true)
    public Long getRowCountEstimate() {
        return rowCountEstimate;
    }

    public void setRowCountEstimate(Long rowCountEstimate) {
        this.rowCountEstimate = rowCountEstimate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TablesEntity that = (TablesEntity) o;

        if (tableCatalog != null ? !tableCatalog.equals(that.tableCatalog) : that.tableCatalog != null) return false;
        if (tableSchema != null ? !tableSchema.equals(that.tableSchema) : that.tableSchema != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (tableType != null ? !tableType.equals(that.tableType) : that.tableType != null) return false;
        if (storageType != null ? !storageType.equals(that.storageType) : that.storageType != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (lastModification != null ? !lastModification.equals(that.lastModification) : that.lastModification != null)
            return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (tableClass != null ? !tableClass.equals(that.tableClass) : that.tableClass != null) return false;
        if (rowCountEstimate != null ? !rowCountEstimate.equals(that.rowCountEstimate) : that.rowCountEstimate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableCatalog != null ? tableCatalog.hashCode() : 0;
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (tableType != null ? tableType.hashCode() : 0);
        result = 31 * result + (storageType != null ? storageType.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (lastModification != null ? lastModification.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (tableClass != null ? tableClass.hashCode() : 0);
        result = 31 * result + (rowCountEstimate != null ? rowCountEstimate.hashCode() : 0);
        return result;
    }
}
