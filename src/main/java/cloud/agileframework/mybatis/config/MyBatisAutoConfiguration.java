package cloud.agileframework.mybatis.config;

import cloud.agileframework.common.util.object.ObjectUtil;
import cloud.agileframework.mybatis.page.CustomConfiguration;
import cloud.agileframework.mybatis.page.MybatisInterceptor;
import com.alibaba.druid.DbType;
import com.alibaba.druid.util.JdbcUtils;
import org.apache.ibatis.plugin.Interceptor;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 描述：
 * <p>创建时间：2018/12/14<br>
 *
 * @author 佟盟
 * @version 1.0
 * @since 1.0
 */
@Configuration
@ConditionalOnClass({DataSource.class})
@AutoConfigureBefore(MybatisAutoConfiguration.class)
public class MyBatisAutoConfiguration {

    public MyBatisAutoConfiguration(MybatisProperties properties) {
        org.apache.ibatis.session.Configuration configuration = properties.getConfiguration();
        CustomConfiguration customConfiguration = new CustomConfiguration();
        if (configuration != null) {
            ObjectUtil.copyProperties(configuration, customConfiguration, new String[]{"mapperRegistry", "typeHandlerRegistry"}, ObjectUtil.ContainOrExclude.EXCLUDE);
        }

        properties.setConfiguration(customConfiguration);
    }

    @Bean
    Interceptor PageInterceptor(DataSourceProperties dataSourceProperties) {
        DbType dbtype = JdbcUtils.getDbTypeRaw(dataSourceProperties.getUrl(), dataSourceProperties.getDriverClassName());
        MybatisInterceptor mybatisInterceptor = new MybatisInterceptor();
        mybatisInterceptor.setType(dbtype);
        return mybatisInterceptor;
    }
}
