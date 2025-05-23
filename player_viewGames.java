package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class player_viewGames extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JComboBox<String> comboBox;
    private int idTeam, idPlayer;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                player_viewGames frame = new player_viewGames(1, 1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public player_viewGames(int idTeam, int idPlayer) {
        this.idTeam = idTeam;
        this.idPlayer = idPlayer;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewLabel = new JLabel("경기 기록");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(6, 35, 588, 22);
        contentPane.add(lblNewLabel);

        JButton btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(e -> {
            new player(idTeam, idPlayer).setVisible(true);
            dispose();
        });
        btnNewButton.setBounds(6, 6, 117, 29);
        contentPane.add(btnNewButton);

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"전체 경기 조회", "우리 팀 경기 조회"}));
        comboBox.setBounds(6, 68, 189, 27);
        contentPane.add(comboBox);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 107, 588, 190);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        // Load default view
        loadAllGames();

        comboBox.addActionListener(e -> {
            if (comboBox.getSelectedIndex() == 1) {
                loadTeamGames();
            } else {
                loadAllGames();
            }
        });
    }

    private void loadAllGames() {
        String sql = "SELECT gr.idGame, gr.dateGame, " +
                "t1.nation AS team1Name, t2.nation AS team2Name, " +
                "gs1.goalOurTeam AS team1Goal, gs2.goalOurTeam AS team2Goal, " +
                "gs2.goalOurTeam AS team1Against, gs1.goalOurTeam AS team2Against " +
                "FROM DB2025_GameRec gr " +
                "LEFT JOIN DB2025_GameStat gs1 ON gr.idGame = gs1.idGame AND gr.idTeam1 = gs1.idOurTeam " +
                "LEFT JOIN DB2025_GameStat gs2 ON gr.idGame = gs2.idGame AND gr.idTeam2 = gs2.idOurTeam " +
                "JOIN DB2025_Team t1 ON gr.idTeam1 = t1.idTeam " +
                "JOIN DB2025_Team t2 ON gr.idTeam2 = t2.idTeam " +
                "ORDER BY gr.idGame ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            DefaultTableModel model = new DefaultTableModel(
                new String[]{"경기 ID", "경기 날짜", "팀1", "팀2", "팀1 득점", "팀1 실점", "팀2 득점", "팀2 실점"}, 0
            );
            table.setModel(model);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idGame"),
                    rs.getDate("dateGame").toString(),
                    rs.getString("team1Name"),
                    rs.getString("team2Name"),
                    rs.getInt("team1Goal"),
                    rs.getInt("team1Against"),
                    rs.getInt("team2Goal"),
                    rs.getInt("team2Against")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTeamGames() {
        String sql = "SELECT gr.idGame, gr.dateGame, " +
                "t2.nation AS opponentTeamName, " +
                "gs.goalOurTeam AS ourScore, " +
                "(SELECT goalOurTeam FROM DB2025_GameStat WHERE idGame = gr.idGame AND idOurTeam != gs.idOurTeam) AS opponentScore, " +
                "gs.allShots, gs.accPass " +
                "FROM DB2025_GameRec gr " +
                "JOIN DB2025_GameStat gs ON gr.idGame = gs.idGame AND gs.idOurTeam = ? " +
                "JOIN DB2025_Team t2 ON (gr.idTeam1 = gs.idOurTeam AND gr.idTeam2 = t2.idTeam) " +
                "                      OR (gr.idTeam2 = gs.idOurTeam AND gr.idTeam1 = t2.idTeam) " +
                "ORDER BY gr.idGame ASC";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idTeam);
            ResultSet rs = pstmt.executeQuery();

            DefaultTableModel model = new DefaultTableModel(
                new String[]{"경기 ID", "경기 날짜", "상대 팀", "우리팀 득점", "우리팀 득점(중복)", "우리팀 실점", "슛", "패스"}, 0
            );
            table.setModel(model);

            while (rs.next()) {
                int ourScore = rs.getInt("ourScore");
                int opponentScore = rs.getInt("opponentScore");

                model.addRow(new Object[]{
                    rs.getInt("idGame"),
                    rs.getDate("dateGame").toString(),
                    rs.getString("opponentTeamName"),
                    ourScore,
                    ourScore, // 중복 득점 (원하는 방식대로 수정 가능)
                    opponentScore,
                    rs.getInt("allShots"),
                    rs.getInt("accPass")
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
