package com.example.shitian.sugarormdemo;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by shitian on 2015-12-08.
 */
public class Person extends SugarRecord {
    @Unique
    int idref;//设置id唯一
    String name;
    String sex;
    //默认的构造方法，对于SugarRecord来说很重要
    public Person(){

    }
    public Person(int idref,String name,String sex){
        this.idref=idref;
        this.name=name;
        this.sex=sex;

    }

}
