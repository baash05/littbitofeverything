package com.oh5.baash.littlebitofeverything;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oh5.baash.littlebitofeverything.patterns.AdditionPattern;
import com.oh5.baash.littlebitofeverything.patterns.PatternsBase;
import com.oh5.baash.littlebitofeverything.patterns.LetterPattern;
import com.oh5.baash.littlebitofeverything.patterns.NumberPattern;
import com.oh5.baash.littlebitofeverything.util.ScreamingMonster;

import java.util.ArrayList;
import java.util.Random;


public class Patterns extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patterns);

        add_home_button();
        alter_choice_button(R.id.choice1);
        alter_choice_button(R.id.choice2);
        alter_choice_button(R.id.choice3);
        set_number_pattern();

        hide_navigation_bar(R.id.patterns_layout);
        delayedIntro(100);
    }

    Handler mIntroRunner = new Handler();
    Runnable mIntroRunnable = new Runnable() {
        @Override
        public void run() {
            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.k_whatnumbersmissing);
            mp.start();
        }
    };
    protected void delayedIntro(int delayMillis) {
        mIntroRunner.removeCallbacks(mIntroRunnable);
        mIntroRunner.postDelayed(mIntroRunnable, delayMillis);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    private static int m_right_answer_count = 0;
    private void alter_choice_button(int button_id){
        final Button button = (Button)findViewById(button_id);
        final ScreamingMonster monster = new ScreamingMonster(this, R.id.patterns_layout);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                if(view == m_right_button) {
                    if( m_right_answer_count < 20) {
                        if (m_right_answer_count % 3 == 0)
                            monster.run();
                        else
                            play_bell_sound();
                    } else {
                        if(new Random().nextInt(3) == 0 )
                            monster.run();
                        else
                            play_bell_sound();
                    }
                    m_right_answer_count ++;
                    pick_pattern();
                } else {
                    play_bad_sound();
                    int mControlsHeight  = view.getHeight();
                    int mShortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);
                    view.animate()
                            .translationY(mControlsHeight)
                            .setDuration(mShortAnimTime);
                }

            }
        });
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
        button.setTextColor(Color.BLACK);
    }

    static class PatternType {
        public static final int NUMBERS = 0;
        public static final int LETTERS = 1;
        public static final int ADDITION = 2;
        public static final int PICTURES = 3;
        public static final int size = 4;
    }
    private void animate_game_view(){
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

    private void pick_pattern(){
        animate_game_view();
        int pattern_type = new Random().nextInt(PatternType.size);
        switch(pattern_type){
            case PatternType.NUMBERS:
                set_number_pattern();
                break;
            case PatternType.LETTERS:
                set_letter_pattern();
                break;
            case PatternType.ADDITION:
                set_addition_pattern();
                break;
            case PatternType.PICTURES:
                set_number_pattern();//set_picture_pattern();
                break;
        }
    }
    private void reset_answer_button(int button_id){
        View view = findViewById(button_id);
        int mControlsHeight  = view.getHeight();
        int mShortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
        view.animate()
                .translationY(0)
                .setDuration(mShortAnimTime);
        view.setEnabled(true);
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

    Button m_right_button = null;


    private void add_puzzle_element(String text){
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.patterns_box);
        final LayoutInflater mLayoutInflater = (LayoutInflater) linearLayout.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View box = mLayoutInflater.inflate(R.layout.puzzle_bit_text_box, null);
        TextView text_fld = (TextView)box.findViewById(R.id.text_field);
        if(text != null)
          text_fld.setText(text);
        else{
          text_fld.setText(" ? ");
          text_fld.setTextColor(0xFF000000);
          box.setBackgroundColor(0x35FFFFFF);
        }
        linearLayout.addView(box);
        m_pattern_buttons.add(box);
    }

    private void set_number_pattern(){
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("What number is missing?");
        clear_pattern_buttons();
        NumberPattern pattern = new NumberPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    private void set_letter_pattern(){
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("What letter is missing?");
        clear_pattern_buttons();
        LetterPattern pattern = new LetterPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    private int addition_pattern_run_count = 0;
    private void set_addition_pattern(){
        addition_pattern_run_count ++;
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("Add the numbers");
        clear_pattern_buttons();
        AdditionPattern pattern = new AdditionPattern(addition_pattern_run_count < 20 ? 5: addition_pattern_run_count/2);
        fill_in_puzzle(pattern);
    }

    private void fill_in_puzzle(PatternsBase pattern){
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
        reset_answer_button(R.id.choice1);
        reset_answer_button(R.id.choice2);
        reset_answer_button(R.id.choice3);

        if( pattern.get_answers()[0].equals(right_answer) )
            m_right_button = button1;
        else if( pattern.get_answers()[1].equals(right_answer))
            m_right_button = button2;
        else
            m_right_button = button3;
    }

    private void set_picture_pattern(){}
}
