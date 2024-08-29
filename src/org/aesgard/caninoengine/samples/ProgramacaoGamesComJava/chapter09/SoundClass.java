package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter09;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class SoundClass extends CaninoGameEngine2D {

    //initialize the component
    public void gameInit() {
        sound.load("/sound/screamandrun.aiff");
        if (sound.isLoaded()) sound.play();
    }

    //redraw the screen
    public void gameDraw() {
        if (sound.isLoaded()) {
            g2d.drawString("Sample file: " + sound.getFilename(), 10, 15);
            g2d.drawString(sound.getClip().getFormat().toString(), 10, 30);
        } else {
            g2d.drawString("Error loading sound file " + sound.getFilename(), 10, 15);
        }
    }
    
    public static void main(String[] args) {
    	new SoundClass().start(640,480);
    }

}
