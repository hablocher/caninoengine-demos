package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter02;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Bounce extends CaninoGameEngine3D {
	//Initial square position and size
	float x = 0.0f;
	float y = 0.0f;
	float rsize = 25;

	// Step size in x and y directions
	// (number of pixels to move each time)
	float xstep = 3.0f;
	float ystep = 3.0f;

	// Keep track of windows changing width and height
	float windowWidth;
	float windowHeight;
	
	List<Timer> timers = new ArrayList<Timer>();

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
	    aspectRatio = w / h;
	    if (w <= h) {
	        windowWidth = 100;
	        windowHeight = 100 / aspectRatio;
	        glOrtho (-100.0, 100.0, -windowHeight, windowHeight, 1.0, -1.0);
	    } else {
	        windowWidth = 100 * aspectRatio;
	        windowHeight = 100;
	        glOrtho (-windowWidth, windowWidth, -100.0, 100.0, 1.0, -1.0);
	    }

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	@Override
	public void SetupRC() {
	    // Set clear color to blue
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
		
		addTaskScheduleAtFixedRate(new MoveSquare(), 0, 33);
	}

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

	   	// Set current drawing color to red
		//		   R	 G	   B
		glColor3f(1.0f, 0.0f, 0.0f);

		// Draw a filled rectangle with current color
		glRectf(x, y, x + rsize, y - rsize);
    	
	}

	private class MoveSquare extends TimerTask {

		@Override
		public void run() {
		    // Reverse direction when you reach left or right edge
		    if(x > windowWidth-rsize || x < -windowWidth)
		        xstep = -xstep;

		    // Reverse direction when you reach top or bottom edge
		    if(y > windowHeight || y < -windowHeight + rsize)
		        ystep = -ystep;

			// Actually move the square
		    x += xstep;
		    y += ystep;

		    // Check bounds. This is in case the window is made
		    // smaller while the rectangle is bouncing and the 
			// rectangle suddenly finds itself outside the new
		    // clipping volume
		    if(x > (windowWidth-rsize + xstep))
		        x = windowWidth-rsize-1;
			else if(x < -(windowWidth + xstep))
				x = -windowWidth -1;

		    if(y > (windowHeight + ystep))
		        y = windowHeight-1; 
			else if(y < -(windowHeight - rsize + ystep))
				y = -windowHeight + rsize - 1;
		}
		
	}
	
	public static void main(String[] argv) {
		Bounce game = new Bounce();
		game.start();
	}
}
