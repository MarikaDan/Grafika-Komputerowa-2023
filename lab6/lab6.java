

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.*;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

public class lab6 extends GLJPanel implements GLEventListener {

	private static final long serialVersionUID = 1L;

	private double rotateY = 0;

	public static void main(String[] args) {
		JFrame window = new JFrame("Stage");
		lab6 panel = new lab6();
		window.setContentPane(panel);
		window.pack();
		window.setResizable(false);
		window.setLocation(50, 50);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	public lab6() {
		super(new GLCapabilities(null));
		setPreferredSize(new Dimension(1000, 500));
		addGLEventListener(this);
		MouseHandler mouser = new MouseHandler();
		addMouseListener(mouser);
	}

	private final static float[][] materials = {
			{ /* "emerald" */ 0.0215f, 0.1745f, 0.0215f, 1.0f, 0.07568f, 0.61424f, 0.07568f, 1.0f, 0.633f, 0.727811f,
					0.633f, 1.0f, 0.6f * 128 },
			{ /* "jade" */ 0.135f, 0.2225f, 0.1575f, 1.0f, 0.54f, 0.89f, 0.63f, 1.0f, 0.316228f, 0.316228f, 0.316228f,
					1.0f, 0.1f * 128 },
			{ /* "obsidian" */ 0.05375f, 0.05f, 0.06625f, 1.0f, 0.18275f, 0.17f, 0.22525f, 1.0f, 0.332741f, 0.328634f,
					0.346435f, 1.0f, 0.3f * 128 },
			{ /* "pearl" */ 0.25f, 0.20725f, 0.20725f, 1.0f, 1.0f, 0.829f, 0.829f, 1.0f, 0.296648f, 0.296648f,
					0.296648f, 1.0f, 0.088f * 128 },
			{ /* "ruby" */ 0.1745f, 0.01175f, 0.01175f, 1.0f, 0.61424f, 0.04136f, 0.04136f, 1.0f, 0.727811f, 0.626959f,
					0.626959f, 1.0f, 0.6f * 128 },
			{ /* "turquoise" */ 0.1f, 0.18725f, 0.1745f, 1.0f, 0.396f, 0.74151f, 0.69102f, 1.0f, 0.297254f, 0.30829f,
					0.306678f, 1.0f, 0.1f * 128 },
			{ /* "brass" */ 0.329412f, 0.223529f, 0.027451f, 1.0f, 0.780392f, 0.568627f, 0.113725f, 1.0f, 0.992157f,
					0.941176f, 0.807843f, 1.0f, 0.21794872f * 128 },
			{ /* "bronze" */ 0.2125f, 0.1275f, 0.054f, 1.0f, 0.714f, 0.4284f, 0.18144f, 1.0f, 0.393548f, 0.271906f,
					0.166721f, 1.0f, 0.2f * 128 },
			{ /* "chrome" */ 0.25f, 0.25f, 0.25f, 1.0f, 0.4f, 0.4f, 0.4f, 1.0f, 0.774597f, 0.774597f, 0.774597f, 1.0f,
					0.6f * 128 },
			{ /* "copper" */ 0.19125f, 0.0735f, 0.0225f, 1.0f, 0.7038f, 0.27048f, 0.0828f, 1.0f, 0.256777f, 0.137622f,
					0.086014f, 1.0f, 0.1f * 128 },
			{ /* "gold" */ 0.24725f, 0.1995f, 0.0745f, 1.0f, 0.75164f, 0.60648f, 0.22648f, 1.0f, 0.628281f, 0.555802f,
					0.366065f, 1.0f, 0.4f * 128 },
			{ /* "silver" */ 0.19225f, 0.19225f, 0.19225f, 1.0f, 0.50754f, 0.50754f, 0.50754f, 1.0f, 0.508273f,
					0.508273f, 0.508273f, 1.0f, 0.4f * 128 },
			{ /* "cyan plastic" */ 0.0f, 0.1f, 0.06f, 1.0f, 0.0f, 0.50980392f, 0.50980392f, 1.0f, 0.50196078f,
					0.50196078f, 0.50196078f, 1.0f, .25f * 128 },
			{ /* "green plastic" */ 0.0f, 0.0f, 0.0f, 1.0f, 0.1f, 0.35f, 0.1f, 1.0f, 0.45f, 0.55f, 0.45f, 1.0f,
					.25f * 128 },
			{ /* "red plastic" */ 0.0f, 0.0f, 0.0f, 1.0f, 0.5f, 0.0f, 0.0f, 1.0f, 0.7f, 0.6f, 0.6f, 1.0f, .25f * 128 },
			{ /* "cyan rubber" */ 0.0f, 0.05f, 0.05f, 1.0f, 0.4f, 0.5f, 0.5f, 1.0f, 0.04f, 0.7f, 0.7f, 1.0f,
					.078125f * 128 },
			{ /* "green rubber" */ 0.0f, 0.05f, 0.0f, 1.0f, 0.4f, 0.5f, 0.4f, 1.0f, 0.04f, 0.7f, 0.04f, 1.0f,
					.078125f * 128 },
			{ /* "red rubber" */ 0.05f, 0.0f, 0.0f, 1.0f, 0.5f, 0.4f, 0.4f, 1.0f, 0.7f, 0.04f, 0.04f, 1.0f,
					.078125f * 128 }, };

	private GLUT glut = new GLUT();
	private GLU glu = new GLU();

	public void display(GLAutoDrawable drawable) {

		GL2 gl2 = drawable.getGL().getGL2();

		gl2.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		gl2.glLoadIdentity();
		glu.gluLookAt(0, 8, 40, 0, 1, 0, 0, 1, 0);

		gl2.glRotated(rotateY, 0, 1, 0);

		float[] gray = { 0.6f, 0.6f, 0.6f, 1 };
		float[] zero = { 0, 0, 0, 1 };
		gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, gray, 0);
		gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, zero, 0);
		gl2.glPushMatrix();
		gl2.glTranslated(0, -1.5, 0);
		gl2.glScaled(1, 0.05, 1);
		glut.glutSolidCube(20);
		gl2.glPopMatrix();

