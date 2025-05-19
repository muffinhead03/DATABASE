package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class staff_playerCreate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_6;
	private JTextField textField_7;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerCreate frame = new staff_playerCreate();
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
	public staff_playerCreate() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerManage(DKicker.currentTeamId).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 92, 438, 134);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel = new JLabel("이름");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("번호");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("소속 팀");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("포지션");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("생년월일");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("출전 가능 여부");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"가능", "불가능"}));
		panel.add(comboBox);
		
		JLabel lblNewLabel_6 = new JLabel("실적");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_6);
		
		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("액션");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7);
		
		textField_7 = new JTextField();
		panel.add(textField_7);
		textField_7.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("생성");
		btnNewButton_1.setBounds(6, 238, 438, 29);
		contentPane.add(btnNewButton_1);

		// 🔽 이 아래가 추가되어야 하는 부분!
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String name = textField.getText();
					int idPlayer = Integer.parseInt(textField_1.getText());
					int idTeam = Integer.parseInt(textField_2.getText());
					String position = textField_3.getText();
					String birthdayStr = textField_4.getText();
					int ableToPlay = comboBox.getSelectedItem().equals("가능") ? 1 : 0;
					int performance = Integer.parseInt(textField_6.getText());
					String action = textField_7.getText();

					java.sql.Date birthday = java.sql.Date.valueOf(birthdayStr);

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
							new staff_playerManage(DKicker.currentTeamId).setVisible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, "등록 실패");
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
				}
			}
		});
		
		JLabel lblNewLabel_8 = new JLabel("선수 생성");
		lblNewLabel_8.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_8.setBounds(6, 47, 438, 32);
		contentPane.add(lblNewLabel_8);
	}
}
