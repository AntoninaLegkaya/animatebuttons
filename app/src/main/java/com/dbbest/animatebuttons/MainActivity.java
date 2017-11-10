package com.dbbest.animatebuttons;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView fab;
    private ImageView fabClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab_main);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Intent intent = new Intent(MainActivity.this, FabActivity.class);


                ActivityOptions options;
//                fab.setBackgroundResource(R.drawable.shape_create_edit_bg);
                options = ActivityOptions.makeSceneTransitionAnimation(
                        MainActivity.this,
                        android.util.Pair.create((View) fab, "bg"));
                startActivity(intent, options.toBundle());
//                fab.setVisibility(View.GONE);

//                final Animation animAppear = AnimationUtils.loadAnimation(MainActivity.this, R.anim.appear_fab_close);
//                final Animation animDisappear = AnimationUtils.loadAnimation(MainActivity.this, R.anim.edit_hide);
//                fabClose.setVisibility(View.VISIBLE);
//                fabClose.startAnimation(animAppear);
//
//
//                animAppear.setAnimationListener(new Animation.AnimationListener() {
//                    @Override
//                    public void onAnimationStart(Animation animation) {
//                        fab.setBackgroundResource(R.drawable.shape_create_edit_bg);
////                        fab.startAnimation(animDisappear);
//                    }
//
//                    @Override
//                    public void onAnimationEnd(Animation animation) {
//
//                        ActivityOptions options;
//                        options = ActivityOptions.makeSceneTransitionAnimation(
//                                MainActivity.this,
//                                android.util.Pair.create((View) fab, "bg"));
//                        fab.setVisibility(View.GONE);
//                        fabClose.setVisibility(View.GONE);
//
//                    }
//
//
//                    @Override
//                    public void onAnimationRepeat(Animation animation) {
//
//                    }
//                });

            }
        });
    }


}
