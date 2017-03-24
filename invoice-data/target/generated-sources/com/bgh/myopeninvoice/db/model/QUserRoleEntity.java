package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserRoleEntity is a Querydsl query type for UserRoleEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserRoleEntity extends EntityPathBase<UserRoleEntity> {

    private static final long serialVersionUID = -1117179510L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserRoleEntity userRoleEntity = new QUserRoleEntity("userRoleEntity");

    public final DatePath<java.util.Date> dateAssigned = createDate("dateAssigned", java.util.Date.class);

    public final QRolesEntity rolesByRoleId;

    public final NumberPath<Integer> userRoleId = createNumber("userRoleId", Integer.class);

    public final QUsersEntity usersByUserId;

    public QUserRoleEntity(String variable) {
        this(UserRoleEntity.class, forVariable(variable), INITS);
    }

    public QUserRoleEntity(Path<? extends UserRoleEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserRoleEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserRoleEntity(PathMetadata metadata, PathInits inits) {
        this(UserRoleEntity.class, metadata, inits);
    }

    public QUserRoleEntity(Class<? extends UserRoleEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.rolesByRoleId = inits.isInitialized("rolesByRoleId") ? new QRolesEntity(forProperty("rolesByRoleId")) : null;
        this.usersByUserId = inits.isInitialized("usersByUserId") ? new QUsersEntity(forProperty("usersByUserId")) : null;
    }

}

