import java.awt.Dimension;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.gl2.GLUT;

public class Primitives extends GLJPanel implements GLEventListener {

	/**
	 * The main program creates a window containing a panel for OpenGL drawing and
	 * sets up listeners to handle events on the panel.
	 */

	public static void main(String[] args) {
		// create the window
		JFrame window = new JFrame("Primitives");

		// create the drawing panel
		Primitives panel = new Primitives();

		// add the drawing panel to the window, and finish window configuration
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit program when
																// window is closed
		window.setVisible(true);
	}

	// --------------------------------------------------------------------------

	public Primitives() {
		// create the drawing panel, with the default OpenGL capabilities
		super(new GLCapabilities(null));
		setPreferredSize(new Dimension(700, 700));

		// specify handlers for events on the panel
		addGLEventListener(this); // OpenGL events
	}

	// ---------------- Methods from the GLEventListener interface --------------

	public void display(GLAutoDrawable drawable) {
		// ... other initialization code

		GL2 gl2 = drawable.getGL().getGL2();
		GLUT glut = new GLUT();

		// clear the display
		gl2.glClearColor(0.7f, 0.7f, 0.7f, 1); // background color gray
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// Set the camera view
		GLU glu = new GLU();
		glu.gluLookAt(10, 10, 25, 10, 10, 10, 0, 1, 0);

		// Closer to the camera (Lower Y, More negative Z):

		// Cube
		drawPedestal(gl2, 7, 6, 15);
		drawPrimitive(gl2, glut, 0, 7, 6.5f, 15);

		// Sphere
		drawPedestal(gl2, 13, 7, 15);
		drawPrimitive(gl2, glut, 1, 13, 7.5f, 15);

		// Midway (Mid Y, Close to origin in Z):

		// Cone
		drawPedestal(gl2, 3, 8, 10);
		drawPrimitive(gl2, glut, 2, 3, 9, 10);

		// Torus
		drawPedestal(gl2, 17, 9, 10);
		drawPrimitive(gl2, glut, 3, 17, 9.5f, 10);

		// Further from the camera (Higher Y, More positive Z):

		// Tetrahedron
		drawPedestal(gl2, 8, 10, 5);
		drawPrimitive(gl2, glut, 4, 8, 9.7f, 5);

		// Dodecahedron
		drawPedestal(gl2, 13, 11, 0);
		drawPrimitive(gl2, glut, 5, 13, 11.5f, 0);

		// Octahedron
		drawPedestal(gl2, 3, 12, -10);
		drawPrimitive(gl2, glut, 6, 3, 12.5f, -10);

		// Farthest from the camera (Highest Y, Furthest negative Z):

		// Cylinder
		drawPedestal(gl2, 18, 13, -10);
		drawPrimitive(gl2, glut, 7, 18, 14, -10);

		// Dipyramid
		drawPedestal(gl2, 10, 14, -20);
		drawPrimitive(gl2, glut, 8, 10, 14.5f, -20);



		gl2.glFlush();
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();

		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		GLU glu = new GLU();
		glu.gluPerspective(60, 1, 1, 100); // 60-degree field of view, 1:1 aspect ratio, z-near 1, z-far 100

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// other settings
		gl2.glEnable(GL2.GL_COLOR_MATERIAL);
		gl2.glEnable(GL2.GL_DEPTH_TEST);
	}

	public void dispose(GLAutoDrawable drawable) {
		// called when the panel is being disposed
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// called when user resizes the window
	}

	private void drawPedestal(GL2 gl2, float x, float y, float z) {
		GLU glu = new GLU();
		gl2.glPushMatrix();

		gl2.glColor3f(1f, 1f, 1f); // White
		gl2.glTranslatef(x, y, z);

		// Rotate the cylinder along the x-axis by 90 degrees
		gl2.glRotatef(90, 1.0f, 0.0f, 0.0f);

		GLUquadric quadric = glu.gluNewQuadric();
		glu.gluCylinder(quadric, 1.5f, 1.5f, 3, 32, 1);
		glu.gluDeleteQuadric(quadric);

		gl2.glPopMatrix();
	}

