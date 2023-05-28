package com.sdms.dao;

import com.sdms.entity.MainTenance;
import com.sdms.entity.RoomRequest;
import com.sdms.service.MainTenanceService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
@Repository
public interface MainTenanceDao extends JpaRepository<MainTenance, Long>, JpaSpecificationExecutor<MainTenance> {
    int countByStatusEquals(MainTenance.status mainTenanceStatus);
    @Modifying
    @Transactional
    void deleteMainTenancesByIdIn(Collection<Long> ids);


}
