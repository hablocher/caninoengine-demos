package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter10;

import static org.aesgard.caninoengine.glutil.APITranslations.glMap1f;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertex2fv;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MAP1_VERTEX_3;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEvalCoord1f;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Bezier extends CaninoGameEngine3D {

	private boolean menuChanged = false;
	private int menuItem = 0;

	// The number of control points for this curve
	private int nNumPoints = 4;

	private float ctrlPoints[][] = { { -4.0f, 0.0f, 0.0f }, // End Point
			{ -6.0f, 4.0f, 0.0f }, // Control Point
			{ 6.0f, -4.0f, 0.0f }, // Control Point
			{ 4.0f, 0.0f, 0.0f } }; // End Point

	public Bezier() {
		// TODO Auto-generated constructor stub
	}

	// This function is used to superimpose the control points over the curve
	private void DrawPoints() {
		int i; // Counting variable

		// Set point size larger to make more visible
		glPointSize(5.0f);

		// Loop through all control points for this example
		glBegin(GL_POINTS);
		for (i = 0; i < nNumPoints; i++)
			glVertex2fv(ctrlPoints[i]);
		glEnd();
	}

	@Override
	public void RenderScene() {
		int i;

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

		// Sets up the bezier
		// This actually only needs to be called once and could go in
		// the setup function
		glMap1f(GL_MAP1_VERTEX_3, // Type of data generated
				0.0f, // Lower u range
				100.0f, // Upper u range
				3, // Distance between points in the data
				nNumPoints, // number of control points
				ctrlPoints); // array of control points

		// Enable the evaluator
		glEnable(GL_MAP1_VERTEX_3);

		// Use a line strip to "connect-the-dots"
		glBegin(GL_LINE_STRIP);
		for (i = 0; i <= 100; i++) {
			// Evaluate the curve at this point
			glEvalCoord1f((float) i);
		}
		glEnd();

		// Use higher level functions to map to a grid, then evaluate the
		// entire thing.
		// Put these two functions in to replace above loop

		// Map a grid of 100 points from 0 to 100
		// glMapGrid1d(100,0.0,100.0);

		// Evaluate the grid, using lines
		// glEvalMesh1(GL_LINE,0,100);

		// Draw the Control Points
		DrawPoints();

		if (menuChanged) {
			processMenu(menuItem);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
		// Clear Window to white
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		// Draw in Blue
		glColor3f(0.0f, 0.0f, 1.0f);
	}

	@Override
	public void ChangeSize(int w, int h) {
		// Prevent a divide by zero
		if (h == 0)
			h = 1;

		// Set Viewport to window dimensions
		glViewport(0, 0, w, h);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		gluOrtho2D(-10.0f, 10.0f, -10.0f, 10.0f);

		// Modelview matrix reset
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Reset flags as appropriate in response to menu selections
	private void processMenu(int value) {

		menuItem = value;

		switch (value) {
		case 1:
			break;

		case 2:
			break;

		case 3:
		default:
			break;
		}
	}

	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			int iChoosed = menuItem;
			if (action.equalsIgnoreCase("item")) {
				iChoosed = 1;
			}
			if (iChoosed != menuItem) {
				menuItem = iChoosed;
				menuChanged = true;
			}
		}
	}

	protected void SpecialKeys() {
		super.SpecialKeys();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Bezier game = new Bezier();

		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		JMenuItem menuItem;
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem("item");
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		game.addPopup(menu);

		game.start(800, 600);
	}

}
