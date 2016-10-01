package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/7/26.
 */
public class Video extends BaseEntity {

    public String game_video_id;//: 视频ID,
    public String url;//: 视频URL,
    public List<StarComment> star_comments;
}
