package cn.devmgr.tutorial;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix="tutorial", ignoreInvalidFields = true)
public class KeyValueGroup {

    private Map<String, Map<Integer, String>> maps;

    public Map<String, Map<Integer, String>> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Map<Integer, String>> maps) {
        this.maps = maps;
    }

    //    private Map<Integer, String> types;

//    public Map<Integer, String> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(Map<Integer, String> categories) {
//        this.categories = categories;
//    }
//
//    public Map<Integer, String> getTypes() {
//        return types;
//    }
//
//    public void setTypes(Map<Integer, String> types) {
//        this.types = types;
//    }

    public String toString(){
        return this.getClass().getName() + "<" + this.maps + "; "  + ">";
    }
}
