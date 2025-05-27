package dataKicker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

// 기존의 전술 ID를 선택하고, 이름/포메이션/설명을 수정하여 저장하는 기능을 제공합니다.
public class staff_fieldTactics_Edit extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	//입력 필드들입니다.
	private JTextField textField_1; // 전술 이름 입력
	private JTextField textField_2; // 포메이션 입력
	private JTextField textField_3; // 설명 입력력

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					staff_fieldTactics_Edit frame = new staff_fieldTactics_Edit();
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
	public staff_fieldTactics_Edit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		//뒤로가기 버튼으로, 수정 없이 필드 전술 목록(staff_fieldTactics())으로 돌아갑니다.
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new staff_fieldTactics().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		//타이틀 라벨 : 화면 상단 중앙 제목을 구성합니다.
		JLabel lblNewLabel = new JLabel("필드 전술 수정");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 29);
		contentPane.add(lblNewLabel);

		//전술 정보 입력 패널1(ID, 이름, 포메이션) 등을 입력받을 수 있습니다.
		JPanel panel = new JPanel();
		panel.setBounds(6, 75, 438, 80);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 4, 0, 0));

		//필드 1 - 전술 ID를 선택할 수 있습니다.
		JLabel lblNewLabel_1 = new JLabel("필드전술 ID");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox();// 전술 ID 목록에서 선택합니다
		panel.add(comboBox);//이 콤보박스는 DB에서 불러온 idTactic 값을 목록으로 제공해야 합니다.

		//필드 2 - 전술 이름을 입력할 수 있습니다.
		JLabel lblNewLabel_1_1 = new JLabel("전술 이름"); 
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_1);

		
		textField_1 = new JTextField();
		panel.add(textField_1);
		textField_1.setColumns(10);

		//필드 3 - 포메이션을 입력할 수 있습니다.
		JLabel lblNewLabel_1_2 = new JLabel("포메이션");
		lblNewLabel_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lblNewLabel_1_2);
		
		textField_2 = new JTextField();
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(6, 161, 438, 68);
		contentPane.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 2, 0, 0));

		//필드 4 - 전술에 대한 설명이 나오는 곳입니다. 
		JLabel lblNewLabel_1_3 = new JLabel("전술 설명");
		lblNewLabel_1_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel_1_3);
		
		textField_3 = new JTextField();
		panel_1.add(textField_3);
		textField_3.setColumns(10);

		//저장 버튼 패널입니다.(수정 내용을 DB에 반영하는 곳)
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(6, 229, 438, 37);
		contentPane.add(panel_2);
		
		JButton btnNewButton_1 = new JButton("저장");
		panel_2.add(btnNewButton_1);
	}
}
