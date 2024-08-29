package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEdgeFlag;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Star extends CaninoGameEngine3D {
	private static final String OFF = "Off";

	private static final String ON = "On";

	private static final String POINTS = "Points";

	private static final String OUTLINE = "Outline";

	private static final String SOLID = "Solid";

	// Flags for effects
	int MODE_SOLID = 0;
	int MODE_LINE  = 1;
	int MODE_POINT = 2;

	int iMode = MODE_SOLID;
	boolean bEdgeFlag = true;

	@Override
	public void SetupRC() {
		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

		// Set drawing color to green
		glColor3f(0.0f, 1.0f, 0.0f);
	}

	@Override
	public void RenderScene() {
		// Clear the window
		glClear(GL_COLOR_BUFFER_BIT);

		// Draw back side as a polygon only, if flag is set
		if(iMode == MODE_LINE)
			glPolygonMode(GL_FRONT_AND_BACK,GL_LINE);

		if(iMode == MODE_POINT)
			glPolygonMode(GL_FRONT_AND_BACK,GL_POINT);

		if(iMode == MODE_SOLID)
			glPolygonMode(GL_FRONT_AND_BACK,GL_FILL);

		// Save matrix state and do the rotation
		glPushMatrix();
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);

		// Begin the triangles
		glBegin(GL_TRIANGLES);

			glEdgeFlag(bEdgeFlag);
			glVertex2f(-20.0f, 0.0f);
			glEdgeFlag(true);
			glVertex2f(20.0f, 0.0f);
			glVertex2f(0.0f, 40.0f);

			glVertex2f(-20.0f,0.0f);
			glVertex2f(-60.0f,-20.0f);
			glEdgeFlag(bEdgeFlag);
			glVertex2f(-20.0f,-40.0f);
			glEdgeFlag(true);

			glVertex2f(-20.0f,-40.0f);
			glVertex2f(0.0f, -80.0f);
			glEdgeFlag(bEdgeFlag);
			glVertex2f(20.0f, -40.0f);		
			glEdgeFlag(true);

			glVertex2f(20.0f, -40.0f);		
			glVertex2f(60.0f, -20.0f);
			glEdgeFlag(bEdgeFlag);
			glVertex2f(20.0f, 0.0f);
			glEdgeFlag(true);

			// Center square as two triangles
			glEdgeFlag(bEdgeFlag);
			glVertex2f(-20.0f, 0.0f);
			glVertex2f(-20.0f,-40.0f);
			glVertex2f(20.0f, 0.0f);
			
			glVertex2f(-20.0f,-40.0f);
			glVertex2f(20.0f, -40.0f);
			glVertex2f(20.0f, 0.0f);
			glEdgeFlag(true);
		
		// Done drawing Triangles
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
			if (action.equalsIgnoreCase(SOLID)) 
				iMode = MODE_SOLID;

			if (action.equalsIgnoreCase(OUTLINE)) 
				iMode = MODE_LINE;

			if (action.equalsIgnoreCase(POINTS)) 
				iMode = MODE_POINT;
			
			if (action.equalsIgnoreCase(ON)) 
				bEdgeFlag = true;
			
			if (action.equalsIgnoreCase(OFF)) 
				bEdgeFlag = false;
		}
	}

	public static void main(String[] argv) {
		Star game = new Star();
		
		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();

		JMenuItem menuItem;
		
		JMenu sub1 = new JMenu("Mode");
		menuItem = new JMenuItem(SOLID);
		menuItem.addActionListener(listener);
		sub1.add(menuItem);
		menuItem = new JMenuItem(OUTLINE);
		menuItem.addActionListener(listener);
		sub1.add(menuItem);
		menuItem = new JMenuItem(POINTS);
		menuItem.addActionListener(listener);
		sub1.add(menuItem);

		JMenu sub2 = new JMenu("Edges");
		menuItem = new JMenuItem(ON);
		menuItem.addActionListener(listener);
		sub2.add(menuItem);
		menuItem = new JMenuItem(OFF);
		menuItem.addActionListener(listener);
		sub2.add(menuItem);

		JPopupMenu mainMenu = new JPopupMenu();
		mainMenu.add(sub1);
		mainMenu.add(sub2);

		game.addPopup(mainMenu);
		
		game.start();
	}

}
