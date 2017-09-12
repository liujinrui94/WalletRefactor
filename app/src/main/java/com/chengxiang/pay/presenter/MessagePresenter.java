package com.chengxiang.pay.presenter;

import com.chengxiang.pay.bean.MessageBean;
import com.chengxiang.pay.model.CallBackUtils;
import com.chengxiang.pay.model.MessagePostModel;
import com.chengxiang.pay.view.MessageView;

import java.util.ArrayList;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/7/29 9:47
 * @description: 消息相关请求层
 */


public interface MessagePresenter {
    /**
     * 消息列表
     */
    class MessageList {
        private MessagePostModel.PostMessageList postMessageList;
        private MessageView.MessageListView messageListView;

        public MessageList(MessageView.MessageListView messageListView) {
            this.messageListView = messageListView;
            postMessageList = new MessagePostModel.PostMessageList();
        }

        public void PostMessageList() {
            postMessageList.postMessageList(messageListView.getMessageListJsonString(), new CallBackUtils.MessageListCallBack() {
                @Override
                public void messageListResponse(ArrayList<MessageBean> messageList) {
                    messageListView.messageListResponse(messageList);
                }

                @Override
                public void OnNetError() {
                    messageListView.showCordError("获取消息列表网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    messageListView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 消息详情（调用后消息标记为已读）
     */
    class MessageDetail {
        private MessagePostModel.PostMessageDetail postMessageDetail;
        private MessageView.MessageDetailView messageDetailView;

        public MessageDetail(MessageView.MessageDetailView messageDetailView) {
            this.messageDetailView = messageDetailView;
            postMessageDetail = new MessagePostModel.PostMessageDetail();
        }

        public void PostMessageDetail() {
            postMessageDetail.postMessageDetail(messageDetailView.getMessageDetailJsonString(), new CallBackUtils.MessageDetailCallBack() {
                @Override
                public void messageDetailResponse() {
                    messageDetailView.messageDetailResponse();
                }

                @Override
                public void OnNetError() {
                    messageDetailView.showCordError("查看消息详情网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    messageDetailView.showCordError(msg);
                }
            });
        }
    }

    /**
     * 编辑消息（已读、删除）
     */
    class MessageModify {
        private MessagePostModel.PostMessageModify postMessageModify;
        private MessageView.MessageModifyView messageModifyView;

        public MessageModify(MessageView.MessageModifyView messageModifyView) {
            this.messageModifyView = messageModifyView;
            postMessageModify = new MessagePostModel.PostMessageModify();
        }

        public void PostMessageModify() {
            postMessageModify.postMessageModify(messageModifyView.getMessageModifyJsonString(), new CallBackUtils.MessageModifyCallBack() {
                @Override
                public void messageModifyResponse() {
                    messageModifyView.messageModifyResponse();
                }

                @Override
                public void OnNetError() {
                    messageModifyView.showCordError("编辑消息网络连接异常");
                }

                @Override
                public void CodeError(String msg) {
                    messageModifyView.showCordError(msg);
                }
            });
        }
    }


}

