package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

public class player_myTacticsTeam_field extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				player_myTacticsTeam_field frame = new player_myTacticsTeam_field();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	// JTextArea 기반 셀 렌더러 (행 높이만 늘리고 열 너비 고정)
	static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
		public TextAreaRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			setText(value != null ? value.toString() : "");

			// 현재 열 너비 기준으로 preferredSize를 설정 (열 너비는 고정)
			setSize(table.getColumnModel().getColumn(column).getWidth(), Short.MAX_VALUE);

			int preferredHeight = getPreferredSize().height;
			if (table.getRowHeight(row) < preferredHeight) {
				table.setRowHeight(row, preferredHeight);
			}

			return this;
		}
	}

	public player_myTacticsTeam_field() {
		

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(e -> {
			new player_myTacticsTeam().setVisible(true);
			dispose();
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		JLabel lblNewLabel = new JLabel("필드 전술");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 38, 438, 29);
		contentPane.add(lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 79, 438, 187);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"전술 ID", "전술 이름", "포메이션", "설명", "사용 횟수"}
		));
		scrollPane.setViewportView(table);

		// ✅ 설명 열에만 줄바꿈 렌더러 적용 (열 너비는 그대로 유지)
		table.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());

		loadSetpieceTactics(DKicker_player_choose.playerid, table);
	}

	public void loadSetpieceTactics(int idPlayer, JTable table) {
		String query = "SELECT F.idTactic AS fieldTacticId, F.tacticName AS fieldTacticName, F.tacticFormation AS fieldFormation, F.explainTactics AS fieldDescription,\r\n COUNT(*) AS useCount\r\n"
				+ "FROM view_GameSummary G\r\n"
				+ "LEFT JOIN DB2025_Tactics F ON G.idField = F.idTactic AND F.tacticType = 'Field' AND F.idTeam = ?\r\n"
				
				+ "WHERE G.idOurTeam = ?\r\n"
				+ "GROUP BY F.idTactic, F.tacticName, F.tacticFormation, F.explainTactics\r\n"
								+ "ORDER BY useCount DESC\r\n"
				+ "LIMIT 3;";

		try (Connection conn = DBUtil.getConnection();
		     PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setInt(1, DKicker.currentTeamId);
			stmt.setInt(2, DKicker.currentTeamId);

			try (ResultSet rs = stmt.executeQuery()) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);

				while (rs.next()) {
					int idTactic = rs.getInt("fieldTacticId");
					String name = rs.getString("fieldTacticName");
					String formation = rs.getString("fieldFormation");
					String description = rs.getString("fieldDescription");
					int count = rs.getInt("useCount");

					model.addRow(new Object[]{idTactic, name, formation, description, count});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "데이터 로딩 실패: " + e.getMessage());
		}
	}
}
