package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter10;

import java.awt.Color;
import java.awt.Rectangle;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class SimpleLoop extends CaninoGameEngine2D {

    public void gameDraw() {
        //create a random rectangle
        int w = rand.nextInt(100);
        int h = rand.nextInt(100);
        int x = rand.nextInt(getGameWidth() - w);
        int y = rand.nextInt(getGameHeight() - h);
        Rectangle rect = new Rectangle(x,y,w,h);

        //generate a random color
        int red = rand.nextInt(256);
        int green = rand.nextInt(256);
        int blue = rand.nextInt(256);
        g2d.setColor(new Color(red,green,blue));

        //draw the rectangle
        g2d.fill(rect);
    }
    
    public static void main(String[] args) {
    	new SimpleLoop().start();
    }

}
