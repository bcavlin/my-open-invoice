package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QInvoiceEntity is a Querydsl query type for InvoiceEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInvoiceEntity extends EntityPathBase<InvoiceEntity> {

    private static final long serialVersionUID = 1673555274L;

    public static final QInvoiceEntity invoiceEntity = new QInvoiceEntity("invoiceEntity");

    public final NumberPath<Integer> companyFrom = createNumber("companyFrom", Integer.class);

    public final NumberPath<Integer> companyTo = createNumber("companyTo", Integer.class);

    public final DatePath<java.util.Date> createdDate = createDate("createdDate", java.util.Date.class);

    public final DatePath<java.util.Date> dueDate = createDate("dueDate", java.util.Date.class);

    public final DatePath<java.util.Date> fromDate = createDate("fromDate", java.util.Date.class);

    public final NumberPath<Integer> invoiceId = createNumber("invoiceId", Integer.class);

    public final StringPath note = createString("note");

    public final DatePath<java.util.Date> paidDate = createDate("paidDate", java.util.Date.class);

    public final NumberPath<Integer> rate = createNumber("rate", Integer.class);

    public final NumberPath<Integer> taxId = createNumber("taxId", Integer.class);

    public final NumberPath<java.math.BigDecimal> taxPercent = createNumber("taxPercent", java.math.BigDecimal.class);

    public final StringPath title = createString("title");

    public final DatePath<java.util.Date> toDate = createDate("toDate", java.util.Date.class);

    public QInvoiceEntity(String variable) {
        super(InvoiceEntity.class, forVariable(variable));
    }

    public QInvoiceEntity(Path<? extends InvoiceEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInvoiceEntity(PathMetadata metadata) {
        super(InvoiceEntity.class, metadata);
    }

}

