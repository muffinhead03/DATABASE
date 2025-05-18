package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class viewGames extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JTable table;
	private int idTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewGames frame = new viewGames(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void loadTeamGames() {
		String sql = "SELECT idGame, dateGame,idOurTeam, idAgainstTeam, goalFor, goalAgainst\n"
				+ "FROM DB2025_GameRec \n"
				+ "WHERE idOurTeam = ? OR idAgainstTeam = ?";
		try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setInt(1, idTeam); // 바인딩
		        pstmt.setInt(2, idTeam);

		        try (ResultSet rs = pstmt.executeQuery()) {
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		            model.setRowCount(0); // 기존 데이터 삭제

		        	while (rs.next()) {
		            	int gameId = rs.getInt("idGame");
		                Date date = rs.getDate("dateGame");
		                int opponent = rs.getInt("idAgainstTeam");
		                int goalFor = rs.getInt("goalFor");
		                int goalAgainst = rs.getInt("goalAgainst");
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
	
	private void loadAllGames() {
		String sql = "SELECT idGame, dateGame,idOurTeam, idAgainstTeam, goalFor, goalAgainst\n"
				+ "FROM DB2025_GameRec";
		try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {


		        try (ResultSet rs = pstmt.executeQuery()) {
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		            model.setRowCount(0); // 기존 데이터 삭제

		        	while (rs.next()) {
		            	int gameId = rs.getInt("idGame");
		                Date date = rs.getDate("dateGame");
		                int team1 = rs.getInt("idOurteam");
		                int team2 = rs.getInt("idAgainstTeam");
		                int goalFor = rs.getInt("goalFor");
		                int goalAgainst = rs.getInt("goalAgainst");

		                model.addRow(new Object[]{gameId, date.toString(),team1, team2, goalFor, goalAgainst});
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}

	/**
	 * Create the frame.
	 */
	public viewGames(int idTeam) {
		this.idTeam=idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 92, 438, 174);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"경기 ID", "경기 날짜", "팀1", "팀2", "팀1 득점", "팀1 실점"			}
		));
		scrollPane.setViewportView(table);
		
		lblNewLabel = new JLabel("경기 기록");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 35, 438, 22);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"전체 경기 조회", "우리팀 경기 조회"}));
		comboBox.setBounds(6, 64, 170, 27);
		contentPane.add(comboBox);
		
		loadAllGames();
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() == 1) { // "우리 팀 경기 조회" 선택 시
					String[] columnNames = { "경기 ID", "경기 날짜", "상대 팀", "득점", "실점" };
		            DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 빈 모델
		            table.setModel(model); // JTable에 모델 적용
					loadTeamGames();
				}
				if (comboBox.getSelectedIndex() == 0) {
					String[] columnNames = { "경기 ID", "경기 날짜", "팀1", "팀2", "팀1 득점", "팀1 실점" };
		            DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 빈 모델
		            table.setModel(model); // JTable에 모델 적용
					loadAllGames();
				}
			}
		});
	}
}
