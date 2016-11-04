package com.ktfootball.app.UI.Activity.BlockBook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.frame.app.base.activity.BaseToolBarActivity2;
import com.frame.app.utils.LogUtils;
import com.ktfootball.app.Constants;
import com.ktfootball.app.Entity.PaymentCallback;
import com.ktfootball.app.Net.CallServer;
import com.ktfootball.app.Net.HttpListener;
import com.ktfootball.app.R;
import com.ktfootball.app.Request.PayResult;
import com.ktfootball.app.Request.PaymentCallbackRequest;
import com.ktfootball.app.Utils.SignUtils;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by jy on 16/6/28.
 */
public class PaymentActivity extends BaseToolBarActivity2 {

    // 商户PID
    public static final String PARTNER = "2088701146043231";
    // 商户收款账号
    public static final String SELLER = "business@tempot.com";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAMLp5ENga4eh7b0PlX3YmytVuox2/KQGYbvWyRZVn0Hh9Ekf6t2yvEk/+8q3jP22qHZszY5moDgk1rd3Z1n2j6OMa2QlMO7neS3VPsnOp9mTe85aq05ud8XxT/IG5n3hgJZ9POEqsYXVkx6HH1E762Qn/hdU+7FLSHKjeMupMjyRAgMBAAECgYB6m3gr7dYWPoT2vXvS6tNI4idzvlFTuHP4HbbmZrvETKdG7F3oUUthZG816Fo3KpQz0mNHZUT5rCqow+JuU8usA0nQiGsjU8ZLOWJ2PclH3Gq1IKC0XmrjMzocSG5XmVL6cgzO0eh0j0mlyNIosoeKxwc0e2S6rJAfKwcQTh9kQQJBAORybutUNS//RdOV7BF0S7RcSvK2Fx02rr7M5AhkQAFCCOGB3LkrkUA6bVwTrYpcHhmzuFslhSRluDTPVBNRPUkCQQDabBOlgV477yEO4d5T1HDEnB3C+FiKCQlys3HVFdlad/7WjGUR8J/2tYDu/AGOmMww0TbTYLta1pu8dqM9R20JAkAlrmJxWrBzPE4OOnp33pDmLRsHsdVaMjzcp/stDywniPbiC4OW34LT708+kthbLBqi7qwIDMZLXKPnwhMCJLJRAkB3TRjeCW9DJTXVPyGEgECn4u2OlL8nhhAXClReovR5KZmT7lM2HUwCINkvr43aKTM+ZsefZPJwh0/A/LvkuKKhAkBjUZnoRE36HxWlwS3LSFbyY9ilooByapqS+W/PKwQFzeVztOnhID8dOGgi/Frr+BmIOYS5hXuQ2Pt/4aSw7/vd";
    private static final int SDK_PAY_FLAG = 1;
    private String orderid;
    private ImageView submit;
    private String pay;
    private TextView pay_1;
    private TextView pay_2;

    @Override
    protected void initToolBar() {
        setToolBarTitle(getString(R.string.queren_pay));
    }

    @Override
    protected void OnNavigationClick(View v) {
        finish();
    }

    @Override
    protected void initHandler(Message msg) {
        switch (msg.what) {
            case SDK_PAY_FLAG: {
                PayResult payResult = new PayResult((String) msg.obj);
                /**
                 * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                 * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                 * docType=1) 建议商户依赖异步通知
                 */
                String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                String resultStatus = payResult.getResultStatus();
                // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                if (TextUtils.equals(resultStatus, "9000")) {
                    doPaymentCallback();
                } else {
                    // 判断resultStatus 为非"9000"则代表可能支付失败
                    // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                    if (TextUtils.equals(resultStatus, "8000")) {
                        showToast(getString(R.string.is_paying));

                    } else {
                        // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        showToast(getString(R.string.pay_error));
                    }
                }
                break;
            }
            default:
                break;
        }
    }

    private void doPaymentCallback() {
        showLoadingDiaglog();
        PaymentCallbackRequest request = new PaymentCallbackRequest(Constants.PAYMENT_CALLBACK, RequestMethod.POST);
        request.add("out_trade_no",orderid);
        CallServer.getRequestInstance().add(this, 0, request, new HttpListener<PaymentCallback>() {
            @Override
            public void onSucceed(int what, Response<PaymentCallback> response) {
                closeLoadingDialog();
//                if(response.get().response.equals("success")){
//                    LogUtils.e("success");
//                }else{
//                    LogUtils.e(response.get().msg);
//                }
                showDialogToast(getString(R.string.pay_succsee), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(Constants.PAY_SUCCESS);
                        finish();
                    }
                });
            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                closeLoadingDialog();
            }
        }, false, false);
    }

    @Override
    protected void setListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay(v);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        orderid = getIntent().getStringExtra("orderid");
        pay = getIntent().getStringExtra("pay");
        pay_1.setText("￥ "+pay);
        pay_2.setText("￥ "+pay);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        setBackgroundResource(R.drawable.bg_blockbook);
        addContentView(R.layout.layout_payment);
        submit = (ImageView) findViewById(R.id.layout_payment_submit);
        pay_1 = (TextView) findViewById(R.id.layout_payment_ed);
        pay_2 = (TextView) findViewById(R.id.layout_payment_pay_2);
    }

    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay(View v) {
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            //
                            finish();
                        }
                    }).show();
            return;
        }
//        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", "0.1");
        String orderInfo = getOrderInfo("测试的商品", "该测试商品的详细描述", pay);

        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(getThis());
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderid + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm" + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
