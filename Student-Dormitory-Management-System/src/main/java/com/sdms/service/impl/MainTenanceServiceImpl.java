package com.sdms.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sdms.common.page.Page;
import com.sdms.common.page.PageRequest;
import com.sdms.common.result.OperationResult;
import com.sdms.dao.MainTenanceDao;
import com.sdms.entity.MainTenance;
import com.sdms.entity.RoomRequest;
import com.sdms.qmodel.QMainTenance;
import com.sdms.qmodel.QRoom;
import com.sdms.qmodel.QStudent;
import com.sdms.service.MainTenanceService;
import com.sdms.service.RoomService;
import com.sdms.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.sdms.common.result.OperationResult.failure;
import static com.sdms.common.result.OperationResult.success;
import static com.sdms.common.util.StringUtils.*;
import static com.sdms.entity.MainTenance.status.*;



@Service
@Slf4j
public class MainTenanceServiceImpl implements MainTenanceService {
    @Resource
    private MainTenanceDao mainTenanceDao;

    @Resource
    private StudentService studentService;

    @Resource
    private RoomService roomService;

    @Resource
    private JPAQueryFactory queryFactory;


    @Override
    public void fillTransientFields(MainTenance mainTenance) {
        val room = mainTenance.getRoom();
        if (room != null) {
            mainTenance.setRoomId(room.getId());
            mainTenance.setRoomName(room.getName());
        }
        val status = mainTenance.getStatus();
        if (status != null) {
            mainTenance.setStatusMsg(status.getMessage());
            mainTenance.setStatusCode(status.getCode());
        }
        val student = mainTenance.getStudent();
        if (student != null) {
            mainTenance.setStudentId(student.getId());
            mainTenance.setStudentName(student.getName());
        }
    }



    @Override
    public int getNoHandleCount() {
        return mainTenanceDao.countByStatusEquals(MainTenance.status.PROCESSED);
    }

    @Override
    public List<MainTenance.status> listAllStatuses() {
        return Arrays.asList(MainTenance.status.values());
    }

    @Override
    public Page<MainTenance> fetchPage(PageRequest pageRequest) {
        boolean vague = pageRequest.getQueryType() == 0; // 0 表示模糊查询
        val param = pageRequest.getParam();
        val mainTenanceId = parseLong(param.get("mainTenanceId"));
        val keyWord = trimAllWhitespace(param.get("keyWord"));
        val mstatus = parseLong(param.get("status"));
        val mainTenance = QMainTenance.mainTenance;
        val room = QRoom.room;
        val student = QStudent.student;
        // 动态拼接查询条件
        val condition = new BooleanBuilder();
        if (mstatus != null) {
            val mainTenanceStatus = valueOfCode(mstatus.intValue());
            if (mainTenanceStatus != null) {
                condition.and(mainTenance.status.eq(mainTenanceStatus));
            }
        }
        val subCondition = new BooleanBuilder();
        if (mainTenanceId != null) {
            subCondition.or(mainTenance.id.eq(mainTenanceId));
        }
        if (!isBlankOrNull(keyWord)) {
            if (vague) {
                // 模糊查询
                subCondition.or(room.name.like("%" + keyWord + "%"));
                subCondition.or(student.name.like("%" + keyWord + "%"));
            } else {
                // 精确查询
                subCondition.or(room.name.eq(keyWord));
                subCondition.or(student.name.eq(keyWord));
            }
        }
        condition.and(subCondition);
        val query = queryFactory
                .select(Projections.bean(MainTenance.class,
                        mainTenance.id,
                        mainTenance.status,
                        mainTenance.description,
                        mainTenance.date,
                        room.id.as("roomId"),
                        room.name.as("roomName"),
                        student.id.as("studentId"),
                        student.name.as("studentName")))
                .from(mainTenance)
                .leftJoin(room).on(mainTenance.room.id.eq(room.id))
                .leftJoin(student).on(mainTenance.student.id.eq(student.id))
                .orderBy(mainTenance.id.asc())
                .where(condition)
                .offset((pageRequest.getPage() - 1) * pageRequest.getLimit()).limit(pageRequest.getLimit());
        // 执行分页查询
        val result = query.fetchResults();
        result.getResults().forEach(this::fillTransientFields);
        return Page.of(result);
    }

    @Override
    public MainTenance getMainTenanceById(Long id) {
        val optional = mainTenanceDao.findById(id);
        if (optional.isPresent()) {
            val mainTenance = optional.get();
            this.fillTransientFields(mainTenance);
            return mainTenance;
        }
        return null;
    }

    @Override
    public OperationResult<MainTenance> saveMainTenance(MainTenance mainTenance) {
        if (mainTenance == null) {
            return failure("报修申请为null");
        }
        val s = mainTenance.getStudent();
        if (s == null || isBlankOrNull(s.getId())) {
            return failure("没有选择学生");
        }
        val r = mainTenance.getRoom();
        if (r == null || r.getId() == null) {
            return failure("没有选择寝室");
        }
        val d=mainTenance.getDescription();
        if(d==null || d.equals("")){
            return failure("没有报修描述");
        }
        mainTenance.setStatus(MainTenance.status.valueOfCode(mainTenance.getStatusCode()));
        return success(mainTenanceDao.save(mainTenance));
    }

    @Override
    public OperationResult<String> deleteMainTenanceByIds(Collection<Long> ids) {
        try {
            mainTenanceDao.deleteMainTenancesByIdIn(ids);
        } catch (Exception e) {
            log.error("报修申请删除失败,ids=" + ids + ",error is " + e.getMessage());
            return failure("删除失败");
        }
        return success("删除成功");
    }

    @Override
    public OperationResult<MainTenance> newMainTenance(String studentId, Long roomId,String description) {
        val s = studentService.getStudentById(studentId);
        val r = roomService.getRoomById(roomId);
        if (s != null && r != null) {
            val mainTenance = new MainTenance();
            mainTenance.setRoom(r);
            mainTenance.setStudent(s);
            mainTenance.setStatus(UNTREATED);
            mainTenance.setDescription(description);
            mainTenance.setDate(new Date());
            MainTenance rq;
            try {
                rq = mainTenanceDao.save(mainTenance);
            } catch (Exception e) {
                log.error("新增请求失败, error=" + e.getMessage());
                return failure("失败");
            }
            return success(rq);
        } else {
            return failure("参数异常");
        }
    }



}
