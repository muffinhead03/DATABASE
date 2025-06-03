package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

//선수 스쿼드 조회 및 관리하는 화면입니다.
public class staff_playerManage_squad extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                staff_playerManage_squad frame = new staff_playerManage_squad(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //팀 ID를 받아 UI를 초기화하는 생성자입니다.
    public staff_playerManage_squad(int idTeam) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //경기 ID를 선택할 수 있는 콤보박스입니다. 
        JLabel lblGameId = new JLabel("\uACBD\uAE30 ID");
        lblGameId.setHorizontalAlignment(SwingConstants.CENTER);
        lblGameId.setBounds(320, 6, 60, 24);
        contentPane.add(lblGameId);

        JComboBox<Integer> gameIdComboBox = new JComboBox<>();
        gameIdComboBox.setBounds(380, 6, 60, 24);
        gameIdComboBox.addActionListener(e -> {
            Integer selectedGameId = (Integer) gameIdComboBox.getSelectedItem();
            if (selectedGameId != null) {
                loadSquadData(selectedGameId);  // 자동 필터링된 테이블 로드
            }
        });
        contentPane.add(gameIdComboBox);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(6, 6, 117, 29);
        btnBack.addActionListener(e -> {
            new staff_playerManage(idTeam).setVisible(true);
            dispose();
        });
        contentPane.add(btnBack);

        //타이틀 라벨입니다. 
        JLabel lblTitle = new JLabel("선수 스쿼드 조회 및 관리");
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(6, 32, 438, 29);
        contentPane.add(lblTitle);

        //스쿼드 정보 테이블입니다.
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 73, 438, 104);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "\uC120\uC218 ID", "\uC774\uB984", "\uD3EC\uC9C0\uC158", "\uCD9C\uC804 \uC2DC\uAC04" }));
        scrollPane.setViewportView(table);

        //선수 정보 입력 패널(조회용 필드)입니다.
        JPanel panel = new JPanel();
        panel.setBounds(6, 178, 438, 52);
        contentPane.add(panel);
        panel.setLayout(new GridLayout(0, 4, 0, 0));

        panel.add(new JLabel("\uC120\uC218 ID", SwingConstants.CENTER));
        JComboBox<Integer> comboBox = new JComboBox<>();
        panel.add(comboBox);

        panel.add(new JLabel("\uC774\uB984", SwingConstants.CENTER));
        textField_1 = new JTextField();
        panel.add(textField_1);

        panel.add(new JLabel("\uD3EC\uC9C0\uC158", SwingConstants.CENTER));
        textField_2 = new JTextField();
        panel.add(textField_2);

        panel.add(new JLabel("\uCD9C\uC804 \uC2DC\uAC04", SwingConstants.CENTER));
        textField_3 = new JTextField();
        panel.add(textField_3);

        //하단 버튼 패널입니다. 
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(6, 238, 438, 28);
        contentPane.add(panel_1);
        panel_1.setLayout(new GridLayout(1, 0, 0, 0));

        //조회버튼입니다.
        JButton btnQuery = new JButton("\uC870\uD68C");
        btnQuery.addActionListener(e -> {
            int selectedGameId = (int) gameIdComboBox.getSelectedItem();
            int selectedPlayerId = (int) comboBox.getSelectedItem();
            loadSquadData(selectedGameId, selectedPlayerId);
        });
        panel_1.add(btnQuery);

        //수정 버튼입니다.
        JButton btnUpdate = new JButton("\uC218\uC815");
        btnUpdate.addActionListener(e -> {
            try {
                int idGame = (int) gameIdComboBox.getSelectedItem();
                int idPlayer = (int) comboBox.getSelectedItem();
                int playTime = Integer.parseInt(textField_3.getText());

                String query = "UPDATE DB2025_Squad SET playTime = ? WHERE idGame = ? AND idPlayer = ?";
                try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, playTime);
                    pstmt.setInt(2, idGame);
                    pstmt.setInt(3, idPlayer);
                    int result = pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, result > 0 ? "\uC218\uC815 \uC644\uB8CC" : "\uC218\uC815 \uC2E4\uD328");
                    loadSquadData();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        panel_1.add(btnUpdate);

        //삭제버튼입니다. 
        JButton btnDelete = new JButton("\uC0AD\uC81C");
        btnDelete.addActionListener(e -> {
            try {
                int idGame = (int) gameIdComboBox.getSelectedItem();
                int idPlayer = (int) comboBox.getSelectedItem();

                String query = "DELETE FROM DB2025_Squad WHERE idGame = ? AND idPlayer = ?";
                try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
                    pstmt.setInt(1, idGame);
                    pstmt.setInt(2, idPlayer);
                    int result = pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, result > 0 ? "\uC0AD\uC81C \uC644\uB8CC" : "\uC0AD\uC81C \uC2E4\uD328");
                    loadSquadData();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        panel_1.add(btnDelete);

        //추가 버튼입니다. 
        JButton btnAdd = new JButton("\uC2DC\uC791\uC0C1 \uCD94\uAC00 \uC885\uB8CC");
        btnAdd.addActionListener(e -> {
            Integer idGame = (Integer) gameIdComboBox.getSelectedItem();
            Integer idPlayer = (Integer) comboBox.getSelectedItem();

            if (idGame == null || idPlayer == null) {
                JOptionPane.showMessageDialog(null, "경기 ID와 선수 ID를 모두 선택하세요.");
                return;
            }

            try (Connection conn = DBUtil.getConnection()) {
                // 1. 이미 등록된 선수인지 확인합니니다.
                String checkSql = "SELECT * FROM DB2025_Squad WHERE idGame = ? AND idPlayer = ?";
                PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                checkStmt.setInt(1, idGame);
                checkStmt.setInt(2, idPlayer);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "이미 이 경기에 출전한 선수입니다.");
                    rs.close();
                    checkStmt.close();
                    return;
                }
                rs.close();
                checkStmt.close();

                // 2. 출전 시간 입력 받습니다.
                String inputTime = JOptionPane.showInputDialog("출전 시간을 입력하세요 (숫자):");
                if (inputTime == null || inputTime.trim().isEmpty()) return;
                int playTime = Integer.parseInt(inputTime.trim());

                // 3. INSERT 실행합니다.
                String insertSql = "INSERT INTO DB2025_Squad (idGame, idPlayer, playTime) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setInt(1, idGame);
                insertStmt.setInt(2, idPlayer);
                insertStmt.setInt(3, playTime);

                int result = insertStmt.executeUpdate();
                insertStmt.close();

                if (result > 0) {
                    JOptionPane.showMessageDialog(null, "신규 추가 완료!");
                    loadSquadData();  // 전체 갱신
                } else {
                    JOptionPane.showMessageDialog(null, "추가 실패");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "출전 시간은 숫자로 입력해야 합니다.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
            }
        });
panel_1.add(btnAdd);

        //초기 데이터 로드합니다. 
        loadSquadData();
        loadPlayerIdsToComboBox(comboBox, idTeam);
        loadGameIdsToComboBox(gameIdComboBox);
    }

    //콤보박스에 모든 경기 ID를 로드합니다.
    private void loadGameIdsToComboBox(JComboBox<Integer> comboBox) {
        String query = "SELECT DISTINCT idGame FROM DB2025_Squad ORDER BY idGame";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                comboBox.addItem(rs.getInt("idGame"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //콤보박스에 현재 팀의 선수 ID를 로드합니다. 
    private void loadPlayerIdsToComboBox(JComboBox<Integer> comboBox, int idTeam) {
        comboBox.removeAllItems();  // 기존 항목 초기화

        String query = "SELECT idPlayer FROM DB2025_Player WHERE idTeam = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idTeam);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                comboBox.addItem(rs.getInt("idPlayer"));  // 콤보박스에 선수 ID 추가
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //전체 스쿼드 목록을 로딩합니다. 
    private void loadSquadData() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        String query = "SELECT s.idPlayer, p.playerName, p.position, s.playTime FROM DB2025_Squad s JOIN DB2025_Player p ON s.idPlayer = p.idPlayer";
        try (Connection conn = DBUtil.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("idPlayer"),
                    rs.getString("playerName"),
                    rs.getString("position"),
                    rs.getInt("playTime")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //특정 경기의 스쿼드 목록을 로딩합니다. 
    private void loadSquadData(int idGame) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        String query = "SELECT s.idPlayer, p.playerName, p.position, s.playTime " +
                       "FROM DB2025_Squad s " +
                       "JOIN DB2025_Player p ON s.idPlayer = p.idPlayer " +
                       "WHERE s.idGame = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idGame);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idPlayer"),
                    rs.getString("playerName"),
                    rs.getString("position"),
                    rs.getInt("playTime")
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //특정 경기의 스쿼드목록 오버로딩, gameID랑 playerId를 받고 수행한다. 
    private void loadSquadData(int gameId, int playerId) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        String query = "SELECT s.idPlayer, p.playerName, p.position, s.playTime FROM DB2025_Squad s JOIN DB2025_Player p ON s.idPlayer = p.idPlayer WHERE s.idGame = ? AND s.idPlayer = ?";
        try (Connection conn = DBUtil.getConnection(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, gameId);
            pstmt.setInt(2, playerId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("idPlayer"),
                    rs.getString("playerName"),
                    rs.getString("position"),
                    rs.getInt("playTime")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
