package DB2025Team09;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class viewTactics_statistics_List extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JTextField txtTacticId;
    private JRadioButton rbtnField, rbtnSetPiece;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
            	viewTactics_statistics_List frame = new viewTactics_statistics_List();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public viewTactics_statistics_List() {
        setTitle("전술 검색");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(6, 6, 117, 29);
        btnBack.addActionListener(e -> {
            new viewTactics_statistics(DKicker.currentTeamId).setVisible(true);
            dispose();
        });
        contentPane.add(btnBack);

        JLabel lblTitle = new JLabel("전술 ID로 전술 검색");
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblTitle.setBounds(6, 40, 480, 25);
        contentPane.add(lblTitle);

        rbtnField = new JRadioButton("필드 전술");
        rbtnField.setBounds(50, 75, 100, 23);
        contentPane.add(rbtnField);

        rbtnSetPiece = new JRadioButton("세트피스 전술");
        rbtnSetPiece.setBounds(160, 75, 120, 23);
        contentPane.add(rbtnSetPiece);

        ButtonGroup group = new ButtonGroup();
        group.add(rbtnField);
        group.add(rbtnSetPiece);

        JLabel lblId = new JLabel("전술 ID:");
        lblId.setBounds(50, 110, 60, 16);
        contentPane.add(lblId);

        txtTacticId = new JTextField();
        txtTacticId.setBounds(110, 105, 150, 26);
        contentPane.add(txtTacticId);
        txtTacticId.setColumns(10);

        JButton btnSearch = new JButton("검색");
        btnSearch.setBounds(280, 105, 80, 26);
        contentPane.add(btnSearch);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 150, 480, 210);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {"우리팀", "상대팀", "전술 이름", "포메이션"}
        ));
        scrollPane.setViewportView(table);

        // 검색 버튼 이벤트
        btnSearch.addActionListener(e -> searchTactic());
    }

    private void searchTactic() {
        String tacticIdText = txtTacticId.getText().trim();
        if (tacticIdText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "전술 ID를 입력해주세요.");
            return;
        }

        try {
            int tacticId = Integer.parseInt(tacticIdText);
            boolean isField = rbtnField.isSelected();
            boolean isSetPiece = rbtnSetPiece.isSelected();

            if (!isField && !isSetPiece) {
                JOptionPane.showMessageDialog(this, "전술 종류를 선택해주세요.");
                return;
            }

            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // 테이블 초기화

            String columnName, formationColumn;
            String query = "SELECT gr.idGame, t1.nation AS ourTeam, t2.nation AS opponentTeam, v.%s AS tacticName, v.%s AS tacticFormation " +
                           "FROM DB2025_Tactics_in_Game v " +
                           "JOIN DB2025_GameRec gr ON v.idGame = gr.idGame " +
                           "JOIN DB2025_Team t1 ON gr.idOurTeam = t1.idTeam " +
                           "JOIN DB2025_Team t2 ON gr.idAgainstTeam = t2.idTeam " +
                           "WHERE v.idTactic = ? AND v.tacticType = ? " +
                           "ORDER BY v.idGame ASC";

            if (isField) {
                columnName = "fieldName";
                formationColumn = "fieldFormation";
            } else {
                columnName = "setpieceName";
                formationColumn = "setpieceFormation";
            }

            String formattedQuery = String.format(query, columnName, formationColumn);

            try (Connection conn = DBUtil.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(formattedQuery)) {

                pstmt.setInt(1, tacticId);
                pstmt.setString(2, isField ? "Field" : "Setpiece");

                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getString("ourTeam"),
                        rs.getString("opponentTeam"),
                        rs.getString("tacticName"),
                        rs.getString("tacticFormation")
                    });
                }

            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "숫자 형식의 전술 ID를 입력해주세요.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB 오류가 발생했습니다.");
        }
    }
}
