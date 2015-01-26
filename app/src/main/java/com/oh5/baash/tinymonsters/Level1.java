package com.oh5.baash.tinymonsters;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oh5.baash.tinymonsters.patterns.AdditionPattern;
import com.oh5.baash.tinymonsters.patterns.BasePattern;
import com.oh5.baash.tinymonsters.patterns.GreaterLessPattern;
import com.oh5.baash.tinymonsters.patterns.LetterPattern;
import com.oh5.baash.tinymonsters.patterns.NumberPattern;
import com.oh5.baash.tinymonsters.patterns.SubtractionPattern;
import com.oh5.baash.tinymonsters.util.EggShaker;

import java.util.ArrayList;
import java.util.Random;


public class Level1 extends BaseActivity {

    protected EggShaker m_eggShaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        setContentView(R.layout.activity_level1);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        m_eggShaker = new EggShaker();

        set_number_pattern();
        add_button_events();
    }

    protected void add_button_events(){
        ImageButton button;

        //add the home button handler
        button = (ImageButton)findViewById(R.id.home_button);
        button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });

        //add the egg buttons handler
        button  = (ImageButton)findViewById(R.id.egg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                play_egg_touch_sound();
            }
        });
    }

    protected void play_egg_touch_sound(){
        m_eggShaker.read_view_index(this);
        MediaPlayer mp = MediaPlayer.create(this, R.raw.s_yahh);
        mp.start();
    }

    protected void right_answer_selected(View button){
        button.setEnabled(false);
        if( shake_egg() != 0 ) reward_notice();
        else                   monster_notice();
        pick_pattern();
    }

    protected void wrong_answer_selected(View button){
        punish_notice();
        button.setEnabled(false);
        int height = button.getHeight();
        button.animate().translationY(height).setDuration(500);
    }

    protected int shake_egg(){
        return m_eggShaker.shake_and_promote(this);
    }

    protected void monster_notice(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 200, 200, 200, 200, 200,200, 200, 200};
        v.vibrate(pattern, -1);

        //create the player
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.s_yahh);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            Handler m_media_over_handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if (mp != null && msg.what == 0)
                        mp.release();
                }
            };
            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                m_media_over_handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
        mp.start();
    }

    static class PatternType {
        public static final int NUMBERS = 0;
        public static final int LETTERS = 1;
        public static final int ADDITION = 2;
        public static final int SUBTRACTION = 3;
        public static final int GREATER_LESS = 4;
        public static final int size = 5;
    }

    protected void pick_pattern(){
        animate_game_view();
        int pattern_type = new Random().nextInt(PatternType.size);
        switch(pattern_type){
            case PatternType.NUMBERS:       set_number_pattern();       break;
            case PatternType.LETTERS:       set_letter_pattern();       break;
            case PatternType.ADDITION:      set_addition_pattern();     break;
            case PatternType.SUBTRACTION:   set_subtraction_pattern();  break;
            case PatternType.GREATER_LESS:  set_greater_less_pattern(); break;
        }
    }

    protected void animate_game_view(){
        final View game_view = findViewById(R.id.game);
        game_view.setEnabled(false);
        int view_height  = game_view.getHeight();
        TranslateAnimation animation = new TranslateAnimation(0,0, view_height, 0);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                game_view.setEnabled(true);
            }
        });
        animation.setDuration(200);
        game_view.startAnimation(animation);
    }

    private ArrayList m_pattern_buttons = new ArrayList();
    private void clear_pattern_buttons(){
        for( int x = m_pattern_buttons.size() - 1; x >= 0; x--){
            View button = (View) m_pattern_buttons.remove(x);
            ViewGroup layout = (ViewGroup) button.getParent();
            if (null != layout) {
                layout.removeView(button);
            }
        }
    }

    protected void add_puzzle_element(String text){
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.patterns_box);
        final LayoutInflater mLayoutInflater = (LayoutInflater) linearLayout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View box = null;
        if(text != null) {
          box = mLayoutInflater.inflate(R.layout.puzzle_bit_text_box, null);
          TextView text_fld = (TextView)box.findViewById(R.id.text_field);
          text_fld.setText(text);
        }else{
          box = mLayoutInflater.inflate(R.layout.puzzle_bit_text_box_answer, null);
        }
        linearLayout.addView(box);
        m_pattern_buttons.add(box);
    }

    protected void set_greater_less_pattern(){
        GreaterLessPattern pattern = new GreaterLessPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    protected void set_number_pattern(){
        NumberPattern pattern = new NumberPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    protected void set_letter_pattern(){
        LetterPattern pattern = new LetterPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    protected int addition_pattern_run_count = 0;
    protected void set_addition_pattern(){
        addition_pattern_run_count ++;
        AdditionPattern pattern = new AdditionPattern(addition_pattern_run_count < 20 ? 5: addition_pattern_run_count/4);
        fill_in_puzzle(pattern);
    }
    protected int subtraction_pattern_run_count = 0;
    private void set_subtraction_pattern(){
        subtraction_pattern_run_count ++;
        SubtractionPattern pattern = new SubtractionPattern(subtraction_pattern_run_count < 20 ? 5: subtraction_pattern_run_count/4);
        fill_in_puzzle(pattern);
    }

    protected void fill_in_puzzle(BasePattern pattern){
        clear_pattern_buttons();
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText(pattern.description);
        String [] puzzle = pattern.get_puzzle();
        String right_answer = pattern.get_right_answer();
        for(int x = 0; x < pattern.get_size(); x ++) {
            if(puzzle[x].equals(right_answer))
                add_puzzle_element(null);
            else
                add_puzzle_element(puzzle[x]);
        }
        Button button1 = (Button)findViewById(R.id.choice1);
        Button button2 = (Button)findViewById(R.id.choice2);
        Button button3 = (Button)findViewById(R.id.choice3);
        button1.setText("" + pattern.get_answers()[0]);
        button2.setText("" + pattern.get_answers()[1]);
        button3.setText("" + pattern.get_answers()[2]);
        button1.animate().translationY(0).setDuration(500);
        button2.animate().translationY(0).setDuration(500);
        button3.animate().translationY(0).setDuration(500);
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrong_answer_selected(view);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrong_answer_selected(view);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                wrong_answer_selected(view);
            }
        });

        if( pattern.get_answers()[0].equals(right_answer) )
            button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_answer_selected(view);
            }
        });
        else if( pattern.get_answers()[1].equals(right_answer))
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {right_answer_selected(view);
                }
            });
        else
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {right_answer_selected(view);
                }
            });
    }
}
