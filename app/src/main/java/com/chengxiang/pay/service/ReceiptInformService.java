package com.chengxiang.pay.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.compat.BuildConfig;
import android.util.Log;

import com.chengxiang.pay.bean.JGPushBean;

import java.util.Locale;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/8/15 16:12
 * @description: 收款到账语音提醒//暂时用不到
 */
public class ReceiptInformService extends Service {
    public static final String TAG = "ReceiptInformService";
    private TextToSpeech speech;
    private  AudioManager mAudioManager;
    int max;//系统声音最大值
    int current;//系统当前值

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }

    private void ToSpeech(final JGPushBean jgPushBean) {
         max = mAudioManager.getStreamMaxVolume( AudioManager.STREAM_MUSIC );
         current = mAudioManager.getStreamVolume( AudioManager.STREAM_MUSIC );
        if (current<max/2){
            mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, max, 0);
        }
        speech = new TextToSpeech(ReceiptInformService.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = speech.setLanguage(Locale.CHINESE);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        if (BuildConfig.DEBUG) {
                            Log.e("ReceiptInformService", "朗读出现错误...");
                        }
                    } else {
                        speech.speak("您有一笔" + jgPushBean.getAmt() + "元收款", TextToSpeech.QUEUE_FLUSH, null);
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (speech != null) {
            speech.stop();
            speech.shutdown();
        }
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, current, 0);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        JGPushBean jgPushBean = (JGPushBean) intent.getSerializableExtra("jgPush");
        ToSpeech(jgPushBean);
        return super.onStartCommand(intent, flags, startId);
    }

}
