package com.oh5.baash.littlebitofeverything;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oh5.baash.littlebitofeverything.util.NumberPattern;

import java.util.ArrayList;
import java.util.Random;


public class Patterns extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_patterns);

        hide_navigation_bar(R.id.patterns_layout);
        add_home_button();
        alter_choice_button(R.id.choice1);
        alter_choice_button(R.id.choice2);
        alter_choice_button(R.id.choice3);
        pick_pattern();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        MediaPlayer mp = MediaPlayer.create(this, R.raw.k_whatnumbersmissing);
        mp.start();

    }

    private void alter_choice_button(int button_id){
        Button button = (Button)findViewById(button_id);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(view == m_right_button) {
                    play_bell_sound();
                    pick_pattern();
                    reset_answer_button(R.id.choice1);
                    reset_answer_button(R.id.choice2);
                    reset_answer_button(R.id.choice3);
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
        public static final int PICTURES = 2;
        public static final int size = 3;
    }
    private void pick_pattern(){
        int pattern_type = new Random().nextInt(PatternType.size);
        switch(pattern_type){
            case PatternType.NUMBERS:
                set_number_pattern();
                break;
            case PatternType.LETTERS:
                set_number_pattern();//set_letter_pattern();
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
    }

    private ArrayList m_pattern_buttons;
    private void clear_pattern_buttons(){
        if( m_pattern_buttons != null) {
            while (m_pattern_buttons.size() > 0) {
                Button button = (Button) m_pattern_buttons.remove(0);
                ViewGroup layout = (ViewGroup) button.getParent();
                if (null != layout) {
                    layout.removeView(button);
                }
            }
        }
    }

    Button m_right_button = null;
    private void set_number_pattern(){
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("What number is missing?");
        clear_pattern_buttons();
        NumberPattern pattern = new NumberPattern( new Random().nextInt(2) + 5);
        for(int x = 0; x < pattern.size(); x ++) {
            if (pattern.get_pattern()[x] != pattern.m_right_answer) {
                add_button_value(pattern.get_pattern()[x]);
            } else {
                add_button_value(-1);
            }
        }
        Button button1 = (Button)findViewById(R.id.choice1);
        Button button2 = (Button)findViewById(R.id.choice2);
        Button button3 = (Button)findViewById(R.id.choice3);
        button1.setText("" + pattern.get_answers()[0]);
        button2.setText("" + pattern.get_answers()[1]);
        button3.setText("" + pattern.get_answers()[2]);
        if( pattern.get_answers()[0] == pattern.m_right_answer)
            m_right_button = button1;
        else if( pattern.get_answers()[1] == pattern.m_right_answer)
            m_right_button = button2;
        else
            m_right_button = button3;

    }

    private void add_button_value(int image_id){
        Button btn = new Button(this);
        if(image_id >= 0) {
            btn.setText("" + image_id);
            btn.setBackgroundColor(Color.GREEN);
        }
        else {
            btn.setText("    ");
            btn.setBackgroundColor(Color.LTGRAY);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(10, 0, 10, 0);
        btn.setLayoutParams(params);
        btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.patterns_box);
        linearLayout.addView(btn);
        if(m_pattern_buttons == null){
            m_pattern_buttons = new ArrayList();
        }
        m_pattern_buttons.add(btn);
    }

    private void set_letter_pattern(){
        TextView title = (TextView)findViewById(R.id.title_bar);
        title.setText("What letter is missing?");
    }

    private void set_picture_pattern(){}
}
