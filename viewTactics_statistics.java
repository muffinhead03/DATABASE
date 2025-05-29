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

//전술별 경기 통계 분석 화면 클래스입니다. 전술별 경기 목록과 성과 요약 화면으로 이동할 수 있습니다.
public class viewTactics_statistics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	// 테스트용 메인함수입니다. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewTactics_statistics frame = new viewTactics_statistics(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//사용자가 현재 로그인한 팀 ID를 매개변수로 받는 생성자입니다. 
	public viewTactics_statistics(int idTeam) {
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics(idTeam).setVisible(tr다
		JButton btnNewButton_2 = new JButton("전술별 성과 요약");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics_statistics_Achieved(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);
	}

}
