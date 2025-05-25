package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.*;
import java.sql.*;

public class viewPlayers extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private int idTeam;
    JComboBox comboBox_1 = new JComboBox();
    JComboBox comboBox_1_1 = new JComboBox();
    JComboBox comboBox = new JComboBox();
    private JTextField txtName, txtPosition, txtBirthday, txtNumber, txtAction, txtPlayTime;
    private JButton btnAvailability;
    private boolean isAvailable = true;
    private int selectedPlayerId = -1;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                viewPlayers frame = new viewPlayers(DKicker.currentTeamId);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public viewPlayers(int idTeam) {
        this.idTeam = DKicker.currentTeamId;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTitle = new JLabel("선수 정보");
        lblTitle.setFont(new Font("Lucida Grande", Font.BOLD, 17));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle.setBounds(6, 10, 588, 25);
        contentPane.add(lblTitle);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(6, 6, 117, 29);
        btnBack.addActionListener(e -> {
            new staff(DKicker.currentTeamId).setVisible(true);
            dispose();
        });
        contentPane.add(btnBack);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 45, 588, 150);
        contentPane.add(scrollPane);

        table = new JTable();
        table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {"선수 ID", "이름", "포지션", "출전 시간"}));
        scrollPane.setViewportView(table);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedPlayerId = (int) table.getValueAt(row, 0);
                    txtName.setText(table.getValueAt(row, 1).toString());
                    txtPosition.setText(table.getValueAt(row, 2).toString());
                    txtBirthday.setText(table.getValueAt(row, 3).toString());
                    txtAction.setText("(포지션)");
                    txtPlayTime.setText("출전시간");

                    // DB에서 나머지 정보 조회
                    try (Connection conn = DBUtil.getConnection()) {
                        String sql = "SELECT * FROM DB2025_Player WHERE idPlayer = ?";
                        PreparedStatement pstmt = conn.prepareStatement(sql);
                        pstmt.setInt(1, selectedPlayerId);
                        ResultSet rs = pstmt.executeQuery();
                        if (rs.next()) {
                            txtNumber.setText(String.valueOf(selectedPlayerId));
                            txtAction.setText(rs.getString("action"));
                            txtPlayTime.setText(String.valueOf(rs.getInt("performance")));
                            txtPosition.setText(rs.getString("position"));
                            isAvailable = rs.getBoolean("availability"); // 출전 가능 여부
                            btnAvailability.setText(isAvailable ? "가능" : "불가능");
                            txtNumber.setText("0"); // number 컬럼이 없으므로 기본값 또는 제거

                            txtAction.setText(rs.getString("playerAction")); // 기존: rs.getString("action")

                            txtPlayTime.setText(String.valueOf(rs.getInt("performance")));

                            isAvailable = rs.getInt("ableToPlay") == 1; // 기존: rs.getBoolean("availability")

                            btnAvailability.setText(isAvailable ? "가능" : "불가능");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        JLabel lblNewLabel_1 = new JLabel("포지션");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(6, 200, 39, 16);
        contentPane.add(lblNewLabel_1);

        comboBox.setModel(new DefaultComboBoxModel(new String[] {"전체", "AM", "DF", "DM", "FW", "GK"}));
        comboBox.setBounds(46, 196, 77, 27);
        contentPane.add(comboBox);

        JLabel lblNewLabel_2 = new JLabel("정렬");
        lblNewLabel_2.setBounds(288, 200, 28, 16);
        contentPane.add(lblNewLabel_2);

        comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"가나다순", "최신 등록순", "최다 출전 시간순"}));
        comboBox_1.setBounds(313, 196, 131, 27);
        contentPane.add(comboBox_1);

        JLabel lblNewLabel_3 = new JLabel("소속 팀");
        lblNewLabel_3.setBounds(122, 200, 39, 16);
        contentPane.add(lblNewLabel_3);

        comboBox_1_1.setModel(new DefaultComboBoxModel(new String[] {"전체", "팀1", "팀2", "팀3"}));
        comboBox_1_1.setBounds(159, 196, 117, 27);
        contentPane.add(comboBox_1_1);

        JPanel panel = new JPanel();
        panel.setBounds(6, 330, 438, 29);
        contentPane.add(panel);
        panel.setLayout(new GridLayout(0, 2, 0, 0));

        JButton btnNewButton_1 = new JButton("수정");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedPlayerId == -1) {
                    JOptionPane.showMessageDialog(null, "선수를 먼저 선택하세요.");
                    return;
                }

                try (Connection conn = DBUtil.getConnection()) {
                    String sql = "UPDATE DB2025_Player\n"
                    		+ "SET playerName=?, position=?, birthday=?, playerAction=?, performance=?, ableToPlay=?\n"
                    		+ "WHERE idPlayer=?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setString(1, txtName.getText());
                    pstmt.setString(2, txtPosition.getText());
                    pstmt.setDate(3, Date.valueOf(txtBirthday.getText()));
                    pstmt.setString(4, txtAction.getText());
                    pstmt.setInt(5, Integer.parseInt(txtPlayTime.getText()));
                    pstmt.setInt(6, isAvailable ? 1 : 0); // TINYINT로 세팅
                    pstmt.setInt(7, selectedPlayerId);
                    int updated = pstmt.executeUpdate();
                    if (updated > 0) {
                        JOptionPane.showMessageDialog(null, "선수 정보가 수정되었습니다.");
                        reloadFilter();
                    } else {
                        JOptionPane.showMessageDialog(null, "수정 실패: 존재하지 않는 선수입니다.");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "오류 발생: 수정 실패");
                }
            }
        });
        panel.add(btnNewButton_1);

        JButton btnNewButton_2 = new JButton("삭제");
        btnNewButton_2.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "삭제할 선수를 선택하세요.");
                return;
            }
            int result = JOptionPane.showConfirmDialog(null, "정말 삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                try {
                    int playerId = (int) table.getValueAt(selectedRow, 0);
                    Connection conn = DBUtil.getConnection();
                    String sql = "DELETE FROM DB2025_Player WHERE idPlayer = ?";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, playerId);
                    int deleted = pstmt.executeUpdate();
                    if (deleted > 0) {
                        ((DefaultTableModel) table.getModel()).removeRow(selectedRow);
                        JOptionPane.showMessageDialog(null, "선수가 삭제되었습니다.");
                    } else {
                        JOptionPane.showMessageDialog(null, "삭제 실패: 존재하지 않는 선수입니다.");
                    }
                    pstmt.close();
                    conn.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "오류 발생: 삭제 실패");
                }
            }
        });
        panel.add(btnNewButton_2);

        txtName = new JTextField(); txtName.setBounds(6, 250, 90, 26); contentPane.add(txtName);
        txtNumber = new JTextField(); txtNumber.setBounds(100, 250, 50, 26); txtNumber.setEditable(false);contentPane.add(txtNumber);
        txtPosition = new JTextField(); txtPosition.setBounds(160, 250, 60, 26); contentPane.add(txtPosition);
        txtBirthday = new JTextField(); txtBirthday.setBounds(230, 250, 90, 26); contentPane.add(txtBirthday);
        txtAction = new JTextField(); txtAction.setBounds(330, 250, 100, 26); contentPane.add(txtAction);
        txtPlayTime = new JTextField(); txtPlayTime.setBounds(440, 250, 50, 26); contentPane.add(txtPlayTime);

        btnAvailability = new JButton("가능");
        btnAvailability.setBounds(500, 250, 80, 26);
        btnAvailability.addActionListener(e -> {
            isAvailable = !isAvailable;
            btnAvailability.setText(isAvailable ? "가능" : "불가능");
        });
        contentPane.add(btnAvailability);

        comboBox.addActionListener(e -> reloadFilter());
        comboBox_1_1.addActionListener(e -> reloadFilter());
        comboBox_1.addActionListener(e -> reloadFilter());

        loadPlayers("전체", "전체", "가나다순");
    }

    private void reloadFilter() {
        String position = (String) comboBox.getSelectedItem();
        String teamName = (String) comboBox_1_1.getSelectedItem();
        String sort = (String) comboBox_1.getSelectedItem();
        loadPlayers(position, teamName, sort);
    }

    private void loadPlayers(String position, String teamName, String sortOption) {
        try (Connection conn = DBUtil.getConnection()) {
            StringBuilder sql = new StringBuilder("SELECT idPlayer, playerName, position, birthday FROM DB2025_Player WHERE 1=1");
            if (!"전체".equals(position)) sql.append(" AND position = ?");
            if (!"전체".equals(teamName)) sql.append(" AND idTeam = ?");
            switch (sortOption) {
                case "가나다순" -> sql.append(" ORDER BY playerName ASC");
                case "최신 등록순" -> sql.append(" ORDER BY idPlayer DESC");
                case "최다 출전 시간순" -> sql.append(" ORDER BY performance DESC");
            }
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            int index = 1;
            if (!"전체".equals(position)) pstmt.setString(index++, position);
            if (!"전체".equals(teamName)) pstmt.setInt(index++, getTeamIdByName(teamName));

            ResultSet rs = pstmt.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[] {
                    rs.getInt("idPlayer"),
                    rs.getString("playerName"),
                    rs.getString("position"),
                    rs.getDate("birthday")
                });
            
            
            }
            
            
            if (table.getRowCount() > 0) {
                table.setRowSelectionInterval(0, 0);
                int row = 0;
                selectedPlayerId = (int) table.getValueAt(row, 0);
                txtName.setText(table.getValueAt(row, 1).toString());
                txtPosition.setText(table.getValueAt(row, 2).toString());
                txtBirthday.setText(table.getValueAt(row, 3).toString());

                try (Connection conn2 = DBUtil.getConnection()) {
                    String sql2 = "SELECT * FROM DB2025_Player WHERE idPlayer = ?";
                    PreparedStatement pstmt2 = conn2.prepareStatement(sql2);
                    pstmt2.setInt(1, selectedPlayerId);
                    ResultSet rs2 = pstmt2.executeQuery();
                    if (rs2.next()) {
                        txtNumber.setText(String.valueOf(rs2.getInt("number")));
                        txtAction.setText(rs2.getString("playeraction"));
                        txtPlayTime.setText(String.valueOf(rs2.getInt("performance")));
                        isAvailable = rs2.getBoolean("availability");
                        btnAvailability.setText(isAvailable ? "가능" : "불가능");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if (table.getRowCount() > 0) {
            table.setRowSelectionInterval(0, 0); // 첫 번째 행 선택
            int row = 0;
            selectedPlayerId = (int) table.getValueAt(row, 0);
            txtName.setText(table.getValueAt(row, 1).toString());
            txtPosition.setText(table.getValueAt(row, 2).toString());
            txtBirthday.setText(table.getValueAt(row, 3).toString());

            // 나머지 정보 불러오기
            try (Connection conn2 = DBUtil.getConnection()) {
                String sql2 = "SELECT * FROM DB2025_Player WHERE idPlayer = ?";
                PreparedStatement pstmt2 = conn2.prepareStatement(sql2);
                pstmt2.setInt(1, selectedPlayerId);
                ResultSet rs2 = pstmt2.executeQuery();
                if (rs2.next()) {
                    txtNumber.setText(String.valueOf(rs2.getInt("number")));
                    txtAction.setText(rs2.getString("playeraction"));
                    txtPlayTime.setText(String.valueOf(rs2.getInt("performance")));
                    isAvailable = rs2.getBoolean("availability");
                    btnAvailability.setText(isAvailable ? "가능" : "불가능");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private int getTeamIdByName(String name) {
        return switch (name) {
            case "팀1" -> 1;
            case "팀2" -> 2;
            case "팀3" -> 3;
            default -> -1;
        };
    }
}
