package com.njr.game.engine.io;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

import com.njr.game.GameMain;

public class Window{

	private static final long serialVersionUID = 1L;
	
	public Window(int width, int height, String title, GameMain game) {
		JFrame frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width, height));
		frame.setMaximumSize(new Dimension(width, height));
		frame.setMinimumSize(new Dimension(width, height));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);
		frame.setVisible(true);
		game.start();
	}

}
