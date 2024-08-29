package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter05;

import java.awt.Image;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.sprite.BaseSpriteImage;

public class BitmapTest extends CaninoGameEngine2D {
    private Image image;

    public void gameInit() {
        image = BaseSpriteImage.readImage("/sprites/asteroid0.png");
    }

    public void gameDraw() {
        int width = getGameWidth() - image.getWidth(getComponent()) - 1;
        int height = getGameHeight() - image.getHeight(getComponent()) - 1;
        getScreen().drawImage(image, rand.nextInt(width), rand.nextInt(height), getComponent());
    }
    
    public static void main(String[] args)
    {
    	new BitmapTest().start();
    }
}
