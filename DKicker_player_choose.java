package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.*;


public class DKicker_player_choose  extends JFrame  {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private String teamName;  // 전달받을 팀 이름을 저장할 변수
	private int teamnum;
	public static int currentidPlayer;

    public DKicker_player_choose(String teamName) {
    	this(currentidPlayer);
        this.teamName = teamName;
        this.teamnum = Integer.parseInt(teamName.replaceAll("[^0-9]", ""));
        // String 형으로 넘어온 변수에서 숫자만
        loadPlayerData();  // 팀 선수 불러오기
    }
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DKicker_player_choose frame = new DKicker_player_choose(DKicker.currentTeamId);
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
	
	private void loadPlayerData() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // 기존 테이블 데이터 초기화
	    //선택된 팀 선수만 쿼리
	    String sql = "SELECT idPlayer, playerName FROM DB2025_Player WHERE idTeam = ?";
	    try (Connection conn = DBUtil.getConnection();PreparedStatement pstmt = conn.prepareStatement(sql)) {

	           
	           pstmt.setInt(1, teamnum); // 변수 바인딩

	           try (ResultSet rs = pstmt.executeQuery()) {
	               while (rs.next()) {
	                   String id = rs.getString("idPlayer");
	                   String name = rs.getString("playerName");
	                   model.addRow(new Object[]{id, name});
	               }
	           }

	       } catch (SQLException e) {
	           e.printStackTrace();
	       }
	   }
	
	
	
	public DKicker_player_choose(int n) {
		try {
		    Connection conn = DBUtil.getConnection();  // 공통 메서드 사용
		    // SQL 실행...
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new DKicker().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 84, 438, 141);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				 "\uC120\uC218 ID", "\uC120\uC218 \uC774\uB984"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("선수 선택");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 47, 438, 25);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 227, 438, 39);
		contentPane.add(panel);
		
		JButton btnNewButton_1 = new JButton("선택");
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            Object playerIdObj = table.getValueAt(selectedRow, 0);
		            int playerid = Integer.parseInt(playerIdObj.toString());

		            // ✅ 선택된 선수 ID를 전역 변수에 저장
		            currentidPlayer = playerid;

		            // 필요시 로그 확인
		            System.out.println("선택된 player ID = " + currentidPlayer);

		            // 다음 화면으로 전환
		            new player().setVisible(true);
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(null, "선수를 선택해 주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE);
		        }
		    }
		});
		panel.add(btnNewButton_1);
		
		System.out.println(teamName);
		System.out.println(teamnum);
		loadPlayerData();
	}
}
