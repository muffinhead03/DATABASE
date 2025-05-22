package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class player_viewPlayers extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
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
					player_viewPlayers frame = new player_viewPlayers();
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
	public player_viewPlayers() {
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
				new player().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 94, 438, 172);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC120\uC218 ID", "\uC774\uB984", "\uD3EC\uC9C0\uC158", "\uCD9C\uC804 \uC2DC\uAC04"
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
		comboBoxSort.setModel(new DefaultComboBoxModel<>(new String[] {"가나다순", "최신 등록순", "최다 출전 시간순"}));
		comboBoxSort.setBounds(313, 69, 131, 27);
		contentPane.add(comboBoxSort);
		
		JLabel lblNewLabel_3 = new JLabel("소속 팀");
		lblNewLabel_3.setBounds(122, 73, 39, 16);
		contentPane.add(lblNewLabel_3);
		
		comboBoxTeam = new JComboBox<>();
		comboBoxTeam.setModel(new DefaultComboBoxModel<>(new String[] {"전체", "팀1", "팀2", "팀3"}));
		comboBoxTeam.setBounds(159, 69, 117, 27);
		contentPane.add(comboBoxTeam);
		
		JLabel lblNewLabel_4 = new JLabel("나의 포지션: AM");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(288, 6, 156, 16);
		contentPane.add(lblNewLabel_4);
		
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
	    
	    StringBuilder query = new StringBuilder("SELECT idPlayer, playerName, position, performance FROM db2025_player WHERE 1=1");

	    if (!selectedPosition.equals("전체")) {
	        query.append(" AND position = '").append(selectedPosition).append("'");
	    }

	    if (!selectedTeam.equals("전체")) {
	        query.append(" AND idTeam = ").append(selectedTeam.replace("팀", ""));
	    }

	    // 정렬 조건
	    if (selectedSort.equals("가나다순")) {
	        query.append(" ORDER BY playerName ASC");
	    } else if (selectedSort.equals("최신 등록순")) {
	        query.append(" ORDER BY idPlayer DESC");
	    } else if (selectedSort.equals("최다 출전 시간순")) {
	        query.append(" ORDER BY performance DESC");
	    }

	    try (Connection conn = DBUtil.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(query.toString());
	         ResultSet rs = pstmt.executeQuery()) {

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("idPlayer"),
	                rs.getString("playerName"),
	                rs.getString("position"),
	                rs.getInt("performance")
	            };
	            model.addRow(row);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
