package com.bgh.myopeninvoice.api.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AttachmentDTO implements java.io.Serializable {

    private Integer attachmentId;

    private Integer invoiceId;

    private Integer contentId;

    private InvoiceDTO invoiceByInvoiceId;

    private ContentDTO contentByContentId;

}
