# REST Docs

```java
@AutoConfigureMockMvc     //自动配置mockMvc，缺少这个注解会无法@Autowired MockMvc类
@AutoConfigureRestDocs    //自动配置RestDocs，缺少这个注解且没手工配置，会导致出现空指针异常
```