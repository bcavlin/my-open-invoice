package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "COLUMNS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class ColumnsEntity {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private String columnName;
    private Integer ordinalPosition;
    private String columnDefault;
    private String isNullable;
    private Integer dataType;
    private Integer characterMaximumLength;
    private Integer characterOctetLength;
    private Integer numericPrecision;
    private Integer numericPrecisionRadix;
    private Integer numericScale;
    private String characterSetName;
    private String collationName;
    private String typeName;
    private Integer nullable;
    private Boolean isComputed;
    private Integer selectivity;
    private String checkConstraint;
    private String sequenceName;
    private String remarks;
    private Short sourceDataType;

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
    @Column(name = "COLUMN_NAME", nullable = true, length = 2147483647)
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "ORDINAL_POSITION", nullable = true)
    public Integer getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Integer ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
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
    @Column(name = "CHARACTER_MAXIMUM_LENGTH", nullable = true)
    public Integer getCharacterMaximumLength() {
        return characterMaximumLength;
    }

    public void setCharacterMaximumLength(Integer characterMaximumLength) {
        this.characterMaximumLength = characterMaximumLength;
    }

    @Basic
    @Column(name = "CHARACTER_OCTET_LENGTH", nullable = true)
    public Integer getCharacterOctetLength() {
        return characterOctetLength;
    }

    public void setCharacterOctetLength(Integer characterOctetLength) {
        this.characterOctetLength = characterOctetLength;
    }

    @Basic
    @Column(name = "NUMERIC_PRECISION", nullable = true)
    public Integer getNumericPrecision() {
        return numericPrecision;
    }

    public void setNumericPrecision(Integer numericPrecision) {
        this.numericPrecision = numericPrecision;
    }

    @Basic
    @Column(name = "NUMERIC_PRECISION_RADIX", nullable = true)
    public Integer getNumericPrecisionRadix() {
        return numericPrecisionRadix;
    }

    public void setNumericPrecisionRadix(Integer numericPrecisionRadix) {
        this.numericPrecisionRadix = numericPrecisionRadix;
    }

    @Basic
    @Column(name = "NUMERIC_SCALE", nullable = true)
    public Integer getNumericScale() {
        return numericScale;
    }

    public void setNumericScale(Integer numericScale) {
        this.numericScale = numericScale;
    }

    @Basic
    @Column(name = "CHARACTER_SET_NAME", nullable = true, length = 2147483647)
    public String getCharacterSetName() {
        return characterSetName;
    }

    public void setCharacterSetName(String characterSetName) {
        this.characterSetName = characterSetName;
    }

    @Basic
    @Column(name = "COLLATION_NAME", nullable = true, length = 2147483647)
    public String getCollationName() {
        return collationName;
    }

    public void setCollationName(String collationName) {
        this.collationName = collationName;
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
    @Column(name = "NULLABLE", nullable = true)
    public Integer getNullable() {
        return nullable;
    }

    public void setNullable(Integer nullable) {
        this.nullable = nullable;
    }

    @Basic
    @Column(name = "IS_COMPUTED", nullable = true)
    public Boolean getComputed() {
        return isComputed;
    }

    public void setComputed(Boolean computed) {
        isComputed = computed;
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
    @Column(name = "SEQUENCE_NAME", nullable = true, length = 2147483647)
    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
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
    @Column(name = "SOURCE_DATA_TYPE", nullable = true)
    public Short getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(Short sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColumnsEntity that = (ColumnsEntity) o;

        if (tableCatalog != null ? !tableCatalog.equals(that.tableCatalog) : that.tableCatalog != null) return false;
        if (tableSchema != null ? !tableSchema.equals(that.tableSchema) : that.tableSchema != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (ordinalPosition != null ? !ordinalPosition.equals(that.ordinalPosition) : that.ordinalPosition != null)
            return false;
        if (columnDefault != null ? !columnDefault.equals(that.columnDefault) : that.columnDefault != null)
            return false;
        if (isNullable != null ? !isNullable.equals(that.isNullable) : that.isNullable != null) return false;
        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) return false;
        if (characterMaximumLength != null ? !characterMaximumLength.equals(that.characterMaximumLength) : that.characterMaximumLength != null)
            return false;
        if (characterOctetLength != null ? !characterOctetLength.equals(that.characterOctetLength) : that.characterOctetLength != null)
            return false;
        if (numericPrecision != null ? !numericPrecision.equals(that.numericPrecision) : that.numericPrecision != null)
            return false;
        if (numericPrecisionRadix != null ? !numericPrecisionRadix.equals(that.numericPrecisionRadix) : that.numericPrecisionRadix != null)
            return false;
        if (numericScale != null ? !numericScale.equals(that.numericScale) : that.numericScale != null) return false;
        if (characterSetName != null ? !characterSetName.equals(that.characterSetName) : that.characterSetName != null)
            return false;
        if (collationName != null ? !collationName.equals(that.collationName) : that.collationName != null)
            return false;
        if (typeName != null ? !typeName.equals(that.typeName) : that.typeName != null) return false;
        if (nullable != null ? !nullable.equals(that.nullable) : that.nullable != null) return false;
        if (isComputed != null ? !isComputed.equals(that.isComputed) : that.isComputed != null) return false;
        if (selectivity != null ? !selectivity.equals(that.selectivity) : that.selectivity != null) return false;
        if (checkConstraint != null ? !checkConstraint.equals(that.checkConstraint) : that.checkConstraint != null)
            return false;
        if (sequenceName != null ? !sequenceName.equals(that.sequenceName) : that.sequenceName != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (sourceDataType != null ? !sourceDataType.equals(that.sourceDataType) : that.sourceDataType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableCatalog != null ? tableCatalog.hashCode() : 0;
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (ordinalPosition != null ? ordinalPosition.hashCode() : 0);
        result = 31 * result + (columnDefault != null ? columnDefault.hashCode() : 0);
        result = 31 * result + (isNullable != null ? isNullable.hashCode() : 0);
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        result = 31 * result + (characterMaximumLength != null ? characterMaximumLength.hashCode() : 0);
        result = 31 * result + (characterOctetLength != null ? characterOctetLength.hashCode() : 0);
        result = 31 * result + (numericPrecision != null ? numericPrecision.hashCode() : 0);
        result = 31 * result + (numericPrecisionRadix != null ? numericPrecisionRadix.hashCode() : 0);
        result = 31 * result + (numericScale != null ? numericScale.hashCode() : 0);
        result = 31 * result + (characterSetName != null ? characterSetName.hashCode() : 0);
        result = 31 * result + (collationName != null ? collationName.hashCode() : 0);
        result = 31 * result + (typeName != null ? typeName.hashCode() : 0);
        result = 31 * result + (nullable != null ? nullable.hashCode() : 0);
        result = 31 * result + (isComputed != null ? isComputed.hashCode() : 0);
        result = 31 * result + (selectivity != null ? selectivity.hashCode() : 0);
        result = 31 * result + (checkConstraint != null ? checkConstraint.hashCode() : 0);
        result = 31 * result + (sequenceName != null ? sequenceName.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (sourceDataType != null ? sourceDataType.hashCode() : 0);
        return result;
    }
}
