package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.Font;
import java.sql.*;
import java.sql.Date;
import java.util.*;

public class player_myInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private int idTeam, idPlayer;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				player_myInfo frame = new player_myInfo(1,1);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public player_myInfo(int idTeam, int idPlayer) {
		this.idTeam=idTeam;
		this.idPlayer = idPlayer;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("이름:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(211, 68, 48, 16);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("생년월일:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(211, 96, 48, 16);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("포지션:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(211, 124, 48, 16);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("소속 팀:");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(211, 152, 48, 16);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_5 = new JLabel("액션:");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(221, 180, 38, 16);
		contentPane.add(lblNewLabel_5);

		JLabel lblNewLabel_7 = new JLabel("출전 가능 여부:");
		lblNewLabel_7.setBounds(179, 208, 80, 16);
		contentPane.add(lblNewLabel_7);

		textField = new JTextField();
		textField.setBounds(260, 63, 112, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(260, 91, 112, 26);
		contentPane.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(260, 119, 112, 26);
		contentPane.add(textField_2);
		textField_2.setColumns(10);

		textField_3 = new JTextField();
		textField_3.setBounds(260, 147, 112, 26);
		contentPane.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(260, 175, 112, 26);
		contentPane.add(textField_4);
		textField_4.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBounds(23, 68, 144, 160);
		contentPane.add(panel);
		panel.setLayout(null);

		ButtonGroup buttonGroup = new ButtonGroup();

		rdbtnNewRadioButton = new JRadioButton("가능");
		rdbtnNewRadioButton.setBounds(260, 205, 63, 23);
		buttonGroup.add(rdbtnNewRadioButton);
		contentPane.add(rdbtnNewRadioButton);

		rdbtnNewRadioButton_1 = new JRadioButton("불가능");
		rdbtnNewRadioButton_1.setBounds(323, 204, 72, 23);
		buttonGroup.add(rdbtnNewRadioButton_1);
		contentPane.add(rdbtnNewRadioButton_1);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(e -> {
			new player(idTeam, idPlayer).setVisible(true);
			dispose();
		});
		btnBack.setBounds(6, 6, 117, 29);
		contentPane.add(btnBack);

		addUpdateButton();     // 수정 버튼 추가
		loadPlayerInfo();      // 플레이어 정보 로드
	}

	

	private void addUpdateButton() {
	    JButton btnUpdate = new JButton("수정");
	    btnUpdate.setBounds(159, 240, 117, 26);
	    contentPane.add(btnUpdate);

	    btnUpdate.addActionListener(e -> {
	        try (Connection conn = DBUtil.getConnection()) {
	            StringBuilder sql = new StringBuilder("UPDATE DB2025_Player SET ");
	            List<Object> params = new ArrayList<>();

	            String name = textField.getText().trim();
	            String birthdateStr = textField_1.getText().trim();
	            String position = textField_2.getText().trim();
	            String team = textField_3.getText().trim();
	            String playerAction = textField_4.getText().trim();
	            Integer ableToPlay = null;

	            // 라디오 버튼 처리 (출전 가능 여부)
	            if (rdbtnNewRadioButton.isSelected()) {
	                ableToPlay = 1;  // 가능
	            } else if (rdbtnNewRadioButton_1.isSelected()) {
	                ableToPlay = 0;  // 불가능
	            }

	            // 각 필드 체크 후 SQL 구성
	            if (!name.isEmpty()) {
	                sql.append("playerName=?, ");
	                params.add(name);
	            }
	            if (!birthdateStr.isEmpty()) {
	                sql.append("birthday=?, ");
	                params.add(Date.valueOf(birthdateStr));
	            }
	            if (!position.isEmpty()) {
	                sql.append("position=?, ");
	                params.add(position);
	            }
	            if (!team.isEmpty()) {
	                sql.append("idTeam=?, ");
	                params.add(team);
	            }
	            if (!playerAction.isEmpty()) {
	                sql.append("playerAction=?, ");
	                params.add(playerAction);
	            }
	            if (ableToPlay != null) {
	                sql.append("ableToPlay=?, ");
	                params.add(ableToPlay);
	            }

	            if (params.isEmpty()) {
	                System.out.println("수정할 항목이 없습니다.");
	                return;
	            }

	            // 마지막 쉼표 제거
	            sql.setLength(sql.length() - 2);
	            sql.append(" WHERE idPlayer=?");
	            params.add(idPlayer);

	            try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
	                for (int i = 0; i < params.size(); i++) {
	                    Object param = params.get(i);
	                    if (param instanceof Date) {
	                        pstmt.setDate(i + 1, (Date) param);
	                    } else if (param instanceof Integer) {
	                        pstmt.setInt(i + 1, (Integer) param);
	                    } else {
	                        pstmt.setString(i + 1, param.toString());
	                    }
	                }

	                int rows = pstmt.executeUpdate();
	                if (rows > 0) {
	                    System.out.println("정보가 성공적으로 수정되었습니다.");
	                } else {
	                    System.out.println("정보 수정 실패");
	                }
	            }
	        } catch (SQLException | IllegalArgumentException ex) {
	            ex.printStackTrace();
	        }
	    });
	}

	
	
	private void loadPlayerInfo() {
	    try (Connection conn = DBUtil.getConnection()) {
	        String sql = "SELECT playerName, birthday, position, idTeam, playerAction , ableToPlay FROM DB2025_Player WHERE idPlayer = ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, idPlayer);

	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    String name = rs.getString("playerName");
	                    Date birthday = rs.getDate("birthday");
	                    String position = rs.getString("position");
	                    String team = rs.getString("idTeam");
	                    String action = rs.getString("playerAction");
	                    int able = rs.getInt("ableToPlay");

	                    textField.setText(name);
	                    textField_1.setText(birthday != null ? birthday.toString() : "");
	                    textField_2.setText(position);
	                    textField_3.setText(team);
	                    textField_4.setText(action);  // Optional: Keep this if needed

	                    // 출전 가능 여부 라디오 버튼 설정
	                    if (able == 1) {
	                        rdbtnNewRadioButton.setSelected(true);  // 가능
	                    } else {
	                        rdbtnNewRadioButton_1.setSelected(true);  // 불가능
	                    }
	                }
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

}
