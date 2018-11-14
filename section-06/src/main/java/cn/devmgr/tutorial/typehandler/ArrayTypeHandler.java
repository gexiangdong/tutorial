package cn.devmgr.tutorial.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis自带的ArrayTypeHandler(org.apache.ibatis.type.ArrayTypeHandler)，
 * 在做insert/update 如果属性(javaType)不是java.sql.Array类型会报错，无法插入/修改数据；
 * 这导致我们通常使用的String[], int[]等类型都不能插入/修改数据（但可使用select查询到这些java类型中）
 * 
 * 出现类似如下异常：
 * Caused by: org.apache.ibatis.type.TypeException: Error setting non null for parameter #5 with JdbcType null . 
 * Try setting a different JdbcType for this parameter or a different configuration property. 
 * Cause: java.lang.ClassCastException: [Ljava.lang.String; cannot be cast to java.sql.Array
 * at cn.devmgr.tutorial.OrderDaoTests.testAddProduct(OrderDaoTests.java:85)
 * Caused by: java.lang.ClassCastException: [Ljava.lang.String; cannot be cast to java.sql.Array
 *
 * 这里些的这个ArrayTypeHandler就是为了避免这个问题，可insert/update/select
 *
 * @param <T>
 */
public class ArrayTypeHandler<T> extends BaseTypeHandler<T> {
    private static final Logger logger = LoggerFactory.getLogger(ArrayTypeHandler.class);
    private Class<T> type;

    public ArrayTypeHandler(Class<T> type) {
        this.type = type;
        if(logger.isTraceEnabled()) {
            Type[] ts = type.getGenericInterfaces();
            logger.trace("<T> {}, {}, {}, getGenericInterfaces:{} getGenericSuperclass:{} ", type, type.getTypeParameters(), type.isArray(), type.getGenericInterfaces(), type.getGenericSuperclass());
            if(ts != null && ts.length > 0) {
                if (type.getGenericInterfaces()[0] instanceof ParameterizedType) {
                    ParameterizedType parameterizedType = (ParameterizedType) type.getGenericInterfaces()[0];
                    Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                    for (Type actualTypeArgument : actualTypeArguments) {
                        logger.trace("\tactualTypeArgument:{}", actualTypeArgument);
                    }
                }else{
                    logger.trace("\t type.getGenericInterfaces()[0]: {}", type.getGenericInterfaces()[0]);
                }
            }
        }
    }
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, T t, JdbcType jdbcType) throws SQLException {
        logger.trace("setNonNullParameter(ps, i, {}, {}", t, jdbcType);
        logger.trace("<T> {}, {}, {}, getGenericInterfaces:{} getGenericSuperclass:{} ", type, type.getTypeParameters(), type.isArray(), type.getGenericInterfaces(), type.getGenericSuperclass());
        String typeName = null;
        if (t instanceof Integer[]) {
            typeName = "integer";
        } else if (t instanceof String[]) {
            typeName = "varchar";
        } else if (t instanceof Boolean[]) {
            typeName = "boolean";
        } else if (t instanceof Double[]) {
            typeName = "numeric";
        }

        if (typeName == null) {
            throw new TypeException(
                    "ArrayTypeHandler parameter typeName error, your type is "
                            + t.getClass().getName());
        }

        Connection conn = preparedStatement.getConnection();
        Array array = conn.createArrayOf(typeName, (Object[]) t);
        preparedStatement.setArray(i, array);

    }

    @Override
    public T getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return getArray(resultSet.getArray(columnName));
    }

    @Override
    public T getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return  getArray(resultSet.getArray(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return  getArray(callableStatement.getArray(columnIndex));
    }

    private T getArray(Array array) throws SQLException{
        if (array == null) {
            return null;
        }
        try {
            return (T) array.getArray();
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }
}
