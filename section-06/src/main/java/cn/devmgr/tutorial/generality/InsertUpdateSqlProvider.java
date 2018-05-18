package cn.devmgr.tutorial.generality;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
 
import org.apache.ibatis.jdbc.SQL;
 
 
/**
 * 使用此类有几个默认规则： 表名和类名相对应，属性名和字段名对应
 * java类内用驼峰命名法；数据库表和字段都用下划线
 * 类的属性定义的变量名和get/set方法名，以及set的参数类型一致，才会映射到数据库字段
 * 例如： private String name; 而且有 public void setName(String name) 和 public String getName()方法
 * 如果不是严格按照此规则定义的属性，不会被影射到数据库字段上
 */
public class InsertUpdateSqlProvider {
 
    /**
     * id如果传入了值，会被insert使用；如果id为null，不会被insert的columns列出
     */
    public static String insert(Object obj) {
        Map<String, String> map;
        try {
            map = getFieldsMap(obj, true);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return new SQL() {
            {
                INSERT_INTO(getTableName(obj));
                for (String col : map.keySet()) {
                    VALUES(col, map.get(col));
                }
            }
        }.toString();
    }
     
    private static String updateById(Object obj, boolean includeNullValueField) {
        Map<String, String> map;
        try {
            map = getFieldsMap(obj, includeNullValueField);
            map.remove("id");
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return new SQL() {
            {
                UPDATE(getTableName(obj));
                for (String col : map.keySet()) {
                    SET(col + "=" + map.get(col));
                }
                WHERE("id = #{id}");
            }
        }.toString();
    }
 
    public static String updateById(Object obj) {
        return updateById(obj, true);
    }
 
    public static String updateNonNullById(Object obj) {
        return updateById(obj, false);
    }
 
    private static Map<String, String> getFieldsMap(Object obj, boolean includeNullValue)
            throws IllegalArgumentException, IllegalAccessException {
        HashMap<String, String> result = new HashMap<>();
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields(); // getDeclaredFields
        for (Field f : fields) {
            String col = f.getName();
            String colName = col.substring(0, 1).toUpperCase() + col.substring(1);
            f.setAccessible(true);
            try {
                cls.getMethod("get" + colName);
                cls.getMethod("set" + colName, f.getType());
            } catch (NoSuchMethodException | SecurityException e) {
                continue;
            }
            if ((!"id".equals(col) && includeNullValue) || f.get(obj) != null) {
                result.put(camelCase2Underscore(col), "#{" + col + "}");
            }
        }
        return result;
    }
 
    private static String getTableName(Object obj) {
        return camelCase2Underscore(obj.getClass().getSimpleName());
    }
 
    private static String camelCase2Underscore(String s) {
        StringBuffer buf = new StringBuffer();
        for (String w : s.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
            buf.append("_").append(w.toLowerCase());
        }
        return buf.substring(1);
    }
}