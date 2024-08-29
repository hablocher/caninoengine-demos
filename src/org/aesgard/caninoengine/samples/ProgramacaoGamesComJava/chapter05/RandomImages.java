package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter05;

/*************************************************************
 * Beginning Java Game Programming, 2nd Edition
 * by Jonathan S. Harbour
 * RandomImages program
 *************************************************************/
import java.awt.Color;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.sprite.BaseSpriteImage;

public class RandomImages extends CaninoGameEngine2D {

	//image variable
    private Image image;

    //component init event
    public void gameInit() {
        image = BaseSpriteImage.readImage("/sprites/spaceship.png");
    }

    //component paint event
    public void gameDraw() {
        //working transform object
        AffineTransform trans = new AffineTransform();

        int width = getGameWidth();
        int height = getGameHeight();

        //fill the background with black
        getScreen().setColor(Color.BLACK);
        getScreen().fillRect(0, 0, getGameWidth(), getGameHeight());

        //draw the image multiple times
        for (int n = 0; n < 50; n++) {
            trans.setTransform(identity);
            //move, rotate, scale the image randomly
            trans.translate(rand.nextInt()%width, rand.nextInt()%height);
            trans.rotate(Math.toRadians(360 * rand.nextDouble()));
            double scale = rand.nextDouble()+1;
            trans.scale(scale, scale);

            //draw the image
            getScreen().drawImage(image, trans, getComponent());
        }
    }
    public static void main(String[] args)
    {
    	new RandomImages().start();
    }
    
}
