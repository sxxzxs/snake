package com.Snake;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
	public static final int ROWS  = 50;
	public static final int COLS  = 50;
	public static final int BLOCK_SIZE = 10;
	Snake snake = new Snake();
	Egg egg =  new Egg();
	Image offScreenImage = null;
	
	public void launch(){
		setLocation(200,200);
		setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		setVisible(true);
		
		//添加事件监听，功能:点拔插会关闭窗口
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}			
		});		
		
		//启动线程
		new Thread(new PaintThread()).start();
		//添加监听器
		this.addKeyListener(new keyMonitor());
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);		
		g.setColor(c);
		//画出横线
		for(int i = 1; i < ROWS; i++) {
			g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
		}
		for(int i = 1; i < COLS; i++) {
			g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, ROWS * BLOCK_SIZE);
		}
		
		//画出snake
		snake.draw(g);
		//画出蛋
		egg.draw(g);
		
		snake.eat(egg);
	}
	
	/*解决双缓冲,没必要深究，截获update,首先把画出来的东西（蛇，Egg）先画在内存的图片中，
	图片大小和游戏画面一致，然后把内存中图片一次性画到屏幕上（把内存的内容复制到显存）*/
	@Override
	public void update(Graphics g) {
		if(offScreenImage == null) {
			offScreenImage = this.createImage(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		}
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0,  null);
	}
	
	//建立一个线程来不断调用repaint()方法
	private class PaintThread implements Runnable{

		@Override
		public void run() {			
			while(true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}						
	}
	
	//建立一个监听器
	private class keyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {			
			snake.keyPressed(e);
		}
		
		
	}

	public static void main(String[] args) {
		
		new Yard().launch();
	}

}
