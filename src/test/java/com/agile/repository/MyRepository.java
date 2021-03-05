package com.agile.repository;

import cloud.agileframework.mybatis.page.MybatisPage;
import cloud.agileframework.mybatis.page.Page;
import com.agile.TestService;
import com.agile.repository.entity.SysApiEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 佟盟
 * 日期 2020/8/7 16:51
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Mapper
public interface MyRepository {

    @Select("<script> select * from sys_api </script>")
    List<SysApiEntity> queryAll();

    @Select("<script> select * from sys_api </script>")
    Page<SysApiEntity> page(MybatisPage pageInfo);

    @Select("<script> select * from sys_api where sys_api_id = #{param}</script>")
    Page<SysApiEntity> page2(TestService.CustomPage pageInfo);

    @Select("<script> select * from sys_api where sys_api_id = #{param}</script>")
    SysApiEntity findOne(@Param("param") String id);
}
