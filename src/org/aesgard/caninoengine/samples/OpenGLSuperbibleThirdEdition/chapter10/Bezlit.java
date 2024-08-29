package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter10;

import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glMap2f;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertex3fv;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_AUTO_NORMAL;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FILL;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MAP2_VERTEX_3;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEvalMesh2;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMapGrid2f;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Bezlit extends CaninoGameEngine3D {
	private boolean menuChanged = false;
	private int menuItem = 0;

	// The number of control points for this curve
	private int nNumPoints = 3;

	private float ctrlPoints[][][] = {
			{ { -4.0f, 0.0f, 4.0f }, { -2.0f, 4.0f, 4.0f },
					{ 4.0f, 0.0f, 4.0f } },

			{ { -4.0f, 0.0f, 0.0f }, { -2.0f, 4.0f, 0.0f },
					{ 4.0f, 0.0f, 0.0f } },

			{ { -4.0f, 0.0f, -4.0f }, { -2.0f, 4.0f, -4.0f },
					{ 4.0f, 0.0f, -4.0f } } };

	public Bezlit() {
		// TODO Auto-generated constructor stub
	}

	// This function is used to superimpose the control points over the curve
	private void DrawPoints() {
		int i, j; // Counting variables

		// Set point size larger to make more visible
		glPointSize(5.0f);

		// Loop through all control points for this example
		glBegin(GL_POINTS);
		for (i = 0; i < nNumPoints; i++)
			for (j = 0; j < 3; j++)
				glVertex3fv(ctrlPoints[i][j]);
		glEnd();
	}

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Save the modelview matrix stack
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();

		// Rotate the mesh around to make it easier to see
		glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
		glRotatef(60.0f, 1.0f, 0.0f, 0.0f);

		// Sets up the bezier
		// This actually only needs to be called once and could go in
		// the setup function
		glMap2f(GL_MAP2_VERTEX_3, // Type of data generated
				0.0f, // Lower u range
				10.0f, // Upper u range
				3, // Distance between points in the data
				3, // Dimension in u direction (order)
				0.0f, // Lover v range
				10.0f, // Upper v range
				9, // Distance between points in the data
				3, // Dimension in v direction (order)
				ctrlPoints); // array of control points

		// Enable the evaluator
		glEnable(GL_MAP2_VERTEX_3);

		// Use higher level functions to map to a grid, then evaluate the
		// entire thing.

		// Map a grid of 10 points from 0 to 10
		glMapGrid2f(10, 0.0f, 10.0f, 10, 0.0f, 10.0f);

		// Evaluate the grid, using lines
		glEvalMesh2(GL_FILL, 0, 10, 0, 10);

		// Restore the modelview matrix
		glPopMatrix();
		
		DrawPoints();
		
		if (menuChanged) {
			processMenu(menuItem);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
		// Light values and coordinates
		float ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };
		float diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };
		float lightPos[] = { 20.0f, 0.0f, 0.0f, 0.0f };

		// Clear Window to white
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		glEnable(GL_DEPTH_TEST); // Hidden surface removal

		// Enable lighting
		glEnable(GL_LIGHTING);

		// Setup light 0
		glLightfv(GL_LIGHT0, GL_AMBIENT, ambientLight);
		glLightfv(GL_LIGHT0, GL_DIFFUSE, diffuseLight);

		// Position and turn on the light
		glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
		glEnable(GL_LIGHT0);

		// Enable color tracking
		glEnable(GL_COLOR_MATERIAL);

		// Set Material properties to follow glColor values
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

		// Automatically generate normals for evaluated surfaces
		glEnable(GL_AUTO_NORMAL);

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

		glOrtho(-10.0f, 10.0f, -10.0f, 10.0f, -10.0f, 10.0f);

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
		Bezlit game = new Bezlit();

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
