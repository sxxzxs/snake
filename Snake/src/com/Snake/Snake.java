package com.Snake;

import java.awt.Color;
import java.awt.Graphics;

public class Snake {
	private Node head  = null;
	private Node tail = null;
	private int size = 0;
	
	private Node node = new Node(20, 30, Dir.L);
	public Snake() {
		head = node;
		tail = node;
		size = 1;
	}
	
	public void addToTail() {
		Node node = null;
		switch(tail.dir) {
		case L:
			node = new Node(tail.row,tail.col + 1,tail.dir);
		case U:
			node = new Node(tail.row + 1,tail.col,tail.dir);
		case R:
			node = new Node(tail.row,tail.col - 1,tail.dir);
		case D:
			node = new Node(tail.row - 1,tail.col,tail.dir);
		}
		tail.next = node;
		tail = node;
	}
	
	public void addToHead() {
		Node node = null;
		switch(head.dir) {
		case L:
			node = new Node(head.row,head.col - 1,head.dir);
		case U:
			node = new Node(head.row - 1,head.col,head.dir);
		case R:
			node = new Node(head.row,head.col + 1,head.dir);
		case D:
			node = new Node(head.row + 1,head.col,head.dir);
		}
		head.pre = node;
		head = node;
	}
	
	public void draw(Graphics g) {
		if(size <= 0) return;
		for(Node node = head; node != null; node = node.next) {
			node.draw(g);
		}
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

}
