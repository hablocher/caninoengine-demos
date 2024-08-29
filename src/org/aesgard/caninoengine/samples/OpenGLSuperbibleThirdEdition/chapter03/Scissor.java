package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SCISSOR_TEST;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glScissor;
import static org.lwjgl.opengl.GL11.glViewport;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Scissor extends CaninoGameEngine3D {

	@Override
	public void SetupRC() {

	}

	@Override
	public void RenderScene() {
        // Clear blue window
        glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
        
        // Now set scissor to smaller red sub region
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        glScissor(100, 100, 600, 400);
        glEnable(GL_SCISSOR_TEST);
        glClear(GL_COLOR_BUFFER_BIT);
        
        // Finally, an even smaller green rectangle
        glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
        glScissor(200, 200, 400, 200);
        glClear(GL_COLOR_BUFFER_BIT);
        
        // Turn scissor back off for next render
        glDisable(GL_SCISSOR_TEST);
	}

	@Override
	public void ChangeSize(int w, int h) {
		// Prevent a divide by zero
		if(h == 0)
			h = 1;

		// Set Viewport to window dimensions
	        glViewport(0, 0, w, h);


		// Set the perspective coordinate system
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Set 2D Coordinate system
		glOrtho(-4.0, 4.0, -3.0, 3.0, -1.0f, 1f);
		//gluOrtho2D(-4.0, 4.0, -3.0, 3.0);

		// Modelview matrix reset
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public static void main(String[] argv) {
		Scissor game = new Scissor();
		game.start();
	}

}
