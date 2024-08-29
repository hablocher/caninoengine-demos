package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter05;

/*****************************************************
* Beginning Java Game Programming, 2nd Edition
* by Jonathan S. Harbour
* TransparentTest program
*****************************************************/

import java.awt.Image;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.sprite.BaseSpriteImage;

public class TransparentTest extends CaninoGameEngine2D {

	private Image image;

    public void gameInit() {
        image = BaseSpriteImage.readImage("/sprites/asteroid1.png");
    }

    public void gameDraw() {
        int width = getGameWidth() - image.getWidth(getComponent()) - 1;
        int height = getGameHeight() - image.getHeight(getComponent()) - 1;
        getScreen().drawImage(image, rand.nextInt(width), rand.nextInt(height), getComponent());
    }
    
    public static void main(String[] args)
    {
    	new TransparentTest().start();
    }
}
