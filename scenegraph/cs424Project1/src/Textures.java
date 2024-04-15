import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.util.awt.ImageUtil;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.awt.AWTTextureIO;

import jogamp.opengl.glu.mipmap.Image;

public class Textures extends GLJPanel implements GLEventListener, MouseListener, MouseMotionListener {

	/**
	 * The main program creates a window containing a panel for OpenGL drawing.
	 */

	public static void main(String[] args) {
		// create the window
		JFrame window = new JFrame("Lab5");

		// create the drawing panel
		Textures panel = new Textures();

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

	public Textures() {
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

	private Map<String, Texture> textures = new HashMap<>();
	
	private double t = Math.sqrt(3)/4;
	
	double[][] verticesPrism = {
		    {-t, .5, -.25},  // V0
		    { t, .5,  .25},  // V1
		    { 0, .5,  .5 },  // V2
		    {-t, -.5, -.25}, // V3
		    { t, -.5,  .25}, // V4
		    { 0, -.5,  .5 }  // V5
		};

		int[][] facesPrism = {
		    {2, 1, 4, 5}, // Front
		    {2, 0, 3, 5}, // Left
		    {1, 0, 3, 4}, // Right
		    {2, 1, 0},    // Top
		    {3, 4, 5}     // Bottom
		};
		public double[][] verticesHouse = {
			    { 2, -1, 2 }, { 2, -1, -2 }, { 2, 1, -2 },
			    { 2, 1, 2 }, { 1.5, 1.5, 0 },
			    { -1.5, 1.5, 0 }, { -2, -1, 2 },
			    { -2, 1, 2 }, { -2, 1, -2 },
			    { -2, -1, -2 }
			};

			public int[][] facesHouse = {
			    { 0, 1, 2, 3 }, { 3, 2, 4 }, { 7, 3, 4, 5 },
			    { 2, 8, 5, 4 }, { 5, 8, 7 }, { 0, 3, 7, 6 },
			    { 0, 6, 9, 1 }, { 2, 1, 9, 8 },
			    { 6, 7, 8, 9 }
			};
		
	public void display(GLAutoDrawable drawable) {
		// called when the panel needs to be drawn
		GLUquadric shape = glu_.gluNewQuadric();
		GL2 gl2 = drawable.getGL().getGL2();
		TexturedShapes textureShapes = new TexturedShapes();

		// clear the display
		gl2.glClearColor(0, 0, 0, 1); // background color black
		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();

		// set viewing transform
		glu_.gluLookAt(0, 0, 50, 0, 0, 0, 0, 1, 0);

		// mouse-driven rotation
		gl2.glRotatef(rotateX_, 1, 0, 0);
		gl2.glRotatef(rotateY_, 0, 1, 0);

		float startingX = -7f; // A starting position to the left
		float shiftAmount = 3f; // How much to shift each time; adjust as needed based on object size
		
		// TODO draw scene

		// Loading texture
		Texture ambrosiaTexture = getTexture("ambrosia");
		Texture barkTexture = getTexture("bark");
		Texture maelstrmTexture = getTexture("maelstrm");
		Texture weave7Texture = getTexture("weave7");
		Texture towelTexture = getTexture("towel");

		gl2.glPushMatrix();

		gl2.glEnable(GL2.GL_TEXTURE_2D);
		
			// Ambrosia Texture Teapot
		ambrosiaTexture.enable(gl2); // Enable ambrosiaTexture
		ambrosiaTexture.bind(gl2);
		gl2.glTranslatef(startingX, 0, 0);

		glut_.glutSolidTeapot(1); // Draw the teapot
		ambrosiaTexture.disable(gl2); // Disable ambrosiaTexture

		startingX = +shiftAmount;

		// bark Texture Quadric (Sphere)
		gl2.glEnable(GL2.GL_TEXTURE_2D);
		barkTexture.enable(gl2); // Enable barkTexture

		// Set texture parameters: wrap s and t coordinates
		gl2.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
		gl2.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);

		barkTexture.bind(gl2);

		gl2.glTranslatef(startingX, 0, 0);

		// Modify the texture matrix
		gl2.glMatrixMode(GL2.GL_TEXTURE);
		gl2.glLoadIdentity();
		gl2.glScaled(2.0, 2.0, 1.0);  // Scale the texture coordinates: repeat texture twice in s and t direction
		gl2.glMatrixMode(GL2.GL_MODELVIEW);  // Don't forget to switch back to modelview matrix mode!

		// Draw textured sphere
		glu_.gluQuadricTexture(shape, true); // Enable texture coordinates for the quadric
		glu_.gluSphere(shape, 1, 16, 16); // Draw the sphere

		gl2.glMatrixMode(GL2.GL_TEXTURE);
		gl2.glLoadIdentity();
		gl2.glMatrixMode(GL2.GL_MODELVIEW);

		barkTexture.disable(gl2); // Disable barkTexture

		startingX = +shiftAmount;

		// Maelstrm Texture Shape (Ring)
		maelstrmTexture.enable(gl2); // Enable maelstrmTexture
		maelstrmTexture.bind(gl2);
		gl2.glTranslatef(startingX, 0, 0);

		TexturedShapes.cube(gl2);
		maelstrmTexture.disable(gl2);

		startingX = +shiftAmount;

		// weave7 Texture Shape (Prism)
		gl2.glEnable(GL2.GL_TEXTURE_2D);
		weave7Texture.enable(gl2); // Enable weave7Texture
		weave7Texture.bind(gl2);
		gl2.glTranslatef(startingX, 0, 0);

		drawPolyhedron(gl2, Polyhedron.PRISM, verticesPrism, facesPrism);
		weave7Texture.disable(gl2); // Disable weave7Texture

		startingX = +shiftAmount;		

		// towel Texture Shape (Prism)
		gl2.glEnable(GL2.GL_TEXTURE_2D);
		towelTexture.enable(gl2); // Enable towelTexture
		towelTexture.bind(gl2);
		gl2.glTranslatef(startingX, 0, 0);

		drawPolyhedron(gl2, Polyhedron.HOUSE, verticesHouse, facesHouse);
		towelTexture.disable(gl2); // Disable towelTexture
		
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
		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);

		// Loads images
		List<String> fileNamesToLoad = List.of("ambrosia.jpg", "bark.jpg", "maelstrm.jpg", "weave7.jpg", "towel.jpg");
		loadSpecificTextures("textures", fileNamesToLoad);

	}

