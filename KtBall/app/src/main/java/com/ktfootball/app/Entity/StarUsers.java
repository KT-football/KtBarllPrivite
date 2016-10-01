package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/7/21.
 */
public class StarUsers extends BaseEntity {

    public List<Star> users;

    public class Star {
        public String user_id;//":3,
        public String nickname;//":"刘力豪leokt足球",
        public String avatar;//":"/system/user_profiles/avatars/000/000/008/original/0?1456748421"
    }

}
