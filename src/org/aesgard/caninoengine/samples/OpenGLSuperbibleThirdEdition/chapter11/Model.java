package org.aesgard.caninoengine.samples.OpenGLSuperbibleThirdEdition.chapter11;

import static org.aesgard.caninoengine.glutil.APITranslations.fabs;
import static org.aesgard.caninoengine.glutil.APITranslations.glDrawElements;
import static org.aesgard.caninoengine.util.BufferUtils.array2FloatBuffer;
import static org.lwjgl.opengl.GL11.GL_NORMAL_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_COORD_ARRAY;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_SHORT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glNormalPointer;
import static org.lwjgl.opengl.GL11.glTexCoordPointer;
import static org.lwjgl.opengl.GL11.glVertexPointer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Formatter;

public final class Model {

	// //////////////////////////////////////////////////////////////
	// These are hard coded for this particular example
	private static int[] uiIndexes = new int[2248 * 3]; // Maximum number of
															// indexes
	private static float[][] vVerts = new float[2248 * 3][3]; // (Worst case
																// scenario)
	private static float[][] vText = new float[2248 * 3][2];
	private static float[][] vNorms = new float[2248 * 3][3];
	
	private static int iLastIndex = 0; // Number of indexes actually used

	private static short[][] face_indicies = new short[2248][9];
	private static float[][] vertices = new float[1254][3];
	private static float[][] normals = new float[1227][3];
	private static float[][] textures = new float[2141][2];

	private static int iIndexes = 0;
	
	private static boolean modelLoaded = false;
	
	public static String message = "";

	private static void loadModel(String fileName) throws Exception {
		InputStream is = Model.class.getClassLoader().getResourceAsStream(fileName);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		StringBuffer model = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null)
			model.append(line);
		br.close();
		is.close();

		String[] arrays = model.toString().split("#");

		String s_face_indicies = arrays[0];
		String s_vertices = arrays[1];
		String s_normals = arrays[2];
		String s_textures = arrays[3];

		arrays = null;

		String[] a_face_indicies = s_face_indicies.split("[|]");
		String[] a_vertices = s_vertices.split("[|]");
		String[] a_normals = s_normals.split("[|]");
		String[] a_textures = s_textures.split("[|]");

		s_face_indicies = null;
		s_vertices = null;
		s_normals = null;
		s_textures = null;

		for (int i = 0; i < a_face_indicies.length; i++) {
			String[] values = a_face_indicies[i].split(",");
			for (int j = 0; j < values.length; j++) {
				face_indicies[i][j] = Short.parseShort(values[j].trim());
			}
		}

		for (int i = 0; i < a_vertices.length; i++) {
			String[] values = a_vertices[i].split(",");
			for (int j = 0; j < values.length; j++) {
				vertices[i][j] = Float.parseFloat(values[j]);
			}
		}

		for (int i = 0; i < a_normals.length; i++) {
			String[] values = a_normals[i].split(",");
			for (int j = 0; j < values.length; j++) {
				normals[i][j] = Float.parseFloat(values[j]);
			}
		}

