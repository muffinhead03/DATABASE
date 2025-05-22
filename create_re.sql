DROP DATABASE IF EXISTS DB2025Team09;

DROP USER IF EXISTS DB2025Team09@localhost;

CREATE USER DB2025Team09@localhost identified by 'DB2025Team09';

CREATE DATABASE DB2025Team09;

GRANT all privileges on DB2025Team09.* to DB2025Team09@localhost with grant option;
FLUSH PRIVILEGES;

commit;

USE DB2025Team09;


/*
	## Create 'DB2025_Team' Table
	# Purpose: Stores national team information.	
	
	includes:
	- team ID --Primary Key
	- nation name -- NOT NULL
	- FIFA ranking -- NOT NULL
	- current game-related.
*/
CREATE TABLE DB2025_Team(
idTeam INTEGER PRIMARY KEY,
nation varchar(30) NOT NULL,
FIFArank INTEGER NOT NULL,
currName varchar(50),
currRank INTEGER,
currPoints INTEGER
);

/*
	## Create 'DB2025_Player' Table
	# Purpose: Stores individual player information.
	
	includes:
	- player ID --Primary Key
	- name
	- position
	- birth date -- NOT NULL
	- team --Foreign Key
	- play eligibility
	- action status.
*/
CREATE TABLE DB2025_Player(
idPlayer INTEGER PRIMARY KEY,
playerName varchar(20),
position varchar(10),
birthday date NOT NULL,
idTeam INTEGER,
ableToPlay TINYINT,
performance INTEGER,
playerAction varchar(20),
FOREIGN KEY(idTeam) REFERENCES DB2025_Team(idTeam) ON DELETE SET NULL
);

/*
	## Create 'DB2025_Tactics' Table
	# Purpose: Stores tactical information.
 	
 	includes:
 	- tactic ID --Primary Key
 	- team ID
 	- tactic type(Field or Setpiece) -- NOT NULL
 	- name -- NOT NULL
 	- formation -- NOT NULL
 	- description -- NOT NULL
 	- usability status -- NOT NULL
*/
CREATE TABLE DB2025_Tactics(
idTactic INTEGER PRIMARY KEY,
idTeam INTEGER NOT NULL,
tacticType varchar(10) NOT NULL,
tacticName  varchar(50) NOT NULL,
tacticFormation varchar(50) NOT NULL,
explainTactics varchar(100) NOT NULL,
ableToTactic TINYINT NOT NULL,
FOREIGN KEY(idTeam) REFERENCES DB2025_Team(idTeam) ON DELETE CASCADE
);

/*
	## Create 'DB2025_GameRec' Table
	# Purpose: Stores basic game information.
	
	includes
	- game ID --Primary Key
	- date -- NOT NULL
	- our/opponent team IDs -- NOT NULL, Foreign Key
	- applied tactics(Field/Setpiece) --Foreign Key
	- score date(goals for/against)
*/
CREATE TABLE DB2025_GameRec (
    idGame INTEGER PRIMARY KEY,
    dateGame DATE NOT NULL,
    idTeam1 INTEGER NOT NULL,
    idTeam2 INTEGER NOT NULL,
    CHECK (idTeam1 < idTeam2),
    FOREIGN KEY (idTeam1) REFERENCES DB2025_Team(idTeam) ON DELETE CASCADE,
    FOREIGN KEY (idTeam2) REFERENCES DB2025_Team(idTeam) ON DELETE CASCADE
);


/*
	## Create 'DB2025_GameStat' Table: 
	# Purpose: Stores detailed game statistics per team.
	
	includes
	- game ID --Primary Key, Foreign Key
	- total shots
	- shots on target
	- accurate passes
	-	interceptions
	- blocks
*/ 
CREATE TABLE DB2025_GameStat (
    idGame INTEGER,
    idOurTeam INTEGER NOT NULL,
    goalOurTeam INTEGER,

    idField INTEGER,
    idSetpiece INTEGER,

    allShots INTEGER,
    shotOnTarget INTEGER,
    accPass INTEGER,
    attackPass INTEGER,
    intercept INTEGER,
    blocking INTEGER,

    PRIMARY KEY (idGame, idOurTeam),

    FOREIGN KEY (idGame) REFERENCES DB2025_GameRec(idGame) ON DELETE CASCADE,
    FOREIGN KEY (idOurTeam) REFERENCES DB2025_Team(idTeam) ON DELETE CASCADE,
    FOREIGN KEY (idField) REFERENCES DB2025_Tactics(idTactic) ON DELETE SET NULL,
    FOREIGN KEY (idSetpiece) REFERENCES DB2025_Tactics(idTactic) ON DELETE SET NULL
);


/*
	## Create 'DB2025_Squad' Table
	# Purpose: Tracks player participation per game.
	
	includes
	- game ID --Priamry Key, Foreign Key
	- player ID --Priamry Key, Foreign Key
	- the amount of time in minutes for each player in each game.
*/
CREATE TABLE DB2025_Squad(
idGame INTEGER,
idPlayer INTEGER,
playTime INTEGER,
PRIMARY KEY(idGame, idPlayer),
FOREIGN KEY(idGame) REFERENCES DB2025_GameRec(idGame) ON DELETE CASCADE,
FOREIGN KEY(idPlayer) REFERENCES DB2025_Player(idPlayer) ON DELETE CASCADE
);