package cn.devmgr.tutorial;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


/**
 * 此配置实现了这种需求： 系统内有一个主数据库，配置在application.yml中turtorial.datasource下；
 * 此数据源内有一张表，表内记载若干数据库的信息（dataSourceClassName, serverName, port, dbName ...等）
 * 每个请求根据request.getHeader("customerId")的不同，而动态选择表内的一个数据源。
 * 每个请求会在DbFilter中设置ThreadLocalContext来标记当前的customerId。
 * 在此类提供的DynamicDataSource中根据ThreadLocalContext中的值来决定使用哪个数据源
 *
 * 如果在一个请求中，需要使用不同的数据源，在service中，调用其他数据源前，可以用用ThreadLocalContent.setDbKey()来切换
 */
@Configuration
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${tutorial.datasource.className}") String dataSourceClassName;
    @Value("${tutorial.datasource.serverName}") String dataSourceServerName;
    @Value("${tutorial.datasource.portNumber}") int dataSourcePortNumber;
    @Value("${tutorial.datasource.dataBaseName}") String dataSourceDataBaseName;
    @Value("${tutorial.datasource.user}") String dataSourceUser;
    @Value("${tutorial.datasource.password}") String dataSourcePassword;


    public class DynamicDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            String key = ThreadLocalContext.getDbKey();
            DataSourceConfig.this.logger.trace("determineCurrentLookupKey...{}", key);
            if(key == null){
                RuntimeException re = new RuntimeException("无法确定使用哪个DataSource，在ThreadLocalContext未找到设置");
                logger.error(re);
                throw re;
            }
            return key;
        }

    }


    @Bean
    @Primary
    public SqlSessionFactoryBean sqlSessionFactory() {
        logger.trace("sqlSessionFactory()");
        DataSource ds = dynamicDataSource();
        SqlSessionFactoryBean sfb = new SqlSessionFactoryBean();
        sfb.setDataSource(ds);
        return sfb;
    }

    @Bean
    public DynamicDataSource dynamicDataSource() {
        logger.trace("DynamicDataSource()....");
        try {
            DynamicDataSource dds = new DynamicDataSource();
            dds.setTargetDataSources(createTargetDataSources());
            return dds;
        }catch(Exception e){
            logger.error("创建动态数据源失败", e);
            throw new RuntimeException(e);
        }
    }


    private DataSource createMainDataSource(){
        logger.trace("mainDataSource: {}, {}, {}, {}, {}, {}", dataSourceClassName, dataSourceServerName,
                dataSourcePortNumber, dataSourceDataBaseName, dataSourceUser, dataSourcePassword);
        DataSource main = createDataSource(dataSourceClassName, dataSourceServerName, dataSourcePortNumber,
                dataSourceDataBaseName, dataSourceUser, dataSourcePassword);
        return main;
    }

    private Map<Object, Object> createTargetDataSources() throws SQLException {
        logger.trace("createTargetDataSources");
        Map<Object, Object> map = new HashMap<>();

        //把所有的数据源都放入到map
        DataSource main = createMainDataSource();
        map.put("main", main);

        Connection conn = main.getConnection();
        Statement stat = conn.createStatement();
        ResultSet rs = stat.executeQuery("select id, dataSourceClassName, dataSourceUser, dataSourcePassword, " +
                "dataSourceDataBaseName, dataSourcePortNumber, dataSourceServerName from customer ");
        while(rs.next()){
            String id = rs.getString("id");
            String className = rs.getString("dataSourceClassName");
            String serverName = rs.getString("dataSourceServerName");
            int port = rs.getInt("dataSourcePortNumber");
            String dbName = rs.getString("dataSourceDataBaseName");
            String user = rs.getString("dataSourceUser");
            String password = rs.getString("dataSourcePassword");
            logger.trace("adding datasource {} {}", id, dbName);
            map.put(id, createDataSource(className, serverName, port, dbName, user, password));
        }
        rs.close();
        stat.close();
        conn.close();
        return map;
    }

    private DataSource createDataSource(String className, String serverName, int port, String dbName,
                                        String user, String password) {
        logger.trace("createDataSource({}, {}, {}, {}, {}, {})", className, serverName, port, dbName, user, password);
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", className);
        props.setProperty("dataSource.serverName", serverName);
        if(port > 0) {
            props.setProperty("dataSource.portNumber", String.valueOf(port));
        }
        props.setProperty("dataSource.databaseName", dbName);
        if(user != null) {
            props.setProperty("dataSource.user", user);
        }
        if(password != null) {
            props.setProperty("dataSource.password", password);
        }
        props.put("dataSource.logWriter", new PrintWriter(System.out));

        HikariConfig config = new HikariConfig(props);
        config.setMaximumPoolSize(15);  //连接池最多15个连接

        HikariDataSource ds = new HikariDataSource(config);

        return ds;
    }

}
