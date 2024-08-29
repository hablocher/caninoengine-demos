package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter07;

import java.awt.Color;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.entity2d.AnimatedSprite;
import org.aesgard.caninoengine.entity2d.Point2D;
import org.aesgard.caninoengine.entity2d.base.ImageEntity;

public class AnimationClass extends CaninoGameEngine2D {

	//sprite variables
    private AnimatedSprite ball;

    
    private ImageEntity background = new ImageEntity(this);

    public void gameInit() {
        background.load("/sprites/woodgrain.png");
        //load the ball animation strip
        ball = new AnimatedSprite(getComponent(), getScreen());
        ball.load("/sprites/xball.png", 8, 8, 64, 64);
        ball.setPosition(new Point2D(rand.nextInt(300), rand.nextInt(200)));
        ball.setFrameDelay(1);
        ball.setVelocity(new Point2D(3,3));
        ball.setRotationRate(1.0);
    }

    public void gameUpdate() {
        //see if it's time to animate
        ball.updateAnimation();

        //update the ball position
        ball.updatePosition();
        if ((ball.position().X() < 0) || (ball.position().X() > getGameWidth() - 64)) {
            double x = ball.velocity().X() * -1;
            ball.setVelocity(new Point2D(x, ball.velocity().Y()));
            x = ball.position().X();
            ball.setPosition(new Point2D(x, ball.position().Y()));
        }
        if ((ball.position().Y() < 0) || (ball.position().Y() > getGameHeight() - 64)) {
            double y = ball.velocity().Y() * -1;
            ball.setVelocity(new Point2D(ball.velocity().X(), y));
            y = ball.position().Y() + ball.velocity().Y();
            ball.setPosition(new Point2D(ball.position().X(), y));
        }

        //lastly, update the rotation
        ball.updateRotation();
    }

    public void gameDraw() {
        g2d.drawImage(background.getImage(),0,0,getGameWidth()-1,getGameHeight()-1,getComponent());

        ball.draw();

        getScreen().setColor(Color.BLACK);
        getScreen().drawString("Position: " + ball.position().X() + "," +
                       ball.position().Y(), 5, 10);
        getScreen().drawString("Velocity: " + ball.velocity().X() + "," +
                       ball.velocity().Y(), 5, 25);
        getScreen().drawString("Animation: " + ball.currentFrame(), 5, 40);
    }

    public static void main(String[] args) {
    	new AnimationClass().setFullscreen(false).start(640,480);
    }
}

