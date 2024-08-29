package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter04;

import static org.aesgard.caninoengine.glutil.APITranslations.gltApplyCameraTransform;
import static org.aesgard.caninoengine.glutil.APITranslations.gltInitFrame;
import static org.aesgard.caninoengine.glutil.APITranslations.glutSolidSphere;
import static org.aesgard.caninoengine.glutil.APITranslations.rand;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.actor.Torus;
import org.aesgard.caninoengine.glutil.GLTFrame;

public class SphereWorld extends CaninoGameEngine3D {
	
	private final int NUM_SPHERES = 100;
	private GLTFrame[] spheres = new GLTFrame[NUM_SPHERES];
	private GLTFrame   frameCamera = new GLTFrame();
    private float yRot = 0.0f;         // Rotation angle for animation
    private Torus torus = new Torus();
		
	@Override
	public void RenderScene() {
	    int i;
	    yRot += 0.5f;
	        
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	        
	    glPushMatrix();
	    	gltApplyCameraTransform(frameCamera);
	        
	        // Draw the ground
	        DrawGround();
	        
	        // Draw the randomly located spheres
	        for(i = 0; i < NUM_SPHERES; i++)
	        {
	            glPushMatrix();
	            spheres[i].gltApplyCameraTransform();
	            glutSolidSphere(0.1f, 13, 26);
	            glPopMatrix();
	        }

	        glPushMatrix();
	            glTranslatef(0.0f, 0.0f, -2.5f);
	    
	            glPushMatrix();
	                glRotatef(-yRot * 2.0f, 0.0f, 1.0f, 0.0f);
	                glTranslatef(1.0f, 0.0f, 0.0f);
	                glutSolidSphere(0.1f, 13, 26);
	            glPopMatrix();
	    
	            glRotatef(yRot, 0.0f, 1.0f, 0.0f);
	            torus.gltDrawTorus(0.35f, 0.15f, 40, 20);
	        glPopMatrix();
	    glPopMatrix();

	}

	@Override
	public void SetupRC() {
	    int iSphere;
	    
	    // Bluish background
	    glClearColor(0.0f, 0.0f, .50f, 1.0f );
	         
	    // Draw everything as wire frame
	    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
	    
	    gltInitFrame(frameCamera); // Initialize the camera

	    // Randomly place the sphere inhabitants
	    for(iSphere = 0; iSphere < NUM_SPHERES; iSphere++)
	        {
	    	spheres[iSphere] = new GLTFrame();
	    	gltInitFrame(spheres[iSphere]);  // Initialize the frame 
	        
	        // Pick a random location between -20 and 20 at .1 increments
	        spheres[iSphere].vLocation[0] = (float)((rand() % 400) - 200) * 0.1f;
	        spheres[iSphere].vLocation[1] = 0.0f; 
	        spheres[iSphere].vLocation[2] = (float)((rand() % 400) - 200) * 0.1f;
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
	
	// Draw a gridded ground
	private void DrawGround()
	{
	    float fExtent = 20.0f;
	    float fStep = 1.0f;
	    float y = -0.4f;
	    int iLine;
	    
	    glBegin(GL_LINES);
	       for(iLine = (int)-fExtent; iLine <= fExtent; iLine += fStep)
	       {
	          glVertex3f(iLine, y, fExtent);    // Draw Z lines
	          glVertex3f(iLine, y, -fExtent);
	    
	          glVertex3f(fExtent, y, iLine);
	          glVertex3f(-fExtent, y, iLine);
	       }
	    
	    glEnd();
	}
	
	public static void main(String[] argv) {
		SphereWorld game = new SphereWorld();
		game.start();
	}

}
