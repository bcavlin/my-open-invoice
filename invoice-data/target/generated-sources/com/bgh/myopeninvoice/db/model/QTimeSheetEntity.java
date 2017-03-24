package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTimeSheetEntity is a Querydsl query type for TimeSheetEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTimeSheetEntity extends EntityPathBase<TimeSheetEntity> {

    private static final long serialVersionUID = 474779247L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTimeSheetEntity timeSheetEntity = new QTimeSheetEntity("timeSheetEntity");

    public final NumberPath<java.math.BigDecimal> hoursWorked = createNumber("hoursWorked", java.math.BigDecimal.class);

    public final NumberPath<Integer> invoiceItemId = createNumber("invoiceItemId", Integer.class);

    public final QInvoiceItemsEntity invoiceItemsByInvoiceItemId;

    public final DatePath<java.util.Date> itemDate = createDate("itemDate", java.util.Date.class);

    public final NumberPath<Integer> timesheetId = createNumber("timesheetId", Integer.class);

    public QTimeSheetEntity(String variable) {
        this(TimeSheetEntity.class, forVariable(variable), INITS);
    }

    public QTimeSheetEntity(Path<? extends TimeSheetEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTimeSheetEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTimeSheetEntity(PathMetadata metadata, PathInits inits) {
        this(TimeSheetEntity.class, metadata, inits);
    }

    public QTimeSheetEntity(Class<? extends TimeSheetEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.invoiceItemsByInvoiceItemId = inits.isInitialized("invoiceItemsByInvoiceItemId") ? new QInvoiceItemsEntity(forProperty("invoiceItemsByInvoiceItemId"), inits.get("invoiceItemsByInvoiceItemId")) : null;
    }

}

