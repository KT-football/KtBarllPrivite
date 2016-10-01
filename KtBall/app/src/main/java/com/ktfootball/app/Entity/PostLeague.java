package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;

/**
 * Created by jy on 16/7/6.
 */
public class PostLeague extends BaseEntity implements Serializable{

    public String league_id;//: 战队ID,
    public String league_avatar;//: 战队头像URL,
    public String league_name;// "战队名称"
}
