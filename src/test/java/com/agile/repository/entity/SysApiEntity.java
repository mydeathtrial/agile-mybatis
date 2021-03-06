package com.agile.repository.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：[系统管理]目标任务表
 *
 * @author agile gennerator
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@TableName("sys_api")
public class SysApiEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Long sysApiId;
    private String name;
    private Boolean type;
    //    private String typeText;
    private String businessName;
    private String businessCode;
    private String remarks;
}
