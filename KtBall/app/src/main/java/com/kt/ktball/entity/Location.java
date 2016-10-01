package com.kt.ktball.entity;

/**
 * Created by ARTHUR on 2016/4/18.
 */
public class Location {
//             "time_start": "2016-04-06",
    public String time_start;
//            "place": "申长路988号",
    public String place;
//            "location": "121.327236,31.187085",
    public String location;
//            "name": "根与芽公益活动",
    public String name;
//            "game_id": 1431
    public long game_id;

    @Override
    public String toString() {
        return "Location{" +
                "time_start='" + time_start + '\'' +
                ", place='" + place + '\'' +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", game_id=" + game_id +
                '}';
    }
}
