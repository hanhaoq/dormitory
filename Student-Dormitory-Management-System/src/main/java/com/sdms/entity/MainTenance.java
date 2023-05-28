package com.sdms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
@Getter
@Setter
@QueryEntity
@Entity
@Table(name = "t_mainTenance")
@Api(value = "报修申请")
@ToString
public class MainTenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '报修申请的主键'")
    @ApiModelProperty("主键")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "varchar(255) default 'UNTREATED' comment '报修申请的状态'")
    @JsonIgnore
    private MainTenance.status status;

    @Column(nullable = false)
    @ApiModelProperty("提交日期")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '报修的具体详情'")
    @ApiModelProperty("报修描述")
    private String description;

    @Getter
    @ToString
    public enum status {

        UNTREATED(0,"未处理"),
        PROCESSED(1,"已处理");
        private final int code;

        private final String message;

        status(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public static MainTenance.status valueOfCode(int code) {
            for (MainTenance.status value : values()) {
                if (value.code == code) {
                    return value;
                }
            }
            return null;
        }
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    @JsonIgnore
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    @JsonIgnore
    private Room room;


    @Transient
    @ApiModelProperty("临时字段:处理状态编码")
    private int statusCode;

    @Transient
    @ApiModelProperty("临时字段:处理状态描述")
    private String statusMsg;

    @Transient
    @ApiModelProperty("临时字段:学生id,即学号")
    private String studentId;

    @Transient
    @ApiModelProperty("临时字段:学生姓名")
    private String studentName;

    @Transient
    @ApiModelProperty("临时字段:寝室id")
    private Long roomId;

    @Transient
    @ApiModelProperty("临时字段:寝室名称")
    private String roomName;
}
