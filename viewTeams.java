package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

// viewTeams 클래스는 전체 팀 목록(국가대표팀)의 기본 정보를 테이블로 보여주는 화면입니다. DB에서 팀 ID, 국가, 현재 대회 이름, FIFA 랭킹을 조회해 출력합니다.
public class viewTeams extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table;


	//독립 실행 테스트용 메인 메서드입니다. 
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewTeams frame = new viewTeams(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	//idTeam을 매개변수로 받는 생성자입니다. 
	public viewTeams(int idTeam) {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 64, 438, 202);
		contentPane.add(scrollPane);

		// 테이블 초기화: 열 정의 (팀 ID, 국가, 현재 대회, FIFA 랭킹)
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uD300 ID", "\uAD6D\uAC00", "현재 대회 이름", "FIFA \uB7AD\uD0B9"}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblNewLabel = new JLabel("팀 정보");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 21);
		contentPane.add(lblNewLabel);

		// 뒤로가기 버튼: 스태프 메인화면으로 돌아감
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff(idTeam).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		loadTeamData();
	}

	// DB에서 전체 팀 목록을 조회하여 JTable에 로드하는 함수입니다.	
	private void loadTeamData() {
	    DefaultTableModel model = (DefaultTableModel) table.getModel();
	    model.setRowCount(0); // 기존 행 삭제

	    String query = "SELECT idTeam AS id, currName AS name, nation, FIFArank FROM DB2025_Team";

	    try (Connection conn = DBUtil.getConnection();
	         java.sql.Statement stmt = conn.createStatement();
	         ResultSet rs = stmt.executeQuery(query)) {

	        while (rs.next()) {
	            Object[] row = {
	                rs.getInt("id"),
	                rs.getString("nation"),
	                rs.getString("name"),
	                rs.getInt("FIFArank")
	            };
	            model.addRow(row);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(this, "팀 정보를 불러오지 못했습니다.");
	    }
	}

}
