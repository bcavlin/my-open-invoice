package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCompaniesEntity is a Querydsl query type for CompaniesEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCompaniesEntity extends EntityPathBase<CompaniesEntity> {

    private static final long serialVersionUID = -1091222792L;

    public static final QCompaniesEntity companiesEntity = new QCompaniesEntity("companiesEntity");

    public final StringPath addressLine1 = createString("addressLine1");

    public final StringPath addressLine2 = createString("addressLine2");

    public final StringPath businessName = createString("businessName");

    public final StringPath businessNumber = createString("businessNumber");

    public final NumberPath<Integer> companyId = createNumber("companyId", Integer.class);

    public final ArrayPath<byte[], Byte> companyLogo = createArray("companyLogo", byte[].class);

    public final StringPath companyName = createString("companyName");

    public final NumberPath<Integer> ownedByMe = createNumber("ownedByMe", Integer.class);

    public final StringPath phone1 = createString("phone1");

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

