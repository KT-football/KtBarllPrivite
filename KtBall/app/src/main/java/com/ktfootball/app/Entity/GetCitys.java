package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/6/27.
 */
public class GetCitys extends BaseEntity {

    public List<City> citys;

    public class City {
        public String city_id; //城市id,
        public String name; //城市名
    }
}
