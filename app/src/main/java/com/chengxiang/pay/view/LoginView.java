package com.chengxiang.pay.view;

import com.chengxiang.pay.bean.ResponseParamsLogin;
import com.chengxiang.pay.framework.base.BaseView;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 8:19
 * @description: 登录页面视图层
 */


public interface LoginView {

    /**
     * 用户登录视图层
     */
    interface UserLoginView extends BaseView {

        String getUserLoginJsonString();

        void startActivity(ResponseParamsLogin info);
    }


    /**
     * 获取短信验证码
     */
    interface UserGetVerifiedCodeView extends BaseView {

        String getVerifiedCodeJsonString();

        void callVerifiedCode(String isUser);
    }

    /**
     * 用户注册视图层
     */
    interface UserRegisterView extends BaseView {

        void callRegisterResponse(String intentType);

        String getUserRegisterJsonString();

    }

    /**
     * 用户重置密码视图层
     */
    interface UserResetPasswordView extends BaseView {


        void callResetPasswordResponse();

        String getUserResetPasswordJsonString();

    }
}