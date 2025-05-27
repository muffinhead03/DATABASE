package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.sql.*;
import java.awt.*;

//선수 등록 화면 클래스입니다.
public class staff_playerCreate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	//입력 필드 선언합니다.
	private JTextField textField; // 이름
	private JTextField textField_1; // 선수 ID(자동 생성)
	private JTextField textField_2; // 팀 ID (읽기 전용)
	private JTextField textField_3; // 포지션
	private JTextField textField_4; // 생년월일
	private JTextField textField_6; // 실적
	private JTextField textField_7; // 액션
	private int idTeam; // 현재 로그인한 팀 ID

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				staff_playerCreate frame = new staff_playerCreate(1);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public staff_playerCreate(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(e -> {
			new staff_playerManage(idTeam).setVisible(true);
			dispose();
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//입력 필드들이 포함된 패널입니다. 
		JPanel panel = new JPanel();
		panel.setBounds(6, 92, 438, 134);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		//이름 입력하는 곳입니다.
		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		//선수 ID(자동 부여, 편집 불가)입니다.
		JLabel lblNewLabel_1 = new JLabel("선수 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		textField_1 = new JTextField(String.valueOf(getNextPlayerId()));
		textField_1.setEditable(false);
		panel.add(textField_1);
		textField_1.setColumns(10);

		//팀 ID(편집 불가)입니다. 
		JLabel lblNewLabel_2 = new JLabel("소속 팀");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		textField_2 = new JTextField(String.valueOf(idTeam));
		textField_2.setEditable(false);
		panel.add(textField_2);
		textField_2.setColumns(10);

		//포지션 입력하는 곳입니다.
		JLabel lblNewLabel_3 = new JLabel("포지션");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);

		//생년월일 입력하는 곳입니다. 
		JLabel lblNewLabel_4 = new JLabel("생년월일");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);

		//출전 가능 여부입니다. 
		JLabel lblNewLabel_5 = new JLabel("출전 가능 여부");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setModel(new DefaultComboBoxModel<>(new String[] { "가능", "불가능" }));
		panel.add(comboBox);

		//실적 입력하는 곳입니다.
		JLabel lblNewLabel_6 = new JLabel("실적");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_6);
		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(10);

		//액션 설명하는 곳입니다.
		JLabel lblNewLabel_7 = new JLabel("액션");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7);
		textField_7 = new JTextField();
		panel.add(textField_7);
		textField_7.setColumns(10);

		//생성 버튼입니다.
		JButton btnNewButton_1 = new JButton("생성");
		btnNewButton_1.setBounds(6, 238, 438, 29);
		contentPane.add(btnNewButton_1);

		//선수 생성 버튼 클릭 시 DB등록을 시도합니다. 
		btnNewButton_1.addActionListener(e -> {
			try {
				String name = textField.getText().trim();
				int idPlayer = Integer.parseInt(textField_1.getText());
				
				String position = textField_3.getText().trim();
				String birthdayStr = textField_4.getText().trim();
				int ableToPlay = comboBox.getSelectedItem().equals("가능") ? 1 : 0;
				int performance = Integer.parseInt(textField_6.getText().trim());
				String action = textField_7.getText().trim();

				//생년월일 형식 확인하는 곳입니다. 
				java.sql.Date birthday;
				try {
					birthday = java.sql.Date.valueOf(birthdayStr);
				} catch (IllegalArgumentException ex) {
					JOptionPane.showMessageDialog(null, "생년월일 형식은 yyyy-mm-dd 이어야 합니다.");
					return;
				}

				//DB INSERT 쿼리 준비하는 곳입니다.
				String query = "INSERT INTO DB2025_Player " +
						"(idPlayer, playerName, position, birthday, idTeam, ableToPlay, performance, playerAction) " +
						"VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

				try (Connection conn = DBUtil.getConnection();
					 PreparedStatement pstmt = conn.prepareStatement(query)) {

					pstmt.setInt(1, idPlayer);
					pstmt.setString(2, name);
					pstmt.setString(3, position);
					pstmt.setDate(4, birthday);
					pstmt.setInt(5, idTeam);
					pstmt.setInt(6, ableToPlay);
					pstmt.setInt(7, performance);
					pstmt.setString(8, action);

					int result = pstmt.executeUpdate();

					
					if (result > 0) {
						JOptionPane.showMessageDialog(null, "선수 등록 완료!");
						new staff_playerManage(idTeam).setVisible(true);
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "등록 실패");
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
			}
		});

		//화면 상단 라벨입니다.
		JLabel lblNewLabel_8 = new JLabel("선수 생성");
		lblNewLabel_8.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setBounds(6, 47, 438, 32);
		contentPane.add(lblNewLabel_8);
	}

	//다음으로 사용할 선수 ID입니다. DB에서 가장 큰 선수 ID를 조회하여, 다음 ID를 생성합니다.
	public int getNextPlayerId() {
		int nextId = 1;
		try (Connection conn = DBUtil.getConnection();
			 Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery("SELECT MAX(idPlayer) FROM DB2025_Player")) {

			if (rs.next()) {
				nextId = rs.getInt(1) + 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextId;
	}
}
