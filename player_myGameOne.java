package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class player_myGameOne extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	//테스트용 메인 함수
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player_myGameOne frame = new player_myGameOne(1,1,1);
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
	public player_myGameOne(int idTeam, int idPlayer, int idGame) {
		//선수 메뉴
		//2. 경기 기옥 조회
		//UI 설정입니다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		//뒤로 가기 버튼, 선택시 선수 메뉴 창으로 이동합니다.
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myGame(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 47, 438, 219);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		//클릭 시, 경기 상세 기록 창으로 이동합니다
		JButton btnNewButton_1 = new JButton("경기 상세 기록");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myGameDetail(idTeam, idPlayer, idGame).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);
		
		//클릭 시, 경기에 사용된 전술 정보 창으로 이동합니다.
		JButton btnNewButton_2 = new JButton("경기에 사용된 전술 정보");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new player_myGameTactics(idTeam, idPlayer, idGame).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);
	}

}
