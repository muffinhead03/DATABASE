package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import java.awt.GridLayout;

public class viewPlayers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam;
	private JComboBox<String> comboBoxPosition;
	private JComboBox<String> comboBoxSort;
	private JComboBox<String> comboBoxTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewPlayers frame = new viewPlayers(0);
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
	public viewPlayers(int idTeam) {
		this.idTeam = idTeam;
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("선수 정보");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 17));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 36, 438, 25);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 94, 438, 137);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC120\uC218 ID", "\uC774\uB984", "\uD3EC\uC9C0\uC158","액션", "실적"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel_1 = new JLabel("포지션");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(6, 73, 39, 16);
		contentPane.add(lblNewLabel_1);
		
		comboBoxPosition = new JComboBox<>();
		comboBoxPosition.setModel(new DefaultComboBoxModel<>(new String[] {"전체", "AM", "DF", "DM", "FW", "GK"}));
		comboBoxPosition.setBounds(46, 69, 77, 27);
		contentPane.add(comboBoxPosition);
		
		JLabel lblNewLabel_2 = new JLabel("정렬");
		lblNewLabel_2.setBounds(288, 73, 28, 16);
		contentPane.add(lblNewLabel_2);
		
		comboBoxSort = new JComboBox<>();
		comboBoxSort.setModel(new DefaultComboBoxModel<>(new String[] {"번호 순", "이름 가나다순","액션", "최고 실적순"}));
		comboBoxSort.setBounds(313, 69, 131, 27);
		contentPane.add(comboBoxSort);
		
		JLabel lblNewLabel_3 = new JLabel("소속 팀");
		lblNewLabel_3.setBounds(122, 73, 39, 16);
		contentPane.add(lblNewLabel_3);
		
		comboBoxTeam = new JComboBox<>();

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		try {
		    Connection conn = DBUtil.getConnection();
		    Statement stmt = conn.createStatement();
		    ResultSet rs = stmt.executeQuery("SELECT idTeam FROM DB2025_Team");

		    while (rs.next()) {
		        int id = rs.getInt("idTeam");
		       
		        model.addElement("팀"+id);
		    }

		    rs.close();
		    stmt.close();
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		    javax.swing.JOptionPane.showMessageDialog(null, "팀 목록 로딩 중 오류가 발생했습니다.");
		}

		comboBoxTeam.setModel(model);
		comboBoxTeam.setBounds(159, 69, 117, 27);
		contentPane.add(comboBoxTeam);
		JPanel panel = new JPanel();
		panel.setBounds(6, 237, 438, 29);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton_1 = new JButton("수정");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewPlayers_Edit(idTeam).setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("삭제");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow == -1) {
					JOptionPane.showMessageDialog(null, "삭제할 선수를 테이블에서 선택해주세요.");
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(null, "정말 이 선수를 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION) return;

				// 테이블의 첫 번째 열(idPlayer)에서 ID 추출
				int playerId = (int) table.getValueAt(selectedRow, 0);

				try {
					Connection conn = DBUtil.getConnection();
					String sql = "DELETE FROM DB2025_Player WHERE idPlayer = ?";
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, playerId);

					int rows = pstmt.executeUpdate();
					if (rows > 0) {
						JOptionPane.showMessageDialog(null, "선수 정보가 성공적으로 삭제되었습니다.");
						loadPlayerData();  // 테이블 갱신
					} else {
						JOptionPane.showMessageDialog(null, "삭제 실패: 해당 선수를 찾을 수 없습니다.");
					}

					pstmt.close();
					conn.close();
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "삭제 중 오류가 발생했습니다.");
				}
			}
		});

		
		panel.add(btnNewButton_2);
		
		comboBoxPosition.addActionListener(e -> loadPlayerData());
		comboBoxSort.addActionListener(e -> loadPlayerData());
		comboBoxTeam.addActionListener(e -> loadPlayerData());
		loadPlayerData();
	}
	
private void loadPlayerData() {
		
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0);

	    // 콤보박스에서 선택한 값 가져오기
	    String selectedPosition = comboBoxPosition.getSelectedItem().toString();
	    String selectedSort = comboBoxSort.getSelectedItem().toString();
	    String selectedTeam = comboBoxTeam.getSelectedItem().toString();
	    
	    StringBuilder query = new StringBuilder("SELECT idPlayer, playerName, position, playerAction, performance FROM db2025_player WHERE 1=1");

	    if (!selectedPosition.equals("전체")) {
	        query.append(" AND position = '").append(selectedPosition).append("'");
	    }

	    if (!selectedTeam.equals("전체")) {
	        query.append(" AND idTeam = ").append(selectedTeam.replace("팀", ""));
	    }

	    // 정렬 조건
	    if (selectedSort.equals("이름 가나다순")) {
	        query.append(" ORDER BY playerName ASC");
	    } else if (selectedSort.equals("최고 실적순")) {
	        query.append(" ORDER BY performance DESC");
	    } else if(selectedSort.equals("번호 순")){
	    	query.append(" ORDER BY idPlayer ASC");	
	    } else if(selectedSort.equals("액션")){
	    	query.append(" ORDER BY playerAction ASC");	
	    }

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query.toString());
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("idPlayer"),
	                rs.getString("playerName"),
	                rs.getString("position"),
	                rs.getString("playerAction"),
	                rs.getInt("performance")
	            };
	            model.addRow(row);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	
}