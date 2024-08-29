package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_POINT_SIZE_GRANULARITY;
import static org.lwjgl.opengl.GL11.GL_POINT_SIZE_RANGE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex3f;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class Pointsz extends Points {

	@Override
	public void RenderScene() {

		float x,y,z,angle; 															// Storeage for coordinates and angles
		FloatBuffer sizes = ByteBuffer.allocateDirect( 16*4 ).asFloatBuffer();	 	// Store supported point size range
		float step;		 															// Store supported point size increments
		float curSize;	 															// Store current size

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

		// Save matrix state and do the rotation
		glPushMatrix();
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);
		
		// Get supported point size range and step size
		glGetFloat(GL_POINT_SIZE_RANGE , sizes);
		step = glGetFloat(GL_POINT_SIZE_GRANULARITY);
		
		// Set the initial point size
		curSize = sizes.get(0);

		// Set beginning z coordinate
		z = -50.0f;

		// Loop around in a circle three times
		for(angle = 0.0f; angle <= (2.0f*3.1415f)*3.0f; angle += 0.1f) {
			// Calculate x and y values on the circle
			x = 50.0f*(float)Math.sin(angle);
			y = 50.0f*(float)Math.cos(angle);
		
			// Specify the point size before the primative is specified
			glPointSize(curSize);

			// Draw the point
			glBegin(GL_POINTS);
				glVertex3f(x, y, z);
			glEnd();

			// Bump up the z value and the point size
			z += 0.5f;
			curSize += step;
		}

		// Restore matrix state
		glPopMatrix();
		
	}
	
	public static void main(String[] argv) {
		Pointsz game = new Pointsz();
		game.start();
	}
	
}
