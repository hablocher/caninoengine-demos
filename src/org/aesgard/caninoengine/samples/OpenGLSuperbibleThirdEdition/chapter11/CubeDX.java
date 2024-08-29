package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter11;
import static org.aesgard.caninoengine.glutil.APITranslations.glColor3ub;
import static org.aesgard.caninoengine.glutil.APITranslations.glDrawElements;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertexPointer;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_FRONT_AND_BACK;
import static org.lwjgl.opengl.GL11.GL_LINE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPolygonMode;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class CubeDX extends CaninoGameEngine3D {

	private boolean menuChanged = false;
	private int menuItem = 0;
	
	// Array containing the six vertices of the cube
	static float corners[] = { -25.0f, 25.0f, 25.0f, // 0 // Front of cube
	                              25.0f, 25.0f, 25.0f, // 1
	                              25.0f, -25.0f, 25.0f,// 2
	                             -25.0f, -25.0f, 25.0f,// 3
	                             -25.0f, 25.0f, -25.0f,// 4  // Back of cube
	                              25.0f, 25.0f, -25.0f,// 5
	                              25.0f, -25.0f, -25.0f,// 6
	                             -25.0f, -25.0f, -25.0f };// 7

	// Array of indexes to create the cube
	static byte indexes[] = { 0, 1, 2, 3,	// Front Face
	                             4, 5, 1, 0,	// Top Face
	                             3, 2, 6, 7,	// Bottom Face
	                             5, 4, 7, 6,	// Back Face
								 1, 5, 6, 2,	// Right Face
								 4, 0, 3, 7 };	// Left Face

	public CubeDX() {
		// TODO Auto-generated constructor stub
	}
	
	public CubeDX(String title) {
		super(title);
	}

	@Override
	public void RenderScene() {
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	    // Make the cube a wire frame
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		// Save the matrix state
	    glMatrixMode(GL_MODELVIEW);
	    glPushMatrix();
	    glTranslatef(0.0f, 0.0f, -200.0f);
	    
	    // Rotate about x and y axes
	    glRotatef(xRot, 1.0f, 0.0f, 0.0f);
	    glRotatef(yRot, 0.0f, 0.0f, 1.0f);

	    // Enable and specify the vertex array
	    glEnableClientState(GL_VERTEX_ARRAY);
	    glVertexPointer(3, GL_FLOAT, 0, corners);
	       
	    // Using Drawarrays
		glDrawElements(GL_QUADS, 24, GL_UNSIGNED_BYTE, indexes);
	  
	    glPopMatrix();

		if (menuChanged) {
			processMenu(menuItem);
			menuChanged = false;
		}
	}

	@Override
	public void SetupRC() {
	    // Black background
	    glClearColor(1.0f, 1.0f, 1.0f, 1.0f );

		glColor3ub(0,0,0);

	}

	@Override
	public void ChangeSize(int w, int h) {
	    // Prevent a divide by zero
	    if(h == 0)
	        h = 1;

	    // Set Viewport to window dimensions
	    glViewport(0, 0, w, h);

	    // Reset coordinate system
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    // Establish clipping volume (left, right, bottom, top, near, far)
	    gluPerspective(35.0f, (float)w/(float)h, 1.0f, 1000.0f);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();


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
		CubeDX game = new CubeDX();

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
