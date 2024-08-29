package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter04;

import static org.aesgard.caninoengine.glutil.MatrixMath.gltRotationMatrix;
import static org.aesgard.caninoengine.util.Constants.gltDegToRad;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrix;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import java.nio.FloatBuffer;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.actor.Torus;
import org.lwjgl.BufferUtils;

public class Transformgl extends CaninoGameEngine3D {
    Torus torus = new Torus();
    
    float yRot = 0.0f;         // Rotation angle for animation
    
	@Override
	public void RenderScene() {
	    float[] transformationMatrix = new float[16];   // Storeage for rotation matrix

	    yRot += 0.5f;
	        
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	        
	    // Build a rotation matrix
	    gltRotationMatrix(gltDegToRad(yRot), 0.0f, 1.0f, 0.0f, transformationMatrix);
	    transformationMatrix[12] = 0.0f;
	    transformationMatrix[13] = 0.0f;
	    transformationMatrix[14] = -2.5f;
	    
	    FloatBuffer b = BufferUtils.createFloatBuffer(16);
	    b.put(transformationMatrix);
	    b.flip();
	        
	    glLoadMatrix(b);

	    torus.gltDrawTorus(0.35f, 0.15f, 40, 20);

	}

	@Override
	public void SetupRC() {
	    // Bluish background
	    glClearColor(0.0f, 0.0f, .50f, 1.0f );
	         
	    // Draw everything as wire frame
	    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
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
		Transformgl game = new Transformgl();
		game.start();
	}

}
