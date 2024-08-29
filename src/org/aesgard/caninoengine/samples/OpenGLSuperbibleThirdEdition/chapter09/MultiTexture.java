package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter09;

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
 import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.*;

 */
import static org.aesgard.caninoengine.glutil.APITranslations.glDeleteTextures;
import static org.aesgard.caninoengine.glutil.APITranslations.glGenTextures;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightModelfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.aesgard.caninoengine.glutil.APITranslations.glMaterialfv;
import static org.aesgard.caninoengine.glutil.APITranslations.gltDrawSphere;
import static org.aesgard.caninoengine.glutil.APITranslations.gltDrawTorus;
import static org.aesgard.caninoengine.glutil.APITranslations.gltMoveFrameForward;
import static org.aesgard.caninoengine.glutil.APITranslations.gltRotateFrameLocalY;
import static org.aesgard.caninoengine.glutil.APITranslations.rand;
import static org.lwjgl.opengl.EXTBgra.GL_BGR_EXT;
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
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LIGHT_MODEL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MODULATE;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_R;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_S;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_T;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_GEN_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_GEN_R;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_GEN_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_GEN_T;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_STRIP;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
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
import static org.lwjgl.opengl.GL11.glLightModeli;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexGeni;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL12.GL_LIGHT_MODEL_COLOR_CONTROL;
import static org.lwjgl.opengl.GL12.GL_SEPARATE_SPECULAR_COLOR;
import static org.lwjgl.opengl.GL12.GL_TEXTURE_WRAP_R;
import static org.lwjgl.opengl.GL13.GL_COMPRESSED_RGB;
import static org.lwjgl.opengl.GL13.GL_REFLECTION_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_X;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Y;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP_POSITIVE_Z;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL14.GL_GENERATE_MIPMAP;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.glutil.GLTFrame;
import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.texture.Texture;
import org.aesgard.caninoengine.texture.TextureLoader;
import org.lwjgl.input.Keyboard;

public class MultiTexture extends CaninoGameEngine3D {
	private int NUM_SPHERES = 30;
	private GLTFrame[] spheres = new GLTFrame[NUM_SPHERES];
	private GLTFrame frameCamera = new GLTFrame();

	// Light and material Data
	float fLightPos[] = { -100.0f, 100.0f, 50.0f, 1.0f }; // Point source
	float fNoLight[] = { 0.0f, 0.0f, 0.0f, 0.0f };
	float fLowLight[] = { 0.25f, 0.25f, 0.25f, 1.0f };
	float fBrightLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	private int GROUND_TEXTURE = 0;
	private int SPHERE_TEXTURE = 1;
	private int WOOD_TEXTURE = 2;
	private int CUBE_MAP = 3;
	private int NUM_TEXTURES = 4;
	private int[] textureObjects = new int[NUM_TEXTURES];

	private String szTextureFiles[] = new String[] { "res/multitexture/grass.tga",
			"res/multitexture/orb.tga", "res/multitexture/wood.tga" };

	private String szCubeFaces[] = new String[] { "res/multitexture/right.tga",
			"res/multitexture/left.tga", "res/multitexture/up.tga", "res/multitexture/down.tga", "res/multitexture/backward.tga",
			"res/multitexture/forward.tga" };

	private int cube[] = { GL_TEXTURE_CUBE_MAP_POSITIVE_X,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_X, GL_TEXTURE_CUBE_MAP_POSITIVE_Y,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_Y, GL_TEXTURE_CUBE_MAP_POSITIVE_Z,
			GL_TEXTURE_CUBE_MAP_NEGATIVE_Z };

	private TextureLoader loader = new TextureLoader();

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		glPushMatrix();
		frameCamera.gltApplyCameraTransform();

		// Position light before any other transformations
		glLightfv(GL_LIGHT0, GL_POSITION, fLightPos);

		// Draw the ground
		glColor3f(1.0f, 1.0f, 1.0f);
		DrawGround();

		// Draw inhabitants
		DrawInhabitants();

