package com.agile.repository;

import cloud.agileframework.mybatis.page.MybatisPage;
import cloud.agileframework.mybatis.page.Page;
import com.agile.TestService;
import com.agile.repository.entity.SysApiEntity;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author 佟盟
 * 日期 2020/8/7 16:51
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@Mapper
public interface MyRepository {

    @Update({"drop table if exists SYS_API;",
            "create table SYS_API\n" +
            "(\n" +
            "    SYS_API_ID    INT not null,\n" +
            "    NAME          VARCHAR2,\n" +
            "    BUSINESS_NAME VARCHAR2,\n" +
            "    BUSINESS_CODE VARCHAR2,\n" +
            "    REMARKS       TEXT,\n" +
            "    TYPE          VARCHAR,\n" +
            "    constraint SYS_API_PK\n" +
            "        primary key (SYS_API_ID)\n" +
            ")"})
    void create();

    @Select("<script> select * from sys_api </script>")
    List<SysApiEntity> queryAll();

    @Select("<script> select * from sys_api </script>")
    Page<SysApiEntity> page(MybatisPage pageInfo);

    @Select("<script> select * from sys_api where business_name like #{param}</script>")
    Page<SysApiEntity> page2(TestService.CustomPage pageInfo);

    @Select("<script> select * from sys_api where sys_api_id = #{param}</script>")
    SysApiEntity findOne(@Param("param") String id);

    @Select("<script> select '{\"nickname\": \"goodspeed\", \"avatar\": \"avatar_url\", \"tags\": [\"python\", \"golang\", \"db\"]}'::jsonb->>'nickname' as nickname</script>")
    List<Map<String,Object>> findOne2();

    SysApiEntity findOne3(@Param("param") String id);


    @Select("SELECT * FROM sys_api where sys_api_id = #{id}")
    @Results({
            @Result(property = "tudou1",  column = "name"),
    })
    SysApiEntity testResult(Long id);

//    @Options(useGeneratedKeys=true, keyProperty="sysApiId", keyColumn="sys_api_id") 支持主键自增数据库
    @Insert("INSERT INTO sys_api(sys_api_id,business_code,business_name,name,type,remarks) VALUES(#{sysApiId}, #{businessCode},#{businessName},#{name}, #{type}, #{remarks})")
    void insert(SysApiEntity sysApiEntity);

    @Update("UPDATE sys_api SET name=#{name} WHERE sys_api_id =#{sysApiId}")
    void update(SysApiEntity sysApiEntity);

    @Delete("<script> DELETE FROM sys_api <where> " +
            " 1=1" +
            "<if test=\"id != null\"> and sys_api_id=#{id}</if> " +
            "</where> </script> ")
    void delete(Long id);

}
