package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.Font;
import java.awt.GridLayout;

public class staff_fieldTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_fieldTactics frame = new staff_fieldTactics();
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
	public staff_fieldTactics() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 300); // 창 너비 확장
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(e -> {
			new staff_tacticManage().setVisible(true);
			dispose();
		});
		btnBack.setBounds(6, 6, 117, 29);
		contentPane.add(btnBack);

		JLabel lblTitle = new JLabel("필드 전술 관리");
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(6, 24, 638, 29);
		contentPane.add(lblTitle);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 55, 638, 124);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"필드전술 ID", "전술 이름", "포메이션", "설명","사용 가능 여부"}
		));
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 185, 620, 60);  // 적당한 위치로 조정 (필요에 따라 위치 조절)
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 5, 10, 5));

		// 라벨 (1행)
		JLabel lblNewLabel_4 = new JLabel("필드전술 ID", SwingConstants.CENTER);
		JLabel lblNewLabel_5 = new JLabel("전술 이름", SwingConstants.CENTER);
		JLabel lblNewLabel_6 = new JLabel("포메이션", SwingConstants.CENTER);
		JLabel lblNewLabel_7 = new JLabel("설명", SwingConstants.CENTER);
		JLabel lblNewLabel_8 = new JLabel("사용 가능 여부", SwingConstants.CENTER);

		panel_1.add(lblNewLabel_4);
		panel_1.add(lblNewLabel_5);
		panel_1.add(lblNewLabel_6);
		panel_1.add(lblNewLabel_7);
		panel_1.add(lblNewLabel_8);

		// 텍스트 필드 (2행)
		JTextField txtId = new JTextField();
		JTextField textField_5 = new JTextField();
		JTextField textField_6 = new JTextField();
		JTextField textField_7 = new JTextField();
		JTextField textField_8 = new JTextField(); // 사용 가능 여부

		panel_1.add(txtId);
		panel_1.add(textField_5);
		panel_1.add(textField_6);
		panel_1.add(textField_7);
		panel_1.add(textField_8);


		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 245, 626, 16);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnEdit = new JButton("수정");
		btnEdit.addActionListener(e -> {
		    updateTactic(txtId, textField_5, textField_6, textField_7, textField_8);

			
		});
		panel_2.add(btnEdit);

		JButton btnDelete = new JButton("삭제");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idText = txtId.getText().trim();
				if (idText.isEmpty()) {
					JOptionPane.showMessageDialog(null, "삭제할 필드 전술 ID를 입력해주세요");
					return;
				}

				int inputId;
				try {
					inputId = Integer.parseInt(idText);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "유효한 숫자 ID를 입력해주세요");
					return;
				}

				boolean found = false;
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				for (int i = 0; i < model.getRowCount(); i++) {
					if ((int) model.getValueAt(i, 0) == inputId) {
						found = true;
						break;
					}
				}

				if (!found) {
					JOptionPane.showMessageDialog(null, "해당 ID의 필드 전술이 존재하지 않습니다.");
					return;
				}

				int result = JOptionPane.showConfirmDialog(
					null, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION
				);

				if (result == JOptionPane.YES_OPTION) {
					deleteTactic(inputId);
					JOptionPane.showMessageDialog(null, "삭제되었습니다.");
					loadTacticsData();
					txtId.setText("");
				}
			}
		});
		panel_2.add(btnDelete);

		JButton btnAdd = new JButton("신규 추가");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField_5.getText().trim();
				String formation = textField_6.getText().trim();
				String explain = textField_7.getText().trim();
				String ableTo = textField_8.getText().trim();
				if (ableTo.isEmpty()) {
				    JOptionPane.showMessageDialog(null, "사용 가능 시 1, 불가능 시 0을 입력해주세요.");
				    return;
				}

				if (name.isEmpty() || formation.isEmpty() || explain.isEmpty()) {
					JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.");
					return;
				}

				int newId = getNextTacticId();

				String insertQuery = "INSERT INTO db2025_tactics (idTactic, tacticName, tacticFormation, explainTactics, tacticType, idTeam,ableToTactic) " +
								"VALUES (?, ?, ?, ?, 'Field', ?,?)";

				try (Connection conn = DBUtil.getConnection();
					 java.sql.PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {

					pstmt.setInt(1, newId);
					pstmt.setString(2, name);
					pstmt.setString(3, formation);
					pstmt.setString(4, explain);
					pstmt.setInt(5, DKicker.currentTeamId);
					pstmt.setString(6, ableTo);

					pstmt.executeUpdate();
					JOptionPane.showMessageDialog(null, "신규 전술이 추가되었습니다.");

					loadTacticsData();

					txtId.setText("");
					textField_5.setText("");
					textField_6.setText("");
					textField_7.setText("");
					textField_8.setText("");

				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "추가 중 오류가 발생했습니다.");
				}
			}
		});
		panel_2.add(btnAdd);

		loadTacticsData();
	}

	private void loadTacticsData() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);

		String query = "SELECT idTactic AS id, tacticName AS name, tacticFormation AS formation, explainTactics AS explainTactics, ableToTactic AS ableToTactic " +
					 "FROM db2025_tactics WHERE tacticType = 'Field'";

		try (Connection conn = DBUtil.getConnection();
			 java.sql.Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				String able;
				if(rs.getInt("ableToTactic")==1) {
					able = "가능";
				}else {
					able = "불가능";
				}
				Object[] row = {
					rs.getInt("id"),
					rs.getString("name"),
					rs.getString("formation"),
					rs.getString("explainTactics"),
					able
					
				};
				model.addRow(row);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteTactic(int id) {
		String query = "DELETE FROM db2025_tactics WHERE idTactic = ?";

		try (Connection conn = DBUtil.getConnection();
			 java.sql.PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다.");
		}
	}

	private int getNextTacticId() {
		String query = "SELECT MAX(idTactic) AS maxId FROM db2025_tactics";
		int maxId = 0;

		try (Connection conn = DBUtil.getConnection();
			 java.sql.Statement stmt = conn.createStatement();
			 ResultSet rs = stmt.executeQuery(query)) {

			if (rs.next()) {
				maxId = rs.getInt("maxId");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return maxId + 1;
	}
	
	private void updateTactic(JTextField txtId, JTextField txtName, JTextField txtFormation,
            JTextField txtExplain, JTextField txtAbleTo) {
String idText = txtId.getText().trim();

if (idText.isEmpty()) {
JOptionPane.showMessageDialog(null, "수정할 필드 전술 ID를 입력해주세요.");
return;
}

int id;
try {
id = Integer.parseInt(idText);
} catch (NumberFormatException ex) {
JOptionPane.showMessageDialog(null, "유효한 숫자 ID를 입력해주세요.");
return;
}

// 필드 전술 존재 여부 확인
boolean exists = false;
DefaultTableModel model = (DefaultTableModel) table.getModel();
for (int i = 0; i < model.getRowCount(); i++) {
if ((int) model.getValueAt(i, 0) == id) {
exists = true;
break;
}
}

if (!exists) {
JOptionPane.showMessageDialog(null, "해당 ID의 필드 전술이 존재하지 않습니다.");
return;
}

// 수정할 필드 값 수집
String name = txtName.getText().trim();
String formation = txtFormation.getText().trim();
String explain = txtExplain.getText().trim();
String ableTo = txtAbleTo.getText().trim();

if (name.isEmpty() && formation.isEmpty() && explain.isEmpty() && ableTo.isEmpty()) {
JOptionPane.showMessageDialog(null, "수정할 항목이 없습니다.");
return;
}

StringBuilder queryBuilder = new StringBuilder("UPDATE db2025_tactics SET ");
java.util.List<Object> params = new java.util.ArrayList<>();

if (!name.isEmpty()) {
queryBuilder.append("tacticName = ?, ");
params.add(name);
}
if (!formation.isEmpty()) {
queryBuilder.append("tacticFormation = ?, ");
params.add(formation);
}
if (!explain.isEmpty()) {
queryBuilder.append("explainTactics = ?, ");
params.add(explain);
}
if (!ableTo.isEmpty()) {
try {
int able = Integer.parseInt(ableTo);
if (able != 0 && able != 1) throw new NumberFormatException();
queryBuilder.append("ableToTactic = ?, ");
params.add(able);
} catch (NumberFormatException ex) {
JOptionPane.showMessageDialog(null, "사용 가능 여부는 0 또는 1이어야 합니다.");
return;
}
}

// 마지막 쉼표 제거
queryBuilder.setLength(queryBuilder.length() - 2);
queryBuilder.append(" WHERE idTactic = ?");
params.add(id);

try (Connection conn = DBUtil.getConnection();
java.sql.PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

for (int i = 0; i < params.size(); i++) {
pstmt.setObject(i + 1, params.get(i));
}

pstmt.executeUpdate();
JOptionPane.showMessageDialog(null, "수정이 완료되었습니다.");

// 화면 갱신
loadTacticsData();

// 입력 필드 초기화
txtId.setText("");
txtName.setText("");
txtFormation.setText("");
txtExplain.setText("");
txtAbleTo.setText("");

} catch (Exception ex) {
ex.printStackTrace();
JOptionPane.showMessageDialog(null, "수정 중 오류가 발생했습니다.");


}
}

}
