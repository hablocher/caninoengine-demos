package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.*;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Single extends CaninoGameEngine3D {
    double dRadius = 0.1;
    double dAngle = 0.0;

	@Override
	public void SetupRC() {

	}

	@Override
	public void RenderScene() {
        
        // Clear blue window
        glClearColor(0.0f, 0.0f, 1.0f, 0.0f);
        
        if(dAngle == 0.0)
            glClear(GL_COLOR_BUFFER_BIT);
                
        glBegin(GL_POINTS);
            glVertex2d(dRadius * Math.cos(dAngle), dRadius * Math.sin(dAngle));
        glEnd();
        
        dRadius *= 1.01;
        dAngle += 0.1;
        
        if(dAngle > 30.0)
        {
            dRadius = 0.1;
            dAngle = 0.0;
        }
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
		//gluOrtho2D(-4.0, 4.0, -3.0, 3.0);
		glOrtho(-4.0, 4.0, -3.0, 3.0, -1.0f, 1.0f);
		
		// Modelview matrix reset
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public static void main(String[] argv) {
		Single game = new Single();
		game.start();
	}

}
