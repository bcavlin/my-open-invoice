package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "SESSION_STATE", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class SessionStateEntity {
    private String key;
    private String sql;

    @Basic
    @Column(name = "KEY", nullable = true, length = 2147483647)
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "SQL", nullable = true, length = 2147483647)
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionStateEntity that = (SessionStateEntity) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        return result;
    }
}
