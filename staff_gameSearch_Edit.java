package dataKicker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class staff_gameSearch_Edit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;
	private JTextField textField_10;
	private JTextField textField_11;
	private JTextField textField_12;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_gameSearch_Edit frame = new staff_gameSearch_Edit();
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
	public staff_gameSearch_Edit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameSearch().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("경기 기록 수정");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 227, 438, 39);
		contentPane.add(panel_1);
		
		JButton btnNewButton_1 = new JButton("저장");
		panel_1.add(btnNewButton_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(6, 73, 438, 153);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("경기 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();
		panel.add(comboBox);
		
		JLabel lblNewLabel_2 = new JLabel("경기 날짜");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2);
		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_2_1 = new JLabel("상대 팀");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_1);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblNewLabel_2_2 = new JLabel("사용한 필드 전술");
		lblNewLabel_2_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_2);
		
		textField_3 = new JTextField();
		panel.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("사용한 세트피스 전술");
		panel.add(lblNewLabel_3);
		
		textField_4 = new JTextField();
		panel.add(textField_4);
		textField_4.setColumns(10);
		
		JLabel lblNewLabel_2_3 = new JLabel("득점");
		lblNewLabel_2_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_3);
		
		textField_5 = new JTextField();
		panel.add(textField_5);
		textField_5.setColumns(10);
		
		JLabel lblNewLabel_2_4 = new JLabel("실점");
		lblNewLabel_2_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_4);
		
		textField_6 = new JTextField();
		panel.add(textField_6);
		textField_6.setColumns(10);
		
		JLabel lblNewLabel_2_5 = new JLabel("전체 슛팅 수");
		lblNewLabel_2_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_5);
		
		textField_7 = new JTextField();
		panel.add(textField_7);
		textField_7.setColumns(10);
		
		JLabel lblNewLabel_2_6 = new JLabel("유효 슛팅 수");
		lblNewLabel_2_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_6);
		
		textField_8 = new JTextField();
		panel.add(textField_8);
		textField_8.setColumns(10);
		
		JLabel lblNewLabel_2_7 = new JLabel("정확한 패스 수");
		lblNewLabel_2_7.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_7);
		
		textField_9 = new JTextField();
		panel.add(textField_9);
		textField_9.setColumns(10);
		
		JLabel lblNewLabel_2_8 = new JLabel("공격지역 패스 수");
		lblNewLabel_2_8.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_8);
		
		textField_10 = new JTextField();
		panel.add(textField_10);
		textField_10.setColumns(10);
		
		JLabel lblNewLabel_2_9 = new JLabel("수비-가로채기 수");
		lblNewLabel_2_9.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_9);
		
		textField_11 = new JTextField();
		panel.add(textField_11);
		textField_11.setColumns(10);
		
		JLabel lblNewLabel_2_10 = new JLabel("수비 -차단 수");
		lblNewLabel_2_10.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_2_10);
		
		textField_12 = new JTextField();
		panel.add(textField_12);
		textField_12.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("출전 선수 수정");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_gameSearch_EditPlayers().setVisible(true); dispose();
			}
		});
		btnNewButton_2.setBounds(327, 6, 117, 29);
		contentPane.add(btnNewButton_2);
	}
}
