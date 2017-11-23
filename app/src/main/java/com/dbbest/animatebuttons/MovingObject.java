package com.dbbest.animatebuttons;

import android.widget.ImageView;

public class MovingObject {
    private int left;
    private int top;
    private int right;
    private int buttom;
    private ImageView view;

    public MovingObject(int left, int top) {

        this.left = left;
        this.top = top;
    }

    public MovingObject(int left, int top, int right, int buttom, ImageView view) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.buttom = buttom;
        this.view = view;
    }

    public ImageView getView() {
        return view;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return left;
    }

    public int getTop() {
        return top;
    }

    public void setXY(int left, int top, int right, int buttom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.buttom = buttom;
    }
}
