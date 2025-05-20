package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;

import java.net.URL;
import java.sql.*;


public class DKicker extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    public static int currentTeamId;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
       try {
           Connection conn = DBUtil.getConnection();  // 공통 메서드 사용
           // SQL 실행...
       } catch (SQLException e) {
           e.printStackTrace();
       }
       EventQueue.invokeLater(new Runnable() {
          public void run() {
             try {
                DKicker frame = new DKicker();
                frame.setVisible(true);
             } catch (Exception e) {
                e.printStackTrace();
             }
          }
       });
    }

    /**
     * Create the frame.
     */
    public DKicker() {
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setBounds(100, 100, 450, 300);
       contentPane = new JPanel();
       contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

       setContentPane(contentPane);
       contentPane.setLayout(null);
       
       JLabel lblNewLabel = new JLabel("Login");
       lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 36));
       lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
       lblNewLabel.setBounds(6, 50, 438, 78);
       contentPane.add(lblNewLabel);
       
       // 축구공 이미지 라벨
       ImageIcon ballIcon = null;
       try {
          // 이미지 리소스 경로 (src/DB2025Team09/ball.png 기준)
          URL imageUrl = getClass().getClassLoader().getResource("DB2025Team09/ball.png");

          // 경로가 유효한지 출력
          System.out.println("이미지 경로: " + imageUrl);

          if (imageUrl == null) {
             throw new Exception("⚠ 이미지 리소스를 찾을 수 없습니다.");
          }

          // 아이콘 생성
           ballIcon = new ImageIcon(imageUrl);

           // 유효한 이미지인지 확인
           if (ballIcon.getIconWidth() == -1) {
              throw new Exception("⚠ 이미지 로딩 실패 (잘못된 이미지 포맷이거나 깨짐)");
          }

          } catch (Exception e) {
           System.out.println("⚠ 이미지 로딩 중 오류: " + e.getMessage());
          }
             
          if (ballIcon != null) {
             Image scaled = ballIcon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
              ImageIcon resized = new ImageIcon(scaled);

              JLabel leftBall = new JLabel(resized);
              leftBall.setBounds(40, 50, 64, 64);
              contentPane.add(leftBall);

              JLabel rightBall = new JLabel(resized);
              rightBall.setBounds(345, 50, 64, 64);
              contentPane.add(rightBall);
          }


       JComboBox comboBox = new JComboBox();
       comboBox.setModel(new DefaultComboBoxModel(new String[] {"팀1", "팀2", "팀3"}));
       comboBox.setBounds(96, 168, 253, 27);
       contentPane.add(comboBox);

       // btnNewButton 선언 및 초기화
       JButton btnNewButton = new JButton("선수");
       btnNewButton.setBounds(96, 207, 117, 29);
       contentPane.add(btnNewButton);

       btnNewButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               String selectedTeam = (String) comboBox.getSelectedItem();  // 선택된 팀명 가져오기
               if("팀1".equals(selectedTeam)) {
            	   currentTeamId = 1;
               } else if("팀2".equals(selectedTeam)) {
            	   currentTeamId = 2;
               }
               else if("팀3".equals(selectedTeam)) {
            	   currentTeamId = 3;
               }
               
               new DKicker_player_choose(selectedTeam).setVisible(true);
               
               dispose();
           }
       });


       
       JButton btnNewButton_1 = new JButton("스태프");
       btnNewButton_1.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
              String selectedTeam = (String) comboBox.getSelectedItem();
              int idTeam = Integer.parseInt(selectedTeam.replaceAll("[^0-9]", ""));
             new staff(idTeam).setVisible(true); dispose();
          }
       });
       btnNewButton_1.setBounds(232, 207, 117, 29);
       contentPane.add(btnNewButton_1);
       
       
       JLabel lblNewLabel_1 = new JLabel("팀을 선택하세요");
       lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
       lblNewLabel_1.setBounds(96, 140, 253, 16);
       contentPane.add(lblNewLabel_1);
    }
}
