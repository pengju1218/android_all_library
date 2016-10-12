/*

package com.ruiec.test;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.logic.http.AsynNetUtils;
import com.logic.http.RequestParams;

import com.ruiec.test.bean.Msg;
import com.syhd.payandroid.util.MD5;
import com.syhd.payandroid.util.PayUtil;
import com.syhd.payandroid.weixin.WXPay;


public class MainActivity extends Activity implements View.OnClickListener {

    private android.widget.Button paybu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);
        this.paybu = (Button) findViewById(R.id.pay_bu);

        paybu.setOnClickListener(this);


    }

    public void sendData() {

        String nonce_str = PayUtil.create_nonce_str();
        String out_trade_no = PayUtil.genOutTradNo();
        String ip = getIp();

        RequestParams params = new RequestParams();
        params.put("appid", "wx5d0ec692ec17456d");
        params.put("body", "APP支付测试");
        params.put("mch_id", "1392976302");
        params.put("nonce_str", nonce_str);
        params.put("notify_url", "http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php");
        params.put("out_trade_no", out_trade_no);
        params.put("spbill_create_ip", ip);
        params.put("total_fee", "10");
        params.put("trade_type", "APP");

        String tmp = "appid=wx5d0ec692ec17456d&body=APP支付测试&mch_id=1392976302&nonce_str="
                + nonce_str + "&notify_url=http://wxpay.weixin.qq.com/pub_v2/pay/notify.v2.php"
                + "&out_trade_no=" + out_trade_no + "&spbill_create_ip=" + ip + "&total_fee=10&trade_type=APP&"
                + "key=esckf6uxCxNpN4N1pUk8HHyxyajPW0Tm";


        params.put("sign", MD5.MD5Encode(tmp).toUpperCase());

        AsynNetUtils.postXml("https://api.mch.weixin.qq.com/pay/unifiedorder", params, new AsynNetUtils.Callback() {
            @Override
            public void onResponse(String response) {

                Log.i("eweaearewa", response);

                if (response == null || "".equals(response)) {
                    return;
                }
                Msg msg = JSON.parseObject(response, Msg.class);

                if ("SUCCESS".equals(msg.getResult_code())) {

                    long tie = System.currentTimeMillis() / 1000;

                    String tx = "appid=" + msg.getAppid() + "&noncestr=" + msg.getNonce_str() +
                            "&package=Sign=WXPay&partnerid=" + msg.getMch_id() + "&prepayid=" + msg.getPrepay_id()
                            + "&timestamp=" + tie + "&key=esckf6uxCxNpN4N1pUk8HHyxyajPW0Tm";


                    String payInfo = "{\"appid\":\"" + msg.getAppid() +
                            "\",\"partnerid\":\"" + msg.getMch_id() +
                            "\",\"prepayid\":\"" + msg.getPrepay_id() +
                            "\",\"package\":\"Sign=WXPay\"," +
                            "\"noncestr\":\"" + msg.getNonce_str() + "\"," +
                            "\"timestamp\":\"" + tie + "\",\"sign\":\"" + MD5.MD5Encode(tx).toUpperCase() + "\"}";

                    Log.i("eseae", tx);
                    Log.i("payinfo", payInfo + "");
                    WXPay wxpay = new WXPay(MainActivity.this, "wx5d0ec692ec17456d");
                    wxpay.doPay(payInfo, new WXPay.WXPayResultCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.d("aaaaaaaaaaaa", "支付成功");
                        }

                        @Override
                        public void onError(int error_code) {
                            Log.d("aaaaaaaaaaaa", "支付失败" + error_code);
                        }

                        @Override
                        public void onCancel() {
                            Log.d("aaaaaaaaaaaa", "支付取消");
                        }
                    });
                }


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pay_bu) {
            sendData();

         */
/*   String s = "appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA&key=192006250b4c09247ec02edce69f6a2d";
            String ss = MD5.getMessageDigest(s.getBytes()).toUpperCase();
            Log.i("ewaeae", ss + "");*//*

            // Toast.makeText(MainActivity.this, getIp() + "-----------", Toast.LENGTH_SHORT).show();
        }
    }

    private String getIp() {
        WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();

        // 格式化IP address，例如：格式化前：1828825280，格式化后：192.168.1.109
        String ip = String.format("%d.%d.%d.%d",
                (ipAddress & 0xff),
                (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff),
                (ipAddress >> 24 & 0xff));
        return ip;

    }
}
*/
