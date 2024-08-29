package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Triangle extends CaninoGameEngine3D {
	private static final String TOGGLE_OUTLINE_BACK = "Toggle outline back";

	private static final String TOGGLE_CULL_BACKFACE = "Toggle cull backface";

	private static final String TOGGLE_DEPTH_TEST = "Toggle depth test";

	// Flags for effects
	// Flags for effects
	boolean iCull;
	boolean iOutline;
	boolean iDepth;

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
		
	}

	@Override
	public void RenderScene() {
		float x,y,angle;  // Storage for coordinates and angles
		int iPivot = 1;		// Used to flag alternating colors

		// Clear the window and the depth buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Turn culling on if flag is set
		if(iCull)
			glEnable(GL_CULL_FACE);
		else
			glDisable(GL_CULL_FACE);

		// Enable depth testing if flag is set
		if(iDepth)
			glEnable(GL_DEPTH_TEST);
		else
			glDisable(GL_DEPTH_TEST);

		// Draw back side as a polygon only, if flag is set
		if(iOutline)
			glPolygonMode(GL_BACK,GL_LINE);
		else
			glPolygonMode(GL_BACK,GL_FILL);


		// Save matrix state and do the rotation
		glPushMatrix();
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);


		// Begin a triangle fan
		glBegin(GL_TRIANGLE_FAN);

		// Pinnacle of cone is shared vertex for fan, moved up Z axis
		// to produce a cone instead of a circle
		glVertex3f(0.0f, 0.0f, 75.0f);

		// Loop around in a circle and specify even points along the circle
		// as the vertices of the triangle fan
		for(angle = 0.0f; angle < (2.0f*GL_PI); angle += (GL_PI/8.0f))
		{
			// Calculate x and y position of the next vertex
			x = 50.0f*(float)Math.sin(angle);
			y = 50.0f*(float)Math.cos(angle);

			// Alternate color between red and green
			if((iPivot %2) == 0)
				glColor3f(0.0f, 1.0f, 0.0f);
			else
				glColor3f(1.0f, 0.0f, 0.0f);

			// Increment pivot to change color next time
			iPivot++;

			// Specify the next vertex for the triangle fan
			glVertex2f(x, y);
		}

		// Done drawing fan for cone
		glEnd();


		// Begin a new triangle fan to cover the bottom
		glBegin(GL_TRIANGLE_FAN);

		// Center of fan is at the origin
		glVertex2f(0.0f, 0.0f);
		for(angle = 0.0f; angle < (2.0f*GL_PI); angle += (GL_PI/8.0f))
		{
			// Calculate x and y position of the next vertex
			x = 50.0f*(float)Math.sin(angle);
			y = 50.0f*(float)Math.cos(angle);

			// Alternate color between red and green
			if((iPivot %2) == 0)
				glColor3f(0.0f, 1.0f, 0.0f);
			else
				glColor3f(1.0f, 0.0f, 0.0f);

			// Increment pivot to change color next time
			iPivot++;

			// Specify the next vertex for the triangle fan
			glVertex2f(x, y);
		}

		// Done drawing the fan that covers the bottom
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

		// Reset projection matrix stack
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Establish clipping volume (left, right, bottom, top, near, far)
		if (w <= h) 
			glOrtho (-nRange, nRange, -nRange*h/w, nRange*h/w, -nRange, nRange);
		else 
			glOrtho (-nRange*w/h, nRange*w/h, -nRange, nRange, -nRange, nRange);

		// Reset Model view matrix stack
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if (action.equalsIgnoreCase(TOGGLE_DEPTH_TEST)) 
				iDepth = !iDepth;

			if (action.equalsIgnoreCase(TOGGLE_CULL_BACKFACE)) 
				iCull = !iCull;

			if (action.equalsIgnoreCase(TOGGLE_OUTLINE_BACK)) 
				iOutline = !iOutline;
		}
	}
	
	public static void main(String[] argv) {
		Triangle game = new Triangle();

		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		JPopupMenu popup = new JPopupMenu();
		JMenuItem menuItem = new JMenuItem(TOGGLE_DEPTH_TEST);
		menuItem.addActionListener(listener);
		popup.add(menuItem);
		menuItem = new JMenuItem(TOGGLE_CULL_BACKFACE);
		menuItem.addActionListener(listener);
		popup.add(menuItem);
		menuItem = new JMenuItem(TOGGLE_OUTLINE_BACK);
		menuItem.addActionListener(listener);
		popup.add(menuItem);
		game.addPopup(popup);

		game.start();
	}

}
