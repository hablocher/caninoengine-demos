package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter10;

import static org.aesgard.caninoengine.glutil.APITranslations.glVertex3dv;
import static org.aesgard.caninoengine.glutil.APITranslations.gluDeleteTess;
import static org.aesgard.caninoengine.glutil.APITranslations.gluTessBeginContour;
import static org.aesgard.caninoengine.glutil.APITranslations.gluTessBeginPolygon;
import static org.aesgard.caninoengine.glutil.APITranslations.gluTessCallback;
import static org.aesgard.caninoengine.glutil.APITranslations.gluTessEndContour;
import static org.aesgard.caninoengine.glutil.APITranslations.gluTessEndPolygon;
import static org.aesgard.caninoengine.glutil.APITranslations.gluTessProperty;
import static org.aesgard.caninoengine.glutil.APITranslations.gluTessVertex;
import static org.aesgard.caninoengine.glutil.TessallationType.glBegin;
import static org.aesgard.caninoengine.glutil.TessallationType.glEnd;
import static org.aesgard.caninoengine.glutil.TessallationType.glVertex3dv;
import static org.aesgard.caninoengine.glutil.TessallationType.tessError;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_LOOP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.GLU_TESS_BEGIN;
import static org.lwjgl.util.glu.GLU.GLU_TESS_END;
import static org.lwjgl.util.glu.GLU.GLU_TESS_ERROR;
import static org.lwjgl.util.glu.GLU.GLU_TESS_VERTEX;
import static org.lwjgl.util.glu.GLU.GLU_TESS_WINDING_ODD;
import static org.lwjgl.util.glu.GLU.GLU_TESS_WINDING_RULE;
import static org.lwjgl.util.glu.GLU.gluNewTess;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.glutil.VertexData;
import org.lwjgl.util.glu.GLUtessellator;

public class Florida extends CaninoGameEngine3D {

	private static final String COMPLEX_POLYGON = "Complex Polygon";
	private static final String CONCAVE_POLYGON = "Concave Polygon";
	private static final String LINE_LOOPS = "Line Loops";

	private boolean menuChanged = false;

	// ///////////////////////////////
	// Coast Line Data
	private int COAST_POINTS = 24;
	private double vCoast[][] = { { -70.0, 30.0, 0.0 }, { -50.0, 30.0, 0.0 },
			{ -50.0, 27.0, 0.0 }, { -5.0, 27.0, 0.0 }, { 0.0, 20.0, 0.0 },
			{ 8.0, 10.0, 0.0 }, { 12.0, 5.0, 0.0 }, { 10.0, 0.0, 0.0 },
			{ 15.0, -10.0, 0.0 }, { 20.0, -20.0, 0.0 }, { 20.0, -35.0, 0.0 },
			{ 10.0, -40.0, 0.0 }, { 0.0, -30.0, 0.0 }, { -5.0, -20.0, 0.0 },
			{ -12.0, -10.0, 0.0 }, { -13.0, -5.0, 0.0 }, { -12.0, 5.0, 0.0 },
			{ -20.0, 10.0, 0.0 }, { -30.0, 20.0, 0.0 }, { -40.0, 15.0, 0.0 },
			{ -50.0, 15.0, 0.0 }, { -55.0, 20.0, 0.0 }, { -60.0, 25.0, 0.0 },
			{ -70.0, 25.0, 0.0 } };

	// Lake Okeechobee
	private int LAKE_POINTS = 4;
	private double vLake[][] = { { 10.0, -20.0, 0.0 }, { 15.0, -25.0, 0.0 },
			{ 10.0, -30.0, 0.0 }, { 5.0, -25.0, 0.0 } };

	// Which Drawing Method
	private final int DRAW_LOOPS = 0;
	private final int DRAW_CONCAVE = 1;
	private final int DRAW_COMPLEX = 2;

	private int iMethod = DRAW_LOOPS; // Default, draw line loops

