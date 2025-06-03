package DB2025Team09;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;

public class player_setpieceTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam, idPlayer;
//테스트용 메인 함수 입니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				player_setpieceTactics frame = new player_setpieceTactics(1, 1);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	private void loadTacticsData() {
		
		// 공통메뉴
		//4-2 세트피스 전술 목록
		// 데이터 쿼리와 로드를 위한 메서드 입니다.
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		String query = "SELECT idTactic, tacticName, tacticFormation, explainTactics, ableToTactic "
				+ "FROM DB2025_Tactics WHERE tacticType = 'Setpiece' AND idTeam = ?";
		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, idTeam);

			try (ResultSet rs = pstmt.executeQuery()) {
				while (rs.next()) {
					int idTactic = rs.getInt("idTactic");
					String tacticName = rs.getString("tacticName");
					String tacticFormation = rs.getString("tacticFormation");
					String explainTactics = rs.getString("explainTactics");
					String able = (rs.getInt("ableToTactic") == 1) ? "가능" : "불가능";

					Object[] row = {idTactic, tacticName, tacticFormation, explainTactics, able};
					model.addRow(row);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public player_setpieceTactics(int idTeam, int idPlayer) {
		
		//공통 메뉴
		//4. 전술 정보 조회
		//4-2. 세트 피스 전술 목록
		//우리팀의 전체 세트피스 전술 목록, 전술의 상세 정보를 조회한다.
		
		this.idTeam = idTeam;
		this.idPlayer = idPlayer;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 350); // 창 크기 줄임
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(e -> {
			new player_viewTactics(idTeam, idPlayer).setVisible(true);
			dispose();
		});
		btnBack.setBounds(6, 6, 117, 29);
		contentPane.add(btnBack);

		JLabel lblTitle = new JLabel("세트피스 전술");
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(6, 32, 620, 29); // 라벨 너비 줄임
		contentPane.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 66, 620, 230); // 스크롤 영역 크기 줄임
		contentPane.add(scrollPane);

		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {
				"전술 ID", "필드 전술 이름", "포메이션", "전술 설명", "사용 가능 여부"
			}
		));

		table.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());
		table.setRowHeight(60);
		table.setRowMargin(5);
		scrollPane.setViewportView(table);

		loadTacticsData();
	}

	// 줄바꿈용 셀 렌더러
	class TextAreaRenderer extends JTextArea implements TableCellRenderer {
		public TextAreaRenderer() {
			setLineWrap(true);
			setWrapStyleWord(true);
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table, Object value,
													   boolean isSelected, boolean hasFocus,
													   int row, int column) {
			setText(value == null ? "" : value.toString());
			if (isSelected) {
				setBackground(table.getSelectionBackground());
				setForeground(table.getSelectionForeground());
			} else {
				setBackground(table.getBackground());
				setForeground(table.getForeground());
			}
			setFont(table.getFont());
			return this;
		}
	}
}
