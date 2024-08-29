package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter08;

import java.awt.Color;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.entity2d.base.ImageEntity;

public class KeyboardTest extends CaninoGameEngine2D {
    static int SCREENWIDTH = 640;
    static int SCREENHEIGHT = 480;
    
    private ImageEntity background = new ImageEntity(this);

    public void gameInit() {
        background.load("/sprites/woodgrain.png");
    }

    public void gameDraw() {
        g2d.drawImage(background.getImage(),0,0,SCREENWIDTH-1,SCREENHEIGHT-1,getComponent());
        getScreen().setColor(Color.BLACK);
        getScreen().drawString("Press a key...", 20, 20);
        getScreen().drawString("Key code: " + getKeyCode(), 20, 50);
        getScreen().drawString("Key char: " + getKeyChar(), 20, 70);
    }
    
    public static void main(String[] args) {
    	new KeyboardTest().start(640,480);
    }
}
