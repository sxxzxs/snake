package com.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

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
		this(randInt(3, 48),randInt(3,48));
	}
	
	public void reAppear() {
		this.row = randInt(3, 48);
		this.col = randInt(3, 48);
	}
	
	void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(color);
		g.fillOval(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
		g.setColor(c);
		if(color == Color.YELLOW) color = Color.RED;
		else color = Color.YELLOW;
	}
	
	//边界检测
	public Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
	}
	
	//伪随机器,有些蛋出来被窗口遮挡住了，用这个来控制出现的位置
	public static int randInt(int min,int max) {
		//Random rand  = new Random();
		//int randomNum = rand.nextInt((max - min) + 1) + min;
		//int randomNum = min + (int)(Math.random() * ((max - min) + 1));
		int randomNum = ThreadLocalRandom.current().nextInt(min, max + 1);
		return randomNum;
	}

}
