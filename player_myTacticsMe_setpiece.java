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
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class player_myTacticsMe_setpiece extends JFrame {

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
					player_myTacticsMe_setpiece frame = new player_myTacticsMe_setpiece();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	//데이터베이스속 데이터 로드
		private void loadsetpieceData() {
		    DefaultTableModel model = (DefaultTableModel) table.getModel();
		    model.setRowCount(0); // 기존 테이블 초기화

		    String sql = 
		    	    "SELECT DISTINCT " +
		    	    "    S.idTactic AS setpieceTacticId, " +
		    	    "    S.tacticName AS tacticName, " +
		    	    "    S.tacticFormation AS tacticFormation " +
		    	    "FROM " +
		    	    "    DB2025_Squad Q " +
		    	    "JOIN " +
		    	    "    DB2025_Player P ON Q.idPlayer = P.idPlayer " +
		    	    "JOIN " +
		    	    "    DB2025_GameRec G ON Q.idGame = G.idGame " +
		    	    "JOIN " +
		    	    "    DB2025_Tactics S ON G.idSetpiece = S.idTactic " +
		    	    "WHERE " +
		    	    "    P.idPlayer = ? " +
		    	    "    AND S.tacticType = 'Setpiece' " +
		    	    "    AND S.idTeam = P.idTeam";


		    try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setInt(1, DKicker_player_choose.playerid); // 바인딩

		        try (ResultSet rs = pstmt.executeQuery()) {
		            while (rs.next()) {
		                int tacticId = rs.getInt("setpieceTacticId");
		                String tacticName = rs.getString("tacticName");
		                String formation = rs.getString("tacticFormation");

		                model.addRow(new Object[]{tacticId, tacticName, formation});
		            }
		        }

		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

	/**
	 * Create the frame.
	 */
	public player_myTacticsMe_setpiece() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myTacticsMe().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("세트피스 전술");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 73, 438, 193);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC138\uD2B8\uD53C\uC2A4 \uC804\uC220 ID", "\uC804\uC220 \uC774\uB984", "\uD3EC\uBA54\uC774\uC158"
			}
		));
		scrollPane.setViewportView(table);
		loadsetpieceData();
	}

}