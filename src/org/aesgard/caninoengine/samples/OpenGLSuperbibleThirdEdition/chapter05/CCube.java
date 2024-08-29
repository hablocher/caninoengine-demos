package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter05;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DITHER;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class CCube extends CaninoGameEngine3D {
	@Override
	public void RenderScene() {
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
	    glPushMatrix();

	    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	    glRotatef(yRot, 0.0f, 1.0f, 0.0f);


	    // Draw six quads
	    glBegin(GL_QUADS);
	        // Front Face
	        // White
	        glColor3ub((byte) 255, (byte)255, (byte)255);
	        glVertex3f(50.0f,50.0f,50.0f);

	        // Yellow
	        glColor3ub((byte) 255, (byte)255, (byte)0);
	        glVertex3f(50.0f,-50.0f,50.0f);

	        // Red
	        glColor3ub((byte) 255, (byte)0, (byte)0);
	        glVertex3f(-50.0f,-50.0f,50.0f);

	        // Magenta
	        glColor3ub((byte) 255, (byte)0, (byte)255);
	        glVertex3f(-50.0f,50.0f,50.0f);

		
		// Back Face
	        // Cyan
	        glColor3f(0.0f, 1.0f, 1.0f);
	        glVertex3f(50.0f,50.0f,-50.0f);

	        // Green
	        glColor3f(0.0f, 1.0f, 0.0f);
	        glVertex3f(50.0f,-50.0f,-50.0f);
			
	        // Black
	        glColor3f(0.0f, 0.0f, 0.0f);
	        glVertex3f(-50.0f,-50.0f,-50.0f);

	        // Blue
	        glColor3f(0.0f, 0.0f, 1.0f);
	        glVertex3f(-50.0f,50.0f,-50.0f);
		
		// Top Face
	        // Cyan
	        glColor3f(0.0f, 1.0f, 1.0f);
	        glVertex3f(50.0f,50.0f,-50.0f);

	        // White
	        glColor3f(1.0f, 1.0f, 1.0f);
	        glVertex3f(50.0f,50.0f,50.0f);

	        // Magenta
	        glColor3f(1.0f, 0.0f, 1.0f);
	        glVertex3f(-50.0f,50.0f,50.0f);

	        // Blue
	        glColor3f(0.0f, 0.0f, 1.0f);
	        glVertex3f(-50.0f,50.0f,-50.0f);
		
		// Bottom Face
	        // Green
	        glColor3f(0.0f, 1.0f, 0.0f);
	        glVertex3f(50.0f,-50.0f,-50.0f);

	        // Yellow
	        glColor3f(1.0f, 1.0f, 0.0f);
	        glVertex3f(50.0f,-50.0f,50.0f);

	        // Red
	        glColor3f(1.0f, 0.0f, 0.0f);
	        glVertex3f(-50.0f,-50.0f,50.0f);

	        // Black
	        glColor3f(0.0f, 0.0f, 0.0f);
	        glVertex3f(-50.0f,-50.0f,-50.0f);
		
		// Left face
	        // White
	        glColor3f(1.0f, 1.0f, 1.0f);
	        glVertex3f(50.0f,50.0f,50.0f);

	        // Cyan
	        glColor3f(0.0f, 1.0f, 1.0f);
	        glVertex3f(50.0f,50.0f,-50.0f);

	        // Green
	        glColor3f(0.0f, 1.0f, 0.0f);
	        glVertex3f(50.0f,-50.0f,-50.0f);

	        // Yellow
	        glColor3f(1.0f, 1.0f, 0.0f);
	        glVertex3f(50.0f,-50.0f,50.0f);
		
		// Right face
	        // Magenta
	        glColor3f(1.0f, 0.0f, 1.0f);
	        glVertex3f(-50.0f,50.0f,50.0f);

	        // Blue
	        glColor3f(0.0f, 0.0f, 1.0f);
	        glVertex3f(-50.0f,50.0f,-50.0f);

	        // Black
	        glColor3f(0.0f, 0.0f, 0.0f);
	        glVertex3f(-50.0f,-50.0f,-50.0f);

	        // Red
	        glColor3f(1.0f, 0.0f, 0.0f);
	        glVertex3f(-50.0f,-50.0f,50.0f);
	    glEnd();

	    glPopMatrix();

	}

	@Override
	public void SetupRC() {
	    // Black background
	    glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

	    glEnable(GL_DEPTH_TEST);	
	    glEnable(GL_DITHER);
	    glShadeModel(GL_SMOOTH);
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

	    fAspect = (float)w / (float)h;
	    gluPerspective(35.0f, fAspect, 1.0f, 1000.0f);
	     
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	    glTranslatef(0.0f, 0.0f, -400.0f);
	}
	
	public static void main(String[] argv) {
		CCube game = new CCube();
		game.start();
	}

}
