package com.Snake;

import java.awt.Color;
import java.awt.Font;
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
	public boolean gameover = false;	//游戏是否结束
	public int score = 0;
	Snake snake = new Snake(this);
	Egg egg =  new Egg();
	Image offScreenImage = null;
	PaintThread paintThread = new PaintThread();
	private Font fontGameOver = new Font("宋体", Font.BOLD, 50);
	
	public void launch(){
		setLocation(200,200);
		setSize(COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);
		setVisible(true);
		setTitle("岳小佳的蛇皮走位(按F1可重新开始)");
		
		//添加事件监听，功能:点拔插会关闭窗口
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}			
		});		
		
		//启动线程
		new Thread(paintThread).start();
		//添加监听器
		this.addKeyListener(new keyMonitor());
	}
	
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(0, 0, COLS * BLOCK_SIZE, ROWS * BLOCK_SIZE);	
		g.setColor(Color.DARK_GRAY);
		
		//画出横线
		for(int i = 1; i < ROWS; i++) {
			g.drawLine(0, BLOCK_SIZE * i, COLS * BLOCK_SIZE, BLOCK_SIZE * i);
		}
		for(int i = 1; i < COLS; i++) {
			g.drawLine(BLOCK_SIZE * i, 0, BLOCK_SIZE * i, ROWS * BLOCK_SIZE);
		}		
		
		g.setColor(Color.YELLOW);
		
		g.drawString("score:" + score, 10, 60);
		
		if(gameover) {
			g.setFont(fontGameOver);
			g.drawString("游戏结束", 150, 270);
			paintThread.gameOver();
		}
		
		//new Color(204,204,255)
		g.setColor(Color.YELLOW);
		//画出墙
		//左
		for(int i = 1;i <ROWS;i++) {
			g.fillRect(0, i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		}
		
		//右
		for(int i = 1;i <ROWS;i++) {
			g.fillRect((COLS-1) * BLOCK_SIZE , i * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		}
		
		//上
		for(int i = 1; i < COLS;i++) {
			g.fillRect(i * BLOCK_SIZE, BLOCK_SIZE *3-8,BLOCK_SIZE, BLOCK_SIZE);
		}
		
		for(int i = 1; i < COLS;i++) {
			g.fillRect(i * BLOCK_SIZE, BLOCK_SIZE * (ROWS-1),BLOCK_SIZE, BLOCK_SIZE);
		}
		
		g.setColor(c);
		
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
		public boolean running = true;	//线程是否继续运行
		private boolean pause = false;
		@Override
		public void run() {			
			while(running) {
				if(pause) continue;
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}						
		}	
		
		public void gameOver() {
			running = false;
		}
		
		public void pause() {
			this.pause = true;
		}
		
		public void reStart() {
			this.pause = false;
			snake = new Snake(Yard.this);
			gameover = false;
			running = true;
			launch();
		}
	}
	
	//建立一个监听器
	private class keyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {			
			snake.keyPressed(e);
		}
		
		
	}
	
	/**
	 * 拿到所得的分数
	 * @return 分数
	 */
	
	public int getScore() {
		return score;
	}
	
	/**
	 * 设置所得的分数
	 * @param score 分数
	 */
	
	public void setScore(int score) {
		this.score = score;
	}
	
	public void stop() {
		gameover  = true;
		
	}

	public void Restart() {
		paintThread.reStart();
		
	}

	public static void main(String[] args) {
		
		new Yard().launch();
	}
	

}
