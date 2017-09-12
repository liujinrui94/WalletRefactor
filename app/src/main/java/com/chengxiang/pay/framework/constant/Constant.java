package com.chengxiang.pay.framework.constant;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/22 12:36
 * @description: 常数
 */


public class Constant {
    public static final String RESPONSE_SUCCESS = "0000";//返回成功


    // APP_ID 替换为你的应用从官方网站申请到的合法appId
    public static final String WECHAT_APP_ID = "wx2b5a15ea13fbceb9";//微信appId
    public static final String ALI_PAY_APP_ID = "2017050407115614";//支付宝appId
    public static final String QQ_APP_ID = "1106232197";//QQappId

    public static final String SINA_APP_ID = "2816112400";//Sina appId
    public static final String SINA_APP_SECRET = "3e8b39c5b5fc23b2a19782a40832a208";//Sina App Secret

    public static final String BAIDU_OCR_AK = "iF2bev9fxujZViZCSuDjq09S";//百度OCR AK
    public static final String BAIDU_OCR_SK = "CvhaGLb5fcGOGvpA5tyhAkFTTimThPi7";//百度OCR SK

    public static final String XF_ID = "appid=599552c3";//讯飞语音播报ID

    public static String SDK_APP_ID = "0000005011";

    public static String APP_SECRET = "74255f9acc4ff9202395df0471ad6c23";// sdk appSecret

    // public static final String MOBILE_HOST = "http://test.rytpay.com.cn";// 测试
    // public static final String MOBILE_HOST = "http://192.168.1.133:8080";// 宽宽
    public static final String MOBILE_HOST = "http://wallet.rytpay.com.cn";// 生产

    public static String orgId = "000074";//机构号

    public static String MOBILE_FRONT = MOBILE_HOST + "/fvp-qp/qp.do?reqJson=";//访问默认前端地址

    public static String EXTENSION_URL = MOBILE_HOST;//推广

    public static String MOBILE_UPLOAD = MOBILE_HOST + "/fvp-qp/upfile.do?reqJson=";//上传文件地址

}
