package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter06;

/*****************************************************
* Beginning Java Game Programming, 2nd Edition
* by Jonathan S. Harbour
* SpriteTest program
*****************************************************/

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.entity2d.Point2D;
import org.aesgard.caninoengine.entity2d.Sprite;
import org.aesgard.caninoengine.sprite.SpriteImage;

public class SpriteTest extends CaninoGameEngine2D {
	Sprite asteroid;
    SpriteImage background;

    public void gameInit() {
		//load the background
		background = new SpriteImage(getComponent());
        background.load("/sprites/bluespace.png");

        //load the asteroid sprite
        asteroid = new Sprite(getComponent(), getScreen());
        asteroid.load("/sprites/asteroid2.png");
    }

    public void gameDraw() {
    	getScreen().drawImage(background.getImage(), 0, 0, getGameWidth()-1, getGameHeight()-1, getComponent());

        int width = getGameWidth() - asteroid.imageWidth() - 1;
        int height = getGameHeight() - asteroid.imageHeight() - 1;

		Point2D point = new Point2D(rand.nextInt(width), rand.nextInt(height));
        asteroid.setPosition(point);
        asteroid.transform();
        asteroid.draw();
    }
    
    public static void main(String[] args) {
    	new SpriteTest().start(60,640,480);
    }

}
