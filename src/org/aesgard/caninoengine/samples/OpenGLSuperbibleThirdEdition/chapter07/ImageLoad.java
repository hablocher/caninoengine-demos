package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter07;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDrawPixels;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glRasterPos2i;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;

import java.nio.ByteBuffer;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.aesgard.caninoengine.image.ImageType;
import org.aesgard.caninoengine.texture.Texture;
import org.aesgard.caninoengine.texture.TextureLoader;

public class ImageLoad extends CaninoGameEngine3D {
	
	TextureLoader loader = new TextureLoader();

	@Override
	public void RenderScene() {
		ByteBuffer pImage = null;
		
	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT);
	    
	    // Targa's are 1 byte aligned
		glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

		Texture texture = loader.Load(ImageType.TGA, "res/fire.tga");
		
		pImage = texture.getByteBuffer();
		
	    // Use Window coordinates to set raster position
		glRasterPos2i(0, 0);
		
	    // Draw the pixmap
	    if(pImage != null)
	        glDrawPixels(texture.getWidth(), texture.getHeight(), GL_RGB, GL_UNSIGNED_BYTE, pImage);
		
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
		
	    // Set the clipping volume
	    gluOrtho2D(0.0f, (float) w, 0.0f, (float) h);
	        
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();     

	}
	
	public static void main(String[] argv) {
		ImageLoad game = new ImageLoad();
		game.start(512,512);
	}

}
