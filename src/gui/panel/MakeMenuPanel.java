package gui.panel;

import javax.swing.JPanel;
import java.awt.SystemColor;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MakeMenuPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MakeMenuPanel() {
		setBackground(SystemColor.info);
		setLayout(null);
		
		JButton btnGame1 = new JButton("같은 그림 찾기");
		btnGame1.setBounds(141, 176, 137, 137);
		add(btnGame1);
		
		JButton btnGame2 = new JButton("오목");
		btnGame2.setBounds(59, 335, 137, 137);
		add(btnGame2);
		
		JButton btnGame3 = new JButton("두더지잡기");
		btnGame3.setBounds(227, 335, 137, 137);
		add(btnGame3);
		
		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);

	}
}
