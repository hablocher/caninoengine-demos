package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter04;

import java.awt.Color;
import java.awt.Polygon;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class RotatePolygon extends CaninoGameEngine2D {

	private int[] xpoints;
    private int[] ypoints;

    //here's the shape used for drawing
    private Polygon poly;

    //polygon rotation variable
    private int rotation;
    
    private int velocity;
    
    protected void gameInit() {
    	xpoints  = new int[]{  0,-10, -7,  7, 10 };
        ypoints  = new int[]{-10, -2, 10, 10, -2 };
        rotation = 0;
        velocity = 5;

        //create the polygon
        poly = new Polygon(xpoints, ypoints, xpoints.length);
    }

    public void gameDraw() {
    	
    	// Reset o sistema de coordenadas do buffer
    	getScreen().setTransform(identity);
    	
        //fill the background with black
        getScreen().setColor(Color.BLACK);
        getScreen().fillRect(0, 0, getGameWidth(), getGameHeight());

        //move, rotate, and scale the shape randomly
        getScreen().translate(getGameWidth() / 2, getGameHeight() / 2);
        getScreen().scale(20, 20);
        getScreen().rotate(Math.toRadians(rotation));

        //draw the shape with a random color
        getScreen().setColor(Color.RED);
        getScreen().fill(poly);
        getScreen().setColor(Color.BLUE);
        getScreen().draw(poly);
        
    }
    
    public void doKeyLeft() {
        rotation-=velocity;
        if (rotation < 0) rotation = 359;
    }
    
    public void doKeyRight() {
        rotation+=velocity;
        if (rotation > 360) rotation = 0;
    }

    public void doMouseButton1Pressed() {
        rotation-=velocity;
        if (rotation < 0) rotation = 359;
    }
    
    public void doMouseButton3Pressed() {
        rotation+=velocity;
        if (rotation > 360) rotation = 0;
    }
    
    public static void main(String[] args)
    {
    	new RotatePolygon().setFullscreen(false).start();
    }

}
