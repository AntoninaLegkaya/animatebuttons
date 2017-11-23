package com.dbbest.animatebuttons;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View
        .OnTouchListener {
    private static final String[] IMAGES_TAG = {"compass", "location", "share", "add", "presentation", "camera",
            "call", "main"};
    private final List<MovingObject> viewList = new ArrayList(7);
    Boolean touchFlag = false;
    boolean dropFlag = false;
    int eX, eY;
    LayoutParams imageParams;
    private ImageView fab;
    private boolean isShowingMarker;
    private CoordinatorLayout mainlayout;
    private View selected_item = null;
    private int windowwidth;
    private int windowheight;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainlayout = findViewById(R.id.layout_main);


        fab = findViewById(R.id.fab_main);
        ImageView callView = findViewById(R.id.fab_call);
        ImageView compassView = findViewById(R.id.fab_compass);
        ImageView addView = findViewById(R.id.fab_add);
        ImageView shareView = findViewById(R.id.fab_share);
        ImageView cameraView = findViewById(R.id.fab_camera);
        ImageView presentationView = findViewById(R.id.fab_view);
        ImageView locationView = findViewById(R.id.fab_myplaces);


        viewList.add(new MovingObject(0, 0, 0,
                0, cameraView));
        viewList.add(new MovingObject(0, 0, 0,
                0, callView));
        viewList.add(new MovingObject(0, 0, 0,
                0, shareView));
        viewList.add(new MovingObject(0, 0, 0,
                0, addView));
        viewList.add(new MovingObject(0, 0, 0,
                0, locationView));
        viewList.add(new MovingObject(0, 0, 0,
                0, presentationView));
        viewList.add(new MovingObject(0, 0, 0,
                0, compassView));

        callView.setVisibility(View.GONE);
        cameraView.setVisibility(View.GONE);
        shareView.setVisibility(View.GONE);
        addView.setVisibility(View.GONE);
        locationView.setVisibility(View.GONE);
        presentationView.setVisibility(View.GONE);
        compassView.setVisibility(View.GONE);


        fab.setBackground(getDrawable(R.drawable.fab_bg));

        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();

        fab.setOnTouchListener(this);
        fab.setOnClickListener(this);
//                new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                morph();
//                fab.setVisibility(View.GONE);
//            }
//        });

        synchronized (viewList) {
            mainlayout.setOnDragListener(new View.OnDragListener() {
                @Override
                public boolean onDrag(View v, DragEvent event) {
                    switch (event.getAction()) {
                        case DragEvent.ACTION_DRAG_STARTED:
                            int leftCord = (int) event.getX();
                            int topCord = (int) event.getY();


                            break;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            leftCord = (int) event.getX();
                            topCord = (int) event.getY();
                            if (leftCord > windowwidth) {
                                leftCord = windowwidth;
                            }
                            if (topCord > windowheight) {
                                topCord = windowheight;
                            }

                            Timber.i("Entered position x : %d, y : %d", leftCord, topCord);


                            for (MovingObject aViewList : viewList) {
                                aViewList.getView().setVisibility(View.INVISIBLE);


                                CoordinatorLayout.LayoutParams lp =
                                        new CoordinatorLayout.LayoutParams(
                                                new ViewGroup.MarginLayoutParams(
                                                        CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                                                        CoordinatorLayout.LayoutParams.WRAP_CONTENT));
                                lp.setMargins(leftCord, topCord, 0,
                                        0);

                                aViewList.setLeft(leftCord);
                                aViewList.setTop(topCord);
                                aViewList.getView().setLayoutParams(lp);

                            }


                            break;

                        case DragEvent.ACTION_DRAG_EXITED:
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION:
                            leftCord = (int) event.getX();
                            topCord = (int) event.getY();
//                            Timber.i("Location position x : %d, y : %d", leftCord, topCord);
                            putFabPosition(leftCord, topCord);


                            break;

                        case DragEvent.ACTION_DRAG_ENDED:
                            Timber.i("Action is DragEvent.ACTION_DRAG_ENDED");
                            selected_item.setLayoutParams(imageParams);
                            selected_item.setVisibility(View.VISIBLE);
                            for (MovingObject aViewList : viewList) {
                                View view = aViewList.getView();
                                view.setLayoutParams(imageParams);
                                view.setVisibility(View.VISIBLE);
                            }
                            break;

                        case DragEvent.ACTION_DROP:

                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

        }
    }

    private void putFabPosition(int x, int y) {

        int i = 7;

        for (MovingObject aViewList : viewList) {
            int cordX = aViewList.getLeft();
            int cordY = aViewList.getTop();
            int dx = x - cordX;
            int dy = y - cordY;

            if (dx != 0) {
                cordX = cordX + i * (dx / 8);
            }

            if (dy != 0) {
                cordY = cordY + i * (dy / 8);
            }

            CoordinatorLayout.LayoutParams lp =
                    new CoordinatorLayout.LayoutParams(
                            new ViewGroup.MarginLayoutParams(
                                    CoordinatorLayout.LayoutParams.WRAP_CONTENT,
                                    CoordinatorLayout.LayoutParams.WRAP_CONTENT));
            aViewList.setXY(cordX, cordY, 0, 0);
//            Timber.i("Move coordinates x_cord : %d, y_cord : %d", cordX, cordY);
            lp.setMargins(cordX - 75, cordY - 75, 0, 0);

            aViewList.getView().setLayoutParams(lp);
            aViewList.getView().setVisibility(View.VISIBLE);

            i--;
        }
    }


    public void morph() {

        final Intent intent = new Intent(MainActivity.this, FabActivity.class);
        isShowingMarker = !isShowingMarker;

        ActivityOptions options;
        options = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this,
                android.util.Pair.create((View) fab, "bg"));
        startActivity(intent, options.toBundle());

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:
                Timber.i("ACTION_DOWN");
                selected_item = v;
                imageParams = v.getLayoutParams();


                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
                v.startDrag(null, shadowBuilder, v, 0);
                v.setVisibility(View.INVISIBLE);
                break;

            case MotionEvent.ACTION_MOVE:
                Timber.i("ACTION_MOVE");
               break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        morph();
        fab.setVisibility(View.GONE);
    }
}