		for (int i = 0; i < a_textures.length; i++) {
			String[] values = a_textures[i].split(",");
			for (int j = 0; j < values.length; j++) {
				textures[i][j] = Float.parseFloat(values[j]);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////
	// Compare two floating point values and return true if they are
	// close enough together to be considered the same.
	private static boolean IsSame(float x, float y, float epsilon) {
		if (fabs(x - y) < epsilon)
			return true;

		return false;
	}

	// /////////////////////////////////////////////////////////////
	// Goes through the arrays and looks for duplicate verticies
	// that can be shared. This expands the original array somewhat
	// and returns the number of true unique verticies that now
	// populates the vVerts array.
	private static int IndexTriangles() {
		int iFace, iPoint, iMatch;
		float e = 0.000001f; // How small a difference to equate

		// LOOP THROUGH all the faces
		int iIndexCount = 0;
		for (iFace = 0; iFace < 2248; iFace++) {
			for (iPoint = 0; iPoint < 3; iPoint++) {
				// Search for match
				for (iMatch = 0; iMatch < iLastIndex; iMatch++) {
	                // If Vertex is the same...
	                if(IsSame(vertices[face_indicies[iFace][iPoint]][0], vVerts[iMatch][0], e) &&
	                   IsSame(vertices[face_indicies[iFace][iPoint]][1], vVerts[iMatch][1], e) &&
	                   IsSame(vertices[face_indicies[iFace][iPoint]][2], vVerts[iMatch][2], e) &&
	                   
	                   // AND the Normal is the same...
	                   IsSame(normals[face_indicies[iFace][iPoint+3]][0], vNorms[iMatch][0], e) &&
	                   IsSame(normals[face_indicies[iFace][iPoint+3]][1], vNorms[iMatch][1], e) &&
	                   IsSame(normals[face_indicies[iFace][iPoint+3]][2], vNorms[iMatch][2], e) &&
	                   
	                   // And Texture is the same...
	                   IsSame(textures[face_indicies[iFace][iPoint+6]][0], vText[iMatch][0], e) &&
	                   IsSame(textures[face_indicies[iFace][iPoint+6]][1], vText[iMatch][1], e))
	                {
	                    // Then add the index only
	                    uiIndexes[iIndexCount] = iMatch;
	                    iIndexCount++;
	                    break;
	                }
				}

				// No match found, add this vertex to the end of our list, and
				// update the index array
				if (iMatch == iLastIndex) {
					// Add data and new index
					vVerts[iMatch][0] = vertices[face_indicies[iFace][iPoint]][0];
					vVerts[iMatch][1] = vertices[face_indicies[iFace][iPoint]][1];
					vVerts[iMatch][2] = vertices[face_indicies[iFace][iPoint]][2];

					vNorms[iMatch][0] = normals[face_indicies[iFace][iPoint+3]][0];
					vNorms[iMatch][1] = normals[face_indicies[iFace][iPoint+3]][1];
					vNorms[iMatch][2] = normals[face_indicies[iFace][iPoint+3]][2];

					vText[iMatch][0] = textures[face_indicies[iFace][iPoint+6]][0];
					vText[iMatch][1] = textures[face_indicies[iFace][iPoint+6]][1];
					
	                //memcpy(vVerts[iMatch], vertices[face_indicies[iFace][iPoint]], sizeof(float) * 3);
	                //memcpy(vNorms[iMatch], normals[face_indicies[iFace][iPoint+3]], sizeof(float) * 3);
	                //memcpy(vText[iMatch],  textures[face_indicies[iFace][iPoint+6]], sizeof(float) * 2);
					uiIndexes[iIndexCount] = iLastIndex;
					iIndexCount++;
					iLastIndex++;
				}
			}
		}
		return iIndexCount;
	}

	// ///////////////////////////////////////////
	// Function to stitch the triangles together
	// and draw the ship
	public static void DrawModel() {
		if (!modelLoaded) {
			try {
				loadModel("res/hummer.mdl");
			} catch (Exception e) {
				e.printStackTrace();
			}
			modelLoaded = true;
		}
		// The first time this is called, reindex the triangles. Report the
		// results
		// in the window title
		if (iIndexes == 0) {
			iIndexes = IndexTriangles();
			Formatter fmt = new Formatter();
			message = fmt.format("Verts = %d Indexes = %d", iLastIndex, iIndexes).toString();
		}

		// Use vertices, normals, and texture coordinates
		glEnableClientState(GL_VERTEX_ARRAY);
		glEnableClientState(GL_NORMAL_ARRAY);
		glEnableClientState(GL_TEXTURE_COORD_ARRAY);

		// Here's where the data is now
		glVertexPointer(3, 0, array2FloatBuffer(vVerts));
		glNormalPointer(0, array2FloatBuffer(vNorms));
		glTexCoordPointer(2, 0, array2FloatBuffer(vText));

		// Draw them
		glDrawElements(GL_TRIANGLES, iIndexes, GL_UNSIGNED_SHORT, uiIndexes);
	}

}
