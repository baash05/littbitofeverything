package com.oh5.baash.tinymonsters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;


public class FullscreenActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;


        setContentView(com.oh5.baash.tinymonsters.R.layout.activity_fullscreen);
        set_parent_button();
        add_level_one_button();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        animate_arrow_and_star();

    }

    private void add_level_one_button(){
        final View simon_head = findViewById(com.oh5.baash.tinymonsters.R.id.kindyButton2);
        simon_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Level1.class));
            }
        });
    }


    protected void set_parent_button(){
        final FullscreenActivity app = this;
        final View parent_button = findViewById(com.oh5.baash.tinymonsters.R.id.parent_button);
        parent_button.setEnabled(true);
        parent_button.setVisibility(View.INVISIBLE);
        parent_button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(app)
                        .setTitle("Needs work?")
                        .setMessage("This application is in development at all times.\r\nI will be glad to field any suggestions you have.")
                        .setPositiveButton("Email Suggestions", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(Intent.ACTION_SEND);
                                i.setType("message/rfc822");
                                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"david.rawk@gmail.com"});
                                i.putExtra(Intent.EXTRA_SUBJECT, "Suggestions for Little Bits");
                                i.putExtra(Intent.EXTRA_TEXT, "I love the app, but it could be better if...\r\n");
                                try {
                                    startActivity(Intent.createChooser(i, "Send mail..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(app, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("All Good", null)
                        .setIcon(android.R.drawable.ic_dialog_email)
                        .show();
                return true;
            }
        });

    }


    protected void animate_arrow_and_star(){
        findViewById(R.id.yellow_arrow).setVisibility(View.INVISIBLE);
        findViewById(R.id.play_star).setVisibility(View.INVISIBLE);
        CountDownTimer count = new CountDownTimer( 750, 500) {
            public void onTick(long millisUntilFinished) {
                animate_view_up(R.id.yellow_arrow, 500);
            }
            public void onFinish() {
                animate_view_up(R.id.play_star, 250);
            }
        };
        count.start();
    }

    protected void animate_view_up(int view_id, int duration){
        final View view = findViewById(view_id);
        TranslateAnimation animation = new TranslateAnimation(0,0, view.getTop(), 0);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }

            @Override
            public void onAnimationEnd(Animation animation) {}
        });
        animation.setDuration(duration);
        view.startAnimation(animation);
    }




}
