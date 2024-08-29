package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter05;

import static org.aesgard.caninoengine.glutil.APITranslations.glColor3ub;
import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
import static org.aesgard.caninoengine.glutil.MatrixMath.gltMakeShadowMatrix;
import static org.aesgard.caninoengine.util.BufferUtils.array2FloatBuffer;
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
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glMultMatrix;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.actor.Jet;

public class Shadow extends CaninoGameEngine3D {
	
	// These values need to be available globally
	// Light values and coordinates
	float  ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };
	float  diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };
	float  specular[]     = { 1.0f, 1.0f, 1.0f, 1.0f};
	float  lightPos[]     = { -75.0f, 150.0f, -50.0f, 0.0f };
	float  specref[]      = { 1.0f, 1.0f, 1.0f, 1.0f };

	// Transformation matrix to project shadow
	float[] shadowMat = new float[16];
	
	private Jet jet = new Jet();
	
	@Override
	public void RenderScene() {
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	    // Draw the ground, we do manual shading to a darker green
	    // in the background to give the illusion of depth
	    glBegin(GL_QUADS);
	        glColor3ub(0,32,0);
	        glVertex3f(400.0f, -150.0f, -200.0f);
	        glVertex3f(-400.0f, -150.0f, -200.0f);
	        glColor3ub(0,255,0);
	        glVertex3f(-400.0f, -150.0f, 200.0f);
	        glVertex3f(400.0f, -150.0f, 200.0f);
	    glEnd();

	    // Save the matrix state and do the rotations
	    glPushMatrix();

	    // Draw jet at new orientation, put light in correct position
	    // before rotating the jet
	    glEnable(GL_LIGHTING);
	    glLight(GL_LIGHT0,GL_POSITION,array2FloatBuffer(lightPos));
	    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	    glRotatef(yRot, 0.0f, 1.0f, 0.0f);

	    jet.DrawJet(0);

	    // Restore original matrix state
	    glPopMatrix();	


	    // Get ready to draw the shadow and the ground
	    // First disable lighting and save the projection state
	    glDisable(GL_DEPTH_TEST);
	    glDisable(GL_LIGHTING);
	    glPushMatrix();

	    // Multiply by shadow projection matrix
	    glMultMatrix(array2FloatBuffer(shadowMat));

	    // Now rotate the jet around in the new flattend space
	    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	    glRotatef(yRot, 0.0f, 1.0f, 0.0f);

	    // Pass true to indicate drawing shadow
	    jet.DrawJet(1);	

	    // Restore the projection to normal
	    glPopMatrix();

	    // Draw the light source
	    glPushMatrix();
	    glTranslatef(lightPos[0],lightPos[1], lightPos[2]);
	    glColor3ub(255,255,0);
	    glutSolidSphere(5.0f,10,10);
	    glPopMatrix();

	    // Restore lighting state variables
	    glEnable(GL_DEPTH_TEST);
	}

	@Override
	public void SetupRC() {
	    // Any three points on the ground (counter clockwise order)
	    float[][] points = {{ -30.0f, -149.0f, -20.0f },
	                        { -30.0f, -149.0f, 20.0f },
	                        { 40.0f, -149.0f, 20.0f }};

	    glEnable(GL_DEPTH_TEST);	// Hidden surface removal
	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
	    glEnable(GL_CULL_FACE);		// Do not calculate inside of jet

	    // Setup and enable light 0
	    glLight(GL_LIGHT0,GL_AMBIENT,array2FloatBuffer(ambientLight));
	    glLight(GL_LIGHT0,GL_DIFFUSE,array2FloatBuffer(diffuseLight));
	    glLight(GL_LIGHT0,GL_SPECULAR,array2FloatBuffer(specular));
	    glLight(GL_LIGHT0,GL_POSITION,array2FloatBuffer(lightPos));
	    glEnable(GL_LIGHT0);

	    // Enable color tracking
	    glEnable(GL_COLOR_MATERIAL);
		
	    // Set Material properties to follow glColor values
	    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

	    // All materials hereafter have full specular reflectivity
	    // with a high shine
	    glMaterial(GL_FRONT, GL_SPECULAR,array2FloatBuffer(specref));
	    glMateriali(GL_FRONT,GL_SHININESS,128);

	    // Light blue background
	    glClearColor(0.0f, 0.0f, 1.0f, 1.0f );

	    // Calculate projection matrix to draw shadow on the ground
	    gltMakeShadowMatrix(points, lightPos, shadowMat);
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

	    fAspect = (float)w/(float)h;
	    gluPerspective(60.0f, fAspect, 200.0f, 500.0f);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();

	    // Move out Z axis so we can see everything
	    glTranslatef(0.0f, 0.0f, -400.0f);
	    glLight(GL_LIGHT0,GL_POSITION,array2FloatBuffer(lightPos));
	}
	
	public static void main(String[] argv) {
		Shadow game = new Shadow();
		game.start();
	}

}
