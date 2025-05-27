package DB2025Team09;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//실적 기준 선수 검색 화면을 띄웁니다. 
public class staff_playerSearchPerform extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam;

	//어플리케이션 실행을 위한 메인 메서드입니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerSearchPerform frame = new staff_playerSearchPerform(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//팀 ID를 받아서 해당 팀의 선수들 중 실적 기준으로 검색하는 생성자입니다. 
	public staff_playerSearchPerform(int idTeam) {
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
				new staff_playerSearchTypes(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//제목 라벨입니다. 
		JLabel lblNewLabel = new JLabel("실적에 따른 선수 검색");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 39, 438, 29);
		contentPane.add(lblNewLabel);

		//실적 입력 필드입니다. 
		JTextField textField = new JTextField();
		textField.setBounds(57, 80, 305, 26);
		contentPane.add(textField);
		textField.setColumns(10);
	
		//검색 버튼입니다. 
		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setBounds(362, 80, 56, 29);
		contentPane.add(btnNewButton_1);

		//검색 버튼 클릭 이벤트 처리합니다. 
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					//사용자 입력값 확인합니다.
					String input = textField.getText().trim();
					if (input.isEmpty()) {
						return;
					}
					int minPerformance = Integer.parseInt(input);//실적 최소값입니다.

					//테이블 초기화합니다.
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					model.setRowCount(0); // 기존 데이터 초기화

					//DB에서 실적 기준 선수 검색 쿼리입니다. 
					String query = "SELECT idPlayer, playerName, performance, position, birthday, ableToPlay, playerAction " +
					               "FROM DB2025_Player WHERE performance >= ? AND idTeam = ?";

					try (Connection conn = DBUtil.getConnection();
					     PreparedStatement pstmt = conn.prepareStatement(query)) {

						pstmt.setInt(1, minPerformance);
						pstmt.setInt(2, idTeam);
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
					ex.printStackTrace();//에러 출력합니다.
				}
			}
		});

		//테이블 스크롤 영역 생성합니다. 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 118, 438, 148);
		contentPane.add(scrollPane);

		//검색 결과 출력용 테이블입니다.
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC120\uC218 ID", "\uC774\uB984", "\uC2E4\uC801", "\uD3EC\uC9C0\uC158", "\uC0DD\uB144\uC6D4\uC77C", "\uCD9C\uC804 \uAC00\uB2A5 \uC5EC\uBD80", "\uC561\uC158"
			}
		));
		scrollPane.setViewportView(table);
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
	}

}
