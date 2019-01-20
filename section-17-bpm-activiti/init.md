# 搭建项目环境

本节使用的是spring boot 2.1.2， activiti 6.0.0 （两者都是目前最高版本），而activiti-spring-boot 依旧在使用spring boot 1.x 版本中的spring security 4.x， spring boot 2.x 则集成了spring security 5.x，两者有些差异，因此本节有些内容是为了消除这些差异而写的代码/设置； 在将来activiti升级后应该不再需要。



## POM

增加依赖并设置版本

```xml
  <properties>
    <!-- 增加下面这行 -->
    <activiti.version>6.0.0</activiti.version>
  </properties>
  
  
  <dependencies>
 
    <!-- 增加下面这2个 -->
    <dependency>
      <groupId>org.activiti</groupId>
      <artifactId>activiti-spring-boot-starter-basic</artifactId>
      <version>${activiti.version}</version>
    </dependency>
    <dependency>
      <groupId>org.activiti</groupId>
      <artifactId>activiti-spring-boot-starter-rest-api</artifactId>
      <version>${activiti.version}</version>
    </dependency>

    <!-- 增加一个数据库的驱动，也可以是其他数据库，并在application.yml配置数据源 -->
    <dependency>
      <groupId>org.postgresql</groupId>
      <artifactId>postgresql</artifactId>
      <version>42.2.5</version>
    </dependency>

  </dependencies>
  
```


### 在src/main/resources目录创建配置

