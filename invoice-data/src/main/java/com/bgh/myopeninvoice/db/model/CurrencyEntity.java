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
import java.util.Collection;

/**
 * Created by bcavlin on 07/04/17.
 */
@Entity
@Table(name = "CURRENCY", schema = "INVOICE", catalog = "INVOICEDB")
public class CurrencyEntity implements Serializable {
    private Integer ccyId;
    private String name;
    private String description;
    private Collection<ContractsEntity> contractsByCcyId;
    private Collection<InvoiceEntity> invoicesByCcyId;
    private Collection<InvoiceItemsEntity> invoiceItemsByCcyId;

    @Id
    @GeneratedValue
    @Column(name = "CCY_ID", nullable = false)
    public Integer getCcyId() {
        return ccyId;
    }

    public void setCcyId(Integer ccyId) {
        this.ccyId = ccyId;
    }

    @Basic
    @Column(name = "NAME", nullable = false, length = 10)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyEntity that = (CurrencyEntity) o;

        if (ccyId != null ? !ccyId.equals(that.ccyId) : that.ccyId != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ccyId != null ? ccyId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "currencyByCcyId")
    public Collection<ContractsEntity> getContractsByCcyId() {
        return contractsByCcyId;
    }

    public void setContractsByCcyId(Collection<ContractsEntity> contractsByCcyId) {
        this.contractsByCcyId = contractsByCcyId;
    }

    @OneToMany(mappedBy = "currencyByCcyId")
    public Collection<InvoiceEntity> getInvoicesByCcyId() {
        return invoicesByCcyId;
    }

    public void setInvoicesByCcyId(Collection<InvoiceEntity> invoicesByCcyId) {
        this.invoicesByCcyId = invoicesByCcyId;
    }

    @Override
    public String toString() {
        return "CurrencyEntity{" +
                "ccyId=" + ccyId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
