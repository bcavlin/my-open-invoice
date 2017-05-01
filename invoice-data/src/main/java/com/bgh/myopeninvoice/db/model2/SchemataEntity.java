package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "SCHEMATA", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class SchemataEntity {
    private String catalogName;
    private String schemaName;
    private String schemaOwner;
    private String defaultCharacterSetName;
    private String defaultCollationName;
    private Boolean isDefault;
    private String remarks;
    private Integer id;

    @Basic
    @Column(name = "CATALOG_NAME", nullable = true, length = 2147483647)
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Basic
    @Column(name = "SCHEMA_NAME", nullable = true, length = 2147483647)
    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    @Basic
    @Column(name = "SCHEMA_OWNER", nullable = true, length = 2147483647)
    public String getSchemaOwner() {
        return schemaOwner;
    }

    public void setSchemaOwner(String schemaOwner) {
        this.schemaOwner = schemaOwner;
    }

    @Basic
    @Column(name = "DEFAULT_CHARACTER_SET_NAME", nullable = true, length = 2147483647)
    public String getDefaultCharacterSetName() {
        return defaultCharacterSetName;
    }

    public void setDefaultCharacterSetName(String defaultCharacterSetName) {
        this.defaultCharacterSetName = defaultCharacterSetName;
    }

    @Basic
    @Column(name = "DEFAULT_COLLATION_NAME", nullable = true, length = 2147483647)
    public String getDefaultCollationName() {
        return defaultCollationName;
    }

    public void setDefaultCollationName(String defaultCollationName) {
        this.defaultCollationName = defaultCollationName;
    }

    @Basic
    @Column(name = "IS_DEFAULT", nullable = true)
    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
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

        SchemataEntity that = (SchemataEntity) o;

        if (catalogName != null ? !catalogName.equals(that.catalogName) : that.catalogName != null) return false;
        if (schemaName != null ? !schemaName.equals(that.schemaName) : that.schemaName != null) return false;
        if (schemaOwner != null ? !schemaOwner.equals(that.schemaOwner) : that.schemaOwner != null) return false;
        if (defaultCharacterSetName != null ? !defaultCharacterSetName.equals(that.defaultCharacterSetName) : that.defaultCharacterSetName != null)
            return false;
        if (defaultCollationName != null ? !defaultCollationName.equals(that.defaultCollationName) : that.defaultCollationName != null)
            return false;
        if (isDefault != null ? !isDefault.equals(that.isDefault) : that.isDefault != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = catalogName != null ? catalogName.hashCode() : 0;
        result = 31 * result + (schemaName != null ? schemaName.hashCode() : 0);
        result = 31 * result + (schemaOwner != null ? schemaOwner.hashCode() : 0);
        result = 31 * result + (defaultCharacterSetName != null ? defaultCharacterSetName.hashCode() : 0);
        result = 31 * result + (defaultCollationName != null ? defaultCollationName.hashCode() : 0);
        result = 31 * result + (isDefault != null ? isDefault.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
