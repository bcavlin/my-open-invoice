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
import java.util.Arrays;

/**
 * Created by bcavlin on 06/05/17.
 */
@Entity
@Table(name = "REPORT_TEMPLATES", schema = "INVOICE", catalog = "INVOICEDB")
public class ReportTemplatesEntity {
    private Integer templateId;
    private byte[] content;
    private String templateName;

    @Id
    @GeneratedValue
    @Column(name = "TEMPLATE_ID", nullable = false)
    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    @Lob
    @Column(name = "CONTENT", nullable = false)
    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Basic
    @Column(name = "TEMPLATE_NAME", nullable = false, length = 255)
    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReportTemplatesEntity that = (ReportTemplatesEntity) o;

        if (templateId != null ? !templateId.equals(that.templateId) : that.templateId != null) return false;
        if (!Arrays.equals(content, that.content)) return false;
        if (templateName != null ? !templateName.equals(that.templateName) : that.templateName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = templateId != null ? templateId.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(content);
        result = 31 * result + (templateName != null ? templateName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ReportTemplatesEntity{" +
                "templateId=" + templateId +
                ", templateName='" + templateName + '\'' +
                '}';
    }
}
