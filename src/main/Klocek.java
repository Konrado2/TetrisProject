package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class Klocek {
	
	private int [][] coords; 
	private int [][] reference;
	private BufferedImage kostka;
	private PanelGry panelGry;
	private int kolor;
	private int x=0;
	private int y =0;
	private int deltaX;
	private int opoznienie;
	private long time;
	private long lastTime;
	static int normal =250;
	private int fast=30;
	
	
	
	
	
	private boolean kolizja=false;
	private boolean ruchWOsiX=false;
	
	public Klocek(int[][] coords, BufferedImage kostka, PanelGry panelGry, int kolor) {
		
		this.coords = coords;
		this.kostka = kostka;
		this.panelGry = panelGry;
		this.kolor = kolor;
		deltaX=0;
		x=4;
		y=0;
		opoznienie=normal;
		time=0;
		lastTime=System.currentTimeMillis();
		
		reference=new int[coords.length][coords[0].length];
		System.arraycopy(coords, 0, reference,0, coords.length);
		
	}
	public void update(){
		ruchWOsiX = true;
		time += System.currentTimeMillis() - lastTime;
		lastTime = System.currentTimeMillis();
		
		if(kolizja)
		{
			for(int row = 0; row < coords.length; row ++)
			{
				for(int col = 0; col < coords[0].length; col ++)
				{
					if(coords[row][col] != 0)
						panelGry.getPanelGry()[y + row][x + col] = kolor;
				}
			}
			checkLine();
			panelGry.dodajWynik();
			panelGry.setAktualnyKlocek();;
		}
		
		if(!(x + deltaX + coords[0].length > 10) && !(x + deltaX < 0))
		{
			
			for(int row = 0; row < coords.length; row++)
			{
				for(int col = 0; col < coords[row].length; col ++)
				{
					if(coords[row][col] != 0)
					{
						if(panelGry.getPanelGry()[y + row][x + deltaX + col] != 0)
						{
							ruchWOsiX = false;
						}
						
					}
				}
			}
			
			if(ruchWOsiX)
				x += deltaX;
			
		}
		
		if(!(y + 1 + coords.length > 20))
		{
			
			for(int row = 0; row < coords.length; row++)
			{
				for(int col = 0; col < coords[row].length; col ++)
				{
					if(coords[row][col] != 0)
					{
						
						if(panelGry.getPanelGry()[y + 1 + row][x +  col] != 0)
						{
							kolizja = true;
						}
					}
				}
			}
			if(time > opoznienie)
				{
					y++;
					time = 0;
				}
		}else{
			kolizja = true;
		}
		
		deltaX = 0;
	}
	
public void render(Graphics g){
		
		for(int row = 0; row < coords.length; row ++)
		{
			for(int col = 0; col < coords[0].length; col ++)
			{
				if(coords[row][col] != 0)
				{
					g.drawImage(kostka, col*30 + x*30, row*30 + y*30, null);	
				}
			}		
		}
		
		for(int row = 0; row < reference.length; row ++)
		{
			for(int col = 0; col < reference[0].length; col ++)
			{
				if(reference[row][col] != 0)
				{
					g.drawImage(kostka, col*30 + 320, row*30 + 160, null);	
				}	
				
			}
				
		}

	}

public void rotateShape()
{
	
	int[][] rotatedShape = null;
	
	rotatedShape = transposeMatrix(coords);
	
	rotatedShape = reverseRows(rotatedShape);
	
	if((x + rotatedShape[0].length > 10) || (y + rotatedShape.length > 20))
	{
		return;
	}
	
	for(int row = 0; row < rotatedShape.length; row++)
	{
		for(int col = 0; col < rotatedShape[row].length; col ++)
		{
			if(rotatedShape[row][col] != 0)
			{
				if(panelGry.getPanelGry()[y + row][x + col] != 0)
				{
					return;
				}
			}
		}
	}
	coords = rotatedShape;
}


private int[][] transposeMatrix(int[][] matrix){
    int[][] temp = new int[matrix[0].length][matrix.length];
    for (int i = 0; i < matrix.length; i++)
        for (int j = 0; j < matrix[0].length; j++)
            temp[j][i] = matrix[i][j];
    return temp;
}
private int[][] reverseRows(int[][] matrix){
	
	int middle = matrix.length/2;
	
	
	for(int i = 0; i < middle; i++)
	{
		int[] temp = matrix[i];
		
		matrix[i] = matrix[matrix.length - i - 1];
		matrix[matrix.length - i - 1] = temp;
	}
	
	return matrix;
	
}
	
	
	private void checkLine(){
		int size = panelGry.getPanelGry().length - 1;
		
		for(int i = panelGry.getPanelGry().length - 1; i > 0; i--)
		{
			int count = 0;
			for(int j = 0; j < panelGry.getPanelGry()[0].length; j++)
			{
				if(panelGry.getPanelGry()[i][j] != 0)
					count++;
				
				panelGry.getPanelGry()[size][j] = panelGry.getPanelGry()[i][j];
			}
			if(count < panelGry.getPanelGry()[0].length)
				size --;
		}
	}
	
	
	
	
	public int[][] getCoords() {
		return coords;
	}
	public void setCoords(int[][] coords) {
		this.coords = coords;
	}
	public BufferedImage getKostka() {
		return kostka;
	}
	public void setKostka(BufferedImage kostka) {
		this.kostka = kostka;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getColor() {
		return kolor;
	}
	public void setColor(int color) {
		this.kolor = color;
	}
	public int getDeltaX() {
		return deltaX;
	}
	public void setDeltaX(int deltaX) {
		this.deltaX = deltaX;
	}
	
	public void speedUp(){
		opoznienie = fast;
	}
	
	public void speedDown(){
		opoznienie = normal;
	}
	
	
	

}
