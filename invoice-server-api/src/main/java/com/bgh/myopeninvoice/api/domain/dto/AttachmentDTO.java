package com.bgh.myopeninvoice.api.domain.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class AttachmentDTO implements java.io.Serializable {

    private Integer attachmentId;

    private Integer invoiceId;

    private Integer contentId;

    private InvoiceDTO invoiceByInvoiceId;

    private ContentDTO contentByContentId;

}
