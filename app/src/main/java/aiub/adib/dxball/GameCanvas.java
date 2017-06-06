package aiub.adib.dxball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


public class GameCanvas extends View {
    int gameFinished=0;
    int score=0;
    public static int LIFE,checkWidth=0;
    public static boolean newLife;
    public static boolean GAMEOVER;
    Paint paint;
    float brickX=0, brickY=0;
    Canvas canvas;
    Ball ball;
    Bar bar;
    float barWidth,ballSpeed;
    boolean barMoveLeft,first;

    ArrayList<Bricks> bricks=new ArrayList<Bricks>();


    public GameCanvas(Context context) {
        super(context);
        paint=new Paint();
        LIFE = 3;
        first = true;

        GAMEOVER = false;

        newLife = true;
        barMoveLeft = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        this.canvas = canvas;
        int col;
        brickX = 0;
        brickY = (canvas.getHeight()/25)*2;
        if(first) {
            first=false;
            for(int i=0; i<20; i++){
                if(brickX>=canvas.getWidth()) {
                    brickX = 0;
                    brickY += canvas.getHeight()/15;
                }
                if(i%2==0){
                    col = Color.rgb(0, 150, 0);
                    bricks.add(new Bricks(brickX,brickY,brickX+canvas.getWidth()/5,brickY+canvas.getHeight()/15,col));
                }else{
                    col = Color.rgb(255, 0, 7);
                    bricks.add(new Bricks(brickX,brickY,brickX+canvas.getWidth()/5,brickY+canvas.getHeight()/15,col));
                }
                brickX+=canvas.getWidth()/5;
            }
        }

        if(newLife){
            newLife = false;
            //new ball
            ballSpeed = 5;
            ball=new Ball(canvas.getWidth()/2,canvas.getHeight()-60,20);
            ball.setDX(ballSpeed);
            ball.setDY(-ballSpeed);
            //new bar
            barWidth = canvas.getWidth()/4;
            bar = new Bar(canvas.getWidth()/2-(barWidth/2), canvas.getHeight()-35, barWidth, 30);
            checkWidth = canvas.getWidth();
        }

        if((bricks.size()==0)){
            first = true;
            newLife = true;
        }

        //background
        paint.setColor(Color.WHITE);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        //Ball
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(ball.getX(), ball.getY(), ball.getR(), paint);

        //text
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        //paint.setFakeBoldText(true);
        canvas.drawText("SCORE: "+score,10,40,paint);

        paint.setTextSize(40);
        //paint.setFakeBoldText(true);
        canvas.drawText("LIFE: "+LIFE,canvas.getWidth()-150,40,paint);

        //Bar
        paint.setColor(Color.BLACK);
        canvas.drawRect(bar.getLeft(), bar.getTop(), bar.getRight(), bar.getBottom(), paint);

        //bricks
        for(int i=0;i<bricks.size();i++){
            paint.setColor(Color.TRANSPARENT);
            canvas.drawRect(bricks.get(i).getLeft(),bricks.get(i).getTop(),bricks.get(i).getRight(),bricks.get(i).getBottom(),bricks.get(i).getPaint());
        }

        //gameover
        if(LIFE == 0 ){//game over
            if(gameFinished==0){//game finished life over
                paint.setColor(Color.WHITE);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

                paint.setTextSize(70);
                paint.setFakeBoldText(true);
                paint.setColor(Color.RED);
                canvas.drawText("GAME OVER",canvas.getWidth()/2-200,canvas.getHeight()/2,paint);
                paint.setColor(Color.BLUE);
                canvas.drawText("SCORE: "+score,canvas.getWidth()/2-160,canvas.getHeight()/2+80,paint);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((MainActivity)getContext()).finish();
            }
        }

        if(LIFE > 0){ //game over
            if(gameFinished == 1){
                paint.setColor(Color.WHITE);
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

                paint.setTextSize(70);
                paint.setFakeBoldText(true);
                paint.setColor(Color.GREEN);
                canvas.drawText("Congratulations !",canvas.getWidth()/2-280,canvas.getHeight()/2,paint);
                paint.setColor(Color.LTGRAY);
                canvas.drawText("SCORE: "+score,canvas.getWidth()/2-160,canvas.getHeight()/2+80,paint);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ((MainActivity)getContext()).finish();
            }
        }

        //collision
        this.BrickCollision(bricks,ball,canvas);
        this.BarCollision(bar,ball, canvas);
        ball.ballBoundaryCheck(canvas);
        barBoundaryCheck(canvas);
        //movement
        ball.move();
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float touchx = event.getX();
        float touchy = event.getY();
        if(touchx>=(bar.barRight)){
            bar.setRight(bar.getRight() + 20);
            bar.setLeft(bar.getLeft() + 20);
            return true;
        }
        if(touchx<=bar.barLeft){
            bar.setRight(bar.getRight() - 20);
            bar.setLeft(bar.getLeft() - 20);
            return true;
        }
        return true;
    }

    public void barBoundaryCheck(Canvas canvas) {
        if(bar.getRight()>=canvas.getWidth())
            barMoveLeft = true;
        if(bar.getLeft()<=0)
            barMoveLeft = false;
    }

    public void BarCollision(Bar myBar,Ball myBall,Canvas canvas){
        // Ball And Bar Collision Detect
        if(((myBall.getY()+myBall.getR())>=myBar.getTop())&&((myBall.getY()+myBall.getR())<=myBar.getBottom())&& ((myBall.getX())>=myBar.getLeft())&& ((myBall.getX())<=myBar.getRight())) {
            myBall.setDY(-(myBall.getDY()));
        }

    }
    public void BrickCollision(ArrayList<Bricks> br ,Ball myBall,Canvas canvas) {
        // Ball And Bricks Collision
        for (int i = 0; i < bricks.size(); i++) {
            if (((myBall.getY() - myBall.getR()) <= br.get(i).getBottom()) && ((myBall.getY() + myBall.getR()) >= br.get(i).getTop()) && ((myBall.getX()) >= br.get(i).getLeft()) && ((myBall.getX()) <= br.get(i).getRight())) {
                br.remove(i);
                score += 1;
                myBall.setDY(-(myBall.getDY()));
            }
        }

    }
}
