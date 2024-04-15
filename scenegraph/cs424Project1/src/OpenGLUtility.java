import com.jogamp.opengl.GL2;

public class OpenGLUtility {
	public static float[][] materials = {
	                         			// PLASTIC (Red plastic)
	                         			{ 0.3f, 0.0f, 0.0f, 1.0f, 
	                         					0.6f, 0.0f, 0.0f, 1.0f, 
	                         					0.8f, 0.3f, 0.3f, 1.0f, 
	                         					32.0f }, 

	                         			// RUBBER (Red rubber)
	                         			{ 0.05f, 0.0f, 0.0f, 1.0f, 
	                         						0.1f, 0.0f, 0.0f, 1.0f, 
	                         						0.5f, 0.2f, 0.2f, 1.0f, 
	                         						5.0f },

	                         			// METAL_1 (Copper)
	                         			{ 0.19125f, 0.0735f, 0.0225f, 1.0f, 
	                         						0.7038f, 0.27048f, 0.0828f, 1.0f, 
	                         						0.9f, 0.782f, 0.773f, 1.0f, 
	                         						150f }, 

	                         			// METAL_2 (Dull gray metal)
	                         			{ 0.3f, 0.3f, 0.3f, 1.0f, 
	                         						0.4f, 0.4f, 0.4f, 1.0f, 
	                         						0.8f, 0.8f, 0.8f, 1.0f, 
	                         						150f }, 

	                         			// STONE (Obsidian)
	                         			{ 0.05375f, 0.05f, 0.06625f, 1.0f, 
	                         						0.18275f, 0.17f, 0.22525f, 1.0f, 
	                         						0.332741f, 0.328634f, 0.346435f, 1.0f, 
	                         						38.4f }

	                         	};
	public static void setMaterial(GL2 gl2, int materialType) {
		// Ambient color
		gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, materials[materialType], 0);
		// Diffuse color
		gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, materials[materialType], 4);
		// Specular color
		gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, materials[materialType], 8);
		// Shininess 
		gl2.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, materials[materialType][12]);
	}

	public static void setPointLight(GL2 gl2, int lightId, float[] rgba, float[] position) {
	    gl2.glLightfv(lightId, GL2.GL_AMBIENT, rgba, 0);
	    gl2.glLightfv(lightId, GL2.GL_DIFFUSE, rgba, 0);
	    gl2.glLightfv(lightId, GL2.GL_SPECULAR, rgba, 0);
	    gl2.glLightfv(lightId, GL2.GL_POSITION, position, 0);
	    gl2.glEnable(lightId);
	}

	public static void setDirectionalLight(GL2 gl2, int lightId, float[] rgba, float[] direction) {
	    float[] position = { direction[0], direction[1], direction[2], 0.0f }; // w=0 for directional
	    setPointLight(gl2, lightId, rgba, position); 
	}

	public static void setSpotLight(GL2 gl2, int lightId, float[] rgba, float[] position, float[] direction, float cutOff, float exponent) {
	    setPointLight(gl2, lightId, rgba, position);
	    gl2.glLightfv(lightId, GL2.GL_SPOT_DIRECTION, direction, 0);
	    gl2.glLightf(lightId, GL2.GL_SPOT_CUTOFF, cutOff);
	    gl2.glLightf(lightId, GL2.GL_SPOT_EXPONENT, exponent);
	}
}
