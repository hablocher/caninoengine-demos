package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter04;

import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
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
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLightModel;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import java.nio.FloatBuffer;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.lwjgl.BufferUtils;

public class Perspect extends CaninoGameEngine3D {
	@Override
	public void RenderScene() {
	    float fZ,bZ;

	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	    fZ = 100.0f;
	    bZ = -100.0f;

	    // Save the matrix state and do the rotations
	    glPushMatrix();
	    glTranslatef(0.0f, 0.0f, -300.0f);
	    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	    glRotatef(yRot, 0.0f, 1.0f, 0.0f);

	    // Set material color, Red
	    glColor3f(1.0f, 0.0f, 0.0f);

	    // Front Face ///////////////////////////////////
	    glBegin(GL_QUADS);
	        // Pointing straight out Z
	        glNormal3f(0.0f, 0.0f, 1.0f);	

	        // Left Panel
	        glVertex3f(-50.0f, 50.0f, fZ);
	        glVertex3f(-50.0f, -50.0f, fZ);
	        glVertex3f(-35.0f, -50.0f, fZ);
	        glVertex3f(-35.0f,50.0f,fZ);

	        // Right Panel
	        glVertex3f(50.0f, 50.0f, fZ);
	        glVertex3f(35.0f, 50.0f, fZ);
	        glVertex3f(35.0f, -50.0f, fZ);
	        glVertex3f(50.0f,-50.0f,fZ);

	        // Top Panel
	        glVertex3f(-35.0f, 50.0f, fZ);
	        glVertex3f(-35.0f, 35.0f, fZ);
	        glVertex3f(35.0f, 35.0f, fZ);
	        glVertex3f(35.0f, 50.0f,fZ);

	        // Bottom Panel
	        glVertex3f(-35.0f, -35.0f, fZ);
	        glVertex3f(-35.0f, -50.0f, fZ);
	        glVertex3f(35.0f, -50.0f, fZ);
	        glVertex3f(35.0f, -35.0f,fZ);

	        // Top length section ////////////////////////////
	        // Normal points up Y axis
	        glNormal3f(0.0f, 1.0f, 0.0f);
	        glVertex3f(-50.0f, 50.0f, fZ);
	        glVertex3f(50.0f, 50.0f, fZ);
	        glVertex3f(50.0f, 50.0f, bZ);
	        glVertex3f(-50.0f,50.0f,bZ);
			
	        // Bottom section
	        glNormal3f(0.0f, -1.0f, 0.0f);
	        glVertex3f(-50.0f, -50.0f, fZ);
	        glVertex3f(-50.0f, -50.0f, bZ);
	        glVertex3f(50.0f, -50.0f, bZ);
	        glVertex3f(50.0f, -50.0f, fZ);

	        // Left section
	        glNormal3f(1.0f, 0.0f, 0.0f);
	        glVertex3f(50.0f, 50.0f, fZ);
	        glVertex3f(50.0f, -50.0f, fZ);
	        glVertex3f(50.0f, -50.0f, bZ);
	        glVertex3f(50.0f, 50.0f, bZ);

	        // Right Section
	        glNormal3f(-1.0f, 0.0f, 0.0f);
	        glVertex3f(-50.0f, 50.0f, fZ);
	        glVertex3f(-50.0f, 50.0f, bZ);
	        glVertex3f(-50.0f, -50.0f, bZ);
	        glVertex3f(-50.0f, -50.0f, fZ);
	    glEnd();

	    glFrontFace(GL_CW);		// clock-wise polygons face out

	    glBegin(GL_QUADS);
	        // Back section
	        // Pointing straight out Z
	        glNormal3f(0.0f, 0.0f, -1.0f);	

	        // Left Panel
	        glVertex3f(-50.0f, 50.0f, bZ);
	        glVertex3f(-50.0f, -50.0f, bZ);
	        glVertex3f(-35.0f, -50.0f, bZ);
	        glVertex3f(-35.0f,50.0f,bZ);

	        // Right Panel
	        glVertex3f(50.0f, 50.0f, bZ);
	        glVertex3f(35.0f, 50.0f, bZ);
	        glVertex3f(35.0f, -50.0f, bZ);
	        glVertex3f(50.0f,-50.0f,bZ);

	        // Top Panel
	        glVertex3f(-35.0f, 50.0f, bZ);
	        glVertex3f(-35.0f, 35.0f, bZ);
	        glVertex3f(35.0f, 35.0f, bZ);
	        glVertex3f(35.0f, 50.0f,bZ);

	        // Bottom Panel
	        glVertex3f(-35.0f, -35.0f, bZ);
	        glVertex3f(-35.0f, -50.0f, bZ);
	        glVertex3f(35.0f, -50.0f, bZ);
	        glVertex3f(35.0f, -35.0f,bZ);
		
	        // Insides /////////////////////////////
	    	glColor3f(0.75f, 0.75f, 0.75f);

	        // Normal points up Y axis
	        glNormal3f(0.0f, 1.0f, 0.0f);
	        glVertex3f(-35.0f, 35.0f, fZ);
	        glVertex3f(35.0f, 35.0f, fZ);
	        glVertex3f(35.0f, 35.0f, bZ);
	        glVertex3f(-35.0f,35.0f,bZ);
			
	        // Bottom section
	        glNormal3f(0.0f, 1.0f, 0.0f);
	        glVertex3f(-35.0f, -35.0f, fZ);
	        glVertex3f(-35.0f, -35.0f, bZ);
	        glVertex3f(35.0f, -35.0f, bZ);
	        glVertex3f(35.0f, -35.0f, fZ);

	        // Left section
	        glNormal3f(1.0f, 0.0f, 0.0f);
	        glVertex3f(-35.0f, 35.0f, fZ);
	        glVertex3f(-35.0f, 35.0f, bZ);
	        glVertex3f(-35.0f, -35.0f, bZ);
	        glVertex3f(-35.0f, -35.0f, fZ);

	        // Right Section
	        glNormal3f(-1.0f, 0.0f, 0.0f);
	        glVertex3f(35.0f, 35.0f, fZ);
	        glVertex3f(35.0f, -35.0f, fZ);
	        glVertex3f(35.0f, -35.0f, bZ);
	        glVertex3f(35.0f, 35.0f, bZ);
	    glEnd();

	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out

	    // Restore the matrix state
	    glPopMatrix();
	}
	
