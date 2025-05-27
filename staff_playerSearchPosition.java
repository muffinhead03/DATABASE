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
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//포지션 기준으로 팀 내 선수를 검색하는 UI 클래스입니다. 
public class staff_playerSearchPosition extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;//포지션 입력 필드입니다.
	private JTable table;// 결과 출력 테이블입니다.
	private int idTeam;//현재 로그인한 스태프의 팀 ID입니다.

	//애플리케이션 실행용 main 메서드입니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerSearchPosition frame = new staff_playerSearchPosition(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//생성자 : 팀 ID를 받아 포지션 검색 UI를 구성합니다. 
	public staff_playerSearchPosition(int idTeam) {
		this.idTeam = idTeam;

		//기본 프레임 설정합니다. 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		//패널 생성 및 레이아웃 설정합니다.
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		//뒤로가기 버튼입니다. 
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_playerSearchTypes(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//타이틀입니다. 
		JLabel lblNewLabel = new JLabel("포지션에 따른 선수 검색");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 39, 438, 29);
		contentPane.add(lblNewLabel);

		//포지션 입력 필드입니다. 
		textField = new JTextField();
		textField.setBounds(57, 80, 305, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		//검색 버튼입니다. 
		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setBounds(362, 80, 56, 29);
		contentPane.add(btnNewButton_1);

		//검색 버튼 클릭 이벤트입니다. 
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String positionInput = textField.getText().trim(); // 포지션을 입력받는다. 

					//테이블 모델 초기화를 합니다.
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setRowCount(0); // 초기화

					// SQL 쿼리를 구성합니다.
					String query = "SELECT idPlayer, playerName, performance, position, birthday, ableToPlay, playerAction " +
					               "FROM DB2025_Player WHERE idTeam = ? ";
					
					//포지션이 입력되었을 경우 WHERE 절에 조건 추가합니다.
					boolean hasPosition = !positionInput.isEmpty();
					if (hasPosition) {
						query += " AND position = ?";
					}

					//DB 연결 및 쿼리 실행합니다. 
					try (Connection conn = DBUtil.getConnection();
					     PreparedStatement pstmt = conn.prepareStatement(query)) {

						pstmt.setInt(1, idTeam);
						pstmt.setString(2, textField.getText().trim());

						if (hasPosition) {
							pstmt.setString(2, positionInput);
						}

						ResultSet rs = pstmt.executeQuery();

						//결과 테이블에 추가합니다. 
						while (rs.next()) {
							Object[] row = {
								rs.getInt("idPlayer"),
								rs.getString("playerName"),
								rs.getInt("performance"),
								rs.getString("position"),
								rs.getDate("birthday"),
								rs.getInt("ableToPlay") == 1 ? "가능" : "불가능",
								rs.getString("playerAction")
							};
							model.addRow(row);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();//예외 출력합니다. 
				}
			}
		});

		//테이블 스크롤 영역입니다. 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 118, 438, 148);
		contentPane.add(scrollPane);

		//선수 목록 출력 테이블입니다. 
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"선수 ID", "이름", "실적", "포지션", "생년월일", "출전 가능 여부", "액션"
			}
		));
		scrollPane.setViewportView(table);
		

	}
	
}
