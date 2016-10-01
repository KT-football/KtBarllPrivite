package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jy on 16/6/27.
 */
public class GetCityPrice extends BaseEntity implements Serializable{

    public List<CityPrice> city_price;

    public class CityPrice implements Serializable{
        public String _date;//: "日期",
        public List<String> prices;//: "价格"
    }
}
