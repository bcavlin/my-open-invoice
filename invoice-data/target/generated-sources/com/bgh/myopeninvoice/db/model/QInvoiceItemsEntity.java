package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvoiceItemsEntity is a Querydsl query type for InvoiceItemsEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QInvoiceItemsEntity extends EntityPathBase<InvoiceItemsEntity> {

    private static final long serialVersionUID = -399366468L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvoiceItemsEntity invoiceItemsEntity = new QInvoiceItemsEntity("invoiceItemsEntity");

    public final NumberPath<Integer> ccy = createNumber("ccy", Integer.class);

    public final StringPath code = createString("code");

    public final QCurrencyEntity currencyByCcy;

    public final StringPath description = createString("description");

    public final QInvoiceEntity invoiceByInvoiceId;

    public final NumberPath<Integer> invoiceId = createNumber("invoiceId", Integer.class);

    public final NumberPath<Integer> invoiceItemId = createNumber("invoiceItemId", Integer.class);

    public final NumberPath<java.math.BigDecimal> quantity = createNumber("quantity", java.math.BigDecimal.class);

    public final CollectionPath<TimeSheetEntity, QTimeSheetEntity> timeSheetsByInvoiceItemId = this.<TimeSheetEntity, QTimeSheetEntity>createCollection("timeSheetsByInvoiceItemId", TimeSheetEntity.class, QTimeSheetEntity.class, PathInits.DIRECT2);

    public final StringPath unit = createString("unit");

    public final NumberPath<java.math.BigDecimal> value = createNumber("value", java.math.BigDecimal.class);

    public QInvoiceItemsEntity(String variable) {
        this(InvoiceItemsEntity.class, forVariable(variable), INITS);
    }

    public QInvoiceItemsEntity(Path<? extends InvoiceItemsEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvoiceItemsEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvoiceItemsEntity(PathMetadata metadata, PathInits inits) {
        this(InvoiceItemsEntity.class, metadata, inits);
    }

    public QInvoiceItemsEntity(Class<? extends InvoiceItemsEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.currencyByCcy = inits.isInitialized("currencyByCcy") ? new QCurrencyEntity(forProperty("currencyByCcy")) : null;
        this.invoiceByInvoiceId = inits.isInitialized("invoiceByInvoiceId") ? new QInvoiceEntity(forProperty("invoiceByInvoiceId"), inits.get("invoiceByInvoiceId")) : null;
    }

}

