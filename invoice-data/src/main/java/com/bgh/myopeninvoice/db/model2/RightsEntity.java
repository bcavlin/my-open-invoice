package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "RIGHTS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class RightsEntity {
    private String grantee;
    private String granteetype;
    private String grantedrole;
    private String rights;
    private String tableSchema;
    private String tableName;
    private Integer id;

    @Basic
    @Column(name = "GRANTEE", nullable = true, length = 2147483647)
    public String getGrantee() {
        return grantee;
    }

    public void setGrantee(String grantee) {
        this.grantee = grantee;
    }

    @Basic
    @Column(name = "GRANTEETYPE", nullable = true, length = 2147483647)
    public String getGranteetype() {
        return granteetype;
    }

    public void setGranteetype(String granteetype) {
        this.granteetype = granteetype;
    }

    @Basic
    @Column(name = "GRANTEDROLE", nullable = true, length = 2147483647)
    public String getGrantedrole() {
        return grantedrole;
    }

    public void setGrantedrole(String grantedrole) {
        this.grantedrole = grantedrole;
    }

    @Basic
    @Column(name = "RIGHTS", nullable = true, length = 2147483647)
    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
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

        RightsEntity that = (RightsEntity) o;

        if (grantee != null ? !grantee.equals(that.grantee) : that.grantee != null) return false;
        if (granteetype != null ? !granteetype.equals(that.granteetype) : that.granteetype != null) return false;
        if (grantedrole != null ? !grantedrole.equals(that.grantedrole) : that.grantedrole != null) return false;
        if (rights != null ? !rights.equals(that.rights) : that.rights != null) return false;
        if (tableSchema != null ? !tableSchema.equals(that.tableSchema) : that.tableSchema != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = grantee != null ? grantee.hashCode() : 0;
        result = 31 * result + (granteetype != null ? granteetype.hashCode() : 0);
        result = 31 * result + (grantedrole != null ? grantedrole.hashCode() : 0);
        result = 31 * result + (rights != null ? rights.hashCode() : 0);
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
