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
					staff frame = new staff();
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
	public staff() {
		
        

		
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
		
		JButton btnNewButton_1 = new JButton("선수 정보");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewPlayers().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("선수 관리");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerManage().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("팀 정보");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTeams().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("팀 관리");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_teamManage().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("경기 기록");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewGames().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_5);
		
		JButton btnNewButton_7 = new JButton("경기 기록 관리");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameManage().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_7);
		
		JButton btnNewButton_6 = new JButton("전술 정보");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_6);
		
		JButton btnNewButton_8 = new JButton("전술 정보 관리");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_tacticManage().setVisible(true); dispose();
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
