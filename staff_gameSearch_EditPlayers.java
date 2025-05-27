package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;

//특정 경기의 출전 선수 목록을 수정할 수 있는 클래스입니다. 
public class staff_gameSearch_EditPlayers extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table; // 출전 선수 목록을 보여줄 테이블입니다.
    private int idTeam, idGame; // 현재 팀 ID, 경기 ID입니다. 

    private JTextField textField_2; //출전 시간 입력 필드입니다
    private JComboBox comboBox; // 수정할 선수 ID 선택할 수 있는 콤보박스입니다.

    //메인 실행 메서드입니다.
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                staff_gameSearch_EditPlayers frame = new staff_gameSearch_EditPlayers(1, 1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //생성자입니다. 
    public staff_gameSearch_EditPlayers(int idTeam, int idGame) {
        this.idTeam = idTeam;
        this.idGame = idGame;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 400); // 창 크기 조정

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 뒤로 가기 버튼입니다. 
        JButton btnNewButton = new JButton("Back");
        btnNewButton.addActionListener(e -> {
            new staff_gameSearch_Edit(idTeam).setVisible(true);
            dispose();
        });
        btnNewButton.setBounds(6, 6, 117, 29);
        contentPane.add(btnNewButton);

        // 타이틀 라벨입니다.
        JLabel lblNewLabel = new JLabel("출전 선수 수정");
        lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(6, 10, 488, 30);
        contentPane.add(lblNewLabel);

        // 테이블 스크롤 영역 및 패널을 생성하는 영역입니다.
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(6, 50, 468, 150);
        contentPane.add(scrollPane);

        //출전 선수 테이블 생성 및 추가하는 부분입니다. 
        table = new JTable();
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] { "선수 ID", "이름", "포지션", "출전 시간" }
        ));
        scrollPane.setViewportView(table);

        // 수정할 선수 ID 선택 콤보박스 영역입니다.
        JPanel panel = new JPanel();
        panel.setBounds(6, 210, 468, 30);
        contentPane.add(panel);
        panel.setLayout(new GridLayout(1, 2, 10, 0));

        JLabel lblNewLabel_1 = new JLabel("변경할 선수의 ID");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblNewLabel_1);

        comboBox = new JComboBox();
        panel.add(comboBox);

        // 출전 시간 입력 영역입니다. 
        JPanel panel_1 = new JPanel();
        panel_1.setBounds(6, 250, 468, 30);
        contentPane.add(panel_1);
        panel_1.setLayout(new GridLayout(1, 2, 10, 0));

        JLabel lblNewLabel_3 = new JLabel("출전 시간");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
        panel_1.add(lblNewLabel_3);

        textField_2 = new JTextField();//출전 시간 입력합니다.
        panel_1.add(textField_2);
        textField_2.setColumns(10);

        // 저장 버튼 패널입니다.
        JPanel panelSave = new JPanel();
        panelSave.setBounds(6, 300, 468, 40);
        contentPane.add(panelSave);

        JButton btnSave = new JButton("저장");
        panelSave.add(btnSave);

        //저장 버튼 클릭 시, 출전 시간 DB가 업데이트 됩니다. 
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updatePlayerPlayTime(); // 위 메서드 호출
            }
        });


        // 콤보박스에 선수 ID 불러옵니다. 
        try {
            Connection conn = DBUtil.getConnection();
            String sql = "SELECT idPlayer FROM DB2025_Player WHERE idTeam = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idTeam);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                comboBox.addItem(rs.getString("idPlayer"));
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //경기 ID표시용 라벨입니다. 
        JLabel lblGameId = new JLabel("경기 ID: " + idGame);
        lblGameId.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
        lblGameId.setBounds(370, 20, 200, 20);  // 적당한 위치 (왼쪽 상단)
        contentPane.add(lblGameId);

        //테이블 데이터 초기입니다. 
        loadPlayersToTable();
    }
    
    private void loadPlayersToTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0); // 기존 테이블 내용 초기화

        try {
            Connection conn = DBUtil.getConnection();
            String sql = "SELECT idPlayer AS '선수 id', playerName AS '선수 이름', position AS '포지션', playTime AS '출전 시간' " +
                         "FROM DB2025_Game_Players_List WHERE idGame = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, idGame);
            ResultSet rs = pstmt.executeQuery();

            //결과를 테이블에 추가합니다.
            while (rs.next()) {
                Object[] row = {
                    rs.getInt("선수 id"),
                    rs.getString("선수 이름"),
                    rs.getString("포지션"),
                    rs.getString("출전 시간")
                };
                model.addRow(row);
            }

            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "선수 목록 불러오기 중 오류가 발생했습니다.", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    //선택한 선수의 출전 시간을 수정하는 메소드입니다.(DB에 UPDATE를 합니다)
    private void updatePlayerPlayTime() {
        String playTime = textField_2.getText();  // 출전 시간 텍스트 필드에서 가져오기
        String selectedPlayerIdStr = (String) comboBox.getSelectedItem(); // 콤보박스에서 선수 ID 선택

        if (selectedPlayerIdStr == null || selectedPlayerIdStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "선수를 선택하세요.");
            return;
        }

        if (playTime == null || playTime.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "출전 시간을 입력하세요.");
            return;
        }

        try {
            int idPlayer = Integer.parseInt(selectedPlayerIdStr.trim());

            Connection conn = DBUtil.getConnection();
            String sql = "UPDATE DB2025_Squad SET playTime = ? WHERE idGame = ? AND idPlayer = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, playTime);
            pstmt.setInt(2, idGame);  // 이미 클래스 필드에 정의됨
            pstmt.setInt(3, idPlayer);

            int updated = pstmt.executeUpdate();
            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "출전 시간이 성공적으로 수정되었습니다.");
                loadPlayersToTable();  // 테이블 리로드 메서드
            } else {
                JOptionPane.showMessageDialog(this, "수정할 선수가 존재하지 않습니다.", "오류", JOptionPane.ERROR_MESSAGE);
            }

            pstmt.close();
            conn.close();

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "선수 ID가 올바른 형식이 아닙니다.", "입력 오류", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "데이터베이스 오류가 발생했습니다.", "DB 오류", JOptionPane.ERROR_MESSAGE);
        }
    }


}
