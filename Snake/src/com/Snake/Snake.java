package com.Snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Snake {
	private Node head  = null;
	private Node tail = null;
	private int size = 0;
	private Yard y;
	
	private Node node = new Node(20, 30, Dir.D);
	
	public Snake(Yard y) {
		head = node;
		tail = node;
		size = 1;
		this.y = y;
		
	}
	
	//在尾部添加Node
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case L:
			node = new Node(tail.row,tail.col + 1,tail.dir);
			break;
		case U:
			node = new Node(tail.row + 1,tail.col,tail.dir);
			break;
		case R:
			node = new Node(tail.row,tail.col - 1,tail.dir);
			break;
		case D:
			node = new Node(tail.row - 1,tail.col,tail.dir);
			break;
		}
		tail.next = node;
		node.pre = tail;
		tail = node;
		size++;
	}
	
	//在头部添加Node
	public void addToHead() {
		Node node = null;
		switch(head.dir) {
		case L:
			node = new Node(head.row,head.col - 1,head.dir);
			break;
		case U:
			node = new Node(head.row - 1,head.col,head.dir);
			break;
		case R:
			node = new Node(head.row,head.col + 1,head.dir);
			break;
		case D:
			node = new Node(head.row + 1,head.col,head.dir);
			break;
		}
		
		node.next = head;
		head.pre = node;		
		head = node;
		size++;
	}
	
	//画出snake并移动
	public void draw(Graphics g) {
		if(size <= 0) return;
		move();
		for(Node n = head; n != null; n = n.next) {
			n.draw(g);
		}		
	}
	
	//移动可以看做是在移动方向的头部加一个Node,然后把尾部的Node删掉
	private void move() {
		if(!y.pause) {
		addToHead();
		deleteFromTail();
		checkDead();
		}
	}
	
	
	private void checkDead() {
		if(head.row < 3 || head.col < 1 || head.row > Yard.ROWS-2 || head.col > Yard.COLS-2)  {
			y.stop();
		}
		
		//检测自己与自己有没有相撞
		for(Node n = head.next; n != null; n = n.next) {
			if(head.row == n.row && head.col == n.col) {
				y.stop();
			}
		}
		
	}

	//从尾部删除
	private void deleteFromTail() {
		if(size == 0) return;
		tail = tail.pre;
		tail.next = null;
				
	}
	
	//吃掉,相当于在头部加一Node，并让蛋重新随机出现
	public void eat(Egg e) {
		if(this.getRect().intersects(e.getRect())) {
			e.reAppear();
			this.addToHead();
			y.setScore(y.getScore() + 5);
		}
	}
	
	//边界检测
	private Rectangle getRect() {
		return new Rectangle(Yard.BLOCK_SIZE * head.col, Yard.BLOCK_SIZE * head.row, head.w, head.h);
	}

	private class Node{
		int w = Yard.BLOCK_SIZE;
		int h = Yard.BLOCK_SIZE;
		int row, col;
		Dir dir = Dir.L;
		Node next = null;
		Node pre  = null;
		
		Node(int row ,int col,Dir dir){
			this.row = row;
			this.col = col;
			this.dir = dir;
		}
		
		void draw(Graphics g) {
			Color c = g.getColor();
			g.setColor(Color.black);
			g.fillRect(Yard.BLOCK_SIZE * col, Yard.BLOCK_SIZE * row, w, h);
			g.setColor(c);
		}
		
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
		case KeyEvent.VK_LEFT :
			if(head.dir != Dir.R)
				head.dir = Dir.L;
			break;
		case KeyEvent.VK_UP :
			if(head.dir != Dir.D)
				head.dir = Dir.U;
			break;
		case KeyEvent.VK_RIGHT :
			if(head.dir != Dir.L)
				head.dir = Dir.R;
			break;
		case KeyEvent.VK_DOWN :
			if(head.dir != Dir.U)
				head.dir = Dir.D;
			break;
		case KeyEvent.VK_F1:			
			y.Restart();
		case KeyEvent.VK_SPACE:
			y.suspend();
		}
		
	}

}
