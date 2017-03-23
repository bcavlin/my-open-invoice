package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTaxEntity is a Querydsl query type for TaxEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTaxEntity extends EntityPathBase<TaxEntity> {

    private static final long serialVersionUID = -500821944L;

    public static final QTaxEntity taxEntity = new QTaxEntity("taxEntity");

    public final StringPath identifier = createString("identifier");

    public final NumberPath<java.math.BigDecimal> percent = createNumber("percent", java.math.BigDecimal.class);

    public final NumberPath<Integer> taxId = createNumber("taxId", Integer.class);

    public QTaxEntity(String variable) {
        super(TaxEntity.class, forVariable(variable));
    }

    public QTaxEntity(Path<? extends TaxEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTaxEntity(PathMetadata metadata) {
        super(TaxEntity.class, metadata);
    }

}

