package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "QUERY_STATISTICS", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class QueryStatisticsEntity {
    private String sqlStatement;
    private Integer executionCount;
    private Long minExecutionTime;
    private Long maxExecutionTime;
    private Long cumulativeExecutionTime;
    private Double averageExecutionTime;
    private Double stdDevExecutionTime;
    private Integer minRowCount;
    private Integer maxRowCount;
    private Long cumulativeRowCount;
    private Double averageRowCount;
    private Double stdDevRowCount;

    @Basic
    @Column(name = "SQL_STATEMENT", nullable = true, length = 2147483647)
    public String getSqlStatement() {
        return sqlStatement;
    }

    public void setSqlStatement(String sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    @Basic
    @Column(name = "EXECUTION_COUNT", nullable = true)
    public Integer getExecutionCount() {
        return executionCount;
    }

    public void setExecutionCount(Integer executionCount) {
        this.executionCount = executionCount;
    }

    @Basic
    @Column(name = "MIN_EXECUTION_TIME", nullable = true)
    public Long getMinExecutionTime() {
        return minExecutionTime;
    }

    public void setMinExecutionTime(Long minExecutionTime) {
        this.minExecutionTime = minExecutionTime;
    }

    @Basic
    @Column(name = "MAX_EXECUTION_TIME", nullable = true)
    public Long getMaxExecutionTime() {
        return maxExecutionTime;
    }

    public void setMaxExecutionTime(Long maxExecutionTime) {
        this.maxExecutionTime = maxExecutionTime;
    }

    @Basic
    @Column(name = "CUMULATIVE_EXECUTION_TIME", nullable = true)
    public Long getCumulativeExecutionTime() {
        return cumulativeExecutionTime;
    }

    public void setCumulativeExecutionTime(Long cumulativeExecutionTime) {
        this.cumulativeExecutionTime = cumulativeExecutionTime;
    }

    @Basic
    @Column(name = "AVERAGE_EXECUTION_TIME", nullable = true, precision = 0)
    public Double getAverageExecutionTime() {
        return averageExecutionTime;
    }

    public void setAverageExecutionTime(Double averageExecutionTime) {
        this.averageExecutionTime = averageExecutionTime;
    }

    @Basic
    @Column(name = "STD_DEV_EXECUTION_TIME", nullable = true, precision = 0)
    public Double getStdDevExecutionTime() {
        return stdDevExecutionTime;
    }

    public void setStdDevExecutionTime(Double stdDevExecutionTime) {
        this.stdDevExecutionTime = stdDevExecutionTime;
    }

    @Basic
    @Column(name = "MIN_ROW_COUNT", nullable = true)
    public Integer getMinRowCount() {
        return minRowCount;
    }

    public void setMinRowCount(Integer minRowCount) {
        this.minRowCount = minRowCount;
    }

    @Basic
    @Column(name = "MAX_ROW_COUNT", nullable = true)
    public Integer getMaxRowCount() {
        return maxRowCount;
    }

    public void setMaxRowCount(Integer maxRowCount) {
        this.maxRowCount = maxRowCount;
    }

    @Basic
    @Column(name = "CUMULATIVE_ROW_COUNT", nullable = true)
    public Long getCumulativeRowCount() {
        return cumulativeRowCount;
    }

    public void setCumulativeRowCount(Long cumulativeRowCount) {
        this.cumulativeRowCount = cumulativeRowCount;
    }

    @Basic
    @Column(name = "AVERAGE_ROW_COUNT", nullable = true, precision = 0)
    public Double getAverageRowCount() {
        return averageRowCount;
    }

    public void setAverageRowCount(Double averageRowCount) {
        this.averageRowCount = averageRowCount;
    }

    @Basic
    @Column(name = "STD_DEV_ROW_COUNT", nullable = true, precision = 0)
    public Double getStdDevRowCount() {
        return stdDevRowCount;
    }

    public void setStdDevRowCount(Double stdDevRowCount) {
        this.stdDevRowCount = stdDevRowCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QueryStatisticsEntity that = (QueryStatisticsEntity) o;

        if (sqlStatement != null ? !sqlStatement.equals(that.sqlStatement) : that.sqlStatement != null) return false;
        if (executionCount != null ? !executionCount.equals(that.executionCount) : that.executionCount != null)
            return false;
        if (minExecutionTime != null ? !minExecutionTime.equals(that.minExecutionTime) : that.minExecutionTime != null)
            return false;
        if (maxExecutionTime != null ? !maxExecutionTime.equals(that.maxExecutionTime) : that.maxExecutionTime != null)
            return false;
        if (cumulativeExecutionTime != null ? !cumulativeExecutionTime.equals(that.cumulativeExecutionTime) : that.cumulativeExecutionTime != null)
            return false;
        if (averageExecutionTime != null ? !averageExecutionTime.equals(that.averageExecutionTime) : that.averageExecutionTime != null)
            return false;
        if (stdDevExecutionTime != null ? !stdDevExecutionTime.equals(that.stdDevExecutionTime) : that.stdDevExecutionTime != null)
            return false;
        if (minRowCount != null ? !minRowCount.equals(that.minRowCount) : that.minRowCount != null) return false;
        if (maxRowCount != null ? !maxRowCount.equals(that.maxRowCount) : that.maxRowCount != null) return false;
        if (cumulativeRowCount != null ? !cumulativeRowCount.equals(that.cumulativeRowCount) : that.cumulativeRowCount != null)
            return false;
        if (averageRowCount != null ? !averageRowCount.equals(that.averageRowCount) : that.averageRowCount != null)
            return false;
        if (stdDevRowCount != null ? !stdDevRowCount.equals(that.stdDevRowCount) : that.stdDevRowCount != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sqlStatement != null ? sqlStatement.hashCode() : 0;
        result = 31 * result + (executionCount != null ? executionCount.hashCode() : 0);
        result = 31 * result + (minExecutionTime != null ? minExecutionTime.hashCode() : 0);
        result = 31 * result + (maxExecutionTime != null ? maxExecutionTime.hashCode() : 0);
        result = 31 * result + (cumulativeExecutionTime != null ? cumulativeExecutionTime.hashCode() : 0);
        result = 31 * result + (averageExecutionTime != null ? averageExecutionTime.hashCode() : 0);
        result = 31 * result + (stdDevExecutionTime != null ? stdDevExecutionTime.hashCode() : 0);
        result = 31 * result + (minRowCount != null ? minRowCount.hashCode() : 0);
        result = 31 * result + (maxRowCount != null ? maxRowCount.hashCode() : 0);
        result = 31 * result + (cumulativeRowCount != null ? cumulativeRowCount.hashCode() : 0);
        result = 31 * result + (averageRowCount != null ? averageRowCount.hashCode() : 0);
        result = 31 * result + (stdDevRowCount != null ? stdDevRowCount.hashCode() : 0);
        return result;
    }
}
