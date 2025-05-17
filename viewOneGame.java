package dataKicker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class viewOneGame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					viewOneGame frame = new viewOneGame();
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
	public viewOneGame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("해당 경기 정보");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 32, 438, 29);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("경기 ID:");
		lblNewLabel_1.setBounds(6, 73, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("경기 날짜:");
		lblNewLabel_1_1.setBounds(6, 101, 61, 16);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("상대 팀:");
		lblNewLabel_1_2.setBounds(6, 129, 61, 16);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("득점:");
		lblNewLabel_1_3.setBounds(6, 157, 61, 16);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("실점:");
		lblNewLabel_1_4.setBounds(6, 185, 61, 16);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("전체 슈팅:");
		lblNewLabel_1_5.setBounds(163, 73, 61, 16);
		contentPane.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_6 = new JLabel("유효 슈팅:");
		lblNewLabel_1_6.setBounds(163, 101, 61, 16);
		contentPane.add(lblNewLabel_1_6);
		
		JLabel lblNewLabel_1_7 = new JLabel("정확한 패스:");
		lblNewLabel_1_7.setBounds(163, 129, 82, 16);
		contentPane.add(lblNewLabel_1_7);
		
		JLabel lblNewLabel_1_7_1 = new JLabel("공격 지역 패스:");
		lblNewLabel_1_7_1.setBounds(163, 157, 82, 16);
		contentPane.add(lblNewLabel_1_7_1);
		
		JLabel lblNewLabel_1_7_2 = new JLabel("수비-가로채기 수:");
		lblNewLabel_1_7_2.setBounds(163, 185, 102, 16);
		contentPane.add(lblNewLabel_1_7_2);
		
		JLabel lblNewLabel_1_7_3 = new JLabel("수비-차단 수:");
		lblNewLabel_1_7_3.setBounds(163, 213, 82, 16);
		contentPane.add(lblNewLabel_1_7_3);
		
		JButton btnNewButton_1 = new JButton("사용 전술 목록");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewTacticsOnGame().setVisible(true); dispose();
			}
		});
		btnNewButton_1.setBounds(295, 73, 117, 29);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("출전 선수 목록");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new viewPlayersOnGame().setVisible(true); dispose();
			}
		});
		btnNewButton_2.setBounds(295, 114, 117, 29);
		contentPane.add(btnNewButton_2);
	}

}
