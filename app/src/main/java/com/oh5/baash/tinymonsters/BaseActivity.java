package com.oh5.baash.tinymonsters;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by davidrawk on 3/01/15.
 */
public class BaseActivity extends Activity {

    protected TextView m_title_bar = null;
    protected Handler mHideHandler = new Handler();
    protected MediaPlayer player = null;

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
        m_title_bar = (TextView)findViewById(com.oh5.baash.tinymonsters.R.id.title_bar);
        hide_navigation_bar();
    }

    protected void hide_navigation_bar() {
        int api_version = android.os.Build.VERSION.SDK_INT;
        if (api_version >= Build.VERSION_CODES.KITKAT){
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        }
    }


    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide_navigation_bar();
        };
    };

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus){
            mHideHandler.post(mHideRunnable);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();


    }

    private static ArrayList m_right_answer_voice = null;
    private static ArrayList m_wrong_answer_voice = null;
    protected void init_answer_voice(){
        m_right_answer_voice = new ArrayList();
        m_right_answer_voice.add( R.raw.good_job);
        m_right_answer_voice.add( R.raw.k_awesome);
        m_right_answer_voice.add( R.raw.k_doitagain);
        m_right_answer_voice.add( R.raw.k_great_job);
        m_right_answer_voice.add( R.raw.k_super_star);
        m_right_answer_voice.add( R.raw.s_doitagain);
        m_right_answer_voice.add( R.raw.s_good_job);
        m_right_answer_voice.add( R.raw.s_great_job);
        m_right_answer_voice.add( R.raw.s_yourasuperstar);
        m_right_answer_voice.add( R.raw.s_thatwasagoodpick);
        m_wrong_answer_voice = new ArrayList();
        m_wrong_answer_voice.add(R.raw.k_awwh);
        m_wrong_answer_voice.add(R.raw.s_awwh);
        m_wrong_answer_voice.add(R.raw.k_ohohwronganswer);
        m_wrong_answer_voice.add(R.raw.k_ohohwronganswer);
        m_wrong_answer_voice.add(R.raw.k_ohohwronganswer);
        m_wrong_answer_voice.add(R.raw.k_awwh);
    }

    protected void reward_notice(){
        if( m_right_answer_voice == null) init_answer_voice();
        Integer sound_id =  (Integer) m_right_answer_voice.get(new Random().nextInt(m_right_answer_voice.size()));
        MediaPlayer.create(this, sound_id).start();

        Vibrator v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 200, 100, 100};
        v.vibrate(pattern, -1);
    }

    protected void punish_notice(){
        if( m_wrong_answer_voice == null) init_answer_voice();
        Integer sound_id = (Integer)m_wrong_answer_voice.get(new Random().nextInt(m_wrong_answer_voice.size()));
        MediaPlayer.create(this, sound_id).start();
    }


}
