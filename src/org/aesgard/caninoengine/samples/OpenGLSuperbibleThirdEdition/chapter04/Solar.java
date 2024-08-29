package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter04;

import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
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
import static org.lwjgl.opengl.GL11.glColor3ub;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import java.nio.FloatBuffer;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.lwjgl.BufferUtils;

public class Solar extends CaninoGameEngine3D {
	
    FloatBuffer whiteLight = BufferUtils.createFloatBuffer(4);
    FloatBuffer sourceLight = BufferUtils.createFloatBuffer(4);
    FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);

    // Earth and Moon angle of revolution
	float fMoonRot = 0.0f;
	float fEarthRot = 0.0f;

	@Override
	public void RenderScene() {

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Save the matrix state and do the rotations
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();

		// Translate the whole scene out and into view	
		glTranslatef(0.0f, 0.0f, -300.0f);	
		
		// Set material color, Red
		// Sun
        glDisable(GL_LIGHTING);
		glColor3ub((byte)255, (byte)255, (byte)0);
		glutSolidSphere(15.0f, 30, 17);
        glEnable(GL_LIGHTING);

		// Move the light after we draw the sun!
		glLight(GL_LIGHT0,GL_POSITION,lightPos);

		// Rotate coordinate system
		glRotatef(fEarthRot, 0.0f, 1.0f, 0.0f);

		// Draw the Earth
		glColor3ub((byte)0,(byte)0,(byte)255);
		glTranslatef(105.0f,0.0f,0.0f);
		glutSolidSphere(15.0f, 30, 17);


		// Rotate from Earth based coordinates and draw Moon
		glColor3ub((byte)200,(byte)200,(byte)200);
		glRotatef(fMoonRot,0.0f, 1.0f, 0.0f);
		glTranslatef(30.0f, 0.0f, 0.0f);
		fMoonRot+= 15.0f;
		if(fMoonRot > 360.0f)
			fMoonRot = 0.0f;

		glutSolidSphere(6.0f, 30, 17);

		// Restore the matrix state
		glPopMatrix();	// Modelview matrix


		// Step earth orbit 5 degrees
		fEarthRot += 5.0f;
		if(fEarthRot > 360.0f)
			fEarthRot = 0.0f;

	}

	@Override
	public void SetupRC() {
	    whiteLight.put(new float[]{ 0.2f, 0.2f, 0.2f, 1.0f });
	    whiteLight.flip();
	    
	    sourceLight.put(new float[]{ 0.8f, 0.8f, 0.8f, 1.0f });
	    sourceLight.flip();
	    
	    lightPos.put(new float[]{ 0.0f, 0.0f, 0.0f, 1.0f });
	    lightPos.flip();
	    
		// Light values and coordinates
		glEnable(GL_DEPTH_TEST);	// Hidden surface removal
		glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
		glEnable(GL_CULL_FACE);		// Do not calculate inside of jet

		// Enable lighting
		glEnable(GL_LIGHTING);

		// Setup and enable light 0
		glLightModel(GL_LIGHT_MODEL_AMBIENT,whiteLight);
		glLight(GL_LIGHT0,GL_DIFFUSE,sourceLight);
		glLight(GL_LIGHT0,GL_POSITION,lightPos);
		glEnable(GL_LIGHT0);

		// Enable color tracking
		glEnable(GL_COLOR_MATERIAL);
		
		// Set Material properties to follow glColor values
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

		// Black blue background
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

	    // Calculate aspect ratio of the window
	    fAspect = (float)w/(float)h;

	    // Set the perspective coordinate system
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    // field of view of 45 degrees, near and far planes 1.0 and 425
	    gluPerspective(45.0f, fAspect, 1.0f, 425.0f);

	    // Modelview matrix reset
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	public static void main(String[] argv) {
		Solar game = new Solar();
		game.setSleepTime(50);
		game.start();
	}

}
