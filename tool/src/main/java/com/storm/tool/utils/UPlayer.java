package com.storm.tool.utils;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;

import com.storm.tool.listener.IVoiceManager;


/**
 * @创建作者 Storm
 * @创建时间 2017-12-04 23:47
 * @创建描述 ${播放器}
 */

public class UPlayer implements IVoiceManager {

    private final String TAG = UPlayer.class.getName();
    private String path;

    private MediaPlayer mPlayer;
    private OnCompletionListener listener;

    public UPlayer(String path, OnCompletionListener listener) {
        this.path = path;
        mPlayer = new MediaPlayer();
        this.listener = listener;
    }


    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public boolean start() {
        try {
            if (mPlayer != null){
                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.release();
                }
            }else {
                mPlayer = new MediaPlayer();
            }

            //设置要播放的文件
            mPlayer.setDataSource(path);
            mPlayer.prepare();
            //播放
            mPlayer.start();
            mPlayer.setOnCompletionListener(listener);
        } catch (Exception e) {
            Log.e(TAG, "prepare() failed");
        }

        return false;
    }

    @Override
    public boolean stop() {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
        return false;
    }

}