package com.kt.ktball.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by jy on 16/6/9.
 */
public class UserInfo {

    //    { response: "success", nickname: "昵称",gender: "性别:GG或MM", birthday: "1990-10-11", football_age "三年以上", country: "中国", city: "上海" ,country_cities: [{"中国" => [城市1,城市2,城市3]},{..},{...}]] }
    public String nickname;
    public String gender;
    public String birthday;
    public String football_age;
    public String country;
    public String city;
    public List<Map<String,List<String>>> country_cities;
}
