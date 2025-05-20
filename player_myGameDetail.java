package DB2025Team09;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class player_myGameDetail extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player_myGameDetail frame = new player_myGameDetail();
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
	public player_myGameDetail() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("경기 상세 기록");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(6, 34, 438, 28);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myGameOne().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("경기ID:");
		lblNewLabel_1.setBounds(6, 74, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("경기 날짜:");
		lblNewLabel_1_1.setBounds(6, 102, 61, 16);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("상대 팀:");
		lblNewLabel_1_2.setBounds(6, 130, 61, 16);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_2_1 = new JLabel("득점:");
		lblNewLabel_1_2_1.setBounds(6, 158, 61, 16);
		contentPane.add(lblNewLabel_1_2_1);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("실점:");
		lblNewLabel_1_2_2.setBounds(6, 186, 61, 16);
		contentPane.add(lblNewLabel_1_2_2);
		
		JLabel lblNewLabel_1_2_2_1 = new JLabel("전체 슈팅 수:");
		lblNewLabel_1_2_2_1.setBounds(184, 74, 77, 16);
		contentPane.add(lblNewLabel_1_2_2_1);
		
		JLabel lblNewLabel_1_2_2_1_1 = new JLabel("유효 슈팅 수:");
		lblNewLabel_1_2_2_1_1.setBounds(184, 102, 77, 16);
		contentPane.add(lblNewLabel_1_2_2_1_1);
		
		JLabel lblNewLabel_1_2_2_1_1_1 = new JLabel("정확한 패스 수:");
		lblNewLabel_1_2_2_1_1_1.setBounds(184, 130, 87, 16);
		contentPane.add(lblNewLabel_1_2_2_1_1_1);
		
		JLabel lblNewLabel_1_2_2_1_1_1_1 = new JLabel("공격 지역 패스 수:");
		lblNewLabel_1_2_2_1_1_1_1.setBounds(184, 158, 99, 16);
		contentPane.add(lblNewLabel_1_2_2_1_1_1_1);
		
		JLabel lblNewLabel_1_2_2_1_1_1_1_1 = new JLabel("수비-가로채기 수:");
		lblNewLabel_1_2_2_1_1_1_1_1.setBounds(184, 186, 99, 16);
		contentPane.add(lblNewLabel_1_2_2_1_1_1_1_1);
		
		JLabel lblNewLabel_1_2_2_1_1_1_1_1_1 = new JLabel("수비-차단 수:");
		lblNewLabel_1_2_2_1_1_1_1_1_1.setBounds(184, 214, 99, 16);
		contentPane.add(lblNewLabel_1_2_2_1_1_1_1_1_1);
	}

}
