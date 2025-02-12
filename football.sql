CREATE DATABASE IF NOT EXISTS `football`;
USE `football`;

DROP TABLE IF EXISTS `joueur`;
DROP TABLE IF EXISTS `match`;
DROP TABLE IF EXISTS `equipe`;
DROP TABLE IF EXISTS `user`;

CREATE TABLE `equipe` (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(100) NOT NULL,
  ligue VARCHAR(100) NOT NULL,
  points INT UNSIGNED DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `joueur` (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  nom VARCHAR(100) NOT NULL,
  prenom VARCHAR(100) NOT NULL,
  numeroMaillot TINYINT UNSIGNED NOT NULL,
  equipe_id INT UNSIGNED NOT NULL,
  CONSTRAINT fk_joueur_equipe FOREIGN KEY (equipe_id) REFERENCES equipe(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `match` (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  equipe1Id INT UNSIGNED NOT NULL,
  equipe2Id INT UNSIGNED NOT NULL,
  nbButsEquipe1 TINYINT,
  nbButsEquipe2 TINYINT,
  CONSTRAINT fk_match_equipe1 FOREIGN KEY (equipe1Id) REFERENCES equipe(id),
  CONSTRAINT fk_match_equipe2 FOREIGN KEY (equipe2Id) REFERENCES equipe(id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `user` (
  id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(100) NOT NULL,
  password VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Insert sample user
INSERT INTO `user` (username, password) VALUES ('admin', 'admin');

-- Insert sample teams (équipes) with points updated based on played matches:
-- Match 1: PSG beats Marseille (3-1): PSG = 3, Marseille = 0.
-- Match 2: AS Monaco draws with Olympique Lyonnais (2-2): Monaco = 1, Lyon = 1.
-- Match 3: LOSC Lille beats FC Nantes (1-0): Lille = 3, Nantes = 0.
INSERT INTO `equipe` (nom, ligue, points) VALUES ('Paris Saint-Germain', 'Ligue 1', 3);
INSERT INTO `equipe` (nom, ligue, points) VALUES ('Olympique de Marseille', 'Ligue 1', 0);
INSERT INTO `equipe` (nom, ligue, points) VALUES ('AS Monaco', 'Ligue 1', 1);
INSERT INTO `equipe` (nom, ligue, points) VALUES ('Olympique Lyonnais', 'Ligue 1', 1);
INSERT INTO `equipe` (nom, ligue, points) VALUES ('LOSC Lille', 'Ligue 1', 3);
INSERT INTO `equipe` (nom, ligue, points) VALUES ('FC Nantes', 'Ligue 1', 0);

-- Insert sample players for each team

-- Players for Paris Saint-Germain (team id = 1)
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Mbappe', 'Kylian', 7, 1);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Neymar', 'Junior', 10, 1);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Di Maria', 'Angel', 11, 1);

-- Players for Olympique de Marseille (team id = 2)
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Payet', 'Dimitri', 8, 2);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Verratti', 'Marco', 4, 2);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Thauvin', 'Florian', 9, 2);

-- Players for AS Monaco (team id = 3)
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Martial', 'Anthony', 9, 3);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Giovanni', 'Simeone', 5, 3);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Fofana', 'Wylan', 17, 3);

-- Players for Olympique Lyonnais (team id = 4)
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Depay', 'Memphis', 10, 4);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Dembele', 'Sébastien', 14, 4);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Gonalons', 'Bafétimbi', 8, 4);

-- Players for LOSC Lille (team id = 5)
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Gourcuff', 'Yoann', 7, 5);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Valbuena', 'Yohan', 10, 5);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Koulibaly', 'Kalidou', 4, 5);

-- Players for FC Nantes (team id = 6)
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Sissoko', 'Mahamadou', 6, 6);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Meunier', 'Loïc', 8, 6);
INSERT INTO `joueur` (nom, prenom, numeroMaillot, equipe_id) VALUES ('Ndiaye', 'Moussa', 11, 6);

-- Insert sample matches
-- Played matches (scores not -1)
INSERT INTO `match` (equipe1Id, equipe2Id, nbButsEquipe1, nbButsEquipe2)
VALUES (1, 2, 3, 1); -- PSG beat Marseille 3-1
INSERT INTO `match` (equipe1Id, equipe2Id, nbButsEquipe1, nbButsEquipe2)
VALUES (3, 4, 2, 2); -- AS Monaco drew with Lyon 2-2
INSERT INTO `match` (equipe1Id, equipe2Id, nbButsEquipe1, nbButsEquipe2)
VALUES (5, 6, 1, 0); -- Lille beat Nantes 1-0

-- Upcoming matches (not yet played, scores set to -1)
INSERT INTO `match` (equipe1Id, equipe2Id, nbButsEquipe1, nbButsEquipe2)
VALUES (1, 3, -1, -1); -- PSG vs Monaco (not played)
INSERT INTO `match` (equipe1Id, equipe2Id, nbButsEquipe1, nbButsEquipe2)
VALUES (2, 4, -1, -1); -- Marseille vs Lyon (not played)
INSERT INTO `match` (equipe1Id, equipe2Id, nbButsEquipe1, nbButsEquipe2)
VALUES (5, 1, -1, -1); -- Lille vs PSG (not played)
