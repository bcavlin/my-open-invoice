package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttachmentEntity is a Querydsl query type for AttachmentEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachmentEntity extends EntityPathBase<AttachmentEntity> {

    private static final long serialVersionUID = 486608076L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttachmentEntity attachmentEntity = new QAttachmentEntity("attachmentEntity");

    public final NumberPath<Integer> attachmentId = createNumber("attachmentId", Integer.class);

    public final ArrayPath<byte[], Byte> file = createArray("file", byte[].class);

    public final StringPath filename = createString("filename");

    public final QInvoiceEntity invoiceByInvoiceId;

    public final NumberPath<Integer> invoiceId = createNumber("invoiceId", Integer.class);

    public QAttachmentEntity(String variable) {
        this(AttachmentEntity.class, forVariable(variable), INITS);
    }

    public QAttachmentEntity(Path<? extends AttachmentEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttachmentEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttachmentEntity(PathMetadata metadata, PathInits inits) {
        this(AttachmentEntity.class, metadata, inits);
    }

    public QAttachmentEntity(Class<? extends AttachmentEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.invoiceByInvoiceId = inits.isInitialized("invoiceByInvoiceId") ? new QInvoiceEntity(forProperty("invoiceByInvoiceId"), inits.get("invoiceByInvoiceId")) : null;
    }

}

