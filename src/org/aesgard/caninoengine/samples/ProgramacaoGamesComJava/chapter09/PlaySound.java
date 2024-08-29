package org.aesgard.caninoengine.samples.ProgramacaoGamesComJava.chapter09;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.aesgard.caninoengine.CaninoGameEngine2D;
import org.aesgard.caninoengine.util.FileUtil;

public class PlaySound extends CaninoGameEngine2D {

	String filename = "/sound/gong.wav";
    AudioInputStream sample;


    //Initialize the component
    public void gameInit() {
        try {
            sample = AudioSystem.getAudioInputStream(FileUtil.getURL(filename));
            //create a sound buffer
            Clip clip = AudioSystem.getClip();
            //load the audio file
            clip.open(sample);
            //play sample
            clip.start();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (LineUnavailableException e) {
        } catch (UnsupportedAudioFileException e) {
        } catch (Exception e) { }
    }

    public void draw() {
        int y = 1;
        getScreen().drawString("Sample file: " + filename, 10, 15*y++);
        getScreen().drawString("  " + sample.getFormat().toString(), 10, 15*y++);
        getScreen().drawString("  Sampling rate: " + (int)sample.getFormat().getSampleRate(), 10, 15*y++);
        getScreen().drawString("  Sample channels: " + sample.getFormat().getChannels() , 10, 15*y++);
        getScreen().drawString("  Encoded format: " + sample.getFormat().getEncoding().toString(), 10, 15*y++);
        getScreen().drawString("  Sample size: " + sample.getFormat().getSampleSizeInBits() + "-bit", 10, 15*y++);
        getScreen().drawString("  Frame size: " + sample.getFormat().getFrameSize(), 10, 15*y++);
    }
    
    public static void main(String[] args) {
    	new PlaySound().start(640,480);
    }

}
