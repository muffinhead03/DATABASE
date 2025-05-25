package DB2025Team09;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import java.sql.*;

public class staff_gameStatisticsCreate extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_6;
	private JTextField textField_8;
	private int idTeam;
	private JComboBox comboBox;
	private JComboBox fieldbox;
	private JComboBox setpiecebox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_gameStatisticsCreate frame = new staff_gameStatisticsCreate(1);
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
	public staff_gameStatisticsCreate(int idTeam) {
		this.idTeam = idTeam;
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
		
		JLabel lblNewLabel = new JLabel("경기 통계 데이터 생성");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 111, 438, 123);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel_11 = new JLabel("필드 전술");
		lblNewLabel_11.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_11);
		
		fieldbox = new JComboBox();
		panel.add(fieldbox);
		//fieldbox.setColumns(10);
		
		JLabel lblNewLabel_12 = new JLabel("세트피스 전술");
		lblNewLabel_12.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_12);
		
		setpiecebox = new JComboBox();
		panel.add(setpiecebox);
		//setpiecebox.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("전체 슛팅 수");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("유효슛팅 수");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("정확한 패스 수");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("공격지역 패스 수");
		lblNewLabel_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_9);
		
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("수비-가로채기 수");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("수비-차단 수");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		textField_8 = new JTextField();
		panel.add(textField_8);
		textField_8.setColumns(10);
		
		JButton btnNewButton_1 = new JButton("생성");
		btnNewButton_1.setBounds(6, 237, 438, 29);
		contentPane.add(btnNewButton_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insertgamestatic();
			}
		});

		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 75, 438, 29);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("경기 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_1);
		
		comboBox = new JComboBox();
		panel_1.add(comboBox);
		loadGameid();
		
		loadTacticsFromDatabase();
	}
	
	public void loadGameid() {
		try {
		    Connection conn = DBUtil.getConnection();
		    String sql = "SELECT idGame FROM DB2025_GameRec\n"
		    		+ " WHERE idTeam1 = ? OR idTeam2 = ?";
		    PreparedStatement pstmt = conn.prepareStatement(sql);
		    pstmt.setInt(1, idTeam);
		    pstmt.setInt(2, idTeam);
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
	}
	
	private void loadTacticsFromDatabase() {
		try {
			Connection conn = DBUtil.getConnection();
			String sql = "SELECT tacticName, tacticType FROM DB2025_Tactics WHERE idTeam = ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, idTeam);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String tacticName = rs.getString("tacticName");
				String tacticType = rs.getString("tacticType");

				if (tacticType.equalsIgnoreCase("Field")) {
					fieldbox.addItem(tacticName);
				} else if (tacticType.equalsIgnoreCase("Setpiece")) {
					setpiecebox.addItem(tacticName);
				}
			}

			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int getTacticIdByName(String tacticName, String tacticType) throws SQLException {
		Connection conn = DBUtil.getConnection();
		String sql = "SELECT idTactic FROM DB2025_Tactics WHERE tacticName = ? AND tacticType = ? AND idTeam = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, tacticName);
		pstmt.setString(2, tacticType);
		pstmt.setInt(3, idTeam);
		ResultSet rs = pstmt.executeQuery();

		int id = -1;
		if (rs.next()) {
			id = rs.getInt("idTactic");
		}

		rs.close();
		pstmt.close();
		conn.close();
		return id;
	}
	private void insertgamestatic() {
	    try {
	        int idField = getTacticIdByName((String) fieldbox.getSelectedItem(), "Field");
	        int idSetpiece = getTacticIdByName((String) setpiecebox.getSelectedItem(), "Setpiece");
	        int idGame = (Integer) comboBox.getSelectedItem();

	        Connection conn = DBUtil.getConnection();
	        String sql = "UPDATE DB2025_GameStat SET idField = ?, idSetpiece = ?, allShots = ?, shotOnTarget = ?, accPass = ?, attackPass = ?, intercept = ?, blocking = ? WHERE idGame = ? AND idOurTeam = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        
	        pstmt.setInt(1, idField);
	        pstmt.setInt(2, idSetpiece);
	        pstmt.setInt(3, Integer.parseInt(textField_2.getText())); // allShots
	        pstmt.setInt(4, Integer.parseInt(textField_3.getText())); // shotOnTarget
	        pstmt.setInt(5, Integer.parseInt(textField_1.getText())); // accPass
	        pstmt.setInt(6, Integer.parseInt(textField_4.getText())); // attackPass
	        pstmt.setInt(7, Integer.parseInt(textField_6.getText())); // intercept
	        pstmt.setInt(8, Integer.parseInt(textField_8.getText())); // blocking
	        pstmt.setInt(9, idGame);
	        pstmt.setInt(10, idTeam);
	        
	        int affectedRows = pstmt.executeUpdate();
	        if (affectedRows > 0) {
	            System.out.println("경기 통계가 성공적으로 수정되었습니다.");
	        } else {
	            System.out.println("수정할 데이터가 없습니다. 먼저 해당 경기와 팀의 데이터가 존재하는지 확인하세요.");
	        }

	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}


}
