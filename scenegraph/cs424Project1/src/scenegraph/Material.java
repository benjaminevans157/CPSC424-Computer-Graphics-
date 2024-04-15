package scenegraph;

import com.jogamp.opengl.GL2;

public class Material {

	private void setMaterial(GL2 gl2, int materialType) {
		// Ambient color
		gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, materials[materialType], 0);
		// Diffuse color
		gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materials[materialType], 4);
		// Specular color
		gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, materials[materialType], 8);
		// Shininess 
		gl2.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, materials[materialType][12]);
	}
	
}
