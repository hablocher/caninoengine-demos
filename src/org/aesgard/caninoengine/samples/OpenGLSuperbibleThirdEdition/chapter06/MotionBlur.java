package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter06;

import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
import static org.lwjgl.opengl.GL11.GL_ACCUM;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_BACK;
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
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_LOAD;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RETURN;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glAccum;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glShadeModel;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class MotionBlur extends CaninoGameEngine3D {

	// Light and material Data
	private float fLightPos[]   = { -100.0f, 100.0f, 50.0f, 1.0f };  // Point source
	//private float fLightPosMirror[] = { -100.0f, -100.0f, 50.0f, 1.0f };
	private float fNoLight[] = { 0.0f, 0.0f, 0.0f, 0.0f };
	private float fLowLight[] = { 0.25f, 0.25f, 0.25f, 1.0f };
	private float fBrightLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	private float yRot = 45.0f;

	@Override
	public void RenderScene() {
	    float fPass;
	    float fPasses = 10.0f;
	    
	    // Set the current rotation back a few degrees
	    yRot = 35.0f;
	            
	    for(fPass = 0.0f; fPass < fPasses; fPass += 1.0f)
	    {
	        yRot += .75f; //1.0f / (fPass+1.0f);
	       
	        // Draw sphere
	        DrawGeometry();
	        
	        // Accumulate to back buffer
	        if(fPass == 0.0f)
	            glAccum(GL_LOAD, 0.5f);
	        else
	            glAccum(GL_ACCUM, 0.5f * (1.0f / fPasses));
	    }

	    // copy accumulation buffer to color buffer and
	    // do the buffer Swap
	    glAccum(GL_RETURN, 1.0f);
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
	    glLightfv(GL_LIGHT0, GL_POSITION, fLightPos);
	    
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

	/////////////////////////////////////////////////////////////
	//Draw the ground and the revolving sphere
	private void DrawGeometry()
	{
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glPushMatrix();
		DrawGround();
		
		// Place the moving sphere
		glColor3f(1.0f, 0.0f, 0.0f);
		glTranslatef(0.0f, 0.5f, -3.5f);
		glRotatef(-(yRot * 2.0f), 0.0f, 1.0f, 0.0f);
		glTranslatef(1.0f, 0.0f, 0.0f);
		glutSolidSphere(0.1f, 17, 9);
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
		MotionBlur game = new MotionBlur();
		game.start();
	}

}
