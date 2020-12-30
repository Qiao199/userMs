package com.user.userms.dao.po;

/**
 * 用户实体类，与数据表中的字段对应
 */
public class User {
    private Integer id;
    private String name;//姓名
    private String phone;//手机

    public User() {
    }

    public User(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
