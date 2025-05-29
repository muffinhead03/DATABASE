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
import java.sql.Statement;
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
	private int idTeam, idPlayer;
	private JLabel lblMyPosition;

	/**
	 * Launch the application.
	 */
	//테스트용 메인 함수 입니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player_viewPlayers frame = new player_viewPlayers(1,1);
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
	public player_viewPlayers(int idTeam, int idPlayer) {
		//공통 메뉴
		//1. 선수 정보 조회
		//UI 관련 코드입니다.
		this.idTeam = idTeam;
		this.idPlayer = idPlayer;
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
		//뒤로 가기 버튼
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player(idTeam, idPlayer
).setVisible(true); dispose();
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
				"\uC120\uC218 ID", "\uC774\uB984", "\uD3EC\uC9C0\uC158", "액션","실적"
			}
			//테이블 열 이름
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
		comboBoxSort.setModel(new DefaultComboBoxModel<>(new String[] {"번호 순","이름 가나다순","액션", "최고 실적순"}));
		comboBoxSort.setBounds(313, 69, 131, 27);
		contentPane.add(comboBoxSort);
		
		JLabel lblNewLabel_3 = new JLabel("소속 팀");
		lblNewLabel_3.setBounds(122, 73, 39, 16);
		contentPane.add(lblNewLabel_3);
		
		comboBoxTeam = new JComboBox<>();

		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
		
		// 존재하는 팀의 id를 전부 쿼리해서 콤보박스에 넣는 코드
		
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
		
		JLabel lblNewLabel_4 = new JLabel("나의 포지션: ");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel_4.setBounds(200, 6, 90, 16);
		contentPane.add(lblNewLabel_4);
		
		lblMyPosition = new JLabel("불러오는 중...");
		lblMyPosition.setHorizontalAlignment(SwingConstants.LEFT);
		lblMyPosition.setBounds(290,6,100,16);
		contentPane.add(lblMyPosition);
		
		comboBoxPosition.addActionListener(e -> loadPlayerData());
		comboBoxSort.addActionListener(e -> loadPlayerData());
		comboBoxTeam.addActionListener(e -> loadPlayerData());
		
		loadPlayerData();
		loadMyPosition();
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
	private void loadMyPosition(){
		// 1-4 선수 목록을 포지션 별로 조회
		//포지션 목록에서 포지션 선택 시 포지션에 해당하는 선수들의 상세 정보 목록을 조회한다.
		try(Connection conn = DBUtil.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("SELECT position FROM db2025_player WHERE idPlayer = ?")){
			//바인딩
			pstmt.setInt(1, idPlayer);
			ResultSet rs = pstmt.executeQuery();
			
			if (rs.next()) {
				String position = rs.getString("position");
				lblMyPosition.setText(position);
			} else {
				lblMyPosition.setText("알수 없음");
			}
			rs.close();
		
	} catch (Exception e) {
		e.printStackTrace();
		lblMyPosition.setText("오류");
	}
		}
}
