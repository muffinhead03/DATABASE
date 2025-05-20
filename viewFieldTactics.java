package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import java.sql.*;

public class viewFieldTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewFieldTactics frame = new viewFieldTactics(DKicker.currentTeamId);
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
	public viewFieldTactics(int idTeam) {
		this.idTeam = DKicker.currentTeamId;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTactics(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("필드 전술");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 73, 438, 193);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC804\uC220ID", "\uC804\uC220 \uC774\uB984", "\uD3EC\uBA54\uC774\uC158"
			}
		));
		scrollPane.setViewportView(table);
		
		loadFieldTacticsToTable(table);
	}
	
	public void loadFieldTacticsToTable( JTable table) {
	    String[] columnNames = {"전술 ID", "전술 이름", "포메이션"};
	    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

	    try {
	        Connection conn = DBUtil.getConnection();
	        String sql = "SELECT idTactic, tacticName, tacticFormation " +
	                     "FROM DB2025_Tactics " +
	                     "WHERE tacticType = 'Field' AND idTeam = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, idTeam);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int id = rs.getInt("idTactic");
	            String name = rs.getString("tacticName");
	            String formation = rs.getString("tacticFormation");
	            model.addRow(new Object[]{id, name, formation});
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();

	        table.setModel(model); // 테이블에 모델 적용

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "전술 데이터를 불러오는 데 실패했습니다.");
	    }
	}


}
