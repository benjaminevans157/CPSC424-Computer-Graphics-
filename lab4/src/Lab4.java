import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class Lab4 extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener {

	/**
	 * The main program creates a window containing a panel for OpenGL drawing.
	 */

	public static void main(String[] args) {
		// create the window
		JFrame window = new JFrame("Lab4");

		// create the drawing panel
		Lab4 panel = new Lab4();

		// add the drawing panel to the window, and finish window configuration
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit program when
																// window is closed
		window.setVisible(true);

		// start animation
		FPSAnimator animator = new FPSAnimator(panel, 60, true);
		animator.start();
	}

	// --------------------------------------------------------------------------

	public Lab4() {
		// create the drawing panel, with the default OpenGL capabilities
		super(new GLCapabilities(null));
		setPreferredSize(new Dimension(700, 700));

		// specify handlers for events on the panel
		addGLEventListener(this); // OpenGL events
		addMouseListener(this); // mouse events
		addMouseMotionListener(this);
	}

	// ---------------- Methods from the GLEventListener interface --------------

	private GLU glu_ = new GLU();
	private GLUT glut_ = new GLUT();

	private float[][] materials = {
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

	// Constants to index into the materials array
	private static final int PLASTIC = 0;
	private static final int RUBBER = 1;
	private static final int COPPER = 2;
	private static final int DULL_GRAY = 3;
	private static final int EMERALD = 4;

	private float rotateX_, rotateY_ = 0; // rotation of view about the x-, y-axis

	public void display(GLAutoDrawable drawable) {
		// called when the panel needs to be drawn

		GL2 gl2 = drawable.getGL().getGL2();

		// clear the display
		gl2.glClearColor(0, 0, 0, 1); // background color black
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// set viewing transform
		glu_.gluLookAt(0, 0, 75, 0, 0, 0, 0, 1, 0);
		
		// mouse-driven rotation
		gl2.glRotatef(rotateX_, 1, 0, 0);
		gl2.glRotatef(rotateY_, 0, 1, 0);

		// TODO draw scene
		float[] lightPos = { 0.0f, 10.0f, 0.0f, 1.0f }; 
		float[] whiteLight = { 1.0f, 1.0f, 1.0f, 1.0f 	};
		float[] ambientLight = { 0.1f, 0.1f, 0.1f, 1.0f }; 

		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);

		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, whiteLight, 0);
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, whiteLight, 0);
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		
		// Point light (White)
	    float[] whiteLight1 = { 1.0f, 1.0f, 1.0f, 1.0f };
	    float[] lightPosition0 = { 3.0f, 5.0f, 5.0f, 1.0f }; // Above and a bit to the right
	    setPointLight(gl2, GL2.GL_LIGHT0, whiteLight1, lightPosition0);

	    // Directional light (Blue)
	    float[] blueLight = { 0.0f, 0.0f, 1.0f, 1.0f };
	    float[] lightDirection1 = { -0.2f, -1.0f, -0.3f }; // Slightly tilted for a more angular illumination
	    setDirectionalLight(gl2, GL2.GL_LIGHT1, blueLight, lightDirection1);

	    // Spotlight 1 (Red)
	    float[] redLight = { 1.0f, 0.0f, 0.0f, 1.0f };
	    float[] lightPosition2 = { -5.0f, 0.0f, 0.0f, 1.0f }; // To the left of the row of objects
	    float[] lightDirection2 = { 1.0f, 0.0f, 0.0f }; 
	    setSpotLight(gl2, GL2.GL_LIGHT2, redLight, lightPosition2, lightDirection2, 45.0f, 2.0f);


	    // Spotlight 2 (Green)
	    float[] greenLight = { 0.0f, 1.0f, 0.0f, 1.0f };
	    float[] lightPosition3 = { 5.0f, 0.0f, 0.0f, 1.0f }; // To the right of the row of objects
	    float[] lightDirection3 = { -1.0f, 0.0f, 0.0f }; 
	    setSpotLight(gl2, GL2.GL_LIGHT3, greenLight, lightPosition3, lightDirection3, 30.0f, 10.0f);


		// TODO: Set material properties for the sphere
		float[] matDiffuse = { 1.0f, 0.0f, 0.0f, 1.0f }; // Red color
		float[] matSpecular = { 1.0f, 1.0f, 1.0f, 1.0f }; 
		float shininess = 32.0f; 

		float startingX = -11.5f; // A starting position to the left
		float shiftAmount = 2.5f; // How much to shift each time; adjust as needed based on object size

		// Drawing sphere with PLASTIC material
		gl2.glPushMatrix();
		gl2.glTranslatef(startingX, 0, 0);
		setMaterial(gl2, PLASTIC);
		glut_.glutSolidSphere(1, 16, 16);
		gl2.glPopMatrix();

		// Drawing torus with RUBBER material
		startingX += shiftAmount;
		gl2.glPushMatrix();
		gl2.glTranslatef(startingX, 0, 0);
		setMaterial(gl2, RUBBER);
		glut_.glutSolidTorus(0.5, 1, 16, 16); 
		gl2.glPopMatrix();

		// Drawing cone with COPPER material
		startingX += shiftAmount;
		gl2.glPushMatrix();
		gl2.glTranslatef(startingX, 0, 0);
		setMaterial(gl2, COPPER);
		glut_.glutSolidCone(1, 2, 16, 16); 
		gl2.glPopMatrix();

		// Drawing cylinder with DULL_GRAY material
		startingX += shiftAmount;
		gl2.glPushMatrix();
		gl2.glTranslatef(startingX, 0, 0);
		setMaterial(gl2, DULL_GRAY);
		glut_.glutSolidCylinder(1, 2, 16, 16); 
		gl2.glPopMatrix();

		// Drawing teapot with EMERALD material
		startingX += shiftAmount;
		gl2.glPushMatrix();
		gl2.glTranslatef(startingX, 0, 0);
		setMaterial(gl2, EMERALD);
		glut_.glutSolidTeapot(1); 
		gl2.glPopMatrix();
		
		// Set a starting position for the polyhedra
		float polyhedraStartingX = startingX + 2*shiftAmount; 

		// Flat shading
		gl2.glShadeModel(GL2.GL_FLAT);
		    
		// Draw the first polyhedron with flat shading
		gl2.glPushMatrix();
		gl2.glTranslatef(polyhedraStartingX, 0, 0); 
		drawPolyhedron(gl2, Polyhedron.CUBE);  
		gl2.glPopMatrix();

		// Move the starting position for the next polyhedron
		polyhedraStartingX += shiftAmount;

		// Draw the second polyhedron
		gl2.glPushMatrix();
		gl2.glTranslatef(polyhedraStartingX, 0, 0);
		drawPolyhedron(gl2, Polyhedron.TETRAHEDRON);
		gl2.glPopMatrix();

		// Move the starting position for the third polyhedron
		polyhedraStartingX += shiftAmount;

		// Draw the third polyhedron
		gl2.glPushMatrix();
		gl2.glTranslatef(polyhedraStartingX, 0, 0);
		drawPolyhedron(gl2, Polyhedron.OCTAHEDRON);
		gl2.glPopMatrix();

		// Smooth shading
		gl2.glShadeModel(GL2.GL_SMOOTH);

		// Set a new starting position for the smooth-shaded polyhedron 
		polyhedraStartingX += shiftAmount;

		// Draw the poly mesh with smooth shading
		gl2.glPushMatrix();
		gl2.glTranslatef(polyhedraStartingX, 0, 0);  
		drawPolyMesh(gl2, Polyhedron.SOCCER_BALL);
		gl2.glPopMatrix();
	    

		gl2.glFlush();

	}

	public void init(GLAutoDrawable drawable) {
		// called once before the window is opened

		GL2 gl2 = drawable.getGL().getGL2();

		// set up the projection
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu_.gluPerspective(20, (double) getWidth() / getHeight(), 1, 100);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);

		// depth test, for 3D
		gl2.glEnable(GL2.GL_DEPTH_TEST);

		// TODO other configuration
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);
	}

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
	
	void drawPolyhedron(GL2 gl, Polyhedron poly) {
	    for (int i = 0; i < poly.faces.length; i++) {
	        gl.glBegin(GL2.GL_POLYGON);
	        for (int j = 0; j < poly.faces[i].length; j++) {
	            int vertexIndex = poly.faces[i][j];
	            double[] vertex = poly.vertices[vertexIndex];
	            double[] normal = poly.faceNormals[i];
	            gl.glNormal3d(normal[0], normal[1], normal[2]);
	            gl.glVertex3d(vertex[0], vertex[1], vertex[2]);
	        }
	        gl.glEnd();
	    }
	}

	void drawPolyMesh(GL2 gl, Polyhedron poly) {
	    float[][] vertexNormals = computeVertexNormals(gl, poly);
	    
	    for (int i = 0; i < poly.faces.length; i++) {
	        gl.glBegin(GL2.GL_POLYGON);
	        for (int j = 0; j < poly.faces[i].length; j++) {
	            int vertexIndex = poly.faces[i][j];
	            double[] vertex = poly.vertices[vertexIndex];
	            float[] normal = vertexNormals[vertexIndex];
	            gl.glNormal3f(normal[0], normal[1], normal[2]);
	            gl.glVertex3d(vertex[0], vertex[1], vertex[2]);
	        }
	        gl.glEnd();
	    }
	}

	float[][] computeVertexNormals(GL2 gl, Polyhedron poly) {
	    float[][] vertexNormals = new float[poly.vertices.length][3];
	    
	    for (int i = 0; i < poly.faces.length; i++) {
	        for (int j = 0; j < poly.faces[i].length; j++) {
	            int vertexIndex = poly.faces[i][j];
	            double[] faceNormal = poly.faceNormals[i];
	            
	            // Add the face normal to the current vertex normal
	            vertexNormals[vertexIndex][0] += faceNormal[0];
	            vertexNormals[vertexIndex][1] += faceNormal[1];
	            vertexNormals[vertexIndex][2] += faceNormal[2];
	        }
	    }
	    
	    // Normalize the vertex normals
	    for (int i = 0; i < vertexNormals.length; i++) {
	        double[] doubleArray = {vertexNormals[i][0], vertexNormals[i][1], vertexNormals[i][2]};
	        doubleArray = LinAlg.normalize(doubleArray);
	        
	        vertexNormals[i][0] = (float) doubleArray[0];
	        vertexNormals[i][1] = (float) doubleArray[1];
	        vertexNormals[i][2] = (float) doubleArray[2];
	    }
	    
	    return vertexNormals;
	}


	public void dispose(GLAutoDrawable drawable) {
		// called when the panel is being disposed
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		// called when user resizes the window
	}

	// ---------------- helper methods ----------------------------------------

	// TODO add helper methods here

	// ---------------- Methods from the MouseListener interface --------------

	private int prevX_, prevY_; // previous mouse coordinates during drag

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		prevX_ = e.getX();
		prevY_ = e.getY();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX(); // current x coord of mouse
		double dx = x - prevX_; // change in mouse coord
		rotateY_ += dx / 6;
		prevX_ = x;

		int y = e.getY(); // current y coord of mouse
		double dy = y - prevY_; // change in mouse coord
		rotateX_ += dy / 6;
		prevY_ = y;

		repaint(); // redraw the scene
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

}
