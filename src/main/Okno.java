package main;

import java.awt.Window;
import java.io.IOException;

import javax.swing.JFrame;

public class Okno {
	
	static final int SZEROKOSC=470;
	static final int WYSOKOSC=639;
	
	private JFrame okno;
	private PanelGry panelGry;
	private Tytul panelPoczatkowy;
		
	
	
	public Okno() 
	{
		okno=new JFrame("Tetris");
		okno.setSize(SZEROKOSC, WYSOKOSC);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setLocationRelativeTo(null);
		okno.setResizable(false);
		panelGry=new PanelGry();
		panelPoczatkowy=new Tytul(this);
		okno.add(panelPoczatkowy);
		okno.addKeyListener(panelGry);
		okno.addMouseMotionListener(panelPoczatkowy);
		okno.addMouseListener(panelPoczatkowy);
		okno.setVisible(true);
		
	}
	
	public void startTetris() {
		okno.remove(panelPoczatkowy);
		okno.addMouseMotionListener(panelGry);
		okno.addMouseListener(panelGry);
		okno.add(panelGry);
		panelGry.startGame();
		okno.revalidate();
	}
	
	
	
	public static void main(String[] args)  {
		new Okno();
		
	}
	
			
			

}
