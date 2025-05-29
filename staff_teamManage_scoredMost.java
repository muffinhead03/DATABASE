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
import java.sql.*;

//우리 팀이 가장 많은 득점을 기록한 상대팀 정보를 조회하는 프레임입니다. 
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
				new staff_teamManage(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//타이틀 라벨입니다. 
		JLabel lblNewLabel = new JLabel("우리 팀이 최다 득점한 상태팀");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);

		//결과 표시 채널입니다. 
		JPanel panel = new JPanel();
		panel.setBounds(6, 73, 438, 82);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		//각 라벨의 구조입니다. 
		JLabel lblNewLabel_1 = new JLabel("상대 팀 ID:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
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
		

		//DB를 조회해서, 우리 팀이 가장 많은 득점을 기록한 상대 팀 1팀을 분석합니다. 
		try {

		    Connection conn = DBUtil.getConnection();

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

		//결과가 있으면, 이를 UI에 출력하는 역할을 합니다.
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
			    //결과가 없다면, 해당 else문을 실행합니다. 
		        lblNewLabel_2.setText("정보 없음");
		        lblNewLabel_1_2.setText("-");
		        lblNewLabel_1_4.setText("-");
		        lblNewLabel_1_6.setText("-");
		    }

		    rs.close();
		    pstmt.close();
		    conn.close();
		} catch (SQLException e) {
		    e.printStackTrace();
		}
	}
	
	
}
