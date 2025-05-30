package DB2025Team09;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

//출전 가능 여부에 따른 선수 검색 화면
public class staff_playerSearchAvailable extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;
	private int idTeam; // 현재 사용자의 팀 ID입니다.

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_playerSearchAvailable frame = new staff_playerSearchAvailable(1);
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
	//생성자로 팀 ID를 받아 UI및 이벤트를 구성합니다.
	public staff_playerSearchAvailable(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
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
		
		JLabel lblNewLabel = new JLabel("출전 가능 여부에 따른 선수 검색");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 39, 438, 29);
		contentPane.add(lblNewLabel);
		
		// 라디오 버튼 생성
		JRadioButton radioAvailable = new JRadioButton("출전 가능");
		radioAvailable.setBounds(57, 80, 100, 23);
		contentPane.add(radioAvailable);

		JRadioButton radioUnavailable = new JRadioButton("출전 불가능");
		radioUnavailable.setBounds(160, 80, 120, 23);
		contentPane.add(radioUnavailable);

		// 버튼 그룹 (둘 중 하나만 선택되도록)
		ButtonGroup group = new ButtonGroup();
		group.add(radioAvailable);
		group.add(radioUnavailable);
		
		//라디오 버튼 중 하나 고르고 검색 버튼 선택 시 결과가 표시됩니다.
		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setBounds(362, 80, 56, 29);
		contentPane.add(btnNewButton_1);

		btnNewButton_1.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		try {
			Integer ableValue = null;
			if (radioAvailable.isSelected()) {
				ableValue = 1;
			} else if (radioUnavailable.isSelected()) {
				ableValue = 0;
			} else {
				// 아무것도 선택 안 한 경우
				return;
			}

			DefaultTableModel model = (DefaultTableModel) table.getModel();
			model.setRowCount(0); // 초기화
			// 쿼리문
			String query = "SELECT idPlayer, playerName, ableToPlay, position, birthday, performance, playerAction " +
			               "FROM DB2025_Player WHERE ableToPlay = ? AND idTeam = ?";

			try (Connection conn = DBUtil.getConnection();
			     PreparedStatement pstmt = conn.prepareStatement(query)) {
				pstmt.setInt(1, ableValue);
				pstmt.setInt(2, idTeam); //바인딩

				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					// 테이블에 데이터 넣는 과정
					Object[] row = {
						rs.getInt("idPlayer"),
						rs.getString("playerName"),
						rs.getInt("ableToPlay") == 1 ? "가능" : "불가능",
						rs.getString("position"),
						rs.getDate("birthday"),
						rs.getInt("performance"),
						rs.getString("playerAction")
					};
					model.addRow(row);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
});


		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 118, 438, 148);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC120\uC218 ID", "\uC774\uB984", "\uCD9C\uC804 \uAC00\uB2A5 \uC5EC\uBD80", "\uD3EC\uC9C0\uC158", "\uC0DD\uB144\uC6D4\uC77C", "\uC2E4\uC801", "\uC561\uC158"
			}
		));
		scrollPane.setViewportView(table);
	}

}