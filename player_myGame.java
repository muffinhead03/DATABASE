package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class player_myGame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int iDplayer = 0;
					player_myGame frame = new player_myGame();
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
	private void loadGameData() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // 기존 데이터 초기화

	    String sql = "SELECT G.idGame, G.dateGame, T.nation AS opponentTeamName, G.goalFor, G.goalAgainst " +
	                 "FROM DB2025_Squad S " +
	                 "JOIN DB2025_GameRec G ON S.idGame = G.idGame " +
	                 "JOIN DB2025_Team T ON G.idAgainstTeam = T.idTeam " +
	                 "WHERE S.idPlayer = ?";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setInt(1, DKicker_player_choose.currentidPlayer); // 바인딩

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                int gameId = rs.getInt("idGame");
	                Date date = rs.getDate("dateGame");
	                String opponent = rs.getString("opponentTeamName");
	                int goalFor = rs.getInt("goalFor");
	                int goalAgainst = rs.getInt("goalAgainst");

	                model.addRow(new Object[]{gameId, date.toString(), opponent, goalFor, goalAgainst});
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public player_myGame() {
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("나의 출전 경기");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(5, 37, 440, 31);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton_4 = new JButton("Back");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player().setVisible(true); dispose();
			}
		});
		btnNewButton_4.setBounds(5, 6, 117, 29);
		contentPane.add(btnNewButton_4);
		
		JPanel panel = new JPanel();
		panel.setBounds(5, 80, 439, 186);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uACBD\uAE30ID", "\uACBD\uAE30 \uB0A0\uC9DC", "\uC0C1\uB300 \uD300", "\uB4DD\uC810", "\uC2E4\uC810"
			}
		));
		scrollPane.setViewportView(table);
		loadGameData();
		
		
	}
}
