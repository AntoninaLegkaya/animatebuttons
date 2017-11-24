package com.dbbest.animatebuttons;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.FloatPropertyCompat;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
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
    private float downX;
    private float downY;
    private float offsetX;
    private float offsetY;
    private boolean isTerminationConditionMet = false;

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

//        callView.setVisibility(View.GONE);
//        cameraView.setVisibility(View.GONE);
//        shareView.setVisibility(View.GONE);
//        addView.setVisibility(View.GONE);
//        locationView.setVisibility(View.GONE);
//        presentationView.setVisibility(View.GONE);
//        compassView.setVisibility(View.GONE);


        fab.setBackground(getDrawable(R.drawable.fab_bg));
        gestureDetector = new GestureDetector(this, new MyGestureDetector());

        velocityTracker = VelocityTracker.obtain();


        windowwidth = getWindowManager().getDefaultDisplay().getWidth();
        windowheight = getWindowManager().getDefaultDisplay().getHeight();


//        fab.setOnTouchListener(this);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                callOnClick();
//            }
//        });
//        fab.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//
//                SpringAnimation animX = new SpringAnimation(fab,
//                        new FloatPropertyCompat<View>("translationX") {
//                            @Override
//                            public float getValue(View view) {
//                                return view.getTranslationX();
//                            }
//
//                            @Override
//                            public void setValue(View view, float value) {
//                                view.setTranslationX(value);
//                            }
//                        }, 0);
//                animX.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//                animX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//                animX.setStartVelocity(0);
//                animX.start();
//
//                SpringAnimation animY = new SpringAnimation(fab,
//                        new FloatPropertyCompat<View>("translationY") {
//                            @Override
//                            public float getValue(View view) {
//                                return view.getTranslationY();
//                            }
//
//                            @Override
//                            public void setValue(View view, float value) {
//                                view.setTranslationY(value);
//                            }
//                        }, 0);
//                animY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
//                animY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
//                animY.setStartVelocity(0);
//                animY.start();
//                return true;
//            }
//        });

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
                            cancelFlingAnimation();
                            isDragging = true;
                            offsetX = fab.getTranslationX();
                            offsetY = fab.getTranslationY();
                            velocityTracker.addMovement(event);
                        }
                        return true;
                    }

                    case MotionEvent.ACTION_MOVE: {
                        Timber.i("ACTION_MOVE");
                        if (!isDragging) {
                            break;
                        }

                        final float translationX = event.getX() - downX + offsetX;
                        float translationY = event.getY() - downY + offsetY;
                        fab.setTranslationX(translationX);
                        fab.setTranslationY(translationY);
                        velocityTracker.addMovement(event);
                        transitionXY(translationX, translationY, event);


                        return true;
                    }

                    case MotionEvent.ACTION_UP: {
                        Timber.i("ACTION_UP");
                        isDragging = false;

                        cancelFlingAnimation();
                        looseFab(fab);
                        looseFab(callView);
                        looseFab(compassView);
                        looseFab(cameraView);
                        looseFab(addView);
                        looseFab(locationView);
                        looseFab(presentationView);
                        looseFab(shareView);

                        initCordListView();

                        return true;
                    }


                    case MotionEvent.ACTION_CANCEL: {
                        Timber.i("ACTION_CANCEL");
                        if (!isDragging) {
                            break;
                        }
                        isDragging = false;

                        cancelFling(fab);
                        cancelFling(callView);
                        cancelFling(compassView);
                        cancelFling(cameraView);
                        cancelFling(addView);
                        cancelFling(locationView);
                        cancelFling(presentationView);
                        cancelFling(shareView);

                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void initCordListView() {
        for (MovingObject aViewList : viewList) {
            aViewList.setLeft(offsetX);
            aViewList.setTop(offsetY);
        }
    }


    private void transitionXY(float x, float y, MotionEvent event) {

        float i = 1.2f;
        float xMoveCord = x;
        float yMoveCord = y;

        for (MovingObject aViewList : viewList) {
            float cordX = aViewList.getLeft();
            float cordY = aViewList.getTop();

            float dx = x - cordX;
            float dy = y - cordY;

            if (dx != 0) {
                cordX = cordX + i * (dx / 6);
            }

            if (dy != 0) {
                cordY = cordY + i * (dy / 6);
            }
            aViewList.setLeft(cordX);
            aViewList.setTop(cordY);

            aViewList.getView().setTranslationX(cordX);
            aViewList.getView().setTranslationY(cordY);


            i = i + 1f;

            followToHeardFab(aViewList.getView(), xMoveCord, yMoveCord);
            xMoveCord = cordX;
            yMoveCord = cordY;
        }
    }

    private void cancelFling(ImageView view) {
        velocityTracker.computeCurrentVelocity(1000);
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

    private void looseFab(ImageView view) {
        SpringAnimation animX = new SpringAnimation(view,
                new FloatPropertyCompat<View>("translationX") {
                    @Override
                    public float getValue(View view) {
                        return view.getTranslationX();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationX(value);
                    }
                }, 0);
        animX.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        animX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        animX.setStartVelocity(0);
        animX.start();

        SpringAnimation animY = new SpringAnimation(view,
                new FloatPropertyCompat<View>("translationY") {
                    @Override
                    public float getValue(View view) {
                        return view.getTranslationY();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationY(value);
                    }
                }, 0);
        animY.getSpring().setStiffness(SpringForce.STIFFNESS_MEDIUM);
        animY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        animY.setStartVelocity(0);
        animY.start();
    }

    private void followToHeardFab(ImageView view, final float x, final float y) {
        view.setTranslationX(x);
        view.setTranslationY(y);
        SpringAnimation animX = new SpringAnimation(view,
                new FloatPropertyCompat<View>("translationX") {
                    @Override
                    public float getValue(View view) {
                        return view.getTranslationX();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationX(value);
                    }
                }, x);
        animX.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        animX.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        animX.setStartVelocity(0);
        animX.start();

        SpringAnimation animY = new SpringAnimation(view,
                new FloatPropertyCompat<View>("translationY") {
                    @Override
                    public float getValue(View view) {
                        return view.getTranslationY();
                    }

                    @Override
                    public void setValue(View view, float value) {
                        view.setTranslationY(value);
                    }
                }, y);
        animY.getSpring().setStiffness(SpringForce.STIFFNESS_VERY_LOW);
        animY.getSpring().setDampingRatio(SpringForce.DAMPING_RATIO_LOW_BOUNCY);
        animY.setStartVelocity(0);
        animY.start();
    }


//        mainlayout.setOnDragListener(new View.OnDragListener() {
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                switch (event.getAction()) {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        int leftCord = (int) event.getX();
//                        int topCord = (int) event.getY();
//
//
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        leftCord = (int) event.getX();
//                        topCord = (int) event.getY();
//                        if (leftCord > windowwidth) {
//                            leftCord = windowwidth;
//                        }
//                        if (topCord > windowheight) {
//                            topCord = windowheight;
//                        }
//
//                        Timber.i("Entered position x : %d, y : %d", leftCord, topCord);
//
//
//                        for (MovingObject aViewList : viewList) {
//                            aViewList.getView().setVisibility(View.INVISIBLE);
//
//
//                            CoordinatorLayout.LayoutParams lp =
//                                    new CoordinatorLayout.LayoutParams(
//                                            new ViewGroup.MarginLayoutParams(
//                                                    CoordinatorLayout.LayoutParams.WRAP_CONTENT,
//                                                    CoordinatorLayout.LayoutParams.WRAP_CONTENT));
//                            lp.setMargins(leftCord, topCord, 0,
//                                    0);
//
//                            aViewList.setLeft(leftCord);
//                            aViewList.setTop(topCord);
//                            aViewList.getView().setLayoutParams(lp);
//
//                        }
//
//
//                        break;
//
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        break;
//
//                    case DragEvent.ACTION_DRAG_LOCATION:
//                        leftCord = (int) event.getX();
//                        topCord = (int) event.getY();
////                            Timber.i("Location position x : %d, y : %d", leftCord, topCord);
//                        putFabPosition(leftCord, topCord);
//
//
//
//
//
//                        break;
//
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        Timber.i("Action is DragEvent.ACTION_DRAG_ENDED");
//
//
//                        selected_item.setLayoutParams(imageParams);
//                        selected_item.setVisibility(View.VISIBLE);
//                        for (MovingObject aViewList : viewList) {
//                            View view = aViewList.getView();
//                            view.setLayoutParams(imageParams);
//                            view.setVisibility(View.VISIBLE);
//                        }
//                        break;
//
//                    case DragEvent.ACTION_DROP:
//
//                        break;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        });
//
//    }

//    private void putFabPosition(int x, int y) {
//
//        int i = 7;
//
//        for (MovingObject aViewList : viewList) {
//            int cordX = aViewList.getLeft();
//            int cordY = aViewList.getTop();
//            int dx = x - cordX;
//            int dy = y - cordY;
//
//            if (dx != 0) {
//                cordX = cordX + i * (dx / 8);
//            }
//
//            if (dy != 0) {
//                cordY = cordY + i * (dy / 8);
//            }
//
//            CoordinatorLayout.LayoutParams lp =
//                    new CoordinatorLayout.LayoutParams(
//                            new ViewGroup.MarginLayoutParams(
//                                    CoordinatorLayout.LayoutParams.WRAP_CONTENT,
//                                    CoordinatorLayout.LayoutParams.WRAP_CONTENT));
//            aViewList.setXY(cordX, cordY, 0, 0);
////            Timber.i("Move coordinates x_cord : %d, y_cord : %d", cordX, cordY);
//            lp.setMargins(cordX - 75, cordY - 75, 0, 0);
//
//            aViewList.getView().setLayoutParams(lp);
//            aViewList.getView().setVisibility(View.VISIBLE);
//
//            i--;
//        }
//    }


    public void morph() {

        final Intent intent = new Intent(MainActivity.this, FabActivity.class);
        isShowingMarker = !isShowingMarker;

        ActivityOptions options;
        options = ActivityOptions.makeSceneTransitionAnimation(
                MainActivity.this,
                android.util.Pair.create((View) fab, "bg"));
        startActivity(intent, options.toBundle());

    }


//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//
//
//        return gestureDetector.onTouchEvent(event);
//    }

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

    private class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {

        public boolean onSingleTapUp(MotionEvent e) {
            callOnClick();
            return false;
        }

        public void onLongPress(MotionEvent e) {
            Timber.i("ACTION_LONG_PRESS");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(fab);
            fab.startDrag(null, shadowBuilder, fab, 0);
            fab.setVisibility(View.INVISIBLE);
        }

        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }

        public boolean onSingleTapConfirmed(MotionEvent e) {
            return false;

        }

        public void onShowPress(MotionEvent e) {
        }

        public boolean onDown(MotionEvent e) {
            Timber.i("ACTION_DOWN");
            selected_item = fab;
            imageParams = fab.getLayoutParams();

            return true;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, final float distanceX, float distanceY) {
            return super.onScroll(e1, e2, distanceX, distanceY);
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return super.onFling(e1, e2, velocityX, velocityY);

        }


    }
}
