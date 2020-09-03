package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import javafx.scene.shape.Circle;



public class Tytul extends JPanel implements MouseListener,MouseMotionListener {
	
	private int myszX;
	private int myszY;
	private boolean lewyPrzycisk = false;
	private BufferedImage tytul;
	private BufferedImage sterowanie;
	private BufferedImage start;
	private Okno okno;
	private BufferedImage[] przyciskStartGry = new BufferedImage[2];
	private Timer timer;
	private Circle obrys;
	
	public Tytul(Okno okno) {
		try {
			tytul = ImageIO.read(PanelGry.class.getResource("/tetrisGora.png"));
			sterowanie = ImageIO.read(PanelGry.class.getResource("/sterowanie.png"));
			start = ImageIO.read(PanelGry.class.getResource("/start.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		timer=new Timer(1000/60, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			repaint();
				
			}
		});
		
		timer.start();
		myszX = 0;
		myszY = 0;
		
		przyciskStartGry[0] = start.getSubimage(0, 0, 100, 80);
		przyciskStartGry[1] = start.getSubimage(100, 0, 100, 80);
		
		obrys = new Circle(Okno.SZEROKOSC/2 + 7, Okno.WYSOKOSC/2 - 30, 41);
		this.okno = okno;
		
		
	}
	

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		if(lewyPrzycisk && obrys.contains(myszX, myszY))
			okno.startTetris();
			
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, Okno.SZEROKOSC, Okno.WYSOKOSC);
		
		g.drawImage(tytul, Okno.SZEROKOSC/2 - tytul.getWidth()/2, Okno.WYSOKOSC/2 - tytul.getHeight()/2 - 200, null);
		g.drawImage(sterowanie, Okno.SZEROKOSC/2 - sterowanie.getWidth()/2,
				Okno.WYSOKOSC/2 - sterowanie.getHeight()/2 + 150, null);
		
		if(obrys.contains(myszX, myszY))
			g.drawImage(przyciskStartGry[0], Okno.SZEROKOSC/2 - 50, Okno.WYSOKOSC/2 - 100, null);
		else
			g.drawImage(przyciskStartGry[1], Okno.SZEROKOSC/2 - 50, Okno.WYSOKOSC/2 - 100, null);
		
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		myszX = e.getX();
		myszY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		myszX = e.getX();
		myszY = e.getY();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			lewyPrzycisk = true;
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			lewyPrzycisk = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
