package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class viewTactics_statistics_List extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewTactics_statistics_List frame = new viewTactics_statistics_List(1);
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
	public viewTactics_statistics_List(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		//setBounds(100, 100, 800, 600);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics_statistics(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("전술 사용 경기 목록");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 33, 788, 29);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 74, 788, 192);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uACBD\uAE30 ID", "\uACBD\uAE30 \uB0A0\uC9DC", "\uC0C1\uB300 \uD300 \uC774\uB984", "\uB4DD\uC810", "\uC2E4\uC810", "\uC804\uC220 \uC774\uB984", "\uD3EC\uBA54\uC774\uC158"
			}
		));
		scrollPane.setViewportView(table);
		
		
		
		loadAllTacticUsages(idTeam);
	}
	public void loadAllTacticUsages(int idTeam) {
	    String[] columnNames = {"전술 ID", "전술 이름", "포메이션", "경기 ID", "경기 날짜", "상대 팀", "득점", "실점", "전술 위치"};
	    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

	    try {
	        Connection conn = DBUtil.getConnection();

	        String sql = 
	            "SELECT Tac.idTactic, Tac.tacticName, Tac.tacticFormation, " +
	            "       G.idGame, G.dateGame, T.nation AS opponentTeam, " +
	            "       G.goalFor, G.goalAgainst, '필드 전술' AS usageType " +
	            "FROM DB2025_GameRec G " +
	            "JOIN DB2025_Tactics Tac ON G.idField = Tac.idTactic " +
	            "JOIN DB2025_Team T ON G.idAgainstTeam = T.idTeam " +
	            "WHERE Tac.idTeam = ? " +
	            "UNION ALL " +
	            "SELECT Tac.idTactic, Tac.tacticName, Tac.tacticFormation, " +
	            "       G.idGame, G.dateGame, T.nation AS opponentTeam, " +
	            "       G.goalFor, G.goalAgainst, '세트피스 전술' AS usageType " +
	            "FROM DB2025_GameRec G " +
	            "JOIN DB2025_Tactics Tac ON G.idSetpiece = Tac.idTactic " +
	            "JOIN DB2025_Team T ON G.idAgainstTeam = T.idTeam " +
	            "WHERE Tac.idTeam = ? " +
	            "ORDER BY idTactic, idGame ";

	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, idTeam);
	        pstmt.setInt(2, idTeam);

	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            model.addRow(new Object[]{
	                rs.getInt("idTactic"),
	                rs.getString("tacticName"),
	                rs.getString("tacticFormation"),
	                rs.getInt("idGame"),
	                rs.getDate("dateGame"),
	                rs.getString("opponentTeam"),
	                rs.getInt("goalFor"),
	                rs.getInt("goalAgainst"),
	                rs.getString("usageType")
	            });
	        }

	        table.setModel(model);

	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	
}
