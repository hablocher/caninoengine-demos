package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter02;

import static org.lwjgl.opengl.GL11.*;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Simple extends CaninoGameEngine3D {

	@Override
	public void SetupRC() {
		glClearColor(0.0f, 1.0f, 1.0f, 1.0f);
	}

	@Override
	public void RenderScene() {
		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT);

	}

	@Override
	public void ChangeSize(int w, int h) {}

	public static void main(String[] argv) {
		Simple game = new Simple();
		game.start();
	}

}
