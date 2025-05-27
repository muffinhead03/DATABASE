package DB2025Team09;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

//필드 전술 관리 창 클래스입니다. 
public class staff_fieldTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam; //현재 팀ID

	private JTextField txtId, txtName, txtFormation, txtExplain;
	private JComboBox<String> comboAble;

	//테스트용 메인 함수. 실제로는 이용되지 않습니다. 개발 중 단독 실행시 사용됩니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				staff_fieldTactics frame = new staff_fieldTactics(1);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public staff_fieldTactics(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 370);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(6, 6, 117, 29);
		btnBack.addActionListener(e -> {
			new staff_tacticManage(idTeam).setVisible(true);
			dispose();
		});
		contentPane.add(btnBack);

		//타이틀 라벨입니다.
		JLabel lblTitle = new JLabel("필드 전술 관리");
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(6, 24, 618, 29);
		contentPane.add(lblTitle);

		//테이블 표시 패널입니다. 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 55, 618, 140);
		contentPane.add(scrollPane);

		//테이블을 구성한 부분이다. 
		table = new JTable();
		// ableToTactic 컬럼을 테이블에 보여주고 싶으면 아래에 "사용 가능 여부" 컬럼 추가
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
			"ID", "전술 이름", "포메이션", "설명", "사용 가능 여부"
		}));
		scrollPane.setViewportView(table);

		// 전술 정보를 입력받는 UI 영역을 구성하는 코드입니다.
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 205, 618, 70);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 5, 10, 5));

		// 전술 추가/수정 시 필요한 데이터를 입력받는 5개의 입력 필드를 구성하는 패널입니다.
		panel_1.add(new JLabel("전술 ID", SwingConstants.CENTER));
		panel_1.add(new JLabel("전술 이름", SwingConstants.CENTER));
		panel_1.add(new JLabel("포메이션", SwingConstants.CENTER));
		panel_1.add(new JLabel("설명", SwingConstants.CENTER));
		panel_1.add(new JLabel("사용 가능 여부", SwingConstants.CENTER));

		// 위에서 순서대로 전술ID, 전술 이름, 포메이션, 전술 설명, 사용가능 여부를 사용자가 입력하고 설정하는 부분이다. 
		txtId = new JTextField();
		txtName = new JTextField();
		txtFormation = new JTextField();
		txtExplain = new JTextField();
		comboAble = new JComboBox<>(new String[] {"가능", "불가능"});

		panel_1.add(txtId);
		panel_1.add(txtName);
		panel_1.add(txtFormation);
		panel_1.add(txtExplain);
		panel_1.add(comboAble);

		// 기능 버튼을 배치하는 하단 패널입니다.
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 280, 618, 35);
		contentPane.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 3, 10, 0));

		//신규 전술 추가 버튼입니다.
		JButton btnAdd = new JButton("신규 추가");
		btnAdd.addActionListener(e -> addTactic());
		panel_2.add(btnAdd);

		//전술을 삭제하는 버튼입니다. 
		JButton btnDelete = new JButton("삭제");
		btnDelete.addActionListener(e -> deleteTactic());
		panel_2.add(btnDelete);

		//전술 수정하는 버튼입니다. 
		JButton btnEdit = new JButton("수정");
		btnEdit.addActionListener(e -> editTactic());
		panel_2.add(btnEdit);

		// 테이블 클릭시 텍스트 필드에 값 표시, 선택한 행의 값이 입력 필드에 자동 입력되는 것입니다. 
		table.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
				int row = table.getSelectedRow();
				txtId.setText(table.getValueAt(row, 0).toString());
				txtName.setText(table.getValueAt(row, 1).toString());
				txtFormation.setText(table.getValueAt(row, 2).toString());
				txtExplain.setText(table.getValueAt(row, 3).toString());
				// 사용 가능 여부는 테이블 4번째 컬럼에 "가능"/"불가능" 텍스트로 표현되며, 문자열로 비교하여 콤보박스를 선택 설정한다. 
				String ableText = table.getValueAt(row, 4).toString();
				if (ableText.equals("1") || ableText.equals("가능")) {
					comboAble.setSelectedItem("가능");
				} else {
					comboAble.setSelectedItem("불가능");
				}
			}
		});
		//프로그램 시작 시 전술 데이터 로드한다.
		loadTacticsData();
	}

	//DB에서 필드 전술 데이터를 불러와 테이블에 출력합니다.
	private void loadTacticsData() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);//기존 행을 초기화합니다.
		String query = "SELECT idTactic, tacticName, tacticFormation, explainTactics, ableToTactic FROM db2025_tactics WHERE tacticType = 'Field' AND idTeam = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) { // ✅ PreparedStatement 사용

	        pstmt.setInt(1, idTeam);
	        ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int able = rs.getInt("ableToTactic");
				model.addRow(new Object[] {
					rs.getInt("idTactic"),
					rs.getString("tacticName"),
					rs.getString("tacticFormation"),
					rs.getString("explainTactics"),
					able == 1 ? "가능" : "불가능"
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//신규 전술을 추가합니다.
	private void addTactic() {
		String name = txtName.getText().trim();
		String formation = txtFormation.getText().trim();
		String explain = txtExplain.getText().trim();

		
		if (name.isEmpty() && formation.isEmpty() && explain.isEmpty() && comboAble.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this, "수정할 항목을 하나 이상 입력하세요.");
			return;
		}


		int able = comboAble.getSelectedItem().equals("가능") ? 1 : 0;
		int newId = getNextTacticId();//ID 생성합니다.
		
		String insertQuery = "INSERT INTO db2025_tactics (idTactic, tacticName, tacticFormation, explainTactics, tacticType, idTeam, ableToTactic) VALUES (?, ?, ?, ?, 'Field', ?, ?)";
		try (Connection conn = DBUtil.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
			pstmt.setInt(1, newId);
			pstmt.setString(2, name);
			pstmt.setString(3, formation);
			pstmt.setString(4, explain);
			pstmt.setInt(5, idTeam);
			pstmt.setInt(6, able);
			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(this, "전술이 추가되었습니다.");
			clearInputs();
			loadTacticsData();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "추가 중 오류 발생");
		}
	}

	//전술을 삭제합니다.
	private void deleteTactic() {
		String idText = txtId.getText().trim();
		if (idText.isEmpty()) {
			JOptionPane.showMessageDialog(this, "ID를 입력하세요.");
			return;
		}
		int id;
		try {
			id = Integer.parseInt(idText);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "숫자 ID를 입력하세요.");
			return;
		}

		int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
		if (confirm != JOptionPane.YES_OPTION) return;

		String query = "DELETE FROM db2025_tactics WHERE idTactic = ? AND tacticType = 'Field'";
		try (Connection conn = DBUtil.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			int affected = pstmt.executeUpdate();
			if (affected > 0) {
				JOptionPane.showMessageDialog(this, "삭제 완료");
				loadTacticsData();
				clearInputs();
			} else {
				JOptionPane.showMessageDialog(this, "해당 ID의 세트피스 전술이 없습니다.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "삭제 중 오류");
		}
	}

	//전술 정보를 수정합니다다
	private void editTactic() {
	    String idText = txtId.getText().trim();
	    if (idText.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "수정할 ID를 입력하세요.");
	        return;
	    }

	    int id;
	    try {
	        id = Integer.parseInt(idText);
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "유효한 숫자 ID를 입력하세요.");
	        return;
	    }

	    if (!tacticExists(id)) {
	        JOptionPane.showMessageDialog(this, "해당 ID의 전술이 존재하지 않습니다.");
	        return;
	    }

	    String name = txtName.getText().trim();
	    String formation = txtFormation.getText().trim();
	    String explain = txtExplain.getText().trim();
	    int able = comboAble.getSelectedItem().equals("가능") ? 1 : 0;

	    // 아무 항목도 입력되지 않았는지 확인합니다. (가능 여부는 항상 포함되므로 제외)
	    if (name.isEmpty() && formation.isEmpty() && explain.isEmpty()) {
	        int confirm = JOptionPane.showConfirmDialog(this, "사용 가능 여부만 변경하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
	        if (confirm != JOptionPane.YES_OPTION) return;
	    }

		//SQL 업데이트 쿼리 구성합니다.
	    StringBuilder sb = new StringBuilder("UPDATE db2025_tactics SET ");
	    boolean first = true;
	    if (!name.isEmpty()) {
	        sb.append("tacticName = ?");
	        first = false;
	    }
	    if (!formation.isEmpty()) {
	        if (!first) sb.append(", ");
	        sb.append("tacticFormation = ?");
	        first = false;
	    }
	    if (!explain.isEmpty()) {
	        if (!first) sb.append(", ");
	        sb.append("explainTactics = ?");
	        first = false;
	    }

	    if (!first) sb.append(", ");
	    sb.append("ableToTactic = ?");
	    sb.append(" WHERE idTactic = ? AND tacticType = 'Field'");

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sb.toString())) {
	        int idx = 1;
	        if (!name.isEmpty()) pstmt.setString(idx++, name);
	        if (!formation.isEmpty()) pstmt.setString(idx++, formation);
	        if (!explain.isEmpty()) pstmt.setString(idx++, explain);
	        pstmt.setInt(idx++, able);
	        pstmt.setInt(idx, id);
	        pstmt.executeUpdate();
	        JOptionPane.showMessageDialog(this, "수정 완료");
	        loadTacticsData();
	        clearInputs();
	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "수정 중 오류");
	    }
	}
	//해당 전술 ID가 존재하는지 확인합니다.
	private boolean tacticExists(int id) {
		String query = "SELECT 1 FROM db2025_tactics WHERE idTactic = ? AND tacticType = 'Field'";
		try (Connection conn = DBUtil.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			return rs.next();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// 가장 큰 전술 ID를 기준으로 다음 ID 생성합니다. 
	private int getNextTacticId() {
		String query = "SELECT MAX(idTactic) AS maxId FROM db2025_tactics";
		try (Connection conn = DBUtil.getConnection();
		     Statement stmt = conn.createStatement();
		     ResultSet rs = stmt.executeQuery(query)) {
			if (rs.next()) return rs.getInt("maxId") + 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	//입력 필드 초기화합니다. 
	private void clearInputs() {
		txtId.setText("");
		txtName.setText("");
		txtFormation.setText("");
		txtExplain.setText("");
	
}}
