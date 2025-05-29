package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

//전술별 성과 요약 화면 클래스입니다. 각 전술의 평균 득점, 실점, 슈팅 수, 패스 수 등 주요 지표를 테이블로 보여줍니다.
public class viewTactics_statistics_Achieved extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam;//로그인한 사용자의 팀 ID입니다. 
	
	//테스트용 메인 메서드입니다. 실제 배포 시에는 사용되지 않습니다. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewTactics_statistics_Achieved frame = new viewTactics_statistics_Achieved(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// idTema을 매개변수로 받는 생성자입니다. 
	public viewTactics_statistics_Achieved(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		//뒤로가기 버튼입니다.
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics_statistics(idTeam).setVisible(true); dispose();
			}
			
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//제목 라벨입니다. 
		JLabel lblNewLabel = new JLabel("전술별 성과 요약");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 30, 788, 29);
		contentPane.add(lblNewLabel);

		//테이블 영역을 포함하는 스크롤 패널입니다. 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 71, 788, 195);
		contentPane.add(scrollPane);

		//전술 통계 테이블을 생성하고 초기화합니다. 
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC804\uC220 ID", "\uC804\uC220 \uC774\uB984", "\uC0AC\uC6A9 \uD69F\uC218", "\uD3C9\uADE0 \uB4DD\uC810", "\uD3C9\uADE0 \uC2E4\uC810", "\uD3C9\uADE0 \uC29B\uD305 \uC218", "\uD3C9\uADE0 \uD328\uC2A4 \uC218"
			}
		));
		scrollPane.setViewportView(table);
		
		loadAchievedStatisticsToTable(table);

	}
	//DB에서 전술별 경기 통계 데이터를 불러와 테이블에 표시합니다.
	public void loadAchievedStatisticsToTable(JTable table) {
	    String[] columnNames = {"전술 ID", "전술 이름", "사용 횟수", "평균 득점", "평균 실점", "평균 슈팅 수", "평균 패스 수"};
	    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

	    try {
	        Connection conn = DBUtil.getConnection();

		    //전술별 경기 기록 통계 쿼리입니다. 
	        String sql =
	            "SELECT T.idTactic, T.tacticName, COUNT(*) AS useCount,\n"
	            + "       AVG(S.goalOurTeam) AS avgGoals, AVG(O.goalOurTeam) AS avgGoalsAgainst,\n"
	            + "       AVG(S.allShots) AS avgShots, AVG(S.accPass) AS avgPasses\n"
	            + "FROM DB2025_GameStat S\n"
	            + "JOIN DB2025_GameStat O ON S.idGame = O.idGame AND S.idOurTeam <> O.idOurTeam\n"
	            + "JOIN DB2025_GameRec R ON S.idGame = R.idGame\n"
	            + "JOIN DB2025_Tactics T ON S.idField = T.idTactic OR S.idSetpiece = T.idTactic\n"
	            + "WHERE T.idTeam = ?\n"
	            + "GROUP BY T.idTactic, T.tacticName\n"
	            + "ORDER BY useCount DESC;\n";

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, idTeam);

	        ResultSet rs = pstmt.executeQuery();

		    //결과를 테이블에 한줄씩 추가합니다.
	        while (rs.next()) {
	            int idTactic = rs.getInt("idTactic");
	            String tacticName = rs.getString("tacticName");
	            int useCount = rs.getInt("useCount");
	            double avgGoals = rs.getDouble("avgGoals");
	            double avgAgainst = rs.getDouble("avgGoalsAgainst");
	            double avgShots = rs.getDouble("avgShots");
	            double avgPasses = rs.getDouble("avgPasses");

	            model.addRow(new Object[]{
	                idTactic,
	                tacticName,
	                useCount,
	                String.format("%.2f", avgGoals),
	                String.format("%.2f", avgAgainst),
	                String.format("%.2f", avgShots),
	                String.format("%.2f", avgPasses)
	            });
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();

		    //테이블에 모델을 적용합니다. 
	        table.setModel(model);

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "전술 성과 데이터를 불러오는 데 실패했습니다.");
	    }
	}


}
