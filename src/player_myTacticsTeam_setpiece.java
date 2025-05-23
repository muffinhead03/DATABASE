package DB2025Team09;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class player_myTacticsTeam_setpiece extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idPlayer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player_myTacticsTeam_setpiece frame = new player_myTacticsTeam_setpiece(0);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void loadSetpieceTactics() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // 기존 데이터 제거


	    try (Connection conn = DBUtil.getConnection();) {
	        String sql = "SELECT idTactic, tacticName, tacticFormation, explainTactics " +
	                     "FROM DB2025_Tactics " +
	                     "WHERE idTeam = (SELECT idTeam FROM DB2025_Player WHERE idPlayer = ?) " +
	                     "AND tacticType = 'Setpiece'";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, idPlayer);
	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {
	            int idTactic = rs.getInt("idTactic");
	            String name = rs.getString("tacticName");
	            String formation = rs.getString("tacticFormation");
	            String desc = rs.getString("explainTactics");

	            model.addRow(new Object[] { idTactic, name, formation, desc });
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	/**
	 * Create the frame.
	 */
	public player_myTacticsTeam_setpiece(int idPlayer) {
		this.idPlayer = idPlayer;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myTacticsTeam(idPlayer).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("세트피스 전술");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 40, 438, 29);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 81, 438, 185);
		contentPane.add(scrollPane);
		
		 String[] columnNames = {"ID", "전술명", "포메이션", "설명"};
	        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
	        table = new JTable(model);

	        // 설명 칼럼에 TextAreaRenderer 적용
	        table.getColumnModel().getColumn(3).setCellRenderer(new TextAreaRenderer());
			
		
		
		scrollPane.setViewportView(table);
	    loadSetpieceTactics();

	}
	
	static class TextAreaRenderer extends JTextArea implements TableCellRenderer {
        public TextAreaRenderer() {
            setLineWrap(true);
            setWrapStyleWord(true);
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value != null ? value.toString() : "");
            setSize(table.getColumnModel().getColumn(column).getWidth(), getPreferredSize().height);
            if (table.getRowHeight(row) != getPreferredSize().height) {
                table.setRowHeight(row, getPreferredSize().height);
            }
            return this;
        }
	}
}
