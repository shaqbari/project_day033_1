package com.ss.page;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Content extends JPanel{
	JLabel la;
	
	public Content() {
		la = new JLabel("세종대왕");
		
		add(la);
		
		setPreferredSize(new Dimension(700, 500));//지정해야 원하는 크기 얻는다.
		setBackground(Color.CYAN);
	}
}
