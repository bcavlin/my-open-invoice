package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "TRIGGERS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class TriggersEntity {
    private String triggerCatalog;
    private String triggerSchema;
    private String triggerName;
    private String triggerType;
    private String tableCatalog;
    private String tableSchema;
    private String tableName;
    private Boolean before;
    private String javaClass;
    private Integer queueSize;
    private Boolean noWait;
    private String remarks;
    private String sql;
    private Integer id;

    @Basic
    @Column(name = "TRIGGER_CATALOG", nullable = true, length = 2147483647)
    public String getTriggerCatalog() {
        return triggerCatalog;
    }

    public void setTriggerCatalog(String triggerCatalog) {
        this.triggerCatalog = triggerCatalog;
    }

    @Basic
    @Column(name = "TRIGGER_SCHEMA", nullable = true, length = 2147483647)
    public String getTriggerSchema() {
        return triggerSchema;
    }

    public void setTriggerSchema(String triggerSchema) {
        this.triggerSchema = triggerSchema;
    }

    @Basic
    @Column(name = "TRIGGER_NAME", nullable = true, length = 2147483647)
    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    @Basic
    @Column(name = "TRIGGER_TYPE", nullable = true, length = 2147483647)
    public String getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

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
    @Column(name = "BEFORE", nullable = true)
    public Boolean getBefore() {
        return before;
    }

    public void setBefore(Boolean before) {
        this.before = before;
    }

    @Basic
    @Column(name = "JAVA_CLASS", nullable = true, length = 2147483647)
    public String getJavaClass() {
        return javaClass;
    }

    public void setJavaClass(String javaClass) {
        this.javaClass = javaClass;
    }

    @Basic
    @Column(name = "QUEUE_SIZE", nullable = true)
    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    @Basic
    @Column(name = "NO_WAIT", nullable = true)
    public Boolean getNoWait() {
        return noWait;
    }

    public void setNoWait(Boolean noWait) {
        this.noWait = noWait;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TriggersEntity that = (TriggersEntity) o;

        if (triggerCatalog != null ? !triggerCatalog.equals(that.triggerCatalog) : that.triggerCatalog != null)
            return false;
        if (triggerSchema != null ? !triggerSchema.equals(that.triggerSchema) : that.triggerSchema != null)
            return false;
        if (triggerName != null ? !triggerName.equals(that.triggerName) : that.triggerName != null) return false;
        if (triggerType != null ? !triggerType.equals(that.triggerType) : that.triggerType != null) return false;
        if (tableCatalog != null ? !tableCatalog.equals(that.tableCatalog) : that.tableCatalog != null) return false;
        if (tableSchema != null ? !tableSchema.equals(that.tableSchema) : that.tableSchema != null) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (before != null ? !before.equals(that.before) : that.before != null) return false;
        if (javaClass != null ? !javaClass.equals(that.javaClass) : that.javaClass != null) return false;
        if (queueSize != null ? !queueSize.equals(that.queueSize) : that.queueSize != null) return false;
        if (noWait != null ? !noWait.equals(that.noWait) : that.noWait != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (sql != null ? !sql.equals(that.sql) : that.sql != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = triggerCatalog != null ? triggerCatalog.hashCode() : 0;
        result = 31 * result + (triggerSchema != null ? triggerSchema.hashCode() : 0);
        result = 31 * result + (triggerName != null ? triggerName.hashCode() : 0);
        result = 31 * result + (triggerType != null ? triggerType.hashCode() : 0);
        result = 31 * result + (tableCatalog != null ? tableCatalog.hashCode() : 0);
        result = 31 * result + (tableSchema != null ? tableSchema.hashCode() : 0);
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (before != null ? before.hashCode() : 0);
        result = 31 * result + (javaClass != null ? javaClass.hashCode() : 0);
        result = 31 * result + (queueSize != null ? queueSize.hashCode() : 0);
        result = 31 * result + (noWait != null ? noWait.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (sql != null ? sql.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
