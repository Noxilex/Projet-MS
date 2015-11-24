package main.IA;

import java.util.Random;

public class iaGeometric extends iaAbstract{

	int maxOccurences;
	int cptIA;
	@Override
	public void updateDirection() {
		Random r = new Random();
		if(cptIA%maxOccurences == 0){
			maxOccurences = r.nextInt(5)*5+5;
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
				left = true;
				break;
			case 1: 
				up = true;
				break;
			case 2:
				right = true;
				break;
			case 3:
				down = true;
				break;
			}
		}
		cptIA++;
	}

}
