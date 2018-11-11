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
import java.math.BigDecimal;

/**
 * Created by bcavlin on 14/03/17.
 */
@Data
@Entity
@Table(name = "TAX", schema = "INVOICE")
public class TaxEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAX_ID")
    private Integer taxId;

    @Basic
    @Column(name = "PERCENT", nullable = false, precision = 4)
    private BigDecimal percent;

    @Basic
    @Column(name = "IDENTIFIER", nullable = false, length = 50)
    private String identifier;

}
