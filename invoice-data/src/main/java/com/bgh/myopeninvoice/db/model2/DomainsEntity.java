package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "DOMAINS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class DomainsEntity {
    private String domainCatalog;
    private String domainSchema;
    private String domainName;
    private String columnDefault;
    private String isNullable;
    private Integer dataType;
    private Integer precision;
    private Integer scale;
    private String typeName;
    private Integer selectivity;
    private String checkConstraint;
    private String remarks;
    private String sql;
    private Integer id;

    @Basic
    @Column(name = "DOMAIN_CATALOG", nullable = true, length = 2147483647)
    public String getDomainCatalog() {
        return domainCatalog;
    }

    public void setDomainCatalog(String domainCatalog) {
        this.domainCatalog = domainCatalog;
    }

    @Basic
    @Column(name = "DOMAIN_SCHEMA", nullable = true, length = 2147483647)
    public String getDomainSchema() {
        return domainSchema;
    }

    public void setDomainSchema(String domainSchema) {
        this.domainSchema = domainSchema;
    }

    @Basic
    @Column(name = "DOMAIN_NAME", nullable = true, length = 2147483647)
    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    @Basic
    @Column(name = "COLUMN_DEFAULT", nullable = true, length = 2147483647)
    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    @Basic
    @Column(name = "IS_NULLABLE", nullable = true, length = 2147483647)
    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
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
    @Column(name = "PRECISION", nullable = true)
    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @Basic
    @Column(name = "SCALE", nullable = true)
    public Integer getScale() {
        return scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
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
    @Column(name = "SELECTIVITY", nullable = true)
    public Integer getSelectivity() {
        return selectivity;
    }

    public void setSelectivity(Integer selectivity) {
        this.selectivity = selectivity;
    }

    @Basic
    @Column(name = "CHECK_CONSTRAINT", nullable = true, length = 2147483647)
    public String getCheckConstraint() {
        return checkConstraint;
    }

    public void setCheckConstraint(String checkConstraint) {
        this.checkConstraint = checkConstraint;
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

        DomainsEntity that = (DomainsEntity) o;

        if (domainCatalog != null ? !domainCatalog.equals(that.domainCatalog) : that.domainCatalog != null)
            return false;
        if (domainSchema != null ? !domainSchema.equals(that.domainSchema) : that.domainSchema != null) return false;
        if (domainName != null ? !domainName.equals(that.domainName) : that.domainName != null) return false;
        if (columnDefault != null ? !columnDefault.equals(that.columnDefault) : that.columnDefault != null)
            return false;
        if (isNullable != null ? !isNullable.equals(that.isNullable) : that.isNullable != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (precision != null ? !precision.equals(that.precision) : that.precision != null) return false;
        if (scale != null ? !scale.equals(that.scale) : that.scale != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (selectivity != null ? !selectivity.equals(that.selectivity) : that.selectivity != null) return false;
        if (checkConstraint != null ? !checkConstraint.equals(that.checkConstraint) : that.checkConstraint != null)
            return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = domainCatalog != null ? domainCatalog.hashCode() : 0;
        result = 31 * result + (domainSchema != null ? domainSchema.hashCode() : 0);
        result = 31 * result + (domainName != null ? domainName.hashCode() : 0);
        result = 31 * result + (columnDefault != null ? columnDefault.hashCode() : 0);
        result = 31 * result + (isNullable != null ? isNullable.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (precision != null ? precision.hashCode() : 0);
        result = 31 * result + (scale != null ? scale.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (selectivity != null ? selectivity.hashCode() : 0);
        result = 31 * result + (checkConstraint != null ? checkConstraint.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
