package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "SEQUENCES", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class SequencesEntity {
    private String sequenceCatalog;
    private String sequenceSchema;
    private String sequenceName;
    private Long currentValue;
    private Long increment;
    private Boolean isGenerated;
    private String remarks;
    private Long cache;
    private Long minValue;
    private Long maxValue;
    private Boolean isCycle;
    private Integer id;

    @Basic
    @Column(name = "SEQUENCE_CATALOG", nullable = true, length = 2147483647)
    public String getSequenceCatalog() {
        return sequenceCatalog;
    }

    public void setSequenceCatalog(String sequenceCatalog) {
        this.sequenceCatalog = sequenceCatalog;
    }

    @Basic
    @Column(name = "SEQUENCE_SCHEMA", nullable = true, length = 2147483647)
    public String getSequenceSchema() {
        return sequenceSchema;
    }

    public void setSequenceSchema(String sequenceSchema) {
        this.sequenceSchema = sequenceSchema;
    }

    @Basic
    @Column(name = "SEQUENCE_NAME", nullable = true, length = 2147483647)
    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName;
    }

    @Basic
    @Column(name = "CURRENT_VALUE", nullable = true)
    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    @Basic
    @Column(name = "INCREMENT", nullable = true)
    public Long getIncrement() {
        return increment;
    }

    public void setIncrement(Long increment) {
        this.increment = increment;
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
    @Column(name = "REMARKS", nullable = true, length = 2147483647)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Basic
    @Column(name = "CACHE", nullable = true)
    public Long getCache() {
        return cache;
    }

    public void setCache(Long cache) {
        this.cache = cache;
    }

    @Basic
    @Column(name = "MIN_VALUE", nullable = true)
    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    @Basic
    @Column(name = "MAX_VALUE", nullable = true)
    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    @Basic
    @Column(name = "IS_CYCLE", nullable = true)
    public Boolean getCycle() {
        return isCycle;
    }

    public void setCycle(Boolean cycle) {
        isCycle = cycle;
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

        SequencesEntity that = (SequencesEntity) o;

        if (sequenceCatalog != null ? !sequenceCatalog.equals(that.sequenceCatalog) : that.sequenceCatalog != null)
            return false;
        if (sequenceSchema != null ? !sequenceSchema.equals(that.sequenceSchema) : that.sequenceSchema != null)
            return false;
        if (sequenceName != null ? !sequenceName.equals(that.sequenceName) : that.sequenceName != null) return false;
        if (currentValue != null ? !currentValue.equals(that.currentValue) : that.currentValue != null) return false;
        if (increment != null ? !increment.equals(that.increment) : that.increment != null) return false;
        if (isGenerated != null ? !isGenerated.equals(that.isGenerated) : that.isGenerated != null) return false;
        if (remarks != null ? !remarks.equals(that.remarks) : that.remarks != null) return false;
        if (cache != null ? !cache.equals(that.cache) : that.cache != null) return false;
        if (minValue != null ? !minValue.equals(that.minValue) : that.minValue != null) return false;
        if (maxValue != null ? !maxValue.equals(that.maxValue) : that.maxValue != null) return false;
        if (isCycle != null ? !isCycle.equals(that.isCycle) : that.isCycle != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = sequenceCatalog != null ? sequenceCatalog.hashCode() : 0;
        result = 31 * result + (sequenceSchema != null ? sequenceSchema.hashCode() : 0);
        result = 31 * result + (sequenceName != null ? sequenceName.hashCode() : 0);
        result = 31 * result + (currentValue != null ? currentValue.hashCode() : 0);
        result = 31 * result + (increment != null ? increment.hashCode() : 0);
        result = 31 * result + (isGenerated != null ? isGenerated.hashCode() : 0);
        result = 31 * result + (remarks != null ? remarks.hashCode() : 0);
        result = 31 * result + (cache != null ? cache.hashCode() : 0);
        result = 31 * result + (minValue != null ? minValue.hashCode() : 0);
        result = 31 * result + (maxValue != null ? maxValue.hashCode() : 0);
        result = 31 * result + (isCycle != null ? isCycle.hashCode() : 0);
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
