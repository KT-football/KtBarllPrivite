package com.ktfootball.app.Entity;


import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class GamePlace extends BaseEntity implements Serializable {

    public ArrayList<Games> games;

    public class Games implements Serializable {
        //                 "date_end": "2016-12-29",
        public String date_end;
        //                "id": 1343,
        public String id;
        //                "date_start": "2015-08-21",
        public String date_start;
        //                "name": "P2联合创业办公社北京中关村KT足球赛",
        public String name;
        //                "enter_ktb": 0,
        public String enter_ktb;
        //                "enter_time_end": null,
        public String enter_time_end;
        //                "enter_time_start": null,
        public String enter_time_start;
        //                "avatar": "/assets/default_game_avatar.jpg",
        public String avatar;
        //                "place": "北京中关村e世界A座P2"
        public String place;
        public String introduction;
    }
}