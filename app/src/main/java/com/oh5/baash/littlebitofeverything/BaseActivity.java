package com.oh5.baash.littlebitofeverything;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.oh5.baash.littlebitofeverything.util.util.SystemUiHider;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by davidrawk on 3/01/15.
 */
public class BaseActivity extends Activity {

    private SystemUiHider mSystemUiHider;
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onConfigurationChanged(newConfig);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    protected void hide_navigation_bar(int view_id){
        if(false) {
            final View contentView = findViewById(view_id);
            mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
            mSystemUiHider.setup();
            mSystemUiHider.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                @Override
                @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                public void onVisibilityChange(boolean visible) {
                    if (visible) {
                        delayedHide(1000);
                    }
                }
            });
        }
    }

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };
    protected void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    protected void add_home_button(){
        View button = findViewById(R.id.home_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private static ArrayList m_write_answer_voice = null;
    private static ArrayList m_wrong_answer_voice = null;

    protected void init_answer_voice(){
        m_write_answer_voice = new ArrayList();
        m_write_answer_voice.add(MediaPlayer.create(this, R.raw.k_great_job));
        m_write_answer_voice.add(MediaPlayer.create(this, R.raw.k_great_job));
        m_write_answer_voice.add(MediaPlayer.create(this, R.raw.s_yahh));
        m_write_answer_voice.add(MediaPlayer.create(this, R.raw.s_yahh));
        m_write_answer_voice.add(MediaPlayer.create(this, R.raw.s_yourasuperstar));
        m_write_answer_voice.add(MediaPlayer.create(this, R.raw.k_doitagain));
        m_write_answer_voice.add(MediaPlayer.create(this, R.raw.s_thatwasagoodpick));
        m_wrong_answer_voice = new ArrayList();
        m_wrong_answer_voice.add(MediaPlayer.create(this, R.raw.k_awwh));
        m_wrong_answer_voice.add(MediaPlayer.create(this, R.raw.s_awwh));
        m_wrong_answer_voice.add(MediaPlayer.create(this, R.raw.k_ohohwronganswer));
        m_wrong_answer_voice.add(MediaPlayer.create(this, R.raw.k_awwh));
        m_wrong_answer_voice.add(MediaPlayer.create(this, R.raw.k_whatnumbersmissing));
    }

    protected int m_right_answer_count = 0;
    protected int m_wrong_answer_count = 0;
    static final ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    protected void play_bell_sound(){
        m_right_answer_count ++;
        MediaPlayer mp;
        if( m_write_answer_voice == null) init_answer_voice();
        mp = (MediaPlayer)m_write_answer_voice.get(new Random().nextInt(m_write_answer_voice.size()));
        mp.start();
    }

    protected void play_bad_sound(){
        m_wrong_answer_count ++;
        MediaPlayer mp;
        if( m_wrong_answer_voice == null) init_answer_voice();
        mp = (MediaPlayer)m_wrong_answer_voice.get(new Random().nextInt(m_wrong_answer_voice.size()));
        mp.start();
    }



}
