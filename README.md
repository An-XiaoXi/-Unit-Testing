> 有信心未必会赢，没信心一定会输！ 成功不是必然的，但努力是必需的。
>
> 加油！😃

<img style="margin-left: 100px;width: 150px;" src="https://i03piccdn.sogoucdn.com/aac9c5a137dabe23" ></img>



## 废话不多说 干就完了



> CTRL + SHIFT + T ，快速生成测试类



SpringBoot 嵌套 SpringBootTest 单元测试，大多数开发人员使用 spring-boot-starter-test，它同时导入 SpringBoot 测试模块以及JUnit Jupiter、AssertJ、Hamcrest 和许多其他有用的库，能够帮助我们测试自己的代码是否可用。



### 1、导入 `Pom`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!-- 模块版本 -->
    <modelVersion>4.0.0</modelVersion>

    <!-- 版本控制器 -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.3.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <!-- GAV 坐标 -->
    <groupId>com.example</groupId>
    <artifactId>demo</artifactId>
    <version>0.0.1-SNAPSHOT</version>

    <!-- 项目描述 -->
    <name>demo</name>
    <description>Demo project for Spring Boot</description>

    <!-- JDK 版本 -->
    <properties>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>

        <!-- WEB 启动器 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <!-- 测试组件 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
            <exclusions>
                <exclusion>
                    <groupId>org.junit.vintage</groupId>
                    <artifactId>junit-vintage-engine</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Maven 插件 -->
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```





### 2、实际操作

嵌套新的 POM ，以便我们做查询测试

```xml
<!-- MySQL -->
<dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.21</version>
</dependency>

<!-- MyBaits -->
<dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>2.1.3</version>
</dependency>
```





##### 实体类

需要准备一个实体类，也可以使用 lombok 插件

```java
package com.example.demo.entity;

public class Admin {
    private Integer adminid;

    private String adminname;

    private String sex;

    private String tel;

    private String email;

    private String pwd;

    private String cardid;

    private String role;
	
    // GET / SET 省略 。。。
}
```



##### `Dao` 层

这里的话，我就直接用 MyBiats 的注解了哈 😃

```java
package com.example.demo.mapper;

import com.example.demo.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 标识为 Dao 层
@Mapper
public interface AdminMapper {
	
    // 使用注解查询
    @Select("select * from admin")
    List<Admin> FindAll();

    int deleteByPrimaryKey(Integer adminid);

    int insert(Admin record);

    int insertSelective(Admin record);

    Admin selectByPrimaryKey(Integer adminid);

    int updateByPrimaryKeySelective(Admin record);

    int updateByPrimaryKey(Admin record);
}
```



##### `SpringBoot` 启动器

```java
package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// 扫描 Dao 层
@MapperScan("com.example.demo.mapper")
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```



`SpringBootTest` 测试类

```java
package com.example.demo;

import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class DemoApplicationTests {

    /* Dao 层接口 */
    @Autowired
    private AdminMapper adminMapper;

    @Test
    void contextLoads() {
        
        List<Admin> admins = adminMapper.FindAll();

        System.out.println(admins);
    }

}
```





### 3、执行

控制台如下：

```
[Admin{adminid=9527, adminname='超级管理员', sex='男', tel='13658377857', email='1253838283@qq.com', pwd='123456', cardid='3132', role='0'}]
```



## 源代码：

`GitHub` 地址：https://github.com/An-XiaoXi/-Unit-Testing

