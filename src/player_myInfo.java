package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.sql.*;
import java.util.*;


public class player_myInfo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private int iDplayer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player_myInfo frame = new player_myInfo(0);
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
	public player_myInfo(int iDplayer) {
		this.iDplayer = iDplayer;
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
		
		JButton btnNewButton_1 = new JButton("Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player(iDplayer).setVisible(true); dispose();
			}
		});
		btnNewButton_1.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("수정");
		btnNewButton_2.setBounds(159, 240, 117, 26);
		contentPane.add(btnNewButton_2);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*String name = textField.getText();
				String birthdate = textField_1.getText();
				String position = textField_2.getText();
				String team = textField_3.getText();
				String action = textField_4.getText();*/

				// JDBC 연결 및 업데이트
				try (Connection conn = DBUtil.getConnection()) {
				    StringBuilder sql = new StringBuilder("UPDATE DB2025_Player SET ");
				    List<Object> params = new ArrayList<>();

				    String name = textField.getText().trim();
				    String birthdateStr = textField_1.getText().trim();
				    String position = textField_2.getText().trim();
				    String team = textField_3.getText().trim();
				    String action = textField_4.getText().trim();

				    if (!name.isEmpty()) {
				        sql.append("playerName=?, ");
				        params.add(name);
				    }
				    if (!birthdateStr.isEmpty()) {
				        sql.append("birthday=?, ");
				        java.sql.Date birthday = java.sql.Date.valueOf(birthdateStr);  // "YYYY-MM-DD"
				        params.add(birthday);
				    }
				    if (!position.isEmpty()) {
				        sql.append("position=?, ");
				        params.add(position);
				    }
				    if (!team.isEmpty()) {
				        sql.append("idTeam=?, ");
				        params.add(team);
				    }
				    if (!action.isEmpty()) {
				        sql.append("playerAction=?, ");
				        params.add(action);
				    }

				    if (params.isEmpty()) {
				        System.out.println("수정할 항목이 없습니다.");
				        return;
				    }

				    // 마지막 ", " 제거
				    sql.setLength(sql.length() - 2);
				    sql.append(" WHERE idPlayer=?");
				    params.add(iDplayer);

				    try (PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
				        for (int i = 0; i < params.size(); i++) {
				            Object param = params.get(i);
				            if (param instanceof java.sql.Date) {
				                pstmt.setDate(i + 1, (java.sql.Date) param);
				            } else {
				                pstmt.setString(i + 1, param.toString());
				            }
				        }

				        int rowsAffected = pstmt.executeUpdate();
				        if (rowsAffected > 0) {
				            System.out.println("정보가 성공적으로 수정되었습니다.");
				        } else {
				            System.out.println("정보 수정 실패: 해당 ID가 존재하지 않습니다.");
				        }
				    }
				} catch (SQLException ex) {
				    ex.printStackTrace();
				} catch (IllegalArgumentException ex) {
				    System.out.println("생년월일 형식이 올바르지 않습니다. (예: YYYY-MM-DD)");
				}

			}
		});

		
		JLabel lblNewLabel_5 = new JLabel("액션:");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_5.setBounds(221, 180, 38, 16);
		contentPane.add(lblNewLabel_5);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(260, 175, 112, 26);
		contentPane.add(textField_4);
		
		JPanel panel = new JPanel();
		panel.setBounds(23, 68, 144, 160);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_7 = new JLabel("출전 가능 여부:");
		lblNewLabel_7.setBounds(179, 208, 80, 16);
		contentPane.add(lblNewLabel_7);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("가능");
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.setEnabled(false);
		rdbtnNewRadioButton.setBounds(260, 205, 63, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("불가능");
		rdbtnNewRadioButton_1.setEnabled(false);
		rdbtnNewRadioButton_1.setBounds(323, 204, 72, 23);
		contentPane.add(rdbtnNewRadioButton_1);
	}
}
