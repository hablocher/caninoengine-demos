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
import static org.lwjgl.opengl.EXTBgra.GL_BGR_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.EXTTextureFilterAnisotropic.GL_TEXTURE_MAX_ANISOTROPY_EXT;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DECAL;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_LINEAR_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_LINEAR;
import static org.lwjgl.opengl.GL11.GL_NEAREST_MIPMAP_NEAREST;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGetFloat;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL12.GL_CLAMP_TO_EDGE;
import static org.lwjgl.util.glu.GLU.gluBuild2DMipmaps;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.texture.Texture;
import org.aesgard.caninoengine.texture.TextureLoader;
import org.lwjgl.input.Keyboard;

public class Anisotropic extends CaninoGameEngine3D {
	private static final String mANISOTROPIC_OFF = "Anisotropic Off";
	private static final String mANISOTROPIC_FILTER = "Anisotropic Filter";
	private static final String mGL_LINEAR_MIPMAP_LINEAR = "GL_LINEAR_MIPMAP_LINEAR";
	private static final String mGL_LINEAR_MIPMAP_NEAREST = "GL_LINEAR_MIPMAP_NEAREST";
	private static final String mGL_NEAREST_MIPMAP_LINEAR = "GL_NEAREST_MIPMAP_LINEAR";
	private static final String mGL_NEAREST_MIPMAP_NEAREST = "GL_NEAREST_MIPMAP_NEAREST";
	private static final String mGL_LINEAR = "GL_LINEAR";
	private static final String mGL_NEAREST = "GL_NEAREST";
	
	// Rotation amounts
	private static float zPos = -60.0f;

	// Texture objects
	private int TEXTURE_BRICK = 0;
	private int TEXTURE_FLOOR = 1;
	private int TEXTURE_CEILING = 2;
	private int TEXTURE_COUNT = 3;

	private int[] textures = new int[TEXTURE_COUNT];

	private String[] szTextureFiles = { "res/brick.tga", "res/floor.tga",
			"res/ceiling.tga" };

	private int iMenu;
	
	private TextureLoader loader = new TextureLoader();
	
	private boolean menuChanged = false;

	@Override
	public void RenderScene() {
		float z;

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

		// Save the matrix state and do the rotations
		glPushMatrix();
		// Move object back and do in place rotation
		glTranslatef(0.0f, 0.0f, zPos);

		// Floor
		for (z = 60.0f; z >= 0.0f; z -= 10) {
			glBindTexture(GL_TEXTURE_2D, textures[TEXTURE_FLOOR]);
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(-10.0f, -10.0f, z);

			glTexCoord2f(1.0f, 0.0f);
			glVertex3f(10.0f, -10.0f, z);

			glTexCoord2f(1.0f, 1.0f);
			glVertex3f(10.0f, -10.0f, z - 10.0f);

			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(-10.0f, -10.0f, z - 10.0f);
			glEnd();

			// Ceiling
			glBindTexture(GL_TEXTURE_2D, textures[TEXTURE_CEILING]);
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(-10.0f, 10.0f, z - 10.0f);

			glTexCoord2f(1.0f, 1.0f);
			glVertex3f(10.0f, 10.0f, z - 10.0f);

			glTexCoord2f(1.0f, 0.0f);
			glVertex3f(10.0f, 10.0f, z);

			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(-10.0f, 10.0f, z);
			glEnd();

			// Left Wall
			glBindTexture(GL_TEXTURE_2D, textures[TEXTURE_BRICK]);
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(-10.0f, -10.0f, z);

			glTexCoord2f(1.0f, 0.0f);
			glVertex3f(-10.0f, -10.0f, z - 10.0f);

			glTexCoord2f(1.0f, 1.0f);
			glVertex3f(-10.0f, 10.0f, z - 10.0f);

			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(-10.0f, 10.0f, z);
			glEnd();

			// Right Wall
			glBegin(GL_QUADS);
			glTexCoord2f(0.0f, 1.0f);
			glVertex3f(10.0f, 10.0f, z);

			glTexCoord2f(1.0f, 1.0f);
			glVertex3f(10.0f, 10.0f, z - 10.0f);

			glTexCoord2f(1.0f, 0.0f);
			glVertex3f(10.0f, -10.0f, z - 10.0f);

			glTexCoord2f(0.0f, 0.0f);
			glVertex3f(10.0f, -10.0f, z);
			glEnd();
		}

		// Restore the matrix state
		glPopMatrix();
		
	    if (menuChanged) {
	    	processMenu(iMenu);
	    	menuChanged = false;
	    }

	}

