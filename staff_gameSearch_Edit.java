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
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;

// 경기 기록 및 통계 수정을 위한 GUI 클래스
public class staff_gameSearch_Edit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// 입력 필드
	private JTextField textField_1; //경기 날짜
	private JTextField textField_2; // 사용 안됨
	private JTextField textField_3; // 필드 전술 ID
	private JTextField textField_4; //세트피스 전술 ID
	private JTextField textField_5; //득점
	private JTextField textField_6; //실점
	private JTextField textField_7; // 전체 슛팅 수
	private JTextField textField_8; //유효 슛팅 수
	private JTextField textField_9; //정확한 패스 수
	private JTextField textField_10; // 공격지역 패스 수
	private JTextField textField_11; // 가로채기 수
	private JTextField textField_12; // 차단 수
	
	private int idTeam;//현재 로그인한 팀 ID
	private JComboBox comboBox; // 경기 ID 선택
	private JLabel labelOpponentId; //상대 팀 ID 표시

	//실행 메서드(테스트용)입니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_gameSearch_Edit frame = new staff_gameSearch_Edit(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// 생성자이며, 팀 ID를 받아 화면 구성한다.
	public staff_gameSearch_Edit(int idTeam) {
		this.idTeam = idTeam;

		// 기본 프레임 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//뒤로가기 버튼튼
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameManage(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//제목
		JLabel lblNewLabel = new JLabel("경기 기록 수정");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);

		//저장 버튼 패널
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 227, 438, 39);
		contentPane.add(panel_1);
		
		JButton btnNewButton_1 = new JButton("저장");
		panel_1.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        updateGameStatistics();
		    }
		});

		//입력 필드 패널널
		JPanel panel = new JPanel();
		panel.setBounds(6, 73, 438, 153);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		//경기 ID 선택
		JLabel lblNewLabel_1 = new JLabel("경기 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		comboBox = new JComboBox();
		panel.add(comboBox);
		loadGameid();//DB에서 경기 ID 불러오기
		comboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (comboBox.getSelectedItem() != null) {
		            showAgainstTeam();//상대 팀 ID 표시
		        }
		    }
		});

		//경기 날짜
		JLabel lblNewLabel_2 = new JLabel("경기 날짜");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);

		//상대 팀
		textField_1 = new JTextField();
		/*
			경기 날짜
  			DB2025_GameRec.gameDate와 연동해주기
			*/


		
		panel.add(textField_1);
		textField_1.setColumns(10);

		//필드 전술 ID
		JLabel lblNewLabel_2_1 = new JLabel("상대 팀");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_1);

		labelOpponentId = new JLabel();
		panel.add(labelOpponentId);  
		showAgainstTeam();//초기 상대팀 표시

		//필드 전술 ID
		JLabel lblNewLabel_2_2 = new JLabel("필드 전술");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_2);
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);

		//세트피스 전술 ID
		JLabel lblNewLabel_3 = new JLabel("세트피스 전술");
		panel.add(lblNewLabel_3);
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);

		//득점 반영하였습니다.
		JLabel lblNewLabel_2_3 = new JLabel("득점");
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_3);
		textField_5 = new JTextField();
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		//실점 반영하였습니다.
		JLabel lblNewLabel_2_4 = new JLabel("실점");
		lblNewLabel_2_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_4);
		textField_6 = new JTextField();
		panel.add(textField_6);
		/*
  
  		DB연동이 안되어 있다고 합니다. (DB2025_GameStat.goalAgainst와 연동)
  		*/


		
		textField_6.setColumns(10);

		// 전체 슛팅 수 및 유효 슛팅 수
		JLabel lblNewLabel_2_5 = new JLabel("전체 슛팅 수");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_5);
		textField_7 = new JTextField();
		panel.add(textField_7);
		textField_7.setColumns(10);
		JLabel lblNewLabel_2_6 = new JLabel("유효 슛팅 수");
		lblNewLabel_2_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_6);
		textField_8 = new JTextField();
		panel.add(textField_8);
		textField_8.setColumns(10);

		//패스 수를 표시하는 부분입니다. 
		JLabel lblNewLabel_2_7 = new JLabel("정확한 패스 수");
		lblNewLabel_2_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_7);
		textField_9 = new JTextField();
		panel.add(textField_9);
		textField_9.setColumns(10);
		JLabel lblNewLabel_2_8 = new JLabel("공격지역 패스 수");
		lblNewLabel_2_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_8);
		textField_10 = new JTextField();
		panel.add(textField_10);
		textField_10.setColumns(10);

		//수비 가로채기 및 차단에 대한 데이터를 표시하는 부분입니다. 
		JLabel lblNewLabel_2_9 = new JLabel("수비-가로채기 수");
		lblNewLabel_2_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_9);
		textField_11 = new JTextField();
		panel.add(textField_11);
		textField_11.setColumns(10);
		JLabel lblNewLabel_2_10 = new JLabel("수비 -차단 수");
		lblNewLabel_2_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_10);
		textField_12 = new JTextField();
		panel.add(textField_12);
		textField_12.setColumns(10);

		//출전 선수 수정 버튼입니다. 
		JButton btnNewButton_2 = new JButton("출전 선수 수정");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 int idGame = (Integer) comboBox.getSelectedItem();
				new staff_gameSearch_EditPlayers(idTeam, idGame).setVisible(true); dispose();
			}
		});
		btnNewButton_2.setBounds(327, 6, 117, 29);
		contentPane.add(btnNewButton_2);

		//통계를 볼 수 있는 버튼입니다. 
		JButton btnViewStats = new JButton("통계 보기");
		btnViewStats.setBounds(200, 6, 117, 29);
		contentPane.add(btnViewStats);

		btnViewStats.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int idGame = (Integer) comboBox.getSelectedItem();
		        showGameStatistics();
		    }
		});
		
	}

	//DB에서 팀이 참가한 경기 목록을 불러울 수 있는 메소드입니다. 
	public void loadGameid() {
		try {
		    Connection conn = DBUtil.getConnection();
		    String sql = "SELECT idGame, idTeam1, idTeam2 FROM DB2025_GameRec\n"
		    		+ " WHERE idTeam1 = ? OR idTeam2 = ?";
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, idTeam);
		    pstmt.setInt(2, idTeam);
		    ResultSet rs = pstmt.executeQuery();

		    while (rs.next()) {
		        int idGame = rs.getInt("idGame");
		        comboBox.addItem(idGame); // comboBox에 경기 ID 추가
		    }
		 // showGameStatistics() 또는 loadGameid() 내부에서 아래 코드 추가:
		    
		    rs.close();
		    pstmt.close();
		    
		    conn.close();
		    
		    
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
	}

	//상대 팀 정보를 불러와서 라벨에 표시한다. 
	private void showAgainstTeam() {
		try {
			int idGame = (Integer) comboBox.getSelectedItem();
		    Connection conn = DBUtil.getConnection();
		    String sql = "SELECT idTeam1, idTeam2 FROM DB2025_GameRec\n"
		    		+ " WHERE (idTeam1 = ? OR idTeam2 = ?) AND idGame = ?";
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, idTeam);
		    pstmt.setInt(2, idTeam);
		    pstmt.setInt(3, idGame);
		    ResultSet rs = pstmt.executeQuery();

		    
		    	if (rs.next()) {
		    	    int team1 = rs.getInt("idTeam1");
		    	    int team2 = rs.getInt("idTeam2");

		    	    int opponentId = (idTeam == team1) ? team2 : team1;
		    	    labelOpponentId.setText(String.valueOf(opponentId));  // JLabel에 표시
		    	}

		    
		 // showGameStatistics() 또는 loadGameid() 내부에서 아래 코드 추가:
		    
		    rs.close();
		    pstmt.close();
		    
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}


	//DB에서 해당 경기의 통계 정보 조회를 합니다. 
	private void showGameStatistics() {
	    try {
	        int idGame = (Integer) comboBox.getSelectedItem();

	        Connection conn = DBUtil.getConnection();

	        String sql = "SELECT \n"
	        		
	        		+ "    V.idGame, V.idOurTeam,\n"
	        		+ "    V.idAgainstTeam,\n"
	        		+ "    V.goalFor,\n"
	        		+ "    V.goalAgainst,\n"
	        		+ "    T1.tacticName AS fieldTactic,\n"
	        		+ "    T2.tacticName AS setpieceTactic,\n"
	        		+ "    A.allShots,\n"
	        		+ "    A.shotOnTarget,\n"
	        		+ "    A.accPass,\n"
	        		+ "    A.attackPass,\n"
	        		+ "    A.intercept,\n"
	        		+ "    A.blocking\n"
	        		+ "FROM DB2025_view_GameSummary V\n"
	        		+ "JOIN DB2025_GameStat A ON V.idGame = A.idGame AND V.idOurTeam = A.idOurTeam\n"
	        		+ "LEFT JOIN DB2025_Tactics T1 ON T1.idTactic = A.idField\n"
	        		+ "LEFT JOIN DB2025_Tactics T2 ON T2.idTactic = A.idSetpiece\n where A.idgame = ?";
	        		
	        		
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, idGame);
	        ResultSet rs = pstmt.executeQuery();

	        boolean found = false;

	        while (rs.next()) {
	            int gameId = rs.getInt("idGame");
	            int ourTeamId = rs.getInt("idOurTeam");

	            if (gameId == idGame && ourTeamId == idTeam) {
	                StringBuilder sb = new StringBuilder();
	                sb.append("경기 ID: ").append(rs.getInt("idGame")).append("\n");
	                sb.append("우리 팀 ID: ").append(rs.getInt("idOurTeam")).append("\n");
	                sb.append("상대 팀 ID: ").append(rs.getInt("idAgainstTeam")).append("\n");
	                sb.append("우리 팀 득점: ").append(rs.getInt("goalFor")).append("\n");
	                sb.append("상대 팀 득점: ").append(rs.getInt("goalAgainst")).append("\n");
	                //sb.append("필드 전술 ID: ").append(rs.getInt("idField")).append("\n");
	                sb.append("필드 전술명: ").append(rs.getString("fieldTactic")).append("\n");
	                //sb.append("세트피스 전술 ID: ").append(rs.getInt("idSetpiece")).append("\n");
	                sb.append("세트피스 전술명: ").append(rs.getString("setpieceTactic")).append("\n");

	                sb.append("전체 슛팅 수: ").append(rs.getInt("allShots")).append("\n");
	                sb.append("유효 슛팅 수: ").append(rs.getInt("shotOnTarget")).append("\n");
	                sb.append("정확한 패스 수: ").append(rs.getInt("accPass")).append("\n");
	                sb.append("공격지역 패스 수: ").append(rs.getInt("attackPass")).append("\n");
	                sb.append("수비 - 가로채기 수: ").append(rs.getInt("intercept")).append("\n");
	                sb.append("수비 - 차단 수: ").append(rs.getInt("blocking")).append("\n");

	                javax.swing.JOptionPane.showMessageDialog(null, sb.toString(), "게임 통계", javax.swing.JOptionPane.INFORMATION_MESSAGE);
	                found = true;
	                break;
	            }
	        }

	        if (!found) {
	            javax.swing.JOptionPane.showMessageDialog(null, "해당 경기 통계가 존재하지 않습니다.", "정보 없음", javax.swing.JOptionPane.WARNING_MESSAGE);
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        javax.swing.JOptionPane.showMessageDialog(null, "오류 발생: " + e.getMessage(), "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
	    }
	}

	//DB에 경기 통계 업데이트를 하는 부분입니다. 
	private void updateGameStatistics() {
	    int idGame = (Integer) comboBox.getSelectedItem();

	    try (Connection conn = DBUtil.getConnection()) {
	        StringBuilder sql = new StringBuilder("UPDATE DB2025_GameStat SET ");
	        boolean first = true;

	        // 조건적으로 세팅할 항목들을 쿼리 문자열로 구성하였습니다.
	        if (!textField_3.getText().trim().isEmpty()) {
	            sql.append("idField = ?");
	            first = false;
	        }
	        if (!textField_4.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("idSetpiece = ?");
	            first = false;
	        }
	        if (!textField_5.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("goalOurTeam = ?");
	            first = false;
	        }
	        if (!textField_7.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("allShots = ?");
	            first = false;
	        }
	        if (!textField_8.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("shotOnTarget = ?");
	            first = false;
	        }
	        if (!textField_9.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("accPass = ?");
	            first = false;
	        }
	        if (!textField_10.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("attackPass = ?");
	            first = false;
	        }
	        if (!textField_11.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("intercept = ?");
	            first = false;
	        }
	        if (!textField_12.getText().trim().isEmpty()) {
	            if (!first) sql.append(", ");
	            sql.append("blocking = ?");
	            first = false;
	        }

	        sql.append(" WHERE idGame = ? AND idOurTeam = ?");

	        PreparedStatement pstmt = conn.prepareStatement(sql.toString());

	        // 값을 바인딩하였습니다.
	        int paramIndex = 1;
	        if (!textField_3.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_3.getText().trim()));
	        }
	        if (!textField_4.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_4.getText().trim()));
	        }
	        if (!textField_5.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_5.getText().trim()));
	        }
	        if (!textField_7.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_7.getText().trim()));
	        }
	        if (!textField_8.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_8.getText().trim()));
	        }
	        if (!textField_9.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_9.getText().trim()));
	        }
	        if (!textField_10.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_10.getText().trim()));
	        }
	        if (!textField_11.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_11.getText().trim()));
	        }
	        if (!textField_12.getText().trim().isEmpty()) {
	            pstmt.setInt(paramIndex++, Integer.parseInt(textField_12.getText().trim()));
	        }

	        pstmt.setInt(paramIndex++, idGame);
	        pstmt.setInt(paramIndex++, idTeam);  // this.idTeam

	        int updated = pstmt.executeUpdate();

	        if (updated > 0) {
	            javax.swing.JOptionPane.showMessageDialog(null, "경기 통계가 성공적으로 수정되었습니다.");
	        } else {
	            javax.swing.JOptionPane.showMessageDialog(null, "수정할 데이터가 없습니다.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        javax.swing.JOptionPane.showMessageDialog(null, "오류 발생: " + e.getMessage(), "오류", javax.swing.JOptionPane.ERROR_MESSAGE);
	    }
	}

}
