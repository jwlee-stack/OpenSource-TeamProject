package gui.panel;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import gui.frame.GameFrame;

/**
 * 메뉴 Panel. 해당 화면에서 랭크 확인 및 게임 시작을 할 수 있음
 * @author phdljr
 *
 */
public class MenuPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public MenuPanel(GameFrame gf) {
		setBackground(Color.GREEN);
		add(new JLabel("메뉴 화면"));
	}
}
