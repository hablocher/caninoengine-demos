package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter06;

import static org.aesgard.caninoengine.glutil.APITranslations.cos;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertex2fv;
import static org.aesgard.caninoengine.glutil.APITranslations.rand;
import static org.aesgard.caninoengine.glutil.APITranslations.sin;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NICEST;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POINTS;
import static org.lwjgl.opengl.GL11.GL_POINT_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_POINT_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_POLYGON_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_POLYGON_SMOOTH_HINT;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glHint;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPointSize;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Smoother extends CaninoGameEngine3D {

	private static final String ANTIALIASED_RENDERING_2 = "Antialiased Rendering 2";
	private static final String ANTIALIASED_RENDERING_1 = "Antialiased Rendering 1";
	// Array of small stars
	private int SMALL_STARS = 100;
	float[][] vSmallStars= new float[SMALL_STARS][2];

	private int MEDIUM_STARS = 40;
	float[][] vMediumStars = new float[MEDIUM_STARS][2];

	private int LARGE_STARS = 15;
	float[][] vLargeStars = new float[LARGE_STARS][2];

	private int SCREEN_X  =  800;
	private int SCREEN_Y  =  600;
	
	private int iAAMode = 0;

	@Override
	public void RenderScene() {
	    int i;                  // Loop variable
	    float x = 700.0f;     // Location and radius of moon
	    float y = 500.0f;
	    float r = 50.0f;
	    float angle = 0.0f;   // Another looping variable
			        
	    // Clear the window
	    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	         
	    // Everything is white
	    glColor3f(1.0f, 1.0f, 1.0f);
	    
	    // Draw small stars
	    glPointSize(1.0f);
	    glBegin(GL_POINTS);
	        for(i = 0; i < SMALL_STARS; i++)
	            glVertex2fv(vSmallStars[i]);
	    glEnd();
	        
	    // Draw medium sized stars
	    glPointSize(3.05f);
	    glBegin(GL_POINTS);
	        for(i = 0; i< MEDIUM_STARS; i++)
	            glVertex2fv(vMediumStars[i]);
	    glEnd();
	        
	    // Draw largest stars
	    glPointSize(5.5f);
	    glBegin(GL_POINTS);
	        for(i = 0; i < LARGE_STARS; i++)
	            glVertex2fv(vLargeStars[i]);
	    glEnd();
	        
	    // Draw the "moon"
	    glBegin(GL_TRIANGLE_FAN);
	        glVertex2f(x, y);
	        for(angle = 0; angle < 2.0f * 3.141592f; angle += 0.1f)
	            glVertex2f(x + (float)cos(angle) * r, y + (float)sin(angle) * r);
	            glVertex2f(x + r, y);
	    glEnd();

	    // Draw distant horizon
	    glLineWidth(3.5f);
	    glBegin(GL_LINE_STRIP);
	        glVertex2f(0.0f, 25.0f);
	        glVertex2f(50.0f, 100.0f);
	        glVertex2f(100.0f, 25.0f);
	        glVertex2f(225.0f, 125.0f);
	        glVertex2f(300.0f, 50.0f);
	        glVertex2f(375.0f, 100.0f);
	        glVertex2f(460.0f, 25.0f);
	        glVertex2f(525.0f, 100.0f);
	        glVertex2f(600.0f, 20.0f);
	        glVertex2f(675.0f, 70.0f);
	        glVertex2f(750.0f, 25.0f);
	        glVertex2f(800.0f, 90.0f);    
	    glEnd();
	    
	    switch (iAAMode) {
		case 1:
            // Turn on antialiasing, and give hint to do the best
            // job possible.
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_BLEND);
            glEnable(GL_POINT_SMOOTH);
            glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);
            glEnable(GL_LINE_SMOOTH);
            glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
            glEnable(GL_POLYGON_SMOOTH);
            glHint(GL_POLYGON_SMOOTH_HINT, GL_NICEST);
            iAAMode = 0;
			break;

		case 2:
            // Turn off blending and all smoothing
            glDisable(GL_BLEND);
            glDisable(GL_LINE_SMOOTH);
            glDisable(GL_POINT_SMOOTH);
            glDisable(GL_POLYGON_SMOOTH);
            iAAMode = 0;
			break;
		}

	}

	@Override
	public void SetupRC() {
	    int i;
        
	    // Populate star list
	    for(i = 0; i < SMALL_STARS; i++)
	    {
	        vSmallStars[i][0] = (float)(rand() % SCREEN_X);
	        vSmallStars[i][1] = (float)(rand() % (SCREEN_Y - 100))+100.0f;
	    }
	            
	    // Populate star list
	    for(i = 0; i < MEDIUM_STARS; i++)
	    {
	        vMediumStars[i][0] = (float)(rand() % SCREEN_X * 10)/10.0f;
	        vMediumStars[i][1] = (float)(rand() % (SCREEN_Y - 100))+100.0f;
	    }

	    // Populate star list
	    for(i = 0; i < LARGE_STARS; i++)
	    {
	        vLargeStars[i][0] = (float)(rand() % SCREEN_X*10)/10.0f;
	        vLargeStars[i][1] = (float)(rand() % (SCREEN_Y - 100)*10.0f)/ 10.0f +100.0f;
	    }
	                   
	    // Black background
	    glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

	    // Set drawing color to white
	    glColor3f(0.0f, 0.0f, 0.0f);
	}
	
	
	@Override
	public void ChangeSize(int w, int h) {
	    // Prevent a divide by zero
	    if(h == 0)
	        h = 1;

	    // Set Viewport to window dimensions
	    glViewport(0, 0, w, h);

	    // Reset projection matrix stack
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    // Establish clipping volume (left, right, bottom, top, near, far)
	    gluOrtho2D(0.0f, SCREEN_X, 0.0f, SCREEN_Y);

	    // Reset Model view matrix stack
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();

	}
	
	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if (action.equalsIgnoreCase(ANTIALIASED_RENDERING_1)) {
				iAAMode = 1;
			}

			if (action.equalsIgnoreCase(ANTIALIASED_RENDERING_2)) {
				iAAMode = 2;
			}
		}
	}
	
	public static void main(String[] argv) {
		Smoother game = new Smoother();
		
		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		
		JMenuItem menuItem;
		
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem(ANTIALIASED_RENDERING_1);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(ANTIALIASED_RENDERING_2);
		menuItem.addActionListener(listener);
		menu.add(menuItem);

		game.addPopup(menu);
		
		game.start();
	}

}
