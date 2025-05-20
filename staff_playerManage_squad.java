package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class staff_playerManage_squad extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerManage_squad frame = new staff_playerManage_squad();
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
	public staff_playerManage_squad() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerManage().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("선수 스쿼드 조회 및 관리");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 73, 438, 104);
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
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 178, 438, 52);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("선수 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		
		JLabel lblNewLabel_1_1 = new JLabel("이름");
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_1);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1_2 = new JLabel("포지션");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_2);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_1_3 = new JLabel("출전 시간");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_3);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 238, 438, 28);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JButton btnNewButton_4 = new JButton("조회");
		panel_1.add(btnNewButton_4);		
		
		JButton btnNewButton_3 = new JButton("수정");
		panel_1.add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton("삭제");
		panel_1.add(btnNewButton_2);
		
		JButton btnNewButton_1 = new JButton("신규추가");
		panel_1.add(btnNewButton_1);
		
		btnNewButton_4.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        loadSquadData();
		    }
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        try {
		            int idPlayer = (int) comboBox.getSelectedItem();
		            int playTime = Integer.parseInt(textField_3.getText());
		            int idGame = Integer.parseInt(  // 추후 변경 가능
		                javax.swing.JOptionPane.showInputDialog("경기 ID를 입력하세요:")
		            );

		            String query = "INSERT INTO DB2025_Squad (idGame, idPlayer, playTime) VALUES (?, ?, ?)";

		            try (Connection conn = DBUtil.getConnection();
		                 PreparedStatement pstmt = conn.prepareStatement(query)) {

		                pstmt.setInt(1, idGame);
		                pstmt.setInt(2, idPlayer);
		                pstmt.setInt(3, playTime);

		                int result = pstmt.executeUpdate();

		                if (result > 0) {
		                    JOptionPane.showMessageDialog(null, "스쿼드 추가 성공!");
		                    loadSquadData();
		                } else {
		                    JOptionPane.showMessageDialog(null, "추가 실패");
		                }
		            }

		        } catch (Exception ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
		        }
		    }
		});
		
		btnNewButton_2.addActionListener(e -> {
		    try {
		        int idPlayer = (int) comboBox.getSelectedItem();
		        int idGame = Integer.parseInt(
		            JOptionPane.showInputDialog("삭제할 경기 ID를 입력하세요:")
		        );

		        String query = "DELETE FROM DB2025_Squad WHERE idGame = ? AND idPlayer = ?";

		        try (Connection conn = DBUtil.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(query)) {

		            pstmt.setInt(1, idGame);
		            pstmt.setInt(2, idPlayer);

		            int result = pstmt.executeUpdate();
		            if (result > 0) {
		                JOptionPane.showMessageDialog(null, "삭제 완료");
		                loadSquadData();
		            } else {
		                JOptionPane.showMessageDialog(null, "삭제 실패");
		            }

		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
		    }
		});
		
		btnNewButton_3.addActionListener(e -> {
		    try {
		        int idPlayer = (int) comboBox.getSelectedItem();
		        int playTime = Integer.parseInt(textField_3.getText());
		        int idGame = Integer.parseInt(
		            JOptionPane.showInputDialog("수정할 경기 ID를 입력하세요:")
		        );

		        String query = "UPDATE DB2025_Squad SET playTime = ? WHERE idGame = ? AND idPlayer = ?";

		        try (Connection conn = DBUtil.getConnection();
		             PreparedStatement pstmt = conn.prepareStatement(query)) {

		            pstmt.setInt(1, playTime);
		            pstmt.setInt(2, idGame);
		            pstmt.setInt(3, idPlayer);

		            int result = pstmt.executeUpdate();
		            if (result > 0) {
		                JOptionPane.showMessageDialog(null, "수정 완료");
		                loadSquadData();
		            } else {
		                JOptionPane.showMessageDialog(null, "수정 실패");
		            }

		        }
		    } catch (Exception ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "오류 발생: " + ex.getMessage());
		    }
		});

		
		
		
		loadSquadData();
		loadPlayerIdsToComboBox(comboBox);
	}
		
		private void loadSquadData() {
		    DefaultTableModel model = (DefaultTableModel) table.getModel();
		    model.setRowCount(0);

		    String query = "SELECT s.idPlayer, p.playerName, p.position, s.playTime " +
		                   "FROM DB2025_Squad s " +
		                   "JOIN DB2025_Player p ON s.idPlayer = p.idPlayer";

		    try (Connection conn = DBUtil.getConnection();
		         java.sql.Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(query)) {

		        while (rs.next()) {
		            Object[] row = {
		                rs.getInt("idPlayer"),
		                rs.getString("playerName"),
		                rs.getString("position"),
		                rs.getInt("playTime")
		            };
		            model.addRow(row);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		
	
		}
		
		private void loadPlayerIdsToComboBox(JComboBox comboBox) {
		    String query = "SELECT idPlayer FROM DB2025_Player";

		    try (Connection conn = DBUtil.getConnection();
		         java.sql.Statement stmt = conn.createStatement();
		         ResultSet rs = stmt.executeQuery(query)) {

		        while (rs.next()) {
		            comboBox.addItem(rs.getInt("idPlayer"));
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		}
		
		
}
