package com.ktfootball.app.Entity;


import com.ktfootball.app.Base.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by jy on 16/7/12.
 */
public class ClubBcOrders extends BaseEntity implements Serializable {

    public Map<String,List<Orders>> bc_orders;

    public class Orders implements Serializable {

        public String bc_order_number;//: "订单号",
        public String amount;//: 订单金额,
        public String pay_time;//: 订单时间,
        public String address;//: 订单地址
    }
}