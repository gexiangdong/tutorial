package cn.devmgr.tutorial;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Configuration
@MapperScan(
    annotationClass = SecondaryMapper.class,
    basePackages = "cn.devmgr",
    sqlSessionFactoryRef = "secondarySqlSessionFactory",
    sqlSessionTemplateRef = "secondarySqlSessionTemplate")
@MapperScan(
    annotationClass = Mapper.class,
    basePackages = "cn.devmgr",
    sqlSessionFactoryRef = "mainSqlSessionFactory",
    sqlSessionTemplateRef = "mainSqlSessionTemplate")
public class DatabaseConfiguration {
  private static final Logger logger = LoggerFactory.getLogger(DatabaseConfiguration.class);

  // --------------------- main -----------------------------------------

  @Bean(name = "mainDataSource")
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.main")
  public DataSource getMainServiceDataSource() {
    logger.trace("getMainServiceDataSource....");
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "mainSqlSessionFactory")
  @Primary
  public SqlSessionFactory mainSqlSessionFactory(@Qualifier("mainDataSource") DataSource dataSource)
      throws Exception {
    logger.trace("mainSqlSessionFactory....");
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    return bean.getObject();
  }

  //  @Bean(name = "mainTransactionManager")
  //  @Primary
  //  public DataSourceTransactionManager mainTransactionManager(
  //      @Qualifier("mainDataSource") DataSource dataSource) {
  //    try {
  //      logger.trace("mainTransactionManager {}", dataSource.getConnection().isReadOnly());
  //      Connection conn = dataSource.getConnection();
  //      Statement statement = conn.createStatement();
  //      ResultSet rs = statement.executeQuery("select current_database()");
  //      while (rs.next()) {
  //        logger.trace("mainTransactionManager current database: {}", rs.getString(1));
  //      }
  //      rs.close();
  //      statement.close();
  //    } catch (Exception ex) {
  //      logger.error("ERROR", ex);
  //    }
  //    return new DataSourceTransactionManager(dataSource);
  //  }

  @Bean(name = "mainSqlSessionTemplate")
  @Primary
  public SqlSessionTemplate mainSqlSessionTemplate(
      @Qualifier("mainSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    logger.trace("mainSqlSessionFactory....");
    return new SqlSessionTemplate(sqlSessionFactory);
  }

  // --------------------- secondary -------------------------------------
  @Bean(name = "secondaryDataSource")
  @ConfigurationProperties(prefix = "spring.datasource.secondary")
  public DataSource getSecondaryServiceDataSource() {
    logger.trace("getSecondaryServiceDataSource....");
    return DataSourceBuilder.create().build();
  }

  @Bean(name = "secondarySqlSessionFactory")
  public SqlSessionFactory secondarySqlSessionFactory(
      @Qualifier("secondaryDataSource") DataSource dataSource) throws Exception {
    logger.trace("secondarySqlSessionFactory....");
    SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
    bean.setDataSource(dataSource);
    bean.setMapperLocations(
        new PathMatchingResourcePatternResolver()
            .getResources("classpath*:cn/devmgr/mall/order/SecondaryOrderDao.xml"));
    return bean.getObject();
  }

  @Bean(name = "secondaryTransactionManager")
  public DataSourceTransactionManager secondaryTransactionManager(
      @Qualifier("secondaryDataSource") DataSource dataSource) {
    try {
      logger.trace("secondaryTransactionManager {}", dataSource.getConnection().isReadOnly());
      Connection conn = dataSource.getConnection();
      Statement statement = conn.createStatement();
      ResultSet rs = statement.executeQuery("select current_database()");
      while (rs.next()) {
        logger.trace("current database: {}", rs.getString(1));
      }
      rs.close();
      statement.close();
    } catch (Exception ex) {
      logger.error("ERROR", ex);
    }
    DataSourceTransactionManager tm = new DataSourceTransactionManager(dataSource);
    return tm;
  }

  @Bean(name = "secondarySqlSessionTemplate")
  public SqlSessionTemplate secondarySqlSessionTemplate(
      @Qualifier("secondarySqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
    logger.trace("secondarySqlSessionTemplate....");
    return new SqlSessionTemplate(sqlSessionFactory);
  }
}
