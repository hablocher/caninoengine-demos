package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter09;

import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.util.FileUtil;

public class PlayMusic extends CaninoGameEngine2D {

	String filename = "/midi/titlemusic.mid";
    Sequence song;

    //initialize the component
    public void gameInit() {
        try {
            song = MidiSystem.getSequence(FileUtil.getURL(filename));
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(song);
            sequencer.open();
            sequencer.start();

        } catch (InvalidMidiDataException e) {
        } catch (MidiUnavailableException e) {
        } catch (IOException e) { }
    }

    //repaint the component window
    public void draw() {
        int x=10, y = 1;
        if (song != null) {
            getScreen().drawString("Midi File: " + filename, x, 15 * y++);
            getScreen().drawString("Resolution: " + song.getResolution(), x, 15 * y++);
            getScreen().drawString("Tick length: " + song.getTickLength(), x, 15 * y++);
            getScreen().drawString("Tracks: " + song.getTracks().length, x, 15 * y++);
            getScreen().drawString("Patches: " + song.getPatchList().length, x, 15 * y++);
        } else {
        	getScreen().drawString("Error loading sequence file " + filename, 10, 15);
        }
    }
    
    public static void main(String[] args) {
    	new PlayMusic().start(640,480);
    }

}
