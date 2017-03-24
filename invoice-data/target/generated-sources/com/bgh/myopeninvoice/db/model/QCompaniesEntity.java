package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCompaniesEntity is a Querydsl query type for CompaniesEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompaniesEntity extends EntityPathBase<CompaniesEntity> {

    private static final long serialVersionUID = -1091222792L;

    public static final QCompaniesEntity companiesEntity = new QCompaniesEntity("companiesEntity");

    public final StringPath addressLine1 = createString("addressLine1");

    public final StringPath addressLine2 = createString("addressLine2");

    public final StringPath businessNumber = createString("businessNumber");

    public final CollectionPath<CompanyContactEntity, QCompanyContactEntity> companyContactsByCompanyId = this.<CompanyContactEntity, QCompanyContactEntity>createCollection("companyContactsByCompanyId", CompanyContactEntity.class, QCompanyContactEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final ArrayPath<byte[], Byte> companyLogo = createArray("companyLogo", byte[].class);

    public final StringPath companyName = createString("companyName");

    public final CollectionPath<InvoiceEntity, QInvoiceEntity> invoicesByCompanyId = this.<InvoiceEntity, QInvoiceEntity>createCollection("invoicesByCompanyId", InvoiceEntity.class, QInvoiceEntity.class, PathInits.DIRECT2);

    public final CollectionPath<InvoiceEntity, QInvoiceEntity> invoicesByCompanyId_0 = this.<InvoiceEntity, QInvoiceEntity>createCollection("invoicesByCompanyId_0", InvoiceEntity.class, QInvoiceEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> ownedByMe = createNumber("ownedByMe", Integer.class);

    public final StringPath phone1 = createString("phone1");

    public final CollectionPath<RatesEntity, QRatesEntity> ratesByCompanyId = this.<RatesEntity, QRatesEntity>createCollection("ratesByCompanyId", RatesEntity.class, QRatesEntity.class, PathInits.DIRECT2);

    public QCompaniesEntity(String variable) {
        super(CompaniesEntity.class, forVariable(variable));
    }

    public QCompaniesEntity(Path<? extends CompaniesEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCompaniesEntity(PathMetadata metadata) {
        super(CompaniesEntity.class, metadata);
    }

}

