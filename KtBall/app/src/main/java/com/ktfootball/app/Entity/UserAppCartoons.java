package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/6/14.
 */
public class UserAppCartoons extends BaseEntity {

    public String avatar; //用户头像url,
    public String total_finished_times; //总完成次数,
    public String total_finished_minutes; //总完成分钟数,
    public String study_days; //连续学习天数,
    public List<AppCarToons> app_cartoons;

    public class AppCarToons {
        public String id; //课程id,
        public String name; //标题,
        public String sub_name; //子标题,
        public String avatar; //"封面",
        public String now_level_name;// 目前等级名,
        public String now_level_color; //目前等级颜色,
        public String now_level_progress; //目前等级进度
    }
}
