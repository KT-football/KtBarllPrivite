package com.ktfootball.app.Entity;


import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;

public class GetRole extends BaseEntity implements Serializable {

    public String is_judge;//: 0或1(0: 不是裁判 1: 是裁判),
    public String is_club_manager;//: 0或1(0: 不是俱乐部管理员 1: 是俱乐部管理员),
    public String youku_token;//: "优酷token",
    public String tudou_token;//: "土豆token"
}