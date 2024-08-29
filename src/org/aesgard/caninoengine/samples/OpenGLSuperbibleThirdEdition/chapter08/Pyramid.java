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
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.util.glu.Util.*;
import static org.lwjgl.opengl.ARBImaging.*;
 */
import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glNormal3fv;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertex3fv;
import static org.aesgard.caninoengine.glutil.VectorMath.gltGetNormalVector;
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
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LUMINANCE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MODULATE;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.texture.Texture;
import org.aesgard.caninoengine.texture.TextureLoader;

public class Pyramid extends CaninoGameEngine3D {
	//////////////////////////////////////////////////////////////////
	// Module globals to save source image data
	private int iWidth, iHeight;
	private int eFormat = GL_RGB;;
	private int iComponents = GL_LUMINANCE;
	
    private Texture texture;
    
	TextureLoader loader = new TextureLoader();
	
	@Override
	public void RenderScene() {
	    float[] vNormal = new float[] {0,0,0};
	    float[][] vCorners = { { 0.0f, .80f, 0.0f },     // Top           0
	                           { -0.5f, 0.0f, -.50f },    // Back left     1
	                           { 0.5f, 0.0f, -0.50f },    // Back right    2
	                           { 0.5f, 0.0f, 0.5f },      // Front right   3
	                           { -0.5f, 0.0f, 0.5f }};    // Front left    4
	                              
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	    // Save the matrix state and do the rotations
	    glPushMatrix();
	        // Move object back and do in place rotation
	        glTranslatef(0.0f, -0.25f, -4.0f);
	        glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	        glRotatef(yRot, 0.0f, 1.0f, 0.0f);

	        // Draw the Pyramid
	        glColor3f(1.0f, 1.0f, 1.0f);
	        glBegin(GL_TRIANGLES);
	            // Bottom section - two triangles
	            glNormal3f(0.0f, -1.0f, 0.0f);
	            glTexCoord2f(1.0f, 1.0f);
	            glVertex3fv(vCorners[2]);
	            
	            glTexCoord2f(0.0f, 0.0f);
	            glVertex3fv(vCorners[4]);
	            
	            glTexCoord2f(0.0f, 1.0f);
	            glVertex3fv(vCorners[1]);
	            
	            
	            glTexCoord2f(1.0f, 1.0f);
	            glVertex3fv(vCorners[2]);
	            
	            glTexCoord2f(1.0f, 0.0f);
	            glVertex3fv(vCorners[3]);
	            
	            glTexCoord2f(0.0f, 0.0f);
	            glVertex3fv(vCorners[4]);
	            
	            // Front Face
	            gltGetNormalVector(vCorners[0], vCorners[4], vCorners[3], vNormal);
	            glNormal3fv(vNormal);
	            glTexCoord2f(0.5f, 1.0f);
	            glVertex3fv(vCorners[0]);
	            glTexCoord2f(0.0f, 0.0f);
	            glVertex3fv(vCorners[4]);
	            glTexCoord2f(1.0f, 0.0f);
	            glVertex3fv(vCorners[3]);
	            
	            // Left Face
	            gltGetNormalVector(vCorners[0], vCorners[1], vCorners[4], vNormal);
	            glNormal3fv(vNormal);
	            glTexCoord2f(0.5f, 1.0f);
	            glVertex3fv(vCorners[0]);
	            glTexCoord2f(0.0f, 0.0f);
	            glVertex3fv(vCorners[1]);
	            glTexCoord2f(1.0f, 0.0f);
	            glVertex3fv(vCorners[4]);

	            // Back Face
	            gltGetNormalVector(vCorners[0], vCorners[2], vCorners[1], vNormal);
	            glNormal3fv(vNormal);
	            glTexCoord2f(0.5f, 1.0f);
	            glVertex3fv(vCorners[0]);
	            
	            glTexCoord2f(0.0f, 0.0f);
	            glVertex3fv(vCorners[2]);
	            
	            glTexCoord2f(1.0f, 0.0f);
	            glVertex3fv(vCorners[1]);
	            
	            // Right Face
	            gltGetNormalVector(vCorners[0], vCorners[3], vCorners[2], vNormal);
	            glNormal3fv(vNormal);
	            glTexCoord2f(0.5f, 1.0f);
	            glVertex3fv(vCorners[0]);
	            glTexCoord2f(0.0f, 0.0f);
	            glVertex3fv(vCorners[3]);
	            glTexCoord2f(1.0f, 0.0f);
	            glVertex3fv(vCorners[2]);
	        glEnd();
	    

	    // Restore the matrix state
	    glPopMatrix();
		
	}

	// This function does any needed initialization on the rendering
	// context.  Here it sets up and initializes the lighting for
	// the scene.
	@Override
	public void SetupRC() {
	    
	    // Light values and coordinates
	    float  whiteLight[] = { 0.05f, 0.05f, 0.05f, 1.0f };
	    float  sourceLight[] = { 0.25f, 0.25f, 0.25f, 1.0f };
	    float	 lightPos[] = { -10.f, 5.0f, 5.0f, 1.0f };

	    glEnable(GL_DEPTH_TEST);	// Hidden surface removal
	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
	    glEnable(GL_CULL_FACE);		// Do not calculate inside of jet

	    // Enable lighting
	    glEnable(GL_LIGHTING);

	    // Setup and enable light 0
	    glLightModelfv(GL_LIGHT_MODEL_AMBIENT,whiteLight);
	    glLightfv(GL_LIGHT0,GL_AMBIENT,sourceLight);
	    glLightfv(GL_LIGHT0,GL_DIFFUSE,sourceLight);
	    glLightfv(GL_LIGHT0,GL_POSITION,lightPos);
	    glEnable(GL_LIGHT0);

	    // Enable color tracking
	    glEnable(GL_COLOR_MATERIAL);
		
	    // Set Material properties to follow glColor values
	    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

	    // Black blue background
	    glClearColor(0.0f, 0.0f, 0.0f, 1.0f );
	    
	    // Load texture
	    glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
	    texture = loader.Load(ImageType.TGA, "res/stone.tga");
		iWidth = texture.getWidth();
		iHeight = texture.getHeight();
	    glTexImage2D(GL_TEXTURE_2D, 0, iComponents, iWidth, iHeight, 0, eFormat, GL_UNSIGNED_BYTE, texture.getByteBuffer());
	    
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
	    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
	    
	    glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
	    glEnable(GL_TEXTURE_2D);
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
	    gluPerspective(35.0f, fAspect, 1.0f, 40.0f);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	@Override
	public void SpecialKeys() {
		super.SpecialKeys();
        xRot = (float)((int)xRot % 360);
        yRot = (float)((int)yRot % 360);
	}

	public static void main(String[] argv) {
		Pyramid game = new Pyramid();
		game.start(800 ,600);
	}

}
