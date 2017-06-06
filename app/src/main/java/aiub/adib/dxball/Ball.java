package aiub.adib.dxball;

import android.graphics.Canvas;


public class Ball {
    private float gameOver=0;
    private float x;
    private float y;
    private float r;
    private float dx;
    private float dy;

    public  Ball(float x,float y,float r){
        this.x=x;
        this.y=y;
        this.r=r;
        dx=0;
        dy=0;
    }

    public float getX(){
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getR() {
        return r;
    }


    public float getDX() {
        return dx;
    }

    public float getDY() {
        return dy;
    }


    public void setDX(float dx) {
        this.dx = dx;
    }

    public void setDY(float dy) {
        this.dy = dy;
    }

    public float getGameOver(){
        return gameOver;
    }

    public void move(){
        x=x+dx;
        y=y+dy;
    }

    public void ballBoundaryCheck(Canvas canvas) {

        if((this.y-this.r)>=canvas.getHeight()){
            GameCanvas.LIFE -= 1;
            GameCanvas.newLife = true;
        }

        if((this.x+this.r)>=canvas.getWidth() || (this.x-this.r)<=0){
            this.dx = -this.dx;
        }
        if( (this.y-this.r)<=0){
            this.dy = -this.dy;
        }

        if(GameCanvas.LIFE==0) {
            GameCanvas.GAMEOVER = true;
        }




    }

}
