package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

public class staff_gameManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//teamId 0으로 테스트 실행합니다. 
					staff_gameManage frame = new staff_gameManage(0);
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
	public staff_gameManage(int idTeam) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 69, 438, 197);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		//경기 기록 생성하는 버튼입니다. 
		JButton button = new JButton("경기 기록 생성");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//경기 생성 화면으로 이동합니다.
				new staff_gameCreate(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(button);

		//경기 기록 수정하는 버튼입니다. 
		JButton btnNewButton_1 = new JButton("경기 기록 수정");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//경기 수정 화면으로 이동합니다. 
				new staff_gameSearch_Edit(idTeam).setVisible(true); dispose();
			}
		});

		//경기 통계 생성하는 버튼입니다. 
		JButton btnNewButton_2 = new JButton("경기 통계 데이터 생성");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//통계를 생성하는 화면(staff_gameStatisticsCreate(idTeam))으로 이동한다
				new staff_gameStatisticsCreate(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);

		//출전 선수 명단을 생성하는 버튼입니다. 
		JButton btnNewButton_3 = new JButton("경기 출전 선수 명단 생성");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//선수 명단 입력 화면(staff_gamePlayerListCreate(idTeam)으로 이동합니다.
				new staff_gamePlayerListCreate(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_3);
		panel.add(btnNewButton_1);

		//팀 간의 경기 요약을 볼 수 있습니다. 
		JButton btnNewButton_4 = new JButton("다른 팀과 치른 경기 요약");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//팀 간 경기 요약 화면(staff_gameWithOtherTeams(idTeam))으로 이동합니다.. 
				new staff_gameWithOtherTeams(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_4);

		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//타이틀 레벨입니다. 
		JLabel lblNewLabel = new JLabel("경기 기록 관리");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 36, 438, 29);
		contentPane.add(lblNewLabel);
	}
}
