package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;

/**
 * Created by jy on 16/7/11.
 */
public class PostResult extends BaseEntity implements Serializable{

    public String battle_id;//: 对战ID ,
    public int user1_change_power;//: 用户1战斗力变化,
    public int user2_change_power;//: 用户2战斗力变化,
    public int user3_change_power;//: 用户3战斗力变化,
    public int user4_change_power;//: 用户4战斗力变化,
    public int user5_change_power;//: 用户5战斗力变化,
    public int user6_change_power;//: 用户6战斗力变化,
    public String user1_power;//: 用户1战斗力,
    public String user2_power;//: 用户2战斗力,
    public String user3_power;//: 用户3战斗力,
    public String user4_power;//: 用户4战斗力,
    public String user5_power;//: 用户5战斗力,
    public String user6_power;//: 用户6战斗力,
    public String user1_winrate;//: 用户1胜率，
    public String user2_winrate;//: 用户2胜率,
    public String user3_winrate;//: 用户3胜率，
    public String user4_winrate;//: 用户4胜率,
    public String user5_winrate;//: 用户5胜率,
    public String user6_winrate;//: 用户6胜率
}
