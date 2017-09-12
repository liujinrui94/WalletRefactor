package com.chengxiang.pay.bean;

import java.util.List;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/22 18:42
 * @description: 请求用户升级条件响应
 */


public class ResponseParamsUpgradeCondition extends BaseResponseParams {

    private String levelName;//当前级别名称
    private String feeRate;//当前级别费率
    private List<LevelInformation> levelList;//级别list

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(String feeRate) {
        this.feeRate = feeRate;
    }

    public List<LevelInformation> getLevelList() {
        return levelList;
    }

    public void setLevelList(List<LevelInformation> levelList) {
        this.levelList = levelList;
    }

    public class LevelInformation {
        private String levelName;//级别名称
        private String levelId;//级别id
        private String levelRate;//级别费率
        private String proAmt;//预计下月收益
        private String unAmt;//升级金额
        private String upUserNum;//升级人数
        private String upOrdAmt;//升级交易额

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public String getLevelRate() {
            return levelRate;
        }

        public void setLevelRate(String levelRate) {
            this.levelRate = levelRate;
        }

        public String getProAmt() {
            return proAmt;
        }

        public void setProAmt(String proAmt) {
            this.proAmt = proAmt;
        }

        public String getUnAmt() {
            return unAmt;
        }

        public void setUnAmt(String unAmt) {
            this.unAmt = unAmt;
        }

        public String getUpUserNum() {
            return upUserNum;
        }

        public void setUpUserNum(String upUserNum) {
            this.upUserNum = upUserNum;
        }

        public String getUpOrdAmt() {
            return upOrdAmt;
        }

        public void setUpOrdAmt(String upOrdAmt) {
            this.upOrdAmt = upOrdAmt;
        }
    }
}
