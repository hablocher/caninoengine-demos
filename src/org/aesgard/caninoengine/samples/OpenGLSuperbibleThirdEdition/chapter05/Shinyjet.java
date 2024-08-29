package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter05;

import static org.aesgard.caninoengine.glutil.APITranslations.glColor3ub;
import static org.aesgard.caninoengine.glutil.APITranslations.glNormal3fv;
import static org.aesgard.caninoengine.glutil.APITranslations.glVertex3fv;
import static org.aesgard.caninoengine.glutil.VectorMath.gltGetNormalVector;
import static org.aesgard.caninoengine.util.BufferUtils.array2FloatBuffer;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_AMBIENT_AND_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_CCW;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_COLOR_MATERIAL;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_FRONT;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_SHININESS;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorMaterial;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrontFace;
import static org.lwjgl.opengl.GL11.glLight;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMaterial;
import static org.lwjgl.opengl.GL11.glMateriali;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glNormal3f;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRotatef;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.util.glu.Project.gluPerspective;

import org.aesgard.caninoengine.CaninoGameEngine3D;

public class Shinyjet extends CaninoGameEngine3D {
	
	@Override
	public void RenderScene() {
		float[] vNormal = new float[3];	// Storeage for calculated surface normal

		// Clear the window with current clearing color
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		// Save the matrix state and do the rotations
		glPushMatrix();
		glRotatef(xRot, 1.0f, 0.0f, 0.0f);
		glRotatef(yRot, 0.0f, 1.0f, 0.0f);


		// Nose Cone - Points straight down
	        // Set material color
		glColor3ub(128, 128, 128);
		glBegin(GL_TRIANGLES);
            glNormal3f(0.0f, -1.0f, 0.0f);
			glNormal3f(0.0f, -1.0f, 0.0f);
			glVertex3f(0.0f, 0.0f, 60.0f);
			glVertex3f(-15.0f, 0.0f, 30.0f);
			glVertex3f(15.0f,0.0f,30.0f);
		
            // Verticies for this panel
            {
	            float[][] vPoints = {{ 15.0f, 0.0f,  30.0f},
	                                { 0.0f,  15.0f, 30.0f},
	                                { 0.0f,  0.0f,  60.0f}};

	            // Calculate the normal for the plane
	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
				glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }	

            {
	            float[][] vPoints = {{ 0.0f, 0.0f, 60.0f },
	                                { 0.0f, 15.0f, 30.0f },
	                                { -15.0f, 0.0f, 30.0f }};

	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }

            // Body of the Plane ////////////////////////
            {
	            float[][] vPoints = {{ -15.0f, 0.0f, 30.0f },
	                                { 0.0f, 15.0f, 30.0f },
							        { 0.0f, 0.0f, -56.0f }};

	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                	
            {
	            float[][] vPoints = {{ 0.0f, 0.0f, -56.0f },
	                                { 0.0f, 15.0f, 30.0f },
	                                { 15.0f,0.0f,30.0f }};

	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                			    
			glNormal3f(0.0f, -1.0f, 0.0f);
			glVertex3f(15.0f,0.0f,30.0f);
			glVertex3f(-15.0f, 0.0f, 30.0f);
			glVertex3f(0.0f, 0.0f, -56.0f);
	    
            ///////////////////////////////////////////////
            // Left wing
            // Large triangle for bottom of wing
            {
	            float[][] vPoints = {{ 0.0f,2.0f,27.0f },
                                    { -60.0f, 2.0f, -8.0f },
                                    { 60.0f, 2.0f, -8.0f }};

			    gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
			    glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            {
	            float[][] vPoints = {{ 60.0f, 2.0f, -8.0f},
									{0.0f, 7.0f, -8.0f},
									{0.0f,2.0f,27.0f }};
            
	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            {
	            float[][] vPoints = {{60.0f, 2.0f, -8.0f},
									{-60.0f, 2.0f, -8.0f},
									{0.0f,7.0f,-8.0f }};

			    gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
			    glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            {
                float[][] vPoints = {{0.0f,2.0f,27.0f},
                                    {0.0f, 7.0f, -8.0f},
                                    {-60.0f, 2.0f, -8.0f}};

	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            // Tail section///////////////////////////////
            // Bottom of back fin
			glNormal3f(0.0f, -1.0f, 0.0f);
			glVertex3f(-30.0f, -0.50f, -57.0f);
			glVertex3f(30.0f, -0.50f, -57.0f);
			glVertex3f(0.0f,-0.50f,-40.0f);

            {
	            float[][] vPoints = {{ 0.0f,-0.5f,-40.0f },
									{30.0f, -0.5f, -57.0f},
									{0.0f, 4.0f, -57.0f }};

	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            {
	            float[][] vPoints = {{ 0.0f, 4.0f, -57.0f },
									{ -30.0f, -0.5f, -57.0f },
									{ 0.0f,-0.5f,-40.0f }};

	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }

            {
	            float[][] vPoints = {{ 30.0f,-0.5f,-57.0f },
									{ -30.0f, -0.5f, -57.0f },
									{ 0.0f, 4.0f, -57.0f }};

	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            {
	            float[][] vPoints = {{ 0.0f,0.5f,-40.0f },
									{ 3.0f, 0.5f, -57.0f },
									{ 0.0f, 25.0f, -65.0f }};
	
	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            {
                float[][] vPoints = {{ 0.0f, 25.0f, -65.0f },
									{ -3.0f, 0.5f, -57.0f},
									{ 0.0f,0.5f,-40.0f }};

                gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
                glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
            {
	            float[][] vPoints = {{ 3.0f,0.5f,-57.0f },
									{ -3.0f, 0.5f, -57.0f },
									{ 0.0f, 25.0f, -65.0f }};
	
	            gltGetNormalVector(vPoints[0], vPoints[1], vPoints[2], vNormal);
	            glNormal3fv(vNormal);
				glVertex3fv(vPoints[0]);
				glVertex3fv(vPoints[1]);
				glVertex3fv(vPoints[2]);
            }
	                
        glEnd();
	                
	    	// Restore the matrix state
		glPopMatrix();
	}

	@Override
	public void SetupRC() {
		   // Light values and coordinates
	    float  ambientLight[] = { 0.3f, 0.3f, 0.3f, 1.0f };
	    float  diffuseLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };
	    float  specular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	    float  specref[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	    glEnable(GL_DEPTH_TEST);	// Hidden surface removal
	    glFrontFace(GL_CCW);		// Counter clock-wise polygons face out
	    glEnable(GL_CULL_FACE);		// Do not calculate inside of jet

	    // Enable lighting
	    glEnable(GL_LIGHTING);

	    // Setup and enable light 0
	    glLight(GL_LIGHT0,GL_AMBIENT,array2FloatBuffer(ambientLight));
	    glLight(GL_LIGHT0,GL_DIFFUSE,array2FloatBuffer(diffuseLight));
	    glLight(GL_LIGHT0,GL_SPECULAR,array2FloatBuffer(specular));
	    glEnable(GL_LIGHT0);

	    // Enable color tracking
	    glEnable(GL_COLOR_MATERIAL);
		
	    // Set Material properties to follow glColor values
	    glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);

	    // All materials hereafter have full specular reflectivity
	    // with a high shine
	    glMaterial(GL_FRONT, GL_SPECULAR,array2FloatBuffer(specref));
	    glMateriali(GL_FRONT, GL_SHININESS, 128);
	    
	    // Light blue background
	    glClearColor(0.0f, 0.0f, 1.0f, 1.0f );
	}

	@Override
	public void ChangeSize(int w, int h) {
	    float fAspect;
	    float lightPos[] = { -50.f, 50.0f, 100.0f, 1.0f };

	    // Prevent a divide by zero
	    if(h == 0)
	        h = 1;

	    // Set Viewport to window dimensions
	    glViewport(0, 0, w, h);

	    // Reset coordinate system
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();

	    fAspect = (float) w / (float) h;
	    gluPerspective(45.0f, fAspect, 1.0f, 225.0f);
	    
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	    
	    glLight(GL_LIGHT0,GL_POSITION,array2FloatBuffer(lightPos));
	    glTranslatef(0.0f, 0.0f, -150.0f);
	}
	
	public static void main(String[] argv) {
		Shinyjet game = new Shinyjet();
		game.start();
	}

}
