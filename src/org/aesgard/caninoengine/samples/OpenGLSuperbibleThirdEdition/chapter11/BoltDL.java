package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter11;

import static org.aesgard.caninoengine.glutil.APITranslations.cos;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glMaterialfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glNormal3fv;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertex3fv;
import static org.aesgard.caninoengine.glutil.APITranslations.sin;
import static org.aesgard.caninoengine.glutil.VectorMath.gltGetNormalVector;
import static org.aesgard.caninoengine.glutil.VectorMath.gltNormalizeVector;
import static org.aesgard.caninoengine.util.Constants.GLT_PI;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_COMPILE;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_QUAD_STRIP;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glCallList;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glEndList;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glGenLists;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNewList;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class BoltDL extends CaninoGameEngine3D {

	private boolean menuChanged = false;
	private int menuItem = 0;

	// Display list identifiers
	private int headList, shaftList, threadList;

	public BoltDL() {
		// TODO Auto-generated constructor stub
	}

	public BoltDL(String title) {
		super(title);
	}

	// ////////////////////////////////////////////////////////////////////////////
	// Spiraling thread
	private void RenderThread() {
		float x, y, z, angle; // Calculate coordinates and step angle
		float height = 75.0f; // Height of the threading
		float diameter = 20.0f; // Diameter of the threading
		float[] vNormal = new float[3];
		float[][] vCorners = new float[4][3];// Storeage for calculate normal
												// and corners
		float step = (3.1415f / 32.0f); // one revolution
		float revolutions = 7.0f; // How many time around the shaft
		float threadWidth = 2.0f; // How wide is the thread
		float threadThick = 3.0f; // How thick is the thread
		float zstep = .125f; // How much does the thread move up
								// the Z axis each time a new segment
								// is drawn.

		// Set material color for head of screw
		glColor3f(0.0f, 0.0f, 0.4f);

		z = -height / 2.0f + 2.0f; // Starting spot almost to the end

		// Go around and draw the sides until finished spinning up
		for (angle = 0.0f; angle < GLT_PI * 2.0f * revolutions; angle += step) {
			// Calculate x and y position of the next vertex
			x = diameter * (float) sin(angle);
			y = diameter * (float) cos(angle);

			// Store the next vertex next to the shaft
			vCorners[0][0] = x;
			vCorners[0][1] = y;
			vCorners[0][2] = z;

			// Calculate the position away from the shaft
			x = (diameter + threadWidth) * (float) sin(angle);
			y = (diameter + threadWidth) * (float) cos(angle);

			vCorners[1][0] = x;
			vCorners[1][1] = y;
			vCorners[1][2] = z;

			// Calculate the next position away from the shaft
			x = (diameter + threadWidth) * (float) sin(angle + step);
			y = (diameter + threadWidth) * (float) cos(angle + step);

			vCorners[2][0] = x;
			vCorners[2][1] = y;
			vCorners[2][2] = z + zstep;

			// Calculate the next position along the shaft
			x = (diameter) * (float) sin(angle + step);
			y = (diameter) * (float) cos(angle + step);

			vCorners[3][0] = x;
			vCorners[3][1] = y;
			vCorners[3][2] = z + zstep;

			// We'll be using triangels, so make
			// counter clock-wise polygons face out
			glFrontFace(GL_CCW);
			glBegin(GL_TRIANGLES); // Start the top section of thread

			// Calculate the normal for this segment
			gltGetNormalVector(vCorners[0], vCorners[1], vCorners[2], vNormal);
			glNormal3fv(vNormal);

			// Draw two triangles to cover area
			glVertex3fv(vCorners[0]);
			glVertex3fv(vCorners[1]);
			glVertex3fv(vCorners[2]);

			glVertex3fv(vCorners[2]);
			glVertex3fv(vCorners[3]);
			glVertex3fv(vCorners[0]);
			glEnd();

			// Move the edge along the shaft slightly up the z axis
			// to represent the bottom of the thread
			vCorners[0][2] += threadThick;
			vCorners[3][2] += threadThick;

			// Recalculate the normal since points have changed, this
			// time it points in the opposite direction, so reverse it
			gltGetNormalVector(vCorners[0], vCorners[1], vCorners[2], vNormal);
			vNormal[0] = -vNormal[0];
			vNormal[1] = -vNormal[1];
			vNormal[2] = -vNormal[2];

			// Switch to clock-wise facing out for underside of the
			// thread.
			glFrontFace(GL_CW);

			// Draw the two triangles
			glBegin(GL_TRIANGLES);
			glNormal3fv(vNormal);

			glVertex3fv(vCorners[0]);
			glVertex3fv(vCorners[1]);
			glVertex3fv(vCorners[2]);

			glVertex3fv(vCorners[2]);
			glVertex3fv(vCorners[3]);
			glVertex3fv(vCorners[0]);
			glEnd();

			// Creep up the Z axis
			z += zstep;
		}
	}

	// //////////////////////////////////////////////////////////////////////
	// Creates the shaft of the bolt as a cylinder with one end
	// closed.
	private void RenderShaft() {
		float x, z, angle; // Used to calculate cylinder wall
		float height = 75.0f; // Height of the cylinder
		float diameter = 20.0f; // Diameter of the cylinder
		float[] vNormal = new float[3];
		float[][] vCorners = new float[2][3]; // Storeage for vertex
												// calculations
		float step = (3.1415f / 50.0f); // Approximate the cylinder wall with
										// 100 flat segments.

		// Set material color for head of screw
		glColor3f(0.0f, 0.0f, 0.7f);

		// First assemble the wall as 100 quadrilaterals formed by
		// placing adjoining Quads together
		glFrontFace(GL_CCW);
		glBegin(GL_QUAD_STRIP);

		// Go around and draw the sides
		for (angle = (2.0f * 3.1415f); angle > 0.0f; angle -= step) {
			// Calculate x and y position of the first vertex
			x = diameter * (float) sin(angle);
			z = diameter * (float) cos(angle);

			// Get the coordinate for this point and extrude the
			// length of the cylinder.
			vCorners[0][0] = x;
			vCorners[0][1] = -height / 2.0f;
			vCorners[0][2] = z;

			vCorners[1][0] = x;
			vCorners[1][1] = height / 2.0f;
			vCorners[1][2] = z;

			// Instead of using real normal to actual flat section
			// Use what the normal would be if the surface was really
			// curved. Since the cylinder goes up the Y axis, the normal
			// points from the Y axis out directly through each vertex.
			// Therefore we can use the vertex as the normal, as long as
			// we reduce it to unit length first and assume the y component
			// to be zero
			vNormal[0] = vCorners[1][0];
			vNormal[1] = 0.0f;
			vNormal[2] = vCorners[1][2];

			// Reduce to length of one and specify for this point
			gltNormalizeVector(vNormal);
			glNormal3fv(vNormal);
			glVertex3fv(vCorners[0]);
			glVertex3fv(vCorners[1]);
		}

		// Make sure there are no gaps by extending last quad to
		// the original location
		glVertex3f(diameter * (float) sin(2.0f * 3.1415f), -height / 2.0f,
				diameter * (float) cos(2.0f * 3.1415f));

		glVertex3f(diameter * (float) sin(2.0f * 3.1415f), height / 2.0f,
				diameter * (float) cos(2.0f * 3.1415f));

		glEnd(); // Done with cylinder sides

		// Begin a new triangle fan to cover the bottom
		glBegin(GL_TRIANGLE_FAN);
		// Normal points down the Y axis
		glNormal3f(0.0f, -1.0f, 0.0f);

		// Center of fan is at the origin
		glVertex3f(0.0f, -height / 2.0f, 0.0f);

		// Spin around matching step size of cylinder wall
		for (angle = (2.0f * 3.1415f); angle > 0.0f; angle -= step) {
			// Calculate x and y position of the next vertex
			x = diameter * (float) sin(angle);
			z = diameter * (float) cos(angle);

			// Specify the next vertex for the triangle fan
			glVertex3f(x, -height / 2.0f, z);
		}

		// Be sure loop is closed by specifiying initial vertex
		// on arc as the last too
		glVertex3f(diameter * (float) sin(2.0f * 3.1415f), -height / 2.0f,
				diameter * (float) cos(2.0f * 3.1415f));
		glEnd();
	}

	// /////////////////////////////////////////////////////////////////////////
	// Creates the head of the bolt
	private void RenderHead() {
		float x, y, angle; // Calculated positions
		float height = 25.0f; // Thickness of the head
		float diameter = 30.0f; // Diameter of the head
		float[] vNormal = new float[3];
		float[][] vCorners = new float[4][3]; // Storeage of vertices and
												// normals
		float step = (3.1415f / 3.0f); // step = 1/6th of a circle = hexagon

		// Set material color for head of bolt
		glColor3f(0.0f, 0.0f, 0.7f);

		// Begin a new triangle fan to cover the top
		glFrontFace(GL_CCW);
		glBegin(GL_TRIANGLE_FAN);

		// All the normals for the top of the bolt point straight up
		// the z axis.
		glNormal3f(0.0f, 0.0f, 1.0f);

		// Center of fan is at the origin
		glVertex3f(0.0f, 0.0f, height / 2.0f);

		// Divide the circle up into 6 sections and start dropping
		// points to specify the fan. We appear to be winding this
		// fan backwards. This has the effect of reversing the winding
		// of what would have been a CW wound primative. Avoiding a state
		// change with glFrontFace().

		// First and Last vertex closes the fan
		glVertex3f(0.0f, diameter, height / 2.0f);

		for (angle = (2.0f * 3.1415f) - step; angle >= 0; angle -= step) {
			// Calculate x and y position of the next vertex
			x = diameter * (float) sin(angle);
			y = diameter * (float) cos(angle);

			// Specify the next vertex for the triangle fan
			glVertex3f(x, y, height / 2.0f);
		}

		// Last vertex closes the fan
		glVertex3f(0.0f, diameter, height / 2.0f);

		// Done drawing the fan that covers the bottom
		glEnd();

		// Begin a new triangle fan to cover the bottom
		glBegin(GL_TRIANGLE_FAN);

		// Normal for bottom points straight down the negative z axis
		glNormal3f(0.0f, 0.0f, -1.0f);

		// Center of fan is at the origin
		glVertex3f(0.0f, 0.0f, -height / 2.0f);

		// Divide the circle up into 6 sections and start dropping
		// points to specify the fan
		for (angle = 0.0f; angle < (2.0f * 3.1415f); angle += step) {
			// Calculate x and y position of the next vertex
			x = diameter * (float) sin(angle);
			y = diameter * (float) cos(angle);

			// Specify the next vertex for the triangle fan
			glVertex3f(x, y, -height / 2.0f);
		}

		// Last vertex, used to close the fan
		glVertex3f(0.0f, diameter, -height / 2.0f);

		// Done drawing the fan that covers the bottom
		glEnd();

		// Build the sides out of triangles (two each). Each face
		// will consist of two triangles arranged to form a
		// quadralateral
		glBegin(GL_QUADS);

		// Go around and draw the sides
		for (angle = 0.0f; angle < (2.0f * 3.1415f); angle += step) {
			// Calculate x and y position of the next hex point
			x = diameter * (float) sin(angle);
			y = diameter * (float) cos(angle);

			// start at bottom of head
			vCorners[0][0] = x;
			vCorners[0][1] = y;
			vCorners[0][2] = -height / 2.0f;

			// extrude to top of head
			vCorners[1][0] = x;
			vCorners[1][1] = y;
			vCorners[1][2] = height / 2.0f;

			// Calculate the next hex point
			x = diameter * (float) sin(angle + step);
			y = diameter * (float) cos(angle + step);

			// Make sure we aren't done before proceeding
			if (angle + step < 3.1415 * 2.0) {
				// If we are done, just close the fan at a
				// known coordinate.
				vCorners[2][0] = x;
				vCorners[2][1] = y;
				vCorners[2][2] = height / 2.0f;

				vCorners[3][0] = x;
				vCorners[3][1] = y;
				vCorners[3][2] = -height / 2.0f;
			} else {
				// We aren't done, the points at the top and bottom
				// of the head.
				vCorners[2][0] = 0.0f;
				vCorners[2][1] = diameter;
				vCorners[2][2] = height / 2.0f;

				vCorners[3][0] = 0.0f;
				vCorners[3][1] = diameter;
				vCorners[3][2] = -height / 2.0f;
			}

			// The normal vectors for the entire face will
			// all point the same direction
			gltGetNormalVector(vCorners[0], vCorners[1], vCorners[2], vNormal);
			glNormal3fv(vNormal);

			// Specify each quad seperately to lie next
			// to each other.
			glVertex3fv(vCorners[0]);
			glVertex3fv(vCorners[1]);
			glVertex3fv(vCorners[2]);
			glVertex3fv(vCorners[3]);
		}

		glEnd();
	}

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Save the matrix state
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		
		// Rotate about x and y axes
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 0.0f, 1.0f);

		// Render just the Thread of the nut
		//RenderShaft();
		glCallList(shaftList);
	    
		glPushMatrix();
		glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
		//RenderThread();
		glCallList(threadList);
	    
		glTranslatef(0.0f,0.0f,45.0f);
		//RenderHead();
	    glCallList(headList);
		glPopMatrix();

		if (menuChanged) {
			processMenu(menuItem);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
		// Light values and coordinates
		float  ambientLight[] = {0.4f, 0.4f, 0.4f, 1.0f };
		float  diffuseLight[] = {0.7f, 0.7f, 0.7f, 1.0f };
		float  specular[] = { 0.9f, 0.9f, 0.9f, 1.0f};
		float	 lightPos[] = { -50.0f, 200.0f, 200.0f, 1.0f };
		float  specref[] =  { 0.6f, 0.6f, 0.6f, 1.0f };

		glEnable(GL_DEPTH_TEST);	// Hidden surface removal
		glEnable(GL_CULL_FACE);		// Do not calculate inside of solid object
		glFrontFace(GL_CCW);
		
		// Enable lighting
		glEnable(GL_LIGHTING);

		// Setup light 0
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT,ambientLight);
		glLightfv(GL_LIGHT0,GL_AMBIENT,ambientLight);
		glLightfv(GL_LIGHT0,GL_DIFFUSE,diffuseLight);
		glLightfv(GL_LIGHT0,GL_SPECULAR,specular);

		// Position and turn on the light
		glLightfv(GL_LIGHT0,GL_POSITION,lightPos);
		glEnable(GL_LIGHT0);

		// Enable color tracking
		glEnable(GL_COLOR_MATERIAL);
		
		// Set Material properties to follow glColor values
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

		// All materials hereafter have full specular reflectivity
		// with a moderate shine
		glMaterialfv(GL_FRONT, GL_SPECULAR,specref);
		glMateriali(GL_FRONT,GL_SHININESS,64);

		// Black background
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f );
	    
	    // Get Display list names
	    headList = glGenLists(3);
	    shaftList = headList + 1;
	    threadList = headList + 2;
	    
	    // Prebuild the display lists
	    glNewList(headList, GL_COMPILE);
	        RenderHead();
	    glEndList();
	    
	    glNewList(shaftList, GL_COMPILE);
	        RenderShaft();
	    glEndList();
	    
	    glNewList(threadList, GL_COMPILE);
	        RenderThread();
	    glEndList();
	}

	@Override
	public void ChangeSize(int w, int h) {
		float nRange = 100.0f;

		// Prevent a divide by zero
		if(h == 0)
			h = 1;

		// Set Viewport to window dimensions
	    glViewport(0, 0, w, h);

		// Reset coordinate system
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Establish clipping volume (left, right, bottom, top, near, far)
	    if (w <= h) 
			glOrtho (-nRange, nRange, -nRange*h/w, nRange*h/w, -nRange*2.0f, nRange*2.0f);
	    else 
			glOrtho (-nRange*w/h, nRange*w/h, -nRange, nRange, -nRange*2.0f, nRange*2.0f);

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
		BoltDL game = new BoltDL("Bolt Assembly Using Display Lists");

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