		gl2.glPushMatrix();
		gl2.glTranslated(0, -2, 0);
		Polygon_and_Triangle(gl2, 22, 5, 9);
		gl2.glPopMatrix();
	}

	public void Podstawa(GL2 gl2, int n, int r) {
		gl2.glBegin(GL2.GL_POLYGON);
		for (int i = 0; i <= n; i++) {
			gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, materials[12], 5);
			gl2.glVertex3d((double) (r * Math.cos(2 * i * Math.PI / n)), 1,
					(double) (r * Math.sin(2 * i * Math.PI / n))); // (x, y, z)
		}
		gl2.glEnd();
	}

	public void Polygon_and_Triangle(GL2 gl2, int n, int r, int h) {
		Podstawa(gl2, n, r);
		for (int i = 0; i <= n; i++) {
			gl2.glBegin(GL2.GL_POLYGON);
			gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, materials[12], 7);
			gl2.glVertex3d((double) (r * Math.cos(2 * i * Math.PI / n)), 1,
					(double) (r * Math.sin(2 * i * Math.PI / n)));
			gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, materials[12], 11);
			gl2.glVertex3d(0, h, 0);
			i++;
			gl2.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE, materials[12], 12);
			gl2.glVertex3d((double) (r * Math.cos(2 * i * Math.PI / n)), 1,
					(double) (r * Math.sin(2 * i * Math.PI / n)));
			i--;
			gl2.glEnd();
		}
	}

	public void init(GLAutoDrawable drawable) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		glu.gluPerspective(20, (double) getWidth() / getHeight(), 1, 100);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glEnable(GL2.GL_DEPTH_TEST);
		gl2.glEnable(GL2.GL_NORMALIZE);
		gl2.glEnable(GL2.GL_LIGHTING);
		gl2.glEnable(GL2.GL_LIGHT0);

		float position[] = { 0.0f, 3.0f, 3.0f, 0.0f };
		gl2.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, position, 0);
	}

	public void dispose(GLAutoDrawable drawable) {
	}

	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
	}

	private class MouseHandler extends MouseAdapter {

		private int prevX;
		private boolean dragging;

		public void mouseDragged(MouseEvent evt) {
			if (dragging) {
				int x = evt.getX();
				double dx = x - prevX;
				rotateY += dx / 7;
				repaint();
				prevX = x;
			}
		}

		public void mousePressed(MouseEvent evt) {
			if (dragging)
				return;
			prevX = evt.getX();
			dragging = true;
			lab6.this.addMouseMotionListener(this);
		}

		public void mouseReleased(MouseEvent evt) {
			dragging = false;
			lab6.this.removeMouseMotionListener(this);
		}

	}
}