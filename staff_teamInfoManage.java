package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class staff_teamInfoManage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int teamId;

	// 🔧 텍스트필드를 인스턴스 변수로 선언
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					// 테스트용 teamId = 1 (실제 환경에서는 로그인 정보에서 받아야 함)
					staff_teamInfoManage frame = new staff_teamInfoManage(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public staff_teamInfoManage(int teamId) {
		this.teamId = teamId;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_teamManage().setVisible(true);
				dispose();
			}
		});

		JLabel lblNewLabel = new JLabel("우리 팀의 대외 정보 관리");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 39, 438, 22);
		contentPane.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 100, 438, 123);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		panel_1.add(new JLabel("FIFA 랭킹", SwingConstants.CENTER));
		textField = new JTextField();
		panel_1.add(textField);

		panel_1.add(new JLabel("현재 진행 중인 대회", SwingConstants.CENTER));
		textField_1 = new JTextField();
		panel_1.add(textField_1);

		panel_1.add(new JLabel("현재 진행 중인 대회 순위", SwingConstants.CENTER));
		textField_2 = new JTextField();
		panel_1.add(textField_2);

		panel_1.add(new JLabel("현재 진행 중인 대회 승점", SwingConstants.CENTER));
		textField_3 = new JTextField();
		panel_1.add(textField_3);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 227, 438, 39);
		contentPane.add(panel_2);

		JButton btnNewButton_1 = new JButton("생성");
		panel_2.add(btnNewButton_1);

		// 🔻 DB에서 팀 정보 불러오기
		try {
			Connection conn = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/db2025team09", "root", "asdf1234!");

			String sql = "SELECT FIFArank, currName, currRank, currPoints FROM db2025_team WHERE idTeam = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, teamId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				textField.setText(rs.getString("FIFArank"));
				textField_1.setText(rs.getString("currName"));
				textField_2.setText(rs.getString("currRank"));
				textField_3.setText(rs.getString("currPoints"));
			}

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 🔻 저장 버튼 이벤트: DB 업데이트
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connection conn = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/db2025team09", "root", "asdf1234!");

					String sql = "UPDATE db2025_team SET FIFArank = ?, currName = ?, currRank = ?, currPoints = ? WHERE idTeam = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, textField.getText());
					pstmt.setString(2, textField_1.getText());
					pstmt.setString(3, textField_2.getText());
					pstmt.setString(4, textField_3.getText());
					pstmt.setInt(5, teamId);
					


					pstmt.executeUpdate();
					conn.close();
					
					JOptionPane.showMessageDialog(null, "생성 완료!");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "오류발생!");
					
				}
			}
		});
	}
}
