package com.oh5.baash.littlebitofeverything;

import android.os.Bundle;


public class Kindy2 extends Kindy {

    static public int m_score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_kindy2);

        add_home_button();
        alter_choice_button(R.id.choice1);
        alter_choice_button(R.id.choice2);
        alter_choice_button(R.id.choice3);
        set_number_pattern();

        hide_navigation_bar(R.id.patterns_layout);
        delayedIntro(100);
    }

}
