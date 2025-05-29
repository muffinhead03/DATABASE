package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import java.sql.*;

public class viewPlayers_Edit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JComboBox comboBox;
	private JToggleButton tglbtnNewToggleButton;

	private int idTeam;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewPlayers_Edit frame = new viewPlayers_Edit(1);
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
	public viewPlayers_Edit(int idTeam) {
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
				new viewPlayers(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("선수 수정");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 29, 438, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 70, 438, 154);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("선수 ID");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		comboBox = new JComboBox();
		panel.add(comboBox);
		
		
		
		JLabel lblNewLabel_4 = new JLabel("이름");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_4);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		/*JLabel lblNewLabel_6 = new JLabel("번호");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_6);
		
		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(10);*/
		
		JLabel lblNewLabel_3 = new JLabel("포지션");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_3_1 = new JLabel("생년월일");
		lblNewLabel_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_3_1);
		
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("액션");
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_5);
		
		textField_5 = new JTextField();
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("실적");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("출전 가능 여부");
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_7);
		tglbtnNewToggleButton = new JToggleButton("가능");
		tglbtnNewToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnNewToggleButton.isSelected()) {
		            tglbtnNewToggleButton.setText("불가능"); // 눌렀을 때
		        } else {
		            tglbtnNewToggleButton.setText("가능"); // 안 눌렀을 때
		        }
			}
		});
		panel.add(tglbtnNewToggleButton);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 236, 438, 30);
		contentPane.add(panel_1);
		
		JButton btnNewButton_1 = new JButton("저장");
		panel_1.add(btnNewButton_1);
		btnNewButton_1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selected = (String) comboBox.getSelectedItem();
		        if (selected == null || selected.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "선수를 선택해주세요.");
		            return;
		        }

		        int playerId = Integer.parseInt(selected);
		        String name = textField_3.getText();
		        String position = textField_2.getText();
		        String birthday = textField_4.getText();
		        String action = textField_5.getText();
		        String performanceStr = textField.getText();
		        int ableToPlay = tglbtnNewToggleButton.isSelected() ? 0 : 1;

		        // 입력 유효성 검사
		        if (name.isEmpty() || position.isEmpty() || birthday.isEmpty() || action.isEmpty() || performanceStr.isEmpty()) {
		            javax.swing.JOptionPane.showMessageDialog(null, "모든 필드를 입력해주세요.");
		            return;
		        }

		        int performance = 0;
		        try {
		            performance = Integer.parseInt(performanceStr);
		        } catch (NumberFormatException ex) {
		            javax.swing.JOptionPane.showMessageDialog(null, "출전 시간은 숫자로 입력해주세요.");
		            return;
		        }

		        try {
		            Connection conn = DBUtil.getConnection();
		            String sql = "UPDATE DB2025_Player SET playerName = ?, position = ?, birthday = ?, playerAction = ?, performance = ?, ableToPlay = ? WHERE idPlayer = ?";
		            PreparedStatement pstmt = conn.prepareStatement(sql);
		            pstmt.setString(1, name);
		            pstmt.setString(2, position);
		            pstmt.setString(3, birthday);
		            pstmt.setString(4, action);
		            pstmt.setInt(5, performance);
		            pstmt.setInt(6, ableToPlay);
		            pstmt.setInt(7, playerId);

		            int rows = pstmt.executeUpdate();
		            if (rows > 0) {
		                javax.swing.JOptionPane.showMessageDialog(null, "선수 정보가 성공적으로 업데이트되었습니다.");
		            } else {
		                javax.swing.JOptionPane.showMessageDialog(null, "업데이트 실패: 해당 선수 정보를 찾을 수 없습니다.");
		            }

		            pstmt.close();
		            conn.close();
		        } catch (Exception ex) {
		            ex.printStackTrace();
		            javax.swing.JOptionPane.showMessageDialog(null, "업데이트 중 오류가 발생했습니다.");
		        }
		    }
		});

		loadPlayerid();
	}
	
	private void loadPlayerInfo(int playerId, JToggleButton toggle) {
	    try {
	        Connection conn = DBUtil.getConnection();
	        String sql = "SELECT playerName, birthday, position, idTeam, playerAction, ableToPlay,performance FROM DB2025_Player WHERE idPlayer = ?";
	        PreparedStatement pstmt = conn.prepareStatement(sql);
	        pstmt.setInt(1, playerId);

	        ResultSet rs = pstmt.executeQuery();

	        if (rs.next()) {
	            textField_3.setText(rs.getString("playerName"));     // 이름
	            textField_4.setText(rs.getString("birthday"));       // 생년월일
	            textField_2.setText(rs.getString("position"));       // 포지션
	            textField_5.setText(rs.getString("playerAction"));   // 액션
	            int perfom = rs.getInt("performance");
	            textField.setText(String.valueOf(perfom));


	            int able = rs.getInt("ableToPlay");
	            toggle.setSelected(able == 0); // 0이면 선택되어 있어야 "불가능"
	            toggle.setText(able == 1 ? "가능" : "불가능");
	        }

	        rs.close();
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        javax.swing.JOptionPane.showMessageDialog(null, "선수 정보 로딩 중 오류가 발생했습니다.");
	    }
	}
	
	private void loadPlayerid() {
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

		try {
			  Connection conn = DBUtil.getConnection();
			    String sql = "SELECT idPlayer FROM DB2025_Player WHERE idTeam = ?";
			    PreparedStatement pstmt = conn.prepareStatement(sql);
			    
			    pstmt.setInt(1, idTeam); // idTeam은 메서드 인자나 로컬 변수로 정의되어 있어야 함

			    ResultSet rs = pstmt.executeQuery();

			    while (rs.next()) {
			        int idplayer = rs.getInt("idPlayer");
			        model.addElement(String.valueOf(idplayer));
			    }


		    rs.close();
		    pstmt.close();
		    conn.close();
		} catch (Exception e) {
		    e.printStackTrace();
		    javax.swing.JOptionPane.showMessageDialog(null, "팀 목록 로딩 중 오류가 발생했습니다.");
		}
		comboBox.setModel(model);
		comboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String selected = (String) comboBox.getSelectedItem();
		        if (selected != null && !selected.isEmpty()) {
		            int playerId = Integer.parseInt(selected);
		            loadPlayerInfo(playerId, tglbtnNewToggleButton);
		        }
		    }
		});

		// ✅ 콤보박스에 처음 로딩된 선수가 있을 경우, 자동으로 첫 선수 정보 로드
		if (model.getSize() > 0) {
		    comboBox.setSelectedIndex(0); // 첫 번째 선수 선택
		    String selected = (String) comboBox.getSelectedItem();
		    if (selected != null && !selected.isEmpty()) {
		        int playerId = Integer.parseInt(selected);
		        loadPlayerInfo(playerId, tglbtnNewToggleButton);
		    }
		}
		

	}
	
	

	
}
