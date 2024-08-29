package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter11;

import static org.aesgard.caninoengine.glutil.APITranslations.glLightfv;
import static org.lwjgl.opengl.EXTBgra.GL_BGR_EXT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_CLAMP;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MODULATE;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RGB8;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterf;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.texture.Texture;
import org.aesgard.caninoengine.texture.TextureLoader;

public class ModelIVA extends CaninoGameEngine3D {

	private TextureLoader loader = new TextureLoader();
	private boolean menuChanged = false;
	private int menuItem = 0;

	private float yRot = 0.0f;

	public ModelIVA() {
		// TODO Auto-generated constructor stub
	}

	public ModelIVA(String title) {
		super(title);
	}

	@Override
	public void RenderScene() {
		yRot += 0.5f;

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		glPushMatrix();

		glTranslatef(0.0f, 0.0f, -15.f);
		glRotatef(-70.0f, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 0.0f, 1.0f);

		Model.DrawModel();
		
		if (Model.message != null)
			setTitle(Model.message);

		glPopMatrix();

		if (menuChanged) {
			processMenu(menuItem);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
		float fAmbLight[] = { 0.1f, 0.1f, 0.1f, 1.0f};
		float fDiffLight[] = { 1.0f, 1.0f, 1.0f, 1.0f};

		// Bluish background
		glClearColor(0.0f, 0.0f, .50f, 1.0f);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_CULL_FACE);

		// Lit texture environment
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);

		// Load the main texture
		Texture texture = loader.Load(ImageType.TGA, "res/Hummer.tga");
		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB8, texture.getWidth(),
				texture.getHeight(), 0, GL_BGR_EXT, GL_UNSIGNED_BYTE,
				texture.getByteBuffer());
		texture.close();

		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
		glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
		glEnable(GL_TEXTURE_2D);

		// Set up lighting
		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
		glLightfv(GL_LIGHT0, GL_AMBIENT, fAmbLight);
		glLightfv(GL_LIGHT0, GL_DIFFUSE, fDiffLight);

	}

	@Override
	public void ChangeSize(int w, int h) {
		float fAspect;
		float lightPos[] = { -10.0f, 100.0f, 20.0f, 1.0f };

		// Prevent a divide by zero, when window is too short
		// (you cant make a window of zero width).
		if (h == 0)
			h = 1;

		glViewport(0, 0, w, h);

		fAspect = (float) w / (float) h;

		// Reset the coordinate system before modifying
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Set the clipping volume
		gluPerspective(35.0f, fAspect, 1.0f, 50.0f);

		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();

		// Light never changes, put it here
		glLightfv(GL_LIGHT0, GL_POSITION, lightPos);
	}

	// /////////////////////////////////////////////////////////////////////////////
	// Reset flags as appropriate in response to menu selections
	private void processMenu(int value) {

		menuItem = value;

		switch (value) {
		case 1:
			break;

		case 2:
			break;

		case 3:
		default:
			break;
		}
	}

	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			int iChoosed = menuItem;
			if (action.equalsIgnoreCase("item")) {
				iChoosed = 1;
			}
			if (iChoosed != menuItem) {
				menuItem = iChoosed;
				menuChanged = true;
			}
		}
	}

	protected void SpecialKeys() {
		super.SpecialKeys();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ModelIVA game = new ModelIVA("Indexed Model Data");

		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		JMenuItem menuItem;
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem("item");
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		game.addPopup(menu);

		game.start(800, 600);
	}

}
