package com.dbbest.animatebuttons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private static final int SWIPE_MIN_DISTANCE = 10;
    private static final int SWIPE_MAX_OFF_PATH = 50;
    private static final int SWIPE_THRESHOLD_VELOCITY = 10;
    private static final String[] IMAGES_TAG = {"compass", "location", "share", "add", "presentation", "camera",
            "call", "main"};
    final Handler handler = new Handler();
    ;
    Boolean touchFlag = false;
    boolean dropFlag = false;
    int eX, eY;
    LayoutParams imageParams;
    private List<MovingObject> viewList = new ArrayList(7);
    private ImageView fab;
    private boolean isShowingMarker;
    private CoordinatorLayout mainlayout;
    private View selected_item = null;
    private ImageView callView;
    private ImageView compassView;
    private ImageView addView;
    private ImageView shareView;
    private ImageView cameraView;
    private ImageView presentationView;
    private ImageView locationView;
    private int windowwidth;
    private int windowheight;
    private GestureDetector gestureDetector;
    private VelocityTracker velocityTracker;
    private FlingAnimation flingXAnimation;
    private FlingAnimation flingYAnimation;
    private Rect circleHitRect = new Rect();
    private boolean isDragging = false;
    private boolean isAnimationCompleted = false;
    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;

    private float translationX;
    private float translationY;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainlayout = findViewById(R.id.layout_main);


        fab = findViewById(R.id.fab_main);
        final ImageView callView = findViewById(R.id.fab_call);
        final ImageView compassView = findViewById(R.id.fab_compass);
        final ImageView addView = findViewById(R.id.fab_add);
        final ImageView shareView = findViewById(R.id.fab_share);
        final ImageView cameraView = findViewById(R.id.fab_camera);
        final ImageView presentationView = findViewById(R.id.fab_view);
        final ImageView locationView = findViewById(R.id.fab_myplaces);

        viewList.add(new MovingObject(0, 0, 0,
                0, fab, "fab", 0));
        viewList.add(new MovingObject(0, 0, 0,
                0, cameraView, "camera", 1));
        viewList.add(new MovingObject(0, 0, 0,
                0, callView, "call", 2));
        viewList.add(new MovingObject(0, 0, 0,
                0, shareView, "share", 3));
        viewList.add(new MovingObject(0, 0, 0,
                0, addView, "add", 4));
        viewList.add(new MovingObject(0, 0, 0,
                0, locationView, "location", 5));
        viewList.add(new MovingObject(0, 0, 0,
                0, presentationView, "presentation", 6));
        viewList.add(new MovingObject(0, 0, 0,
                0, compassView, "compass", 7));
        composeAnimation(0, 0);

        fab.setBackground(getDrawable(R.drawable.fab_bg));
        gestureDetector = new GestureDetector(this, new MyGestureDetector());

        velocityTracker = VelocityTracker.obtain();


        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();


        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });


        findViewById(R.id.layout_main).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Timber.i("ACTION_DOWN");

                        downX = event.getX();
                        downY = event.getY();

                        fab.getHitRect(circleHitRect);
                        if (circleHitRect.contains((int) downX, (int) downY)) {
                            isDragging = true;

                            offsetX = fab.getTranslationX();
                            offsetY = fab.getTranslationY();
                            velocityTracker.addMovement(event);
                            initCordListView();

                        }
                        return true;
                    }

                    case MotionEvent.ACTION_MOVE: {

                        if (!isDragging) {
                            break;
                        } else {
                            Timber.i("ACTION_MOVE ");
                            translationX = event.getX() - downX + offsetX;
                            translationY = event.getY() - downY + offsetY;
                            velocityTracker.addMovement(event);
                            composeAnimation(translationX, translationY);

                        }
                        return true;
                    }

                    case MotionEvent.ACTION_UP: {
                        Timber.i("ACTION_UP");
                        composeAnimation(0, 0);
                        cancelFlingAnimation();
                        isDragging = false;

                        return true;
                    }

                }
                return false;
            }
        });
    }


    private void initCordListView() {
        for (MovingObject aViewList : viewList) {
            aViewList.setLeft(0);
            aViewList.setTop(0);
        }
    }

    private void composeAnimation(final float x, final float y) {
        AnimatorSet mainSet = new AnimatorSet();
        AnimatorSet fabSet = transitionXY(viewList.get(0), x, y);
        AnimatorSet cameraSet = transitionXY(viewList.get(1), x, y);
        AnimatorSet callSet = transitionXY(viewList.get(2), x, y);
        AnimatorSet shareSet = transitionXY(viewList.get(3), x, y);
        AnimatorSet addSet = transitionXY(viewList.get(4), x, y);
        AnimatorSet locationSet = transitionXY(viewList.get(5), x, y);
        AnimatorSet presentationSet = transitionXY(viewList.get(6), x, y);
        AnimatorSet compassSet = transitionXY(viewList.get(7), x, y);
        mainSet.playSequentially(fabSet, cameraSet, callSet, shareSet, addSet, locationSet,
                presentationSet, compassSet);
        mainSet.start();

        mainSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                Timber.i("Animation completed");
                isAnimationCompleted = true;
            }

            @Override
            public void onAnimationStart(Animator animation) {
                Timber.i("Animation started");
                isAnimationCompleted = false;
            }
        });


    }

    @SuppressLint("ObjectAnimatorBinding")
    private AnimatorSet transitionXY(MovingObject fabView, float x, float y) {

        float cordX;
        float cordY;


        if (fabView.getName().equals("fab")) {
            cordX = x;
            cordY = y;
        } else {

            cordX = fabView.getLeft();
            cordY = fabView.getTop();
            if (cordX == 0 && cordY == 0) {
                float dx = x / 10;
                float dy = y / 10;
                if (dx != 0) {
                    cordX = cordX + fabView.getPossition() * dx;
                }

                if (dy != 0) {
                    cordY = cordY + fabView.getPossition() * dy;
                }

            } else {


                float dx = x - cordX;
                float dy = y - cordY;

                if (dx != 0) {
                    cordX = cordX + dx;
                }

                if (dy != 0) {
                    cordY = cordY + dy;

                }

            }
        }

//        Timber.i("Name: %s fromX:%f, fromY:%f toX:%f, toY:%f *****************************"
//                , fabView.getName(), fabView.getLeft(), fabView.getTop(), cordX, cordY);
        ObjectAnimator xAnimator = ObjectAnimator.ofFloat(fabView.getView(), "translationX", fabView
                .getLeft(), cordX);
        ObjectAnimator yAnimator = ObjectAnimator.ofFloat(fabView.getView(), "translationY", fabView
                .getTop(), cordY);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(xAnimator, yAnimator);
        set.setDuration(150);
        fabView.setLeft(cordX);
        fabView.setTop(cordY);
        return set;

    }

    private void cancelFling(ImageView view) {
        velocityTracker.computeCurrentVelocity(0);
        if (view.getTranslationX() != 0) {
            flingXAnimation = new FlingAnimation(view,
                    DynamicAnimation.TRANSLATION_X)
                    .setFriction(0.1f)
                    .setStartVelocity(velocityTracker.getXVelocity());
            flingXAnimation.start();
        }
        if (view.getTranslationY() != 0) {
            flingYAnimation = new FlingAnimation(view,
                    DynamicAnimation.TRANSLATION_Y)
                    .setFriction(0.1f)
                    .setStartVelocity(velocityTracker.getYVelocity());
            flingYAnimation.start();
        }
        velocityTracker.clear();
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


    private void callOnClick() {
        morph();
        fab.setVisibility(View.GONE);
    }

    private void cancelFlingAnimation() {
        if (flingXAnimation != null) {
            flingXAnimation.cancel();
        }
        if (flingYAnimation != null) {
            flingYAnimation.cancel();
        }
    }

    @Override
    protected void onPause() {
        initCordListView();
        composeAnimation(0, 0);
        super.onPause();
    }

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapUp(MotionEvent e) {
            Timber.i("SINGLE_TAP");
            callOnClick();
            return true;
        }

        public void onLongPress(MotionEvent e) {
            Timber.i("LONG_TAP");
            if (!isDragging && isAnimationCompleted) {
                callOnClick();
            }
        }


    }
}
