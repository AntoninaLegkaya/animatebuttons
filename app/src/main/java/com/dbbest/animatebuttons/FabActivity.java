package com.dbbest.animatebuttons;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings("SIC_INNER_SHOULD_BE_STATIC_ANON")
@SuppressWarnings({"PMD.GodClass", "PMD.AccessorMethodGeneration"})
public class FabActivity extends AppCompatActivity {

    private static final int DURATION = 200;
    private Transition sharedElementEnterTransition;
    private  Transition.TransitionListener transitionListener;
    private ImageView closeButton;
    private  FrameLayout bgLayout;
    private  ImageView callButton;
    private Animation showEdit;
    private  Animation showCompass;
    private Animation showShare;
    private  Animation showAdd;
    private  Animation showView;
    private  Animation showCamera;
    private int height;
    private int width;
    private boolean isShowingMarker;


    @Override
    public void onBackPressed() {

        sharedElementEnterTransition.removeListener(transitionListener);
        setAnim(bgLayout, false);
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

        bgLayout = findViewById(R.id.bg);
        closeButton = findViewById(R.id.fab_close);
        final ImageView compassButton = findViewById(R.id.fab_compass);
        final ImageView myPlacesButton = findViewById(R.id.fab_myplaces);
        final ImageView shareButton = findViewById(R.id.fab_share);
        final ImageView addButton = findViewById(R.id.fab_add);
        final ImageView viewButton = findViewById(R.id.fab_view);
        final ImageView cameraButton = findViewById(R.id.fab_camera);

        callButton = findViewById(R.id.fab_call);

        showEdit = AnimationUtils.loadAnimation(getApplication(), R.anim.edit_show);
        final Animation hideEdit = AnimationUtils.loadAnimation(getApplication(), R.anim.edit_hide);

        showCompass = AnimationUtils.loadAnimation(getApplication(), R.anim.compass_show);
        final Animation hideCompass = AnimationUtils.loadAnimation(getApplication(),
                R.anim.compass_hide);
     Animation showMyPlaces = AnimationUtils.loadAnimation(getApplication(), R.anim.myplaces_show);
        final Animation hideMyPlaces = AnimationUtils.loadAnimation(getApplication(),
                R.anim.myplaces_hide);
        showShare = AnimationUtils.loadAnimation(getApplication(), R.anim.share_show);
        final Animation hideShare = AnimationUtils.loadAnimation(getApplication(),
                R.anim.share_hide);
        showAdd = AnimationUtils.loadAnimation(getApplication(), R.anim.add_show);
        final Animation hideAdd = AnimationUtils.loadAnimation(getApplication(), R.anim.add_hide);

        showView = AnimationUtils.loadAnimation(getApplication(), R.anim.view_show);
        final Animation hideView = AnimationUtils.loadAnimation(getApplication(), R.anim.view_hide);

        showCamera = AnimationUtils.loadAnimation(getApplication(), R.anim.camera_show);
        final Animation hideCamera = AnimationUtils.loadAnimation(getApplication(),
                R.anim.camera_hide);

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myPlacesButton.startAnimation(hideMyPlaces);
                myPlacesButton.setClickable(false);
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
                setAnim(bgLayout, true);
                setFab(closeButton, false);
            }

            @Override
            public void onTransitionCancel(Transition transition) {
                // Nothing here
            }

            @Override
            public void onTransitionPause(Transition transition) {
                // Nothing here
            }

            @Override
            public void onTransitionResume(Transition transition) {
                // Nothing here
            }
        };

        sharedElementEnterTransition.addListener(transitionListener);

        callButton.setVisibility(View.VISIBLE);

        FrameLayout.LayoutParams myPlacesParams = (FrameLayout.LayoutParams)
                myPlacesButton.getLayoutParams();
        myPlacesParams.rightMargin += 0;
        myPlacesParams.bottomMargin += (int) (myPlacesButton.getHeight() * 2.5);
        myPlacesButton.setLayoutParams(myPlacesParams);
        myPlacesButton.startAnimation(showMyPlaces);
        myPlacesButton.setClickable(true);

        showMyPlaces.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cameraButton.startAnimation(showCamera);
                cameraButton.setClickable(true);
            }

        });

        hideMyPlaces.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cameraButton.startAnimation(hideCamera);
                cameraButton.setClickable(false);
            }

        });

        showCamera.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shareButton.startAnimation(showShare);
                shareButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }
        });

        hideCamera.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                shareButton.startAnimation(hideShare);
                shareButton.setClickable(false);
            }

        });

        showShare.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                addButton.startAnimation(showAdd);
                addButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }
        });

        hideShare.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                addButton.startAnimation(hideAdd);
                addButton.setClickable(false);
            }

        });

        showAdd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewButton.startAnimation(showView);
                viewButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }
        });
        hideAdd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                viewButton.startAnimation(hideView);
                viewButton.setClickable(false);
            }

        });


        showView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                compassButton.startAnimation(showCompass);
                compassButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }
        });

        hideView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                compassButton.startAnimation(hideCompass);
                compassButton.setClickable(true);
            }

        });
        hideCompass.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                callButton.startAnimation(hideEdit);
                callButton.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }
        });
        hideEdit.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Nothing here
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                setAnim(bgLayout, false);
                setFab(closeButton, true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Nothing here
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setAnim(final View myView, boolean isShow) {

        int cx = closeButton.getWidth() / 2;
        int cy = closeButton.getHeight() / 2;

        float finalRadius = (float) Math.hypot(width, height);

        int[] startingLocation = new int[2];
        closeButton.getLocationInWindow(startingLocation);

        Animator anim;
        if (isShow) {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (closeButton.getX() + cx),
                            (int) (closeButton.getY() + cy), 0, finalRadius);
            myView.setVisibility(View.VISIBLE);
        } else {
            anim =
                    ViewAnimationUtils.createCircularReveal(myView, (int) (closeButton.getX() + cx),
                            (int) (closeButton.getY() + cy), finalRadius, 0);
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                    // Nothing here
                }

                @Override
                public void onAnimationEnd(Animator animator) {

                    myView.setVisibility(View.INVISIBLE);

                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    // Nothing here
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                    // Nothing here
                }
            });
        }

        anim.setDuration(DURATION);
        anim.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void setFab(final View myView, boolean isShow) {

        int cx = myView.getWidth() / 2;
        int cy = myView.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);
        Animator anim;
        if (isShow) {
            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, initialRadius);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.VISIBLE);
                    finishAfterTransition();
                }
            });
            anim.setDuration(DURATION);
        } else {

            anim = ViewAnimationUtils.createCircularReveal(myView, cx, cy, initialRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    if (callButton != null) {
                        callButton.startAnimation(showEdit);
                    }

                }
            });
        }
        anim.start();

    }

}