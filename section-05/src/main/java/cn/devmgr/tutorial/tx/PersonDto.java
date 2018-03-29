package cn.devmgr.tutorial.tx;

import java.util.Date;

public class PersonDto {
    private int id;
    private String name;
    private Date birthday;
    
    public PersonDto() {
        
    }
    
    public PersonDto(int id, String name) {
        this.id = id;
        this.name = name;
        this.birthday = new Date();
    }
    
    
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{id=" + id + ", name=" + name + "}";
    }
}
