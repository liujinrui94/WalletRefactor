package com.chengxiang.pay.presenter;

import com.chengxiang.pay.bean.ResponseParamsLogin;
import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.LoginPostModel;
import com.chengxiang.pay.view.LoginView;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 8:41
 * @description: 登录页面请求层
 */
public interface LoginPresenter {
    /**
     * 用户登录
     */
    class UserLogin {
        private LoginPostModel.PostUserLogin userLogin;
        private LoginView.UserLoginView loginView;

        public UserLogin(LoginView.UserLoginView loginView) {
            this.loginView = loginView;
            userLogin = new LoginPostModel.PostUserLogin();
        }

        public void PostUserLogin() {
            userLogin.postUserLogin(loginView.getUserLoginJsonString(), new CallBackUtils.UserLoginCallBack() {
                @Override
                public void startActivity(ResponseParamsLogin info) {
                    loginView.startActivity(info);
                }

                @Override
                public void OnNetError() {
                    loginView.showCordError("登录网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    loginView.showCordError(msg);
                }

            });
        }
    }

    /**
     * 获取短信验证码
     */
    class UserGetVerifiedCode {
        private LoginView.UserGetVerifiedCodeView getVerifiedCodeView;
        private LoginPostModel.PostUserGetVerifiedCode getVerifiedCode;

        public UserGetVerifiedCode(LoginView.UserGetVerifiedCodeView getVerifiedCodeView) {
            this.getVerifiedCodeView = getVerifiedCodeView;
            getVerifiedCode = new LoginPostModel.PostUserGetVerifiedCode();
        }


        public void PostUserGetVerifiedCode() {
            getVerifiedCode.postUserGetVerifiedCode(getVerifiedCodeView.getVerifiedCodeJsonString()
                    , new CallBackUtils.UserGetVerifiedCodeCallBack() {
                        @Override
                        public void CallVerifiedCode(String isUser) {
                            getVerifiedCodeView.callVerifiedCode(isUser);
                        }

                        @Override
                        public void OnNetError() {
                            getVerifiedCodeView.showCordError("获取短信验证码网络异常，请稍后重试！");
                        }


                        @Override
                        public void CodeError(String msg) {
                            getVerifiedCodeView.showCordError(msg);
                        }

                    });
        }
    }

    /**
     * 用户注册
     */
    class UserRegister {

        private LoginView.UserRegisterView registerView;
        private LoginPostModel.PostUserRegister register;

        public UserRegister(LoginView.UserRegisterView registerView) {
            this.registerView = registerView;
            register = new LoginPostModel.PostUserRegister();
        }

        //立即注册
        public void PostUserRegister() {
            register.postUserRegister(registerView.getUserRegisterJsonString()
                    , new CallBackUtils.UserRegisterCallBack() {
                        @Override
                        public void callRegisterResponse(String intentType) {
                            registerView.callRegisterResponse(intentType);
                        }

                        @Override
                        public void OnNetError() {
                            registerView.showCordError("用户注册网络异常");
                        }


                        @Override
                        public void CodeError(String msg) {
                            registerView.showCordError(msg);
                        }

                    });
        }
    }


    /**
     * 用户密码重置
     */
    class UserResetPwd {
        private LoginPostModel.PostUserResetPwd resetPwd;
        //密码重置
        private LoginView.UserResetPasswordView resetPwdView;

        public UserResetPwd(LoginView.UserResetPasswordView resetPwdView) {
            this.resetPwdView = resetPwdView;
            resetPwd = new LoginPostModel.PostUserResetPwd();
        }

        public void PostUserResetPwd() {
            resetPwd.postUserResetPwd(resetPwdView.getUserResetPasswordJsonString()
                    , new CallBackUtils.UserResetPwdCallBack() {
                        @Override
                        public void Response() {
                            resetPwdView.callResetPasswordResponse();
                        }

                        @Override
                        public void OnNetError() {
                            resetPwdView.showCordError("密码重置网络加载异常");
                        }


                        @Override
                        public void CodeError(String msg) {
                            resetPwdView.showCordError(msg);
                        }

                    });
        }
    }
}

