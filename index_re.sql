CREATE VIEW view_GameSummary 
AS SELECT s1.idGame, r.dateGame, s1.idOurTeam, s2.idOurTeam AS idAgainstTeam,
 s1.idField, s1.idSetpiece, s1.goalOurTeam AS goalFor, s2.goalOurTeam AS goalAgainst 
FROM DB2025_GameStat s1 
JOIN DB2025_GameStat s2 ON s1.idGame = s2.idGame AND s1.idOurTeam <> s2.idOurTeam 
JOIN DB2025_GameRec r ON s1.idGame = r.idGame;

CREATE VIEW DB2025_Game_Players_List (idGame, idPlayer, playerName, position, playTime)
AS SELECT sq.idGame, sq.idPlayer, pl.playerName, pl.position, sq.playTime
FROM DB2025_Squad sq, DB2025_Player pl
WHERE sq.idPlayer = pl.idPlayer;

CREATE VIEW DB2025_Tactics_in_Game (idGame, dateGame, goalFor, goalAgainst, fieldName, fieldFormation, setpieceName, setpieceFormation)
AS SELECT gs.idGame, gr.dateGame, gs.goalOurTeam AS goalFor,
 gs_op.goalOurTeam AS goalAgainst, tf.tacticName AS fieldName,
 tf.tacticFormation AS fieldFormation, ts.tacticName AS setpieceName,
 ts.tacticFormation AS setpieceFormation 
 FROM DB2025_GameStat gs JOIN DB2025_GameRec gr ON gs.idGame = gr.idGame 
 JOIN DB2025_GameStat gs_op ON gs.idGame = gs_op.idGame AND gs.idOurTeam <> gs_op.idOurTeam 
 LEFT JOIN DB2025_Tactics tf ON gs.idField = tf.idTactic AND tf.tacticType = 'Field' 
 LEFT JOIN DB2025_Tactics ts ON gs.idSetpiece = ts.idTactic AND ts.tacticType = 'Setpiece';

CREATE VIEW DB2025_Game_Info_All (idGame, dateGame, idOurTeam, idAgainstTeam, goalFor,
 goalAgainst, allShots,shotOnTarget,accPass, attackPass,intercept, blocking)
AS SELECT s1.idGame, r.dateGame, s1.idOurTeam, s2.idOurTeam AS idAgainstTeam,
 s1.goalOurTeam AS goalFor, s2.goalOurTeam AS goalAgainst, s1.allShots,
 s1.shotOnTarget, s1.accPass, s1.attackPass, s1.intercept, s1.blocking 
FROM DB2025_GameStat s1 
JOIN DB2025_GameStat s2 ON s1.idGame = s2.idGame AND s1.idOurTeam <> s2.idOurTeam 
JOIN DB2025_GameRec r ON s1.idGame = r.idGame;


CREATE INDEX DB2025_idx_Player_position
ON DB2025_Player(position ASC);

# 인덱스 재구성
ANALYZE TABLE DB2025_Player;

CREATE INDEX DB2025_idx_Player_ableToPlay
ON DB2025_Player(ableToPlay DESC);

# 인덱스 재구성
ANALYZE TABLE DB2025_Player;

CREATE INDEX DB2025_idx_Player_performance
ON DB2025_Player(performance DESC);

# 인덱스 재구성
ANALYZE TABLE DB2025_Player;

CREATE INDEX idx_GameRec_idTeam1 ON DB2025_GameRec(idTeam1); 
ANALYZE TABLE DB2025_GameRec;
CREATE INDEX idx_GameRec_idTeam2 ON DB2025_GameRec(idTeam2);
ANALYZE TABLE DB2025_GameRec;
CREATE INDEX idx_GameStat_idOurTeam ON DB2025_GameStat(idOurTeam);
ANALYZE TABLE DB2025_GameStat; 
CREATE INDEX idx_GameStat_idField ON DB2025_GameStat(idField); 
ANALYZE TABLE DB2025_GameStat;
CREATE INDEX idx_GameStat_idSetpiece ON DB2025_GameStat(idSetpiece);
ANALYZE TABLE DB2025_GameStat;
