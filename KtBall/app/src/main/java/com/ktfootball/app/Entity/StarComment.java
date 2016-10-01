package com.ktfootball.app.Entity;

import java.util.List;

/**
 * Created by jy on 16/7/26.
 */
public class StarComment{
    public String id;//: 评论ID,
    public String user_id;//: 明星用户ID,
    public String nickname;//: 昵称,
    public String avatar;//: 头像,
    public String content;//: 评论内容,
    public String is_read;//: 是否已读(0 未读, 1 已读)
    public String is_star;//: 是否明星(1 是 1 否)
    public String starjob;//: 明星职位
    public List<SubComment> sub_comments;
}
