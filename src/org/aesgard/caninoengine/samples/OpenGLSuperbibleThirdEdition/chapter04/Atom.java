package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter04;

import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Atom extends CaninoGameEngine3D {

	// Angle of revolution around the nucleus
	float fElect1 = 0.0f;

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Reset the modelview matrix
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Translate the whole scene out and into view	
		// This is the initial viewing transformation
		glTranslatef(0.0f, 0.0f, -100.0f);	

		// Red Nucleus
		glColor3ub((byte)255, (byte)0, (byte)0);
		
		glutSolidSphere(10.0f, 15, 15);
		
		// Yellow Electrons
		glColor3ub((byte)255,(byte)255,(byte)0);

		// First Electron Orbit
		// Save viewing transformation
		glPushMatrix();

		// Rotate by angle of revolution
		glRotatef(fElect1, 0.0f, 1.0f, 0.0f);

		// Translate out from origin to orbit distance
		glTranslatef(90.0f, 0.0f, 0.0f);

		// Draw the electron
		glutSolidSphere(6.0f, 15, 15);

		// Restore the viewing transformation
		glPopMatrix();

		// Second Electron Orbit
		glPushMatrix();
		glRotatef(45.0f, 0.0f, 0.0f, 1.0f);
		glRotatef(fElect1, 0.0f, 1.0f, 0.0f);
		glTranslatef(-70.0f, 0.0f, 0.0f);
		glutSolidSphere(6.0f, 15, 15);
		glPopMatrix();


		// Third Electron Orbit
		glPushMatrix();
		glRotatef(360.0f-45.0f,0.0f, 0.0f, 1.0f);
		glRotatef(fElect1, 0.0f, 1.0f, 0.0f);
		glTranslatef(0.0f, 0.0f, 60.0f);
		glutSolidSphere(6.0f, 15, 15);
		glPopMatrix();


		// Increment the angle of revolution
		fElect1 += 10.0f;
		if(fElect1 > 360.0f)
			fElect1 = 0.0f;

	}
	
	@Override
	public void SetupRC() {
		glEnable(GL_DEPTH_TEST);	// Hidden surface removal
		glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
		glEnable(GL_CULL_FACE);		// Do not calculate inside of jet

		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
		
	}

	@Override
	public void ChangeSize(int w, int h) {
	    float nRange = 100.0f;

	    // Prevent a divide by zero
	    if(h == 0)
	        h = 1;

	    // Set Viewport to window dimensions
	    glViewport(0, 0, w, h);


	    // Reset coordinate system
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    // Establish clipping volume (left, right, bottom, top, near, far)
	    if (w <= h) 
	        glOrtho (-nRange, nRange, nRange*h/w, -nRange*h/w, -nRange*2.0f, nRange*2.0f);
	    else 
	        glOrtho (-nRange*w/h, nRange*w/h, nRange, -nRange, -nRange*2.0f, nRange*2.0f);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	public static void main(String[] argv) {
		Atom game = new Atom();
		game.setSleepTime(50);
		game.start();
	}

}
