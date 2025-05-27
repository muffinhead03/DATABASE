package DB2025Team09;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

//코치가 특정 경기에 대한 출전 선수 명단(Squad)을 생성 및 관리하는 UI
public class staff_gamePlayerListCreate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JTextField textField_2;     // 출전 시간 입력 필드
	private JComboBox comboBox;         // 경기 ID 선택 콤보박스
	private JComboBox comboBox_1;       // 선수 이름 선택 콤보박스

	private int idTeam;                 // 현재 팀 ID

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				staff_gamePlayerListCreate frame = new staff_gamePlayerListCreate(1); // 테스트용 idTeam = 1
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public staff_gamePlayerListCreate(int idTeam) {
		this.idTeam = idTeam;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		// 전체 패널
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBounds(6, 6, 117, 29);
		btnNewButton.addActionListener(e -> {
			new staff_gameManage(idTeam).setVisible(true);
			dispose();
		});
		contentPane.add(btnNewButton);

		// 제목라벨입니다. 
		JLabel lblNewLabel = new JLabel("경기 출전 선수 명단 생성");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 29);
		contentPane.add(lblNewLabel);

		// 경기 ID 선택 패널입니다. 
		JPanel panel = new JPanel();
		panel.setBounds(6, 75, 438, 29);
		panel.setLayout(new GridLayout(1, 2));
		contentPane.add(panel);

		panel.add(new JLabel("경기 ID", SwingConstants.CENTER));
		comboBox = new JComboBox();
		panel.add(comboBox);

		// comboBox에 경기 ID 목록 로딩할 수 있도록 합니다.(DB에서 데려옵니다)
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT idGame FROM DB2025_view_GameSummary WHERE idOurTeam = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idTeam);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				comboBox.addItem(rs.getInt("idGame"));
			}
			rs.close(); pstmt.close(); conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 선수 선택 및 출전 시간 입력하는는 영역입니다. 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 116, 438, 40);
		contentPane.add(scrollPane);

		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(1, 5));

		panel_1.add(new JLabel("선수 이름", SwingConstants.CENTER));
		comboBox_1 = new JComboBox();
		panel_1.add(comboBox_1);

		panel_1.add(new JLabel("출전 시간", SwingConstants.CENTER));
		textField_2 = new JTextField(); // 출전 시간 입력
		panel_1.add(textField_2);
		textField_2.setColumns(10);

		//  comboBox_1에 선수 이름 목록 로딩합니다.
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT playerName FROM DB2025_Player WHERE idTeam = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idTeam);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				comboBox_1.addItem(rs.getString("playerName"));
			}
			rs.close(); pstmt.close(); conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		//  스쿼드 목록 보기 버튼입니다. 
		JButton btnNewButton_2 = new JButton("선수 명단");
		btnNewButton_2.setBounds(327, 168, 117, 29);
		btnNewButton_2.addActionListener(e -> {
			Object selectedGameObj = comboBox.getSelectedItem();
			if (selectedGameObj == null) {
				JOptionPane.showMessageDialog(null, "경기를 먼저 선택하세요.");
				return;
			}
			int selectedGameId = (int) selectedGameObj;

			JFrame squadFrame = new JFrame("스쿼드 목록");
			squadFrame.setSize(600, 400);
			squadFrame.setLocationRelativeTo(null);

			try {
				Connection conn = DBUtil.getConnection();
				String sql = "SELECT idPlayer AS '선수 id', playerName AS '선수 이름', position AS '포지션', playTime AS '출전 시간' " +
							 "FROM DB2025_Game_Players_List WHERE idGame = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, selectedGameId);
				ResultSet rs = pstmt.executeQuery();

				DefaultTableModel model = new DefaultTableModel();
				ResultSetMetaData meta = rs.getMetaData();
				int colCount = meta.getColumnCount();

				for (int i = 1; i <= colCount; i++) {
					model.addColumn(meta.getColumnLabel(i));
				}
				while (rs.next()) {
					Object[] row = new Object[colCount];
					for (int i = 0; i < colCount; i++) {
						row[i] = rs.getObject(i + 1);
					}
					model.addRow(row);
				}

				JTable table = new JTable(model);
				squadFrame.add(new JScrollPane(table));
				squadFrame.setVisible(true);

				rs.close(); pstmt.close(); conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "스쿼드 정보를 불러오는데 실패했습니다.");
			}
		});
		contentPane.add(btnNewButton_2);

		// 선수 추가 버튼입니다. 
		JButton btnNewButton_1 = new JButton("생성");
		btnNewButton_1.setBounds(6, 237, 438, 29);
		btnNewButton_1.addActionListener(e -> {
			String playerName = (String) comboBox_1.getSelectedItem();
			Object gameObj = comboBox.getSelectedItem();

			if (gameObj == null || playerName == null) {
				JOptionPane.showMessageDialog(null, "경기와 선수를 모두 선택하세요.");
				return;
			}

			try {
				Integer.parseInt(textField_2.getText().trim()); // 출전 시간 숫자 체크
				boolean success = enterSquad();
				JOptionPane.showMessageDialog(null, success ? "선수 추가 성공!" : "선수 추가 실패");
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "출전 시간을 숫자로 입력하세요.");
			}
		});
		contentPane.add(btnNewButton_1);

		// 선수 삭제 버튼입니다. 
		JButton btnDelete = new JButton("삭제");
		btnDelete.setBounds(6, 200, 438, 29);
		btnDelete.addActionListener(e -> {
			boolean success = deleteSquadEntry();
			if (success) {
				JOptionPane.showMessageDialog(null, "스쿼드에서 삭제되었습니다.");
			}
		});
		contentPane.add(btnDelete);
	}

	//스쿼드에 선수 추가 (DB2025_Squad INSERT)합니다. 
	private boolean enterSquad() {
		String playerName = (String) comboBox_1.getSelectedItem();
		int idGame = (int) comboBox.getSelectedItem();
		int playTime = Integer.parseInt(textField_2.getText());

		try {
			Connection conn = DBUtil.getConnection();

			// 1. 선수 이름으로 idPlayer 조회
			String sql = "SELECT idPlayer FROM DB2025_Player WHERE playerName = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, playerName);
			ResultSet rs = pstmt.executeQuery();

			int idPlayer = -1;
			if (rs.next()) {
				idPlayer = rs.getInt("idPlayer");
			}
			rs.close(); pstmt.close();

			// 2. Squad 테이블에 삽입
			String sql2 = "INSERT INTO DB2025_Squad (idGame, idPlayer, playTime) VALUES (?, ?, ?)";
			PreparedStatement pstmt2 = conn.prepareStatement(sql2);
			pstmt2.setInt(1, idGame);
			pstmt2.setInt(2, idPlayer);
			pstmt2.setInt(3, playTime);
			pstmt2.executeUpdate();

			pstmt2.close(); conn.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//스쿼드에서 특정 선수 제거
	private boolean deleteSquadEntry() {
		String playerName = (String) comboBox_1.getSelectedItem();
		Object gameObj = comboBox.getSelectedItem();

		if (playerName == null || gameObj == null) {
			JOptionPane.showMessageDialog(null, "경기와 선수를 모두 선택하세요.");
			return false;
		}

		int idGame = (int) gameObj;

		try {
			Connection conn = DBUtil.getConnection();

			// 1. 선수 이름으로 idPlayer 조회
			String findSql = "SELECT idPlayer FROM DB2025_Player WHERE playerName = ?";
			PreparedStatement findStmt = conn.prepareStatement(findSql);
			findStmt.setString(1, playerName);
			ResultSet rs = findStmt.executeQuery();

			int idPlayer = -1;
			if (rs.next()) {
				idPlayer = rs.getInt("idPlayer");
			} else {
				JOptionPane.showMessageDialog(null, "선수 정보를 찾을 수 없습니다.");
				rs.close(); findStmt.close(); conn.close();
				return false;
			}
			rs.close(); findStmt.close();

			// 2. Squad 테이블에서 삭제
			String deleteSql = "DELETE FROM DB2025_Squad WHERE idGame = ? AND idPlayer = ?";
			PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
			deleteStmt.setInt(1, idGame);
			deleteStmt.setInt(2, idPlayer);

			int affected = deleteStmt.executeUpdate();
			deleteStmt.close(); conn.close();

			if (affected > 0) {
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "삭제할 스쿼드 항목이 없습니다.");
				return false;
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "삭제 중 오류 발생.");
			return false;
		}
	}
}
