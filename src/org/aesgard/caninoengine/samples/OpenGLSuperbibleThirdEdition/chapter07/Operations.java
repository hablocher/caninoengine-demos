package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter07;
/*
import static org.aesgard.caninoengine.glutil.APITranslations.*;
import static org.aesgard.caninoengine.glutil.MatrixMath.*;
import static org.aesgard.caninoengine.glutil.VectorMath.*;
import static org.aesgard.caninoengine.util.Constants.*;
import static org.aesgard.caninoengine.util.BufferUtils.*;
import static org.lwjgl.opengl.ARBMultisample.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.util.glu.Util.*;
import static org.lwjgl.opengl.ARBImaging.*;
 */
import static org.aesgard.caninoengine.glutil.APITranslations.glGetIntegerv;
import static org.aesgard.caninoengine.glutil.APITranslations.glPixelMapfv;
import static org.lwjgl.opengl.GL11.GL_BLUE_SCALE;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_GREEN_SCALE;
import static org.lwjgl.opengl.GL11.GL_LUMINANCE;
import static org.lwjgl.opengl.GL11.GL_MAP_COLOR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PIXEL_MAP_B_TO_B;
import static org.lwjgl.opengl.GL11.GL_PIXEL_MAP_G_TO_G;
import static org.lwjgl.opengl.GL11.GL_PIXEL_MAP_R_TO_R;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RED_SCALE;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawPixels;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glPixelTransferf;
import static org.lwjgl.opengl.GL11.glPixelTransferi;
import static org.lwjgl.opengl.GL11.glPixelZoom;
import static org.lwjgl.opengl.GL11.glRasterPos2i;
import static org.lwjgl.opengl.GL11.glReadPixels;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteBuffer;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.texture.Texture;
import org.aesgard.caninoengine.texture.TextureLoader;
import org.lwjgl.BufferUtils;

public class Operations extends CaninoGameEngine3D {
	
	private static final String INVERT_COLORS = "Invert Colors";
	private static final String BLACK_AND_WHITE = "Black and White";
	private static final String JUST_BLUE_CHANNEL = "Just Blue Channel";
	private static final String JUST_GREEN_CHANNEL = "Just Green Channel";
	private static final String JUST_RED_CHANNEL = "Just Red Channel";
	private static final String ZOOM_PIXELS = "Zoom Pixels";
	private static final String FLIP_PIXELS = "Flip Pixels";
	private static final String DRAW_PIXELS = "Draw Pixels";
	private static final String SAVE_IMAGE = "Save SpriteImage";
	//////////////////////////////////////////////////////////////////
	// Module globals to save source image data
	private int iWidth, iHeight;
	private int eFormat = GL_RGB;;

	// Global variable to store desired drawing mode
	private int    iRenderMode = 1; 
    
    private Texture texture;
    
	TextureLoader loader = new TextureLoader();
    
	@Override
	public void RenderScene() {
	    int[] iViewport= new int[16];
	    ByteBuffer pModifiedBytes=  null;
	    float[] invertMap = new float[256];
	    int i;
	    
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT);
	    
	    // Current Raster Position always at bottom left hand corner of window
	    glRasterPos2i(0, 0);
	        
	    // Do image operation, depending on rendermode index
	    switch(iRenderMode)
	        {
	        case 2:     // Flip the pixels
	            glPixelZoom(-1.0f, -1.0f);
	            glRasterPos2i(iWidth, iHeight);
	            break;
	            
	        case 3:     // Zoom pixels to fill window
	            glGetIntegerv(GL_VIEWPORT, iViewport);
	            glPixelZoom((float) iViewport[2] / (float)iWidth, (float) iViewport[3] / (float)iHeight); 
	            break;
	            
	        case 4:     // Just Red
	            glPixelTransferf(GL_RED_SCALE, 1.0f);         
	            glPixelTransferf(GL_GREEN_SCALE, 0.0f);
	            glPixelTransferf(GL_BLUE_SCALE, 0.0f); 
	            break;
	            
	        case 5:     // Just Green
	            glPixelTransferf(GL_RED_SCALE, 0.0f);         
	            glPixelTransferf(GL_GREEN_SCALE, 1.0f);
	            glPixelTransferf(GL_BLUE_SCALE, 0.0f); 
	            break;
	            
	        case 6:     // Just Blue
	            glPixelTransferf(GL_RED_SCALE, 0.0f);         
	            glPixelTransferf(GL_GREEN_SCALE, 0.0f);
	            glPixelTransferf(GL_BLUE_SCALE, 1.0f); 
	            break;
	            
	        case 7:     // Black & White, more tricky
	            // First draw image into color buffer
	            glDrawPixels(iWidth, iHeight, eFormat, GL_UNSIGNED_BYTE, texture.getByteBuffer());
	            
	            // Allocate space for the luminance map
	            pModifiedBytes = BufferUtils.createByteBuffer(iWidth * iHeight);
	            
	            // Scale colors according to NSTC standard
	            glPixelTransferf(GL_RED_SCALE, 0.3f);         
	            glPixelTransferf(GL_GREEN_SCALE, 0.59f);
	            glPixelTransferf(GL_BLUE_SCALE, 0.11f);
	            
	            // Read pixles into buffer (scale above will be applied)
	            glReadPixels(0,0,iWidth, iHeight, GL_LUMINANCE, GL_UNSIGNED_BYTE, pModifiedBytes);
	            
	            // Return color scaling to normal
	            glPixelTransferf(GL_RED_SCALE, 1.0f);
	            glPixelTransferf(GL_GREEN_SCALE, 1.0f);
	            glPixelTransferf(GL_BLUE_SCALE, 1.0f);
	            break;
	            
	        case 8:     // Invert colors
	            invertMap[0] = 1.0f;
	            for(i = 1; i < 256; i++)
	                invertMap[i] = 1.0f - (1.0f / 255.0f * (float)i);
	                
	            glPixelMapfv(GL_PIXEL_MAP_R_TO_R, 255, invertMap);
	            glPixelMapfv(GL_PIXEL_MAP_G_TO_G, 255, invertMap);
	            glPixelMapfv(GL_PIXEL_MAP_B_TO_B, 255, invertMap);
	            glPixelTransferi(GL_MAP_COLOR, GL_TRUE);
	            break;

	        case 1:     // Just do a plain old image copy
	        default:
	                    // This line intentially left blank
	            break;
	        }
			
