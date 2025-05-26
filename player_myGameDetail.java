package DB2025Team09;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;

public class player_myGameDetail extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lblGameId, lblDate, lblOpponent, lblGoalFor, lblGoalAgainst;
	private JLabel lblShots, lblOnTarget, lblAccPass, lblAttackPass, lblIntercept, lblBlocking;
	private int idTeam, idPlayer, idGame;
	//테스트용 메인 함수입니다.
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				player_myGameDetail frame = new player_myGameDetail(1,1,1);
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public player_myGameDetail(int idTeam, int idPlayer, int idGame) {
		//선수 메뉴
		//2. 경기 기록 조회
		//2-2내가 출전한 경기의 상세 기록 조회
		this.idGame = idGame;
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
		//뒤로 가기 버튼, player_myGameOne 으로 이동합니다.
		JButton btnNewButton = new JButton("Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new player_myGameOne( idTeam, idPlayer, idGame).setVisible(true);
				dispose();
			}
		});
		btnNewButton.setBounds(6, 6, 117, 29);
		contentPane.add(btnNewButton);

		// 라벨 제목
		String[] labels = { "경기ID:", "경기 날짜:", "상대 팀:", "득점:", "실점:", "전체 슈팅 수:", "유효 슈팅 수:", 
		                    "정확한 패스 수:", "공격 지역 패스 수:", "수비-가로채기 수:", "수비-차단 수:" };
		int[] x = { 6, 6, 6, 6, 6, 184, 184, 184, 184, 184, 184 };
		int[] y = { 74, 102, 130, 158, 186, 74, 102, 130, 158, 186, 214 };

		for (int i = 0; i < labels.length; i++) {
			JLabel label = new JLabel(labels[i]);
			label.setBounds(x[i], y[i], 150, 16);
			contentPane.add(label);
		}

		// 값 표시용 라벨들 초기화 및 위치 설정
		lblGameId = new JLabel("");
		lblGameId.setBounds(70, 74, 100, 16);
		contentPane.add(lblGameId);

		lblDate = new JLabel("");
		lblDate.setBounds(70, 102, 100, 16);
		contentPane.add(lblDate);

		lblOpponent = new JLabel("");
		lblOpponent.setBounds(70, 130, 100, 16);
		contentPane.add(lblOpponent);

		lblGoalFor = new JLabel("");
		lblGoalFor.setBounds(70, 158, 100, 16);
		contentPane.add(lblGoalFor);

		lblGoalAgainst = new JLabel("");
		lblGoalAgainst.setBounds(70, 186, 100, 16);
		contentPane.add(lblGoalAgainst);

		lblShots = new JLabel("");
		lblShots.setBounds(300, 74, 100, 16);
		contentPane.add(lblShots);

		lblOnTarget = new JLabel("");
		lblOnTarget.setBounds(300, 102, 100, 16);
		contentPane.add(lblOnTarget);

		lblAccPass = new JLabel("");
		lblAccPass.setBounds(300, 130, 100, 16);
		contentPane.add(lblAccPass);

		lblAttackPass = new JLabel("");
		lblAttackPass.setBounds(300, 158, 100, 16);
		contentPane.add(lblAttackPass);

		lblIntercept = new JLabel("");
		lblIntercept.setBounds(300, 186, 100, 16);
		contentPane.add(lblIntercept);

		lblBlocking = new JLabel("");
		lblBlocking.setBounds(300, 214, 100, 16);
		contentPane.add(lblBlocking);

		loadGameDetail(); // 경기 데이터 로드
	}

	private void loadGameDetail() {
		
		//출전 경기 세부 통계를 쿼리를 통해 불러오는 메서드 입니다.
		//2-2 내가 출전한 경기의 상세 기록 조회
		
		String sql = """
		    SELECT GIA.*, T.nation AS opponentTeamName
		    FROM DB2025_Game_Info_All GIA
		    JOIN DB2025_Team T ON GIA.idAgainstTeam = T.idTeam
		    WHERE GIA.idGame = ?
		""";

		try (Connection conn = DBUtil.getConnection();
		     PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, idGame); 

			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					lblGameId.setText(String.valueOf(idGame));
					lblDate.setText(rs.getDate("dateGame").toString());
					lblOpponent.setText(rs.getString("opponentTeamName"));
					lblGoalFor.setText(String.valueOf(rs.getInt("goalFor")));
					lblGoalAgainst.setText(String.valueOf(rs.getInt("goalAgainst")));
					lblShots.setText(String.valueOf(rs.getInt("allShots")));
					lblOnTarget.setText(String.valueOf(rs.getInt("shotOnTarget")));
					lblAccPass.setText(String.valueOf(rs.getInt("accPass")));
					lblAttackPass.setText(String.valueOf(rs.getInt("attackPass")));
					lblIntercept.setText(String.valueOf(rs.getInt("intercept")));
					lblBlocking.setText(String.valueOf(rs.getInt("blocking")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
