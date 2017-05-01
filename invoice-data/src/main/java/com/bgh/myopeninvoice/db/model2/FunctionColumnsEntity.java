package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "FUNCTION_COLUMNS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class FunctionColumnsEntity {
    private String aliasCatalog;
    private String aliasSchema;
    private String aliasName;
    private String javaClass;
    private String javaMethod;
    private Integer columnCount;
    private Integer pos;
    private String columnName;
    private Integer dataType;
    private String typeName;
    private Integer precision;
    private Short scale;
    private Short radix;
    private Short nullable;
    private Short columnType;
    private String remarks;
    private String columnDefault;

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
    @Column(name = "COLUMN_COUNT", nullable = true)
    public Integer getColumnCount() {
        return columnCount;
    }

    public void setColumnCount(Integer columnCount) {
        this.columnCount = columnCount;
    }

    @Basic
    @Column(name = "POS", nullable = true)
    public Integer getPos() {
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    @Basic
    @Column(name = "COLUMN_NAME", nullable = true, length = 2147483647)
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
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
    @Column(name = "PRECISION", nullable = true)
    public Integer getPrecision() {
        return precision;
    }

    public void setPrecision(Integer precision) {
        this.precision = precision;
    }

    @Basic
    @Column(name = "SCALE", nullable = true)
    public Short getScale() {
        return scale;
    }

    public void setScale(Short scale) {
        this.scale = scale;
    }

    @Basic
    @Column(name = "RADIX", nullable = true)
    public Short getRadix() {
        return radix;
    }

    public void setRadix(Short radix) {
        this.radix = radix;
    }

    @Basic
    @Column(name = "NULLABLE", nullable = true)
    public Short getNullable() {
        return nullable;
    }

    public void setNullable(Short nullable) {
        this.nullable = nullable;
    }

    @Basic
    @Column(name = "COLUMN_TYPE", nullable = true)
    public Short getColumnType() {
        return columnType;
    }

    public void setColumnType(Short columnType) {
        this.columnType = columnType;
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
    @Column(name = "COLUMN_DEFAULT", nullable = true, length = 2147483647)
    public String getColumnDefault() {
        return columnDefault;
    }

    public void setColumnDefault(String columnDefault) {
        this.columnDefault = columnDefault;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FunctionColumnsEntity that = (FunctionColumnsEntity) o;

        if (aliasCatalog != null ? !aliasCatalog.equals(that.aliasCatalog) : that.aliasCatalog != null) return false;
        if (aliasSchema != null ? !aliasSchema.equals(that.aliasSchema) : that.aliasSchema != null) return false;
        if (aliasName != null ? !aliasName.equals(that.aliasName) : that.aliasName != null) return false;
        if (javaClass != null ? !javaClass.equals(that.javaClass) : that.javaClass != null) return false;
        if (javaMethod != null ? !javaMethod.equals(that.javaMethod) : that.javaMethod != null) return false;
        if (columnCount != null ? !columnCount.equals(that.columnCount) : that.columnCount != null) return false;
        if (pos != null ? !pos.equals(that.pos) : that.pos != null) return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (precision != null ? !precision.equals(that.precision) : that.precision != null) return false;
        if (scale != null ? !scale.equals(that.scale) : that.scale != null) return false;
        if (radix != null ? !radix.equals(that.radix) : that.radix != null) return false;
        if (nullable != null ? !nullable.equals(that.nullable) : that.nullable != null) return false;
        if (columnType != null ? !columnType.equals(that.columnType) : that.columnType != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (columnDefault != null ? !columnDefault.equals(that.columnDefault) : that.columnDefault != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = aliasCatalog != null ? aliasCatalog.hashCode() : 0;
        result = 31 * result + (aliasSchema != null ? aliasSchema.hashCode() : 0);
        result = 31 * result + (aliasName != null ? aliasName.hashCode() : 0);
        result = 31 * result + (javaClass != null ? javaClass.hashCode() : 0);
        result = 31 * result + (javaMethod != null ? javaMethod.hashCode() : 0);
        result = 31 * result + (columnCount != null ? columnCount.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (precision != null ? precision.hashCode() : 0);
        result = 31 * result + (scale != null ? scale.hashCode() : 0);
        result = 31 * result + (radix != null ? radix.hashCode() : 0);
        result = 31 * result + (nullable != null ? nullable.hashCode() : 0);
        result = 31 * result + (columnType != null ? columnType.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (columnDefault != null ? columnDefault.hashCode() : 0);
        return result;
    }
}
