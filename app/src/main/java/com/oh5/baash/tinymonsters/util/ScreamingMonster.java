package com.oh5.baash.tinymonsters.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.oh5.baash.tinymonsters.R;

/**
 * Created by davidrawk on 4/01/15.
 */
public class ScreamingMonster {

    private Context m_context;
    private int m_main_view_id;

    public ScreamingMonster(Context i_context, int i_main_view_id){
        m_context = i_context;
        m_main_view_id = i_main_view_id;
    }
    static Point m_image_dimensions = null;
    public Point get_image_dimensions(){
        if(m_image_dimensions == null){
            Drawable d = m_context.getResources().getDrawable(R.drawable.screaming_monster);
            int image_h = d.getIntrinsicHeight();
            int image_w = d.getIntrinsicWidth();
            m_image_dimensions = new Point(image_w, image_h);
        }
        return m_image_dimensions;
    }
    public void run(){
        //create the button off screen
        final ImageView iv = new ImageView(m_context);
        iv.setBackgroundResource(R.drawable.screaming_monster);
        iv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        //animate the button
        final RelativeLayout layout = (RelativeLayout) ((Activity)m_context).getWindow().getDecorView().findViewById(m_main_view_id);

        layout.addView(iv);
        int image_top = layout.getHeight() - get_image_dimensions().y;
        int image_end_x = -get_image_dimensions().x - 10;

        TranslateAnimation animation = new TranslateAnimation(image_end_x, layout.getWidth() + 10, image_top, image_top);
        animation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationRepeat(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                layout.removeView(iv);
            }
        });

        animation.setDuration(2000);
        iv.startAnimation(animation);

        Vibrator v = (Vibrator) m_context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 200, 200, 200, 200, 200,200, 200, 200};
        v.vibrate(pattern, -1);

        //create the player
        MediaPlayer mp = MediaPlayer.create(m_context, R.raw.s_yahh);
        //start the sound
        mp.start();
    }
}