	    // Do the pixel draw
	    if(pModifiedBytes == null)
	        glDrawPixels(iWidth, iHeight, eFormat, GL_UNSIGNED_BYTE, texture.getByteBuffer());
	    else
	        glDrawPixels(iWidth, iHeight, GL_LUMINANCE, GL_UNSIGNED_BYTE, pModifiedBytes);


	    // Reset everyting to default
	    glPixelTransferi(GL_MAP_COLOR, GL_FALSE);
	    glPixelTransferf(GL_RED_SCALE, 1.0f);
	    glPixelTransferf(GL_GREEN_SCALE, 1.0f);
	    glPixelTransferf(GL_BLUE_SCALE, 1.0f);
	    glPixelZoom(1.0f, 1.0f);                    // No Pixel Zooming
	}

	@Override
	public void SetupRC() {
	    // Black background
	    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		
	    // Load the horse image
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
		
		texture = loader.Load(ImageType.TGA, "res/horse.tga");
		
		iWidth = texture.getWidth();
		iHeight = texture.getHeight();
	}
		
	@Override
	public void ChangeSize(int w, int h) {
	    // Prevent a divide by zero, when window is too short
	    // (you cant make a window of zero width).
	    if(h == 0)
	        h = 1;

	    glViewport(0, 0, w, h);
	        
		// Reset the coordinate system before modifying
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
		
	    // Set the clipping volume
	    gluOrtho2D(0.0f, (float) w, 0.0f, (float) h);
	        
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	private class PopupMenuActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String action = e.getActionCommand();
			if (action.equalsIgnoreCase(SAVE_IMAGE)) {
				iRenderMode = 0;
			}

			if (action.equalsIgnoreCase(DRAW_PIXELS)) {
				iRenderMode = 1;
			}
			
			if (action.equalsIgnoreCase(FLIP_PIXELS)) {
				iRenderMode = 2;
			}
			
			if (action.equalsIgnoreCase(ZOOM_PIXELS)) {
				iRenderMode = 3;
			}
			
			if (action.equalsIgnoreCase(JUST_RED_CHANNEL)) {
				iRenderMode = 4;
			}
			
			if (action.equalsIgnoreCase(JUST_GREEN_CHANNEL)) {
				iRenderMode = 5;
			}
			
			if (action.equalsIgnoreCase(JUST_BLUE_CHANNEL)) {
				iRenderMode = 6;
			}

			if (action.equalsIgnoreCase(BLACK_AND_WHITE)) {
				iRenderMode = 7;
			}
			
			if (action.equalsIgnoreCase(INVERT_COLORS)) {
				iRenderMode = 8;
			}
		}
	}
	
	public static void main(String[] argv) {
		Operations game = new Operations();
		
		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		
		JMenuItem menuItem;
		
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem(SAVE_IMAGE);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(DRAW_PIXELS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(FLIP_PIXELS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(ZOOM_PIXELS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(JUST_RED_CHANNEL);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(JUST_GREEN_CHANNEL);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(JUST_BLUE_CHANNEL);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(BLACK_AND_WHITE);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(INVERT_COLORS);
		menuItem.addActionListener(listener);
		menu.add(menuItem);

		game.addPopup(menu);
		
		game.start(800 ,600);
	}

}
