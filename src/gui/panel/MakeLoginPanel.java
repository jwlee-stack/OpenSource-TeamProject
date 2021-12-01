package gui.panel;

import java.awt.Font;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MakeLoginPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public MakeLoginPanel() {
		setBackground(SystemColor.activeCaption);
		setLayout(null);
		setSize(800, 600);
		
		JLabel lbTitle = new JLabel("꾸러미 동산");
		lbTitle.setFont(new Font("한컴 윤체 B", Font.PLAIN, 44));
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setBounds(157, 36, 485, 60);
		add(lbTitle);
		
		JButton btnKakaoLogin = new JButton("New button");
		btnKakaoLogin.setBounds(238, 291, 332, 106);
		add(btnKakaoLogin);
	}

}
