package com.ktfootball.app.Entity;

import com.ktfootball.app.Base.BaseEntity;

/**
 * Created by jy on 16/6/28.
 */
public class BcOrderDetail extends BaseEntity {

    public String pay_time;//: 支付时间,
    public String activity_time;//: 活动时间,
    public String address;//: 活动地址,
    public String contact;//: 下单人
    public String mobile;//: 下单电话,
    public String jie_contact;//: 接单人,
    public String jie_mobile;//: 接单人电话,
    public String number_of_people;//: 活动人数,
    public String invoice_title;//: 发票抬头,
    public String kuaidi_address;//: 发票地址,
    public String kuaidi_contact;//: 收件人,
    public String kuaidi_mobile;//: 收件人电话
    public String stars;
    public String comment;
}
