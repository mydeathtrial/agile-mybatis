package cloud.agileframework.mybatis.page;

import java.io.Serializable;

/**
 * @author 佟盟
 * 日期 2019/3/13 18:38
 * 描述 分页数据模型
 * @version 1.0
 * @since 1.0
 */
public interface MybatisPage extends Serializable {
    /**
     * 页号
     *
     * @return 页号
     */
    int getPageNum();

    /**
     * 页大小
     *
     * @return 大小
     */
    int getPageSize();
}
