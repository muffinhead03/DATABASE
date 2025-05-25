package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class staff_teamManage_scoredMost extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private int idTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_teamManage_scoredMost frame = new staff_teamManage_scoredMost(1);
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
	public staff_teamManage_scoredMost(int idTeam) {
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
				new staff_teamManage().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("우리 팀이 최다 득점한 상태팀");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 73, 438, 82);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("상대 팀 ID:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_1_1 = new JLabel("국가:");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("경기 수:");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("");
		lblNewLabel_1_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("평균 득점:");
		lblNewLabel_1_5.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("");
		lblNewLabel_1_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_6);
		
		
		try {
		    // MySQL 드라이버 명시적으로 로드 (필요 없을 수도 있음)
		    Class.forName("com.mysql.cj.jdbc.Driver");

		    // MySQL 연결 정보 설정
		    String url = "jdbc:mysql://localhost:3306/DB2025Team09?serverTimezone=UTC";
		    String user = "root"; // 사용자의 DB 계정
		    String password = "asdf1234!"; // 실제 비밀번호로 교체

		    Connection conn = DriverManager.getConnection(url, user, password);

		    String query =
		        "SELECT opponentId, T.nation, COUNT(*) AS matchCount, AVG(goal) AS avgGoals " +
		        "FROM ( " +
		        "    SELECT CASE WHEN G.idTeam1 = ? THEN G.idTeam2 ELSE G.idTeam1 END AS opponentId, " +
		        "           S.goalOurTeam AS goal " +
		        "    FROM DB2025_GameRec G " +
		        "    JOIN DB2025_GameStat S ON G.idGame = S.idGame " +
		        "    WHERE S.idOurTeam = ? " +
		        ") AS stats " +
		        "JOIN DB2025_Team T ON stats.opponentId = T.idTeam " +
		        "GROUP BY opponentId " +
		        "ORDER BY avgGoals DESC " +
		        "LIMIT 1";

		    PreparedStatement pstmt = conn.prepareStatement(query);
		    pstmt.setInt(1, idTeam);
		    pstmt.setInt(2, idTeam);

		    ResultSet rs = pstmt.executeQuery();

		    if (rs.next()) {
		        int opponentId = rs.getInt("opponentId");
		        String nation = rs.getString("nation");
		        int matchCount = rs.getInt("matchCount");
		        double avgGoals = rs.getDouble("avgGoals");

		        lblNewLabel_2.setText(String.valueOf(opponentId));
		        lblNewLabel_1_2.setText(nation);
		        lblNewLabel_1_4.setText(String.valueOf(matchCount));
		        lblNewLabel_1_6.setText(String.format("%.2f", avgGoals));
		    } else {
		        lblNewLabel_2.setText("정보 없음");
		        lblNewLabel_1_2.setText("-");
		        lblNewLabel_1_4.setText("-");
		        lblNewLabel_1_6.setText("-");
		    }

		    rs.close();
		    pstmt.close();
		    conn.close();
		} catch (SQLException | ClassNotFoundException e) {
		    e.printStackTrace();
		}
	}
	
	
}
