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

//팀 관련 기능을 관리하는 메인 메뉴 프레임입니다.
public class staff_teamManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int idTeam;

	// 테스트 실행용 메인 메서드입니다. (idTeam =1 이며, 실제로 실행되진 않습니다. )
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_teamManage frame = new staff_teamManage(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public staff_teamManage(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		//메뉴 버튼들이 들어갈 패널입니다. 
		JPanel panel = new JPanel();
		panel.setBounds(6, 79, 438, 187);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		//우리 팀의 대외 정보 관리창입니다. DB를 수정하는 기능을 가집니다. 
		JButton btnNewButton_1 = new JButton("우리 팀의 대외 정보 관리");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_teamInfoManage(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);

		//우리 팀이 최다 득점한 상대팀을 조회할 수 있으며, SELECT 쿼리를 사용하여 DB를 조회하는 기능이 있습니다.
		JButton btnNewButton_2 = new JButton("우리 팀이 최다 득점한 상대팀 조회");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_teamManage_scoredMost(idTeam).setVisible(true); dispose();			}
		});
		panel.add(btnNewButton_2);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//메뉴 타이틀 라벨입니다. 
		JLabel lblNewLabel = new JLabel("팀 관리");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 40, 438, 27);
		contentPane.add(lblNewLabel);
	}
}
