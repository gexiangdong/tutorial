# REST Docs

RESTDocs 是通过编写测试用例的方式，来制作文档。当测试用例都通过测试后，会按照测试用例的内容来生成adoc格式的文档。

不需要对 API 做任何形式的修改，pom中增加的restdocs 的依赖scope都是test，也不会对生产环境产生影响。

## pom 

### 增加依赖

```xml
<dependency>
    <groupId>org.springframework.restdocs</groupId>
    <artifactId>spring-restdocs-mockmvc</artifactId>
    <scope>test</scope>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
    <exclusions>
        <exclusion>
            <groupId>org.junit.vintage</groupId>
            <artifactId>junit-vintage-engine</artifactId>
        </exclusion>
        <exclusion>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

排除掉spring-bootstarter-test中的junit相关依赖是因为他们的版本较低， restdocs 中包含版本更高的junit，restdocs也需要这些高版本的junit。


### 增加插件

```xml
<plugin>
    <groupId>org.asciidoctor</groupId>
    <artifactId>asciidoctor-maven-plugin</artifactId>
    <version>1.5.3</version>
    <executions>
        <execution>
            <id>generate-docs</id>
            <phase>prepare-package</phase>
            <goals>
                <goal>process-asciidoc</goal>
            </goals>
            <configuration>
                <backend>html</backend>
                <doctype>book</doctype>
            </configuration>
        </execution>
    </executions>
    <dependencies>
        <dependency>
            <groupId>org.springframework.restdocs</groupId>
            <artifactId>spring-restdocs-asciidoctor</artifactId>
            <version>${spring-restdocs.version}</version>
        </dependency>
    </dependencies>
</plugin>
```


## 测试用例

具体可参照 [src/test/java/cn/devmgr/tutorial/restdoc](src/test/java/cn/devmgr/tutorial/restdoc)目录下的测试用例。


```java
@AutoConfigureMockMvc     //自动配置mockMvc，缺少这个注解会无法@Autowired MockMvc类
@AutoConfigureRestDocs    //自动配置RestDocs，缺少这个注解且没手工配置，会导致出现空指针异常
```
## 生成文档

如果测试用例都写好后，运行 `mvn package` 如果测试用例都通过，会自动生成文档，文档在 target/generated-snippets 目录下。

这个目录下的文档都是adoc格式的。

