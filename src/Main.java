import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;


public class Main {

	public static void main(String[] args) {
		JFrame jf = new JFrame();
		Fenetre f = new Fenetre();
		jf.setPreferredSize(new Dimension(700,700));
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setLayout(new BorderLayout());
		jf.add(f, BorderLayout.CENTER);
		jf.setResizable(false);
		jf.pack();
		jf.setLocationRelativeTo(null);
		jf.setVisible(true);
	}
}
