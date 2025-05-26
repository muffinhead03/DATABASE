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
	private int idTeam;
	
   
	/**
	 * Launch the application.
	 */
	// 테스트용 main 함수. 실제 구동에서는 쓰이지 않습니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DKicker_player_choose frame = new DKicker_player_choose(1);
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
		
		// 테이블에 선택한 팀의 선수 목록을 불러오는 메서드입니다.
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // 기존 테이블 데이터 초기화
	    //선택된 팀 선수만 쿼리
	    String sql = "SELECT idPlayer, playerName FROM DB2025_Player WHERE idTeam = ?";
	    try (Connection conn = DBUtil.getConnection();
	            PreparedStatement pstmt = conn.prepareStatement(sql)) {
	           
	           pstmt.setInt(1, idTeam); // 변수 바인딩

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
	
	
	
	public DKicker_player_choose(int idTeam) {
		
		// DKicker에서 고른 팀의 선수 명단을 보이고 그 명단에서 이용자의 이름과 id를 고르게 합니다. 그 후 선수 메뉴로 이동합니다.
		//idTeam은 자신이 선택한 팀 id를 나타냅니다. 이는 이후 다른 선수 메뉴 클래스에서도 동일합니다.
		
		this.idTeam = idTeam;
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
		
		//뒤로 가기 버튼, 클릭 시 처음 화면으로 이동합니다.
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
		// 선택 버튼, 클릭 시 선택한 선수 id와 팀 id를 가지고 선수 메뉴 창으로 이동합니다.
		JButton btnNewButton_1 = new JButton("선택");
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int selectedRow = table.getSelectedRow();
		        if (selectedRow != -1) {
		            Object playerIdObj = table.getValueAt(selectedRow, 0);
		            int idPlayer = Integer.parseInt(playerIdObj.toString()); //선택한 선수 id
		            new player(idTeam, idPlayer).setVisible(true);
		            dispose();
		        } else {
		            JOptionPane.showMessageDialog(null, "선수를 선택해 주세요.", "선택 오류", JOptionPane.WARNING_MESSAGE); // 선수를 아직 고르지 않고 버튼을 누를 시 나오는 오류메세지
		        }
		    }
		});
		panel.add(btnNewButton_1);
		
		loadPlayerData();
	}
}
