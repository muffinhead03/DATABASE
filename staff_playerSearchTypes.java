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

//staff_playerSearchTypes 클래스는 코치가 선수 검색을 다양한 조건(포지션, 출전 가능 여부, 실적)으로 수행할 수 있도록 검색 유형을 선택하는 화면을 구성한 클래스입니다.
public class staff_playerSearchTypes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	//프로그램 단독 실행 시 진입점입니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerSearchTypes frame = new staff_playerSearchTypes(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//생성자에서는 팀 ID를 인자로 받아 해당 팀의 코치가 사용할 선수 검색 유형 선택 화면을 구성합니다.
	public staff_playerSearchTypes(int idTeam) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		//메인 패널을 설정합니다. 
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//뒤로 가기 버튼입니다. 
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerManage(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//타이틀 라벨입니다. 
		JLabel lblNewLabel = new JLabel("조건별 선수 검색");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 38, 438, 22);
		contentPane.add(lblNewLabel);

		//검색 조건 버튼 영역입니다. 
		JPanel panel = new JPanel();
		panel.setBounds(6, 72, 438, 194);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		//1. 포지션 기준 검색합니다.
		JButton btnNewButton_1 = new JButton("포지션에 따른 선수 검색");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerSearchPosition(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);

		//2. 출전 가능 여부 기준 검색합니다. 
		JButton btnNewButton_2 = new JButton("출전 가능 여부에 따른 선수 검색");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerSearchAvailable(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);

		//3. 실적 기준 검색합니다. 
		JButton btnNewButton_3 = new JButton("실적에 따른 선수 검색");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerSearchPerform(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_3);
	}

}
