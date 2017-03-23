package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRatesEntity is a Querydsl query type for RatesEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRatesEntity extends EntityPathBase<RatesEntity> {

    private static final long serialVersionUID = 717177776L;

    public static final QRatesEntity ratesEntity = new QRatesEntity("ratesEntity");

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final NumberPath<Integer> rateForUser = createNumber("rateForUser", Integer.class);

    public final NumberPath<Integer> rateId = createNumber("rateId", Integer.class);

    public final NumberPath<java.math.BigDecimal> ratePerDay = createNumber("ratePerDay", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> ratePerHour = createNumber("ratePerHour", java.math.BigDecimal.class);

    public final DatePath<java.util.Date> validFrom = createDate("validFrom", java.util.Date.class);

    public final DatePath<java.util.Date> validTo = createDate("validTo", java.util.Date.class);

    public QRatesEntity(String variable) {
        super(RatesEntity.class, forVariable(variable));
    }

    public QRatesEntity(Path<? extends RatesEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRatesEntity(PathMetadata metadata) {
        super(RatesEntity.class, metadata);
    }

}

