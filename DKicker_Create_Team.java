package DB2025Team09;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class DKicker_Create_Team extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblIdValue;
    private JTextField tfNation;
    private JTextField tfFIFARank;
    private JTextField tfCurrName;
    private JTextField tfCurrPoints;

    public DKicker_Create_Team() {
        setTitle("Create New Team");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JLabel lblId = new JLabel("팀 ID:");
        lblId.setBounds(30, 20, 80, 25);
        contentPane.add(lblId);

        lblIdValue = new JLabel("");
        lblIdValue.setBounds(120, 20, 200, 25);
        contentPane.add(lblIdValue);

        JLabel lblNation = new JLabel("국가:");
        lblNation.setBounds(30, 60, 80, 25);
        contentPane.add(lblNation);

        tfNation = new JTextField();
        tfNation.setBounds(120, 60, 200, 25);
        contentPane.add(tfNation);

        JLabel lblRank = new JLabel("FIFA 랭킹:");
        lblRank.setBounds(30, 100, 80, 25);
        contentPane.add(lblRank);

        tfFIFARank = new JTextField();
        tfFIFARank.setBounds(120, 100, 200, 25);
        contentPane.add(tfFIFARank);

        JLabel lblName = new JLabel("현재 대회 이름:");
        lblName.setBounds(30, 140, 100, 25);
        contentPane.add(lblName);

        tfCurrName = new JTextField();
        tfCurrName.setBounds(140, 140, 180, 25);
        contentPane.add(tfCurrName);

        JLabel lblPoints = new JLabel("승점:");
        lblPoints.setBounds(30, 180, 80, 25);
        contentPane.add(lblPoints);

        tfCurrPoints = new JTextField();
        tfCurrPoints.setBounds(120, 180, 200, 25);
        contentPane.add(tfCurrPoints);

        JButton btnInsert = new JButton("생성");
        btnInsert.setBounds(150, 220, 120, 30);
        contentPane.add(btnInsert);

        btnInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                insertTeam();
            }
        });
        
        JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DKicker().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(226, 6, 117, 29);
		contentPane.add(btnNewButton);

        fetchNextIdTeam();  // 자동으로 ID 표시
    }

    private void fetchNextIdTeam() {
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(idTeam) AS maxId FROM DB2025_Team")) {

            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt("maxId") + 1;
            }
            lblIdValue.setText(String.valueOf(nextId));
        } catch (SQLException e) {
            e.printStackTrace();
            lblIdValue.setText("ERROR");
        }
    }

    private void insertTeam() {
        int idTeam = Integer.parseInt(lblIdValue.getText());
        String nation = tfNation.getText();
        int fifaRank = Integer.parseInt(tfFIFARank.getText());
        String currName = tfCurrName.getText();
        int currPoints = Integer.parseInt(tfCurrPoints.getText());

        String query = "INSERT INTO DB2025_Team (idTeam, nation, FIFArank, currName, currPoints) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, idTeam);
            pstmt.setString(2, nation);
            pstmt.setInt(3, fifaRank);
            pstmt.setString(4, currName);
            pstmt.setInt(5, currPoints);

            int result = pstmt.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "팀이 성공적으로 추가되었습니다.");
                dispose(); // 창 닫기
            } else {
                JOptionPane.showMessageDialog(this, "추가 실패");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "DB 오류: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                DKicker_Create_Team frame = new DKicker_Create_Team();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
