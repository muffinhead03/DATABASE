package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class player extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	//테스트용 메인 함수. 실제로는 이용되지 않습니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player frame = new player(1,1);
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
	public player(int idTeam, int idPlayer) {
		
		// 선수 메뉴 고르기 창입니다.
		//idTeam은 자신이 고른 팀 id, idPlayer은 자신이 고른 선수 id를 표시합니다
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 41, 438, 45);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		//클릭시 나의 정보 창으로 이동합니다.
		JButton btnNewButton = new JButton("나의 정보");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myInfo(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 86, 438, 180);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(3, 2));
		
		//클릭시 나의 출전 경기 창으로 이동합니다.
		JButton btnNewButton_1 = new JButton("나의 출전 경기");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myGame(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		panel_1.add(btnNewButton_1);
		
		//클릭 시 나의 전술 정보 창으로 이동합니다
		JButton btnNewButton_2 = new JButton("나의 전술 정보");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myTactics(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		panel_1.add(btnNewButton_2);
		
		//클릭 시 선수 정보 창으로 이동합니다
		JButton btnNewButton_3 = new JButton("선수 정보");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_viewPlayers(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		panel_1.add(btnNewButton_3);
		
		//클릭 시 팀 정보 창으로 이동합니다
		JButton btnNewButton_4 = new JButton("팀 정보");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_viewTeams(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		panel_1.add(btnNewButton_4);
		
		//클릭 시 경기 기록 창으로 이동합니다
		JButton btnNewButton_5 = new JButton("경기 기록");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_viewGames(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		panel_1.add(btnNewButton_5);
		
		//클릭 시 전체 전술 정보 창으로 이동합니다
		JButton btnNewButton_6 = new JButton("전체 전술 정보");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_viewTactics(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		panel_1.add(btnNewButton_6);
		
		//뒤로 가기 버튼, 클릭 시 처음 화면으로 이동합니다
		JButton btnNewButton_7 = new JButton("Back");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DKicker().setVisible(true); dispose();
			}
		});
		btnNewButton_7.setBounds(0, 0, 117, 29);
		contentPane.add(btnNewButton_7);
	}
}
