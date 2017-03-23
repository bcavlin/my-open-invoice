package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTimeSheetEntity is a Querydsl query type for TimeSheetEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTimeSheetEntity extends EntityPathBase<TimeSheetEntity> {

    private static final long serialVersionUID = 474779247L;

    public static final QTimeSheetEntity timeSheetEntity = new QTimeSheetEntity("timeSheetEntity");

    public final NumberPath<java.math.BigDecimal> hoursWorked = createNumber("hoursWorked", java.math.BigDecimal.class);

    public final NumberPath<Integer> invoiceItemId = createNumber("invoiceItemId", Integer.class);

    public final DatePath<java.util.Date> itemDate = createDate("itemDate", java.util.Date.class);

    public final NumberPath<Integer> timesheetId = createNumber("timesheetId", Integer.class);

    public QTimeSheetEntity(String variable) {
        super(TimeSheetEntity.class, forVariable(variable));
    }

    public QTimeSheetEntity(Path<? extends TimeSheetEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTimeSheetEntity(PathMetadata metadata) {
        super(TimeSheetEntity.class, metadata);
    }

}

