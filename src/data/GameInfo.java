package data;

import javax.swing.JButton;

public class GameInfo {
	private int row;
	private int col;
	private int value;
	private int getsu;
	JButton [][] btn;
	int [][] answer;
	JButton b;

	
	public GameInfo()
	{
		getsu=4;
		btn = new JButton [getsu][getsu];
		answer = new int[getsu][getsu];

	}
	
	public void setrow(String msg) {
		row=Integer.parseInt(msg);
	}
	public int getrow() {
		return row;
	}
	public void setcol(String msg) {
		col=Integer.parseInt(msg);
	}
	public int getcol() {
		return col;
	}
	public void setvalue(String msg) {
		value=Integer.parseInt(msg);
	}
	public void setarray() {
		answer[row][col]=value;
	}
	public int getgetsu() {
		return getsu;
	}
	public int[][] getarray() {
		return answer;
	}
	public void SetClickedButton(String msg) //상대방이 누른 버튼의 좌표 반환 (0번 버튼->[0][0])
	{
		int num=Integer.parseInt(msg);
		b= btn[num/getsu][num%getsu];
	}
	public JButton GetClickedButton()
	{
		return b;
	}
	String IntToStr(int num)
	{
		return Integer.toString(num);
		
	}
	
	
	


	

}
