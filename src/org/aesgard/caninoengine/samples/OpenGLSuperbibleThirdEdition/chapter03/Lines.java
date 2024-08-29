package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Lines extends CaninoGameEngine3D {
	
	@Override
	public void SetupRC() {
		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

		// Set drawing color to green
		glColor3f(0.0f, 1.0f, 0.0f);
	}

	@Override
	public void RenderScene() {
		
        float x,y,z,angle; // Storeage for coordinates and angles

    	// Clear the window with current clearing color
    	glClear(GL_COLOR_BUFFER_BIT);

    	// Save matrix state and do the rotation
    	glPushMatrix();
    	glRotatef(xRot, 1.0f, 0.0f, 0.0f);
    	glRotatef(yRot, 0.0f, 1.0f, 0.0f);

    	// Call only once for all remaining points
    	glBegin(GL_LINES);

    	z = 0.0f;
    	for(angle = 0.0f; angle <= GL_PI; angle += (GL_PI / 20.0f)) {
    		// Top half of the circle
    		x = 50.0f*(float)Math.sin(angle);
    		y = 50.0f*(float)Math.cos(angle);
    		glVertex3f(x, y, z);

    		// Bottom half of the circle
    		x = 50.0f*(float)Math.sin(angle+GL_PI);
    		y = 50.0f*(float)Math.cos(angle+GL_PI);
    		glVertex3f(x, y, z);	
    	}

    	// Done drawing points
    	glEnd();

    	// Restore transformations
    	glPopMatrix();
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
	    	glOrtho (-nRange, nRange, -nRange*h/w, nRange*h/w, -nRange, nRange);
	    else 
	    	glOrtho (-nRange*w/h, nRange*w/h, -nRange, nRange, -nRange, nRange);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	public static void main(String[] argv) {
		Lines game = new Lines();
		game.start();
	}
}
