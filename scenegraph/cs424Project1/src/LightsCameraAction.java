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
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.gl2.GLUT;

public class LightsCameraAction extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener {

	/**
	 * The main program creates a window containing a panel for OpenGL drawing.
	 */

	public static void main(String[] args) {
		// create the window
		JFrame window = new JFrame("Lab5");

		// create the drawing panel
		LightsCameraAction panel = new LightsCameraAction();

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

	public LightsCameraAction() {
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

	private float rotateX_, rotateY_ = 0; // rotation of view about the x-, y-axis

	private float lampPosX = 0;
	private float lampPosY = 0;
	private float lampPosZ = 0;
	private float cameraRotationAngle = 0.0f; // Angle of the camera rotation
	private float pedestalHeight = 3.5f;
	private float orbRadius = 0.5f;

	public void display(GLAutoDrawable drawable) {
		// called when the panel needs to be drawn

		GL2 gl2 = drawable.getGL().getGL2();

		// clear the display
		gl2.glClearColor(0, 0, 0, 1); // background color black
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// set viewing transform
		updateCameraPosition(gl2);
		updateCameraRotation();
		
		// mouse-driven rotation
		gl2.glRotatef(rotateX_, 1, 0, 0);
		gl2.glRotatef(rotateY_, 0, 1, 0);

		// Viewing transformation

		// TODO draw scene
		
	    gl2.glPushMatrix();
		// Set the material properties for the teapot
	    float[] matAmbDiff = {0f, 1f, 1f, 1f}; // Light grey ambient and diffuse reflection
	    float[] matSpec = {1f, 1f, 1f, 1f};    // White specular reflection
	    float shine = 50f; // Shineness
	    
	    gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, matAmbDiff, 0);
	    gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, matSpec, 0);
	    gl2.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, shine);
		glut_.glutSolidTeapot(1);
	    gl2.glPopMatrix();


		// Position and draw the lamp assembly (pedestal + orb)
	    gl2.glPushMatrix();
		gl2.glTranslatef(lampPosX, lampPosY, lampPosZ);
		drawLamp(gl2);
	    gl2.glPopMatrix();
	    
		// Update the position of the lamp for next frame
		updateLampPosition();
		
		
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
		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);

		lampPosX = 10f; // Starting X-position of the lamp
		lampPosY = 0f; // Starting Y-position of the lamp
		cameraRotationAngle = 45f; // Starting rotation angle
	    
	    // Define light source properties
	    float[] lightDiffuse = {1f, 1f, 1f, 1f};
	    float[] lightSpecular = {1f, 1f, 1f, 1f};
	    float[] lightAmbient = {0.1f, 0.1f, 0.1f, 1.0f}; // a bit of ambient light
	    gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lightDiffuse, 0);
	    gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, lightSpecular, 0);
	    gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lightAmbient, 0);
	    
	}
	private void drawLamp(GL2 gl2) {
	    gl2.glPushMatrix();
	    
	    // Move the lamp behind the origin in the Z-axis.
	    gl2.glTranslatef(0f, 0f, -5f);

	    // Draw Pedestal (which draws Orb)
	    drawPedestal(gl2);
	    
	    gl2.glPopMatrix();
	}

	private void drawPedestal(GL2 gl2) {
	    GLU glu = new GLU();
	    gl2.glPushMatrix();
	    
	    // Color for the pedestal
	    gl2.glColor3f(0.5f, 0.5f, 0.5f); // Grey

	    // Set material properties (ambient and diffuse reflection)
	    float[] matAmbDiff = {0.7f, 0.7f, 0.7f, 1.0f};
	    gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, matAmbDiff, 0);
	    
	    // Rotate and draw the pedestal cylinder
	    gl2.glRotatef(-90f, 1f, 0f, 0f); 
	    // Move it down so it is level with the teapot
	    gl2.glTranslatef(0f, 0f, -0.9f);
	    GLUquadric quadric = glu.gluNewQuadric();
	    glu.gluCylinder(quadric, 0.2f, 0.2f, pedestalHeight, 32, 1);
	    glu.gluDeleteQuadric(quadric);
	    
	    // Move to the top of the pedestal 
	    gl2.glTranslatef(0f, 0f, pedestalHeight);
	    
	    // Draw Orb
	    drawOrb(gl2);
	    
	    gl2.glPopMatrix();
	}

	private void drawOrb(GL2 gl2) {
	    GLUT glut = new GLUT();
	    gl2.glPushMatrix();
	    
	    // Make the orb appear yellow
	    float[] matEmission = {1f, 1f, 0f, 1f}; // Yellow emission
	    gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, matEmission, 0);
	    
	    // Draw the orb (sphere)
	    gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, new float[]{1f, 1f, 0f, 1f}, 0);
	    glut.glutSolidSphere(orbRadius, 32, 32);
	    
	    // Reset emission to avoid affecting other objects
	    float[] noEmission = {0f, 0f, 0f, 1f}; 
	    gl2.glMaterialfv(GL2.GL_FRONT, GL2.GL_EMISSION, noEmission, 0);
	    
	    // Position the light source inside the orb
	    gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, new float[]{0f, 0f, 0f, 1f}, 0);
	    
	    gl2.glPopMatrix();
	}


	private void updateLampPosition() {
		// simple oscillation in the X direction
		lampPosX = 5 * (float) Math.sin(System.currentTimeMillis() * 0.001); // Oscillate based on time
	}

	private void updateCameraPosition(GL2 gl2) {
		gl2.glLoadIdentity();
		glu_.gluLookAt(Math.cos(Math.toRadians(cameraRotationAngle)) * 20, 0,
				Math.sin(Math.toRadians(cameraRotationAngle)) * 20, // Eye (camera) position
				0, 0, 0, // Look-at point
				0, 1, 0 // Up vector
		);
	}

	// to make sure it does not exceed 360 degrees
	private void updateCameraRotation() {
		cameraRotationAngle += 0.05f;
		if (cameraRotationAngle >= 360f) {
			cameraRotationAngle -= 360f;
		}
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
