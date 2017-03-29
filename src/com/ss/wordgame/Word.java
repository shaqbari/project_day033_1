/*게임에 등장할 대상 단어가 각각 y축을 따로 갖고,
 * 대량으로 만들어져야 하기 때문에
 * 결국 재사용성을 위한 코드집합인 클래스로 가자
 * */

package com.ss.wordgame;

import java.awt.Graphics;

public class Word {
	String name; //이 객체가 담게될 단어!!
	int x;
	int y;
	int velX;
	int velY;  //단어가 움직일 속도	
	//int interval; 인터발은 쓰레드 하나만 만들어 하나만 있는것이 좋다
	//게임을 만들때는 1. 데이터의 변화, 2. 다시그리기

	//이 단어가 태어날 때 갖추어야 할 초기화할 값
	public Word(String name, int x, int y) {
		this.name=name;
		this.x=x;
		this.y=y;
	}
	
	//1.이 객체에 반영될 데이터 변화코드
	public void tick(){
		y+=5;		
	}
	
	//2.그 반영된 데이터를 이용하여 화면에 그리기
	public void render(Graphics g){
		g.drawString(name, x, y);
	}
}
