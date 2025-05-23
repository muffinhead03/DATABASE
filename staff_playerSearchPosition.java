package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class staff_playerSearchPosition extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;
	private int idTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerSearchPosition frame = new staff_playerSearchPosition(1);
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
	public staff_playerSearchPosition(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerSearchTypes(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("포지션에 따른 선수 검색");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 39, 438, 29);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(57, 80, 305, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setBounds(362, 80, 56, 29);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String positionInput = textField.getText().trim(); // 포지션

					//int minPerformance = positionInput.isEmpty() ? 0 : Integer.parseInt(positionInput);

					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setRowCount(0); // 초기화

					// 조건 조립
					String query = "SELECT idPlayer, playerName, performance, position, birthday, ableToPlay, playerAction " +
					               "FROM DB2025_Player WHERE idTeam = ? ";
					boolean hasPosition = !positionInput.isEmpty();
					if (hasPosition) {
						query += " AND position = ?";
					}

					try (Connection conn = DBUtil.getConnection();
					     PreparedStatement pstmt = conn.prepareStatement(query)) {

						pstmt.setInt(1, idTeam);
						pstmt.setString(2, textField.getText().trim());

						if (hasPosition) {
							pstmt.setString(2, positionInput);
						}

						ResultSet rs = pstmt.executeQuery();
						while (rs.next()) {
							Object[] row = {
								rs.getInt("idPlayer"),
								rs.getString("playerName"),
								rs.getInt("performance"),
								rs.getString("position"),
								rs.getDate("birthday"),
								rs.getInt("ableToPlay") == 1 ? "가능" : "불가능",
								rs.getString("playerAction")
							};
							model.addRow(row);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 118, 438, 148);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"선수 ID", "이름", "실적", "포지션", "생년월일", "출전 가능 여부", "액션"
			}
		));
		scrollPane.setViewportView(table);
		

	}
	
}
