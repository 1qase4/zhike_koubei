package com.czc.bi.pojo.dto;


import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Administrator on 2017/9/27.
 */
public class UserLabel2 {
    private String gender;
    private String age;
    private String marital;
    private String havecar;
    private String constellations;
    private String province;
    private String family;
    private Set<String> shoppings;
    private Set<String> livings;

    public UserLabel2(){
        this.shoppings = new HashSet<>();
        //this.shoppings.add("one_shoppings~~");
        this.livings = new LinkedHashSet<>();
        //this.livings.add("one_livings");
    }

    public String getMarital() {
        return marital;
    }

    public UserLabel2 setMarital(String marital) {
        this.marital = marital;
        return this;
    }

    public String getHavecar() {
        return havecar;
    }

    public UserLabel2 setHavecar(String havecar) {
        this.havecar = havecar;
        return this;
    }

    public String getConstellations() {
        return constellations;
    }

    public UserLabel2 setConstellations(String constellations) {
        this.constellations = constellations;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public UserLabel2 setProvince(String province) {
        this.province = province;
        return this;
    }

    public void addShopping(String shooping){
        this.shoppings.add(shooping);
    }

    public void addLiving(String living){
        this.livings.add(living);
    }

    public String getGender() {
        return gender;
    }

    public UserLabel2 setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getAge() {
        return age;
    }

    public UserLabel2 setAge(String age) {
        this.age = age;
        return this;
    }

    public String getFamily() {
        return family;
    }

    public UserLabel2 setFamily(String family) {
        this.family = family;
        return this;
    }

    public String getShoppings() {
        return String.join(",",shoppings);
    }

    public String getLivings() {
        return String.join(",",livings);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserLabel2{");
        sb.append("gender='").append(gender).append('\'');
        sb.append(", age='").append(age).append('\'');
        sb.append(", family='").append(family).append('\'');
        sb.append(", shoppings=").append(shoppings);
        sb.append(", livings=").append(livings);
        sb.append('}');
        return sb.toString();
    }
}
