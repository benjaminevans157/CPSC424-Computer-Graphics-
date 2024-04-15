package q2;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class Articulated extends GLJPanel implements GLEventListener {

	/**
	 * The main program creates a window containing a panel for OpenGL drawing and
	 * sets up listeners to handle events on the panel.
	 */

	public static void main ( String[] args ) {
		// create the window
		// TODO update window title (replace ... below)
		JFrame window = new JFrame("...");

		// create the drawing panel
		Articulated panel = new Articulated();

		// add the drawing panel to the window, and finish window configuration
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit program when
		                                                       // window is closed
		window.setVisible(true);

		// animation - calls display() 60 times per second
		// start animation
		FPSAnimator animator = new FPSAnimator(panel,60,true);
		animator.start();
	}

	// --------------------------------------------------------------------------

	public Articulated () {
		// create the drawing panel, with the default OpenGL capabilities
		super(new GLCapabilities(null));
		setPreferredSize(new Dimension(700,700));

		// specify handlers for events on the panel
		addGLEventListener(this); // OpenGL events
	}
	
  // Define instance variables
  private float angleUpperLeg = 0;
  private float angleLowerLeg = 0;
  private float angleFoot = 0;
  private boolean kick = false;
  
  private float ballX = 2; // Initialize ball's X to foot's end
  private float ballY = -10; // Initialize ball's Y to foot's vertical position
  private boolean ballMoving = false;

	// ---------------- Methods from the GLEventListener interface --------------

    public void display(GLAutoDrawable drawable) {
        GL2 gl2 = drawable.getGL().getGL2();
        gl2.glClearColor(1, 1, 1, 1);
        gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl2.glMatrixMode(GL2.GL_MODELVIEW);
        gl2.glLoadIdentity();

        // Draw scene
        drawLeg(gl2);
        drawSoccerBall(gl2);
        handleAnimation();

        gl2.glFlush();
    }

	public void init ( GLAutoDrawable drawable ) {
		// called once before the window is opened

		GL2 gl2 = drawable.getGL().getGL2();

		// set up the camera and view
		Drawing2D.setProjection(gl2);

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

	// -----------------------------------------------------------------------

	// TODO define helper methods here
	private void drawLeg(GL2 gl2) {
    Drawing2D.color(gl2, 0, 0, 0); // Black color for the leg

    // Draw upper leg
    Drawing2D.saveTransform(gl2);
    Drawing2D.rotate(gl2, angleUpperLeg);
    Drawing2D.line(gl2, 0, 0, 0, -5);
    Drawing2D.translate(gl2, 0, -5);

    // Draw lower leg
    Drawing2D.rotate(gl2, angleLowerLeg);
    Drawing2D.line(gl2, 0, 0, 0, -5);
    Drawing2D.translate(gl2, 0, -5);

    // Draw foot
    Drawing2D.rotate(gl2, angleFoot);
    Drawing2D.line(gl2, 0, 0, 2, 0);

    Drawing2D.restoreTransform(gl2);
}

private void drawSoccerBall(GL2 gl2) {
    Drawing2D.color(gl2, 0, 0, 1); // Blue color for the ball
    Drawing2D.saveTransform(gl2);
    Drawing2D.translate(gl2, ballX, ballY);
    Drawing2D.filledCircle(gl2, 1);
    Drawing2D.restoreTransform(gl2);
}

private void handleAnimation() {
    if(kick) {
        angleUpperLeg += 0.5;
        angleLowerLeg -= 1;
        angleFoot -= 0.5;

        // Start moving the ball when the foot angle indicates a "kick"
        if(angleFoot <= -5) {
            ballMoving = true;
        }

        if(angleUpperLeg >= 10) {
            kick = false;
        }
    } else {
        angleUpperLeg -= 0.5;
        angleLowerLeg += 1;
        angleFoot += 0.5;

        if(angleUpperLeg <= -10) {
            kick = true;
        }
    }

    if(ballMoving) {
        ballX += 0.1;  // Adjust this value to control ball's speed on X-axis
        ballY += 0.1;  // Adjust this value to control ball's ascent/descent
    }
}

}
