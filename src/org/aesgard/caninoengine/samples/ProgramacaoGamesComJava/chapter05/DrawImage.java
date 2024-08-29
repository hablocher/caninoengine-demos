package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter05;

import java.awt.Color;
import java.awt.Image;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.sprite.BaseSpriteImage;

public class DrawImage extends CaninoGameEngine2D {

	//image variable
    private Image image;

    public void gameInit() {
        image = BaseSpriteImage.readImage("/sprites/castle.png");
    }

    public void gameDraw() {
        //fill the background with black
        getScreen().setColor(Color.BLACK);
        getScreen().fillRect(0, 0, getGameWidth(), getGameHeight());

        //draw the image
        getScreen().drawImage(image, 0, 0, getComponent());
    }
    
    public static void main(String[] args)
    {
    	new DrawImage().start();
    }

}
