package cn.devmgr.tutorial.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ArrayTypeHandler<T> extends BaseTypeHandler<T> {
    private Class<T> type;
    
    public ArrayTypeHandler(Class<T> type) {
        this.type = type;
    }
    
    
    @Override
    public T getNullableResult(ResultSet arg0, String arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T getNullableResult(ResultSet arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public T getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setNonNullParameter(PreparedStatement arg0, int arg1, T arg2, JdbcType arg3) throws SQLException {
        // TODO Auto-generated method stub
        
    }

}
