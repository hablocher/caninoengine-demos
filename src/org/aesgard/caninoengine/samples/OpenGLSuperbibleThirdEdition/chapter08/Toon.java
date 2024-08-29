package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter08;
/*
import static org.aesgard.caninoengine.glutil.APITranslations.*;
import static org.aesgard.caninoengine.glutil.MatrixMath.*;
import static org.aesgard.caninoengine.glutil.VectorMath.*;
import static org.aesgard.caninoengine.util.Constants.*;
import static org.aesgard.caninoengine.util.BufferUtils.*;
import static org.lwjgl.opengl.ARBMultisample.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL21.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL33.*;
import static org.lwjgl.opengl.GL40.*;
import static org.lwjgl.opengl.GL41.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.util.glu.Util.*;
import static org.lwjgl.opengl.ARBImaging.*;
 */
import static org.aesgard.caninoengine.util.BufferUtils.array2ByteBuffer;
import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DECAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_1D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexImage1D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.actor.Torus;

public class Toon extends CaninoGameEngine3D {
	Torus torus = new Torus();
	
	@Override
	public void RenderScene() {
	    // Where is the light coming from
	    float[] vLightDir = { -1.0f, 1.0f, 1.0f };
	        
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glPushMatrix();
	        glTranslatef(0.0f, 0.0f, -2.5f);
	        glRotatef(yRot, 0.0f, 1.0f, 0.0f);
	        torus.toonDrawTorus(0.35f, 0.15f, 50, 25, vLightDir);
		glPopMatrix();

	    // Rotate 1/2 degree more each frame
	    yRot += 0.5f;
	}

	// This function does any needed initialization on the rendering
	// context.  Here it sets up and initializes the lighting for
	// the scene.
	@Override
	public void SetupRC() {
	    // Load a 1D texture with toon shaded values
	    // Green, greener...
	    byte[][] toonTable = { { 0, 32, 0 },
	                           { 0, 64, 0 },
	                           { 0, (byte)128, 0 },
	                           { 0, (byte)192, 0 }};

	    // Bluish background
	    glClearColor(0.0f, 0.0f, .50f, 1.0f );
	    glEnable(GL_DEPTH_TEST);
	    glEnable(GL_CULL_FACE);
	        
	    glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL);
	    glTexParameteri(GL_TEXTURE_1D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	    glTexParameteri(GL_TEXTURE_1D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
	    glTexParameteri(GL_TEXTURE_1D, GL_TEXTURE_WRAP_S, GL_CLAMP);
	    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	    
	    glTexImage1D(GL_TEXTURE_1D, 0, GL_RGB, 4, 0, GL_RGB, GL_UNSIGNED_BYTE, array2ByteBuffer(toonTable));
	    
	    glEnable(GL_TEXTURE_1D);
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
		Toon game = new Toon();
		game.start(800 ,600);
	}

}
