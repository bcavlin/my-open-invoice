package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "IN_DOUBT", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class InDoubtEntity {
    private String transaction;
    private String state;

    @Basic
    @Column(name = "TRANSACTION", nullable = true, length = 2147483647)
    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    @Basic
    @Column(name = "STATE", nullable = true, length = 2147483647)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InDoubtEntity that = (InDoubtEntity) o;

        if (transaction != null ? !transaction.equals(that.transaction) : that.transaction != null) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = transaction != null ? transaction.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }
}
