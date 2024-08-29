package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter08;

import java.awt.Color;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.entity2d.base.ImageEntity;

public class MouseTest extends CaninoGameEngine2D{
	//declare some mouse event variables
    int clickx, clicky;
    int pressx, pressy;
    int releasex, releasey;
    int enterx, entery;
    int exitx, exity;
    int dragx, dragy;
    int movex, movey;
    int mousebutton;
    
    static int SCREENWIDTH = 640;
    static int SCREENHEIGHT = 480;
    
    private ImageEntity background = new ImageEntity(this);

    public void gameInit() {
    	//load the background image
        background.load("/sprites/woodgrain.png");
    }

    //redraw the component window
    public void gameDraw() {
        g2d.drawImage(background.getImage(),0,0,SCREENWIDTH-1,SCREENHEIGHT-1,getComponent());

        getScreen().setColor(Color.BLACK);

        getScreen().drawString("Mouse clicked " + mousebutton + " at " + getMouseX() + "," + getMouseY(), 10, 10);
        getScreen().drawString("Mouse entered at " + enterx + "," + entery, 10, 25);
        getScreen().drawString("Mouse exited at " + exitx + "," + exity, 10, 40);
        getScreen().drawString("Mouse pressed " + mousebutton + " at " + pressx + "," + pressy, 10, 55);
        getScreen().drawString("Mouse released " + mousebutton + " at " + releasex + "," + releasey, 10, 70);
        getScreen().drawString("Mouse dragged at " + dragx + "," + dragy, 10, 85);
        getScreen().drawString("Mouse moved at " + movex + "," + movey, 10, 100);
    }

    public void doMouseButton1Pressed() {
        mousebutton = 1;
    }
    public void doMouseButton2Pressed() {
        mousebutton = 2;
    }
    public void doMouseButton3Pressed() {
        mousebutton = 3;
    }
    public void doMouseEntered() {
        enterx = getMouseX();
        entery = getMouseY();
    }
    public void doMouseExited() {
        exitx = getMouseX();
        exity = getMouseY();
    }
    public void doMousePressed() {
        pressx = getMouseX();
        pressy = getMouseY();
    }
    public void doMouseReleased() {
        releasex = getMouseX();
        releasey = getMouseY();
    }
    public void doMouseDragged() {
        dragx = getMouseX();;
        dragy = getMouseY();
    }
    public void doMouseMoved() {
        movex = getMouseX();
        movey = getMouseY();
    }
    
    public static void main(String[] args) {
    	new MouseTest().start(640,480);
    }

}
