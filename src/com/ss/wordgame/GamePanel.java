package com.ss.wordgame;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel implements ItemListener{
	GameWindow gameWindow;
	
	JPanel p_west; //������ ��Ʈ�� ����
	JPanel p_center; //�ܾ� �׷��� ó�� ����
	JLabel la_user; //���� �α��� ������
	JLabel la_score; //��������
	Choice choice; //�ܾ�õ���ڽ�
	JTextField t_input; //�����Է�â
	JButton bt_start; //���� ���۹�ư
	JButton bt_pause; //���� �����ư
	
	String res="E:/git/java_workspace/project_day033/src/com/ss/res/"; //������ ���
	FileInputStream fis;
	InputStreamReader reader;
	//FileReader reader; //������ ������� �� ���ڱ�� �Է½�Ʈ�� (fis���� ���׷��̵�� �����γ�)
	//utf-8ó�� ���ϴ� ó������ ���׷��̵� ����
	BufferedReader buffer; //���ڱ�ݹ��� ��Ʈ��
	ArrayList<String> wordList=new ArrayList<String>();//�����Ѵܾ ��Ƴ���! ���ӿ� ��Ա� ����
	
	public GamePanel(GameWindow gameWindow) {
		this.gameWindow=gameWindow;		
		
		setLayout(new BorderLayout());
		
		p_west=new JPanel();
		p_center=new JPanel(){
			//�̿����� ���ݺ��� �׸��� �׸� ����!
			public void paint(Graphics g) {
				g.drawString("����", 200, 500);
			}
		};	
		
		la_user=new JLabel("~��");
		la_score=new JLabel("~��");
		choice=new Choice();
		t_input=new JTextField(10);
		bt_start=new JButton("Start");		
		bt_pause=new JButton("Pause");
		
		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.YELLOW);
		
		choice.setPreferredSize(new Dimension(130, 40));
		choice.add("�� �ܾ��� ����");
		choice.addItemListener(this);
		
		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);
		
		add(p_west, BorderLayout.WEST);	
		add(p_center);
		
		setBackground(Color.PINK);
		setVisible(false);//���ʿ� �������
		setPreferredSize(new Dimension(900, 700));
		
		//getCategory();
	}
	
	//���̽� ������Ʈ�� ä���� ���ϸ� �����ϱ�
	public void getCategory(){
		File file=new File(res); //url�� �ȵȴ�. uri�� �Ǵµ� ���߿�
		File[] files=file.listFiles(); //���ϰ� ���丮�� �����ִ� �迭��ȯ.
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String name=files[i].getName();
				String[] arr=name.split("\\.");//�������ø� �ι���� Ư��������.�̾ƴ� ����.���� �ν��Ѵ�.
				if (arr[1].equals("txt")) {//�޸����̶��
					choice.add(name); //arr[0]���� ������ Ȯ���ڻ� name�� ����.
				}				
			}
		}
	}
	
	//�ܾ� �о��!!
	public void getWord(){
		int index=choice.getSelectedIndex();
		if(index!=0){//ù��° ��Ұ� �ƴҶ�
			String name=choice.getSelectedItem();
			//System.out.println(res+name);
			
			try {
				fis=new FileInputStream(res+name);
				reader = new InputStreamReader(fis, "utf-8");
				buffer=new BufferedReader(reader); //��Ʈ���� ����ó�� ���ر��� �ø�
				while(true){
					String data=buffer.readLine();
					if(data==null) break;
					//System.out.println(data);
					wordList.add(data);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (buffer!=null) {
					try {
						buffer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (reader!=null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fis!=null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}		

	public void itemStateChanged(ItemEvent e) {
		//System.out.println("�ٲ��?"); //���ϰ��ִ��� Ȯ��
		getWord();
	}
	
}
