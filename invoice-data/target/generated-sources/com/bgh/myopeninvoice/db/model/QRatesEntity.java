package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRatesEntity is a Querydsl query type for RatesEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRatesEntity extends EntityPathBase<RatesEntity> {

    private static final long serialVersionUID = 717177776L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRatesEntity ratesEntity = new QRatesEntity("ratesEntity");

    public final QCompaniesEntity companiesByCompanyId;

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final CollectionPath<InvoiceEntity, QInvoiceEntity> invoicesByRateId = this.<InvoiceEntity, QInvoiceEntity>createCollection("invoicesByRateId", InvoiceEntity.class, QInvoiceEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> rateForUser = createNumber("rateForUser", Integer.class);

    public final NumberPath<Integer> rateId = createNumber("rateId", Integer.class);

    public final NumberPath<java.math.BigDecimal> ratePerDay = createNumber("ratePerDay", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> ratePerHour = createNumber("ratePerHour", java.math.BigDecimal.class);

    public final QUsersEntity usersByRateForUser;

    public final DatePath<java.util.Date> validFrom = createDate("validFrom", java.util.Date.class);

    public final DatePath<java.util.Date> validTo = createDate("validTo", java.util.Date.class);

    public QRatesEntity(String variable) {
        this(RatesEntity.class, forVariable(variable), INITS);
    }

    public QRatesEntity(Path<? extends RatesEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRatesEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRatesEntity(PathMetadata metadata, PathInits inits) {
        this(RatesEntity.class, metadata, inits);
    }

    public QRatesEntity(Class<? extends RatesEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.companiesByCompanyId = inits.isInitialized("companiesByCompanyId") ? new QCompaniesEntity(forProperty("companiesByCompanyId")) : null;
        this.usersByRateForUser = inits.isInitialized("usersByRateForUser") ? new QUsersEntity(forProperty("usersByRateForUser")) : null;
    }

}

