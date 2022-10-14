package cn.devmgr.tutorial;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JsonTypeHandler<T> extends BaseTypeHandler<T> {
  private static final Logger logger = LoggerFactory.getLogger(JsonTypeHandler.class);
  private static ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
  }

  private Class<T> type;

  public JsonTypeHandler(Class<T> type) {
    this.type = type;
    if (logger.isTraceEnabled()) {
      Type[] ts = type.getGenericInterfaces();
      logger.trace(
          "<T> {}, {}, {}, getGenericInterfaces:{} getGenericSuperclass:{} ",
          type,
          type.getTypeParameters(),
          type.isArray(),
          type.getGenericInterfaces(),
          type.getGenericSuperclass());
      if (ts != null && ts.length > 0) {
        if (type.getGenericInterfaces()[0] instanceof ParameterizedType) {
          ParameterizedType parameterizedType = (ParameterizedType) type.getGenericInterfaces()[0];
          Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
          for (Type actualTypeArgument : actualTypeArguments) {
            logger.trace("\tactualTypeArgument:{}", actualTypeArgument);
          }
        } else {
          logger.trace("\t type.getGenericInterfaces()[0]: {}", type.getGenericInterfaces()[0]);
        }
      }
    }
  }

  private T parse(String json) {
    try {
      if (json == null || json.length() == 0) {
        return null;
      }
      // if (type.isAssignableFrom(List.class)) {
      //      logger.trace("try to read json using TypeReference");
      TypeReference<T> tr =
          new TypeReference<T>() {
            @Override
            public Type getType() {
              return type;
            }
          };
      return objectMapper.readValue(json, tr);
      //      }
      //      logger.trace("type not assignable from list");
      //      return objectMapper.readValue(json, type);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String toJsonString(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return (T) parse(rs.getString(columnName));
  }

  @Override
  public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return (T) parse(rs.getString(columnIndex));
  }

  @Override
  public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return (T) parse(cs.getString(columnIndex));
  }

  @Override
  public void setNonNullParameter(
      PreparedStatement ps, int columnIndex, Object parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setString(columnIndex, toJsonString(parameter));
  }
}
