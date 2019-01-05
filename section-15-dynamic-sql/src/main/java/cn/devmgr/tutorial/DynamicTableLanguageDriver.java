package cn.devmgr.tutorial;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.scripting.LanguageDriver;
import org.apache.ibatis.scripting.xmltags.XMLLanguageDriver;
import org.apache.ibatis.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自定义Mybatis LanguageDriver的例子
 *
 * mybatis首先调用createSqlSource方法，创建一个SqlSource（此时我们的程序尚未对mapper里的方法调用，可认为是mybatis初始化的过程；
 * 不会传递mapper里参数的值），当我们程序调用mapper里的某个方法（方法的languageDriver是这个）会调用SqlSource.getBoundSql方法，并把具体的参数值
 * 传递给getBoundSql方法，得到SqlBound后，会调用LanguageDriver.createParameterHandler
 */
public class DynamicTableLanguageDriver extends XMLLanguageDriver implements LanguageDriver {
    private static final Logger logger = LoggerFactory.getLogger(DynamicTableLanguageDriver.class);

    @Override
    public ParameterHandler createParameterHandler(MappedStatement mappedStatement, Object o, BoundSql boundSql) {
        logger.trace("createParameterHandler({}, Object: {}, BoundSql:{}", mappedStatement, o, boundSql.getSql());
        logger.trace("Object: class:{}, isMap:{}", o.getClass().getName(), o instanceof Map);
//        logger.trace("")
        logger.trace("boundSql: class: {}, sql: {}, getParameterMappings: {}, getParameterObject:{}",
                boundSql.getClass().getName(), boundSql.getSql(), boundSql.getParameterMappings(), boundSql.getParameterObject());
        return super.createParameterHandler(mappedStatement, o, boundSql);
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, XNode xNode, Class<?> parameterType) {
        logger.trace("createSqlSource({}, XNode: {}, {}", configuration, xNode, parameterType);
        return super.createSqlSource(configuration, xNode, parameterType);
    }

    @Override
    public SqlSource createSqlSource(Configuration configuration, String script, Class<?> parameterType) {
        logger.trace("createSqlSource({}, script:{}, {}", configuration, script, parameterType);
        return new DynamicTableSqlSource(configuration);
    }


    public static class DynamicTableSqlSource implements  SqlSource{
        private Configuration configuration;

        public DynamicTableSqlSource(Configuration configuration){
            this.configuration = configuration;
        }

        @Override
        public BoundSql getBoundSql(Object o) {
            logger.trace("DynamicTableSqlSource->getBoundSql object = class:{}, data:{}", o.getClass().getName(), o);
            Map<String, Object> params = (Map<String, Object>) o;
            StringBuffer sql = new StringBuffer();
            sql.append("select ");
            List<String> columns = (List<String>) params.get("columns");
            for(int i=0; i<columns.size(); i++){
                if(i > 0){
                    sql.append(", ");
                }
                sql.append( columns.get(i));
            }
            sql.append(" from ").append(params.get("tableName"));
            Map<String, Object> cons = null;
            List<ParameterMapping> parameterMappings = new ArrayList<>();
            if(params.get("conditions") != null){
                cons = (Map<String, Object>) params.get("conditions");
                if(cons.size() > 0){
                    sql.append(" where ");
                }
                Set<String> keys = cons.keySet();
                boolean isFirst = true;
                for(String k : keys){
                    if(!isFirst){
                        sql.append(" and ");
                    }
                    isFirst = false;
                    // 这里要用JDBC的占位符号'?'，不再用mybatis #{}
                    sql.append(" ").append(k).append(" = ? ");

                    // 下面这行第二个参数，要对应mapper里面的@Param设置的参数名；
                    ParameterMapping.Builder builder = new ParameterMapping.Builder(configuration, "conditions." + k, cons.get(k).getClass());
                    parameterMappings.add(builder.build());
                }
            }
            logger.trace("will create BoundSql with {}, {}, {}", sql.toString(), parameterMappings, cons);
            return new BoundSql(configuration, sql.toString(), parameterMappings, cons);
        }
    }
}
