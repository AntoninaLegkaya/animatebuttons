package com.dbbest.animatebuttons;

import android.widget.ImageView;

public class MovingObject {
    private float left;
    private float top;
    private float right;
    private float buttom;
    private int possition;
    private String name;
    private ImageView view;

    public MovingObject(int left, int top) {

        this.left = left;
        this.top = top;
    }

    public MovingObject(float left, float top, float right, float buttom, ImageView view, String
            name, int
                                possition) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.buttom = buttom;
        this.view = view;
        this.name = name;
        this.possition = possition;

    }

    public int getPossition() {
        return possition;
    }

    public String getName() {
        return name;
    }

    public ImageView getView() {
        return view;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setXY(int left, int top, int right, int buttom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.buttom = buttom;
    }
}
