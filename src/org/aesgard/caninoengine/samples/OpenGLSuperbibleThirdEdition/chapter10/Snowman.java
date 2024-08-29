package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter10;

import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.gluCylinder;
import static org.aesgard.caninoengine.glutil.APITranslations.gluDisk;
import static org.aesgard.caninoengine.glutil.APITranslations.gluNewQuadric;
import static org.aesgard.caninoengine.glutil.APITranslations.gluQuadricNormals;
import static org.aesgard.caninoengine.glutil.APITranslations.gluSphere;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
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
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.GLU_SMOOTH;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.glutil.GLUquadricObj;

public class Snowman extends CaninoGameEngine3D {

	private boolean menuChanged = false;
	private int menuItem = 0;

	public Snowman() {
		// TODO Auto-generated constructor stub
	}
	
	public Snowman(String title) {
		super(title);
	}

	@Override
	public void RenderScene() {
		   GLUquadricObj pObj;	// Quadric Object
		    
		    // Clear the window with current clearing color
		    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		    // Save the matrix state and do the rotations
		    glPushMatrix();
		        // Move object back and do in place rotation
		        glTranslatef(0.0f, -1.0f, -5.0f);
		        glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		        glRotatef(yRot, 0.0f, 1.0f, 0.0f);

		        // Draw something
		        pObj = gluNewQuadric();
		        gluQuadricNormals(pObj, GLU_SMOOTH);
		        
		        // Main Body
		        glPushMatrix();
		            glColor3f(1.0f, 1.0f, 1.0f);
		            gluSphere(pObj, .40f, 26, 13);  // Bottom
		        
		            glTranslatef(0.0f, .550f, 0.0f); // Mid section
		            gluSphere(pObj, .3f, 26, 13);
		        
		            glTranslatef(0.0f, 0.45f, 0.0f); // Head
		            gluSphere(pObj, 0.24f, 26, 13);
		        
		            // Eyes
		            glColor3f(0.0f, 0.0f, 0.0f);
		            glTranslatef(0.1f, 0.1f, 0.21f);
		            gluSphere(pObj, 0.02f, 26, 13);
		        
		            glTranslatef(-0.2f, 0.0f, 0.0f);
		            gluSphere(pObj, 0.02f, 26, 13);
		        
		            // Nose
		            glColor3f(1.0f, 0.3f, 0.3f);
		            glTranslatef(0.1f, -0.12f, 0.0f);
		            gluCylinder(pObj, 0.04f, 0.0f, 0.3f, 26, 13);
		        glPopMatrix();
		        
		        // Hat
		        glPushMatrix();
		            glColor3f(0.0f, 0.0f, 0.0f);
		            glTranslatef(0.0f, 1.17f, 0.0f);
		            glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
		            gluCylinder(pObj, 0.17f, 0.17f, 0.4f, 26, 13);
		            
		            // Hat brim
		            glDisable(GL_CULL_FACE);
		            gluDisk(pObj, 0.17f, 0.28f, 26, 13);
		            glEnable(GL_CULL_FACE);
		            
		            glTranslatef(0.0f, 0.0f, 0.40f);
		            gluDisk(pObj, 0.0f, 0.17f, 26, 13);
		        glPopMatrix();
		        
		    // Restore the matrix state
		    glPopMatrix();


		if (menuChanged) {
			processMenu(menuItem);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
		   // Light values and coordinates
	    float  whiteLight[] = { 0.05f, 0.05f, 0.05f, 1.0f };
	    float  sourceLight[] = { 0.25f, 0.25f, 0.25f, 1.0f };
	    float  lightPos[] = { -10.f, 5.0f, 5.0f, 1.0f };

	    glEnable(GL_DEPTH_TEST);	// Hidden surface removal
	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
	    glEnable(GL_CULL_FACE);		// Do not calculate inside

	    // Enable lighting
	    glEnable(GL_LIGHTING);

	    // Setup and enable light 0
	    glLightModelfv(GL_LIGHT_MODEL_AMBIENT,whiteLight);
	    glLightfv(GL_LIGHT0,GL_AMBIENT,sourceLight);
	    glLightfv(GL_LIGHT0,GL_DIFFUSE,sourceLight);
	    glLightfv(GL_LIGHT0,GL_POSITION,lightPos);
	    glEnable(GL_LIGHT0);

	    // Enable color tracking
	    glEnable(GL_COLOR_MATERIAL);
		
	    // Set Material properties to follow glColor values
	    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

	    // Black blue background
	    glClearColor(0.25f, 0.25f, 0.50f, 1.0f );


	}

	@Override
	public void ChangeSize(int w, int h) {
		float fAspect;

		// Prevent a divide by zero
		if (h == 0)
			h = 1;

		// Set Viewport to window dimensions
		glViewport(0, 0, w, h);

		fAspect = (float) w / (float) h;

		// Reset coordinate system
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Produce the perspective projection
		gluPerspective(35.0f, fAspect, 1.0f, 40.0f);

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
        xRot = (float)((int)xRot % 360);
        yRot = (float)((int)yRot % 360);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Snowman game = new Snowman("Modeling with Quadrics");

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
