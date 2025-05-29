package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.*;
import java.sql.*;

//특정 팀이 사용한 전술의 경기 목록을 보여주는 프레임입니다. 필드 전술과 세트피스 전술을 구분하여 전술별로 사용된 경기와 성과를 조회합니다.
public class viewTactics_statistics_List extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JComboBox<String> comboBox;  // 전술 필터링용 콤보박스
    private int idTeam; // 로그인한 사용자(스태프)의 팀 ID

// 프로그램 실행 테스트용 main 함수입니다.
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                viewTactics_statistics_List frame = new viewTactics_statistics_List(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    // idTeam을 매개변수로 받는 생성자입니다. 
    public viewTactics_statistics_List(int idTeam) {
        this.idTeam = idTeam;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 350);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(6, 6, 117, 29);
        btnBack.addActionListener(e -> {
            new viewTactics_statistics(idTeam).setVisible(true);
            dispose();
        });
        contentPane.add(btnBack);

        //제목라벨입니다.
        JLabel lblTitle = new JLabel("전술 사용 경기 목록");
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(6, 33, 988, 29);
        contentPane.add(lblTitle);

        //테이블 스크롤 영역입니다.
        comboBox = new JComboBox<>();
        comboBox.setBounds(130, 6, 200, 27);
        contentPane.add(comboBox);

        //전술 사용 경기 테이블입니다. 
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 74, 988, 242);
        contentPane.add(scrollPane);

        table = new JTable();
        scrollPane.setViewportView(table);

        // 콤보박스 목록 및 초기 테이블 데이터 로드
        loadTacticNames();
        loadTacticUsagesByTeam(idTeam);

        // 콤보박스 선택 시 필터링 동작 정의
        comboBox.addActionListener(e -> {
            String selected = (String) comboBox.getSelectedItem();
            if (selected == null || selected.equals("전체 전술")) {
                loadTacticUsagesByTeam(idTeam);
            } else {
                loadTacticUsagesByName(selected);
            }
        });
    }

    //DB에서 사용 가능한 전술 이름들을 콤보박스에 로드합니다.
    private void loadTacticNames() {
        comboBox.removeAllItems();
        comboBox.addItem("전체 전술");

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT tacticName FROM DB2025_Tactics WHERE idTeam = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idTeam);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                comboBox.addItem(rs.getString("tacticName"));
            }

            rs.close();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //특정 팀의 전체 전술 사용 내역을 테이블에 로드합니다.
    private void loadTacticUsagesByTeam(int idTeam) {
        loadTacticUsages("WHERE Tac.idTeam = ? AND Tac.idTeam = ?", idTeam, null);
    }

   //오버라이딩으로, 특정 전술 이름에 해당하는 사용 내역만 로드합니다.
    private void loadTacticUsagesByName(String tacticName) {
        loadTacticUsages("WHERE Tac.idTeam = ? AND Tac.tacticName = ?", idTeam, tacticName);
    }

    //전술 사용 내역을 쿼리 조건에 따라 불러와 테이블에 출력합니다.
    private void loadTacticUsages(String whereClause, int idTeam, String tacticName) {
        String[] columnNames = {
            "전술 ID", "전술 이름", "포메이션", "경기 ID", "경기 날짜",
            "상대 팀", "득점", "실점", "전술 속성"
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        try (Connection conn = DBUtil.getConnection()) {
            // 필드 전술과 세트피스 전술을 각각 조회하여 합칩니다 (UNION ALL)
            String sql =
                "SELECT Tac.idTactic, Tac.tacticName, Tac.tacticFormation, " +
                "       G.idGame, G.dateGame, T.nation AS opponentTeam, " +
                "       G.goalFor, G.goalAgainst, '필드 전술' AS usageType " +
                "FROM DB2025_view_GameSummary G " +
                "JOIN DB2025_Tactics Tac ON G.idField = Tac.idTactic " +
                "JOIN DB2025_Team T ON G.idAgainstTeam = T.idTeam " +
                whereClause +
                " UNION ALL " +
                "SELECT Tac.idTactic, Tac.tacticName, Tac.tacticFormation, " +
                "       G.idGame, G.dateGame, T.nation AS opponentTeam, " +
                "       G.goalFor, G.goalAgainst, '세트피스 전술' AS usageType " +
                "FROM DB2025_view_GameSummary G " +
                "JOIN DB2025_Tactics Tac ON G.idSetpiece = Tac.idTactic " +
                "JOIN DB2025_Team T ON G.idAgainstTeam = T.idTeam " +
                whereClause +
                " ORDER BY idTactic, idGame";

            // 전술 이름 유무에 따라 파라미터 설정 분기합니다.
            PreparedStatement pstmt = conn.prepareStatement(sql);
            if (tacticName != null) {
            	pstmt.setInt(1, idTeam);
                pstmt.setString(2, tacticName);
                pstmt.setInt(3, idTeam);
                pstmt.setString(4, tacticName);
            } else {
            	   pstmt.setInt(1, idTeam);
                   pstmt.setInt(2, idTeam);
                   pstmt.setInt(3, idTeam);
                   pstmt.setInt(4, idTeam);
            }

            // 쿼리 실행 및 결과 처리합니다.
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
