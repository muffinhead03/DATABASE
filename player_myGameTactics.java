package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.sql.*;
import java.awt.Font;

public class player_myGameTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private JLabel lblGameId, lblDate, lblOpponent, lblGoalFor, lblGoalAgainst;
	private JLabel lblFieldName, lblFieldFormation, lblSetName, lblSetFormation;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				player_myGameTactics frame = new player_myGameTactics();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public player_myGameTactics() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnBack = new JButton("Back");
		btnBack.addActionListener(e -> {
			new player_myGameOne().setVisible(true);
			dispose();
		});
		btnBack.setBounds(6, 6, 117, 29);
		contentPane.add(btnBack);

		JLabel lblTitle = new JLabel("경기에 사용된 전술 정보");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblTitle.setBounds(6, 34, 438, 22);
		contentPane.add(lblTitle);

		// Labels
		JLabel[] staticLabels = {
			new JLabel("경기ID:"), new JLabel("경기 날짜:"), new JLabel("상대 팀:"),
			new JLabel("득점:"), new JLabel("실점:"), new JLabel("필드 전술 이름:"),
			new JLabel("필드 전술 포메이션:"), new JLabel("세트피스 전술 이름:"), new JLabel("세트피스 전술 포메이션:")
		};

		int[][] positions = {
			{6, 68}, {6, 96}, {6, 124}, {6, 152}, {6, 180},
			{250, 68}, {250, 96}, {250, 124}, {250, 152}
		};

		for (int i = 0; i < staticLabels.length; i++) {
			staticLabels[i].setBounds(positions[i][0], positions[i][1], 150, 16);
			contentPane.add(staticLabels[i]);
		}

		lblGameId = new JLabel(); lblGameId.setBounds(120, 68, 100, 16); contentPane.add(lblGameId);
		lblDate = new JLabel(); lblDate.setBounds(120, 96, 100, 16); contentPane.add(lblDate);
		lblOpponent = new JLabel(); lblOpponent.setBounds(120, 124, 100, 16); contentPane.add(lblOpponent);
		lblGoalFor = new JLabel(); lblGoalFor.setBounds(120, 152, 100, 16); contentPane.add(lblGoalFor);
		lblGoalAgainst = new JLabel(); lblGoalAgainst.setBounds(120, 180, 100, 16); contentPane.add(lblGoalAgainst);

		lblFieldName = new JLabel(); lblFieldName.setBounds(400, 68, 100, 16); contentPane.add(lblFieldName);
		lblFieldFormation = new JLabel(); lblFieldFormation.setBounds(400, 96, 100, 16); contentPane.add(lblFieldFormation);
		lblSetName = new JLabel(); lblSetName.setBounds(400, 124, 100, 16); contentPane.add(lblSetName);
		lblSetFormation = new JLabel(); lblSetFormation.setBounds(400, 152, 100, 16); contentPane.add(lblSetFormation);

		loadTacticInfo();
	}

	private void loadTacticInfo() {
		String sql = """
			SELECT GIA.idGame, GIA.dateGame, T.nation AS opponentTeamName,
			       GIA.goalFor, GIA.goalAgainst,
			       TIG.fieldName, TIG.fieldFormation,
			       TIG.setpieceName, TIG.setpieceFormation
			FROM DB2025_Game_Players_List GPL
			JOIN DB2025_Tactics_in_Game TIG ON GPL.idGame = TIG.idGame
			JOIN DB2025_Game_Info_All GIA ON GIA.idGame = TIG.idGame
			JOIN DB2025_Team T ON GIA.idAgainstTeam = T.idTeam
			WHERE GPL.idPlayer = ?
		""";

		try (Connection conn = DBUtil.getConnection();
			 PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, DKicker_player_choose.playerid);

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					lblGameId.setText(String.valueOf(rs.getInt("idGame")));
					lblDate.setText(rs.getDate("dateGame").toString());
					lblOpponent.setText(rs.getString("opponentTeamName"));
					lblGoalFor.setText(String.valueOf(rs.getInt("goalFor")));
					lblGoalAgainst.setText(String.valueOf(rs.getInt("goalAgainst")));

					lblFieldName.setText(rs.getString("fieldName"));
					lblFieldFormation.setText(rs.getString("fieldFormation"));
					lblSetName.setText(rs.getString("setpieceName"));
					lblSetFormation.setText(rs.getString("setpieceFormation"));
				} else {
					JOptionPane.showMessageDialog(this, "데이터가 없습니다.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "오류 발생: " + e.getMessage());
		}
	}
}
