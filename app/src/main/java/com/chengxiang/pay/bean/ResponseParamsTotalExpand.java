package com.chengxiang.pay.bean;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/10 18:36
 * @description: 累计拓展人返回(9005)
 */


public class ResponseParamsTotalExpand extends BaseResponseParams {
    private String type;//类型0:全部，1:直接，2:间接,3:隔代，4:四级以后
    private String pageTotal;//总页数
    private String currentPage;//当前页码
    private ArrayList<ExpandUserBean> userList;//推荐用户列表

    public void setUserList(ArrayList<ExpandUserBean> userList) {
        this.userList = userList;
    }

    public ArrayList<ExpandUserBean> getUserList() {
        return userList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(String pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

}
