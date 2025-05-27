package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

//코치가 자신의 팀에 대한 새 경기를 기록하고 DB에 저장하는 화면입니다. 
public class staff_gameCreate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JComboBox<String> comboBox_fieldTactic;
	private JComboBox<String> comboBox_setpieceTactic;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;
	private JTable table;
	private int idTeam; // 현재 로그인한 코치의 팀 ID

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_gameCreate frame = new staff_gameCreate(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public staff_gameCreate(int idTeam) {
		this.idTeam = idTeam;

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 이전 화면으로 돌아가는 버튼입니다. 
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameManage(idTeam).setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//경기 기록 테이블입니다(이 팀의 기존 경기 목록들을 표시)
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 75, 438, 140);
		contentPane.add(scrollPane);
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"경기 ID", "경기 날짜", "상대 팀", "득점", "실점"}
		));
		scrollPane.setViewportView(table);

		loadTeamGames();

		//제목 라벨입니다.
		JLabel lblNewLabel = new JLabel("경기 기록 생성");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 29);
		contentPane.add(lblNewLabel);

		//경기 정보 입력 패널입니다.
		JPanel panel = new JPanel();
		panel.setBounds(6, 215, 438, 60);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 6, 0, 0));

		//경기 ID(자동으로 생성)입니다. 
		JLabel lblNewLabel_1 = new JLabel("경기 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JTextField textField_idGame = new JTextField();
		textField_idGame.setEditable(false);//수정 불가능하다는 조건이 있습니다.
		textField_idGame.setText(String.valueOf(getNextGameId()));
		panel.add(textField_idGame);

		//경기 일자(자동 생성)입니다. 
		JLabel lblNewLabel_2 = new JLabel("경기 일자");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);

		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(14);

		//상대 팀 ID 입니다.
		JLabel lblNewLabel_3 = new JLabel("상대 팀");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);

		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(14);

		//자팀 득점내역을 표시합니다.
		JLabel lblNewLabel_6 = new JLabel("득점");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_6);

		textField_5 = new JTextField();
		panel.add(textField_5);
		textField_5.setColumns(14);

		//자팀 실점내역을 표시합니다.
		JLabel lblNewLabel_7 = new JLabel("실점");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7);

		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(14);

		//생성 버튼입니다. 
		JButton btnNewButton_1 = new JButton("생성");
		btnNewButton_1.setBounds(6, 277, 438, 29);
		contentPane.add(btnNewButton_1);

		//생성 버튼 클릭 시 DB에 경기 기록 저장합니다. 
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection conn = null;
				try {
					// 입력값 수집 및 형식 변환합니다.
					String dateGame = textField_1.getText();
					int idAgainstTeam = Integer.parseInt(textField_2.getText());
					int goalFor = Integer.parseInt(textField_5.getText());
					int goalAgainst = Integer.parseInt(textField_6.getText());
					int idGame = Integer.parseInt(textField_idGame.getText());

					LocalDate localDate = LocalDate.parse(dateGame);
					LocalDateTime localDateTime = localDate.atStartOfDay();
					Timestamp timestamp = Timestamp.valueOf(localDateTime);

					conn = DBUtil.getConnection();
					conn.setAutoCommit(false);

					//1. 경기 기본 정보 저장(GameRec)
					String sql1 = "INSERT INTO DB2025_GameRec (idGame, dateGame, idTeam1, idTeam2) VALUES (?, ?, ?, ?)";
					PreparedStatement pstmt1 = conn.prepareStatement(sql1);
					idGame = getNextGameId();

					//ID 크기를 비교해서, DB 저장 시 팀 순서 일관성을 유지합니다. 
					pstmt1.setInt(1, idGame);
					pstmt1.setTimestamp(2, timestamp);
					pstmt1.setInt(3, idTeam);
					pstmt1.setInt(4, idAgainstTeam);
					if (idTeam > idAgainstTeam) {
						pstmt1.setInt(3, idAgainstTeam);
						pstmt1.setInt(4, idTeam);
					}
					pstmt1.executeUpdate();

					//2. 자팀 경기 기록(GameStat) 저장합니다
					String sql2 = "INSERT INTO DB2025_GameStat (idGame, idOurTeam, goalOurTeam) VALUES (?, ?, ?)";
					PreparedStatement pstmt2 = conn.prepareStatement(sql2);
					pstmt2.setInt(1, idGame);
					pstmt2.setInt(2, idTeam);
					pstmt2.setInt(3, goalFor);
					pstmt2.executeUpdate();
					pstmt2.close();

					//3. 상대팀 경기 기록(GameStat)을 저장합니다.
					String sql3 = "INSERT INTO DB2025_GameStat (idGame, idOurTeam, goalOurTeam) VALUES (?, ?, ?)";
					PreparedStatement pstmt3 = conn.prepareStatement(sql3);
					pstmt3.setInt(1, idGame);
					pstmt3.setInt(2, idAgainstTeam);
					pstmt3.setInt(3, goalAgainst);
					pstmt3.executeUpdate();
					pstmt3.close();

					//경기 기록 저장 성공 메세지 및 화면 전환합니다. 
					javax.swing.JOptionPane.showMessageDialog(null, "경기 기록이 저장되었습니다.");
					conn.commit();
					conn.setAutoCommit(true);
					conn.close();

					dispose();
					new staff_gameManage(idTeam).setVisible(true);

				} catch (Exception ex) {
					ex.printStackTrace();
					if (conn != null) {
						try {
							conn.rollback();
						} catch (SQLException rollbackEx) {
							rollbackEx.printStackTrace();
						}
					}
					javax.swing.JOptionPane.showMessageDialog(null, "입력 오류 또는 DB 오류가 발생했습니다.");
				} finally {
					if (conn != null) {
						try {
							conn.setAutoCommit(true);
							conn.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
	}

	//다음 경기 ID를 생성합니다(MAX(idGame) + 1)
	public int getNextGameId() {
		int nextId = 1;
		try {
			Connection conn = DBUtil.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT MAX(idGame) FROM DB2025_GameRec");
			if (rs.next()) {
				nextId = rs.getInt(1) + 1;
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nextId;
	}

	//팀의 기존 경기 목록을 테이블에 불러옵니다. 
	private void loadTeamGames() {
		String sql = "SELECT idGame, dateGame, idOurTeam, idAgainstTeam, goalFor, goalAgainst FROM DB2025_view_GameSummary WHERE idOurTeam = ? ORDER BY dateGame DESC";
		try (Connection conn = DBUtil.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idTeam);

			try (ResultSet rs = pstmt.executeQuery()) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.setRowCount(0);

				while (rs.next()) {
					int gameId = rs.getInt("idGame");
					Date date = rs.getDate("dateGame");
					int opponent = rs.getInt("idAgainstTeam");
					int goalFor = rs.getInt("goalFor");
					int goalAgainst = rs.getInt("goalAgainst");
					
					// 상대팀이 DB 기준에 따라 반대 순서일 수도 있으므로 교정합니다.
					if (opponent == idTeam) {
						opponent = rs.getInt("idOurTeam");
						int temp = goalFor;
						goalFor = goalAgainst;
						goalAgainst = temp;
					}
					model.addRow(new Object[]{gameId, date.toString(), opponent, goalFor, goalAgainst});
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
