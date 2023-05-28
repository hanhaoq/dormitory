package com.sdms.dao;

import com.sdms.entity.RoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.Collection;



/*
*  JpaSpecificationExecutor 是 Spring Data JPA 中提供的一个接口，
*  它扩展了 JpaRepository 接口，并且提供了一些基于条件查询的方法，可以方便地实现复杂的查询操作。
   具体来说，JpaSpecificationExecutor 接口中提供了一个 Specification 类型的参数，它表示查询条件，
*  开发者可以通过自定义 Specification 类型的实现类，实现复杂的查询条件。Specification 接口中定义了两个方法：
   Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb)
   * ：根据查询条件构建 Predicate 对象，用于过滤查询结果。
   default <S> List<S> findAll( Specification<S> spec, Sort sort)：根据指定的查询条件和排序方式查询所有符合条件的实体对象。
   通过 JpaSpecificationExecutor 提供的方法，可以基于条件查询实现更灵活、更高效的数据查询。
*
*
*
*
*
*
* */
public interface RoomRequestDao extends JpaRepository<RoomRequest, Long>, JpaSpecificationExecutor<RoomRequest> {

    int countByStatusEquals(RoomRequest.RoomRequestStatus roomRequestStatus);

    @Modifying
    @Transactional
    void deleteRoomRequestsByIdIn(Collection<Long> ids);

}
