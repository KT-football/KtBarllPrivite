package com.ktfootball.app.Entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by leo on 16/10/19.
 */

public class ChongZhiListBean implements Serializable{

    /**
     * response : success
     * recharge_list : [{"recharge_id":1,"price":100,"ktb":1000,"description":"1000KT币充值"},{"recharge_id":2,"price":300,"ktb":3150,"description":"3150KT币充值"},{"recharge_id":3,"price":500,"ktb":5500,"description":"5500KT币充值"},{"recharge_id":4,"price":800,"ktb":9200,"description":"9200KT币充值"},{"recharge_id":5,"price":1000,"ktb":12000,"description":"12000KT币充值"},{"recharge_id":6,"price":20,"ktb":200,"description":"200KT币充值"},{"recharge_id":7,"price":50,"ktb":500,"description":"500KT币充值"}]
     */

    private String response;
    /**
     * recharge_id : 1
     * price : 100
     * ktb : 1000
     * description : 1000KT币充值
     */

    private List<RechargeListBean> recharge_list;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<RechargeListBean> getRecharge_list() {
        return recharge_list;
    }

    public void setRecharge_list(List<RechargeListBean> recharge_list) {
        this.recharge_list = recharge_list;
    }

    public static class RechargeListBean implements Serializable{
        private int recharge_id;
        private int price;
        private int ktb;
        private String description;

        public int getRecharge_id() {
            return recharge_id;
        }

        public void setRecharge_id(int recharge_id) {
            this.recharge_id = recharge_id;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getKtb() {
            return ktb;
        }

        public void setKtb(int ktb) {
            this.ktb = ktb;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
