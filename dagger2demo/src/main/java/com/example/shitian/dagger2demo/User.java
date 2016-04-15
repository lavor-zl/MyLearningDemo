package com.example.shitian.dagger2demo;


import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by shitian on 2015-12-06.
 */
public class User {
    private String name;
    private String sex;
    @Inject
    public User(){
        name="lavor";
        sex="男";
    }
    public User(int i){

    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