	void drawPolyhedron(GL2 gl, Polyhedron poly, double[][] vertices, int[][] facesPrism2) {
	    
	    for (int i = 0; i < facesPrism2.length; i++) {
	        // Quads or triangles?
	        if (facesPrism2[i].length == 4) gl.glBegin(GL2.GL_QUADS);
	        else if (facesPrism2[i].length == 3) gl.glBegin(GL2.GL_TRIANGLES);
	        else continue; // Skip non-tri/quad faces
	        
	        for (int j = 0; j < facesPrism2[i].length; j++) {
	            int vertexIndex = facesPrism2[i][j];
	            double[] vertex = vertices[vertexIndex];
	            
	            // Here, apply the texture mapping logic.
	            // The specific coordinates you use will depend on your specific texture.
	            if (i == 0) { // Front Face
	                gl.glTexCoord2d(j == 0 || j == 3 ? 0 : 1.0/3, j > 1 ? 0 : 1);
	            } else if (i == 1) { // Left Face
	                gl.glTexCoord2d(j == 0 || j == 3 ? 1.0/3 : 2.0/3, j > 1 ? 0 : 1);
	            } else if (i == 2) { // Right Face
	                gl.glTexCoord2d(j == 0 || j == 3 ? 2.0/3 : 1, j > 1 ? 0 : 1);
	            } else { // Top and Bottom Faces
	                gl.glTexCoord2d(vertex[0] + 0.5, vertex[2] + 0.5); 
	            }
	            
	            double[] normal = poly.faceNormals[i];
	            gl.glNormal3d(normal[0], normal[1], normal[2]);
	            gl.glVertex3d(vertex[0], vertex[1], vertex[2]);
	        }
	        gl.glEnd();
	    }
	}

	public void loadSpecificTextures(String folderPath, List<String> fileNames) {
		for (String fileName : fileNames) {
			File file = new File(folderPath + "/" + fileName);
			if (file.isFile() && fileName.endsWith(".jpg")) {
				BufferedImage img = loadImage(file.getPath());
				ImageUtil.flipImageVertically(img);
				Texture texture = AWTTextureIO.newTexture(GLProfile.getDefault(), img, true);
				// adds images to hashmap with the file name without the extension (i.e.
				// "ambrosia" instead of "ambrosia.jpg", as the key)
				textures.put(getFileNameWithoutExtension(file), texture);
			}
		}
	}

	public static BufferedImage loadImage(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	private String getFileNameWithoutExtension(File file) {
		String fileName = file.getName();
		if (fileName.indexOf(".") > 0) {
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
	}

	public Texture getTexture(String name) {
		return textures.get(name);
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
