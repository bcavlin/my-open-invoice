package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "INDEXES", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class IndexesEntity {
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private Boolean nonUnique;
    private String indexName;
    private Short ordinalPosition;
    private String columnName;
    private Integer cardinality;
    private Boolean primaryKey;
    private String indexTypeName;
    private Boolean isGenerated;
    private Short indexType;
    private String ascOrDesc;
    private Integer pages;
    private String filterCondition;
    private String remarks;
    private String sql;
    private Integer id;
    private Integer sortType;
    private String constraintName;
    private String indexClass;

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
    @Column(name = "NON_UNIQUE", nullable = true)
    public Boolean getNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(Boolean nonUnique) {
        this.nonUnique = nonUnique;
    }

    @Basic
    @Column(name = "INDEX_NAME", nullable = true, length = 2147483647)
    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    @Basic
    @Column(name = "ORDINAL_POSITION", nullable = true)
    public Short getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(Short ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
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
    @Column(name = "CARDINALITY", nullable = true)
    public Integer getCardinality() {
        return cardinality;
    }

    public void setCardinality(Integer cardinality) {
        this.cardinality = cardinality;
    }

    @Basic
    @Column(name = "PRIMARY_KEY", nullable = true)
    public Boolean getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(Boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Basic
    @Column(name = "INDEX_TYPE_NAME", nullable = true, length = 2147483647)
    public String getIndexTypeName() {
        return indexTypeName;
    }

    public void setIndexTypeName(String indexTypeName) {
        this.indexTypeName = indexTypeName;
    }

    @Basic
    @Column(name = "IS_GENERATED", nullable = true)
    public Boolean getGenerated() {
        return isGenerated;
    }

    public void setGenerated(Boolean generated) {
        isGenerated = generated;
    }

    @Basic
    @Column(name = "INDEX_TYPE", nullable = true)
    public Short getIndexType() {
        return indexType;
    }

    public void setIndexType(Short indexType) {
        this.indexType = indexType;
    }

    @Basic
    @Column(name = "ASC_OR_DESC", nullable = true, length = 2147483647)
    public String getAscOrDesc() {
        return ascOrDesc;
    }

    public void setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }

    @Basic
    @Column(name = "PAGES", nullable = true)
    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    @Basic
    @Column(name = "FILTER_CONDITION", nullable = true, length = 2147483647)
    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
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

    @Basic
    @Column(name = "SORT_TYPE", nullable = true)
    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
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
    @Column(name = "INDEX_CLASS", nullable = true, length = 2147483647)
    public String getIndexClass() {
        return indexClass;
    }

    public void setIndexClass(String indexClass) {
        this.indexClass = indexClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IndexesEntity that = (IndexesEntity) o;

        if (tableCatalog != null ? !tableCatalog.equals(that.tableCatalog) : that.tableCatalog != null) return false;
        if (tableSchema != null ? !tableSchema.equals(that.tableSchema) : that.tableSchema != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (nonUnique != null ? !nonUnique.equals(that.nonUnique) : that.nonUnique != null) return false;
        if (indexName != null ? !indexName.equals(that.indexName) : that.indexName != null) return false;
        if (ordinalPosition != null ? !ordinalPosition.equals(that.ordinalPosition) : that.ordinalPosition != null)
            return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (cardinality != null ? !cardinality.equals(that.cardinality) : that.cardinality != null) return false;
        if (primaryKey != null ? !primaryKey.equals(that.primaryKey) : that.primaryKey != null) return false;
        if (indexTypeName != null ? !indexTypeName.equals(that.indexTypeName) : that.indexTypeName != null)
            return false;
        if (isGenerated != null ? !isGenerated.equals(that.isGenerated) : that.isGenerated != null) return false;
        if (indexType != null ? !indexType.equals(that.indexType) : that.indexType != null) return false;
        if (ascOrDesc != null ? !ascOrDesc.equals(that.ascOrDesc) : that.ascOrDesc != null) return false;
        if (pages != null ? !pages.equals(that.pages) : that.pages != null) return false;
        if (filterCondition != null ? !filterCondition.equals(that.filterCondition) : that.filterCondition != null)
            return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (sortType != null ? !sortType.equals(that.sortType) : that.sortType != null) return false;
        if (constraintName != null ? !constraintName.equals(that.constraintName) : that.constraintName != null)
            return false;
        if (indexClass != null ? !indexClass.equals(that.indexClass) : that.indexClass != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tableCatalog != null ? tableCatalog.hashCode() : 0;
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (nonUnique != null ? nonUnique.hashCode() : 0);
        result = 31 * result + (indexName != null ? indexName.hashCode() : 0);
        result = 31 * result + (ordinalPosition != null ? ordinalPosition.hashCode() : 0);
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (cardinality != null ? cardinality.hashCode() : 0);
        result = 31 * result + (primaryKey != null ? primaryKey.hashCode() : 0);
        result = 31 * result + (indexTypeName != null ? indexTypeName.hashCode() : 0);
        result = 31 * result + (isGenerated != null ? isGenerated.hashCode() : 0);
        result = 31 * result + (indexType != null ? indexType.hashCode() : 0);
        result = 31 * result + (ascOrDesc != null ? ascOrDesc.hashCode() : 0);
        result = 31 * result + (pages != null ? pages.hashCode() : 0);
        result = 31 * result + (filterCondition != null ? filterCondition.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (sortType != null ? sortType.hashCode() : 0);
        result = 31 * result + (constraintName != null ? constraintName.hashCode() : 0);
        result = 31 * result + (indexClass != null ? indexClass.hashCode() : 0);
        return result;
    }
}
