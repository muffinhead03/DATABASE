package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class staff extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String selectedTeam;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff frame = new staff(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public staff(int idTeam) {
		
        	//idTeam은 자신이 고른 팀 id를 표시하고, 그를 이용해서 staff(int idTeam)함수를 수행한다. 

		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 41, 438, 225);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2));

		//선수 정보 버튼입니다. 
		JButton btnNewButton_1 = new JButton("선수 정보");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewPlayers(idTeam).setVisible(true); dispose();
					//선수정보 창을 열 수 있습니다
			}
		});
		panel.add(btnNewButton_1);

		//선수 관리를 관리하는 창을 띄우는 버튼이다.
		JButton btnNewButton_2 = new JButton("선수 관리");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerManage(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);

		//팀 정보를 알 수 있는 창을 띄우는 버튼이다. 
		JButton btnNewButton_3 = new JButton("팀 정보");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTeams(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_3);

		//팀 관리하는 창을 띄우는 버튼이다. 
		JButton btnNewButton_4 = new JButton("팀 관리");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_teamManage(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_4);

		//경기 기록 관련
		JButton btnNewButton_5 = new JButton("경기 기록");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewGames(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_5);

		
		JButton btnNewButton_7 = new JButton("경기 기록 관리");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameManage(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_7);
		
		JButton btnNewButton_6 = new JButton("전술 정보");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_6);
		
		JButton btnNewButton_8 = new JButton("전술 정보 관리");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_tacticManage(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_8);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DKicker().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
	}
}
