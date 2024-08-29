package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter07;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.entity2d.base.ImageEntity;
import org.aesgard.caninoengine.sprite.BaseSpriteImage;

public class AnimationTest extends CaninoGameEngine2D {

    //sprite variables
    Image ball;
    int ballX = 300, ballY = 200;
    int speedX, speedY;

    //animation variables
    int currentFrame = 0;
    int totalFrames = 64;
    int animationDirection = 1;
    int frameCount = 0;
    int frameDelay = 5;
    
    private ImageEntity background = new ImageEntity(this);

    public void gameInit() {

    	//load the background image
        background.load("/sprites/woodgrain.png");

        //load the ball animation strip
        ball = BaseSpriteImage.readImage("/sprites/xball.png");

        speedX = rand.nextInt(6)+1;
        speedY = rand.nextInt(6)+1;
    }

    public void gameUpdate() {
        //see if it's time to animate
        frameCount++;
        if (frameCount > frameDelay) {
            frameCount=0;
            //update the animation frame
            currentFrame += animationDirection;
            if (currentFrame > totalFrames - 1) {
                currentFrame = 0;
            }
            else if (currentFrame < 0) {
                currentFrame = totalFrames - 1;
            }
        }

        //update the ball position
        ballX += speedX;
        if ((ballX < 0) || (ballX > getGameWidth() - 64)) {
            speedX *= -1;
            ballX += speedX;
        }
        ballY += speedY;
        if ((ballY < 0) || (ballY > getGameHeight() - 64)) {
            speedY *= -1;
            ballY += speedY;
        }
    }

    public void gameDraw() {
        g2d.drawImage(background.getImage(),0,0,getGameWidth()-1,getGameHeight()-1,getComponent());

        //draw the current frame of animation
        drawFrame(ball, getScreen(), ballX, ballY, 8, currentFrame, 64, 64);

        getScreen().setColor(Color.BLACK);
        getScreen().drawString("Position: " + ballX + "," + ballY, 5, 10);
        getScreen().drawString("Velocity: " + speedX + "," + speedY, 5, 25);
        getScreen().drawString("Animation: " + currentFrame, 5, 40);
    }

    //draw a single frame of animation
    public void drawFrame(Image source, Graphics2D dest,
                          int x, int y, int cols, int frame,
                          int width, int height)
    {
        int fx = (frame % cols) * width;
        int fy = (frame / cols) * height;
        dest.drawImage(source, x, y, x+width, y+height,
                       fx, fy, fx+width, fy+height, getComponent());
    }
    
    public static void main(String[] args) {
    	new AnimationTest().setFullscreen(false).start(640,480);
    }
}

