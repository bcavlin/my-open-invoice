package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "CROSS_REFERENCES", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class CrossReferencesEntity {
    private String pktableCatalog;
    private String pktableSchema;
    private String pktableName;
    private String pkcolumnName;
    private String fktableCatalog;
    private String fktableSchema;
    private String fktableName;
    private String fkcolumnName;
    private Short ordinalPosition;
    private Short updateRule;
    private Short deleteRule;
    private String fkName;
    private String pkName;
    private Short deferrability;

    @Basic
    @Column(name = "PKTABLE_CATALOG", nullable = true, length = 2147483647)
    public String getPktableCatalog() {
        return pktableCatalog;
    }

    public void setPktableCatalog(String pktableCatalog) {
        this.pktableCatalog = pktableCatalog;
    }

    @Basic
    @Column(name = "PKTABLE_SCHEMA", nullable = true, length = 2147483647)
    public String getPktableSchema() {
        return pktableSchema;
    }

    public void setPktableSchema(String pktableSchema) {
        this.pktableSchema = pktableSchema;
    }

    @Basic
    @Column(name = "PKTABLE_NAME", nullable = true, length = 2147483647)
    public String getPktableName() {
        return pktableName;
    }

    public void setPktableName(String pktableName) {
        this.pktableName = pktableName;
    }

    @Basic
    @Column(name = "PKCOLUMN_NAME", nullable = true, length = 2147483647)
    public String getPkcolumnName() {
        return pkcolumnName;
    }

    public void setPkcolumnName(String pkcolumnName) {
        this.pkcolumnName = pkcolumnName;
    }

    @Basic
    @Column(name = "FKTABLE_CATALOG", nullable = true, length = 2147483647)
    public String getFktableCatalog() {
        return fktableCatalog;
    }

    public void setFktableCatalog(String fktableCatalog) {
        this.fktableCatalog = fktableCatalog;
    }

    @Basic
    @Column(name = "FKTABLE_SCHEMA", nullable = true, length = 2147483647)
    public String getFktableSchema() {
        return fktableSchema;
    }

    public void setFktableSchema(String fktableSchema) {
        this.fktableSchema = fktableSchema;
    }

    @Basic
    @Column(name = "FKTABLE_NAME", nullable = true, length = 2147483647)
    public String getFktableName() {
        return fktableName;
    }

    public void setFktableName(String fktableName) {
        this.fktableName = fktableName;
    }

    @Basic
    @Column(name = "FKCOLUMN_NAME", nullable = true, length = 2147483647)
    public String getFkcolumnName() {
        return fkcolumnName;
    }

    public void setFkcolumnName(String fkcolumnName) {
        this.fkcolumnName = fkcolumnName;
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
    @Column(name = "UPDATE_RULE", nullable = true)
    public Short getUpdateRule() {
        return updateRule;
    }

    public void setUpdateRule(Short updateRule) {
        this.updateRule = updateRule;
    }

    @Basic
    @Column(name = "DELETE_RULE", nullable = true)
    public Short getDeleteRule() {
        return deleteRule;
    }

    public void setDeleteRule(Short deleteRule) {
        this.deleteRule = deleteRule;
    }

    @Basic
    @Column(name = "FK_NAME", nullable = true, length = 2147483647)
    public String getFkName() {
        return fkName;
    }

    public void setFkName(String fkName) {
        this.fkName = fkName;
    }

    @Basic
    @Column(name = "PK_NAME", nullable = true, length = 2147483647)
    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    @Basic
    @Column(name = "DEFERRABILITY", nullable = true)
    public Short getDeferrability() {
        return deferrability;
    }

    public void setDeferrability(Short deferrability) {
        this.deferrability = deferrability;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CrossReferencesEntity that = (CrossReferencesEntity) o;

        if (pktableCatalog != null ? !pktableCatalog.equals(that.pktableCatalog) : that.pktableCatalog != null)
            return false;
        if (pktableSchema != null ? !pktableSchema.equals(that.pktableSchema) : that.pktableSchema != null)
            return false;
        if (pktableName != null ? !pktableName.equals(that.pktableName) : that.pktableName != null) return false;
        if (pkcolumnName != null ? !pkcolumnName.equals(that.pkcolumnName) : that.pkcolumnName != null) return false;
        if (fktableCatalog != null ? !fktableCatalog.equals(that.fktableCatalog) : that.fktableCatalog != null)
            return false;
        if (fktableSchema != null ? !fktableSchema.equals(that.fktableSchema) : that.fktableSchema != null)
            return false;
        if (fktableName != null ? !fktableName.equals(that.fktableName) : that.fktableName != null) return false;
        if (fkcolumnName != null ? !fkcolumnName.equals(that.fkcolumnName) : that.fkcolumnName != null) return false;
        if (ordinalPosition != null ? !ordinalPosition.equals(that.ordinalPosition) : that.ordinalPosition != null)
            return false;
        if (updateRule != null ? !updateRule.equals(that.updateRule) : that.updateRule != null) return false;
        if (deleteRule != null ? !deleteRule.equals(that.deleteRule) : that.deleteRule != null) return false;
        if (fkName != null ? !fkName.equals(that.fkName) : that.fkName != null) return false;
        if (pkName != null ? !pkName.equals(that.pkName) : that.pkName != null) return false;
        if (deferrability != null ? !deferrability.equals(that.deferrability) : that.deferrability != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pktableCatalog != null ? pktableCatalog.hashCode() : 0;
        result = 31 * result + (pktableSchema != null ? pktableSchema.hashCode() : 0);
        result = 31 * result + (pktableName != null ? pktableName.hashCode() : 0);
        result = 31 * result + (pkcolumnName != null ? pkcolumnName.hashCode() : 0);
        result = 31 * result + (fktableCatalog != null ? fktableCatalog.hashCode() : 0);
        result = 31 * result + (fktableSchema != null ? fktableSchema.hashCode() : 0);
        result = 31 * result + (fktableName != null ? fktableName.hashCode() : 0);
        result = 31 * result + (fkcolumnName != null ? fkcolumnName.hashCode() : 0);
        result = 31 * result + (ordinalPosition != null ? ordinalPosition.hashCode() : 0);
        result = 31 * result + (updateRule != null ? updateRule.hashCode() : 0);
        result = 31 * result + (deleteRule != null ? deleteRule.hashCode() : 0);
        result = 31 * result + (fkName != null ? fkName.hashCode() : 0);
        result = 31 * result + (pkName != null ? pkName.hashCode() : 0);
        result = 31 * result + (deferrability != null ? deferrability.hashCode() : 0);
        return result;
    }
}
