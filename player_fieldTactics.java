package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
public class player_fieldTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam, idPlayer;

	//프로그램을 실행하면 player_fielddTactics 창이 뜬다
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player_fieldTactics frame = new player_fieldTactics(1,1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	private void loadTacticsData() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);

	    String query = "SELECT v.idGame, v.idAgainstTeam, t.fieldName\n"
	                 + "FROM view_GameSummary v\n"
	                 + "JOIN DB2025_Tactics t ON v.idGame = t.idGame\n"
	                 + "WHERE v.idOurTeam = ? AND t.idTeam = v.idOurTeam";

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query)) {

	        pstmt.setInt(1, idTeam);  // 바인딩

	        try (ResultSet rs = pstmt.executeQuery()) {
	            while (rs.next()) {
	                Object[] row = {
	                    rs.getInt("idGame"),
	                    rs.getInt("idAgainstTeam"),
	                    rs.getString("fieldName")
	                };
	                model.addRow(row);
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}



	public player_fieldTactics(int idTeam, int idPlayer) {
		this.idTeam = idTeam;
		this.idPlayer = idPlayer;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//"Back"버튼
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_viewTactics(idTeam, idPlayer).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		//제목 라벨("필드 전술")
		JLabel lblNewLabel = new JLabel("필드 전술");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);
		
		//테이블 + 스크롤
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 66, 438, 200);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			//컬럼명 : 전술 ID(전술 고유 번호), 전술 이름, 포메이션
			new String[] {
				"우리팀","상대팀","필드 전술 이름"
			}
		));
		scrollPane.setViewportView(table);
		
		loadTacticsData();
	}

}