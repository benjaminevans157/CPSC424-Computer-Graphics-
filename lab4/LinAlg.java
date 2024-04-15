
/**
 * Some vector operations.
 */
public class LinAlg {

	/**
	 * Compute the length of a vector.
	 * 
	 * @param v
	 *          vector
	 * @return the length of v
	 */
	public static double length ( double[] v ) {
		double sum = 0;
		for ( int ctr = 0 ; ctr < v.length ; ctr++ ) {
			sum += v[ctr] * v[ctr];
		}
		return Math.sqrt(sum);
	}

	/**
	 * Normalize a vector. A new vector is returned; v is not changed.
	 * 
	 * @param v
	 *          vector
	 * @return a unit vector pointing in the same direction as v
	 */
	public static double[] normalize ( double[] v ) {
		double len = length(v);
		double[] unit = new double[v.length];
		for ( int ctr = 0 ; ctr < v.length ; ctr++ ) {
			unit[ctr] = v[ctr] / len;
		}
		return unit;
	}

}
