package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "CATALOGS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class CatalogsEntity {
    private String catalogName;

    @Basic
    @Column(name = "CATALOG_NAME", nullable = true, length = 2147483647)
    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CatalogsEntity that = (CatalogsEntity) o;

        if (catalogName != null ? !catalogName.equals(that.catalogName) : that.catalogName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return catalogName != null ? catalogName.hashCode() : 0;
    }
}
