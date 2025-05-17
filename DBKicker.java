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
import javax.swing.JComboBox;
import java.net.URL;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.Image;



//로그인 선택 화면 GUI

public class DKicker extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DKicker frame = new DKicker();
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
	public DKicker() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//로그인 라벨
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 36));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(96, 50, 238, 78);
		contentPane.add(lblNewLabel);
		
		// 축구공 이미지 라벨
		ImageIcon ballIcon = null;
		try {
		    // 이미지 리소스 경로 (src/DB2025Team09/ball.png 기준)
		    URL imageUrl = getClass().getClassLoader().getResource("DB2025Team09/ball.png");

		    // 경로가 유효한지 출력
		    System.out.println("이미지 경로: " + imageUrl);

		    if (imageUrl == null) {
		        throw new Exception("⚠ 이미지 리소스를 찾을 수 없습니다.");
		    }

		    // 아이콘 생성
		    ballIcon = new ImageIcon(imageUrl);

		    // 유효한 이미지인지 확인
		    if (ballIcon.getIconWidth() == -1) {
		        throw new Exception("⚠ 이미지 로딩 실패 (잘못된 이미지 포맷이거나 깨짐)");
		    }

		} catch (Exception e) {
		    System.out.println("⚠ 이미지 로딩 중 오류: " + e.getMessage());
		}
		


		if (ballIcon != null) {
		    Image scaled = ballIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		    ImageIcon resized = new ImageIcon(scaled);

		    JLabel leftBall = new JLabel(resized);
		    leftBall.setBounds(40, 50, 64, 64);
		    contentPane.add(leftBall);

		    JLabel rightBall = new JLabel(resized);
		    rightBall.setBounds(345, 50, 64, 64);
		    contentPane.add(rightBall);
		}

		
		//"선수" 버튼
		JButton btnNewButton = new JButton("선수");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DKicker_player_choose().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(96, 207, 117, 29);
		contentPane.add(btnNewButton);
		
		//"스태프" 버튼
		JButton btnNewButton_1 = new JButton("스태프");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff().setVisible(true); dispose();
			}
		});
		btnNewButton_1.setBounds(232, 207, 117, 29);
		contentPane.add(btnNewButton_1);
		
		//팀 선택 콤보박스
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"팀1", "팀2", "팀3"}));
		comboBox.setBounds(96, 168, 253, 27);
		contentPane.add(comboBox);
		
		//"팀을 선택하세요"라벨
		JLabel lblNewLabel_1 = new JLabel("팀을 선택하세요");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(96, 140, 253, 16);
		contentPane.add(lblNewLabel_1);
	}
}
