package main;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyMap implements KeyListener{

	private boolean droite = false;
	private boolean gauche = false;
	private boolean haut = false;
	private boolean bas = false;
	private boolean reset = false;
	private boolean canWrite = true;
	
	private int FPSChanger = 0;
	private int FPSInterval = 5;
	private int maxFPSChange = 30;
	
	boolean IA_Activated = false;
	
	private int lastDirection = KeyEvent.VK_RIGHT;
	private int nbOfPoints = 4;
	private boolean image;
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(e.getKeyChar() == 'r')
			reset = true;
		if(e.getKeyChar() == '&')
			nbOfPoints = 1;
		if(e.getKeyChar() == 'é')
			nbOfPoints = 2;
		if(e.getKeyChar() == '"')
			nbOfPoints = 3;
		if(e.getKeyChar() == '\'')
			nbOfPoints = 4;
		if(e.getKeyChar() == 'i')
			IA_Activated = !IA_Activated;
		if(key == KeyEvent.VK_SPACE)
			canWrite = !canWrite;
		if(key == KeyEvent.VK_LEFT){
			gauche = true;
			lastDirection = key;
		}
		else if(key == KeyEvent.VK_RIGHT){
			droite = true;
			lastDirection = key;
		}
		else if(key == KeyEvent.VK_UP){
			haut= true;
		}
		else if(key == KeyEvent.VK_DOWN){
			bas= true;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if(e.getKeyChar() == 'r')
			reset = false;
		if(key == KeyEvent.VK_LEFT)
			gauche = false;
		else if(key == KeyEvent.VK_RIGHT)
			droite = false;
		else if(key == KeyEvent.VK_UP){
			haut= false;
		}else if(key == KeyEvent.VK_DOWN)
			bas= false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == '+' && maxFPSChange-FPSInterval >= FPSChanger)
			FPSChanger += FPSInterval;
		if(e.getKeyChar() == '-' && -maxFPSChange+FPSInterval <= FPSChanger)
			FPSChanger -= FPSInterval;
		if(e.getKeyChar() == '$')
			image = true;
	}
	
	public int getFPSInterval() {
		return FPSInterval;
	}

	public boolean isGauche(){
		return gauche;
	}
	
	public boolean isDroite(){
		return droite;
	}
	
	public boolean isHaut(){
		return haut;
	}
	
	public boolean isBas(){
		return bas;
	}
	
	public boolean hasToReset(){
		return reset;
	}
	
	public int getLastDirection(){
		return lastDirection;
	}

	public int getNbOfPoints(){
		return nbOfPoints;
	}
	
	public boolean canWrite(){
		return canWrite;
	}

	public int getFPSChanger() {
		return FPSChanger;
	}

	public boolean isIAActivated() {
		return IA_Activated;
	}
	
	public void dontNeedImage(){
		image = false;
	}
	
	public boolean needImage(){
		return image;
	}

	public void setDroite(boolean droite) {
		this.droite = droite;
	}

	public void setGauche(boolean gauche) {
		this.gauche = gauche;
	}

	public void setHaut(boolean haut) {
		this.haut = haut;
	}

	public void setBas(boolean bas) {
		this.bas = bas;
	}

	public void setReset(boolean reset) {
		this.reset = reset;
	}

	public void setCanWrite(boolean canWrite) {
		this.canWrite = canWrite;
	}

	public void setFPSChanger(int fPSChanger) {
		FPSChanger = fPSChanger;
	}

	public void setFPSInterval(int fPSInterval) {
		FPSInterval = fPSInterval;
	}

	public void setMaxFPSChange(int maxFPSChange) {
		this.maxFPSChange = maxFPSChange;
	}

	public void setIA_Activated(boolean iA_Activated) {
		IA_Activated = iA_Activated;
	}

	public void setLastDirection(int lastDirection) {
		this.lastDirection = lastDirection;
	}

	public void setNbOfPoints(int nbOfPoints) {
		this.nbOfPoints = nbOfPoints;
	}

	public void setImage(boolean image) {
		this.image = image;
	}
}
