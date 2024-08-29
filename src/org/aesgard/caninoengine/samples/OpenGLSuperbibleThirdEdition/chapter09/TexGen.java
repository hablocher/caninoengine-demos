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
 import static org.lwjgl.opengl.EXTBgra.*;
 */
import static org.aesgard.caninoengine.glutil.APITranslations.glDepthMask;
import static org.aesgard.caninoengine.glutil.APITranslations.glGenTextures;
import static org.aesgard.caninoengine.glutil.APITranslations.glTexGenfv;
import static org.aesgard.caninoengine.glutil.APITranslations.gltDrawTorus;
import static org.lwjgl.opengl.EXTBgra.GL_BGR_EXT;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DECAL;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_EYE_LINEAR;
import static org.lwjgl.opengl.GL11.GL_EYE_PLANE;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_OBJECT_LINEAR;
import static org.lwjgl.opengl.GL11.GL_OBJECT_PLANE;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_REPEAT;
import static org.lwjgl.opengl.GL11.GL_S;
import static org.lwjgl.opengl.GL11.GL_SPHERE_MAP;
import static org.lwjgl.opengl.GL11.GL_T;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_GEN_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_GEN_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_GEN_T;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexGeni;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_COMPRESSED_RGB;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.texture.Texture;
import org.aesgard.caninoengine.texture.TextureLoader;

public class TexGen extends CaninoGameEngine3D {
	private static final String SPHERE_MAP = "Sphere Map";
	private static final String EYE_LINEAR = "Eye Linear";
	private static final String OBJECT_LINEAR = "Object Linear";
	private int[] toTextures = new int[2]; // Two texture objects
	private int iRenderMode = 3; // Sphere Mapped is default

	private TextureLoader loader = new TextureLoader();
	
	private boolean menuChanged = false;

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Switch to orthographic view for background drawing
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		gluOrtho2D(0.0f, 1.0f, 0.0f, 1.0f);

		glMatrixMode(GL_MODELVIEW);
		glBindTexture(GL_TEXTURE_2D, toTextures[1]); // Background texture

		// We will specify texture coordinates
		glDisable(GL_TEXTURE_GEN_S);
		glDisable(GL_TEXTURE_GEN_T);

		// No depth buffer writes for background
		glDepthMask(GL_FALSE);

		// Background image
		glBegin(GL_QUADS);
		glTexCoord2f(0.0f, 0.0f);
		glVertex2f(0.0f, 0.0f);

		glTexCoord2f(1.0f, 0.0f);
		glVertex2f(1.0f, 0.0f);

		glTexCoord2f(1.0f, 1.0f);
		glVertex2f(1.0f, 1.0f);

		glTexCoord2f(0.0f, 1.0f);
		glVertex2f(0.0f, 1.0f);
		glEnd();

		// Back to 3D land
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);

		// Turn texgen and depth writing back on
		glEnable(GL_TEXTURE_GEN_S);
		glEnable(GL_TEXTURE_GEN_T);
		glDepthMask(GL_TRUE);

		// May need to swtich to stripe texture
		if (iRenderMode != 3)
			glBindTexture(GL_TEXTURE_2D, toTextures[0]);

		// Save the matrix state and do the rotations
		glPushMatrix();
		glTranslatef(0.0f, 0.0f, -2.0f);
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);

		// Draw the tours
		gltDrawTorus(0.35, 0.15, 61, 37);

		// Restore the matrix state
		glPopMatrix();
		
	    if (menuChanged) {
	    	processMenu(iRenderMode);
	    	menuChanged = false;
	    }

	}

	@Override
	public void SetupRC() {
		glEnable(GL_DEPTH_TEST); // Hidden surface removal
		glFrontFace(GL_CCW); // Counter clock-wise polygons face out
		glEnable(GL_CULL_FACE); // Do not calculate inside of jet

		// White background
		glClearColor(1.0f, 1.0f, 1.0f, 1.0f);

		// Decal texture environment
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL);

		// Two textures
		glGenTextures(2, toTextures);

		// /////////////////////////////////////////
		// Load the main texture
		glBindTexture(GL_TEXTURE_2D, toTextures[0]);
		Texture texture = loader.Load(ImageType.TGA, "res/stripes.tga");
		glTexImage2D(GL_TEXTURE_2D, 0, GL_COMPRESSED_RGB, texture.getWidth(),
				texture.getHeight(), 0, GL_BGR_EXT, GL_UNSIGNED_BYTE,
				texture.getByteBuffer());
		texture.close();

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glEnable(GL_TEXTURE_2D);

		// /////////////////////////////////////////
		// Load environment map
		glBindTexture(GL_TEXTURE_2D, toTextures[1]);
		texture = loader.Load(ImageType.TGA, "res/Environment.tga");
		glTexImage2D(GL_TEXTURE_2D, 0, GL_COMPRESSED_RGB, texture.getWidth(),
				texture.getHeight(), 0, GL_BGR_EXT, GL_UNSIGNED_BYTE,
				texture.getByteBuffer());
		texture.close();

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glEnable(GL_TEXTURE_2D);

		// Turn on texture coordiante generation
		glEnable(GL_TEXTURE_GEN_S);
		glEnable(GL_TEXTURE_GEN_T);

		// Sphere Map will be the default
		glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
		glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);

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

	    fAspect = (float) w / (float) h;
	    gluPerspective(45.0f, fAspect, 1.0f, 225.0f);
	    
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();

	}

	// /////////////////////////////////////////////////////////////////////////////
	// Reset flags as appropriate in response to menu selections
	private void processMenu(int value) {
		// Projection plane
		float zPlane[] = { 0.0f, 0.0f, 1.0f, 0.0f };

		// Store render mode
		iRenderMode = value;

		// Set up textgen based on menu selection
		switch (value) {
		case 1:
			// Object Linear
			glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_OBJECT_LINEAR);
			glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_OBJECT_LINEAR);
			glTexGenfv(GL_S, GL_OBJECT_PLANE, zPlane);
			glTexGenfv(GL_T, GL_OBJECT_PLANE, zPlane);
			break;

		case 2:
			// Eye Linear
			glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
			glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_EYE_LINEAR);
			glTexGenfv(GL_S, GL_EYE_PLANE, zPlane);
			glTexGenfv(GL_T, GL_EYE_PLANE, zPlane);
			break;

		case 3:
		default:
			// Sphere Map
			glTexGeni(GL_S, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
			glTexGeni(GL_T, GL_TEXTURE_GEN_MODE, GL_SPHERE_MAP);
			break;
		}
	}
	
	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			int iChoosed = iRenderMode;
			if (action.equalsIgnoreCase(OBJECT_LINEAR)) {
				iChoosed = 1;
			}
			if (action.equalsIgnoreCase(EYE_LINEAR)) {
				iChoosed = 2;
			}
			if (action.equalsIgnoreCase(SPHERE_MAP)) {
				iChoosed = 3;
			}
			if (iChoosed != iRenderMode) {
				iRenderMode = iChoosed;
				menuChanged = true;
			}
		}
	}

	public static void main(String[] argv) {
		TexGen game = new TexGen();
		
		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		JMenuItem menuItem;
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem(OBJECT_LINEAR);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(EYE_LINEAR);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(SPHERE_MAP);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		game.addPopup(menu);
		
		game.start(800, 600);
	}

}
