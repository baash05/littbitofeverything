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
        add_pattern_button();
    }


    private void add_pattern_button(){
        final View pattern_button = findViewById(R.id.patternButton);
        pattern_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getApplicationContext(), Patterns.class));
            }
        });
    }




}
