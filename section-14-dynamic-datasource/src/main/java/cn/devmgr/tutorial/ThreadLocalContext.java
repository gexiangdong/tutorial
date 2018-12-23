package cn.devmgr.tutorial;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ThreadLocalContext {
    private static final Logger logger = LoggerFactory.getLogger(ThreadLocalContext.class);

    public static final String CUSTOMERID = "CUSTOMER_ID";
    public static final String DBKEY = "DB_KEY";


    private ThreadBindings threadBindings = new ThreadBindings();

    private static ThreadLocalContext instance = new ThreadLocalContext();

    protected class ThreadBindings extends ThreadLocal<Map<String, Object>> {
        private String instanceTime = (new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")).format(new Date());

        public Map<String, Object> initialValue() {
            return new HashMap<String, Object>();
        }

        public Map<String, Object> getBindings() {
            return (Map<String, Object>) get();
        }

        @Override
        public String toString(){
            return super.toString() + " instanced at " + instanceTime + "; keys=" + getBindings().keySet().toString();
        }

    }

    private ThreadLocalContext(){
    }

    public void set(String name, Object value) {
        Map<String, Object> bindings = threadBindings.getBindings();
        if (bindings == null) {
            logger.warn("setInThread: no bindings!");
            return;
        }

        if (value == null) {
            bindings.remove(name);
        }else {
            bindings.put(name, value);
        }
    }

    public void clear(){
        Map<?, ?> bindings = threadBindings.getBindings();
        if (bindings == null) {
            if(logger.isInfoEnabled()){
                logger.info("clear: no bindings!");
            }
            return;
        }
        bindings.clear();
        threadBindings.remove();
    }


    public Object get(String name) {
        Map<?, ?> bindings = threadBindings.getBindings();
        if (bindings == null) {
            logger.info("get: no bindings!");
            return null;
        }

        return bindings.get(name);
    }


    public static ThreadLocalContext getInstance(){
        return instance;
    }

    public static void setValue(String name, Object value){
        instance.set(name, value);
    }

    public static Object getValue(String name){
        return instance.get(name);
    }

    public static void setCustomerId(String value){
        setValue(CUSTOMERID, value);
    }
    public static String getCustomerId(){
        return (String) getValue(CUSTOMERID);
    }

    public static void setDbKey(String value){
        setValue(DBKEY, value);
    }


    public static String getDbKey(){
        String key = (String) getValue(DBKEY);
        //设置的DbKey优先；如果未设置，使用customerId
        if(key == null){
            key = getCustomerId();
        }
        return key;
    }
}
