package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

import java.util.List;

/**
 * Created by jy on 16/6/28.
 */
public class UserBcOrders extends BaseEntity {

    public List<BcOrders> bc_orders;

    public class BcOrders{
        public String bc_order_number;//: 订单号,
        public String amount;//: 金额,
        public String pay_time;//: 支付时间,
        public String activity_time;//: 活动时间,
        public String status;//: 订单状态
        public String address;
    }
}
