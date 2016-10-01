package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/7/21.
 */
public class CommentedVideos extends BaseEntity {

    public List<Videos> game_videos;

    public class Videos{
        public int id;//: 视频ID,
        public String url;//: 视频URL,
        public String place;//: 比赛地点,
        public String time;//: 比赛时间,
        public String upload_date;//: 上传日期,
        public String picture;//: 视频图片url,
        public String scores;//: 视频图片url,
        public int is_commented;//: 0 未评论 1 已评论,
    }
}