		glPopMatrix();
	}

	@Override
	public void SetupRC() {
		int iSphere;
		int i;

		// Grayish background
		glClearColor(fLowLight[0], fLowLight[1], fLowLight[2], fLowLight[3]);

		// Cull backs of polygons
		glCullFace(GL_BACK);
		glFrontFace(GL_CCW);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);

		// Setup light parameters
		glLightModelfv(GL_LIGHT_MODEL_AMBIENT, fNoLight);
		glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL, GL_SEPARATE_SPECULAR_COLOR);
		glLightfv(GL_LIGHT0, GL_AMBIENT, fLowLight);
		glLightfv(GL_LIGHT0, GL_DIFFUSE, fBrightLight);
		glLightfv(GL_LIGHT0, GL_SPECULAR, fBrightLight);
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);

		// Mostly use material tracking
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		glMateriali(GL_FRONT, GL_SHININESS, 128);

		frameCamera.gltInitFrame(); // Initialize the camera

		// Randomly place the sphere inhabitants
		for (iSphere = 0; iSphere < NUM_SPHERES; iSphere++) {
			spheres[iSphere] = new GLTFrame();
			spheres[iSphere].gltInitFrame(); // Initialize the frame

			// Pick a random location between -20 and 20 at .1 increments
			spheres[iSphere].vLocation[0] = (float) ((rand() % 400) - 200) * 0.1f;
			spheres[iSphere].vLocation[1] = 0.0f;
			spheres[iSphere].vLocation[2] = (float) ((rand() % 400) - 200) * 0.1f;
		}

		// Set up texture maps
		glEnable(GL_TEXTURE_2D);
		glGenTextures(NUM_TEXTURES, textureObjects);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

		// Load regular textures
		for (i = 0; i < CUBE_MAP; i++) {

			glBindTexture(GL_TEXTURE_2D, textureObjects[i]);

			// Load this texture map
			glTexParameteri(GL_TEXTURE_2D, GL_GENERATE_MIPMAP, GL_TRUE);
			Texture texture = loader.Load(ImageType.TGA, szTextureFiles[i]);
			glTexImage2D(GL_TEXTURE_2D, 0, GL_COMPRESSED_RGB,
					texture.getWidth(), texture.getHeight(), 0, GL_BGR_EXT,
					GL_UNSIGNED_BYTE, texture.getByteBuffer());
			texture.close();

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
					GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		}

		glActiveTexture(GL_TEXTURE1);
		glDisable(GL_TEXTURE_2D);
		glEnable(GL_TEXTURE_CUBE_MAP);
		glBindTexture(GL_TEXTURE_CUBE_MAP, textureObjects[CUBE_MAP]);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

		glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_REFLECTION_MAP);
		glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_REFLECTION_MAP);
		glTexGeni(GL_R, GL_TEXTURE_GEN_MODE, GL_REFLECTION_MAP);
		glEnable(GL_TEXTURE_GEN_S);
		glEnable(GL_TEXTURE_GEN_T);
		glEnable(GL_TEXTURE_GEN_R);

		glBindTexture(GL_TEXTURE_CUBE_MAP, textureObjects[CUBE_MAP]);

		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_TEXTURE_WRAP_R, GL_REPEAT);

		// Load Cube Map images
		for (i = 0; i < 6; i++) {

			// Load this texture map
			// glTexParameteri(GL_TEXTURE_CUBE_MAP, GL_GENERATE_MIPMAP,
			// GL_TRUE);
			Texture texture = loader.Load(ImageType.TGA, szCubeFaces[i]);
			glTexImage2D(cube[i], 0, GL_RGB8 /* GL_COMPRESSED_RGB */,
					texture.getWidth(), texture.getHeight(), 0, GL_BGR_EXT,
					GL_UNSIGNED_BYTE, texture.getByteBuffer());
			texture.close();
		}

	}

	// /////////////////////////////////////////////////////////
	// Draw the ground as a series of triangle strips
	private void DrawGround() {
		float fExtent = 20.0f;
		float fStep = 1.0f;
		float y = -0.4f;
		int iStrip, iRun;
		float s = 0.0f;
		float t = 0.0f;
		float texStep = 1.0f / (fExtent * .075f);

		glBindTexture(GL_TEXTURE_2D, textureObjects[GROUND_TEXTURE]);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

		for (iStrip = (int) -fExtent; iStrip <= fExtent; iStrip += fStep) {
			t = 0.0f;
			glBegin(GL_TRIANGLE_STRIP);

			for (iRun = (int) fExtent; iRun >= -fExtent; iRun -= fStep) {
				glTexCoord2f(s, t);
				glNormal3f(0.0f, 1.0f, 0.0f); // All Point up
				glVertex3f(iStrip, y, iRun);

				glTexCoord2f(s + texStep, t);
				glNormal3f(0.0f, 1.0f, 0.0f); // All Point up
				glVertex3f(iStrip + fStep, y, iRun);

				t += texStep;
			}
			glEnd();
			s += texStep;
		}
	}

	// /////////////////////////////////////////////////////////////////////
	// Draw random inhabitants and the rotating torus/sphere duo
	private void DrawInhabitants() {
		int i;

		yRot += 0.5f;
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

		// Draw the randomly located spheres
		glBindTexture(GL_TEXTURE_2D, textureObjects[SPHERE_TEXTURE]);
		for (i = 0; i < NUM_SPHERES; i++) {
			glPushMatrix();
			spheres[i].gltApplyActorTransform();
			gltDrawSphere(0.3f, 21, 11);
			glPopMatrix();
		}

		glPushMatrix();
		glTranslatef(0.0f, 0.1f, -2.5f);

		// Torus alone will be specular
		glMaterialfv(GL_FRONT, GL_SPECULAR, fBrightLight);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);

		// Bind to Wood, first texture
		glBindTexture(GL_TEXTURE_2D, textureObjects[WOOD_TEXTURE]);

		glActiveTexture(GL_TEXTURE1);
		glEnable(GL_TEXTURE_CUBE_MAP);

		glActiveTexture(GL_TEXTURE0);

		gltDrawTorus(0.35, 0.15, 41, 17);

		glActiveTexture(GL_TEXTURE1);
		glDisable(GL_TEXTURE_CUBE_MAP);
		glActiveTexture(GL_TEXTURE0);

		glMaterialfv(GL_FRONT, GL_SPECULAR, fNoLight);
		glPopMatrix();
	}
	
	@Override
	public void SpecialKeys() {
		if (Keyboard.next() || Keyboard.isRepeatEvent()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				gltMoveFrameForward(frameCamera, 0.1f);

			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				gltMoveFrameForward(frameCamera, -0.1f);

			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				gltRotateFrameLocalY(frameCamera, 0.1);

			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				gltRotateFrameLocalY(frameCamera, -0.1);
		}
	}
	
	public void ShutdownRC() {
		// Delete the textures
		glDeleteTextures(NUM_TEXTURES, textureObjects);
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
		MultiTexture game = new MultiTexture();
		game.start(800, 600);
	}

}
