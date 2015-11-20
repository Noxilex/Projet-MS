package test;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import main.KeyMap;

@SuppressWarnings("serial")
public class FenetreTest extends JPanel implements Runnable {

	boolean left;
	boolean right;
	boolean up;
	boolean down;
	boolean reset;

	BufferedImage tmp;
	Graphics2D g2;
	boolean canWrite;
	boolean needImage = false;
	boolean firstTime = true;

	int hexaR;
	int hexaV;
	int hexaB;

	int nbOfPoints;

	boolean running = true;

	final int FPS = 60;
	int vitesse = 1;
	int c = 0;

	private Thread thread;

	private int x;
	private int y;

	private KeyMap m = new KeyMap();

	public FenetreTest() {
		setSize(new Dimension(710, 710));
		addKeyListener(m);

		x = this.getWidth() / 2;
		y = this.getHeight() / 2;
		
		nbOfPoints = 4;

		hexaR = 255;
		hexaV = 0;
		hexaB = 0;

		start();
	}

	private void handleControl() {
		right = m.isDroite();
		left = m.isGauche();
		up = m.isHaut();
		down = m.isBas();
		reset = m.hasToReset();
		if (canWrite && !m.canWrite())
			needImage = true;
		canWrite = m.canWrite();

		nbOfPoints = m.getNbOfPoints();
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	public void addNotify() {

		super.addNotify();

		requestFocus();
	}

	/**
	 * S'occupe de garder en mémoire le dessin
	 */
	public void drawOnImage(boolean draw) {
		if (firstTime) {
			tmp = new BufferedImage(getWidth(), getHeight(),
					BufferedImage.TYPE_INT_RGB);
			g2 = tmp.createGraphics();
			c++;
			g2.setColor(Color.BLACK);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
			firstTime = false;
		}
		if(draw){
			g2.setColor(new Color(hexaR, hexaV, hexaB));
			if (nbOfPoints >= 1) {
				g2.fillRect(x, y, 1, 1);
			}
			if (nbOfPoints >= 2) {
				g2.fillRect(this.getWidth() - x, y, 1, 1);
			}
			if (nbOfPoints >= 3) {
				g2.fillRect(this.getWidth() - x, this.getHeight() - y, 1, 1);
			}
			if (nbOfPoints >= 4) {
				g2.fillRect(x, this.getHeight() - y, 1, 1);
			}
		}
	}

	/**
	 * Paint le dessin de drawOnImage() sur le JPanel, ainsi que le curseur
	 * actuel
	 */
	public void paintComponent(Graphics g) {
		// Idée: Gérer le draw du dessin et le draw du curseur à part
		if (canWrite) {
			drawOnImage(true);
		}else if(!canWrite){
			drawOnImage(false);
		}
		g.drawImage(tmp, 0, 0, this.getWidth(), this.getHeight(), Color.BLACK, null);
		g.setColor(Color.ORANGE);
		if (nbOfPoints >= 1) {
			g.fillRect(x, y, 1, 1);
		}
		g.setColor(Color.CYAN);
		if (nbOfPoints >= 2) {
			g.fillRect(this.getWidth() - x, y, 1, 1);
		}
		if (nbOfPoints >= 3) {
			g.fillRect(this.getWidth() - x, this.getHeight() - y, 1, 1);
		}
		if (nbOfPoints >= 4) {
			g.fillRect(x, this.getHeight() - y, 1, 1);
		}
	}

	public void updateCouleur() {
		/*
		 * System.out.println("hexaR:" + hexaR); System.out.println("hexaV:" +
		 * hexaV); System.out.println("hexaB:" + hexaB);
		 */
		hexaR = hexaR % 256;
		hexaV = hexaV % 256;
		hexaB = hexaB % 256;
		if (hexaR == 255 && hexaV < 255) {
			hexaV++;
			if (hexaB > 0)
				hexaB--;
		} else if (hexaV == 255 && hexaB < 255) {
			hexaB++;
			if (hexaR > 0)
				hexaR--;
		} else if (hexaB == 255 && hexaR < 255) {
			hexaR++;
			if (hexaV > 0)
				hexaV--;
		}
	}

	@Override
	public void run() {
		while (running) {
			// TODO IA de déplacement automatique
			// updateAutoPosition();
			updateCouleur();
			handleControl();

			if (left)
				x -= vitesse;
			if (right)
				x += vitesse;
			if (up)
				y -= vitesse;
			if (down)
				y += vitesse;
			if (reset) {
				firstTime = true;
				reset = false;
				c = 0;
			}
			repaint();
			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
