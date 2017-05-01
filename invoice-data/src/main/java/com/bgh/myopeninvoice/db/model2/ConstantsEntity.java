package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "CONSTANTS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class ConstantsEntity {
    private String constantCatalog;
    private String constantSchema;
    private String constantName;
    private Integer dataType;
    private String remarks;
    private String sql;
    private Integer id;

    @Basic
    @Column(name = "CONSTANT_CATALOG", nullable = true, length = 2147483647)
    public String getConstantCatalog() {
        return constantCatalog;
    }

    public void setConstantCatalog(String constantCatalog) {
        this.constantCatalog = constantCatalog;
    }

    @Basic
    @Column(name = "CONSTANT_SCHEMA", nullable = true, length = 2147483647)
    public String getConstantSchema() {
        return constantSchema;
    }

    public void setConstantSchema(String constantSchema) {
        this.constantSchema = constantSchema;
    }

    @Basic
    @Column(name = "CONSTANT_NAME", nullable = true, length = 2147483647)
    public String getConstantName() {
        return constantName;
    }

    public void setConstantName(String constantName) {
        this.constantName = constantName;
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

        ConstantsEntity that = (ConstantsEntity) o;

        if (constantCatalog != null ? !constantCatalog.equals(that.constantCatalog) : that.constantCatalog != null)
            return false;
        if (constantSchema != null ? !constantSchema.equals(that.constantSchema) : that.constantSchema != null)
            return false;
        if (constantName != null ? !constantName.equals(that.constantName) : that.constantName != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = constantCatalog != null ? constantCatalog.hashCode() : 0;
        result = 31 * result + (constantSchema != null ? constantSchema.hashCode() : 0);
        result = 31 * result + (constantName != null ? constantName.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
