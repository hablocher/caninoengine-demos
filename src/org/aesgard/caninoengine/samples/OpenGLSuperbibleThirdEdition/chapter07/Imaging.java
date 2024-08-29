package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter07;

import static org.aesgard.caninoengine.glutil.APITranslations.glConvolutionFilter2D;
import static org.aesgard.caninoengine.glutil.APITranslations.glGetIntegerv;
import static org.aesgard.caninoengine.glutil.APITranslations.glLoadMatrixf;
import static org.lwjgl.opengl.ARBImaging.GL_COLOR_TABLE;
import static org.lwjgl.opengl.ARBImaging.GL_CONVOLUTION_2D;
import static org.lwjgl.opengl.ARBImaging.GL_HISTOGRAM;
import static org.lwjgl.opengl.ARBImaging.glColorTable;
import static org.lwjgl.opengl.ARBImaging.glHistogram;
import static org.lwjgl.opengl.GL11.GL_COLOR;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_INT;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_LUMINANCE;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_RGB;
import static org.lwjgl.opengl.GL11.GL_UNPACK_ALIGNMENT;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.GL_VIEWPORT;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawPixels;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glPixelStorei;
import static org.lwjgl.opengl.GL11.glRasterPos2i;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL11.glPixelZoom;
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

public class Imaging extends CaninoGameEngine3D {
	
	private static final String HISTOGRAM2 = "Histogram";
	private static final String SHARPEN_IMAGE = "Sharpen SpriteImage";
	private static final String EMBOSS_IMAGE = "Emboss SpriteImage";
	private static final String INVERT_COLOR = "Invert Color";
	private static final String INCREASE_CONTRAST = "Increase Contrast";
	private static final String RAW_STRETCHED_IMAGE = "Raw Stretched SpriteImage";
	private static final String SAVE_IMAGE = "Save SpriteImage";
	//////////////////////////////////////////////////////////////////
	// Module globals to save source image data
	private int iWidth, iHeight;
	private int eFormat = GL_RGB;

	// Global variable to store desired drawing mode
	private int        iRenderMode = 1;    
	private boolean    bHistogram = false;  
	
	TextureLoader loader = new TextureLoader();
	
    ByteBuffer invertTable = ByteBuffer.allocateDirect(256 * 3); // Inverted color table

    // Do a black and white scaling
    private float[] lumMat = { 0.30f, 0.30f, 0.30f, 0.0f,
                              0.59f, 0.59f, 0.59f, 0.0f,
                              0.11f, 0.11f, 0.11f, 0.0f,
                              0.0f,  0.0f,  0.0f,  1.0f };
    
//    private float[][] mSharpen = {  // Sharpen convolution kernel
//    		{0.0f, -1.0f, 0.0f},
//    		{-1.0f, 5.0f, -1.0f },
//    		{0.0f, -1.0f, 0.0f }};

    private int[] mSharpen = {  // Sharpen convolution kernel
    		0, -1, 0,
    		-1, 5, -1 ,
    		0, -1, 0 };

//    private float[][] mEmboss = {   // Emboss convolution kernel
//    		{ 2.0f, 0.0f, 0.0f },
//    		{ 0.0f, -1.0f, 0.0f },
//    		{ 0.0f, 0.0f, -1.0f }};

    private int[] mEmboss = {
            2, Integer.MIN_VALUE, 0,
            0, Integer.MAX_VALUE, 0,
            0, 0, 0
        };
    
    private int[] histoGram = new int[256];    // Storeage for histogram statistics
    
    private Texture texture;
    
