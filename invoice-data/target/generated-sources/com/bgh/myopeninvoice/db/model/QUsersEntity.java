package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUsersEntity is a Querydsl query type for UsersEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUsersEntity extends EntityPathBase<UsersEntity> {

    private static final long serialVersionUID = -1392241051L;

    public static final QUsersEntity usersEntity = new QUsersEntity("usersEntity");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath firstName = createString("firstName");

    public final DatePath<java.util.Date> lastLogged = createDate("lastLogged", java.util.Date.class);

    public final StringPath lastName = createString("lastName");

    public final StringPath middleName = createString("middleName");

    public final StringPath password = createString("password");

    public final NumberPath<Integer> userId = createNumber("userId", Integer.class);

    public final StringPath username = createString("username");

    public final ListPath<UserRoleEntity, QUserRoleEntity> userRoleEntities = this.<UserRoleEntity, QUserRoleEntity>createList("userRoleEntities", UserRoleEntity.class, QUserRoleEntity.class, PathInits.DIRECT2);

    public QUsersEntity(String variable) {
        super(UsersEntity.class, forVariable(variable));
    }

    public QUsersEntity(Path<? extends UsersEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUsersEntity(PathMetadata metadata) {
        super(UsersEntity.class, metadata);
    }

}

