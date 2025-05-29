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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

//경기 기록을 조회하는 화면입니다. 
public class viewGames extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private JTable table;
	private int idTeam;

	// teamId=1을 넣어서 테스트용 실행 메서드입니다. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewGames frame = new viewGames(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//특정 팀(idTeam)의 경기 기록을 DB에서 조회해서 테이블에 출력합니다. 
	// DB 뷰로 DB2025_view_GameSummary를 사용합니다. 
	private void loadTeamGames() {
		String sql = "SELECT idGame, dateGame,idOurTeam, idAgainstTeam, goalFor, goalAgainst\n"
				+ "FROM DB2025_view_GameSummary \n"
				+ "WHERE idOurTeam = ? ORDER BY dateGame DESC";
		try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {

		        pstmt.setInt(1, idTeam); // 바인딩
		        

		        try (ResultSet rs = pstmt.executeQuery()) {
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		            model.setRowCount(0); // 기존 데이터 삭제

		        	while (rs.next()) {
		            	int gameId = rs.getInt("idGame");
		                Date date = rs.getDate("dateGame");
		                int opponent = rs.getInt("idAgainstTeam");
		                int goalFor = rs.getInt("goalFor");
		                int goalAgainst = rs.getInt("goalAgainst");

				//상대팀이 idTeam으로 잘못 표기된 경우, 역전을 처리하는 기능을 합니다. 
		                if (opponent == idTeam) {
		                	opponent = rs.getInt("idOurTeam");
		                	int temp = goalFor;
		                	goalFor = goalAgainst;
		                	goalAgainst = temp;
		                }
		                
		               

		                model.addRow(new Object[]{gameId, date.toString(), opponent, goalFor, goalAgainst});
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}

	//전체 경기 기록을 조회하는 부분입니다. 조건은 idOurTeam < idAgainstTeam을 해서 중복을 제거합니다.
	private void loadAllGames() {
		String sql = "SELECT idGame, dateGame,idOurTeam, idAgainstTeam, goalFor, goalAgainst\n"
				+ "FROM DB2025_view_GameSummary WHERE idOurTeam<idAgainstTeam";
		try (Connection conn = DBUtil.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql)) {


		        try (ResultSet rs = pstmt.executeQuery()) {
		        	DefaultTableModel model = (DefaultTableModel) table.getModel();
		            model.setRowCount(0); // 기존 데이터 삭제

		        	while (rs.next()) {
		            	int gameId = rs.getInt("idGame");
		                Date date = rs.getDate("dateGame");
		                int team1 = rs.getInt("idOurteam");
		                int team2 = rs.getInt("idAgainstTeam");
		                int goalFor = rs.getInt("goalFor");
		                int goalAgainst = rs.getInt("goalAgainst");

		                model.addRow(new Object[]{gameId, date.toString(),team1, team2, goalFor, goalAgainst});
		            }
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
	}

	//프레임 및 UI 구성합니다.
	public viewGames(int idTeam) {
		this.idTeam=idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		//스크롤 가능한 테이블 패널입니다. 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 92, 438, 174);
		contentPane.add(scrollPane);

		//경기 데이터 출력용 테이블을 초기에 구성합니다. 
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
					"경기 ID", "경기 날짜", "팀1", "팀2", "팀1 득점", "팀2 득점"			}
		));
		scrollPane.setViewportView(table);

		//타이틀 라벨입니다.
		lblNewLabel = new JLabel("경기 기록");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 35, 438, 22);
		contentPane.add(lblNewLabel);
		
		btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"전체 경기 조회", "우리팀 경기 조회"}));
		comboBox.setBounds(6, 64, 170, 27);
		contentPane.add(comboBox);

		loadAllGames();

		//콤보박스 선택 변경 시 테이블을 갱신을 하는 부분입니다. 
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//"우리팀 경기 조회"선택하는 부분입니다.
				if (comboBox.getSelectedIndex() == 1) { // "우리 팀 경기 조회" 선택 시
					String[] columnNames = { "경기 ID", "경기 날짜", "상대 팀", "득점", "실점" };
		            DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 빈 모델
		            table.setModel(model); // JTable에 모델 적용
					loadTeamGames();
				}
				//"전체 경기 조회" 선택하는 부분입니다. 
				if (comboBox.getSelectedIndex() == 0) {
					String[] columnNames = { "경기 ID", "경기 날짜", "팀1", "팀2", "팀1 득점", "팀2 득점" };
		            DefaultTableModel model = new DefaultTableModel(columnNames, 0); // 빈 모델
		            table.setModel(model); // JTable에 모델 적용
					loadAllGames();
				}
			}
		});
	}
}
