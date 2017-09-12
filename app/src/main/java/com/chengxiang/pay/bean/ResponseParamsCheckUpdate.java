/*
 * Copyright (c) 2013. Kevin Lee (www.buybal.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.chengxiang.pay.bean;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/29 13:56
 * @description: 检查版本升级返回参数
 */

public class ResponseParamsCheckUpdate extends BaseResponseParams {

    private String osType;//版本类型0:android 1:IOS 2: WinPhone
    private String downloadUrl;//客户端下载地址
    private String newVersion;//最新版本号
    private String isUpVersion;//版本状态0：选择更新 1：强制更新
    private String verDesc;//版本描述


    public String getOsType() {
        return osType;
    }

    public void setOsType(String osType) {
        this.osType = osType;
    }


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(String newVersion) {
        this.newVersion = newVersion;
    }

    public String getUpVersion() {
        return isUpVersion;
    }

    public void setUpVersion(String upVersion) {
        isUpVersion = upVersion;
    }

    public String getVerDesc() {
        return verDesc;
    }

    public void setVerDesc(String verDesc) {
        this.verDesc = verDesc;
    }


}
