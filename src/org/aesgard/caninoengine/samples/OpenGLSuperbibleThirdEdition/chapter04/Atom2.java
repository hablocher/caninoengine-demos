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
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;


public class Atom2 extends CaninoGameEngine3D {

	// Angle of revolution around the nucleus
	float fElect1 = 0.0f;

	@Override
	public void RenderScene() {

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

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
		fElect1 += 20.0f;
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
	    float fAspect;

	    // Prevent a divide by zero
	    if(h == 0)
	        h = 1;

	    // Set Viewport to window dimensions
	    glViewport(0, 0, w, h);

	    // Reset coordinate system
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    fAspect = (float)w/(float)h;
	    gluPerspective(45.0f, fAspect, 1.0f, 500.0f);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	    glTranslatef(0.0f, 0.0f, -250.0f);
	}
	
	public static void main(String[] argv) {
		Atom2 game = new Atom2();
		game.setSleepTime(50);
		game.start();
	}

}
