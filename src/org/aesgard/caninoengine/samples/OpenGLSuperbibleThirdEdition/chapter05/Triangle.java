package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter05;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Triangle extends CaninoGameEngine3D {


	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

		// Enable smooth shading
		glShadeModel(GL_SMOOTH);

		// Draw the triangle
		glBegin(GL_TRIANGLES);
			// Red Apex
			glColor3ub((byte)255,(byte)0,(byte)0);
			glVertex3f(0.0f,200.0f,0.0f);

			// Green on the right bottom corner
			glColor3ub((byte)0,(byte)255,(byte)0);
			glVertex3f(200.0f,-70.0f,0.0f);

			// Blue on the left bottom corner
			glColor3ub((byte)0,(byte)0,(byte)255);
			glVertex3f(-200.0f, -70.0f, 0.0f);
		glEnd();
	}

	@Override
	public void SetupRC() {
		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f );	}
	
	@Override
	public void ChangeSize(int w, int h) {
		float windowHeight,windowWidth;
		
		// Prevent a divide by zero, when window is too short
		// (you cant make a window of zero width).
		if(h == 0)
			h = 1;

		// Set the viewport to be the entire window
	    glViewport(0, 0, w, h);

		// Reset the coordinate system before modifying
	    glLoadIdentity();


		// Keep the square square.

		// Window is higher than wide
		if (w <= h) 
			{
			windowHeight = 250.0f*h/w;
			windowWidth = 250.0f;
			}
	    else 
			{
			// Window is wider than high
			windowWidth = 250.0f*w/h;
			windowHeight = 250.0f;
			}

		// Set the clipping volume
		glOrtho(-windowWidth, windowWidth, -windowHeight, windowHeight, 1.0f, -1.0f);
	}

	public static void main(String[] argv) {
		Triangle game = new Triangle();
		game.start();
	}

}
