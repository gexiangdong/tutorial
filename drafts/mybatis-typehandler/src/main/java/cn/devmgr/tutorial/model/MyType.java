package cn.devmgr.tutorial.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MyType {

    private String name;
    private String value;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    @Override
    public String toString() {
        return "MyType: name=" + this.name + "; value=" + this.value;
    }
}
