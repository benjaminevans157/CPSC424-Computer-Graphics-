package scenegraph;

public class VectorUtils {

    /**
     * Normalize the given vector.
     * 
     * @param v The vector to be normalized.
     * @return The normalized vector.
     * @throws IllegalArgumentException if the input vector has zero magnitude.
     */
	
    public static double[] normalize(double[] v) {    	
    	double magnitude = magnitude(v);

        // Check for zero magnitude to avoid division by zero
        if (magnitude == 0.0) {
            InfoBox.infoBox("Cannot normalize a zero vector", "Illegal Argument Exception");
        	throw new IllegalArgumentException("Cannot normalize a zero vector.");
        }

        double[] normalized = new double[v.length];
        for (int i = 0; i < v.length; i++) {
            normalized[i] = v[i] / magnitude;
        }

        return normalized;
    }

    /**
     * Calculate the magnitude (length) of the vector.
     * 
     * @param v The vector whose magnitude is to be calculated.
     * @return The magnitude of the vector.
     */
    private static double magnitude(double[] v) {
        double sum = 0.0;
        for (double component : v) {
            sum += component * component;
        }
        return Math.sqrt(sum);
    }

   
}

