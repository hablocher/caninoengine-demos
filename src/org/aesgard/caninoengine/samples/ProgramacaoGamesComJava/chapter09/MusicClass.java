package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter09;

import org.aesgard.caninoengine.CaninoGameEngine2D;

public class MusicClass extends CaninoGameEngine2D {

    //initialize the component
    public void gameInit() {
        midi.load("/midi/bsg.mid");
        if (midi.isLoaded()) midi.play();
    }

    //repaint the component window
    public void gameDraw() {
        int x=10, y = 1;
        if (midi.isLoaded()) {
            g2d.drawString("Midi File: " + midi.getFilename(), x, 15 * y++);
            g2d.drawString("Resolution: " + midi.getSong().getResolution(), x, 15 * y++);
            g2d.drawString("Tick length: " + midi.getSong().getTickLength(), x, 15 * y++);
            g2d.drawString("Tracks: " + midi.getSong().getTracks().length, x, 15 * y++);
        } else {
            g2d.drawString("Error loading sequence file " + midi.getFilename(), 10, 15);
        }
    }
    
    public static void main(String[] args) {
    	new MusicClass().start(640,480);
    }
}
