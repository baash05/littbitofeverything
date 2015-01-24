package com.oh5.baash.tinymonsters.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.oh5.baash.tinymonsters.R;

/**
 * Created by davidrawk on 21/01/15.
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
        }
        store_view_index(parent);
        return m_imageIndex;
    }

    static Point m_image_dimensions = null;
    public Point get_image_dimensions(Activity parent){
        if(m_image_dimensions == null){
            Drawable d = parent.getResources().getDrawable(R.drawable.screaming_monster);
            int image_h = d.getIntrinsicHeight();
            int image_w = d.getIntrinsicWidth();
            m_image_dimensions = new Point(image_w, image_h);
        }
        return m_image_dimensions;
    }

    public void monster_run(Activity parent){
        final ImageView view = (ImageView) parent.findViewById(R.id.egg);
        set_image(view);
        final Activity final_parent = parent;
        int image_top = view.getTop();
        int start_x = view.getLeft();
        int width = 0;
        Point size = new Point();
        WindowManager w = parent.getWindowManager();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)    {
            w.getDefaultDisplay().getSize(size);
            width = size.x;
        }else{
            Display d = w.getDefaultDisplay();
            width = d.getWidth();
        }

        ViewParent layout = view.getParent();
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
            case 5:  view.setBackgroundResource( R.drawable.egg_crack_5_eyes) ; break;
            case 6:  view.setBackgroundResource( R.drawable.egg_crack_6) ; break;
            case 7:  view.setBackgroundResource( R.drawable.egg_crack_7) ; break;
            case 8:  view.setBackgroundResource( R.drawable.screaming_monster) ; break;
        }
    }

}
