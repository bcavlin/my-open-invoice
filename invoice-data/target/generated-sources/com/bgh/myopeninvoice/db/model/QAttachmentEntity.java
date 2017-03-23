package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttachmentEntity is a Querydsl query type for AttachmentEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAttachmentEntity extends EntityPathBase<AttachmentEntity> {

    private static final long serialVersionUID = 486608076L;

    public static final QAttachmentEntity attachmentEntity = new QAttachmentEntity("attachmentEntity");

    public final NumberPath<Integer> attachmentId = createNumber("attachmentId", Integer.class);

    public final ArrayPath<byte[], Byte> file = createArray("file", byte[].class);

    public final StringPath filename = createString("filename");

    public final NumberPath<Integer> invoiceId = createNumber("invoiceId", Integer.class);

    public QAttachmentEntity(String variable) {
        super(AttachmentEntity.class, forVariable(variable));
    }

    public QAttachmentEntity(Path<? extends AttachmentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttachmentEntity(PathMetadata metadata) {
        super(AttachmentEntity.class, metadata);
    }

}

