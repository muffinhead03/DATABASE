package DB2025Team09;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class staff_playerSearchPerform extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerSearchPerform frame = new staff_playerSearchPerform(1);
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
	public staff_playerSearchPerform(int idTeam) {
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
		
		JLabel lblNewLabel = new JLabel("실적에 따른 선수 검색");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 39, 438, 29);
		contentPane.add(lblNewLabel);
		
		JTextField textField = new JTextField();
		textField.setBounds(57, 80, 305, 26);
		contentPane.add(textField);
		textField.setColumns(10);
	
		
		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setBounds(362, 80, 56, 29);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String input = textField.getText().trim();
					if (input.isEmpty()) {
						return;
					}
					int minPerformance = Integer.parseInt(input);

					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setRowCount(0); // 기존 데이터 초기화

					String query = "SELECT idPlayer, playerName, performance, position, birthday, ableToPlay, playerAction " +
					               "FROM DB2025_Player WHERE performance >= ? AND idTeam = ?";

					try (Connection conn = DBUtil.getConnection();
					     PreparedStatement pstmt = conn.prepareStatement(query)) {

						pstmt.setInt(1, minPerformance);
						pstmt.setInt(2, idTeam);
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
				"\uC120\uC218 ID", "\uC774\uB984", "\uC2E4\uC801", "\uD3EC\uC9C0\uC158", "\uC0DD\uB144\uC6D4\uC77C", "\uCD9C\uC804 \uAC00\uB2A5 \uC5EC\uBD80", "\uC561\uC158"
			}
		));
		scrollPane.setViewportView(table);
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
	}

}
