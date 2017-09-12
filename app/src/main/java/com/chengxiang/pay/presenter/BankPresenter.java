package com.chengxiang.pay.presenter;

import com.chengxiang.pay.bean.BankInfoBean;
import com.chengxiang.pay.bean.ResponseParamsBranchInfo;
import com.chengxiang.pay.bean.ResponseParamsCityInfo;
import com.chengxiang.pay.bean.ResponseParamsHeadOfficeInfo;
import com.chengxiang.pay.bean.ResponseParamsProvinceInfo;
import com.chengxiang.pay.model.BankPostModel;
import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.view.BankView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/1 8:41
 * @description: 银行页面请求层
 */


public interface BankPresenter {
    /**
     * 获取总行数据
     */
    class HeadOffice {
        private BankPostModel.PostHeadOffice postHeadOffice;
        private BankView.HeadOfficeView headOfficeView;

        public HeadOffice(BankView.HeadOfficeView headOfficeView) {
            this.headOfficeView = headOfficeView;
            postHeadOffice = new BankPostModel.PostHeadOffice();
        }

        public void PostHeadOffice() {
            postHeadOffice.postHeadOffice(headOfficeView.getHeadOfficeJsonString(), new CallBackUtils.HeadOfficeCallBack() {
                @Override
                public void headOfficeResponse(ArrayList<ResponseParamsHeadOfficeInfo.BankInfo> bankList) {
                    headOfficeView.headOfficeResponse(bankList);
                }

                @Override
                public void OnNetError() {
                    headOfficeView.showCordError("获取银行总行信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    headOfficeView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 获取总行对应省
     */
    class Province {
        private BankPostModel.PostProvince postProvince;
        private BankView.ProvinceView provinceView;

        public Province(BankView.ProvinceView provinceView) {
            this.provinceView = provinceView;
            postProvince = new BankPostModel.PostProvince();
        }

        public void PostProvince() {
            postProvince.postProvince(provinceView.getProvinceJsonString(), new CallBackUtils.ProvinceCallBack() {
                @Override
                public void provinceResponse(ArrayList<ResponseParamsProvinceInfo.ProvinceInfo> provList) {
                    provinceView.provinceResponse(provList);
                }

                @Override
                public void OnNetError() {
                    provinceView.showCordError("获取银行所在省信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    provinceView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 获取总行对应省对应市
     */
    class City {
        private BankPostModel.PostCity postCity;
        private BankView.CityView cityView;

        public City(BankView.CityView cityView) {
            this.cityView = cityView;
            postCity = new BankPostModel.PostCity();
        }

        public void PostCity() {
            postCity.postCity(cityView.getCityJsonString(), new CallBackUtils.CityCallBack() {
                @Override
                public void cityResponse(ArrayList<ResponseParamsCityInfo.CityInfo> cityList) {
                    cityView.cityResponse(cityList);
                }

                @Override
                public void OnNetError() {
                    cityView.showCordError("获取银行所在市信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    cityView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 获取支行信息
     */
    class Branch {
        private BankPostModel.PostBranch postBranch;
        private BankView.BranchView branchView;

        public Branch(BankView.BranchView branchView) {
            this.branchView = branchView;
            postBranch = new BankPostModel.PostBranch();
        }

        public void PostBranch() {
            postBranch.postBranch(branchView.getBranchJsonString(), new CallBackUtils.BranchCallBack() {
                @Override
                public void branchResponse(ArrayList<ResponseParamsBranchInfo.PmsBankInfo> pmsBankList) {
                    branchView.branchResponse(pmsBankList);
                }

                @Override
                public void OnNetError() {
                    branchView.showCordError("获取银行支行信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    branchView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 获取我的银行卡相关信息
     */
    class BankCard {
        private BankPostModel.PostBankCard postBankCard;
        private BankView.BankCardView bankCardView;

        public BankCard(BankView.BankCardView bankCardView) {
            this.bankCardView = bankCardView;
            postBankCard = new BankPostModel.PostBankCard();
        }

        public void PostBankCard() {
            postBankCard.postBankCard(bankCardView.getBankCardJsonString(), new CallBackUtils.BankCardCallBack() {
                @Override
                public void bankCardResponse(ArrayList<BankInfoBean> carList) {
                    bankCardView.bankCardResponse(carList);
                }

                @Override
                public void OnNetError() {
                    bankCardView.showCordError("获取银行支行信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    bankCardView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 删除银行卡
     */
    class DeleteBankCard {
        private BankPostModel.PostDeleteBankCard postDeleteBankCard;
        private BankView.DeleteBankCardView deleteBankCardView;

        public DeleteBankCard(BankView.DeleteBankCardView deleteBankCardView) {
            this.deleteBankCardView = deleteBankCardView;
            postDeleteBankCard = new BankPostModel.PostDeleteBankCard();
        }

        public void PostDeleteBankCard() {
            postDeleteBankCard.postDeleteBankCard(deleteBankCardView.getDeleteBankCardJsonString(), new CallBackUtils.DeleteBankCardCallBack() {
                @Override
                public void deleteBankCardResponse() {
                    deleteBankCardView.deleteBankCardResponse();
                }

                @Override
                public void OnNetError() {
                    deleteBankCardView.showCordError("获取银行支行信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    deleteBankCardView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 添加信用卡
     */
    class AddBankCard {
        private BankPostModel.PostAddBankCard postAddBankCard;
        private BankView.AddBankCardView addBankCardView;

        public AddBankCard(BankView.AddBankCardView addBankCardView) {
            this.addBankCardView = addBankCardView;
            postAddBankCard = new BankPostModel.PostAddBankCard();
        }

        public void PostAddBankCard() {
            postAddBankCard.postAddBankCard(addBankCardView.getAddBankCardJsonString(), new CallBackUtils.AddBankCardCallBack() {
                @Override
                public void addBankCardResponse() {
                    addBankCardView.addBankCardResponse();
                }

                @Override
                public void OnNetError() {
                    addBankCardView.showCordError("获取银行支行信息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    addBankCardView.showCordError(msg);
                }
            });
        }
    }
}

