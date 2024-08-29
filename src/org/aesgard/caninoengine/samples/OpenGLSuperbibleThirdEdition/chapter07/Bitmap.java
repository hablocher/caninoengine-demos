package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter07;

import static org.aesgard.caninoengine.util.BufferUtils.array2ByteBuffer;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBitmap;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRasterPos2i;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Bitmap extends CaninoGameEngine3D {

	// Bitmap of camp fire
	private byte fire[] = { (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
					   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
					   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
					   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
					   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
					   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
					   (byte)0x00, (byte)0x00, (byte)0x00, (byte)0xc0,
					   (byte)0x00, (byte)0x00, (byte)0x01, (byte)0xf0,
					   (byte)0x00, (byte)0x00, (byte)0x07, (byte)0xf0,
					   (byte)0x0f, (byte)0x00, (byte)0x1f, (byte)0xe0,
					   (byte)0x1f, (byte)0x80, (byte)0x1f, (byte)0xc0,
					   (byte)0x0f, (byte)0xc0, (byte)0x3f, (byte)0x80,	
					   (byte)0x07, (byte)0xe0, (byte)0x7e, (byte)0x00,
					   (byte)0x03, (byte)0xf0, (byte)0xff, (byte)0x80,
					   (byte)0x03, (byte)0xf5, (byte)0xff, (byte)0xe0,
					   (byte)0x07, (byte)0xfd, (byte)0xff, (byte)0xf8,
					   (byte)0x1f, (byte)0xfc, (byte)0xff, (byte)0xe8,
					   (byte)0xff, (byte)0xe3, (byte)0xbf, (byte)0x70, 
					   (byte)0xde, (byte)0x80, (byte)0xb7, (byte)0x00,
					   (byte)0x71, (byte)0x10, (byte)0x4a, (byte)0x80,
					   (byte)0x03, (byte)0x10, (byte)0x4e, (byte)0x40,
					   (byte)0x02, (byte)0x88, (byte)0x8c, (byte)0x20,
					   (byte)0x05, (byte)0x05, (byte)0x04, (byte)0x40,
					   (byte)0x02, (byte)0x82, (byte)0x14, (byte)0x40,
					   (byte)0x02, (byte)0x40, (byte)0x10, (byte)0x80, 
					   (byte)0x02, (byte)0x64, (byte)0x1a, (byte)0x80,
					   (byte)0x00, (byte)0x92, (byte)0x29, (byte)0x00,
					   (byte)0x00, (byte)0xb0, (byte)0x48, (byte)0x00,
					   (byte)0x00, (byte)0xc8, (byte)0x90, (byte)0x00,
					   (byte)0x00, (byte)0x85, (byte)0x10, (byte)0x00,
					   (byte)0x00, (byte)0x03, (byte)0x00, (byte)0x00,
					   (byte)0x00, (byte)0x00, (byte)0x10, (byte)0x00 };


	@Override
	public void RenderScene() {
		int x, y;
		
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT);
	        
		// Set color to white
		glColor3f(1.0f, 1.0f, 1.0f);
		
		// Loop through 16 rows and columns
		for(y = 0; y < 16; y++)
			{
			// Set raster position for this "square"
			glRasterPos2i(0, y * 32);
			for(x = 0; x < 16; x++)
				// Draw the "fire" bitmap, advance raster position
				glBitmap(32, 32, 0.0f, 0.0f, 32.0f, 0.0f, array2ByteBuffer(fire));
			}

	}

	@Override
	public void SetupRC() {
	    // Black background
	    glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
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

		// Psuedo window coordinates
		gluOrtho2D(0.0f, (float) w, 0.0f, (float) h);

	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();    

	}
	
	public static void main(String[] argv) {
		Bitmap game = new Bitmap();
		game.start(512,512);
	}

}
