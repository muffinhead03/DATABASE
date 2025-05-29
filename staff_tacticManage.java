package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

// 전술 정보를 관리하는 GUI 프레임 클래스입니다.
public class staff_tacticManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	//실제로 동작하는 부분은 아닙니다. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_tacticManage frame = new staff_tacticManage(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// idTeam 로그인한 사용자의 팀 ID입니다. 
	public staff_tacticManage(int idTeam) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		//Grid Layout을 사용한, 전술 버튼들을 담을 패널입니다. 
		JPanel panel = new JPanel();
		panel.setBounds(6, 82, 438, 184);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));

		//필드 전술 관리 버튼입니다. 
		JButton btnNewButton_1 = new JButton("필드 전술 관리");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//staff_fieldTactics화면으로 이동하는 부분입니다. (스태프 창 중 필드 전술을 관리하는 화면)
				new staff_fieldTactics(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);

		//세트피스 전술 관리 버튼입니다. 
		JButton btnNewButton_2 = new JButton("세트피스 전술 관리");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		//staff_setpieceTactics 관리하는 화면으로 가는 버튼입니다. 
				new staff_setpieceTactics(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_2);

		//상단 타이틀 라벨입니다. 
		JLabel lblNewLabel = new JLabel("전술 정보 관리");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 39, 438, 31);
		contentPane.add(lblNewLabel);

		//뒤로가기 버튼입니다.
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
	}

}
