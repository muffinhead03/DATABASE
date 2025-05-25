package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.sql.*;

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
	
	private int idTeam;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public staff_gameCreate(int idTeam) {
		this.idTeam = idTeam;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameManage(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("경기 기록 생성");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 75, 438, 159);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("경기 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JTextField textField_idGame = new JTextField();
		textField_idGame.setEditable(false); // 수정 불가
		textField_idGame.setText(String.valueOf(getNextGameId()));
		panel.add(textField_idGame);
		
		JLabel lblNewLabel_2 = new JLabel("경기 일자");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(14);
		
		JLabel lblNewLabel_3 = new JLabel("상대 팀");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(14);
		
		/*JLabel lblNewLabel_4 = new JLabel("사용 필드 전술");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		comboBox_fieldTactic = new JComboBox<>();
		panel.add(comboBox_fieldTactic);
		//comboBox_fieldTactic.setColumns(14);
		
		JLabel lblNewLabel_5 = new JLabel("사용 세트피스 전술");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		

		comboBox_setpieceTactic = new JComboBox<>();
		panel.add(comboBox_setpieceTactic);
		
		loadTacticsFromDatabase();
		
		JLabel lblNewLabel_6 = new JLabel("득점");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_6);
		
		textField_5 = new JTextField();
		panel.add(textField_5);
		textField_5.setColumns(14);
		
		JLabel lblNewLabel_7 = new JLabel("실점");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7);
		
		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(14);
	
		JLabel lblNewLabel_8 = new JLabel("슛팅");
		lblNewLabel_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_8);
		
		textField_7 = new JTextField();
		panel.add(textField_7);
		textField_7.setColumns(14);
		
		
		JLabel lblNewLabel_13 = new JLabel("유효 슛팅");
		lblNewLabel_13.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_13);
		
		textField_12 = new JTextField();
		panel.add(textField_12);
		textField_12.setColumns(14);
		
		JLabel lblNewLabel_9 = new JLabel("정확한 패스");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_9);
		
		textField_8 = new JTextField();
		panel.add(textField_8);
		textField_8.setColumns(14);
		
		JLabel lblNewLabel_10 = new JLabel("공격지역 패스");
		lblNewLabel_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_10);
		
		textField_9 = new JTextField();
		panel.add(textField_9);
		textField_9.setColumns(14);
		
		JLabel lblNewLabel_11 = new JLabel("인터셉트");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_11);
		
		textField_10 = new JTextField();
		panel.add(textField_10);
		textField_10.setColumns(14);
		
		JLabel lblNewLabel_12 = new JLabel("블로킹");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_12);
		
		textField_11 = new JTextField();
		panel.add(textField_11);
		textField_11.setColumns(14);
		
		*/
		JButton btnNewButton_1 = new JButton("생성");
		btnNewButton_1.setBounds(6, 277, 438, 29);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 입력값 가져오기
					String dateGame = textField_1.getText();
					int idAgainstTeam = Integer.parseInt(textField_2.getText());
					
					//String fieldTacticName = (String) comboBox_fieldTactic.getSelectedItem();
					//String setpieceTacticName = (String) comboBox_setpieceTactic.getSelectedItem();
					//int idField = getTacticIdByName(fieldTacticName, "Field");
					//int idSetpiece = getTacticIdByName(setpieceTacticName, "Setpiece");

					
					//int goalFor = Integer.parseInt(textField_5.getText());
					//int goalAgainst = Integer.parseInt(textField_6.getText());
					int idGame = Integer.parseInt(textField_idGame.getText());
					
					
					//int allShots = Integer.parseInt(textField_7.getText());
					//int accPass = Integer.parseInt(textField_8.getText());
					//int attackPass = Integer.parseInt(textField_9.getText());
					//int intercept = Integer.parseInt(textField_10.getText());
					//int blocking = Integer.parseInt(textField_11.getText());
					//int shotOnTarget = Integer.parseInt(textField_12.getText());
					
					LocalDate localDate = LocalDate.parse(dateGame);
					LocalDateTime localDateTime = localDate.atStartOfDay();
					Timestamp timestamp = Timestamp.valueOf(localDateTime);
					// DB 연결
					Connection conn = DBUtil.getConnection();

					String sql1 = "INSERT INTO DB2025_GameRec " +
								 "(idGame, dateGame, idTeam1, idTeam2) " +
								 "VALUES (?, ?, ?, ?)";

					PreparedStatement pstmt1 = conn.prepareStatement(sql1);
					idGame = getNextGameId();
					// 바인딩
					pstmt1.setInt(1, idGame);
					pstmt1.setTimestamp(2, timestamp);
					
					pstmt1.setInt(3, idTeam); // 생성자에서 전달된 우리 팀 ID
					pstmt1.setInt(4, idAgainstTeam);
					if (idTeam>idAgainstTeam) {
						pstmt1.setInt(4, idTeam); // 생성자에서 전달된 우리 팀 ID
						pstmt1.setInt(3, idAgainstTeam);
					}

					pstmt1.executeUpdate();
					
					/*String sql2 = "INSERT INTO DB2025_GameStat " +
							  "(idGame, allShots, accPass,attackPass, intercept,blocking, shotOnTarget) " +
							  "VALUES (?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement pstmt2 = conn.prepareStatement(sql2);
				pstmt2.setInt(1, idGame);
				pstmt2.setInt(2, allShots);
				pstmt2.setInt(3, accPass);
				pstmt2.setInt(4, attackPass);
				pstmt2.setInt(5, intercept);
				pstmt2.setInt(6, blocking);
				pstmt2.setInt(7, shotOnTarget);
				pstmt2.executeUpdate();
				pstmt2.close();
					*/
					conn.close();

					// 알림창 표시
					javax.swing.JOptionPane.showMessageDialog(null, "경기 기록이 저장되었습니다.");

					// 이전 화면으로 이동
					dispose();
					new staff_gameManage(idTeam).setVisible(true);

				} catch (Exception ex) {
					ex.printStackTrace();
					javax.swing.JOptionPane.showMessageDialog(null, "입력 오류 또는 DB 오류가 발생했습니다.");
				}
			}
		});


	}
	
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
	
	/*private void loadTacticsFromDatabase() {
	    try {
	        Connection conn = DBUtil.getConnection();
	        String sql = "SELECT tacticName, tacticType FROM DB2025_Tactics " +
	                     "WHERE idTeam = ?";

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, idTeam);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String tacticName = rs.getString("tacticName");
	            String tacticType = rs.getString("tacticType");

	            if (tacticType.equalsIgnoreCase("Field")) {
	                comboBox_fieldTactic.addItem(tacticName);
	            } else if (tacticType.equalsIgnoreCase("Setpiece")) {
	                comboBox_setpieceTactic.addItem(tacticName);
	            }
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}*/

	/*private int getTacticIdByName(String tacticName, String tacticType) throws SQLException {
	    Connection conn = DBUtil.getConnection();
	    String sql = "SELECT idTactic FROM DB2025_Tactics WHERE tacticName = ? AND tacticType = ? AND idTeam = ?";
	    PreparedStatement pstmt = conn.prepareStatement(sql);
	    pstmt.setString(1, tacticName);
	    pstmt.setString(2, tacticType);
	    pstmt.setInt(3, DKicker.currentTeamId);
	    ResultSet rs = pstmt.executeQuery();

	    int id = -1;
	    if (rs.next()) {
	        id = rs.getInt("idTactic");
	    }

	    rs.close();
	    pstmt.close();
	    conn.close();
	    return id;
	}
*/
}
