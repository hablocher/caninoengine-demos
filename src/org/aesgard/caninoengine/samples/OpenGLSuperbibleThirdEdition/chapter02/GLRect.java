package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter02;

import static org.lwjgl.opengl.GL11.*;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class GLRect extends CaninoGameEngine3D {

	@Override
	public void SetupRC() {
	    // Set clear color to blue
	    glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
	}

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

	   	// Set current drawing color to red
		//		   R	 G	   B
		glColor3f(1.0f, 0.0f, 0.0f);

		// Draw a filled rectangle with current color
		glRectf(-25.0f, 25.0f, 25.0f, -25.0f);
	}

	@Override
	public void ChangeSize(int w, int h) {
		float aspectRatio;

		// Prevent a divide by zero
		if(h == 0)
			h = 1;
		
		// Set Viewport to window dimensions
		glViewport(0, 0, w, h);

		// Reset coordinate system
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Establish clipping volume (left, right, bottom, top, near, far)
		aspectRatio = (float)w / (float)h;
		if (w <= h) 
			glOrtho (-100.0, 100.0, -100 / aspectRatio, 100.0 / aspectRatio, 1.0, -1.0);
		else 
			glOrtho (-100.0 * aspectRatio, 100.0 * aspectRatio, -100.0, 100.0, 1.0, -1.0);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public static void main(String[] argv) {
		GLRect game = new GLRect();
		game.start();
	}

}
