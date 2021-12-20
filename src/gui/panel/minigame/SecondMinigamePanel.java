package gui.panel.minigame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.AlreadyExist;
import data.Stone;
import gui.frame.GameFrame;

public class SecondMinigamePanel extends JPanel{
	private static final long serialVersionUID = 1L;

	/* 
	 * 바둑 판 크기 : 450*450
	 * 줄 개수 : 18개
	 * 시작 좌표 : 10, 10
	 * 끝 좌표 : 460, 460
	 * 돌 크기 : 450/18=25
	 */
	public static final int WIDTH = 450;
	public static final int HEIGHT = 450;
	private static final int LINE = 18; //총 줄의 개수-1
	public static final int SIZE = WIDTH/LINE;
	private static final int MIN_X = 10;
	private static final int MIN_Y = 10;
	private static final int MAX_X = MIN_X + WIDTH;
	private static final int MAX_Y = MIN_Y + HEIGHT;
	
	private int ghost_x = 0, ghost_y = 0;
	private boolean next_black = false; //검은돌 차례일 때, true
	private int[][] map = new int[19][19];
	
	private Vector<Stone> white_stone = new Vector<Stone>(); //흰 돌 벡터
	private Vector<Stone> black_stone = new Vector<Stone>(); //검은 돌 벡터
	
	private GameFrame gf; //패널 전환을 위해 부모 컨테이너를 가져옴
	
	public SecondMinigamePanel(GameFrame gf) {
		this.gf = gf;
		
		setBackground(new Color(206,167,61));
		MyMouseListener ml = new MyMouseListener();
		addMouseListener(ml);
		addMouseMotionListener(ml);
	}
	
	//해당 Panel을 다시 그려줄 메소드
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 원래 모양
		drawMapLine(g);
		drawStone(g);
		drawGhost(g);
	}

	//바둑판 선 그리기
	private void drawMapLine(Graphics g) {
		g.setColor(new Color(0,0,0));
		g.drawRect(MIN_X, MIN_Y, WIDTH, HEIGHT);
		
		for(int i=0;i<=LINE;i++){
			g.drawLine(MIN_X, MIN_Y + i*SIZE, MAX_X, MIN_Y + i*SIZE); //가로
			g.drawLine(MIN_X + i*SIZE, MIN_Y, MIN_X + i*SIZE, MAX_Y); //세로
		}
	}
	
	//유령 돌 그리기
	private void drawGhost(Graphics g) {
		g.drawOval(ghost_x, ghost_y, SIZE, SIZE);
	}
	
	private void drawStone(Graphics g) {
		//흰 돌 그리기
		g.setColor(Color.WHITE);
		for(Stone s: white_stone) {
			g.fillOval(s.getX(), s.getY(), SIZE, SIZE);
		}

		//검은 돌 그리기
		g.setColor(Color.BLACK);
		for(Stone s: black_stone) {
			g.fillOval(s.getX(), s.getY(), SIZE, SIZE);
		}
	}
	
	private void checkEndGame() {
		int count_white = 0; // 5가 되면 끝. 흰색이 1, 검은색이 2
		int count_black = 0;
		
		//가로 방향으로 5개 찾기
		for(int i=0;i<map.length;i++) {
			for(int j=0;j<map.length;j++) {
				switch(map[i][j]) {
				case 1:
					count_black = 0;
					count_white++;
					break;
				case 2:
					count_black++;
					count_white = 0;
					break;
				default:
					count_black = 0;
					count_white = 0;
				}
				if(count_white == 5 || count_black == 5) {
					String endStr = (count_white == 5) ? "흰돌 승리" : "검은돌 승리";
					JOptionPane.showMessageDialog(null, endStr, "게임 끝", JOptionPane.PLAIN_MESSAGE);
					gf.changePanel("menu");
					return;
				}
			}
		}
	}
	
	private class MyMouseListener extends MouseAdapter{
		
		public void mouseClicked(MouseEvent e) {
			if(e.isMetaDown()) { //우클릭시
				gf.changePanel("menu");
			}
			else {
				try {
					if(map[e.getY()/SIZE][e.getX()/SIZE] != 0) {
						throw new AlreadyExist();
					}
					
					int x = MIN_X + (int)(e.getX()/SIZE)*SIZE - SIZE/2;
					int y = MIN_Y + (int)(e.getY()/SIZE)*SIZE - SIZE/2;
					
					Stone current_stone = new Stone(x, y);
					
					if(next_black == false) {
						white_stone.add(current_stone);
					}
					else {
						black_stone.add(current_stone);
					}
					map[e.getY()/SIZE][e.getX()/SIZE] = (next_black == false) ? 1 : 2; //흰돌이면 1, 검은돌이면 2
							
					next_black = !next_black;
					
					repaint();
					checkEndGame();
				}
				catch(AlreadyExist e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "이미 돌이 존재합니다.", "오류", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		public void mouseMoved(MouseEvent e) {
			int tempx = MIN_X + (int)(e.getX()/SIZE)*SIZE - SIZE/2;
			int tempy = MIN_Y + (int)(e.getY()/SIZE)*SIZE - SIZE/2;
			ghost_x = tempx;
			ghost_y = tempy;
			repaint();
		}
	}
}
