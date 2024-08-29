package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter06;

import static org.aesgard.caninoengine.glutil.APITranslations.glFogfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glMaterialfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glMultMatrixf;
import static org.aesgard.caninoengine.glutil.APITranslations.gltApplyActorTransform;
import static org.aesgard.caninoengine.glutil.APITranslations.gltApplyCameraTransform;
import static org.aesgard.caninoengine.glutil.APITranslations.gltDrawTorus;
import static org.aesgard.caninoengine.glutil.APITranslations.gltInitFrame;
import static org.aesgard.caninoengine.glutil.APITranslations.gltMoveFrameForward;
import static org.aesgard.caninoengine.glutil.APITranslations.gltRotateFrameLocalY;
import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
import static org.aesgard.caninoengine.glutil.APITranslations.rand;
import static org.aesgard.caninoengine.glutil.MatrixMath.gltMakeShadowMatrix;
import static org.lwjgl.opengl.ARBMultisample.GL_MULTISAMPLE_ARB;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_BACK;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_EQUAL;
import static org.lwjgl.opengl.GL11.GL_FOG;
import static org.lwjgl.opengl.GL11.GL_FOG_COLOR;
import static org.lwjgl.opengl.GL11.GL_FOG_END;
import static org.lwjgl.opengl.GL11.GL_FOG_HINT;
import static org.lwjgl.opengl.GL11.GL_FOG_MODE;
import static org.lwjgl.opengl.GL11.GL_FOG_START;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_INCR;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_STENCIL_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_STENCIL_TEST;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glClearStencil;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glCullFace;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFogf;
import static org.lwjgl.opengl.GL11.glFogi;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glStencilFunc;
import static org.lwjgl.opengl.GL11.glStencilOp;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.glutil.GLTFrame;
import org.lwjgl.input.Keyboard;

public class SphereWorld extends CaninoGameEngine3D {

	private int NUM_SPHERES = 30;
	private GLTFrame[] spheres = new GLTFrame[NUM_SPHERES];
	private GLTFrame frameCamera = new GLTFrame();

	// Light and material Data
	private float fLightPos[]   = { -100.0f, 100.0f, 50.0f, 1.0f };  // Point source
	private float fNoLight[] = { 0.0f, 0.0f, 0.0f, 0.0f };
	private float fLowLight[] = { 0.25f, 0.25f, 0.25f, 1.0f };
	private float fBrightLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	private float[] mShadowMatrix = new float[16];
	
	private float yRot = 0.0f;         // Rotation angle for animation


	@Override
	public void RenderScene() {
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);
	        
	    glPushMatrix();
	        gltApplyCameraTransform(frameCamera);
	        
	        // Position light before any other transformations
	        glLightfv(GL_LIGHT0, GL_POSITION, fLightPos);
	        
	        // Draw the ground
	        glColor3f(0.60f, .40f, .10f);
	        DrawGround();
	        
	        // Draw shadows first
	        glDisable(GL_DEPTH_TEST);
	        glDisable(GL_LIGHTING);
	        glEnable(GL_BLEND);
	        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	        glEnable(GL_STENCIL_TEST);
	        glPushMatrix();
	            glMultMatrixf(mShadowMatrix);
	            DrawInhabitants(1);
	        glPopMatrix();
	        glDisable(GL_STENCIL_TEST);
	        glDisable(GL_BLEND);
	        glEnable(GL_LIGHTING);
	        glEnable(GL_DEPTH_TEST);
	        
	        // Draw inhabitants normally
	        DrawInhabitants(0);

