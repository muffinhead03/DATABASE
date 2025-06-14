package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.net.URL;
import java.sql.*;


public class DKicker extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// 프로그램의 가장 처음 창 입니다.
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
		
		//창 UI 관련 설정입니다.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 36));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 50, 438, 78);
		contentPane.add(lblNewLabel);
		
		// 축구공 이미지 라벨
		ImageIcon ballIcon = null;
		try {
			// 이미지 리소스 경로 (src/DB2025Team09/ball.png 기준)
			URL imageUrl = getClass().getClassLoader().getResource("DB2025Team09/ball.png");

			

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


			// 콤보박스 생성, DB2025_Team을 쿼리하여 존재하는 팀 목록이 표시됩니다. 여기서 이용자의 팀을 선택합니다.
			JComboBox<String> comboBox = new JComboBox<>();
			DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

			try {
			    Connection conn = DBUtil.getConnection();
			    Statement stmt = conn.createStatement();
			    ResultSet rs = stmt.executeQuery("SELECT idTeam, nation FROM DB2025_Team");

			    while (rs.next()) {
			        int id = rs.getInt("idTeam");
			        String nation = rs.getString("nation");
			       
			        model.addElement("팀 "+id+" "+nation);
			    }

			    rs.close();
			    stmt.close();
			    conn.close();
			} catch (Exception e) {
			    e.printStackTrace();
			    javax.swing.JOptionPane.showMessageDialog(null, "팀 목록 로딩 중 오류가 발생했습니다.");
			}

			comboBox.setModel(model);
			comboBox.setBounds(96, 168, 253, 27);
			contentPane.add(comboBox);


		// btnNewButton 선언 및 초기화, 클릭 시 선수 고르기 창으로 이동합니다.
		JButton btnNewButton = new JButton("선수");
		btnNewButton.setBounds(96, 207, 117, 29);
		contentPane.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selectedTeam = (String) comboBox.getSelectedItem();  // 선택된 팀명 가져오기
		        int teamId = Integer.parseInt(selectedTeam.replaceAll("[^0-9]", ""));
		        new DKicker_player_choose(teamId).setVisible(true); // 선수 고르기 창으로 이동
		        
		        dispose();
		    }
		});


		// 클릭 시 스태프 메뉴로 이동합니다.
		JButton btnNewButton_1 = new JButton("스태프");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String selectedTeam = (String) comboBox.getSelectedItem();
				 int teamId = Integer.parseInt(selectedTeam.replaceAll("[^0-9]", ""));
				new staff(teamId).setVisible(true); dispose();
			}
		});
		btnNewButton_1.setBounds(232, 207, 117, 29);
		contentPane.add(btnNewButton_1);
		
		//클릭 시 팀 추가 창으로 이동합니다.
		JButton btnNewButton_2 = new JButton("팀 추가");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 
				
				new DKicker_Create_Team().setVisible(true);  dispose();
			}
		});
		btnNewButton_2.setBounds(167, 237, 117, 29);
		contentPane.add(btnNewButton_2);

		
		// 라벨 설정
		JLabel lblNewLabel_1 = new JLabel("팀을 선택하세요");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(96, 140, 253, 16);
		contentPane.add(lblNewLabel_1);
	}
}
