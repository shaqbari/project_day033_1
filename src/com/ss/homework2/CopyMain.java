package com.ss.homework2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

public class CopyMain extends JFrame implements ActionListener, Runnable{
	JProgressBar bar;
	JButton bt_open, bt_save, bt_copy;
	JTextField t_open, t_save;
	JFileChooser chooser;
	File file; //읽어들일 파일
	Thread thread; //복사를 실행할 전용쓰레드
	//메인 메소드는 우리가 알고있는 그 실행부라 불리는 어플리케이션의 운영을 담당하는 역할을 수행한다.
	//따라서 절대 무한루프나 대기상태에 빠지게 해서는 안된다.!!
	
	long total; //원본파일의 전체 용량
	
	public CopyMain() {
		setLayout(new FlowLayout());
		
		bar=new JProgressBar();
		bt_open=new JButton("열기");
		bt_save=new JButton("저장");
		bt_copy=new JButton("복사");
		t_open=new JTextField(35);
		t_save=new JTextField(35);
		chooser=new JFileChooser("D:/git/java_workspace/project_day033/src/com/ss/res/");
		
		bar.setPreferredSize(new Dimension(450, 50));
		bar.setBackground(Color.YELLOW);
		bar.setStringPainted(true);
		bar.setString("0%"); //알아서찾아하자		
		
		add(bar);
		add(bt_open);
		add(t_open);
		add(bt_save);
		add(t_save);
		add(bt_copy);

		//버튼과 리스너 연결
		bt_open.addActionListener(this);
		bt_save.addActionListener(this);
		bt_copy.addActionListener(this);
		
		setSize(500,200);
		setVisible(true);
		setLocationRelativeTo(null); //null을 쓰면 다른 객체에 상대적이지 않고 정중앙에 뜬다.
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
	}
	
	public void actionPerformed(ActionEvent e) {
	    Object obj=e.getSource(); //이벤트를 일으킨 이벤트 소스(이벤트 주체)
		if(obj==bt_open){
			open();			
		}else if(obj==bt_save){
			save();			
		}else if(obj==bt_copy){
			//copy();
			//메인이 직접 복사를 수행하지 말고 쓰레드에게 시키자!
			//쓰레드 생성자에 Runnable 구현객체를 인수로 넣으면,
			//Runnable객체에서 재정의한 run()메소드를 수행한다.
			//thread = new Thread();
			//thread.start();//이렇게 하면 방금생성한 쓰레드 시작시킴
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void open(){
		int result=chooser.showOpenDialog(this);
		if (result==JFileChooser.APPROVE_OPTION) {
			file=chooser.getSelectedFile();
			t_open.setText(file.getAbsolutePath());
			total=file.length();
		}		
	}
	
	public void save(){
		int result=chooser.showOpenDialog(this);
		if (result==JFileChooser.APPROVE_OPTION) {
			File file=chooser.getSelectedFile();
			t_save.setText(file.getAbsolutePath());
		}		
	}
	public void copy(){
		FileInputStream fis=null;//지역변수는 자동초기화되지 않는다.
		FileOutputStream fos=null;
		
		try {
			fis=new FileInputStream(file); //파일에 빨때 꽂았다.
			fos=new FileOutputStream(t_save.getText());
						
			//생성된 스트림을 통해 데이터 읽기			
			int data;
			int count=0;
			while(true){ //main에가 무한루프쓰면 메인쓰레드가 묶여버려 다른작업 못한다. 무한루프는 다른쓰레드로 빼야한다.
				data=fis.read(); //1byte 읽기				
				if(data==-1) break;
				count++;
				fos.write(data); //1byte 출력				
				int v=(int)getPercent(count); //bar.setValue의 인자로 넣기위해 강제형변환
				
				//프로그래스바에 적용
				bar.setValue(v);
				bar.setString(v+"%");				
			}
			JOptionPane.showMessageDialog(this, "복사완료");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fos!=null){
				try {
					fos.close(); //작업 다 끝났으면 닫는것이 원칙
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis!=null){
				try {
					fis.close(); //fis도 닫아줘야 한다.
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
	}
	
	//현재 진행율 구하기 공식
	//진행율=100%*현/전체크기
	public long getPercent(int currentRead){
		return (100*currentRead)/total; //인트가 long이되어야 손실이 없이 자동형변환된다.
	}
	
	public void run() {
		copy();
	}
	
	public static void main(String[] args) {
		new CopyMain();
	}

}
