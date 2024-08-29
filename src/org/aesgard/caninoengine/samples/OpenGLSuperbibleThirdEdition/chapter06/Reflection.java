package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter06;

import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.gltDrawTorus;
import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CW;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Reflection extends CaninoGameEngine3D {

	// Light and material Data
	float fLightPos[]   = { -100.0f, 100.0f, 50.0f, 1.0f };  // Point source
	float fLightPosMirror[] = { -100.0f, -100.0f, 50.0f, 1.0f };
	float fNoLight[] = { 0.0f, 0.0f, 0.0f, 0.0f };
	float fLowLight[] = { 0.25f, 0.25f, 0.25f, 1.0f };
	float fBrightLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	private float yRot = 0.0f;         // Rotation angle for animation    

	@Override
	public void RenderScene() {
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	        
	    glPushMatrix();
	        // Move light under floor to light the "reflected" world
	        glLightfv(GL_LIGHT0, GL_POSITION, fLightPosMirror);
	        glPushMatrix();
	            glFrontFace(GL_CW);             // geometry is mirrored, swap orientation
	            glScalef(1.0f, -1.0f, 1.0f);
	            DrawWorld();
	            glFrontFace(GL_CCW);
	        glPopMatrix();
	    
	        // Draw the ground transparently over the reflection
	        glDisable(GL_LIGHTING);
	        glEnable(GL_BLEND);
	        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	        DrawGround();
	        glDisable(GL_BLEND);
	        glEnable(GL_LIGHTING);
	        
	        // Restore correct lighting and draw the world correctly
	        glLightfv(GL_LIGHT0, GL_POSITION, fLightPos);
	        DrawWorld();
	    glPopMatrix();
	}

	@Override
	public void SetupRC() {
	    // Grayish background
	    glClearColor(fLowLight[0], fLowLight[1], fLowLight[2], fLowLight[3]);
	   
	    // Cull backs of polygons
	    glCullFace(GL_BACK);
	    glFrontFace(GL_CCW);
	    glEnable(GL_CULL_FACE);
	    glEnable(GL_DEPTH_TEST);
	    
	    // Setup light parameters
	    glLightModelfv(GL_LIGHT_MODEL_AMBIENT, fNoLight);
	    glLightfv(GL_LIGHT0, GL_AMBIENT, fLowLight);
	    glLightfv(GL_LIGHT0, GL_DIFFUSE, fBrightLight);
	    glLightfv(GL_LIGHT0, GL_SPECULAR, fBrightLight);
	    glEnable(GL_LIGHTING);
	    glEnable(GL_LIGHT0);
	     
	    // Mostly use material tracking
	    glEnable(GL_COLOR_MATERIAL);
	    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
	    glMateriali(GL_FRONT, GL_SHININESS, 128);
	}
	
	///////////////////////////////////////////////////////////
	//Draw the ground as a series of triangle strips. The 
	//shading model and colors are set such that we end up 
	//with a black and white checkerboard pattern.
	private void DrawGround()
	{
		float fExtent = 20.0f;
		float fStep = 0.5f;
		float y = 0.0f;
		float fColor;
		float iStrip, iRun;
		int iBounce = 0;
		
		glShadeModel(GL_FLAT);
		for(iStrip = -fExtent; iStrip <= fExtent; iStrip += fStep)
		{
			glBegin(GL_TRIANGLE_STRIP);
			for(iRun = fExtent; iRun >= -fExtent; iRun -= fStep)
			{
				if((iBounce %2) == 0)
					fColor = 1.0f;
				else
					fColor = 0.0f;
				
				glColor4f(fColor, fColor, fColor, 0.5f);
				glVertex3f(iStrip, y, iRun);
				glVertex3f(iStrip + fStep, y, iRun);
				
				iBounce++;
			}
			glEnd();
		}
		glShadeModel(GL_SMOOTH);
	}
	
	///////////////////////////////////////////////////////////////////////
	//Draw random inhabitants and the rotating torus/sphere duo
	private void DrawWorld()
	{
		glColor3f(1.0f, 0.0f, 0.0f);
		glPushMatrix();
		glTranslatef(0.0f, 0.5f, -3.5f);
		
		glPushMatrix();
		glRotatef(-yRot * 2.0f, 0.0f, 1.0f, 0.0f);
		glTranslatef(1.0f, 0.0f, 0.0f);
		glutSolidSphere(0.1f, 17, 9);
		glPopMatrix();
		
		
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);
		gltDrawTorus(0.35, 0.15, 61, 37);
		
		glPopMatrix();
	}

	
	@Override
	public void ChangeSize(int w, int h) {
	    float fAspect;

	    // Prevent a divide by zero, when window is too short
	    // (you cant make a window of zero width).
	    if(h == 0)
	        h = 1;

	    glViewport(0, 0, w, h);
	        
	    fAspect = (float)w / (float)h;

	    // Reset the coordinate system before modifying
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
		
	    // Set the clipping volume
	    gluPerspective(35.0f, fAspect, 1.0f, 50.0f);
	        
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();    
	    glTranslatef(0.0f, -0.4f, 0.0f);
	}
	
	public static void main(String[] argv) {
		Reflection game = new Reflection();
		game.start();
	}

}
