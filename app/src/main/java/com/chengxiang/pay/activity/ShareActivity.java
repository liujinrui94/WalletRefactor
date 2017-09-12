package com.chengxiang.pay.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APImageObject;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;
import com.chengxiang.pay.R;
import com.chengxiang.pay.adapter.ShareGridViewAdapter;
import com.chengxiang.pay.bean.BaseBean;
import com.chengxiang.pay.bean.ImageTextGridViewBean;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Constant;
import com.chengxiang.pay.framework.utils.ImageUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzonePublish;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/15 16:06
 * @description: 分享
 */

public class ShareActivity extends BaseActivity implements AdapterView.OnItemClickListener, WbShareCallback {
    private ArrayList<ImageTextGridViewBean> imageTextGridViewBeanList;
    private String shareType;//分享类型 1 推荐分享 其余图片分享
    private Bitmap bitmap;//图片
    private String shareUrl;//推荐分享地址
    private String title;//推荐分享标题
    private String description;//推荐分享内容

    private Context context;
    private WbShareHandler wbShareHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_share);

        Window window = getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.BOTTOM);

        initData();
        initView();
    }

    private void initView() {
        GridView gridView = (GridView) findViewById(R.id.share_gv);
        ShareGridViewAdapter adapter = new ShareGridViewAdapter(context, imageTextGridViewBeanList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    private void initData() {
        shareType = getIntent().getStringExtra("shareType");
        String channelType = getIntent().getStringExtra("channelType");//渠道类型0 微信1支付宝 其余的 所有
        if (TextUtils.equals(shareType, "1")) {
            Bundle b = getIntent().getBundleExtra("bundle");
            bitmap = b.getParcelable("bitmap");
        } else if (TextUtils.equals(shareType, "0")) {
            //显示图像
            Uri imgUri = Uri.parse(getIntent().getStringExtra("imageUri"));
            try {
                bitmap = ImageUtil.getBitmapFormUri(context, imgUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
        imageTextGridViewBeanList = new ArrayList<>();
        if (TextUtils.equals("0", channelType)) {
            importWeChatData();
        } else if (TextUtils.equals("1", channelType)) {
            importAliPayData();
        } else {
            importWeChatData();
            importAliPayData();
            importQQData();
            importSinaData();
        }
        if (TextUtils.equals("1", shareType)) {
            shareUrl = Constant.EXTENSION_URL + "/fvp-qp-business/"
                    + BaseBean.getOrgId() + "/toReg.html"
                    + "?tjUserPhone=" + BaseBean.getPhoneNum();

            title = "诚享钱包-移动支付工具，实现财富共享百万商户的信任选择，您值得拥有！";
            description = "我一直在用诚享钱包，实时到账，\n" +
                    "安全可靠，你也来吧！";
        }
    }

    private void importWeChatData() {
        ImageTextGridViewBean weChatBean = new ImageTextGridViewBean();
        weChatBean.setImageId(R.mipmap.ic_user_upgrade_wechat);
        weChatBean.setDescribe("微信");
        imageTextGridViewBeanList.add(weChatBean);
        ImageTextGridViewBean circleFriendsBean = new ImageTextGridViewBean();
        circleFriendsBean.setImageId(R.mipmap.ic_share_circle_friends);
        circleFriendsBean.setDescribe("朋友圈");
        imageTextGridViewBeanList.add(circleFriendsBean);
    }

    private void importAliPayData() {
        ImageTextGridViewBean aliPayBean = new ImageTextGridViewBean();
        aliPayBean.setImageId(R.mipmap.ic_user_upgrade_ali_pay);
        aliPayBean.setDescribe("支付宝");
        imageTextGridViewBeanList.add(aliPayBean);
    }

    private void importQQData() {
        ImageTextGridViewBean qqBean = new ImageTextGridViewBean();
        qqBean.setImageId(R.mipmap.ic_share_qq);
        qqBean.setDescribe("QQ");
        imageTextGridViewBeanList.add(qqBean);
        ImageTextGridViewBean qzoneBean = new ImageTextGridViewBean();
        qzoneBean.setImageId(R.mipmap.ic_share_qzone);
        qzoneBean.setDescribe("QQ空间");
        imageTextGridViewBeanList.add(qzoneBean);
    }

    private void importSinaData() {
        ImageTextGridViewBean sinaBean = new ImageTextGridViewBean();
        sinaBean.setImageId(R.mipmap.ic_share_sina);
        sinaBean.setDescribe("新浪微博");
        imageTextGridViewBeanList.add(sinaBean);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (imageTextGridViewBeanList.get(position).getImageId()) {
            case R.mipmap.ic_user_upgrade_wechat:
                shareWechat(0, bitmap);
                finish();
                break;
            case R.mipmap.ic_share_circle_friends:
                shareWechat(1, bitmap);
                finish();
                break;
            case R.mipmap.ic_user_upgrade_ali_pay:
                shareAliPay(bitmap);
                finish();
                break;
            case R.mipmap.ic_share_qq:
                shareQQ(bitmap);
                break;
            case R.mipmap.ic_share_qzone:
                shareQzone(bitmap);
                break;
            case R.mipmap.ic_share_sina:
                shareSina(bitmap);
                break;
        }
    }

    private void shareSina(Bitmap bitmap) {
        WbSdk.install(this, new AuthInfo(this, Constant.SINA_APP_ID, "http://www.sina.com"
                , "direct_messages_read,direct_messages_write"));
        wbShareHandler = new WbShareHandler(this);
        wbShareHandler.registerApp();
        wbShareHandler.setProgressColor(R.color.themeColor);

        // 1. 初始化微博的分享消息
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        ImageObject imageObject = new ImageObject();
        imageObject.setImageObject(bitmap);
        weiboMessage.mediaObject = imageObject;

        TextObject textObject = new TextObject();
        textObject.text = "诚享钱包-移动支付工具，实现财富共享百万商户的信任选择，实时到账，安全可靠，您值得拥有！" + shareUrl;
        textObject.title = "诚享钱包";
        weiboMessage.textObject = textObject;

        wbShareHandler.shareMessage(weiboMessage, false);
    }

    private void shareQQ(Bitmap bitmap) {
        Uri imageUri = ImageUtil.getUriFromBitmap(bitmap);
        String imageURL = ImageUtil.getRealFilePath(context, imageUri);
        Tencent mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
        Bundle params = new Bundle();
        if (TextUtils.equals("1", shareType)) {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            params.putString(QQShare.SHARE_TO_QQ_TITLE, title);// 标题
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, description);// 摘要
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, shareUrl);// 内容地址
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, imageURL);// 网络图片地址
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getResources().getString(R.string.app_name));// 应用名称
        } else {
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);// 设置分享类型为纯图片分享
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, imageURL);// 需要分享的本地图片URL
        }
        // 分享操作要在主线程中完成
        mTencent.shareToQQ(ShareActivity.this, params, qqShareListener);
    }

    private void shareQzone(Bitmap bitmap) {
        Uri imageUri = ImageUtil.getUriFromBitmap(bitmap);
        String imageURL = ImageUtil.getRealFilePath(context, imageUri);
        ArrayList<String> imgUrlList = new ArrayList<>();
        imgUrlList.add(imageURL);
        Tencent mTencent = Tencent.createInstance(Constant.QQ_APP_ID, getApplicationContext());
        Bundle params = new Bundle();
        if (TextUtils.equals("1", shareType)) {
            params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
            params.putString(QzoneShare.SHARE_TO_QQ_TITLE, title);// 标题
            params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, description);// 摘要
            params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, shareUrl);// 内容地址
            params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, imgUrlList);// 图片地址
        } else {
            params.putInt(QzonePublish.PUBLISH_TO_QZONE_KEY_TYPE, QzonePublish.PUBLISH_TO_QZONE_TYPE_PUBLISHMOOD);
            params.putStringArrayList(QzonePublish.PUBLISH_TO_QZONE_IMAGE_URL,
                    imgUrlList);// 图片地址ArrayList
        }
        // 分享操作要在主线程中完成
        mTencent.shareToQQ(ShareActivity.this, params, qqShareListener);
    }

    private void shareAliPay(Bitmap bitmap) {
        IAPApi aliApi = APAPIFactory.createZFBApi(context, Constant.ALI_PAY_APP_ID, false); //支付宝初始化
        //构造一个Req
        SendMessageToZFB.Req aliReq = new SendMessageToZFB.Req();
        if (TextUtils.equals("1", shareType)) {
            //初始化一个APWebPageObject对象
            APWebPageObject webPageObject = new APWebPageObject();
            webPageObject.webpageUrl = shareUrl;
            //用APWebPageObject对象初始化一个APMediaMessage对象
            APMediaMessage webMessage = new APMediaMessage();
            webMessage.mediaObject = webPageObject;
            webMessage.title = title;
            webMessage.description = description;
            // 设置缩略图
            webMessage.setThumbImage(bitmap);
            bitmap.recycle();

            aliReq.message = webMessage;
            aliReq.transaction = buildTransaction("webpage");
        } else {
            //初始化一个APImageObject对象
            APImageObject imageObject = new APImageObject(bitmap);
            //用APImageObject对象初始化一个APMediaMessage对象
            APMediaMessage mediaMessage = new APMediaMessage();
            mediaMessage.mediaObject = imageObject;
            aliReq.message = mediaMessage;
            aliReq.transaction = buildTransaction("image");

        }
        //调用api接口发送消息到支付宝
        aliApi.sendReq(aliReq);
    }

    private void shareWechat(int type, Bitmap bitmap) {
        IWXAPI api = WXAPIFactory.createWXAPI(context, Constant.WECHAT_APP_ID, false);
        api.registerApp(Constant.WECHAT_APP_ID);
        if (!api.isWXAppInstalled()) {
            ToastUtils.showLongToast("您还未安装微信客户端");
            return;
        }
        // 初始化WXMediaMessage
        WXMediaMessage msg = new WXMediaMessage();
        if (TextUtils.equals("1", shareType)) {
            //初始化WXWebpageObject
            WXWebpageObject webPage = new WXWebpageObject();
            webPage.webpageUrl = shareUrl;
            msg.mediaObject = webPage;
            msg.title = title;
            msg.description = description;
        } else {
            // 初始化WXImageObject对象
            msg.mediaObject = new WXImageObject(bitmap);
        }
        // 设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        msg.thumbData = ImageUtil.bmpToByteArray(thumbBmp);
        bitmap.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        if (type == 0) {
            // 分享到微信好友
            req.scene = SendMessageToWX.Req.WXSceneSession;
        } else {
            req.scene = SendMessageToWX.Req.WXSceneTimeline;
        }
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis())
                : type + System.currentTimeMillis();
    }


    IUiListener qqShareListener = new IUiListener() {
        @Override
        public void onCancel() {
            ToastUtils.showLongToast("分享取消");
            finish();
        }

        @Override
        public void onComplete(Object response) {
            ToastUtils.showLongToast("分享成功");
            finish();
        }

        @Override
        public void onError(UiError e) {
            ToastUtils.showLongToast("分享出错");
            finish();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_QQ_SHARE || resultCode == Constants.REQUEST_QZONE_SHARE || resultCode == Constants.REQUEST_OLD_SHARE) {
                Tencent.handleResultData(data, qqShareListener);
            }
        }

    }

    @Override
    public void onWbShareSuccess() {
        ToastUtils.showLongToast("微博分享成功");
        finish();
    }

    @Override
    public void onWbShareCancel() {
        ToastUtils.showLongToast("微博分享取消");
        finish();
    }

    @Override
    public void onWbShareFail() {
        ToastUtils.showLongToast("微博分享出错");
        finish();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        wbShareHandler.doResultIntent(intent, this);
    }
}
