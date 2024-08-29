package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_INCR;
import static org.lwjgl.opengl.GL11.GL_KEEP;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEVER;
import static org.lwjgl.opengl.GL11.GL_NOTEQUAL;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearStencil;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRectf;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import static org.lwjgl.opengl.GL11.glStencilOp;
import static org.lwjgl.opengl.GL11.glVertex2d;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.TimerTask;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Stencil extends CaninoGameEngine3D {
	// Initial square position and size
	float x = 0.0f;
	float y = 0.0f;
	float rsize = 25;

	// Step size in x and y directions
	// (number of pixels to move each time)
	float xstep = 1.0f;
	float ystep = 1.0f;

	// Keep track of windows changing width and height
	float windowWidth;
	float windowHeight;

	@Override
	public void SetupRC() {
		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

		// Set drawing color to green
		glColor3f(0.0f, 1.0f, 0.0f);

		// Set color shading model to flat
		glShadeModel(GL_FLAT);

		// Clock wise wound polygons are front facing, this is reversed
		// because we are using triangle fans
		glFrontFace(GL_CW);	
		
		addTaskScheduleAtFixedRate(new Timer(), 0, 33);

	}

	@Override
	public void RenderScene() {
	    double dRadius = 0.1; // Initial radius of spiral
	    double dAngle;        // Looping variable
	            
	    // Clear blue window
	    glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
	        
	    // Use 0 for clear stencil, enable stencil test
	    glClearStencil(0);
	    glEnable(GL_STENCIL_TEST);

	    // Clear color and stencil buffer
	    glClear(GL_COLOR_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	                
	    // All drawing commands fail the stencil test, and are not
	    // drawn, but increment the value in the stencil buffer. 
	    glStencilFunc(GL_NEVER, 0x0, 0x0);
	    glStencilOp(GL_INCR, GL_INCR, GL_INCR);

	    // Spiral pattern will create stencil pattern
	    // Draw the spiral pattern with white lines. We 
	    // make the lines  white to demonstrate that the 
	    // stencil function prevents them from being drawn
	    glColor3f(1.0f, 1.0f, 1.0f);
	    glBegin(GL_LINE_STRIP);
	        for(dAngle = 0; dAngle < 400.0; dAngle += 0.1)
	        {
	            glVertex2d(dRadius * Math.cos(dAngle), dRadius * Math.sin(dAngle));
	            dRadius *= 1.002;
	        }
	    glEnd();
	            
	    // Now, allow drawing, except where the stencil pattern is 0x1
	    // and do not make any further changes to the stencil buffer
	    glStencilFunc(GL_NOTEQUAL, 0x1, 0x1);
	    glStencilOp(GL_KEEP, GL_KEEP, GL_KEEP);
	        
	    // Now draw red bouncing square
	    // (x and y) are modified by a timer function
	    glColor3f(1.0f, 0.0f, 0.0f);
	    glRectf(x, y, x + rsize, y - rsize);
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
	    {
	        windowWidth = 100;
	        windowHeight = 100 / aspectRatio;
	        glOrtho (-100.0, 100.0, -windowHeight, windowHeight, 1.0, -1.0);
	    }
	    else 
	    {
	        windowWidth = 100 * aspectRatio;
	        windowHeight = 100;
	        glOrtho (-windowWidth, windowWidth, -100.0, 100.0, 1.0, -1.0);
	    }

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	private class Timer extends TimerTask {

		@Override
		public void run() {
		    // Reverse direction when you reach left or right edge
		    if(x > windowWidth-rsize || x < -windowWidth)
		        xstep = -xstep;

		    // Reverse direction when you reach top or bottom edge
		    if(y > windowHeight || y < -windowHeight + rsize)
		        ystep = -ystep;


		    // Check bounds. This is in case the window is made
		    // smaller while the rectangle is bouncing and the 
			// rectangle suddenly finds itself outside the new
		    // clipping volume
		    if(x > windowWidth-rsize)
		        x = windowWidth-rsize-1;

		    if(y > windowHeight)
		        y = windowHeight-1; 

			// Actually move the square
		    x += xstep;
		    y += ystep;
		}
		
	}

	public static void main(String[] argv) {
		Stencil game = new Stencil();
		game.start();
	}

}
