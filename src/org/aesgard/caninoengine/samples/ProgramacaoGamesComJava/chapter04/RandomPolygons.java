package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter04;

/**********************************************************
 * Beginning Java Game Programming, 2nd Edition
 * by Jonathan S. Harbour
 * RandomPolygons program
 **********************************************************/

import java.awt.Color;
import java.awt.Polygon;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class RandomPolygons extends CaninoGameEngine2D {

	private int[] xpoints;
    private int[] ypoints;

    //here's the shape used for drawing
    private Polygon poly;

    //component init event
    public void gameInit() {
    	xpoints = new int[]{  0,-10, -7,  7, 10 };
        ypoints = new int[]{-10, -2, 10, 10, -2 };
        
        poly = new Polygon(xpoints, ypoints, xpoints.length);
    }

    //component paint event
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
        	getScreen().scale(5 * rand.nextDouble(), 5 * rand.nextDouble());

            //draw the shape with a random color
        	getScreen().setColor(new Color(rand.nextInt()));
        	getScreen().fill(poly);
        	getScreen().draw(poly);
        }
    }
    
    public static void main(String[] args)
    {
    	new RandomPolygons().start();
    }
    
}
