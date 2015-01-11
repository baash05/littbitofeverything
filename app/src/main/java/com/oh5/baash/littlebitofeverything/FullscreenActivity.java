package com.oh5.baash.littlebitofeverything;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;



public class FullscreenActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);

        hide_navigation_bar(R.id.full_screen);
        add_kindy_button();
        add_kindy_button2();
    }


    private void add_kindy_button(){
        final View kaley_head = findViewById(R.id.kindyButton);
        kaley_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), Kindy.class));
            }
        });
        final View kaley_button = findViewById(R.id.miss_kaley_button);
        kaley_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Kindy.class));
            }
        });
    }

    private void add_kindy_button2(){
        final View simon_head = findViewById(R.id.kindyButton2);
        simon_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Kindy2.class));
            }
        });
        final View simon_button = findViewById(R.id.mr_simon_button);
        simon_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Kindy2.class));
            }
        });
    }



}
