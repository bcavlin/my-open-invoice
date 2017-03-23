package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QInvoiceItemsEntity is a Querydsl query type for InvoiceItemsEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInvoiceItemsEntity extends EntityPathBase<InvoiceItemsEntity> {

    private static final long serialVersionUID = -399366468L;

    public static final QInvoiceItemsEntity invoiceItemsEntity = new QInvoiceItemsEntity("invoiceItemsEntity");

    public final StringPath code = createString("code");

    public final StringPath description = createString("description");

    public final NumberPath<java.math.BigDecimal> hoursTotal = createNumber("hoursTotal", java.math.BigDecimal.class);

    public final NumberPath<Integer> invoiceId = createNumber("invoiceId", Integer.class);

    public final NumberPath<Integer> invoiceItemId = createNumber("invoiceItemId", Integer.class);

    public final NumberPath<java.math.BigDecimal> value = createNumber("value", java.math.BigDecimal.class);

    public QInvoiceItemsEntity(String variable) {
        super(InvoiceItemsEntity.class, forVariable(variable));
    }

    public QInvoiceItemsEntity(Path<? extends InvoiceItemsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QInvoiceItemsEntity(PathMetadata metadata) {
        super(InvoiceItemsEntity.class, metadata);
    }

}

