# agile-mybatis ： Mybatis扩展
[![spring-boot](https://img.shields.io/badge/Spring--boot-LATEST-green)](https://img.shields.io/badge/spring-LATEST-green)
[![maven](https://img.shields.io/badge/build-maven-green)](https://img.shields.io/badge/build-maven-green)
## 它有什么作用

* **结果POJO映射**
查询返回结果可以直接声明为POJO或POJO集合，其映射过程是依赖于cloud.agileframework:common-util对象深度转换器实现，所以支持
识别驼峰与下划线等风格属性的互转。只需声明，无需额外调用，以最低的代码入侵实现类似ORM映射效果。

* **分页拦截器**
分页方式是通过扩展Mybatis拦截器，当入参中包含接口MybatisPage类（及其实现类）且返回参数为Page<T>形式时触发分页拦截器，解析总条数
total及页内容content。只需声明，无需额外调用，以最低的代码入侵实现Mybatis分页。

* **低代码入侵**
不改变任何Mybatis原生编码方式及能力
-------
## 快速入门
开始你的第一个项目是非常容易的。

#### 步骤 1: 下载包
您可以从[最新稳定版本]下载包(https://github.com/mydeathtrial/agile-mybatis/releases).
该包已上传至maven中央仓库，可在pom中直接声明引用

以版本agile-mybatis-0.1.jar为例。
#### 步骤 2: 添加maven依赖
```xml
        <dependency>
            <groupId>cloud.agileframework</groupId>
            <artifactId>agile-mybatis</artifactId>
            <version>0.1</version>
        </dependency>
```
#### 步骤 3: 开箱即用
##### 接口定义，支持Mybatis原生方式，要注意接口上需要使用`@Mapper`注解，且声明为spring bean，这里使用`@Component`声明
```java
@Component
@Mapper
public interface MyRepository {
    //结果直接映射为POJO集合
    @Select("<script> select * from sys_api </script>")
    List<SysApiEntity> queryAll();

    //分页
    @Select("<script> select * from sys_api </script>")
    Page<SysApiEntity> page(MybatisPage pageInfo);

    //自定义分页实现，并扩展参数
    @Select("<script> select * from sys_api where sys_api_id = #{param}</script>")
    Page<SysApiEntity> page2(TestService.CustomPage pageInfo);

    //结果映射为单一POJO对象
    @Select("<script> select * from sys_api where sys_api_id = #{param}</script>")
    SysApiEntity findOne(@Param("param") String id);
}
```
##### API调用，调用过程与调用普通spring bean形式相同，写操作需添加事务注解，此处不做过多描述，可参照spring与mybatis官方文档
```java
@Service
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
```