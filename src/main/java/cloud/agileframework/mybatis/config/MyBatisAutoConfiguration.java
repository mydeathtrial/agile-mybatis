package cloud.agileframework.mybatis.config;

import cloud.agileframework.mybatis.page.MybatisInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
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
public class MyBatisAutoConfiguration {

    @Bean
    Interceptor PageInterceptor(){
        return new MybatisInterceptor();
    }
}
