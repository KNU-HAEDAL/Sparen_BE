ALTER TABLE challenge_review ADD COLUMN difficulty TINYINT DEFAULT(1) NOT NULL;
ALTER TABLE challenge_review ADD COLUMN achievement TINYINT DEFAULT(1) NOT NULL;

ALTER TABLE challenge_review ALTER COLUMN difficulty DROP DEFAULT;
ALTER TABLE challenge_review ALTER COLUMN achievement DROP DEFAULT;
