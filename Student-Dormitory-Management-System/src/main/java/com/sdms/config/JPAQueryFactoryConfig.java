package com.sdms.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class JPAQueryFactoryConfig {

    @Resource
    @PersistenceContext
    private EntityManager entityManager;
    //EntityManager提供了一系列方法，可以执行各种数据库操作，包括插入、更新、删除和查询等。它负责实体对象的持久化、事务管理、缓存管理和查询等功能。

    @Bean(name = "queryFactory")
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
    //QueryDSL是一个用于构建和执行类型安全查询的Java库。
    // 它通过使用代码生成器和类型安全的查询构建器，使得查询编写更加直观和易于维护。
    // JPAQueryFactory是QueryDSL针对JPA（Java Persistence API）提供的一个工厂类，用于创建JPA查询。
}