	    glPopMatrix();
	}

	@Override
	public void SetupRC() {
	    float[][] vPoints = {{ 0.0f, -0.4f, 0.0f },
                			{ 10.0f, -0.4f, 0.0f },
                			{ 5.0f, -0.4f, -5.0f }};
		int iSphere;
		
		glEnable(GL_MULTISAMPLE_ARB);
		
		// Grayish background
		glClearColor(fLowLight[0], fLowLight[1], fLowLight[2], fLowLight[3]);
		
		// Clear stencil buffer with zero, increment by one whenever anybody
		// draws into it. When stencil function is enabled, only write where
		// stencil value is zero. This prevents the transparent shadow from drawing
		// over itself
		glStencilOp(GL_INCR, GL_INCR, GL_INCR);
		glClearStencil(0);
		glStencilFunc(GL_EQUAL, 0x0, 0x01);
		
		// Setup Fog parameters
		glEnable(GL_FOG);
		glFogfv(GL_FOG_COLOR, fLowLight);
		glFogi(GL_FOG_MODE, GL_LINEAR);
		glFogf(GL_FOG_START, 5.0f);
		glFogf(GL_FOG_END, 30.0f);
		glHint(GL_FOG_HINT, GL_NICEST);
		
		// Cull backs of polygons
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_MULTISAMPLE_ARB);
		
		// Setup light parameters
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT, fNoLight);
		glLightfv(GL_LIGHT0, GL_AMBIENT, fLowLight);
		glLightfv(GL_LIGHT0, GL_DIFFUSE, fBrightLight);
		glLightfv(GL_LIGHT0, GL_SPECULAR, fBrightLight);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		
		// Calculate shadow matrix
		gltMakeShadowMatrix(vPoints, fLightPos, mShadowMatrix);
		
		// Mostly use material tracking
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		glMateriali(GL_FRONT, GL_SHININESS, 128);
		
		gltInitFrame(frameCamera);  // Initialize the camera
		
		// Randomly place the sphere inhabitants
		for(iSphere = 0; iSphere < NUM_SPHERES; iSphere++)
		{
			spheres[iSphere] = new GLTFrame();
			gltInitFrame(spheres[iSphere]);    // Initialize the frame
			
			// Pick a random location between -20 and 20 at .1 increments
			spheres[iSphere].vLocation[0] = (float)((rand() % 400) - 200) * 0.1f;
			spheres[iSphere].vLocation[1] = 0.0f; 
			spheres[iSphere].vLocation[2] = (float)((rand() % 400) - 200) * 0.1f;
		}
	}
	

	///////////////////////////////////////////////////////////
	//Draw the ground as a series of triangle strips
	private void DrawGround()
	{
		float fExtent = 20.0f;
		float fStep = 1.0f;
		float y = -0.4f;
		int iStrip, iRun;
		
		for(iStrip = ((int)-fExtent); iStrip <= fExtent; iStrip += fStep)
		{
			glBegin(GL_TRIANGLE_STRIP);
			glNormal3f(0.0f, 1.0f, 0.0f);   // All Point up
			
			for(iRun = ((int)fExtent); iRun >= -fExtent; iRun -= fStep)
			{
				glVertex3f(iStrip, y, iRun);
				glVertex3f(iStrip + fStep, y, iRun);
			}
			glEnd();
		}
	}

	///////////////////////////////////////////////////////////////////////
	//Draw random inhabitants and the rotating torus/sphere duo
	private void DrawInhabitants(int nShadow)
	{
		int i;
		
		if(nShadow == 0)
		yRot += 0.5f;
		else
		glColor4f(0.0f, 0.0f, 0.0f, 0.5f);
		
		// Draw the randomly located spheres
		if(nShadow == 0)
		glColor3f(0.0f, 1.0f, 0.0f);
		
		
		for(i = 0; i < NUM_SPHERES; i++)
		{
			glPushMatrix();
			gltApplyActorTransform(spheres[i]);
			glutSolidSphere(0.3f, 21, 11);
			glPopMatrix();
		}
		
		glPushMatrix();
		glTranslatef(0.0f, 0.1f, -2.5f);
		
		if(nShadow == 0)
			glColor3f(0.0f, 0.0f, 1.0f);
		
		glPushMatrix();
		glRotatef(-yRot * 2.0f, 0.0f, 1.0f, 0.0f);
		glTranslatef(1.0f, 0.0f, 0.0f);
		glutSolidSphere(0.1f,21, 11);
		glPopMatrix();
		
		if(nShadow == 0)
		{
			// Torus alone will be specular
			glColor3f(1.0f, 0.0f, 0.0f);
			glMaterialfv(GL_FRONT, GL_SPECULAR, fBrightLight);
		}
		
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);
		gltDrawTorus(0.35, 0.15, 61, 37);
		glMaterialfv(GL_FRONT, GL_SPECULAR, fNoLight);
		glPopMatrix();
	}

	// Respond to arrow keys by moving the camera frame of reference
	@Override
	public void SpecialKeys() 
	{
		if (Keyboard.next() || Keyboard.isRepeatEvent()) {
			if(Keyboard.isKeyDown(Keyboard.KEY_UP))
				gltMoveFrameForward(frameCamera, 0.1f);

			if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				gltMoveFrameForward(frameCamera, -0.1f);

			if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				gltRotateFrameLocalY(frameCamera, 0.1);

			if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				gltRotateFrameLocalY(frameCamera, -0.1);
		}
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
	}
	
	public static void main(String[] argv) {
		SphereWorld game = new SphereWorld();
		game.start();
	}

}
