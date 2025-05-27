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

	//생성자로 팀 ID를 받아 UI및 이벤트를 구성합니다.
	public staff_playerSearchAvailable(int idTeam) {
		this.idTeam = idTeam;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBound분)입니다. 
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
