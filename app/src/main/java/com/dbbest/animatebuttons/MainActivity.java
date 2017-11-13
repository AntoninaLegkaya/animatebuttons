package com.dbbest.animatebuttons;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView fab;
    private boolean isShowingMarker = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = findViewById(R.id.fab_main);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                morph();
            }
        });
    }

    public void morph() {
        isShowingMarker = !isShowingMarker;
        final Intent intent = new Intent(MainActivity.this, FabActivity.class);


        ActivityOptions options;
        options = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this,
                android.util.Pair.create((View) fab, "bg"));
        final int[] stateSet = {android.R.attr.state_checked * (isShowingMarker ? 1 : -1)};
//        if (isShowingMarker == false) {
//
//            fab.setBackground(getDrawable(R.drawable.fab_bg));
//        } else {
//            fab.setBackground(getDrawable(R.drawable.fab_bg_revers));
//        }
//        fab.setImageState(stateSet, true);
        startActivity(intent, options.toBundle());

    }

}
