package dataKicker;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class player_myGameTactics extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					player_myGameTactics frame = new player_myGameTactics();
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
	public player_myGameTactics() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myGameOne().setVisible(true); dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel = new JLabel("경기에 사용된 전술 정보");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Lucida Grande", Font.BOLD, 18));
		lblNewLabel.setBounds(6, 34, 438, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("경기ID:");
		lblNewLabel_1.setBounds(6, 68, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("경기 날짜:");
		lblNewLabel_1_1.setBounds(6, 96, 61, 16);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("상대 팀:");
		lblNewLabel_1_2.setBounds(6, 124, 61, 16);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_1_3 = new JLabel("득점:");
		lblNewLabel_1_3.setBounds(6, 152, 61, 16);
		contentPane.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_4 = new JLabel("실점:");
		lblNewLabel_1_4.setBounds(6, 180, 61, 16);
		contentPane.add(lblNewLabel_1_4);
		
		JLabel lblNewLabel_1_5 = new JLabel("필드 전술 이름:");
		lblNewLabel_1_5.setBounds(171, 68, 87, 16);
		contentPane.add(lblNewLabel_1_5);
		
		JLabel lblNewLabel_1_5_1 = new JLabel("필드 전술 포메이션:");
		lblNewLabel_1_5_1.setBounds(171, 96, 108, 16);
		contentPane.add(lblNewLabel_1_5_1);
		
		JLabel lblNewLabel_1_5_1_1 = new JLabel("세트피스 전술 이름:");
		lblNewLabel_1_5_1_1.setBounds(171, 124, 108, 16);
		contentPane.add(lblNewLabel_1_5_1_1);
		
		JLabel lblNewLabel_1_5_1_1_1 = new JLabel("세트피스 전술 포메이션:");
		lblNewLabel_1_5_1_1_1.setBounds(171, 152, 131, 16);
		contentPane.add(lblNewLabel_1_5_1_1_1);
	}

}
