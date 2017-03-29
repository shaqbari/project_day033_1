package com.ss.wordgame;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel implements ItemListener, Runnable, ActionListener{
	GameWindow gameWindow;
	
	JPanel p_west; //왼쪽의 컨트롤 영역
	JPanel p_center; //단어 그래픽 처리 영역
	JLabel la_user; //게임 로그인 유저명
	JLabel la_score; //게임점수
	Choice choice; //단어선택드랍박스
	JTextField t_input; //게임입력창
	JButton bt_start; //게임 시작버튼
	JButton bt_pause; //게임 멈춤버튼
	
	String res="E:/git/java_workspace/project_day033/src/com/ss/res/"; //조사할 경로
	FileInputStream fis;
	InputStreamReader reader;
	//FileReader reader; //파일을 대상으로 한 문자기반 입력스트림 (fis에서 업그레이드된 상태인놈)
	//utf-8처리 못하니 처음부터 업그레이드 하자
	BufferedReader buffer; //문자기반버퍼 스트림
	ArrayList<String> wordList=new ArrayList<String>();//조사한단어를 담아놓자! 게임에 써먹기 위해
	
	Thread thread;//단어게임을 진행할 쓰레드
	boolean flag=true; //쓰레드의
	//int y=50; //단어의 y축값 객체화시킨 Word에 넣자
	ArrayList<Word> words=new ArrayList<Word>(); //생성될 Word객체를 담을 컬렉션
	
	
	public GamePanel(GameWindow gameWindow) {
		this.gameWindow=gameWindow;		
		
		setLayout(new BorderLayout());
		
		p_west=new JPanel();
		p_center=new JPanel(){
			//이영역은 지금부터 그림을 그릴 영역!
			public void paintComponent(Graphics g) { //paint대신에 이걸 쓰자.
				//기존 그림 지우기
				g.setColor(Color.PINK);
				g.fillRect(0, 0, 750, 700);
				
				//다시 그리기
				g.setColor(Color.BLACK);				
				//모든 워드들에 대한 render()호출
				for (int i = 0; i < words.size() ; i++) {
					words.get(i).render(g);
				}
				
				//g.drawString("고등어", 200, y);				
				//지우고 다시그려야 제대로 그려진다.
				//단어가 많아지면 클래스화 시켜야 한다.
			}
		};	
		
		la_user=new JLabel("~님");
		la_score=new JLabel("~점");
		choice=new Choice();
		t_input=new JTextField(10);
		bt_start=new JButton("Start");		
		bt_pause=new JButton("Pause");
		
		p_west.setPreferredSize(new Dimension(150, 700));
		p_west.setBackground(Color.YELLOW);
		
		choice.setPreferredSize(new Dimension(130, 40));
		choice.add("▼ 단어장 선택");
		choice.addItemListener(this);
		
		bt_start.addActionListener(this);
		bt_pause.addActionListener(this);
		
		p_west.add(la_user);
		p_west.add(choice);
		p_west.add(t_input);
		p_west.add(bt_start);
		p_west.add(bt_pause);
		p_west.add(la_score);
		
		add(p_west, BorderLayout.WEST);	
		add(p_center);		
		
		setBackground(Color.PINK);
		setVisible(false);//최초에 등장안함
		setPreferredSize(new Dimension(900, 700));
		
		getCategory();
	}
	
	//초이스 컴포넌트에 채워질 파일명 조사하기
	public void getCategory(){
		File file=new File(res); //url은 안된다. uri는 되는데 나중에
		File[] files=file.listFiles(); //파일과 디렉토리가 섞여있는 배열반환.
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				String name=files[i].getName();
				String[] arr=name.split("\\.");//역슬래시를 두번써야 특수문자인.이아닌 문자.으로 인식한다.
				if (arr[1].equals("txt")) {//메모장이라면
					choice.add(name); //arr[0]으로 넣으면 확장자뺀 name이 들어간다.
				}				
			}
		}
	}
	
	//단어 읽어보기!!
	public void getWord(){
		int index=choice.getSelectedIndex();
		if(index!=0){//첫번째 요소가 아닐때
			String name=choice.getSelectedItem();
			//System.out.println(res+name);
			
			try {
				fis=new FileInputStream(res+name);
				reader = new InputStreamReader(fis, "utf-8");
				buffer=new BufferedReader(reader); //스트림을 버퍼처리 수준까지 올림
				while(true){
					String data=buffer.readLine();
					if(data==null) break;
					//System.out.println(data);
					wordList.add(data);
				}
				//준비된 단어를 화면에 보여주기
				createWord();
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
	
	public void createWord(){
		for (int i = 0; i < wordList.size(); i++) {
			String name=wordList.get(i);
			Word word = new Word(name, 10+50*i, 50);
			words.add(word); //워드 객체명단 만들기
		}
		
	}
	
	//게임시작
	public void startGame() {
		if (thread==null) {//null 상태였던 처음 한번만 실행된다.
			thread = new Thread(this);
			thread.start();
		}
	}
	
	//게임 중지
	public void pauseGame(){
				
	}
	
	//단어 내려오는 효과!
	/*public void down(){
		//y값 증가시키고
		//p_center 패널로 하여금 다시 그리게
		//y+=20;
		//p_center.repaint(); //패널을 repaint하면 이상하게 나온다. paint대신 paintComponent를 쓰자
		//하나에서는 이런식으로 가능하지만 여러개를 움직이려면 힘들다.
		
		
		//System.out.println("down()");
	}*/
	
	
	public void itemStateChanged(ItemEvent e) {
		//System.out.println("바꿨니?"); //잘하고있는지 확인
		getWord();
	}

	public void actionPerformed(ActionEvent e) {
		Object obj=e.getSource();
		if (obj==bt_start) {
			startGame();
		}else if(obj==bt_pause){
			pauseGame();
		}		
	}
	
	public void run() {		
		try {
			while(flag){
				thread.sleep(500);
				//down();
				//모든 단어들에대해서 tick()
				//모든 단어들에 대해 reapint()
				for(int i=0; i<words.size(); i++){
					words.get(i).tick();
					//words.get(i).render(g); //g때문에 여기서 호출 불가능 paint에서 호출하자
				}					
				//모든 단어들에 대해서 repaint()					
				p_center.repaint();					
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
	}
	
}
