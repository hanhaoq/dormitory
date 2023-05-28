package com.sdms.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

/**
 * 权限，每种角色都有对应可执行操作的权限，未完成...
 */
@Getter
@Setter
@Entity
@Table(name = "t_permission")
@ApiModel("权限")
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, columnDefinition = "bigint(20) comment '权限的主键'")
    @ApiModelProperty("主键")
    private Long id;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '权限的名称'")
    @ApiModelProperty("名称")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '权限的描述'")
    @ApiModelProperty("描述")
    private String description;

    @Column(nullable = false, columnDefinition = "varchar(255) comment '权限码'")
    @ApiModelProperty("权限码")
    private String code;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(code, that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, code);
    }
}
