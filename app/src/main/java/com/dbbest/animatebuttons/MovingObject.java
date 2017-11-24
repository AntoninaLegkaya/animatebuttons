package com.dbbest.animatebuttons;

import android.widget.ImageView;

public class MovingObject {
    private float left;
    private float top;
    private float right;
    private float buttom;
    private ImageView view;

    public MovingObject(int left, int top) {

        this.left = left;
        this.top = top;
    }

    public MovingObject(float left, float top, float right, float buttom, ImageView view) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.buttom = buttom;
        this.view = view;
    }

    public ImageView getView() {
        return view;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public void setXY(int left, int top, int right, int buttom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.buttom = buttom;
    }
}
