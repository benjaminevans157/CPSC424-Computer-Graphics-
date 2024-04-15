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

public class Lab4 extends GLJPanel
    implements GLEventListener, MouseListener, MouseMotionListener {

	/**
	 * The main program creates a window containing a panel for OpenGL drawing.
	 */

	public static void main ( String[] args ) {
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
		FPSAnimator animator = new FPSAnimator(panel,60,true);
		animator.start();
	}

	// --------------------------------------------------------------------------

	public Lab4 () {
		// create the drawing panel, with the default OpenGL capabilities
		super(new GLCapabilities(null));
		setPreferredSize(new Dimension(700,700));

		// specify handlers for events on the panel
		addGLEventListener(this); // OpenGL events
		addMouseListener(this); // mouse events
		addMouseMotionListener(this);
	}

	// ---------------- Methods from the GLEventListener interface --------------

	private GLU glu_ = new GLU();
	private GLUT glut_ = new GLUT();

	private float rotateX_, rotateY_ = 0; // rotation of view about the x-, y-axis

	public void display ( GLAutoDrawable drawable ) {
		// called when the panel needs to be drawn

		GL2 gl2 = drawable.getGL().getGL2();

		// clear the display
		gl2.glClearColor(0,0,0,1); // background color black
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// set viewing transform
		glu_.gluLookAt(0,0,20,0,0,0,0,1,0);

		// mouse-driven rotation
		gl2.glRotatef(rotateX_,1,0,0);
		gl2.glRotatef(rotateY_,0,1,0);

		// TODO draw scene
		glut_.glutSolidSphere(1,16,16);

		gl2.glFlush();
	}

	public void init ( GLAutoDrawable drawable ) {
		// called once before the window is opened

		GL2 gl2 = drawable.getGL().getGL2();

		// set up the projection
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu_.gluPerspective(20,(double) getWidth() / getHeight(),1,100);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);

		// depth test, for 3D
		gl2.glEnable(GL2.GL_DEPTH_TEST);

		// TODO other configuration
	}

	public void dispose ( GLAutoDrawable drawable ) {
		// called when the panel is being disposed
	}

	public void reshape ( GLAutoDrawable drawable, int x, int y, int width,
	                      int height ) {
		// called when user resizes the window
	}

	// ---------------- helper methods ----------------------------------------

	// TODO add helper methods here
	
	// ---------------- Methods from the MouseListener interface --------------

	private int prevX_, prevY_; // previous mouse coordinates during drag

	@Override
	public void mouseClicked ( MouseEvent e ) {}

	@Override
	public void mouseEntered ( MouseEvent e ) {}

	@Override
	public void mouseExited ( MouseEvent e ) {}

	@Override
	public void mousePressed ( MouseEvent e ) {
		prevX_ = e.getX();
		prevY_ = e.getY();
	}

	@Override
	public void mouseReleased ( MouseEvent e ) {}

	@Override
	public void mouseDragged ( MouseEvent e ) {
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
	public void mouseMoved ( MouseEvent e ) {}

}
