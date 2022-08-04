package com.agile;

import cloud.agileframework.mybatis.page.MybatisPage;
import cloud.agileframework.mybatis.page.Page;
import com.agile.repository.MyRepository;
import com.agile.repository.entity.SysApiEntity;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.dynamic.sql.SqlBuilder;
import org.mybatis.dynamic.sql.SqlColumn;
import org.mybatis.dynamic.sql.SqlTable;
import org.mybatis.dynamic.sql.select.SelectModel;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.IntStream;

/**
 * @author 佟盟
 * 日期 2020/8/7 16:47
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestService {
    private final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private MyRepository repository;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Before
    public void init() {
        repository.create();
        IntStream.range(1, 31).forEach(this::insert);
    }

    @After
    public void after() {
        System.out.println("222222");
        delete();
    }

    @Test
    public void testAll() {
        List<SysApiEntity> list = repository.queryAll();
        Assert.assertEquals(list.size(), 30);
    }

    /**
     * 查询分页
     */
    @Test
    public void testPage() {
        Page<SysApiEntity> page = repository.page(new MybatisPage() {
            @Override
            public int getPageNum() {
                return 2;
            }

            @Override
            public int getPageSize() {
                return 3;
            }
        });
        Assert.assertEquals(page.getTotal(), 30);
        Assert.assertEquals(page.getContent().size(), 3);
        Assert.assertEquals((long) page.getContent().get(0).getSysApiId(), 4l);
        Assert.assertEquals((long) page.getContent().get(1).getSysApiId(), 5l);
        Assert.assertEquals((long) page.getContent().get(2).getSysApiId(), 6l);
    }

    /**
     * 查询自定义分页
     */
    @Test
    public void testPage2() {
        CustomPage customPage = new CustomPage("测试1%", 1, 2);
        Page<SysApiEntity> page = repository.page2(customPage);
        Assert.assertEquals(page.getTotal(), 11);
        Assert.assertEquals(page.getContent().size(), 2);
        Assert.assertEquals((long) page.getContent().get(0).getSysApiId(), 1);
        Assert.assertEquals((long) page.getContent().get(1).getSysApiId(), 10);
    }


    /**
     * 查询单个结果映射
     */
    @Test
    public void findOne() {
        SysApiEntity entity = repository.findOne("1");
        Assert.assertEquals(entity.getType(), "001");
    }

    /**
     * xml查询单个结果映射
     */
    @Test
    public void findOne3() {
        SysApiEntity entity = repository.findOne3("1");
        Assert.assertEquals(entity.getType(), "001");
        Assert.assertEquals(entity.getTudou(), entity.getName());
    }

    /**
     * xml查询单个结果映射
     */
    @Test
    public void testResult() {
        SysApiEntity entity = repository.testResult(1L);
        Assert.assertEquals(entity.getType(), "001");
        Assert.assertEquals(entity.getTudou1(), entity.getName());
    }

    public void insert(long id) {
        String type = "00" + id;
        repository.insert(SysApiEntity.builder()
                .name("test")
                .sysApiId(id)
                .businessName("测试" + id)
                .businessCode("code_" + id)
                .remarks("备注")
                .type(type)
                .build());
        SysApiEntity entity = repository.findOne(id + "");
        Assert.assertEquals(entity.getType(), type);
        Assert.assertEquals(entity.getTudou(), entity.getName());
    }

    @Test
    public void update() {
        repository.update(SysApiEntity.builder()
                .name("111")
                .sysApiId(1L)
                .build());
        SysApiEntity entity = repository.findOne("1");
        Assert.assertEquals(entity.getType(), "001");
        Assert.assertEquals(entity.getTudou(), entity.getName());
    }

    @Test
    public void deleteTest() {
        repository.delete(1L);
        SysApiEntity entity = repository.findOne("1");
        Assert.assertNull(entity);
    }

    public void delete() {
        repository.delete(null);
        SysApiEntity entity = repository.findOne("1");
        Assert.assertNull(entity);
    }

    /**
     * 自定义分页，并扩展参数，实现MybatisPage接口
     */
    public static class CustomPage implements MybatisPage {
        private final String param;
        private final int page;
        private final int size;

        public CustomPage(String param, int page, int size) {
            this.param = param;
            this.page = page;
            this.size = size;
        }

        public String getParam() {
            return param;
        }

        @Override
        public int getPageNum() {
            return page;
        }

        @Override
        public int getPageSize() {
            return size;
        }
    }

    @Test
    public void sy() {
        SqlTable table = SqlTable.of("sys_api");
        SqlColumn<String> id = SqlColumn.of("id", table);
        SqlColumn<String> name = SqlColumn.of("name", table);

        SelectModel selectStatement = SqlBuilder.select(id, name)
                .from(table)
                .where(id, SqlBuilder.isIn("1", "5", "7"))
                .and(id, SqlBuilder.isIn("2", "6", "8"), SqlBuilder.and(name, SqlBuilder.isLike("%bat")))
                .or(id, SqlBuilder.isGreaterThan("60"))
                .orderBy(id.descending(), name)
                .build();
        System.out.println();
    }
}
