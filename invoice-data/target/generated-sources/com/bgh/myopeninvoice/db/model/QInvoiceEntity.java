package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvoiceEntity is a Querydsl query type for InvoiceEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInvoiceEntity extends EntityPathBase<InvoiceEntity> {

    private static final long serialVersionUID = 1673555274L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvoiceEntity invoiceEntity = new QInvoiceEntity("invoiceEntity");

    public final CollectionPath<AttachmentEntity, QAttachmentEntity> attachmentsByInvoiceId = this.<AttachmentEntity, QAttachmentEntity>createCollection("attachmentsByInvoiceId", AttachmentEntity.class, QAttachmentEntity.class, PathInits.DIRECT2);

    public final QCompaniesEntity companiesByCompanyFrom;

    public final QCompaniesEntity companiesByCompanyTo;

    public final NumberPath<Integer> companyFrom = createNumber("companyFrom", Integer.class);

    public final NumberPath<Integer> companyTo = createNumber("companyTo", Integer.class);

    public final DatePath<java.util.Date> createdDate = createDate("createdDate", java.util.Date.class);

    public final DatePath<java.util.Date> dueDate = createDate("dueDate", java.util.Date.class);

    public final DatePath<java.util.Date> fromDate = createDate("fromDate", java.util.Date.class);

    public final NumberPath<Integer> invoiceId = createNumber("invoiceId", Integer.class);

    public final CollectionPath<InvoiceItemsEntity, QInvoiceItemsEntity> invoiceItemsesByInvoiceId = this.<InvoiceItemsEntity, QInvoiceItemsEntity>createCollection("invoiceItemsesByInvoiceId", InvoiceItemsEntity.class, QInvoiceItemsEntity.class, PathInits.DIRECT2);

    public final StringPath note = createString("note");

    public final DatePath<java.util.Date> paidDate = createDate("paidDate", java.util.Date.class);

    public final NumberPath<Integer> rate = createNumber("rate", Integer.class);

    public final QRatesEntity ratesByRate;

    public final QTaxEntity taxByTaxId;

    public final NumberPath<Integer> taxId = createNumber("taxId", Integer.class);

    public final NumberPath<java.math.BigDecimal> taxPercent = createNumber("taxPercent", java.math.BigDecimal.class);

    public final StringPath title = createString("title");

    public final DatePath<java.util.Date> toDate = createDate("toDate", java.util.Date.class);

    public QInvoiceEntity(String variable) {
        this(InvoiceEntity.class, forVariable(variable), INITS);
    }

    public QInvoiceEntity(Path<? extends InvoiceEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvoiceEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvoiceEntity(PathMetadata metadata, PathInits inits) {
        this(InvoiceEntity.class, metadata, inits);
    }

    public QInvoiceEntity(Class<? extends InvoiceEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.companiesByCompanyFrom = inits.isInitialized("companiesByCompanyFrom") ? new QCompaniesEntity(forProperty("companiesByCompanyFrom")) : null;
        this.companiesByCompanyTo = inits.isInitialized("companiesByCompanyTo") ? new QCompaniesEntity(forProperty("companiesByCompanyTo")) : null;
        this.ratesByRate = inits.isInitialized("ratesByRate") ? new QRatesEntity(forProperty("ratesByRate"), inits.get("ratesByRate")) : null;
        this.taxByTaxId = inits.isInitialized("taxByTaxId") ? new QTaxEntity(forProperty("taxByTaxId")) : null;
    }

}