	private void drawPrimitive(GL2 gl2, GLUT glut, int shapeType, float x, float y, float z) {
		GLU glu = new GLU();
		gl2.glPushMatrix();
		gl2.glTranslatef(x, y, z); // Translated y by 3 to put it on top of the pedestal
		switch (shapeType) {
		case 0: // Cube
			gl2.glColor3f(1, 0, 0); // Red
			gl2.glTranslatef(0, 1f, 0);
			glut.glutSolidCube(1);
			gl2.glColor3f(0, 0, 0);
			glut.glutWireCube(1.05f);
			break;
		case 1: // Sphere
			gl2.glColor3f(0, 1, 0); // Green
			gl2.glTranslatef(0, 1f, 0);
			glut.glutSolidSphere(1, 20, 20);
			gl2.glColor3f(0, 0, 0);
			glut.glutWireSphere(1.05f, 20, 20);
			break;
		case 2: // Cone
			gl2.glColor3f(0, 0, 1); // Blue
			gl2.glTranslatef(0, -1f, 0);
			gl2.glRotatef(-90, 1, 0, 0);
			glut.glutSolidCone(1, 2, 20, 20);
			gl2.glColor3f(0, 0, 0);
			glut.glutWireCone(1.05f, 2.1f, 20, 20);
			break;
		case 3: // Torus
			gl2.glColor3f(1, 1, 0); // Yellow
			gl2.glTranslatef(0, 1f, 0);
			glut.glutSolidTorus(0.5, 1, 20, 20);
			gl2.glColor3f(0, 0, 0);
			glut.glutWireTorus(0.52f, 1.05f, 20, 20);
			break;
		case 4: // Tetrahedron
			gl2.glColor3f(0, 1, 1); // Teal
			gl2.glTranslatef(0, 1f, 0); 
			glut.glutSolidTetrahedron();
			gl2.glColor3f(0, 0, 0);
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl2.glColor3f(0, 0, 0);
			glut.glutSolidTetrahedron();
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			break;
		case 5: // Dodecahedron
			gl2.glColor3f(1, 0, 1); // Purple
			gl2.glTranslatef(0, 1f, 0); 
			glut.glutSolidDodecahedron();
			gl2.glColor3f(0, 0, 0);
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl2.glColor3f(0, 0, 0);
			glut.glutSolidDodecahedron();
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			break;
		case 6: // Octahedron
			gl2.glColor3f(.6f, .6f, .1f); // Orange
			gl2.glTranslatef(0, 1f, 0); 
			glut.glutSolidOctahedron();
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl2.glColor3f(0, 0, 0);
			glut.glutSolidOctahedron();
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			break;
		case 7: // Cylinder
			gl2.glColor3f(.2f, .3f, .6f); // Something
			GLUquadric quadric = glu.gluNewQuadric();
			glu.gluCylinder(quadric, 1, 1, 3, 23, 23);
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl2.glColor3f(0, 0, 0); // Black wireframe
			glu.gluCylinder(quadric, 1, 1, 3, 23, 23);
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			break;
		case 8: // Custom Dipyramid
			gl2.glTranslatef(0, 1f, 0); 
			drawDipyramid(gl2);
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_LINE);
			gl2.glColor3f(0, 0, 0);
			drawDipyramid(gl2);
			gl2.glPolygonMode(GL2.GL_FRONT_AND_BACK, GL2.GL_FILL);
			break;
		}
		gl2.glPopMatrix();
	}

	private void drawDipyramid(GL2 gl2) {
		gl2.glColor3f(0.5f, 0.2f, 0.8f); // Purple color
		float[][] vertices = { { 0, 1, 0 }, // Top vertex
				{ 0, 0, 0 }, // Base center vertex
				{ 1, 0, 0 }, // Pentagon vertices...
				{ (float) Math.cos(2 * Math.PI / 5), 0, (float) Math.sin(2 * Math.PI / 5) },
				{ (float) Math.cos(4 * Math.PI / 5), 0, (float) Math.sin(4 * Math.PI / 5) },
				{ (float) Math.cos(6 * Math.PI / 5), 0, (float) Math.sin(6 * Math.PI / 5) },
				{ (float) Math.cos(8 * Math.PI / 5), 0, (float) Math.sin(8 * Math.PI / 5) }, { 0, -1, 0 } // Bottom vertex
		};

		int[][] faces = { { 0, 2, 3 }, { 0, 3, 4 }, { 0, 4, 5 }, { 0, 5, 6 }, { 0, 6, 2 }, { 7, 2, 3 }, { 7, 3, 4 },
				{ 7, 4, 5 }, { 7, 5, 6 }, { 7, 6, 2 } };
		float[] dipyramidVertices = {
				// Base vertices
				0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f,
				// Top vertices
				0.5f, 1.0f, 0.5f, 0.5f, -1.0f, 0.5f, };

		int[] dipyramidIndices = {
				// Base
				0, 1, 2, 3,
				// Sides
				0, 4, 1, 4, 2, 4, 3, 4,
				0, 5, 1, 5, 2, 5, 3, 5 };

		// Draw each face
		for (int[] face : faces) {
			gl2.glBegin(GL2.GL_TRIANGLES);
			for (int vertexIndex : face) {
				gl2.glVertex3f(vertices[vertexIndex][0], vertices[vertexIndex][1], vertices[vertexIndex][2]);
			}
			gl2.glEnd();
		}
		// Wireframe for Dipyramid
	    gl2.glColor3f(0, 0, 0);
	    gl2.glBegin(GL2.GL_LINES);
	    for (int i = 4; i < dipyramidIndices.length; i+=2) {
	        gl2.glVertex3f(dipyramidVertices[dipyramidIndices[i]*3], dipyramidVertices[dipyramidIndices[i]*3 + 1], dipyramidVertices[dipyramidIndices[i]*3 + 2]);
	        gl2.glVertex3f(dipyramidVertices[dipyramidIndices[i+1]*3], dipyramidVertices[dipyramidIndices[i+1]*3 + 1], dipyramidVertices[dipyramidIndices[i+1]*3 + 2]);
	    }
		gl2.glEnd();
	}
}