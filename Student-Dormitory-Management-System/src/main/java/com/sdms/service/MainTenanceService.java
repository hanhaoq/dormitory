package com.sdms.service;

import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.entity.MainTenance;
import com.sdms.entity.RoomRequest;

import java.util.Collection;
import java.util.List;

public interface MainTenanceService extends BaseEntityService<MainTenance> {
    int getNoHandleCount();

    List<MainTenance.status> listAllStatuses();

    Page<MainTenance> fetchPage(PageRequest pageRequest);

    MainTenance getMainTenanceById(Long id);

    OperationResult<MainTenance> saveMainTenance(MainTenance mainTenance);

    OperationResult<String> deleteMainTenanceByIds(Collection<Long> ids);

    OperationResult<MainTenance> newMainTenance(String studentId, Long roomId,String description);
}
