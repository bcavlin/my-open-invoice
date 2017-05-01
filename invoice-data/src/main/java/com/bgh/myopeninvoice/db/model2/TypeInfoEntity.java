package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "TYPE_INFO", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class TypeInfoEntity {
    private String typeName;
    private Integer dataType;
    private Integer precision;
    private String prefix;
    private String suffix;
    private String params;
    private Boolean autoIncrement;
    private Short minimumScale;
    private Short maximumScale;
    private Integer radix;
    private Integer pos;
    private Boolean caseSensitive;
    private Short nullable;
    private Short searchable;

    @Basic
    @Column(name = "TYPE_NAME", nullable = true, length = 2147483647)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
    @Column(name = "PREFIX", nullable = true, length = 2147483647)
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Basic
    @Column(name = "SUFFIX", nullable = true, length = 2147483647)
    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Basic
    @Column(name = "PARAMS", nullable = true, length = 2147483647)
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Basic
    @Column(name = "AUTO_INCREMENT", nullable = true)
    public Boolean getAutoIncrement() {
        return autoIncrement;
    }

    public void setAutoIncrement(Boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

    @Basic
    @Column(name = "MINIMUM_SCALE", nullable = true)
    public Short getMinimumScale() {
        return minimumScale;
    }

    public void setMinimumScale(Short minimumScale) {
        this.minimumScale = minimumScale;
    }

    @Basic
    @Column(name = "MAXIMUM_SCALE", nullable = true)
    public Short getMaximumScale() {
        return maximumScale;
    }

    public void setMaximumScale(Short maximumScale) {
        this.maximumScale = maximumScale;
    }

    @Basic
    @Column(name = "RADIX", nullable = true)
    public Integer getRadix() {
        return radix;
    }

    public void setRadix(Integer radix) {
        this.radix = radix;
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
    @Column(name = "CASE_SENSITIVE", nullable = true)
    public Boolean getCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
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
    @Column(name = "SEARCHABLE", nullable = true)
    public Short getSearchable() {
        return searchable;
    }

    public void setSearchable(Short searchable) {
        this.searchable = searchable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TypeInfoEntity that = (TypeInfoEntity) o;

        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (precision != null ? !precision.equals(that.precision) : that.precision != null) return false;
        if (prefix != null ? !prefix.equals(that.prefix) : that.prefix != null) return false;
        if (suffix != null ? !suffix.equals(that.suffix) : that.suffix != null) return false;
        if (params != null ? !params.equals(that.params) : that.params != null) return false;
        if (autoIncrement != null ? !autoIncrement.equals(that.autoIncrement) : that.autoIncrement != null)
            return false;
        if (minimumScale != null ? !minimumScale.equals(that.minimumScale) : that.minimumScale != null) return false;
        if (maximumScale != null ? !maximumScale.equals(that.maximumScale) : that.maximumScale != null) return false;
        if (radix != null ? !radix.equals(that.radix) : that.radix != null) return false;
        if (pos != null ? !pos.equals(that.pos) : that.pos != null) return false;
        if (caseSensitive != null ? !caseSensitive.equals(that.caseSensitive) : that.caseSensitive != null)
            return false;
        if (nullable != null ? !nullable.equals(that.nullable) : that.nullable != null) return false;
        if (searchable != null ? !searchable.equals(that.searchable) : that.searchable != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = typeName != null ? typeName.hashCode() : 0;
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (precision != null ? precision.hashCode() : 0);
        result = 31 * result + (prefix != null ? prefix.hashCode() : 0);
        result = 31 * result + (suffix != null ? suffix.hashCode() : 0);
        result = 31 * result + (params != null ? params.hashCode() : 0);
        result = 31 * result + (autoIncrement != null ? autoIncrement.hashCode() : 0);
        result = 31 * result + (minimumScale != null ? minimumScale.hashCode() : 0);
        result = 31 * result + (maximumScale != null ? maximumScale.hashCode() : 0);
        result = 31 * result + (radix != null ? radix.hashCode() : 0);
        result = 31 * result + (pos != null ? pos.hashCode() : 0);
        result = 31 * result + (caseSensitive != null ? caseSensitive.hashCode() : 0);
        result = 31 * result + (nullable != null ? nullable.hashCode() : 0);
        result = 31 * result + (searchable != null ? searchable.hashCode() : 0);
        return result;
    }
}
