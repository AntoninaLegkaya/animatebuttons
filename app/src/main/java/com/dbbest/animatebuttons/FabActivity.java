package com.dbbest.animatebuttons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FabActivity extends AppCompatActivity {

    int height;
    int width;
    ConstraintLayout constraintLayout;
    int duration = 200;
    Transition sharedElementEnterTransition;
    Transition.TransitionListener transitionListener;
    private ImageView closeButton;
    private FloatingActionButton editButton;
    private Animation show_edit;
    private Animation show_compass;
    private Animation show_myplaces;
    private Animation show_share;
    private Animation show_add;
    private Animation show_view;
    private Animation show_camera;
    private FrameLayout layoutFabs;
    private boolean isShowingMarker;


    @Override
    public void onBackPressed() {

        sharedElementEnterTransition.removeListener(transitionListener);
        setAnim(constraintLayout, false);
        setFab(closeButton, true);

    }

    public void morph() {
        isShowingMarker = !isShowingMarker;
        final int[] stateSet = {android.R.attr.state_checked * (isShowingMarker ? 1 : -1)};
        closeButton.setImageState(stateSet, true);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fabs);
        layoutFabs = findViewById(R.id.layout_fabs);

        constraintLayout = findViewById(R.id.bg);
        closeButton = findViewById(R.id.fab_close);
        final FloatingActionButton compassButton = findViewById(R.id.fab_compass);
        final FloatingActionButton myplacesButton = findViewById(R.id.fab_myplaces);
        final FloatingActionButton shareButton = findViewById(R.id.fab_share);
        final FloatingActionButton addButton = findViewById(R.id.fab_add);
        final FloatingActionButton viewButton = findViewById(R.id.fab_view);
        final FloatingActionButton cameraButton = findViewById(R.id.fab_camera);

        editButton = findViewById(R.id.fab_edit);

        show_edit = AnimationUtils.loadAnimation(getApplication(), R.anim.edit_show);
        final Animation hide_edit = AnimationUtils.loadAnimation(getApplication(), R.anim.edit_hide);
//

//
        show_compass = AnimationUtils.loadAnimation(getApplication(), R.anim.compass_show);
        final Animation hide_compass = AnimationUtils.loadAnimation(getApplication(), R.anim.compass_hide);
//
//
        show_myplaces = AnimationUtils.loadAnimation(getApplication(), R.anim.myplaces_show);
        final Animation hide_myplaces = AnimationUtils.loadAnimation(getApplication(), R.anim.myplaces_hide);
//
        show_share = AnimationUtils.loadAnimation(getApplication(), R.anim.share_show);
        final Animation hide_share = AnimationUtils.loadAnimation(getApplication(), R.anim.share_hide);
//
        show_add = AnimationUtils.loadAnimation(getApplication(), R.anim.add_show);
        final Animation hide_add = AnimationUtils.loadAnimation(getApplication(), R.anim.add_hide);
//
        show_view = AnimationUtils.loadAnimation(getApplication(), R.anim.view_show);
        final Animation hide_view = AnimationUtils.loadAnimation(getApplication(), R.anim.view_hide);
//
        show_camera = AnimationUtils.loadAnimation(getApplication(), R.anim.camera_show);
        final Animation hide_camera = AnimationUtils.loadAnimation(getApplication(), R.anim.camera_hide);

//        final Animation rotateAnim = AnimationUtils.loadAnimation(getApplication(), R.anim
//                .appear_fab_close);


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myplacesButton.startAnimation(hide_myplaces);
                myplacesButton.setClickable(false);
                sharedElementEnterTransition.removeListener(transitionListener);
                morph();
            }
        });


        getWindow().setEnterTransition(null);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        width = displayMetrics.widthPixels;


        sharedElementEnterTransition = getWindow().getSharedElementEnterTransition();


        transitionListener = new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                morph();
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                setAnim(constraintLayout, true);
                setFab(closeButton, false);
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }
        };

        sharedElementEnterTransition.addListener(transitionListener);


        FrameLayout.LayoutParams myplacesParams = (FrameLayout.LayoutParams) myplacesButton.getLayoutParams();
        myplacesParams.rightMargin += 0;
        myplacesParams.bottomMargin += (int) (myplacesButton.getHeight() * 2.5);
        myplacesButton.setLayoutParams(myplacesParams);
        myplacesButton.startAnimation(show_myplaces);
        myplacesButton.setClickable(true);

        show_myplaces.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cameraButton.startAnimation(show_camera);
                cameraButton.setClickable(true);
            }

        });

        hide_myplaces.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cameraButton.startAnimation(hide_camera);
                cameraButton.setClickable(false);
            }

        });

        show_camera.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shareButton.startAnimation(show_share);
                shareButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hide_camera.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shareButton.startAnimation(hide_share);
                shareButton.setClickable(false);
            }

        });

        show_share.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                addButton.startAnimation(show_add);
                addButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hide_share.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                addButton.startAnimation(hide_add);
                addButton.setClickable(false);
            }

        });

        show_add.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewButton.startAnimation(show_view);
                viewButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hide_add.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewButton.startAnimation(hide_view);
                viewButton.setClickable(false);
            }

        });


        show_view.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                compassButton.startAnimation(show_compass);
                compassButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        hide_view.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                compassButton.startAnimation(hide_compass);
                compassButton.setClickable(true);
            }

        });
        hide_compass.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                editButton.startAnimation(hide_edit);
                editButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        hide_edit.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                setAnim(constraintLayout, false);
                setFab(closeButton, true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAnim(final View myView, boolean isShow) {

// get the center for the clipping circle
        int cx = closeButton.getWidth() / 2;
        int cy = closeButton.getHeight() / 2;

// get the final radius for the clipping circle
        float finalRadius = (float) Math.hypot(width, height);

        int[] startingLocation = new int[2];
        closeButton.getLocationInWindow(startingLocation);

// create the animator for this view (the start radius is zero)
        Animator anim;
        if (isShow) {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (closeButton.getX() + cx), (int) (closeButton.getY() + cy), 0, finalRadius);
            // make the view visible and start the animation
            myView.setVisibility(View.VISIBLE);
        } else {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (closeButton.getX() + cx), (int) (closeButton.getY() + cy), finalRadius, 0);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    myView.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        anim.setDuration(duration);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setFab(final View myView, boolean isShow) {

// get the center for the clipping circle
        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

// get the initial radius for the clipping circle
        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim;
        if (isShow) {
// create the animation (the final radius is zero)
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, initialRadius);
// make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.VISIBLE);

                    finishAfterTransition();

                }
            });
            anim.setDuration(duration);
        } else {

            anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
// make the view invisible when the animation is done
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
//                    myView.setVisibility(View.INVISIBLE);
                    layoutFabs.setVisibility(View.VISIBLE);
                    if (editButton != null) {

                        editButton.startAnimation(show_edit);
                    }

                }
            });
        }
// start the animation
        anim.start();

    }


}