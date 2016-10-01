package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jy on 16/6/15.
 */
public class AddToUserAppCartoons extends BaseEntity implements Serializable{

    public List<AppCarToons> games;

    public class AppCarToons {
        public String id; //课程id,
        public String name; //标题,
        public String sub_name; //子标题,
        public String avatar; //"封面",
//        public String now_level_name;// 目前等级名,
//        public String now_level_color; //目前等级颜色,
//        public String now_level_progress; //目前等级进度
    }

}
