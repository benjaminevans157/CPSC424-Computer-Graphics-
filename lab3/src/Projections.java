import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.glu.GLU;
public class Projections extends GLJPanel
    implements GLEventListener, KeyListener {
	/**
	 * The main program creates a window containing a panel for OpenGL drawing and
	 * sets up listeners to handle events on the panel.
	 */

	public static void main ( String[] args ) {
		// create the window
		JFrame window = new JFrame("Projections");

		// create the drawing panel
		Projections panel = new Projections();

		// add the drawing panel to the window, and finish window configuration
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit program when
		                                                       // window is closed
		window.setVisible(true);
	}

	// --------------------------------------------------------------------------

	public Projections () {
		// create the drawing panel, with the default OpenGL capabilities
		super(new GLCapabilities(null));
		setPreferredSize(new Dimension(700,700));

		// specify handlers for events on the panel
		addGLEventListener(this); // OpenGL events
		addKeyListener(this); // key events
	}

	// ---------------- Methods from the GLEventListener interface --------------

	private GLUT glut_ = new GLUT();
	private GLU glu = new GLU();

	private int projectionType_ = 0; // type of projection to use
	private boolean axis_ = false; // display WC axes

	public void display ( GLAutoDrawable drawable ) {
		// called when the panel needs to be drawn

		GL2 gl2 = drawable.getGL().getGL2();

		// clear the display
		gl2.glClearColor(0,0,0,1); // background color black
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		// set up the camera
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();

		// TODO projection transform
		switch ( projectionType_ ) {
	    case 0: // front
	        gl2.glOrtho(-5, 5, -5, 5, -20, 20);
	        break;
	    case 1: // side
	        gl2.glOrtho(-5, 5, -5, 5, -20, 20);
	        break;
	    case 2: // plan
	        gl2.glOrtho(-5, 5, -5, 5, -20, 20);
	        break;
	    case 3: // isometric
	        gl2.glOrtho(-7, 7, -7, 7, -20, 20);
	        break;
	    case 4: // dimetric
	        gl2.glOrtho(-7, 7, -7, 7, -20, 20);
	        break;
	    case 5: // trimetric
	        gl2.glOrtho(-7, 7, -7, 7, -20, 20);
	        break;
	    case 6: // one-point
	        gl2.glFrustum(-5, 5, -5, 5, 5, 25);
	        break;
	    case 7: // two-point
	        gl2.glFrustum(-5, 5, -5, 5, 5, 25);
	        break;
	    case 8: // three-point
	        gl2.glFrustum(-5, 5, -5, 5, 5, 25);
	        break;
	    case 9: // oblique perspective
	        gl2.glFrustum(-5, 5, -5, 5, 5, 25);
	        break;
	    case 10: // cavalier
	        // To be implemented
	        break;
	    case 11: // cabinet
	        // To be implemented
	        break;
	}

	gl2.glMatrixMode(GL2.GL_MODELVIEW);
	gl2.glLoadIdentity();

		// TODO viewing transform
	switch (projectionType_) {
    case 0: // front
        glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
        break;
    case 1: // side
        glu.gluLookAt(10, 0, 0, 0, 0, 0, 0, 1, 0);
        break;
    case 2: // plan (top)
        glu.gluLookAt(0, 10, 0, 0, 0, 0, 1, 0, 0);
        break;
    case 3: // isometric
        glu.gluLookAt(5, 5, 5, 0, 0, 0, 0, 1, 0);
        break;
    case 4: // dimetric
        glu.gluLookAt(5, 10, 5, 0, 0, 0, 0, 1, 0);
        break;
    case 5: // trimetric
        glu.gluLookAt(10, 5, 3, 0, 0, 0, 0, 1, 0);
        break;
    case 6: // one-point perspective
        glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
        break;
    case 7: // two-point perspective
        glu.gluLookAt(10, 0, 10, 0, 0, 0, 0, 1, 0);
        break;
    case 8: // three-point perspective
        glu.gluLookAt(10, 10, 10, 0, 0, 0, 0, 1, 0);
        break;
    case 9: // oblique perspective
        glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
        break;
    case 10: // cavalier
        glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
        break;
    case 11: // cabinet
        glu.gluLookAt(0, 0, 10, 0, 0, 0, 0, 1, 0);
        break;
}


		// draw scene

		// draw WC axes for reference
		if ( axis_ ) {
			gl2.glColor3f(1,0,0);
			gl2.glLineWidth(3);

			gl2.glBegin(GL2.GL_LINES);
			gl2.glVertex3f(0,0,0);
			gl2.glVertex3f(5,0,0);
			gl2.glVertex3f(0,0,0);
			gl2.glVertex3f(0,5,0);
			gl2.glVertex3f(0,0,0);
			gl2.glVertex3f(0,0,5);
			gl2.glEnd();
		}

		// draw contents of scene

		gl2.glColor3f(1,1,1);
		gl2.glLineWidth(1);

		gl2.glColor3f(1f,1f,1f);

		glut_.glutWireCube(2);

		gl2.glPushMatrix();
		gl2.glTranslatef(-2f,-2f,0f);
		glut_.glutWireCube(1);
		gl2.glPopMatrix();

		gl2.glTranslatef(2.5f,0.5f,0);
		gl2.glPushMatrix();
		gl2.glScalef(2f,3f,1f);
		glut_.glutWireCube(1);
		gl2.glPopMatrix();
		gl2.glTranslatef(0,2.25f,0);
		glut_.glutWireTeapot(1);

		gl2.glFlush();
	}

	public void init ( GLAutoDrawable drawable ) {
		// called once before the window is opened

		GL2 gl2 = drawable.getGL().getGL2();

		// other settings
		gl2.glEnable(GL2.GL_COLOR_MATERIAL);
	}

	public void dispose ( GLAutoDrawable drawable ) {
		// called when the panel is being disposed
	}

	public void reshape ( GLAutoDrawable drawable, int x, int y, int width,
	                      int height ) {
		// called when user resizes the window
	}

	// ---------------- Methods from the KeyListener interface --------------

	public void keyPressed ( KeyEvent evt ) {
		int key = evt.getKeyCode();
		boolean repaint = true;

		// handling for particular key presses
		if ( key == KeyEvent.VK_0 ) {
			projectionType_ = 0;
			System.out.println("front view");

		} else if ( key == KeyEvent.VK_1 ) {
			projectionType_ = 1;
			System.out.println("side view");

		} else if ( key == KeyEvent.VK_2 ) {
			projectionType_ = 2;
			System.out.println("plan view");

		} else if ( key == KeyEvent.VK_3 ) {
			projectionType_ = 3;
			System.out.println("isometric");

		} else if ( key == KeyEvent.VK_4 ) {
			projectionType_ = 4;
			System.out.println("dimetric");

		} else if ( key == KeyEvent.VK_5 ) {
			projectionType_ = 5;
			System.out.println("trimetric");

		} else if ( key == KeyEvent.VK_6 ) {
			projectionType_ = 6;
			System.out.println("one-point perspective");

		} else if ( key == KeyEvent.VK_7 ) {
			projectionType_ = 7;
			System.out.println("two-point perspective");

		} else if ( key == KeyEvent.VK_8 ) {
			projectionType_ = 8;
			System.out.println("three-point perspective");

		} else if ( key == KeyEvent.VK_9 ) {
			projectionType_ = 9;
			System.out.println("oblique perspective");

		} else if ( key == KeyEvent.VK_A ) {
			projectionType_ = 10;
			System.out.println("cavalier");

		} else if ( key == KeyEvent.VK_B ) {
			projectionType_ = 11;
			System.out.println("cabinet");

		} else if ( key == KeyEvent.VK_X ) {
			axis_ = !axis_;
			System.out.println("axis display " + (axis_ ? "ON" : "OFF"));
		}

		if ( repaint ) {
			repaint();
		}
	}

	public void keyReleased ( KeyEvent evt ) {}

	public void keyTyped ( KeyEvent evt ) {}

}
