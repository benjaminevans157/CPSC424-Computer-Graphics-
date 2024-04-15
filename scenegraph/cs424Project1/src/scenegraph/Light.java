package scenegraph;

import com.jogamp.opengl.GL2;

public class Light {

	private void setPointLight(GL2 gl2, int lightId, float[] rgba, float[] position) {
	    gl2.glLightfv(lightId, GL2.GL_AMBIENT, rgba, 0);
	    gl2.glLightfv(lightId, GL2.GL_DIFFUSE, rgba, 0);
	    gl2.glLightfv(lightId, GL2.GL_SPECULAR, rgba, 0);
	    gl2.glLightfv(lightId, GL2.GL_POSITION, position, 0);
	    gl2.glEnable(lightId);
	}

	private void setDirectionalLight(GL2 gl2, int lightId, float[] rgba, float[] direction) {
	    float[] position = { direction[0], direction[1], direction[2], 0.0f }; // w=0 for directional
	    setPointLight(gl2, lightId, rgba, position); 
	}

	private void setSpotLight(GL2 gl2, int lightId, float[] rgba, float[] position, float[] direction, float cutOff, float exponent) {
	    setPointLight(gl2, lightId, rgba, position);
	    gl2.glLightfv(lightId, GL2.GL_SPOT_DIRECTION, direction, 0);
	    gl2.glLightf(lightId, GL2.GL_SPOT_CUTOFF, cutOff);
	    gl2.glLightf(lightId, GL2.GL_SPOT_EXPONENT, exponent);
	}
}
