package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter05;

import static org.aesgard.caninoengine.glutil.APITranslations.glColor3ub;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glMaterialfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidCone;
import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHTING_BIT;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SPOT_CUTOFF;
import static org.lwjgl.opengl.GL11.GL_SPOT_DIRECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLightf;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Spot extends CaninoGameEngine3D {
	private static final String VH_TESS = "VH Tess";
	private static final String MD_TESS = "MD Tess";
	private static final String VL_TESS = "VL Tess";
	private static final String SMOOTH_SHADING = "Smooth Shading";
	private static final String FLAT_SHADING = "Flat Shading";
	
	// Light values and coordinates
	private float  lightPos[] = { 0.0f, 0.0f, 75.0f, 1.0f };
	private float  specular[] = { 1.0f, 1.0f, 1.0f, 1.0f};
	private float  specref[] =  { 1.0f, 1.0f, 1.0f, 1.0f };
	private float  ambientLight[] = { 0.5f, 0.5f, 0.5f, 1.0f};
	private float  spotDir[] = { 0.0f, 0.0f, -1.0f, 0f };

	// Flags for effects
	private static final int MODE_FLAT = 1;
	private static final int MODE_SMOOTH = 2;
	private static final int MODE_VERYLOW = 3;
	private static final int MODE_MEDIUM = 4;
	private static final int MODE_VERYHIGH = 5;

	private int iShade = MODE_FLAT;
	private int iTess = MODE_VERYLOW;

	@Override
	public void RenderScene() {
	    if(iShade == MODE_FLAT)
	        glShadeModel(GL_FLAT);
	    else // 	iShade = MODE_SMOOTH;
	        glShadeModel(GL_SMOOTH);

	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	    // First place the light 
	    // Save the coordinate transformation
	    glPushMatrix();	
	        // Rotate coordinate system
	        glRotatef(yRot, 0.0f, 1.0f, 0.0f);
	        glRotatef(xRot, 1.0f, 0.0f, 0.0f);

	        // Specify new position and direction in rotated coords.
	        glLightfv(GL_LIGHT0,GL_POSITION, lightPos);
	        glLightfv(GL_LIGHT0,GL_SPOT_DIRECTION, spotDir);

	        // Draw a red cone to enclose the light source
	        glColor3ub(255,0,0);	

	        // Translate origin to move the cone out to where the light
	        // is positioned.
	        glTranslatef(lightPos[0],lightPos[1],lightPos[2]);
	        glutSolidCone(4.0f,6.0f,15,15);

	        // Draw a smaller displaced sphere to denote the light bulb
	        // Save the lighting state variables
	        glPushAttrib(GL_LIGHTING_BIT);

	            // Turn off lighting and specify a bright yellow sphere
	            glDisable(GL_LIGHTING);
	            glColor3ub(255,255,0);
	            glutSolidSphere(3.0f, 15, 15);

	        // Restore lighting state variables
	        glPopAttrib();

	    // Restore coordinate transformations
	    glPopMatrix();


	    // Set material color and draw a sphere in the middle
	    glColor3ub(0, 0, 255);

	    if(iTess == MODE_VERYLOW)
	    	glutSolidSphere(30.0f, 7, 7);
	    else 
	        if(iTess == MODE_MEDIUM)
	            glutSolidSphere(30.0f, 15, 15);
	        else //  iTess = MODE_MEDIUM;
	            glutSolidSphere(30.0f, 50, 50);
	}

	@Override
	public void SetupRC() {
	    glEnable(GL_DEPTH_TEST);	// Hidden surface removal
	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
	    glEnable(GL_CULL_FACE);		// Do not try to display the back sides

	    // Enable lighting
	    glEnable(GL_LIGHTING);

	    // Setup and enable light 0
	    // Supply a slight ambient light so the objects can be seen
	    glLightModelfv(GL_LIGHT_MODEL_AMBIENT, ambientLight);
		
	    // The light is composed of just a diffuse and specular components
	    glLightfv(GL_LIGHT0,GL_DIFFUSE,ambientLight);
	    glLightfv(GL_LIGHT0,GL_SPECULAR,specular);
	    glLightfv(GL_LIGHT0,GL_POSITION,lightPos);

	    // Specific spot effects
	    // Cut off angle is 60 degrees
	    glLightf(GL_LIGHT0,GL_SPOT_CUTOFF,50.0f);

	    // Enable this light in particular
	    glEnable(GL_LIGHT0);

	    // Enable color tracking
	    glEnable(GL_COLOR_MATERIAL);
		
	    // Set Material properties to follow glColor values
	    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

	    // All materials hereafter have full specular reflectivity
	    // with a high shine
	    glMaterialfv(GL_FRONT, GL_SPECULAR,specref);
	    glMateriali(GL_FRONT, GL_SHININESS,128);


	    // Black background
	    glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

	}
	
	@Override
	public void ChangeSize(int w, int h) {
	    float fAspect;
	    
	    // Prevent a divide by zero
	    if(h == 0)
	        h = 1;

	    // Set Viewport to window dimensions
	    glViewport(0, 0, w, h);

	    // Reset coordinate system
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    // Establish viewing volume
	    fAspect = (float) w / (float) h;
	    gluPerspective(35.0f, fAspect, 1.0f, 500.0f);
	    
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	    glTranslatef(0.0f, 0.0f, -250.0f);

	}
	
	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if (action.equalsIgnoreCase(FLAT_SHADING)) 
				iShade = MODE_FLAT;

			if (action.equalsIgnoreCase(SMOOTH_SHADING)) 
				iShade = MODE_SMOOTH;

			if (action.equalsIgnoreCase(VL_TESS)) 
				iTess = MODE_VERYLOW;
			
			if (action.equalsIgnoreCase(MD_TESS)) 
				iTess = MODE_MEDIUM;
			
			if (action.equalsIgnoreCase(VH_TESS)) 
				iTess = MODE_VERYHIGH;
		}
	}

	public static void main(String[] argv) {
		Spot game = new Spot();
		
		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		
		JMenuItem menuItem;
		
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem(FLAT_SHADING);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(SMOOTH_SHADING);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(VL_TESS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(MD_TESS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(VH_TESS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);

		game.addPopup(menu);
		
		game.start();
	}

}
