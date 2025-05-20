package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class player_setpieceTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				player_setpieceTactics frame = new player_setpieceTactics();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public player_setpieceTactics() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(e -> {
			new player_viewTactics().setVisible(true);
			dispose();
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("세트피스 전술");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 69, 438, 197);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"전술 ID", "전술 이름", "포메이션"}
		));
		scrollPane.setViewportView(table);

		loadSetpieceData(); // 🔥 여기서 데이터 불러오기 실행
	}

	private void loadSetpieceData() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		String query = "SELECT idTactic AS id, tacticName AS name, tacticFormation AS formation " +
		               "FROM db2025_tactics WHERE tacticType = 'Setpiece'";

		try (Connection conn = DBUtil.getConnection();
		     java.sql.Statement stmt = conn.createStatement();
		     ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Object[] row = {
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("formation")
				};
				model.addRow(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}