	@Override
	public void RenderScene() {
	    int i;                                // Looping variable
		// O LWJGL d‡ um erro com buffer menor do que 16 int's... 
		// A nao ser que se use a opcao "org.lwjgl.util.NoChecks=true"
	    int[] iViewport = new int[16];        // Viewport
		int iLargest;				          // Largest histogram value

	    // Clear the window with current clearing color
	    glClear(GL_COLOR_BUFFER_BIT);
	    
	    // Current Raster Position always at bottom left hand corner of window
	    glRasterPos2i(0, 0);
	    glGetIntegerv(GL_VIEWPORT, iViewport);
	    glPixelZoom((float) iViewport[2] / (float)iWidth, (float) iViewport[3] / (float)iHeight); 

	    if(bHistogram)   // Collect Historgram data
	    {
	        // We are collecting luminance data, use our conversion formula
	        // instead of OpenGL's (which just adds color components together)
	        glMatrixMode(GL_COLOR);
	        glLoadMatrixf(lumMat);
	        glMatrixMode(GL_MODELVIEW);

	        // Start collecting histogram data, 256 luminance values
	        glHistogram(GL_HISTOGRAM, 256, GL_LUMINANCE, false);
	        glEnable(GL_HISTOGRAM);
	    }
	        
	    // Do image operation, depending on rendermode index
	    switch(iRenderMode)
	        {
	        case 5:     // Sharpen image
	            glConvolutionFilter2D(GL_CONVOLUTION_2D, GL_RGB, 3, 3, GL_LUMINANCE, GL_INT, mSharpen);
	            glEnable(GL_CONVOLUTION_2D);
	            break;

	        case 4:     // Emboss image
	            glConvolutionFilter2D(GL_CONVOLUTION_2D, GL_RGB, 3, 3, GL_LUMINANCE, GL_INT, mEmboss);
	            glEnable(GL_CONVOLUTION_2D);
	            glMatrixMode(GL_COLOR);
	            glLoadMatrixf(lumMat);
	            glMatrixMode(GL_MODELVIEW);
	            break;
	        
	        case 3:     // Invert SpriteImage
	            for(i = 0; i < 255; i++)
	            {
	            	invertTable.put((byte)(255 - i));
	            	invertTable.put((byte)(255 - i));
	            	invertTable.put((byte)(255 - i));
	            }
	            invertTable.rewind();
	                
	            glColorTable(GL_COLOR_TABLE, GL_RGB, 256, eFormat, GL_UNSIGNED_BYTE, invertTable);
	            glEnable(GL_COLOR_TABLE);
	            break;
	        
	        case 2:     // Brighten SpriteImage
	            glMatrixMode(GL_COLOR);
	            glScalef(1.25f, 1.25f, 1.25f);
	            glMatrixMode(GL_MODELVIEW);
	            break;
	            
	        case 1:     // Just do a plain old image copy
	        	break;
	        case 0:
	        	//gltWriteTGA("ScreenShot.tga");
	        	break;
	        default:
	                    // This line intentially left blank
	            break;
	        }
			
	    // Do the pixel draw
	    glDrawPixels(iWidth, iHeight, GL_RGB, GL_UNSIGNED_BYTE, texture.getByteBuffer());
	    
	    // Fetch and draw histogram?
	    if(bHistogram)  
	    {
	        // Read histogram data into buffer
	        //glGetHistogram(GL_HISTOGRAM, GL_TRUE, GL_LUMINANCE, GL_INT, histoGram);
	        
	        // Find largest value for scaling graph down
			iLargest = 0;
	        for(i = 0; i < 255; i++)
	            if(iLargest < histoGram[i])
	                iLargest = histoGram[i];
	        
	        // White lines
	        glColor3f(1.0f, 1.0f, 1.0f);
	        glBegin(GL_LINE_STRIP);
	            for(i = 0; i < 255; i++)
	                glVertex2f((float)i, (float)histoGram[i] / (float) iLargest * 128.0f); 
	        glEnd();

	        bHistogram = false;
	        glDisable(GL_HISTOGRAM);
	    }
	    
	        
	    // Reset everyting to default
	    glMatrixMode(GL_COLOR);
	    glLoadIdentity();
	    glMatrixMode(GL_MODELVIEW);
	    glDisable(GL_CONVOLUTION_2D);
	    glDisable(GL_COLOR_TABLE);	
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

			if (action.equalsIgnoreCase(RAW_STRETCHED_IMAGE)) {
				iRenderMode = 1;
			}
			
			if (action.equalsIgnoreCase(INCREASE_CONTRAST)) {
				iRenderMode = 2;
			}
			
			if (action.equalsIgnoreCase(INVERT_COLOR)) {
				iRenderMode = 3;
			}
			
			if (action.equalsIgnoreCase(EMBOSS_IMAGE)) {
				iRenderMode = 4;
			}
			
			if (action.equalsIgnoreCase(SHARPEN_IMAGE)) {
				iRenderMode = 5;
			}
			
			if (action.equalsIgnoreCase(HISTOGRAM2)) {
				iRenderMode = 6;
				bHistogram = true;
			}
		}
	}
	
	public static void main(String[] argv) {
		Imaging game = new Imaging();
		
		// Creating popup menu
		ActionListener listener = game.new PopupMenuActionListener();
		
		JMenuItem menuItem;
		
		JPopupMenu menu = new JPopupMenu();
		menuItem = new JMenuItem(SAVE_IMAGE);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(RAW_STRETCHED_IMAGE);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(INCREASE_CONTRAST);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(INVERT_COLOR);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(EMBOSS_IMAGE);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(SHARPEN_IMAGE);
		menuItem.addActionListener(listener);
		menu.add(menuItem);
		menuItem = new JMenuItem(HISTOGRAM2);
		menuItem.addActionListener(listener);
		menu.add(menuItem);

		game.addPopup(menu);
		
		game.start(600 ,600);
	}

}
