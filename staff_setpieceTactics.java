package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JComboBox;

public class staff_setpieceTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnNewButton_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_setpieceTactics frame = new staff_setpieceTactics();
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
	public staff_setpieceTactics() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_tacticManage(DKicker.currentTeamId).setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("세트피스 전술 관리");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 24, 438, 29);
		contentPane.add(lblNewLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 55, 438, 124);
		contentPane.add(scrollPane);
		
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"\uC138\uD2B8\uD53C\uC2A4 \uC804\uC220 ID", "\uC804\uC220 \uC774\uB984", "\uD3EC\uBA54\uC774\uC158", "\uC124\uBA85"
			}
		));
		scrollPane.setViewportView(table);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 183, 438, 83);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 0, 426, 60);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel_4 = new JLabel("세트피스 전술 ID");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		panel_1.add(lblNewLabel_4);
		
		JComboBox comboBox = new JComboBox();
		panel_1.add(comboBox);
		
		JLabel lblNewLabel_5 = new JLabel("전술 이름");
		lblNewLabel_5.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_5);
		
		JTextField textField_5 = new JTextField();
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("포메이션");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_6.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		panel_1.add(lblNewLabel_6);
		
		JTextField textField_6 = new JTextField();
		panel_1.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("설명");
		lblNewLabel_7.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		lblNewLabel_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_7);
		
		JTextField textField_7 = new JTextField();
		panel_1.add(textField_7);
		textField_7.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 61, 426, 16);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));
		
		btnNewButton_3 = new JButton("수정");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_setpieceTactics_Edit().setVisible(true); dispose();
			}
		});
		panel_2.add(btnNewButton_3);
		
		JButton btnNewButton_1 = new JButton("삭제");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
						            // 여기에서 삭제 작업 실행
						            System.out.println("삭제됨");
						            // 예: listModel.remove(index);
						        } else {
						            System.out.println("삭제 취소됨");
						        }
					}
				});
				panel.add(btnNewButton_2);
			}
		});
		panel_2.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("신규 추가");
		panel_2.add(btnNewButton_2);
	}

}
