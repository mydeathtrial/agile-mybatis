package com.agile;

import cloud.agileframework.mybatis.page.MybatisPage;
import com.agile.repository.MyRepository;
import com.agile.repository.entity.SysApiEntity;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 佟盟
 * 日期 2020/8/7 16:47
 * 描述 TODO
 * @version 1.0
 * @since 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class TestService {
    private final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired
    private MyRepository repository;

    /**
     * 查询分页
     */
    @Test
    public void testPage() {
        List<SysApiEntity> list = repository.page(new MybatisPage() {
            @Override
            public int getPageNum() {
                return 1;
            }

            @Override
            public int getPageSize() {
                return 2;
            }
        });
        logger.info(JSON.toJSONString(list, true));
    }

    /**
     * 查询自定义分页
     */
    @Test
    public void testPage2() {
        CustomPage customPage = new CustomPage("428477999713751040", 1, 2);
        List<SysApiEntity> list = repository.page2(customPage);
        logger.info(JSON.toJSONString(list, true));
    }

    /**
     * 查询单个结果映射
     */
    @Test
    public void findOne() {
        SysApiEntity entity = repository.findOne("428477999713751040");
        logger.info(JSON.toJSONString(entity, true));
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
}
