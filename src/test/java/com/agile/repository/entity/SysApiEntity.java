package com.agile.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class SysApiEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long sysApiId;
    private String name;
    private Boolean type;
    private String typeText;
    private String businessName;
    private String businessCode;
    private String remarks;
}