在src/main/resources目录下创建一个 processes 目录，并且放置一个 .bmpn 的流程配置文件（文件名任意），如果没有这个文件，启动时会报错。

      Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.activiti.spring.SpringProcessEngineConfiguration]: Factory method 'springProcessEngineConfiguration' threw exception; nested exception is java.io.FileNotFoundException: class path resource [processes/] cannot be resolved to URL because it does not exist
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:579) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        ... 59 common frames omitted
      Caused by: java.io.FileNotFoundException: class path resource [processes/] cannot be resolved to URL because it does not exist
        at org.springframework.core.io.ClassPathResource.getURL(ClassPathResource.java:195) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.core.io.support.PathMatchingResourcePatternResolver.findPathMatchingResources(PathMatchingResourcePatternResolver.java:495) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.core.io.support.PathMatchingResourcePatternResolver.getResources(PathMatchingResourcePatternResolver.java:296) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.getResources(AbstractApplicationContext.java:1305) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.support.GenericApplicationContext.getResources(GenericApplicationContext.java:238) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.activiti.spring.boot.AbstractProcessEngineConfiguration.discoverProcessDefinitionResources(AbstractProcessEngineConfiguration.java:79) ~[activiti-spring-boot-starter-basic-6.0.0.jar:na]
        at org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration.baseSpringProcessEngineConfiguration(AbstractProcessEngineAutoConfiguration.java:74) ~[activiti-spring-boot-starter-basic-6.0.0.jar:na]
        at org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration$DataSourceProcessEngineConfiguration.springProcessEngineConfiguration(DataSourceProcessEngineAutoConfiguration.java:57) ~[activiti-spring-boot-starter-basic-6.0.0.jar:na]
        at org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration$DataSourceProcessEngineConfiguration$$EnhancerBySpringCGLIB$$30cb9c3e.CGLIB$springProcessEngineConfiguration$1(<generated>) ~[activiti-spring-boot-starter-basic-6.0.0.jar:na]
        at org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration$DataSourceProcessEngineConfiguration$$EnhancerBySpringCGLIB$$30cb9c3e$$FastClassBySpringCGLIB$$9fbdb05e.invoke(<generated>) ~[activiti-spring-boot-starter-basic-6.0.0.jar:na]
        at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:228) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:361) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration$DataSourceProcessEngineConfiguration$$EnhancerBySpringCGLIB$$30cb9c3e.springProcessEngineConfiguration(<generated>) ~[activiti-spring-boot-starter-basic-6.0.0.jar:na]
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_131]
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_131]
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_131]
        at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_131]
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154) ~[spring-beans-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        ... 60 common frames omitted

无法启动


### 修改启动注解 @SpringBootAppliction

从 `@SpringBootApplication` 改为 

```Java
@SpringBootApplication(exclude=org.activiti.spring.boot.SecurityAutoConfiguration.class)
```
这是为了禁止 activiti 的自动配置的安全机制，否则会出现如下错误而无法启动.


    org.springframework.beans.factory.BeanDefinitionStoreException: Failed to process import candidates for configuration class [cn.devmgr.tutorial.Application]; nested exception is java.io.FileNotFoundException: class path resource [org/springframework/security/config/annotation/authentication/configurers/GlobalAuthenticationConfigurerAdapter.class] cannot be opened because it does not exist
        at org.springframework.context.annotation.ConfigurationClassParser.processImports(ConfigurationClassParser.java:616) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.processDeferredImportSelectors(ConfigurationClassParser.java:548) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.parse(ConfigurationClassParser.java:184) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassPostProcessor.processConfigBeanDefinitions(ConfigurationClassPostProcessor.java:316) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassPostProcessor.postProcessBeanDefinitionRegistry(ConfigurationClassPostProcessor.java:233) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanDefinitionRegistryPostProcessors(PostProcessorRegistrationDelegate.java:273) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(PostProcessorRegistrationDelegate.java:93) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.invokeBeanFactoryPostProcessors(AbstractApplicationContext.java:693) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:531) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:140) ~[spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:752) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:388) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:327) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1246) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1234) [spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at cn.devmgr.tutorial.Application.main(Application.java:15) [classes/:na]
    Caused by: java.io.FileNotFoundException: class path resource [org/springframework/security/config/annotation/authentication/configurers/GlobalAuthenticationConfigurerAdapter.class] cannot be opened because it does not exist
        at org.springframework.core.io.ClassPathResource.getInputStream(ClassPathResource.java:180) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.core.type.classreading.SimpleMetadataReader.<init>(SimpleMetadataReader.java:51) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.core.type.classreading.SimpleMetadataReaderFactory.getMetadataReader(SimpleMetadataReaderFactory.java:103) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory.createMetadataReader(ConcurrentReferenceCachingMetadataReaderFactory.java:88) ~[spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory.getMetadataReader(ConcurrentReferenceCachingMetadataReaderFactory.java:75) ~[spring-boot-2.0.0.RELEASE.jar:2.0.0.RELEASE]
        at org.springframework.core.type.classreading.SimpleMetadataReaderFactory.getMetadataReader(SimpleMetadataReaderFactory.java:81) ~[spring-core-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.asSourceClass(ConfigurationClassParser.java:699) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser$SourceClass.getSuperClass(ConfigurationClassParser.java:869) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.doProcessConfigurationClass(ConfigurationClassParser.java:326) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.processConfigurationClass(ConfigurationClassParser.java:241) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.processMemberClasses(ConfigurationClassParser.java:355) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.doProcessConfigurationClass(ConfigurationClassParser.java:261) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.processConfigurationClass(ConfigurationClassParser.java:241) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassParser.processImports(ConfigurationClassParser.java:606) ~[spring-context-5.0.4.RELEASE.jar:5.0.4.RELEASE]
        ... 15 common frames omitted


出现上面的错误，是因为spring boot 2.0使用spring security 5.x；而activiti使用spring security 4.x。
而spring security 4.x -> 5.x的升级中有些package名字改变了。

如果需要使用 Activiti 的安全机制，可参考[https://www.baeldung.com/activiti-spring-security](https://www.baeldung.com/activiti-spring-security)进行配置。


### 设置Spring security

需要设置 spring security, 并增加注解 `@EnableGlobalMethodSecurity(prePostEnabled = true)`

如果没有设置 `prePostEnabled = true` 会出现如下异常，不能启动

    org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'org.springframework.hateoas.config.HateoasConfiguration': Initialization of bean failed; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'metaDataSourceAdvisor': Cannot resolve reference to bean 'methodSecurityMetadataSource' while setting constructor argument; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'methodSecurityMetadataSource' defined in class path resource [org/springframework/security/config/annotation/method/configuration/GlobalMethodSecurityConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.security.access.method.MethodSecurityMetadataSource]: Factory method 'methodSecurityMetadataSource' threw exception; nested exception is java.lang.IllegalStateException: In the composition of all global method configuration, no annotation support was actually activated
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:584) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:498) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:320) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:318) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:392) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1288) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1127) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:538) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:498) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:320) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:318) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:204) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.context.support.PostProcessorRegistrationDelegate.registerBeanPostProcessors(PostProcessorRegistrationDelegate.java:240) ~[spring-context-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.registerBeanPostProcessors(AbstractApplicationContext.java:707) ~[spring-context-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:531) ~[spring-context-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:142) ~[spring-boot-2.1.2.RELEASE.jar:2.1.2.RELEASE]
        at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:775) [spring-boot-2.1.2.RELEASE.jar:2.1.2.RELEASE]
        at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:397) [spring-boot-2.1.2.RELEASE.jar:2.1.2.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:316) [spring-boot-2.1.2.RELEASE.jar:2.1.2.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1260) [spring-boot-2.1.2.RELEASE.jar:2.1.2.RELEASE]
        at org.springframework.boot.SpringApplication.run(SpringApplication.java:1248) [spring-boot-2.1.2.RELEASE.jar:2.1.2.RELEASE]
        at cn.devmgr.tutorial.bpm.Application.main(Application.java:23) [classes/:na]
    Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'metaDataSourceAdvisor': Cannot resolve reference to bean 'methodSecurityMetadataSource' while setting constructor argument; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'methodSecurityMetadataSource' defined in class path resource [org/springframework/security/config/annotation/method/configuration/GlobalMethodSecurityConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.security.access.method.MethodSecurityMetadataSource]: Factory method 'methodSecurityMetadataSource' threw exception; nested exception is java.lang.IllegalStateException: In the composition of all global method configuration, no annotation support was actually activated
        at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:378) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveValueIfNecessary(BeanDefinitionValueResolver.java:110) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.ConstructorResolver.resolveConstructorArguments(ConstructorResolver.java:662) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:188) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1308) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1154) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:538) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:498) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:320) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:318) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:204) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.aop.framework.autoproxy.BeanFactoryAdvisorRetrievalHelper.findAdvisorBeans(BeanFactoryAdvisorRetrievalHelper.java:91) ~[spring-aop-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator.findCandidateAdvisors(AbstractAdvisorAutoProxyCreator.java:109) ~[spring-aop-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator.findEligibleAdvisors(AbstractAdvisorAutoProxyCreator.java:94) ~[spring-aop-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.aop.framework.autoproxy.AbstractAdvisorAutoProxyCreator.getAdvicesAndAdvisorsForBean(AbstractAdvisorAutoProxyCreator.java:76) ~[spring-aop-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:349) ~[spring-aop-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:301) ~[spring-aop-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:434) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1749) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:576) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        ... 24 common frames omitted
    Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'methodSecurityMetadataSource' defined in class path resource [org/springframework/security/config/annotation/method/configuration/GlobalMethodSecurityConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.security.access.method.MethodSecurityMetadataSource]: Factory method 'methodSecurityMetadataSource' threw exception; nested exception is java.lang.IllegalStateException: In the composition of all global method configuration, no annotation support was actually activated
        at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:627) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.ConstructorResolver.instantiateUsingFactoryMethod(ConstructorResolver.java:456) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.instantiateUsingFactoryMethod(AbstractAutowireCapableBeanFactory.java:1288) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1127) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:538) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:498) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:320) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:318) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.BeanDefinitionValueResolver.resolveReference(BeanDefinitionValueResolver.java:367) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        ... 44 common frames omitted
    Caused by: org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.security.access.method.MethodSecurityMetadataSource]: Factory method 'methodSecurityMetadataSource' threw exception; nested exception is java.lang.IllegalStateException: In the composition of all global method configuration, no annotation support was actually activated
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:185) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.beans.factory.support.ConstructorResolver.instantiate(ConstructorResolver.java:622) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        ... 54 common frames omitted
    Caused by: java.lang.IllegalStateException: In the composition of all global method configuration, no annotation support was actually activated
        at org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration.methodSecurityMetadataSource(GlobalMethodSecurityConfiguration.java:368) ~[spring-security-config-5.1.3.RELEASE.jar:5.1.3.RELEASE]
        at org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration$$EnhancerBySpringCGLIB$$9ca90329.CGLIB$methodSecurityMetadataSource$10(<generated>) ~[spring-security-config-5.1.3.RELEASE.jar:5.1.3.RELEASE]
        at org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration$$EnhancerBySpringCGLIB$$9ca90329$$FastClassBySpringCGLIB$$80529ebe.invoke(<generated>) ~[spring-security-config-5.1.3.RELEASE.jar:5.1.3.RELEASE]
        at org.springframework.cglib.proxy.MethodProxy.invokeSuper(MethodProxy.java:244) ~[spring-core-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.context.annotation.ConfigurationClassEnhancer$BeanMethodInterceptor.intercept(ConfigurationClassEnhancer.java:363) ~[spring-context-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        at org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration$$EnhancerBySpringCGLIB$$9ca90329.methodSecurityMetadataSource(<generated>) ~[spring-security-config-5.1.3.RELEASE.jar:5.1.3.RELEASE]
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_131]
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_131]
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_131]
        at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_131]
        at org.springframework.beans.factory.support.SimpleInstantiationStrategy.instantiate(SimpleInstantiationStrategy.java:154) ~[spring-beans-5.1.4.RELEASE.jar:5.1.4.RELEASE]
        ... 55 common frames omitted


## 数据库内的表

  正常启动后，会在数据库内创建28张以 act_ 开头的表

### 表的分类

数据库内表都以 act_xx_yyy 的形式命名，xx是分类
  The second part is a two-character identification of 
  the use case of the table. This use case will also roughly match the service API.

* ACT_RE_*: RE stands for repository. Tables with this prefix contain static information such as process definitions and process resources (images, rules, etc.).
* ACT_RU_*: RU stands for runtime. These are the runtime tables that contain the runtime data of process instances, user tasks, variables, jobs, etc. Activiti only stores the runtime data during process instance execution, and removes the records when a process instance ends. This keeps the runtime tables small and fast.
* ACT_ID_*: ID stands for identity. These tables contain identity information, such as users, groups, etc.
* ACT_HI_*: HI stands for history. These are the tables that contain historic data, such as past process instances, variables, tasks, etc.
* ACT_GE_*: general data, which is used in various use cases.


