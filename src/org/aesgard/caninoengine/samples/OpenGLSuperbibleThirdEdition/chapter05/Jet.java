package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter05;

import static org.aesgard.caninoengine.glutil.APITranslations.glColor3ub;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Jet extends CaninoGameEngine3D {
	@Override
	public void RenderScene() {
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	    // Save matrix state and do the rotation
	    glPushMatrix();
	    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	    glRotatef(yRot, 0.0f, 1.0f, 0.0f);


	    // Nose Cone /////////////////////////////
	    // White
	    glColor3ub(255, 255, 255);
	    glBegin(GL_TRIANGLES);
	        glVertex3f(0.0f, 0.0f, 60.0f);
	        glVertex3f(-15.0f, 0.0f, 30.0f);
	        glVertex3f(15.0f,0.0f,30.0f);

	        // Black
	        glColor3ub(0,0,0);
	        glVertex3f(15.0f,0.0f,30.0f);
	        glVertex3f(0.0f, 15.0f, 30.0f);
	        glVertex3f(0.0f, 0.0f, 60.0f);
		
	        // Red
	        glColor3ub(255,0,0);
	        glVertex3f(0.0f, 0.0f, 60.0f);
	        glVertex3f(0.0f, 15.0f, 30.0f);
	        glVertex3f(-15.0f,0.0f,30.0f);


		// Body of the Plane ////////////////////////
	        // Green
	        glColor3ub(0,255,0);
	        glVertex3f(-15.0f,0.0f,30.0f);
	        glVertex3f(0.0f, 15.0f, 30.0f);
	        glVertex3f(0.0f, 0.0f, -56.0f);
			
	        glColor3ub(255,255,0);
	        glVertex3f(0.0f, 0.0f, -56.0f);
	        glVertex3f(0.0f, 15.0f, 30.0f);
	        glVertex3f(15.0f,0.0f,30.0f);	
		
	        glColor3ub(0, 255, 255);
	        glVertex3f(15.0f,0.0f,30.0f);
	        glVertex3f(-15.0f, 0.0f, 30.0f);
	        glVertex3f(0.0f, 0.0f, -56.0f);
		
		///////////////////////////////////////////////
		// Left wing
		// Large triangle for bottom of wing
	        glColor3ub(128,128,128);
	        glVertex3f(0.0f,2.0f,27.0f);
	        glVertex3f(-60.0f, 2.0f, -8.0f);
	        glVertex3f(60.0f, 2.0f, -8.0f);
		
	        glColor3ub(64,64,64);
	        glVertex3f(60.0f, 2.0f, -8.0f);
	        glVertex3f(0.0f, 7.0f, -8.0f);
	        glVertex3f(0.0f,2.0f,27.0f);
		
	        glColor3ub(192,192,192);
	        glVertex3f(60.0f, 2.0f, -8.0f);
	        glVertex3f(-60.0f, 2.0f, -8.0f);
	        glVertex3f(0.0f,7.0f,-8.0f);
		
		// Other wing top section
	        glColor3ub(64,64,64);
	        glVertex3f(0.0f,2.0f,27.0f);
	        glVertex3f(0.0f, 7.0f, -8.0f);
	        glVertex3f(-60.0f, 2.0f, -8.0f);
		
		// Tail section///////////////////////////////
		// Bottom of back fin
	        glColor3ub(255,128,255);
	        glVertex3f(-30.0f, -0.50f, -57.0f);
	        glVertex3f(30.0f, -0.50f, -57.0f);
	        glVertex3f(0.0f,-0.50f,-40.0f);
		
	        // top of left side
	        glColor3ub(255,128,0);
	        glVertex3f(0.0f,-0.5f,-40.0f);
	        glVertex3f(30.0f, -0.5f, -57.0f);
	        glVertex3f(0.0f, 4.0f, -57.0f);
		
	        // top of right side
	        glColor3ub(255,128,0);
	        glVertex3f(0.0f, 4.0f, -57.0f);
	        glVertex3f(-30.0f, -0.5f, -57.0f);
	        glVertex3f(0.0f,-0.5f,-40.0f);
		
	        // back of bottom of tail
	        glColor3ub(255,255,255);
	        glVertex3f(30.0f,-0.5f,-57.0f);
	        glVertex3f(-30.0f, -0.5f, -57.0f);
	        glVertex3f(0.0f, 4.0f, -57.0f);
		
	        // Top of Tail section left
	        glColor3ub(255,0,0);
	        glVertex3f(0.0f,0.5f,-40.0f);
	        glVertex3f(3.0f, 0.5f, -57.0f);
	        glVertex3f(0.0f, 25.0f, -65.0f);
		
	        glColor3ub(255,0,0);
	        glVertex3f(0.0f, 25.0f, -65.0f);
	        glVertex3f(-3.0f, 0.5f, -57.0f);
	        glVertex3f(0.0f,0.5f,-40.0f);

	        // Back of horizontal section
	        glColor3ub(128,128,128);
	        glVertex3f(3.0f,0.5f,-57.0f);
	        glVertex3f(-3.0f, 0.5f, -57.0f);
	        glVertex3f(0.0f, 25.0f, -65.0f);
		
	    glEnd(); // Of Jet

	    glPopMatrix();
	}

	@Override
	public void SetupRC() {
	    glEnable(GL_DEPTH_TEST);	// Hidden surface removal
	    glEnable(GL_CULL_FACE);		// Do not calculate inside of jet
	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out

	    // Nice light blue
	    glClearColor(0.0f, 0.0f, 05.f,1.0f);
	}

	@Override
	public void ChangeSize(int w, int h) {
	    float nRange = 80.0f;
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
	        glOrtho (-nRange, nRange, -nRange*h/w, nRange*h/w, -nRange, nRange);
	    else 
	        glOrtho (-nRange*w/h, nRange*w/h, -nRange, nRange, -nRange, nRange);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	public static void main(String[] argv) {
		Jet game = new Jet();
		game.start();
	}

}
