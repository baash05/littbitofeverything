package com.oh5.baash.tinymonsters;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oh5.baash.tinymonsters.patterns.AdditionPattern;
import com.oh5.baash.tinymonsters.patterns.BasePattern;
import com.oh5.baash.tinymonsters.patterns.GreaterLessPattern;
import com.oh5.baash.tinymonsters.patterns.LetterPattern;
import com.oh5.baash.tinymonsters.patterns.NumberPattern;
import com.oh5.baash.tinymonsters.patterns.SubtractionPattern;
import com.oh5.baash.tinymonsters.util.EggShaker;
import com.oh5.baash.tinymonsters.util.ScreamingMonster;

import java.util.ArrayList;
import java.util.Random;


public class Level1 extends BaseActivity {

    static public int m_score = 0;
    protected EggShaker m_eggShaker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        setContentView(R.layout.activity_level1);

        delayedIntro(100);
        //start_count_down();


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

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        m_eggShaker = new EggShaker();
        m_score = m_eggShaker.read_view_index(this);
        ImageButton eggButton  = (ImageButton)findViewById(R.id.egg);
        m_eggShaker.set_image(eggButton);


        add_home_button();
        alter_choice_button(R.id.choice1);
        alter_choice_button(R.id.choice2);
        alter_choice_button(R.id.choice3);
        set_number_pattern();


        eggButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    protected void add_mystery_button(){
        View button = findViewById(R.id.edit_query);


    }

    protected void start_count_down(){
        final int max_seconds = 60;
        final ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
        bar.setMax(max_seconds);
        CountDownTimer count = new CountDownTimer(max_seconds * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) ((millisUntilFinished / 1000));
                bar.setProgress(seconds);
            }

            public void onFinish() {
                if( m_score > 20 ){
                    winning_toast("");
                }
                m_score = 0;
                this.start();
            }
        };
        count.start();
    }


    Handler mIntroRunner = new Handler();
    Runnable mIntroRunnable = new Runnable() {
        @Override
        public void run() {
        int sound_id = new Random().nextBoolean() ?  R.raw.k_whatnumbersmissing  :  R.raw.i_lost_a_number;
      //  MediaPlayer.create(this, sound_id).start();
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

    protected int shake_egg(){
        return m_eggShaker.shake_and_promote(this);
    }

    MediaPlayer mp = null ;
    protected void monster_notice(){
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 200, 200, 200, 200, 200,200, 200, 200};
        v.vibrate(pattern, -1);

        //create the player
        mp = MediaPlayer.create(this, R.raw.s_yahh);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaplayer) {
                m_media_over_handler.sendEmptyMessageDelayed(0, 1000);
            }
        });
        //start the sound
        mp.start();
    }
    private Handler m_media_over_handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(mp != null && msg.what == 0) {
                mp.release();
            }
        }
    };

    protected void alter_choice_button(int button_id){
        final Button button = (Button)findViewById(button_id);
        final ScreamingMonster monster = new ScreamingMonster(this, R.id.root);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setEnabled(false);
                if(view == m_right_button) {
                    if( shake_egg() != 0 ) {
                        reward_notice();
                    } else {
                        monster_notice();
                    }

                    pick_pattern();
                } else {
                    punish_notice();
                    int mControlsHeight  = view.getHeight();
                    int mShortAnimTime = getResources().getInteger(android.R.integer.config_mediumAnimTime);
                    view.animate().translationY(mControlsHeight).setDuration(mShortAnimTime);
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
        public static final int SUBTRACTION = 3;
        public static final int GREATER_LESS = 4;
        //public static final int PICTURES = 3;
        public static final int size = 5;
    }
    protected void pick_pattern(){
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
            case PatternType.SUBTRACTION:
                set_subtraction_pattern();
                break;
            case PatternType.GREATER_LESS:
                set_greater_less_pattern();
                break;
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
       // animation.setStartOffset(200);
        animation.setDuration(200);
        game_view.startAnimation(animation);
    }


    protected void reset_answer_button(int button_id){
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
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("Compare the numbers");
        clear_pattern_buttons();
        GreaterLessPattern pattern = new GreaterLessPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    protected void set_number_pattern(){
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("What number is missing?");
        clear_pattern_buttons();
        NumberPattern pattern = new NumberPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    protected void set_letter_pattern(){
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("What letter is missing?");
        clear_pattern_buttons();
        LetterPattern pattern = new LetterPattern( new Random().nextInt(2) + 5);
        fill_in_puzzle(pattern);
    }

    protected int addition_pattern_run_count = 0;
    protected void set_addition_pattern(){
        addition_pattern_run_count ++;
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("Add the numbers");
        clear_pattern_buttons();
        AdditionPattern pattern = new AdditionPattern(addition_pattern_run_count < 20 ? 5: addition_pattern_run_count/4);
        fill_in_puzzle(pattern);
    }
    protected int subtraction_pattern_run_count = 0;
    private void set_subtraction_pattern(){
        subtraction_pattern_run_count ++;
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("Subtract the numbers");
        clear_pattern_buttons();
        SubtractionPattern pattern = new SubtractionPattern(subtraction_pattern_run_count < 20 ? 5: subtraction_pattern_run_count/4);
        fill_in_puzzle(pattern);
    }

    protected void fill_in_puzzle(BasePattern pattern){
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

    protected void winning_toast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.next_level_toast,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText("This is a custom toast");

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    protected void set_picture_pattern(){}
}
