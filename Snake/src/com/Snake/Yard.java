package com.Snake;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Yard extends Frame {
	private static final int ROWS  = 50;
	private static final int COLS  = 50;
	private static final int BLOCK_SIZE = 10;
	
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
	}

	public static void main(String[] args) {
		
		new Yard().launch();
	}

}
