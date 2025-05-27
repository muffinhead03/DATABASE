package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.GridLayout;

//코치가 경기 기록을 검색하고, 해당 기록을 수정하거나 삭제할 수 있는 화면입니다. 
public class staff_gameSearch extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_gameSearch frame = new staff_gameSearch();
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
	public staff_gameSearch() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameManage(DKicker.currentTeamId).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//타이틀입니다. 
		JLabel lblNewLabel = new JLabel("경기 기록 검색");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 29, 438, 29);
		contentPane.add(lblNewLabel);

		//검색어 입력 필드입니다. 
		textField = new JTextField();
		textField.setBounds(33, 70, 325, 26);
		contentPane.add(textField);
		textField.setColumns(10);

		//검색 버튼입니다. 
		JButton btnNewButton_1 = new JButton("검색");
		btnNewButton_1.setBounds(352, 70, 65, 29);
		contentPane.add(btnNewButton_1);

		// 검색 결과 테이블을 표시할 스크롤 영역입니다. 
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 108, 384, 129);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);// table은 향후 DB 검색 결과를 담을 예정

		// 하단 수정/삭제 버튼 영역입니다. 
		JPanel panel = new JPanel();
		panel.setBounds(33, 249, 384, 17);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		// 경기 수정 버튼 (선택된 경기의 수정을 위한 화면으로 이동합니다)입니다.
		JButton btnNewButton_3 = new JButton("수정");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameSearch_Edit().setVisible(true); dispose();
			}
		});
		panel.add(btnNewButton_3);

		// 경기 삭제 버튼 (삭제 여부를 확인하는 창을 띄우고, 실제 삭제는 추후 구현 예정입니다)입니다.
		JButton btnNewButton_2 = new JButton("삭제");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showOptionDialog(
					    null,
					    "정말 삭제하시겠습니까?",
					    " ",
					    JOptionPane.YES_NO_OPTION,
					    JOptionPane.PLAIN_MESSAGE,
					    null,
					    null,
					    null
					);

				        if (result == JOptionPane.YES_OPTION) {
				            // 추후: 선택된 행의 경기 기록을 DB에서 삭제하는 기능이 추가되어야 합니다.
				            System.out.println("삭제됨");
				           
				        } else {
				            System.out.println("삭제 취소됨");
				        }
			}
		});
		panel.add(btnNewButton_2);
	}

}
