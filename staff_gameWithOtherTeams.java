package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class staff_gameWithOtherTeams extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam;
	private JComboBox comboBox_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_gameWithOtherTeams frame = new staff_gameWithOtherTeams(1);
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
	public staff_gameWithOtherTeams(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 805, 305);
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
		
		JLabel lblNewLabel = new JLabel("다른 팀과 치른 경기 요약");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 33, 438, 29);
		contentPane.add(lblNewLabel);
		
		JComboBox<String> comboBox = new JComboBox<>();

		try {
		    Connection conn = DBUtil.getConnection();
		    String sql = "SELECT idTeam FROM DB2025_Team WHERE idTeam <> ?";
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    
		    pstmt.setInt(1, idTeam);

		    ResultSet rs = pstmt.executeQuery();

		    DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		    while (rs.next()) {
		        int teamId = rs.getInt("idTeam");
		        String teamLabel = "팀 " + teamId;
		        model.addElement(teamLabel);
		    }

		    comboBox.setModel(model);

		    rs.close();
		    pstmt.close();
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}

		comboBox.setBounds(57, 74, 157, 27);
		contentPane.add(comboBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 105, 780, 161);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uACBD\uAE30 id", "\uACBD\uAE30 \uB0A0\uC9DC", "\uB4DD\uC810", "\uC2E4\uC810", "\uC804\uCCB4 \uC29B\uD305 \uC218 ", "\uC720\uD6A8\uC29B\uD305 \uC218", "\uC815\uD655\uD55C \uD328\uC2A4 \uC218", "\uACF5\uACA9\uC9C0\uC5ED \uD328\uC2A4 \uC218", "\uC218\uBE44-\uAC00\uB85C\uCC44\uAE30 \uC218", "\uC218\uBE44-\uCC28\uB2E8 \uC218"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("상대팀");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(6, 77, 52, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("정렬");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		lblNewLabel_1_1.setBounds(269, 77, 52, 16);
		contentPane.add(lblNewLabel_1_1);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"승리", "패배", "최신 경기순"}));
		comboBox_1.setBounds(313, 74, 131, 27);
		contentPane.add(comboBox_1);
		
		ActionListener reloadListener = new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String teamStr = (String) comboBox.getSelectedItem();
		        if (teamStr != null && teamStr.startsWith("팀 ")) {
		            try {
		                int againstTeamId = Integer.parseInt(teamStr.substring(2).trim());
		                loadGameData(idTeam, againstTeamId);
		            } catch (NumberFormatException ex) {
		                ex.printStackTrace();
		            }
		        }
		    }
		};

		comboBox.addActionListener(reloadListener);
		comboBox_1.addActionListener(reloadListener);  // comboBox_1도 같은 리스너 등록

		// 프레임 생성자 마지막 부분
		String teamStr = (String) comboBox.getSelectedItem();
		String sortStr = (String) comboBox_1.getSelectedItem();
		if (teamStr != null && teamStr.startsWith("팀 ") && sortStr != null) {
		    try {
		        int againstTeamId = Integer.parseInt(teamStr.substring(2).trim());
		        loadGameData(idTeam, againstTeamId);
		    } catch (NumberFormatException ex) {
		        ex.printStackTrace();
		    }
		}

		
	}
	private void loadGameData(int idTeam, int againstTeamId) {
	    String sortOption = (String) comboBox_1.getSelectedItem();  // comboBox_1 사용

	    String sql = "SELECT idGame, dateGame, goalFor, goalAgainst, allShots, shotOnTarget, accPass, attackPass, intercept, blocking "
	               + "FROM DB2025_Game_Info_All "
	               + "WHERE idOurTeam = ? AND idAgainstTeam = ?";

	    if ("승리".equals(sortOption)) {
	        sql += " AND goalFor > goalAgainst";
	    } else if ("패배".equals(sortOption)) {
	        sql += " AND goalFor < goalAgainst";
	    } else if ("최신 경기순".equals(sortOption)) {
	        sql += " ORDER BY dateGame DESC";
	    } 
	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, idTeam);
	        pstmt.setInt(2, againstTeamId);

	        ResultSet rs = pstmt.executeQuery();

	        DefaultTableModel model = new DefaultTableModel(
	            new Object[] { "경기 ID", "경기 날짜", "득점", "실점", "전체 슛", "유효 슛", "정확한 패스", "공격 지점 패스", "차단", "블로킹" }, 0);

	        while (rs.next()) {
	            model.addRow(new Object[] {
	                rs.getInt("idGame"),
	                rs.getDate("dateGame"),
	                rs.getInt("goalFor"),
	                rs.getInt("goalAgainst"),
	                rs.getInt("allShots"),
	                rs.getInt("shotOnTarget"),
	                rs.getInt("accPass"),
	                rs.getInt("attackPass"),
	                rs.getInt("intercept"),
	                rs.getInt("blocking")
	            });
	        }

	        table.setModel(model);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
