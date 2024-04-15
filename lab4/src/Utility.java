
/**
 * Some miscellaneous operations.
 */
public class Utility {

	/**
	 * Return a 2D array as a 1D array containing the elements in row-major order.
	 */
	public static double[] flatten ( double[][] array2d ) {
		int n = 0;
		for ( int i = 0 ; i < array2d.length ; i++ ) {
			n += array2d[i].length;
		}

		double[] array = new double[n];
		for ( int i = 0, k = 0 ; i < array2d.length ; i++ ) {
			for ( int j = 0 ; j < array2d[i].length ; j++ ) {
				array[k] = array2d[i][j];
				k++;
			}
		}

		return array;
	}

	/**
	 * Return a 2D array as a 1D array containing the elements in row-major order.
	 */
	public static int[] flatten ( int[][] array2d ) {
		int n = 0;
		for ( int i = 0 ; i < array2d.length ; i++ ) {
			n += array2d[i].length;
		}

		int[] array = new int[n];
		for ( int i = 0, k = 0 ; i < array2d.length ; i++ ) {
			for ( int j = 0 ; j < array2d[i].length ; j++ ) {
				array[k] = array2d[i][j];
				k++;
			}
		}

		return array;
	}

	/**
	 * Return a 2D array as a 1D array containing the elements in row-major order.
	 */
	public static float[] flatten ( float[][] array2d ) {
		int n = 0;
		for ( int i = 0 ; i < array2d.length ; i++ ) {
			n += array2d[i].length;
		}

		float[] array = new float[n];
		for ( int i = 0, k = 0 ; i < array2d.length ; i++ ) {
			for ( int j = 0 ; j < array2d[i].length ; j++ ) {
				array[k] = array2d[i][j];
				k++;
			}
		}

		return array;
	}

}
