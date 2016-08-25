package com.syhd.payandroid.alipay.server;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by lenovo on 2016/7/12.
 */
public class AlipayServer {
    //
    // 商户PID
    public static final String PARTNER = "2088221983985885";
    // 商户收款账号
    public static final String SELLER = "2088221983985885";
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKlxG0vgDnrbmln7"+
            "+1OHBbJS3FmYFEwqz8oc7+pd5EinCJn2u7uw9ban3GF7Nmz6yQsco1y53ROcop6S"+
            "K4G/afJT3OiUv0icUKlko1psCi83QQIhbl5ogf1eh7mAABX7IAzaxfpahnU2eoVx"+
            "wGMjIFm4uFUrEV+x8w5/Y3mcx7pBAgMBAAECgYAaaliwDoETZDL+/kqjLfSUZViW"+
            "2x+m9DGqt7IrXnHdZbTwk8eWrjLQRAEWbWgBu2MmSVOqfAtcKFot0GnZ82ZnsOUw"+
            "pqeEjVYhPM72+okEgGEb6SJH9/I51w3nYXoomrSlgxHorufZB7DPF1D1pxDeeNAf"+
            "NUGPeryh+c8qqKNFJQJBANu3KN7eoixkBYktYylUWskMKcXr5CjLc0nGcYInD5cj"+
            "LxWsedWQWxbbtgx7BcEUwiXpgrr/wOhpLXzfS0ieew8CQQDFbItSWYqg3gSTeBNv"+
            "tiEq/X0SQ+87H3STZVa9fV33NlCcJakXE4UM23OpUpH5uRF68H9EOeRvrxg/cxsd"+
            "kLWvAkEAtMJKGJk2+KhNOZ8ijpT8n5ynHVLFgZ5nudFN/xLdq6zhhsY/8ahymCqn"+
            "6jqn8EKAu6oRyICyl0I4jxwxlovzlwJBAJ6sI7nXD61FY8YmjvCNIFFOFPTWNvN8"+
            "z5QbscBwf1JOjDFDr4hFfv0bb/VZ0Ms1rE/z9UWfhfMhdkqgt91mTi8CQHPDf/V2"+
            "KMcyUbUatqRzu45Y70//47EZuZSenzx8sNZ8RvIolIubHp+9asifb0yXstOaJFQA"+
            "XzgmDqUheezgCt0=";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "";
    private static final int SDK_PAY_FLAG = 1;
    /**
     * 获取订单信息
     * @param orderId 订单号
     * @param subject 商品名称
     * @param body 商品描述
     * @param money 金额
     * @param notifyUrl 支付回调地址
     * @return
     */
    public static String getPayInfo(String orderId,String subject, String body, String money,String notifyUrl) {
        // 订单
        String orderInfo = getOrderInfo(orderId,subject,
                body, money,notifyUrl);

        return signOrderInfo(orderInfo);
    }

    private static String signOrderInfo(String orderInfo) {

        // 对订单做RSA 签名
        String sign = sign(orderInfo);

        try {
            // 仅需对sign 做URL编码
            sign = URLEncoder.encode(sign, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 完整的符合支付宝参数规范的订单信息
        String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        return payInfo;
    }

    /**
     * create the order info. 创建订单信息
     */
    private static String getOrderInfo(String orderId, String subject, String body, String price, String notifyUrl) {
        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号

        orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + notifyUrl
                + "\"";

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
    private static String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private static String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
