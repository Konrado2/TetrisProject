package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;




public class PanelGry extends JPanel implements KeyListener,MouseListener,MouseMotionListener  {
	
	
	private final int WysokoscPaneluGry=20;
	private final int SzerokoscPaneluGry=10;
	private int[][]panelGry=new int [WysokoscPaneluGry][SzerokoscPaneluGry];
	private Klocek[]ksztalt=new Klocek[7];
	private BufferedImage kostki;
	private final int wielkoscKostki=30;
	private int mX;
	private int mY;
	private boolean lewyPrzycisk=false;
	private boolean pauzaGry=false;
	private boolean koniecGry=false;
	private Rectangle muzykaLinia;
	private Rectangle pauzaLinia;
	private Rectangle restartLinia;
	private BufferedImage pauza;
	private BufferedImage restart;
	private BufferedImage muzykaWTle;
	private BufferedImage obrazTla;
	private BufferedImage obrazPauzy;
	private int wynik=0;
	private Clip muzyka1;
	private Clip muzyka2;
	private Clip muzyka3;
	private Clip dzwiekKlocka;
	private int muzykiIndex = 2;
	private Timer wcisnietyPrzycisk=new Timer(800, new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			wcisnietyPrzycisk.stop();
		}
	});
	
	private Timer looper;
	private int FPS=60;
	private int delay=1000/FPS;
	
	private static Klocek aktualnyKlocek;
	private static Klocek nastepnyKlocek;
		
	
	public PanelGry() 
	{
		kostki = LadowaniePlikow.ladujObraz("/klocki.png");
		obrazTla = LadowaniePlikow.ladujObraz("/background.png");
		pauza = LadowaniePlikow.ladujObraz("/pause.png");
		restart = LadowaniePlikow.ladujObraz("/restart.png");
		muzykaWTle = LadowaniePlikow.ladujObraz("/music.png");
		dzwiekKlocka = LadowaniePlikow.zaladujDzwiek("/przycisk.wav");
		muzyka1 = LadowaniePlikow.zaladujDzwiek("/music1.wav");
		muzyka2 = LadowaniePlikow.zaladujDzwiek("/music2.wav");
		muzyka3 = LadowaniePlikow.zaladujDzwiek("/music3.wav");
		muzyka2.loop(Clip.LOOP_CONTINUOUSLY);
		
		
		
		
		
		
		ksztalt[0]=new Klocek(new int[][]{
				{1,1,1,1}
		},kostki.getSubimage(0, 0, wielkoscKostki, wielkoscKostki),this,1);
		
		ksztalt[1]=new Klocek(new int[][]{
			{1,1,1},
			{0,1,0},
			
		},kostki.getSubimage(wielkoscKostki, 0, wielkoscKostki, wielkoscKostki),this,2);
		
		ksztalt[2]=new Klocek(new int[][]{
			{1,1,1},
			{1,0,0},
			
		},kostki.getSubimage(wielkoscKostki*2, 0, wielkoscKostki, wielkoscKostki),this,3);
		
		ksztalt[3]=new Klocek(new int[][]{
			{1,1,1},
			{0,0,1},
			
		},kostki.getSubimage(wielkoscKostki*3, 0, wielkoscKostki, wielkoscKostki),this,4);
		
		ksztalt[4]=new Klocek(new int[][]{
			{0,1,1},
			{1,1,0},
			
		},kostki.getSubimage(wielkoscKostki*4, 0, wielkoscKostki, wielkoscKostki),this,5);
		
		ksztalt[5]=new Klocek(new int[][]{
			{1,1,0},
			{0,1,1},
			
		},kostki.getSubimage(wielkoscKostki*5, 0, wielkoscKostki, wielkoscKostki),this,6);
		
		ksztalt[6]=new Klocek(new int[][]{
			{1,1},
			{1,1},
			
		},kostki.getSubimage(wielkoscKostki*6, 0, wielkoscKostki, wielkoscKostki),this,7);
	
	mX=0;
	mY=0;

	muzykaLinia = new Rectangle(334, 400, 91, 30);
	restartLinia = new Rectangle(334, 450, 91, 30);
	pauzaLinia = new Rectangle(334, 500, 91, 30);
	
//	muzykaLinia = new Rectangle(328, 400, muzykaWTle.getWidth(), muzykaWTle.getHeight() + muzykaWTle.getHeight()/2);
//	restartLinia=new Rectangle(328,500-restart.getHeight()-20,restart.getWidth(),restart.getHeight()+restart.getHeight()/2);
//	pauzaLinia=new Rectangle(328,500,pauza.getWidth(),pauza.getHeight()+pauza.getHeight()/2);

	looper = new Timer(delay, new GameLooper());
	
	}	
	private void update()
	{
		
		if(muzykaLinia.contains(mX-9, mY-30) && lewyPrzycisk  && !wcisnietyPrzycisk.isRunning() && !koniecGry)		//todo dolozyc obsluge przycisku MUSIC
		{
			wcisnietyPrzycisk.start();

			if(muzykiIndex == 0)
			{
				muzyka1.start();
				muzyka1.loop(Clip.LOOP_CONTINUOUSLY);
				muzykiIndex++;
			}

			else if(muzykiIndex == 1)
			{
				muzyka1.stop();
				muzyka2.start();
				muzyka2.loop(Clip.LOOP_CONTINUOUSLY);
				muzykiIndex++;
			}

			else if(muzykiIndex == 2)
			{
				muzyka2.stop();
				muzyka3.start();
				muzyka3.loop(Clip.LOOP_CONTINUOUSLY);
				muzykiIndex++;
			}	

			else if(muzykiIndex == 3)
			{
				muzyka3.stop();																						//todo dolozyc obsluge przycisku MUSIC
				muzykiIndex = 0;
			}		
		}
		
		if(restartLinia.contains(mX-9, mY-33)&& !wcisnietyPrzycisk.isRunning()&&lewyPrzycisk)
		 {
			wcisnietyPrzycisk.start();
			startGame();
		 }
		
		if(pauzaLinia.contains(mX-9, mY-30)&& lewyPrzycisk&&!wcisnietyPrzycisk.isRunning()
				&&!koniecGry)
		{
			wcisnietyPrzycisk.start();
			pauzaGry=!pauzaGry;
		}
		
		
		if(pauzaGry||koniecGry)
		{
			return;
			
		}
		
	
		aktualnyKlocek.update();
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.drawImage(obrazTla, 0, 0, null);
		
		
		for(int row = 0; row < panelGry.length; row++)
		{
			for(int col = 0; col < panelGry[row].length; col ++)
			{
				
				if(panelGry[row][col] != 0)
				{
					
					g.drawImage(kostki.getSubimage((panelGry[row][col] - 1)*wielkoscKostki,
							0, wielkoscKostki, wielkoscKostki), col*wielkoscKostki, row*wielkoscKostki, null);
				}				
					
			}
		}
		for(int row = 0; row < nastepnyKlocek.getCoords().length; row ++)
		{
			for(int col = 0; col < nastepnyKlocek.getCoords()[0].length; col ++)
			{
				if(nastepnyKlocek.getCoords()[row][col] != 0)
				{
					g.drawImage(nastepnyKlocek.getKostka(), col*30 + 320, row*30 + 50, null);	
				}
			}		
		}
		aktualnyKlocek.render(g);
		
		
		if(muzykaLinia.contains(mX-8, mY-30))
			g.drawImage(muzykaWTle.getScaledInstance(muzykaWTle.getWidth() + 1, muzykaWTle.getHeight() + 5, BufferedImage.SCALE_DEFAULT)
					, muzykaLinia.x - 1, muzykaLinia.y - 1, null);
		else
			g.drawImage(muzykaWTle, muzykaLinia.x, muzykaLinia.y, null);
		
		
		
		
//		if(muzykaLinia.contains(mX, mY))
//			g.drawImage(muzykaWTle.getScaledInstance(muzykaWTle.getWidth() + 1, muzykaWTle.getHeight() + 5, BufferedImage.SCALE_DEFAULT)
//					, muzykaLinia.x - 1, muzykaLinia.y - 1, null);
//		else
//			g.drawImage(muzykaWTle, muzykaLinia.x, muzykaLinia.y, null);

		
		
		if(restartLinia.contains(mX-8, mY-33))
			g.drawImage(restart.getScaledInstance(restart.getWidth() + 1, restart.getHeight() + 5,
					BufferedImage.SCALE_DEFAULT), restartLinia.x - 1, restartLinia.y - 1, null);
		else
			g.drawImage(restart, restartLinia.x, restartLinia.y, null);
		
		
		if(pauzaLinia.contains(mX-8, mY-30))
			g.drawImage(pauza.getScaledInstance(pauza.getWidth() + 1, pauza.getHeight() + 5, BufferedImage.SCALE_DEFAULT)
					, pauzaLinia.x - 1, pauzaLinia.y - 1, null);
		else
			g.drawImage(pauza, pauzaLinia.x, pauzaLinia.y, null);
		
		
		
		
		if(pauzaGry)
		{
			String gamePausedString = "PAUZA";
			g.setColor(Color.WHITE);
			g.setFont(new Font("Georgia", Font.BOLD, 30));
			g.drawString(gamePausedString, 35, Okno.WYSOKOSC/2);
			
			obrazPauzy = LadowaniePlikow.ladujObraz("/backgroundP.png");
			g.drawImage(obrazPauzy, 0, 0, null);
			
		}
		if(koniecGry)
		{
			String gameOverString = "KONIEC";
			g.setColor(Color.WHITE);
			g.setFont(new Font("Verdana", Font.BOLD, 34));
			g.drawString(gameOverString, 50, Okno.WYSOKOSC/2);
		}	
		g.setColor(Color.WHITE);
		
		g.setFont(new Font("Georgia", Font.BOLD, 20));
		
		g.drawString("Punkty", Okno.SZEROKOSC - 125, Okno.WYSOKOSC/2);
		g.drawString(wynik+"", Okno.SZEROKOSC - 125, Okno.WYSOKOSC/2 + 30);				// todo rysuje kratke odtad
		
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(new Color(0, 0, 0, 100));  
		
		for(int i = 0; i <= WysokoscPaneluGry; i++)
		{
			g2d.drawLine(0, i*wielkoscKostki, SzerokoscPaneluGry*wielkoscKostki, i*wielkoscKostki);
		}
		for(int j = 0; j <= SzerokoscPaneluGry; j++)
		{
			g2d.drawLine(j*wielkoscKostki, 0, j*wielkoscKostki, WysokoscPaneluGry*30);				// todo rysuje kratke dotad
		}
	}
	public void setNastepnyKlocek() 
	{
		int index=(int)(Math.random()*ksztalt.length);
		nastepnyKlocek=new Klocek(ksztalt[index].getCoords(), ksztalt[index].getKostka(),
				this, ksztalt[index].getColor());
		
	}
	
	public void setAktualnyKlocek()
	{
		aktualnyKlocek=nastepnyKlocek;
		setNastepnyKlocek();
		for(int row = 0; row < aktualnyKlocek.getCoords().length; row ++)
		{
			for(int col = 0; col < aktualnyKlocek.getCoords()[0].length; col ++)
			{
				if(aktualnyKlocek.getCoords()[row][col] != 0)
				{
					if(panelGry[aktualnyKlocek.getY() + row][aktualnyKlocek.getX() + col] != 0)
						koniecGry = true;
				}
			}		
		}
	}
	
	public int [][] getPanelGry(){
		return panelGry;
	}
	
	public void stopGame(){
		wynik = 0;
		Klocek.normal = 250;
		
		for(int row = 0; row < panelGry.length; row++)
		{
			for(int col = 0; col < panelGry[row].length; col ++)
			{
				panelGry[row][col] = 0;
			}
		}
		looper.stop();
	}
	
	
	
	public void dodajWynik() {
		wynik++;
		Klocek.normal = 400 - wynik;			//TODO przyspieszanie gry, 300 wystarczy
	}
	
	public void startGame()
	{
		stopGame();
		setNastepnyKlocek();
		setAktualnyKlocek();
		koniecGry=false;
		looper.start();
	}
	
	class GameLooper implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			update();
			repaint();
			
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
		
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
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyChar() == 112 )
			pauzaGry = !pauzaGry;

		if(e.getKeyCode() == KeyEvent.VK_UP) {
			stuk();
			aktualnyKlocek.rotateShape();}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			stuk();
			aktualnyKlocek.setDeltaX(1);}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			stuk();
			aktualnyKlocek.setDeltaX(-1);}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			stuk();
			aktualnyKlocek.speedUp();}
	}

	
	private void stuk() 
	{
		dzwiekKlocka.loop(1);
	}
	
	
	
//		if(e.getKeyCode() == KeyEvent.VK_UP) 
//			aktualnyKlocek.rotateShape();
//		if(e.getKeyCode() == KeyEvent.VK_RIGHT) 
//			aktualnyKlocek.setDeltaX(1);
//		if(e.getKeyCode() == KeyEvent.VK_LEFT) 
//			aktualnyKlocek.setDeltaX(-1);
//		if(e.getKeyCode() == KeyEvent.VK_DOWN)
//			aktualnyKlocek.speedUp();
//		
//		if(e.getKeyCode() == KeyEvent.VK_P || e.getKeyChar() == 112 )
//			pauzaGry = !pauzaGry;
//	}
		
		
		
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			aktualnyKlocek.speedDown();
		
	}

}