	public Florida() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void RenderScene() {
		int i; // Loop variable

		// Clear the window
		glClear(GL_COLOR_BUFFER_BIT);

		switch (iMethod) {
		case DRAW_LOOPS: // Draw line loops
		{
			glColor3f(0.0f, 0.0f, 0.0f); // Just black outline

			// Line loop with coastline shape
			glBegin(GL_LINE_LOOP);
			for (i = 0; i < COAST_POINTS; i++)
				glVertex3dv(vCoast[i]);
			glEnd();

			// Line loop with shape of interior lake
			glBegin(GL_LINE_LOOP);
			for (i = 0; i < LAKE_POINTS; i++)
				glVertex3dv(vLake[i]);
			glEnd();
		}
			break;

		case DRAW_CONCAVE: // Tesselate concave polygon
		{
			// Tesselator object
			GLUtessellator pTess;

			// Green polygon
			glColor3f(0.0f, 1.0f, 0.0f);

			// Create the tesselator object
			pTess = gluNewTess();

			// Set callback functions
			// Just call glBegin at begining of triangle batch
			gluTessCallback(pTess, GLU_TESS_BEGIN, glBegin);

			// Just call glEnd at end of triangle batch
			gluTessCallback(pTess, GLU_TESS_END, glEnd);

			// Just call glVertex3dv for each vertex
			gluTessCallback(pTess, GLU_TESS_VERTEX, glVertex3dv);

			// Register error callback
			gluTessCallback(pTess, GLU_TESS_ERROR, tessError);

			// Begin the polygon
			gluTessBeginPolygon(pTess, null);

			// Gegin the one and only contour
			gluTessBeginContour(pTess);

			// Feed in the list of vertices
			for (i = 0; i < COAST_POINTS; i++)
				gluTessVertex(pTess, vCoast[i], new VertexData(vCoast[i])); // Can't
																			// be
																			// NULL

			// Close contour and polygon
			gluTessEndContour(pTess);
			gluTessEndPolygon(pTess);

			// All done with tesselator object
			gluDeleteTess(pTess);
		}
			break;

		case DRAW_COMPLEX: // Tesselate, but with hole cut out
		{
			// Tesselator object
			GLUtessellator pTess;

			// Green polygon
			glColor3f(0.0f, 1.0f, 0.0f);

			// Create the tesselator object
			pTess = gluNewTess();

			// Set callback functions
			// Just call glBegin at begining of triangle batch
			gluTessCallback(pTess, GLU_TESS_BEGIN, glBegin);

			// Just call glEnd at end of triangle batch
			gluTessCallback(pTess, GLU_TESS_END, glEnd);

			// Just call glVertex3dv for each vertex
			gluTessCallback(pTess, GLU_TESS_VERTEX, glVertex3dv);

			// Register error callback
			gluTessCallback(pTess, GLU_TESS_ERROR, tessError);

			// How to count filled and open areas
			gluTessProperty(pTess, GLU_TESS_WINDING_RULE, GLU_TESS_WINDING_ODD);

			// Begin the polygon
			gluTessBeginPolygon(pTess, null); // No user data

			// First contour, outline of state
			gluTessBeginContour(pTess);
			for (i = 0; i < COAST_POINTS; i++)
				gluTessVertex(pTess, vCoast[i], new VertexData(vCoast[i]));
			gluTessEndContour(pTess);

			// Second contour, outline of lake
			gluTessBeginContour(pTess);
			for (i = 0; i < LAKE_POINTS; i++)
				gluTessVertex(pTess, vLake[i], new VertexData(vLake[i]));
			gluTessEndContour(pTess);

			// All done with polygon
			gluTessEndPolygon(pTess);

			// No longer need tessellator object
			gluDeleteTess(pTess);
		}
			break;
		}

		if (menuChanged) {
			processMenu(iMethod);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
		// Blue background
		glClearColor(0.0f, 0.0f, 1.0f, 1.0f);

	}

	@Override
	public void ChangeSize(int w, int h) {
		// Prevent a divide by zero
		if (h == 0)
			h = 1;

		// Set Viewport to window dimensions
		glViewport(0, 0, w, h);

		// Reset projection matrix stack
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Establish clipping volume (left, right, bottom, top, near, far)
		gluOrtho2D(-80, 35, -50, 50);

		// Reset Model view matrix stack
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

	}

	// /////////////////////////////////////////////////////////////////////////////
	// Reset flags as appropriate in response to menu selections
	private void processMenu(int value) {

		iMethod = value;

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
			int iChoosed = iMethod;
			if (action.equalsIgnoreCase(LINE_LOOPS)) {
				iChoosed = 0;
			}
			if (action.equalsIgnoreCase(CONCAVE_POLYGON)) {
				iChoosed = 1;
			}
			if (action.equalsIgnoreCase(COMPLEX_POLYGON)) {
				iChoosed = 2;
			}
			if (iChoosed != iMethod) {
				iMethod = iChoosed;
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
		Florida game = new Florida();

		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		JMenuItem menuItem;
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem(LINE_LOOPS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(CONCAVE_POLYGON);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(COMPLEX_POLYGON);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		game.addPopup(menu);

		game.start(800, 600);
	}

}
