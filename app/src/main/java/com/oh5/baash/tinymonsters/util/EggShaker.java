package com.oh5.baash.tinymonsters.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.oh5.baash.tinymonsters.R;

/**
 * Created by david rawk on 21/01/15.
 */
public class EggShaker {

    int m_imageIndex = 0;
    public static final int MAX_INDEX = 8 ;


    public int read_view_index(Activity parent){
        SharedPreferences sharedPref = parent.getPreferences(Context.MODE_PRIVATE);
        m_imageIndex = sharedPref.getInt("egg_index", 0);
        return m_imageIndex;
    }

    public void store_view_index(Activity parent){
        SharedPreferences sharedPref = parent.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("egg_index", m_imageIndex);
        editor.commit();
    }

    public void reset_image(Activity parent){
        m_imageIndex = 0;
        ImageView view  = (ImageView) parent.findViewById(R.id.egg);
        view.setVisibility(View.INVISIBLE);
        set_image(view);
        view.setVisibility(View.VISIBLE);
        store_view_index( parent );
    }

    public int shake_and_promote(Activity parent){
        final ImageView view = (ImageView) parent.findViewById(R.id.egg);

        m_imageIndex++;

        if (m_imageIndex < MAX_INDEX) {
            Animation animator = AnimationUtils.loadAnimation(parent, R.anim.shakeimage);
            view.setVisibility(View.VISIBLE);
            animator.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    set_image(view);
                }
            });
            view.startAnimation(animator);
        } else {
            monster_run(parent);
            m_imageIndex = 0;
        }
        store_view_index(parent);
        return m_imageIndex;
    }

    public void monster_run(Activity parent){
        final ImageView view = (ImageView) parent.findViewById(R.id.egg);
        set_image(view);
        final Activity final_parent = parent;

        Animation animator = AnimationUtils.loadAnimation(parent, R.anim.slid_image);
        animator.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                reset_image(final_parent);
            }
        });
        view.startAnimation(animator);
        m_imageIndex = 0;
    }

    public void set_image(ImageView view){
        switch (m_imageIndex){
            case 0:  view.setBackgroundResource( R.drawable.egg_crack_0 ); break;
            case 1:  view.setBackgroundResource( R.drawable.egg_crack_1) ; break;
            case 2:  view.setBackgroundResource( R.drawable.egg_crack_2) ; break;
            case 3:  view.setBackgroundResource( R.drawable.egg_crack_3) ; break;
            case 4:  view.setBackgroundResource( R.drawable.egg_crack_4) ; break;
            case 5:  view.setBackgroundResource( R.drawable.egg_crack_5) ; break;
            case 6:  view.setBackgroundResource( R.drawable.egg_crack_6) ; break;
            case 7:  view.setBackgroundResource( R.drawable.egg_crack_7) ; break;
            case 8:  view.setBackgroundResource( R.drawable.screaming_monster) ; break;
        }
    }
}
