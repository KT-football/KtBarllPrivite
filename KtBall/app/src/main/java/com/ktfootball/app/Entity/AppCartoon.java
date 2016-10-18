package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jy on 16/6/15.
 */
public class AppCartoon extends BaseEntity implements Serializable{
    public String id; //课程ID,
    public String name; //课程名称,
    public String intro; //介绍,
    public String description; //说明,
    public String avatar; //封面
    public List<String> youku_videos; //封面
    public List<Lessons> lessons; //封面
    public List<Videos> videos; //封面
    public String finished_times; //完成次数,
    public String finished_minutes; //完成分钟数,
    public String now_level_name; //目前等级名,
    public String now_level_color; //目前等级颜色,
    public String now_level_progress; //目前等级进度



    public class Lessons implements Serializable{
        public String id; //课时id,
        public String name; //名称,
        public String avatar; //封面,
        public String intro; //介绍,
        public String download_images_url; //图片zip下载地址,
        public String zip_size; //zip包大小
    }

    public class Videos implements Serializable{
        public String id; //视频id,
        public String download_video_url; //视频下载地址,
        public String video_size; //视频大小,
        public String video_level; //v.level,
        public String youku_videos; //优酷视频列表,
        public String speed;// 速度,
        public String total_times; //总次数
    }
}
