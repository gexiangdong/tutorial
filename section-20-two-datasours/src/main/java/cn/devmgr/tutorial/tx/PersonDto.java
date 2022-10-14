package cn.devmgr.tutorial.tx;

import java.util.Date;
import java.util.Map;

public class PersonDto {
  private int id;
  private String name;
  private Date birthday;
  private Map<String, Object> attributes;
  private Address address;

  public PersonDto() {}

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

  public Map<String, Object> getAttributes() {
    return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
    this.attributes = attributes;
  }

  public Address getAddress() {
    return address;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return this.getClass().getSimpleName() + "{id=" + id + ", name=" + name + "}";
  }
}
