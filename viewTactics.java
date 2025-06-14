package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.GridLayout;

// 전술 관련 화면으로 진입하는 메인 전술 메뉴입니다. 
public class viewTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	// 테스트용 main함수로, 작동되지 않습니다. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewTactics frame = new viewTactics(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// idTEam은 현재 로그인한 팀 ID로, 전술 데이터 접근에 사용합니다. 
	public viewTactics(int idTeam) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("전술 정보");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 42, 438, 22);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 76, 438, 190);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		//필드 전술 버튼입니다.
		JButton btnNewButton_1 = new JButton("필드 전술");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewFieldTactics(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);

		//세트피스 전술 버튼입니다. 
		JButton btnNewButton_2 = new JButton("세트피스 전술");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewSetpieceTactics(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("전술별 경기 통계 분석");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics_statistics(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_3);
	}

}
