package com.chengxiang.pay.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.widget.TextView;

import com.chengxiang.pay.R;
import com.chengxiang.pay.bean.JGPushBean;
import com.chengxiang.pay.framework.base.BaseActivity;
import com.chengxiang.pay.framework.constant.Common;
import com.chengxiang.pay.framework.utils.StringUtil;
import com.chengxiang.pay.framework.utils.ToastUtils;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import java.util.Locale;

/**
 * @author: FengWenyao
 * @email: fengwenyao@qdcftx.com
 * @time: 2017/8/15 12:19
 * @description: 收款结果
 */

public class GatheringResultActivity extends BaseActivity {

    private JGPushBean jgPushBean;
    private Boolean isSpeech;
    private TextToSpeech speech;
    private AudioManager mAudioManager;
    int max = 0;//系统声音最大值
    int current = 0;//系统当前值

    private SpeechSynthesizer mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gathering_result);
        jgPushBean = (JGPushBean) getIntent().getSerializableExtra("jgPush");
        isSpeech = getIntent().getBooleanExtra("isSpeech", false);
        initView();
        initEvent();
    }

    private void initEvent() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if (isSpeech && sharedPreferences.getBoolean(Common.VOICE, true)) {
            ToSpeech();
        }
    }

    private void initView() {
        setTitle("收款结果");
        mTts = SpeechSynthesizer.createSynthesizer(GatheringResultActivity.this, mTtsInitListener);
        TextView amountTv = (TextView) findViewById(R.id.gathering_amount_tv);
        TextView timeTv = (TextView) findViewById(R.id.gathering_time_tv);
        amountTv.setText("收款金额：￥" + StringUtil.parseAmountLong2Str(Long.parseLong(jgPushBean.getAmt())) + "元");
        timeTv.setText("收款时间：" + jgPushBean.getOrderTime());

    }

    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                ToastUtils.showShortToast("初始化失败,错误码：" + code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    private void XFToSpeech(String text) {
        int code = mTts.startSpeaking(text, mTtsListener);
        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //未安装则跳转到提示安装页面
            } else {
                ToastUtils.showShortToast("语音提醒失败," + code);
            }
        }

    }


    private void ToSpeech() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        current = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (current < max / 2) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, 0);
        }

        speech = new TextToSpeech(GatheringResultActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = speech.setLanguage(Locale.CHINESE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        XFToSpeech("您有一笔" + StringUtil.parseAmountLong2Str(Long.parseLong(jgPushBean.getAmt())) + "元收款");
                    } else {
                        speech.speak("您有一笔" + StringUtil.parseAmountLong2Str(Long.parseLong(jgPushBean.getAmt())) + "元收款", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        });
    }


    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
//            showTip("开始播放");
        }

        @Override
        public void onSpeakPaused() {
//            showTip("暂停播放");
        }

        @Override
        public void onSpeakResumed() {
//            showTip("继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
//                showTip("播放完成");
            } else if (error != null) {
//                showTip(error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    @Override
    public void onDestroy() {
        if (speech != null) {
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, 0);
            speech.stop();
            speech.shutdown();
        }
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }

        super.onDestroy();
    }
}
