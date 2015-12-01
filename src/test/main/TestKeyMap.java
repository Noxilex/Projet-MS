package test.main;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;

import main.Fenetre;
import main.KeyMap;

import org.junit.Test;

public class TestKeyMap {
	
	@Test
	public void testNeedImage(){
		KeyMap m = new KeyMap();
		m.setImage(true);
		assertTrue(m.needImage());
		m.dontNeedImage();
		assertFalse(m.needImage());
	}
	
	@Test
	public void testKeyTyped() {
		Fenetre f = new Fenetre();
		KeyMap m = f.getKeyMap();
		KeyEvent e = new KeyEvent(f, 0, 0, 0, 0, '0');
		
		int fps = m.getFPSChanger();
		e.setKeyChar('+');
		m.keyTyped(e);
		int fpsAfter = m.getFPSChanger();
		assertEquals(fpsAfter, fps+m.getFPSInterval());
		
		fps = m.getFPSChanger();
		e.setKeyChar('-');
		m.keyTyped(e);
		fpsAfter = m.getFPSChanger();
		assertEquals(fpsAfter, fps-m.getFPSInterval());
		
		boolean image = m.needImage();
		e.setKeyChar('$');
		assertFalse(image);
		m.keyTyped(e);
		image = m.needImage();
		assertTrue(image);
	}

}
