package com.ktfootball.app.Entity;

import com.frame.app.business.BaseResponse;

import java.util.List;

/**
 * Created by jy on 16/6/17.
 */
public class AppCartoons extends BaseResponse{
    //games: [ { id: 课程id, name: 标题, sub_name: 子标题, avatar: "封面", youku_videos: "优酷视频列表", is_added: (1 已添加 0 未添加)}, {..}, {...}  }
    public List<Cartoons> games;

    public class Cartoons{
        public String id; //课程id,
        public String name; //标题,
        public String sub_name; //子标题,
        public String avatar; //"封面",
        public List youku_videos; //"优酷视频列表",
        public String is_added; //(1 已添加 0 未添加)

    }
}
