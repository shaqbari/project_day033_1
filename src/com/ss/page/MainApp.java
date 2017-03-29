package com.ss.page;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainApp extends JFrame implements ActionListener{
	JPanel p_north, p_center; //p_center에는 페이지들이 붙을예정 
	URL[] url=new URL[3];
	String[] path={
		"/login.png",
		"/content.png",
		"/etc.png"			
	};
	JButton[] menu= new JButton[3]; // bt_login, bt_content, bt_etc;	
	//LoginForm loginForm;
	//Content content;
	//Etc etc;
		
	JPanel[] page= new JPanel[3];
	//ArrayList<JPanel> pages;
	
	public MainApp() {
		p_north=new JPanel();		
		for (int i = 0; i < path.length; i++) {
			url[i]=this.getClass().getResource(path[i]);
			menu[i]=new JButton(new ImageIcon(url[i]));
			p_north.add(menu[i]);			
			menu[i].addActionListener(this);//버튼과 리스너 연결
		}
		//ImageIcon icon=new ImageIcon(this.getClass().getResource("/login.png");)
		//bt_login=new JButton("로그인");
		//bt_content=new JButton("내용");
		//bt_etc=new JButton("기타");
		//배열로가자!				
		
		
		//버튼 누르면서 패널이 만들어지면 누를때마다 생성되 메모리 낭비될 수 있다.
		//버튼이 누르기전에 패널들이 올라와있고 버튼누르면 보여주는걸 바꿔야 한다.
		/*p_center=new JPanel();
		loginForm=new LoginForm();
		content=new Content();
		etc=new Etc();	
				
		p_center.add(loginForm);	//로그인 폼 생성		
		p_center.add(content); //컨텐트 생성boderlayout이면 위의 로그인폼은 안보이게 된다. 
		p_center.add(etc);//그러므로 따로 jpanel을 만들어 거기에 넣자.
		*/		
			
		p_center=new JPanel();
		page[0]=new LoginForm();
		page[1]=new Content();
		page[2]=new Etc();
		
		for (int i = 0; i < page.length; i++) {
			p_center.add(page[i]);
		}
		
		/*p_center.add(page[0]);		
		p_center.add(page[1]);
		p_center.add(page[2]);*/

		/*pages=new ArrayList<JPanel>();
		pages.add(loginForm);
		pages.add(content);
		pages.add(etc);*/
		
		add(p_north, BorderLayout.NORTH);	
		add(p_center);
		
		setSize(700, 680);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		/*if (obj==menu[0]) {
			//로그인폼 o
			//컨텐츠 x
			//기타 x
			loginForm.setVisible(true);
			content.setVisible(false);
			etc.setVisible(false);
			
		}else if (obj==menu[1]) {
			//로그인폼 x
			//컨텐츠 o
			//기타 x	
			loginForm.setVisible(false);
			content.setVisible(true);
			etc.setVisible(false);
			
		}else if (obj==menu[2]) {
			//로그인폼 x
			//컨텐츠 x
			//기타 o
			loginForm.setVisible(false);
			content.setVisible(false);
			etc.setVisible(true);
		}*/
		
		/*if (obj==menu[0]) {
			//로그인폼 o
			//컨텐츠 x
			//기타 x
			page[0].setVisible(true);
			page[1].setVisible(false);
			page[2].setVisible(false);
			
		}else if (obj==menu[1]) {
			//로그인폼 x
			//컨텐츠 o
			//기타 x	
			page[0].setVisible(false);
			page[1].setVisible(true);
			page[2].setVisible(false);
			
		}else if (obj==menu[2]) {
			//로그인폼 x
			//컨텐츠 x
			//기타 o
			page[0].setVisible(false);
			page[1].setVisible(false);
			page[2].setVisible(true);
		}*/
		
		for (int i = 0; i < page.length; i++) {
			if(obj==menu[i]){			
				page[i].setVisible(true);
			}else {
				page[i].setVisible(false);
			}
		}
		
		/*for (int i = 0; i < pages.size(); i++) {
			if(obj==menu[i]){			
				pages.get(i).setVisible(true);
			}else {
				pages.get(i).setVisible(false);
			}
		}*/		
		
	}
	
	public static void main(String[] args) {
		new MainApp();
	}


}
