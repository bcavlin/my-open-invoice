package com.bgh.myopeninvoice.db.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRolesEntity is a Querydsl query type for RolesEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRolesEntity extends EntityPathBase<RolesEntity> {

    private static final long serialVersionUID = -459277478L;

    public static final QRolesEntity rolesEntity = new QRolesEntity("rolesEntity");

    public final NumberPath<Integer> roleId = createNumber("roleId", Integer.class);

    public final StringPath roleName = createString("roleName");

    public final ListPath<UserRoleEntity, QUserRoleEntity> userRoleEntity = this.<UserRoleEntity, QUserRoleEntity>createList("userRoleEntity", UserRoleEntity.class, QUserRoleEntity.class, PathInits.DIRECT2);

    public QRolesEntity(String variable) {
        super(RolesEntity.class, forVariable(variable));
    }

    public QRolesEntity(Path<? extends RolesEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRolesEntity(PathMetadata metadata) {
        super(RolesEntity.class, metadata);
    }

}

