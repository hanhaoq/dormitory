package com.sdms.qmodel;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.sdms.entity.MainTenance;

import com.sdms.qmodel.QRoom;
import com.sdms.qmodel.QStudent;
import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QMainTenance is a Querydsl query type for MainTenance
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMainTenance extends EntityPathBase<MainTenance> {

    private static final long serialVersionUID = 1609576306L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMainTenance mainTenance = new QMainTenance("mainTenance");

    public final DateTimePath<java.util.Date> date = createDateTime("date", java.util.Date.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.sdms.qmodel.QRoom room;

    public final EnumPath<MainTenance.status> status = createEnum("status", MainTenance.status.class);

    public final com.sdms.qmodel.QStudent student;

    public QMainTenance(String variable) {
        this(MainTenance.class, forVariable(variable), INITS);
    }

    public QMainTenance(Path<? extends MainTenance> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMainTenance(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMainTenance(PathMetadata metadata, PathInits inits) {
        this(MainTenance.class, metadata, inits);
    }

    public QMainTenance(Class<? extends MainTenance> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.room = inits.isInitialized("room") ? new QRoom(forProperty("room"), inits.get("room")) : null;
        this.student = inits.isInitialized("student") ? new QStudent(forProperty("student"), inits.get("student")) : null;
    }

}

