package com.kt.ktball.entity;

/**
 * Created by ARTHUR on 2016/4/15.
 */
public class Leagues {
//    league_id: 622
    public long league_id;
//    name: leoand贝
    public String name;
//    game_type:
    public String game_type;
//    usera_id: 3
    public long usera_id = 0;
//    usera_nickname: 刘力豪leokt足球
    public String usera_nickname;
//    usera_avatar: /system/user_profiles/avatars/000/000/008/original/0?1456748421
    public String usera_avatar;
//    usera_power: 1980
    public int usera_power;
//    userb_id: 28844
    public long userb_id;
//    userb_nickname: 贝云滨
    public String userb_nickname;
//    userb_avatar: /system/user_profiles/avatars/000/027/853/original/0?1459316515
    public String userb_avatar;
//    userb_power: 2780
    public int userb_power;
    public long userc_id;
    public String userc_nickname;
    public String userc_avatar;
    public int userc_power;
    public int code = -1;

    @Override
    public String toString() {
        return "Leagues{" +
                "league_id=" + league_id +
                ", name='" + name + '\'' +
                ", game_type='" + game_type + '\'' +
                ", usera_id=" + usera_id +
                ", usera_nickname='" + usera_nickname + '\'' +
                ", usera_avatar='" + usera_avatar + '\'' +
                ", usera_power=" + usera_power +
                ", userb_id=" + userb_id +
                ", userb_nickname='" + userb_nickname + '\'' +
                ", userb_avatar='" + userb_avatar + '\'' +
                ", userb_power=" + userb_power +
                ", userc_id=" + userc_id +
                ", userc_nickname='" + userc_nickname + '\'' +
                ", userc_avatar='" + userc_avatar + '\'' +
                ", userc_power=" + userc_power +
                '}';
    }
}
