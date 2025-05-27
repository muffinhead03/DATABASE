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

//선수 관리 메인 화면입니다.
public class staff_playerManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	//테스트용 메인 메서드입니다. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerManage frame = new staff_playerManage(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	//팀 ID를 받아 화면을 구성하는 생성자입니다.
	public staff_playerManage(int idTeam) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		//메인 컨텐츠 패널 설정합니다.
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//버튼들을 담을 패널입니다.
		JPanel panel = new JPanel();
		panel.setBounds(6, 67, 438, 199);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		//선수 생성 화면으로 이동합니다.
		JButton btnNewButton_1 = new JButton("선수 생성");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			new staff_playerCreate(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);

		//조건별 선수 검색 화면으로 이동합니다. 
		JButton btnNewButton_2 = new JButton("조건별 선수 검색");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerSearchTypes(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);

		//스쿼드 조회 및 관리 화면으로 이동합니다. 
		JButton btnNewButton_3 = new JButton("선수 스쿼드 조회 및 관리");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerManage_squad(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_3);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//타이틀 라벨입니다.
		JLabel lblNewLabel = new JLabel("선수 관리");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 35, 438, 29);
		contentPane.add(lblNewLabel);
	}
}
