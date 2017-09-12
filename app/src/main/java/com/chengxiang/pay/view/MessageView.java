package com.chengxiang.pay.view;

import com.chengxiang.pay.bean.MessageBean;
import com.chengxiang.pay.framework.base.BaseView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/6/26 17:41
 * @description: 消息相关
 */


public interface MessageView {

    /**
     * 消息列表
     */
    interface MessageListView extends BaseView {

        String getMessageListJsonString();

        void messageListResponse(ArrayList<MessageBean> messageList);

    }

    /**
     * 消息详情（调用后消息标记为已读）
     */
    interface MessageDetailView extends BaseView {

        String getMessageDetailJsonString();

        void messageDetailResponse();

    }

    /**
     * 编辑消息（已读、删除）
     */
    interface MessageModifyView extends BaseView {

        String getMessageModifyJsonString();

        void messageModifyResponse();

    }

}