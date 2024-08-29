package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_LINE_STIPPLE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineStipple;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Lstipple extends CaninoGameEngine3D {
	@Override
	public void SetupRC() {
		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

		// Set drawing color to green
		glColor3f(0.0f, 1.0f, 0.0f);

		// Enable Stippling
		glEnable(GL_LINE_STIPPLE);
	}

	@Override
	public void RenderScene() {
		float y;			    // Storeage for varying Y coordinate
		int factor = 3;			// Stippling factor
		short pattern = 0x5555;	// Stipple pattern

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

		// Save matrix state and do the rotation
		glPushMatrix();
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);

		// Step up Y axis 20 units at a time	
		for(y = -90.0f; y < 90.0f; y += 20.0f) {
			// Reset the repeat factor and pattern
			glLineStipple(factor,pattern);

			// Draw the line
			glBegin(GL_LINES);
			glVertex2f(-80.0f, y);
			glVertex2f(80.0f, y);	
			glEnd();

			factor++;
		}

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
		Lstipple game = new Lstipple();
		game.start();
	}

}