	@Override
	public void SetupRC() {
		int iLoop;

		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		// Textures applied as decals, no lighting or coloring effects
		glEnable(GL_TEXTURE_2D);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_DECAL);

		// Load textures
		glGenTextures(TEXTURE_COUNT, textures);
		for (iLoop = 0; iLoop < TEXTURE_COUNT; iLoop++) {
			// Bind to next texture object
			glBindTexture(GL_TEXTURE_2D, textures[iLoop]);

			// Load texture, set filter and wrap modes
			Texture texture = loader.Load(ImageType.TGA, szTextureFiles[iLoop]);
			gluBuild2DMipmaps(GL_TEXTURE_2D, GL_RGB8, texture.getWidth(),
					texture.getHeight(), GL_BGR_EXT, GL_UNSIGNED_BYTE,
					texture.getByteBuffer());
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
					GL_LINEAR_MIPMAP_LINEAR);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
			texture.close();
		}

	}

	public void ShutdownRC() {
		glDeleteTextures(TEXTURE_COUNT, textures);
	}

	public void SpecialKeys() {
		if (Keyboard.next() || Keyboard.isRepeatEvent()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				zPos += 1.0f;

			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				zPos -= 1.0f;
		}
	}

	@Override
	public void ChangeSize(int w, int h) {
		float fAspect;

		// Prevent a divide by zero
		if (h == 0)
			h = 1;

		// Set Viewport to window dimensions
		glViewport(0, 0, w, h);

		fAspect = (float) w / (float) h;

		// Reset coordinate system
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Produce the perspective projection
		gluPerspective(90.0f, fAspect, 1, 120);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

	}

	// /////////////////////////////////////////////////////////////////////////////
	// Change texture filter for each texture object
	private void processMenu(int value) {
		int iLoop;
		float fLargest;

		for (iLoop = 0; iLoop < TEXTURE_COUNT; iLoop++) {
			glBindTexture(GL_TEXTURE_2D, textures[iLoop]);

			switch (value) {
			case 0:
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
						GL_NEAREST);
				break;

			case 1:
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
				break;

			case 2:
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
						GL_NEAREST_MIPMAP_NEAREST);
				break;

			case 3:
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
						GL_NEAREST_MIPMAP_LINEAR);
				break;

			case 4:
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
						GL_LINEAR_MIPMAP_NEAREST);
				break;

			case 5:
				glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER,
						GL_LINEAR_MIPMAP_LINEAR);
				break;

			case 6:
				fLargest = glGetFloat(GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT);
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY_EXT,
						fLargest);
				break;

			case 7:
				glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAX_ANISOTROPY_EXT,
						1.0f);
				break;
			}
		}
	}

	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			int iChoosed = iMenu;
			if (action.equalsIgnoreCase(mGL_NEAREST)) {
				iChoosed = 0;
			}
			if (action.equalsIgnoreCase(mGL_LINEAR)) {
				iChoosed = 1;
			}
			if (action.equalsIgnoreCase(mGL_NEAREST_MIPMAP_NEAREST)) {
				iChoosed = 2;
			}
			if (action.equalsIgnoreCase(mGL_NEAREST_MIPMAP_LINEAR)) {
				iChoosed = 3;
			}
			if (action.equalsIgnoreCase(mGL_LINEAR_MIPMAP_NEAREST)) {
				iChoosed = 4;
			}
			if (action.equalsIgnoreCase(mGL_LINEAR_MIPMAP_LINEAR)) {
				iChoosed = 5;
			}
			if (action.equalsIgnoreCase(mANISOTROPIC_FILTER)) {
				iChoosed = 6;
			}
			if (action.equalsIgnoreCase(mANISOTROPIC_OFF)) {
				iChoosed = 7;
			}
			if (iChoosed != iMenu) {
				iMenu = iChoosed;
				menuChanged = true;
			}
		}
	}
	
	public static void main(String[] argv) {
		Anisotropic game = new Anisotropic();
		
		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		
		JMenuItem menuItem;
		
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem(mGL_NEAREST);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(mGL_LINEAR);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(mGL_NEAREST_MIPMAP_NEAREST);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(mGL_NEAREST_MIPMAP_LINEAR);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(mGL_LINEAR_MIPMAP_NEAREST);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(mGL_LINEAR_MIPMAP_LINEAR);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(mANISOTROPIC_FILTER);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(mANISOTROPIC_OFF);
		menuItem.addActionListener(listener);
		menu.add(menuItem);

		game.addPopup(menu);
		
		game.start(800, 600);
	}

}
