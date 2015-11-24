package main.IA;

import java.io.ObjectInputStream.GetField;

public abstract class iaAbstract {
	boolean left;
	boolean up;
	boolean right;
	boolean down;
	
	public abstract void updateDirection();
	
	public boolean getLeft(){
		return left;
	}
	
	public boolean getUp(){
		return up;
	}
	
	public boolean getRight(){
		return right;
	}
	
	public boolean getDown(){
		return down;
	}
}
