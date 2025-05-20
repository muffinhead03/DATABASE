package DB2025Team09;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import java.sql.*;

public class staff_gamePlayerListCreate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_2;
	private int idTeam;
	private JComboBox comboBox;
	private JComboBox comboBox_1;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_gamePlayerListCreate frame = new staff_gamePlayerListCreate(1);
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
	public staff_gamePlayerListCreate(int idTeam) {
		this.idTeam=idTeam;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameManage(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("경기 출전 선수 명단 생성");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 75, 438, 29);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("경기 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		
		
		comboBox = new JComboBox();
		panel.add(comboBox);
		try {
		    Connection conn = DBUtil.getConnection();
		    String sql = "SELECT idGame FROM DB2025_GameRec WHERE idOurTeam = ?";
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, idTeam);
		    ResultSet rs = pstmt.executeQuery();

		    while (rs.next()) {
		        int idGame = rs.getInt("idGame");
		        comboBox.addItem(idGame); // comboBox에 경기 ID 추가
		    }

		    rs.close();
		    pstmt.close();
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		

		
		JButton btnNewButton_1 = new JButton("생성");
		btnNewButton_1.setBounds(6, 237, 438, 29);
		contentPane.add(btnNewButton_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 116, 438, 40);
		contentPane.add(scrollPane);
		
		JPanel panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new GridLayout(0, 5, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("선수 이름");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_2);
		
		comboBox_1 = new JComboBox();
		panel_1.add(comboBox_1);
		
		
		try {
		    Connection conn = DBUtil.getConnection();
		    String sql = "SELECT playerName FROM DB2025_Player WHERE idTeam = ?";
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, idTeam);
		    ResultSet rs = pstmt.executeQuery();

		    while (rs.next()) {
		        String name = rs.getString("playerName");
		        comboBox_1.addItem(name); // comboBox에 경기 ID 추가
		    }

		    rs.close();
		    pstmt.close();
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		}
		String playerName = (String) comboBox_1.getSelectedItem();
;
		
		JLabel lblNewLabel_3 = new JLabel("출전 시간");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("선수 명단");
		btnNewButton_2.setBounds(327, 168, 117, 29);
		btnNewButton_2.addActionListener(e -> {
		    JFrame squadFrame = new JFrame("스쿼드 목록");
		    squadFrame.setSize(600, 400);
		    squadFrame.setLocationRelativeTo(null);
		    
		    try {
		        // DB 연결
		        Connection conn = DBUtil.getConnection();
		        Statement stmt = conn.createStatement();
		        ResultSet rs = stmt.executeQuery(
		        	    "SELECT s.idGame AS \"경기 ID\", p.playerName AS \"선수 이름\", s.playTime AS \"출전 시간\" " +
		        	    "FROM DB2025_Squad s " +
		        	    "JOIN DB2025_Player p ON s.idPlayer = p.idPlayer"
		        	);
		        // ResultSet -> TableModel
		        DefaultTableModel model = new DefaultTableModel();
		        
		        ResultSetMetaData meta = rs.getMetaData();
		        int colCount = meta.getColumnCount();


		       
		        // 컬럼 이름 추가
		        for (int i = 1; i <= colCount; i++) {
		            model.addColumn(meta.getColumnLabel(i));
		        }

		        // 데이터 추가
		        while (rs.next()) {
		            Object[] row = new Object[colCount];
		            for (int i = 0; i < colCount; i++) {
		                row[i] = rs.getObject(i + 1);
		            }
		            model.addRow(row);
		        }

		        JTable table = new JTable(model);
		        JScrollPane scrollPane1 = new JScrollPane(table);
		        squadFrame.add(scrollPane1);

		        squadFrame.setVisible(true);

		        rs.close();
		        stmt.close();
		        conn.close();
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		        JOptionPane.showMessageDialog(null, "스쿼드 정보를 불러오는데 실패했습니다.");
		    }
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String playerName = (String) comboBox_1.getSelectedItem();
		        int idGame = (int) comboBox.getSelectedItem();

		        try {
		            int playTime = Integer.parseInt(textField_2.getText().trim());
		            boolean success = enterSquad();
		            if (success) {
		                JOptionPane.showMessageDialog(null, "선수 추가 성공!");
		            } else {
		                JOptionPane.showMessageDialog(null, "선수 추가 실패");
		            }
		        } catch (NumberFormatException ex) {
		            JOptionPane.showMessageDialog(null, "출전 시간을 숫자로 입력하세요.");
		        }
		    }
		});

		    
		
		contentPane.add(btnNewButton_2);
		
	}
	private boolean enterSquad() {
		int idPlayer;
		String playerName = (String) comboBox_1.getSelectedItem();
		int idGame = (int) comboBox.getSelectedItem();
		String text = textField_2.getText();         
		int playTime = Integer.parseInt(text);
		if(comboBox.getSelectedItem() == null) {
		    JOptionPane.showMessageDialog(null, "경기를 선택하세요.");
		    
		}
		if(comboBox_1.getSelectedItem() == null) {
		    JOptionPane.showMessageDialog(null, "선수를 선택하세요.");
		    
		}

		try {
		    Connection conn = DBUtil.getConnection();
		    String sql = "SELECT idPlayer FROM DB2025_Player WHERE playerName = ?"; 
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setString(1, playerName);
		    ResultSet rs = pstmt.executeQuery();    
		    idPlayer = -1;
		    if (rs.next()) {
		        idPlayer = rs.getInt("idPlayer");  
		    }
		    rs.close();
		    pstmt.close();
		    
		    String sql2 = "INSERT INTO DB2025_Squad " +
					  "(idGame, idPlayer, playTime) " +
					  "VALUES (?, ?, ?)";
		PreparedStatement pstmt2 = conn.prepareStatement(sql2);
		pstmt2.setInt(1, idGame);
		pstmt2.setInt(2, idPlayer);
		pstmt2.setInt(3, playTime);
		
		pstmt2.executeUpdate();
		pstmt2.close();
		    conn.close();
		    return true;
		} catch (Exception e) {
		    e.printStackTrace();
		    return false;
		}
		
	}
	
}
