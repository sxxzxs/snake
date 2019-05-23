package com.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Egg {
	int row, col;
	int w = Yard.BLOCK_SIZE;
	int h = Yard.BLOCK_SIZE;
	private Color color = Color.YELLOW;
	private static Random r = new Random();
	
	Egg(int row,int col){
		this.row = row;
		this.col = col;
	}
	
	Egg(){
		this(r.nextInt(Yard.COLS),r.nextInt(Yard.ROWS));
	}
	
	public void reAppear() {
		this.row = r.nextInt(Yard.COLS);
		this.col = r.nextInt(Yard.ROWS);
	}
	
	void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
		g.setColor(c);
	}
	
	//边界检测
	public Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
	}
	

}