	@Override
	public void SetupRC() {
	    // Light values and coordinates
	    FloatBuffer whiteLight = BufferUtils.createFloatBuffer(4);
	    whiteLight.put(new float[]{ 0.45f, 0.45f, 0.45f, 1.0f });
	    whiteLight.flip();

	    FloatBuffer sourceLight = BufferUtils.createFloatBuffer(4);
	    sourceLight.put(new float[]{ 0.25f, 0.25f, 0.25f, 1.0f });
	    sourceLight.flip();
	    
	    FloatBuffer lightPos = BufferUtils.createFloatBuffer(4);
	    lightPos.put(new float[]{ -50.f, 25.0f, 250.0f, 0.0f });
	    lightPos.flip(); // Call buffer.flip(); after putting the last float value in the buffer. 
	                     // This will reset the position, ensuring that the next read access will start at index 0.
	                     // for performance reasons it might be advantageous to reuse a buffer instead of creating a 
	                     // new one each time. If you reuse a buffer, be sure to invoke buffer.clear(); 


	    glEnable(GL_DEPTH_TEST);	// Hidden surface removal
	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
	    glEnable(GL_CULL_FACE);		// Do not calculate inside of jet

	    // Enable lighting
	    glEnable(GL_LIGHTING);

	    // Setup and enable light 0
	    glLightModel(GL_LIGHT_MODEL_AMBIENT,whiteLight);
	    glLight(GL_LIGHT0,GL_AMBIENT,sourceLight);
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

	    fAspect = (float)w/(float)h;

	    // Reset coordinate system
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    // Produce the perspective projection
	    gluPerspective(60.0f, fAspect, 1.0f, 400.0f);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	public static void main(String[] argv) {
		Perspect game = new Perspect();
		game.start();
	}

}
