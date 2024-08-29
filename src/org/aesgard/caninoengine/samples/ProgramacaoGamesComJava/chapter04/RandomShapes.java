package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter04;

import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class RandomShapes extends CaninoGameEngine2D {

	//here's the shape used for drawing
    private Shape shape;

    public void gameInit() {
        shape = new Rectangle2D.Double(-1.0, -1.0, 1.0, 1.0);
    }

    public void gameDraw() {
        //save the window width/height
        int width = getGameWidth();
        int height = getGameHeight();

        //fill the background with black
        getScreen().setColor(Color.BLACK);
        getScreen().fillRect(0, 0, width, height);

        for (int n = 0; n < 300; n++) {
            //reset Graphics2D to the identity transform
        	getScreen().setTransform(identity);

            //move, rotate, and scale the shape randomly
        	getScreen().translate(rand.nextInt() % width, rand.nextInt() % height);
        	getScreen().rotate(Math.toRadians(360 * rand.nextDouble()));
        	getScreen().scale(60 * rand.nextDouble(), 60 * rand.nextDouble());

            //draw the shape with a random color
        	getScreen().setColor(new Color(rand.nextInt()));
        	getScreen().fill(shape);
        }
    }
    
    public static void main(String[] args)
    {
    	new RandomShapes().start();
    }

}
