package com.sdms.qmodel;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;
import com.sdms.entity.Student;
import com.sdms.entity.TeachingClass;




/**
 * QTeachingClass is a Querydsl query type for TeachingClass
 *
 *
 *
 *
 * 在使用 Querydsl 进行查询操作时，Querydsl 需要通过生成的 Q 类来映射实体类的属性和表达式，以便在查询中进行条件拼接、排序等操作。
 *
 * 原始的实体类（例如 TeachingClass）通常不包含 Querydsl 相关的注解和方法，因此无法直接在查询中使用实体类的属性和方法进行条件拼接。
 *
 * 通过生成的 Q 类（例如 QTeachingClass），Querydsl 自动生成了与实体类对应的查询路径和属性表达式，使得我们可以方便地在查询中引用实体类的属性，
 *
 * 并进行条件拼接、排序等操作。
 *
 * 所以，为了使用 Querydsl 进行灵活的查询操作，需要使用生成的 Q 类来映射实体类的属性和表达式，而不是直接使用原始的实体类。
 *
 * 这样可以让我们更好地利用 Querydsl 提供的查询能力和语法，以提高查询的灵活性和表达能力。
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeachingClass extends EntityPathBase<TeachingClass> {

    private static final long serialVersionUID = -546241814L;

    public static final QTeachingClass teachingClass = new QTeachingClass("teachingClass");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public final SetPath<Student, QStudent> students = this.<Student, QStudent>createSet("students", Student.class, QStudent.class, PathInits.DIRECT2);

    public QTeachingClass(String variable) {
        super(TeachingClass.class, forVariable(variable));
    }

    public QTeachingClass(Path<? extends TeachingClass> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTeachingClass(PathMetadata metadata) {
        super(TeachingClass.class, metadata);
    }

}

