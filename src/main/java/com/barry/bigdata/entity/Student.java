package com.barry.bigdata.entity;

import com.barry.bigdata.hbase.HbaseColumn;
import com.barry.bigdata.hbase.HbaseTable;

import java.io.Serializable;

/**
 * @Auther: hants
 * @Date: 2018-05-26 22:51
 * @Description: Student entity
 */
@HbaseTable(tableName="t_student")
public class Student implements Serializable {

    private static final long serialVersionUID = 6306456210564083083L;

    /**
     * 表名: student
     * 列族<1>: family
     * 列: family:telphone
     * 列: family:address
     * 列: family:father
     * 列: family:monther
     * 列族<2>: cousor
     * 列: cousor:chinese
     * 列: cousor:mathematics
     * 列: cousor:physicalEducation
     * 列: cousor:physical
     * 列: cousor:chemistry
     * 列族<3>:personal
     * 列: personal:username
     * 列: personal:names
     * 列: personal:gender
     * 列: personal:age
     * */
    @HbaseColumn(family="family", qualifier="telphone")
    private String telphone;

    @HbaseColumn(family="family", qualifier="address")
    private String address;

    @HbaseColumn(family="family", qualifier="father")
    private String father;

    @HbaseColumn(family="family", qualifier="monther")
    private String monther;

    @HbaseColumn(family="cousor", qualifier="chinese")
    private String chinese;

    @HbaseColumn(family="cousor", qualifier="mathematics")
    private String mathematics;

    @HbaseColumn(family="cousor", qualifier="physicalEducation")
    private String physicalEducation;

    @HbaseColumn(family="cousor", qualifier="physical")
    private String physical;

    @HbaseColumn(family="cousor", qualifier="chemistry")
    private String chemistry;

    @HbaseColumn(family="personal", qualifier="username")
    private String username;

    @HbaseColumn(family="personal", qualifier="names")
    private String names;

    @HbaseColumn(family="personal", qualifier="gender")
    private String gender;

    @HbaseColumn(family="personal", qualifier="age")
    private String age;

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMonther() {
        return monther;
    }

    public void setMonther(String monther) {
        this.monther = monther;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getMathematics() {
        return mathematics;
    }

    public void setMathematics(String mathematics) {
        this.mathematics = mathematics;
    }

    public String getPhysicalEducation() {
        return physicalEducation;
    }

    public void setPhysicalEducation(String physicalEducation) {
        this.physicalEducation = physicalEducation;
    }

    public String getPhysical() {
        return physical;
    }

    public void setPhysical(String physical) {
        this.physical = physical;
    }

    public String getChemistry() {
        return chemistry;
    }

    public void setChemistry(String chemistry) {
        this.chemistry = chemistry;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}

