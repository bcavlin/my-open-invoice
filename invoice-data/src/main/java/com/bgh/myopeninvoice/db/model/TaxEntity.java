/*
 * Copyright 2017 Branislav Cavlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bgh.myopeninvoice.db.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by bcavlin on 14/03/17.
 */
@Entity
@Table(name = "TAX", schema = "INVOICE", catalog = "INVOICEDB")
public class TaxEntity implements Serializable {
    private Integer taxId;
    private BigDecimal percent;
    private String identifier;

    @Id
    @GeneratedValue
    @Column(name = "TAX_ID", nullable = false)
    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    @Basic
    @Column(name = "PERCENT", nullable = false, precision = 4)
    public BigDecimal getPercent() {
        return percent;
    }

    public void setPercent(BigDecimal percent) {
        this.percent = percent;
    }

    @Basic
    @Column(name = "IDENTIFIER", nullable = false, length = 50)
    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaxEntity taxEntity = (TaxEntity) o;

        if (taxId != null ? !taxId.equals(taxEntity.taxId) : taxEntity.taxId != null) return false;
        if (percent != null ? !percent.equals(taxEntity.percent) : taxEntity.percent != null) return false;
        if (identifier != null ? !identifier.equals(taxEntity.identifier) : taxEntity.identifier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = taxId != null ? taxId.hashCode() : 0;
        result = 31 * result + (percent != null ? percent.hashCode() : 0);
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TaxEntity{" +
                "taxId=" + taxId +
                ", percent=" + percent +
                ", identifier='" + identifier + '\'' +
                '}';
    }
}
