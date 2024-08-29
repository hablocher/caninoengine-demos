package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter03;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POLYGON;
import static org.lwjgl.opengl.GL11.GL_POLYGON_STIPPLE;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPolygonStipple;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glVertex2f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.nio.ByteBuffer;

import org.aesgard.caninoengine.CaninoGameEngine3D;
import org.lwjgl.BufferUtils;

public class Pstipple extends CaninoGameEngine3D {
	byte[] fly  = new byte[]{
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
			(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
			(byte)0x03, (byte)0x80, (byte)0x01, (byte)0xC0, 
			(byte)0x06, (byte)0xC0, (byte)0x03, (byte)0x60,
			(byte)0x04, (byte)0x60, (byte)0x06, (byte)0x20, 
			(byte)0x04, (byte)0x30, (byte)0x0C, (byte)0x20,
			(byte)0x04, (byte)0x18, (byte)0x18, (byte)0x20, 
			(byte)0x04, (byte)0x0C, (byte)0x30, (byte)0x20,
			(byte)0x04, (byte)0x06, (byte)0x60, (byte)0x20, 
			(byte)0x44, (byte)0x03, (byte)0xC0, (byte)0x22,
			(byte)0x44, (byte)0x01, (byte)0x80, (byte)0x22, 
			(byte)0x44, (byte)0x01, (byte)0x80, (byte)0x22,
			(byte)0x44, (byte)0x01, (byte)0x80, (byte)0x22, 
			(byte)0x44, (byte)0x01, (byte)0x80, (byte)0x22,
			(byte)0x44, (byte)0x01, (byte)0x80, (byte)0x22, 
			(byte)0x44, (byte)0x01, (byte)0x80, (byte)0x22,
			(byte)0x66, (byte)0x01, (byte)0x80, (byte)0x66, 
			(byte)0x33, (byte)0x01, (byte)0x80, (byte)0xCC,
			(byte)0x19, (byte)0x81, (byte)0x81, (byte)0x98, 
			(byte)0x0C, (byte)0xC1, (byte)0x83, (byte)0x30,
			(byte)0x07, (byte)0xe1, (byte)0x87, (byte)0xe0, 
			(byte)0x03, (byte)0x3f, (byte)0xfc, (byte)0xc0,
			(byte)0x03, (byte)0x31, (byte)0x8c, (byte)0xc0, 
			(byte)0x03, (byte)0x33, (byte)0xcc, (byte)0xc0,
			(byte)0x06, (byte)0x64, (byte)0x26, (byte)0x60, 
			(byte)0x0c, (byte)0xcc, (byte)0x33, (byte)0x30,
			(byte)0x18, (byte)0xcc, (byte)0x33, (byte)0x18, 
			(byte)0x10, (byte)0xc4, (byte)0x23, (byte)0x08,
			(byte)0x10, (byte)0x63, (byte)0xC6, (byte)0x08, 
			(byte)0x10, (byte)0x30, (byte)0x0c, (byte)0x08,
			(byte)0x10, (byte)0x18, (byte)0x18, (byte)0x08, 
			(byte)0x10, (byte)0x00, (byte)0x00, (byte)0x08
		   };
	
	// Bitmap of camp fire
	byte fire[] = { 	(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, 
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
	public void SetupRC() {
		ByteBuffer buffer = BufferUtils.createByteBuffer(32*4);
		buffer.put(fire);
		buffer.flip();

		// Black background
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f );

		// Set drawing color to red
		glColor3f(1.0f, 0.0f, 0.0f);
		
		// Enable polygon stippling
		glEnable(GL_POLYGON_STIPPLE);
		
		// Specify a specific stipple pattern
		glPolygonStipple(buffer);
	}

	@Override
	public void RenderScene() {
		// Clear the window
		glClear(GL_COLOR_BUFFER_BIT);

		// Save matrix state and do the rotation
		glPushMatrix();
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);

		// Begin the stop sign shape,
		// use a standard polygon for simplicity
		glBegin(GL_POLYGON);
			glVertex2f(-20.0f, 50.0f);
			glVertex2f(20.0f, 50.0f);
			glVertex2f(50.0f, 20.0f);
			glVertex2f(50.0f, -20.0f);
			glVertex2f(20.0f, -50.0f);
			glVertex2f(-20.0f, -50.0f);
			glVertex2f(-50.0f, -20.0f);
			glVertex2f(-50.0f, 20.0f);
		glEnd();

		// Restore transformations
		glPopMatrix();
	}

	@Override
	public void ChangeSize(int w, int h) {
		float nRange = 100.0f;

		// Prevent a divide by zero
		if(h == 0)
			h = 1;

		// Set Viewport to window dimensions
	    glViewport(0, 0, w, h);

		// Reset projection matrix stack
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();

		// Establish clipping volume (left, right, bottom, top, near, far)
	    if (w <= h) 
			glOrtho (-nRange, nRange, -nRange*h/w, nRange*h/w, -nRange, nRange);
	    else 
			glOrtho (-nRange*w/h, nRange*w/h, -nRange, nRange, -nRange, nRange);

		// Reset Model view matrix stack
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}

	public static void main(String[] argv) {
		Pstipple game = new Pstipple();
		game.start();
	}

}
