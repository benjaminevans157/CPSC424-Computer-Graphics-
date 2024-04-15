package q1;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * Routines for some 2D primitives and basic transformations.
 */
public class Drawing2D {

	/**
	 * Sets up the camera and view for 2D drawing. (Uses an orthographic
	 * projection with clip planes at -10 and 10 in all directions.)
	 */

	public static void setProjection ( GL2 gl2 ) {
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glOrtho(-10,10,-10,10,-10,10); // view volume is (-10,10) in all
		                                   // dimensions
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
	}

	/**
	 * Set the current color (r,g,b). Color values are between 0 and 1.
	 * 
	 * @param gl2
	 * @param r
	 * @param g
	 * @param b
	 */
	public static void color ( GL2 gl2, float r, float g, float b ) {
		gl2.glColor3f(r,g,b);
	}

	/**
	 * A circle centered at (0,0) with the specified radius.
	 * 
	 * @param gl2
	 * @param radius
	 *          circle radius
	 */
	public static void circle ( GL2 gl2, float radius ) {
		double numslices = 32;
		gl2.glBegin(GL2.GL_LINES);
		for ( int i = 0 ; i < numslices ; i++ ) {
			double x1 = radius * Math.cos(i * 2 * Math.PI / numslices);
			double y1 = radius * Math.sin(i * 2 * Math.PI / numslices);
			double x2 = radius * Math.cos((i + 1) * 2 * Math.PI / numslices);
			double y2 = radius * Math.sin((i + 1) * 2 * Math.PI / numslices);
			gl2.glVertex2d(x1,y1);
			gl2.glVertex2d(x2,y2);
		}
		gl2.glEnd();
	}

	/**
	 * A square with the specified side length, centered at (0,0).
	 * 
	 * @param side
	 *          side length
	 */
	public static void square ( GL2 gl2, float side ) {
		gl2.glBegin(GL2.GL_LINES);
		gl2.glVertex2f(-side / 2,-side / 2);
		gl2.glVertex2f(-side / 2,side / 2);
		gl2.glVertex2f(-side / 2,side / 2);
		gl2.glVertex2f(side / 2,side / 2);
		gl2.glVertex2f(side / 2,side / 2);
		gl2.glVertex2f(side / 2,-side / 2);
		gl2.glVertex2f(side / 2,-side / 2);
		gl2.glVertex2f(-side / 2,-side / 2);
		gl2.glEnd();
	}

	/**
	 * A triangle with the specified vertices.
	 * 
	 * @param side
	 *          side length
	 */
	public static void triangle ( GL2 gl2, float x1, float y1, float x2, float y2,
	                              float x3, float y3 ) {
		gl2.glBegin(GL2.GL_LINES);
		gl2.glVertex2f(x1,y1);
		gl2.glVertex2f(x2,y2);
		gl2.glVertex2f(x2,y2);
		gl2.glVertex2f(x3,y3);
		gl2.glVertex2f(x3,y3);
		gl2.glVertex2f(x1,y1);
		gl2.glEnd();
	}

	/**
	 * A line from (x1,y1) to (x2,y2).
	 * 
	 * @param gl2
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void line ( GL2 gl2, float x1, float y1, float x2, float y2 ) {
		gl2.glBegin(GL2.GL_LINES);
		gl2.glVertex2f(x1,y1);
		gl2.glVertex2f(x2,y2);
		gl2.glEnd();
	}

	/**
	 * A filled circle centered at (0,0) with the specified radius.
	 * 
	 * @param gl2
	 * @param radius
	 *          circle radius
	 */
	public static void filledCircle ( GL2 gl2, float radius ) {
		double numslices = 32;
		gl2.glBegin(GL2.GL_TRIANGLE_FAN);
		gl2.glVertex2f(0,0); // center of circle
		for ( int i = 0 ; i <= numslices ; i++ ) {
			gl2.glVertex2f((float) (radius * Math.cos(i * 2 * Math.PI / numslices)),
			               (float) (radius * Math.sin(i * 2 * Math.PI / numslices)));
		}
		gl2.glEnd();
	}

	/**
	 * A filled square with the specified side length, centered at (0,0).
	 * 
	 * @param side
	 *          side length
	 */
	public static void filledSquare ( GL2 gl2, float side ) {
		GLUT glut = new GLUT();
		glut.glutSolidCube(side);
	}

	/**
	 * A filled triangle with the specified vertices.
	 * 
	 * @param side
	 *          side length
	 */
	public static void filledTriangle ( GL2 gl2, float x1, float y1, float x2,
	                                    float y2, float x3, float y3 ) {
		gl2.glBegin(GL2.GL_TRIANGLES);
		gl2.glVertex2f(x1,y1);
		gl2.glVertex2f(x2,y2);
		gl2.glVertex2f(x3,y3);
		gl2.glEnd();
	}

	/**
	 * Translate by (tx,ty).
	 * 
	 * @param gl2
	 * @param tx
	 * @param ty
	 */
	public static void translate ( GL2 gl2, float tx, float ty ) {
		gl2.glTranslatef(tx,ty,0);
	}

	/**
	 * Rotate counterclockwise by theta (degrees).
	 * 
	 * @param gl2
	 * @param theta
	 */
	public static void rotate ( GL2 gl2, float theta ) {
		gl2.glRotatef(theta,0,0,1);
	}

	/**
	 * Scale by (sx,sy).
	 * 
	 * @param gl2
	 * @param sx
	 * @param sy
	 */
	public static void scale ( GL2 gl2, float sx, float sy ) {
		gl2.glScalef(sx,sy,0);
	}

	/**
	 * Save the current transformation.
	 * 
	 * @param gl2
	 */
	public static void saveTransform ( GL2 gl2 ) {
		gl2.glPushMatrix();
	}

	/**
	 * Restore the last-saved transformation.
	 * 
	 * @param gl2
	 */
	public static void restoreTransform ( GL2 gl2 ) {
		gl2.glPopMatrix();
	}
}
