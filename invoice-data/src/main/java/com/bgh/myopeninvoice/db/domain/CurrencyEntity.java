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

package com.bgh.myopeninvoice.db.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * Created by bcavlin on 07/04/17.
 */
@Data
@Entity
@Table(name = "CURRENCY", schema = "INVOICE")
public class CurrencyEntity implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "CCY_ID", nullable = false)
    private Integer ccyId;

    @Basic
    @Column(name = "NAME", nullable = false, length = 10)
    private String name;

    @Basic
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    private String description;

    @OneToMany(mappedBy = "currencyByCcyId")
    private Collection<ContractEntity> contractsByCcyId;

    @OneToMany(mappedBy = "currencyByCcyId")
    private Collection<InvoiceEntity> invoicesByCcyId;


}
