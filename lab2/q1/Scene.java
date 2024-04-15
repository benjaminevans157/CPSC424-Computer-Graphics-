package q1;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class Scene extends GLJPanel implements GLEventListener {

    // Instance variables for animation
    private float earthRotationAngle = 0.0f;
    private float moonRotationAngle = 0.0f;

    /**
     * The main program creates a window containing a panel for OpenGL drawing and
     * sets up listeners to handle events on the panel.
     */

    public static void main(String[] args) {
        JFrame window = new JFrame("Solar System Simulation");

        Scene panel = new Scene();

        window.setContentPane(panel);
        window.pack();
        window.setResizable(false);
        window.setBackground(Color.BLACK);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        // Animation - calls display() 60 times per second
        // Start animation
        FPSAnimator animator = new FPSAnimator(panel, 60, true);
        animator.start();
    }

    // --------------------------------------------------------------------------

    public Scene() {
        // Create the drawing panel, with the default OpenGL capabilities
        super(new GLCapabilities(null));
        setPreferredSize(new Dimension(700, 700));

        // Specify handlers for events on the panel
        addGLEventListener(this); // OpenGL events
    }

    // ---------------- Methods from the GLEventListener interface --------------

    

    public void display(GLAutoDrawable drawable) {
      GL2 gl2 = drawable.getGL().getGL2();

      gl2.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); // Background color: black
      gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

      gl2.glMatrixMode(GL2.GL_MODELVIEW);
      gl2.glLoadIdentity();

      // Draw the Sun first at the origin
      drawSun(gl2);

      // Rotate the Earth's position around the Sun
      gl2.glRotatef(earthRotationAngle, 0.0f, 0.0f, 1.0f);

      // Translate to the Earth's position
      gl2.glTranslatef(6.0f, 0.0f, 0.0f); // Adjust the distance from the Sun

      // Draw the Earth
      drawEarth(gl2);

      // Rotate the Moon's position around the Earth
      gl2.glRotatef(moonRotationAngle, 0.0f, 0.0f, 1.0f);

      // Translate to the Moon's position relative to the Earth
      gl2.glTranslatef(1.7f, 0.0f, 0.0f); // Adjust the distance from the Earth

      // Draw the Moon
      drawMoon(gl2);

      gl2.glFlush();

      // Update rotation angles for animation
      earthRotationAngle += 0.5f; // Adjust the rotation speed as needed
      moonRotationAngle += 1.5f; // Adjust the moon's rotation speed as needed
  }


    

    public void init(GLAutoDrawable drawable) {
        // Called once before the window is opened

        GL2 gl2 = drawable.getGL().getGL2();

        // Set up the camera and view
        Drawing2D.setProjection(gl2);

        // Other settings
        gl2.glEnable(GL2.GL_COLOR_MATERIAL);
    }

    public void dispose(GLAutoDrawable drawable) {
        // Called when the panel is being disposed
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Called when the user resizes the window
    }

    // -----------------------------------------------------------------------

    // TODO Define helper methods here

    // TODO Implement the drawSun method
    private void drawSun(GL2 gl2) {
      Drawing2D.color(gl2, 1.0f, 1.0f, 0.0f); // Set color to yellow
      Drawing2D.filledCircle(gl2, 3.0f); // Adjust the size of the Sun as needed
  }

    // TODO Implement the drawEarth method
    private void drawEarth(GL2 gl2) {
        Drawing2D.color(gl2, 0.0f, 0.0f, 1.0f); // Set color to blue
        Drawing2D.filledCircle(gl2, 1f); // Draw a filled circle for the Earth
        
        // Add land on the Earth (green circles)
        Drawing2D.color(gl2, 0.0f, 0.7f, 0.0f); // Set color to green
        drawLand(gl2, 1.0f); // Draw land on Earth
    }

 // TODO Implement the drawMoon method
    private void drawMoon(GL2 gl2) {
        float moonRadius = 0.5f; // Adjust the size of the Moon
        float moonCraterRadius = 0.1f; // Adjust the size of the Moon's craters
        int numCraters = 5;
        // Draw the Moon
        Drawing2D.color(gl2, 0.7f, 0.7f, 0.7f); // Set color to gray for the Moon
        Drawing2D.filledCircle(gl2, moonRadius); // Adjust the size of the Moon

        // Set color to dark gray for the craters
        Drawing2D.color(gl2, 0.2f, 0.2f, 0.2f);

        // Draw the Moon's craters
        for (int i = 0; i < numCraters; i++) {
            // Calculate random positions for craters on the Moon's surface
            float angle = i * 2 * (float) Math.PI / numCraters;
            float distance = moonRadius * 0.5f; // Adjust the crater distance from the center
            float x = distance * (float) Math.cos(angle);
            float y = distance * (float) Math.sin(angle);
            
            gl2.glPushMatrix();
            gl2.glTranslatef(x, y, 0.0f);
            Drawing2D.filledCircle(gl2, moonCraterRadius); // Adjust the size of the craters
            gl2.glPopMatrix();
        }
    }

 // Helper method to draw land on the Earth
    private void drawLand(GL2 gl2, float radius) {
      int numLandCircles = 6; // Number of land circles
      float landRadius = 0.1f; // Adjust the size of the land circles as needed
      float landOffset = 0.4f; // Adjust the offset to move land circles inward
      
      for (int i = 0; i < numLandCircles; i++) {
          // Calculate positions for land circles evenly spaced on Earth's surface
          float angle = i * 2 * (float) Math.PI / numLandCircles;
          float x = (radius - landOffset) * (float) Math.cos(angle);
          float y = (radius - landOffset) * (float) Math.sin(angle);
          
          gl2.glPushMatrix();
          gl2.glTranslatef(x, y, 0.0f);
          Drawing2D.filledCircle(gl2, landRadius); // Adjust the size of the land circles
          gl2.glPopMatrix();
      }
  }}


