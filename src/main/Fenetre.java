package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Fenetre extends JPanel implements Runnable {

	boolean left;
	boolean right;
	boolean up;
	boolean down;
	boolean reset;
	boolean image;
	
	boolean IA_State = false;
	int cptIA;
	int maxOccurences;
	//0:gauche 1:haut 2:droite 3:bas
	int direction;

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
	private int currentFPS = FPS;
	int vitesse = 1;

	private Thread thread;
	
	private int x;
	private int y;

	private KeyMap keyMap = new KeyMap();

	public KeyMap getKeyMap() {
		return keyMap;
	}

	public Fenetre() {
		setSize(new Dimension(710, 710));
		addKeyListener(keyMap);

		x = this.getWidth() / 2;
		y = this.getHeight() / 2;
		
		nbOfPoints = 4;

		hexaR = 255;
		hexaV = 0;
		hexaB = 0;

		start();
	}
	
	public Fenetre(boolean IA_State) {
		this();
		this.IA_State = IA_State;
		keyMap.IA_Activated = true;
		cptIA = 0;
		Random r = new Random();
		maxOccurences = r.nextInt(20)+5;
	}

	private void handleControl() {
		IA_State = keyMap.isIAActivated();
		if(IA_State){
			updateDirectionIA();
		}else{
			right = keyMap.isDroite();
			left = keyMap.isGauche();
			up = keyMap.isHaut();
			down = keyMap.isBas();
		}
		image = keyMap.needImage();
		reset = keyMap.hasToReset();
		canWrite = keyMap.canWrite();
		nbOfPoints = keyMap.getNbOfPoints();
		
		currentFPS = FPS + keyMap.getFPSChanger();
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
	 * 1) Crée un carré noir à l'initialisation
	 * 2) Dessine les carrés sur l'image
	 */
	public void drawOnImage(boolean draw) {
		if (firstTime) {			
			tmp = new BufferedImage(getWidth(), getHeight(),				//On initialise l'image ici, car lorsqu'on appelle paintComponent, 
					BufferedImage.TYPE_INT_RGB);							//le JPanel change de dimension et les points ne se dessinent plus au même endroit.
			g2 = tmp.createGraphics();
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
	 * 1) Dessine l'image temporaire sur le JPanel
	 * 2) Dessine les points qui indiquent la position actuelle 
	 */
	public void paintComponent(Graphics g) {
		// Idée: Gérer le draw du dessin et le draw du curseur à part
		if (canWrite) {
			drawOnImage(true);
			g.drawImage(tmp, 0, 0, this.getWidth(), this.getHeight(), Color.BLACK, null);
			g.setColor(Color.GREEN);
		}else if(!canWrite){																		
			drawOnImage(false);
			g.drawImage(tmp, 0, 0, this.getWidth(), this.getHeight(), Color.BLACK, null);
			g.setColor(Color.RED);
		}
		g.fillRect(getWidth()-10, getHeight()-10, 10, 10);								//Indicateur d'écriture en bas à droite de l'image:
		g.setColor(Color.ORANGE);														//Vert: Ecriture Activée
		if (nbOfPoints >= 1) {															//Rouge: Ecriture Désactivée
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
		
		//Affichage des FPS
		g2.setColor(new Color(hexaR, hexaV, hexaB));
		g.drawString("FPS:"+currentFPS, getWidth()-50, 20);
	}

	/**
	 * Met à jour la couleur des points
	 */
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
	
	public void saveImage(String nomImage){
		File f = new File(nomImage);
		try {
			ImageIO.write(tmp, "png", f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void updateDirectionIA(){
		Random r = new Random();
		if(cptIA%maxOccurences == 0){
			maxOccurences = r.nextInt(10)*3+30;
			int direction = r.nextInt(4);
			switch(direction){
			case 0:
				left = true;
				up = false;
				right = false;
				down = false;
				break;
			case 1: 
				left = false;
				up = true;
				right = false;
				down = false;
				break;
			case 2:
				left = false;
				up = false;
				right = true;
				down = false;
				break;
			case 3:
				left = false;
				up = false;
				right = false;
				down = true;
				break;
			}
			direction = r.nextInt(4);
			switch(direction){
			case 0:
				if(!right)
					left = true;
				break;
			case 1: 
				if(!down)
					up = true;
				break;
			case 2:
				if(!left)
					right = true;
				break;
			case 3:
				if(!up)
					down = true;
				break;
			}
		}
		cptIA++;
	}

	/**
	 * Boucle de jeu principale
	 */
	@Override
	public void run() {
		while (running) {
			// TODO IA de déplacement automatique
			// updateAutoPosition();
			updateCouleur();
			handleControl();
			
			if(image){
				saveImage("Resources/test.png");
				keyMap.dontNeedImage();
			}

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
			}
			repaint();
			try {
				Thread.sleep(1000 / currentFPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}
}
