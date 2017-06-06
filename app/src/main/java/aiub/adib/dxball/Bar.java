package aiub.adib.dxball;

import android.graphics.Canvas;


public class Bar {
    float barTop,barLeft,barWidth,barHeight,barRight,barBottom;

    Bar(float left, float top, float width, float height){
        barLeft = left;
        barTop = top;
        barWidth = width;
        barHeight = height;
        barRight = barLeft+barWidth;
        barBottom = barTop+barHeight;
    }

    public void setLeft (float left)
    {
        this.barLeft = left;
    }

    public void setTop (float top)
    {
        this.barTop = top;
    }

    public void setRight (float right)
    {
        this.barRight = right;
    }

    public void setBottom (float bottom)
    {
        this.barBottom = bottom;
    }

    public float getLeft() {
        return barLeft;
    }


    public float getTop() {
        return barTop;
    }

    public float getRight() {
        return barRight;
    }

    public float getBottom() {
        return barBottom;
    }

    public float getBarWidth() {
        return barWidth;
    }

    public float getBarHeight() {
        return barHeight;
    }



}