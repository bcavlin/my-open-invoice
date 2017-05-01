package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "SESSIONS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class SessionsEntity {
    private Integer id;
    private String userName;
    private String sessionStart;
    private String statement;
    private String statementStart;
    private String containsUncommitted;

    @Basic
    @Column(name = "ID", nullable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USER_NAME", nullable = true, length = 2147483647)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "SESSION_START", nullable = true, length = 2147483647)
    public String getSessionStart() {
        return sessionStart;
    }

    public void setSessionStart(String sessionStart) {
        this.sessionStart = sessionStart;
    }

    @Basic
    @Column(name = "STATEMENT", nullable = true, length = 2147483647)
    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    @Basic
    @Column(name = "STATEMENT_START", nullable = true, length = 2147483647)
    public String getStatementStart() {
        return statementStart;
    }

    public void setStatementStart(String statementStart) {
        this.statementStart = statementStart;
    }

    @Basic
    @Column(name = "CONTAINS_UNCOMMITTED", nullable = true, length = 2147483647)
    public String getContainsUncommitted() {
        return containsUncommitted;
    }

    public void setContainsUncommitted(String containsUncommitted) {
        this.containsUncommitted = containsUncommitted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SessionsEntity that = (SessionsEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;
        if (sessionStart != null ? !sessionStart.equals(that.sessionStart) : that.sessionStart != null) return false;
        if (statement != null ? !statement.equals(that.statement) : that.statement != null) return false;
        if (statementStart != null ? !statementStart.equals(that.statementStart) : that.statementStart != null)
            return false;
        if (containsUncommitted != null ? !containsUncommitted.equals(that.containsUncommitted) : that.containsUncommitted != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (sessionStart != null ? sessionStart.hashCode() : 0);
        result = 31 * result + (statement != null ? statement.hashCode() : 0);
        result = 31 * result + (statementStart != null ? statementStart.hashCode() : 0);
        result = 31 * result + (containsUncommitted != null ? containsUncommitted.hashCode() : 0);
        return result;
    }
}
