ALTER TABLE challenge_review ADD COLUMN difficulty TINYINT DEFULT(1) NOT NULL;
ALTER TABLE challenge_review ADD COLUMN achievement TINYINT DEFULT(1) NOT NULL;

ALTER TABLE challenge_review ALTER COLUMN difficulty DROP DEFAULT;
ALTER TABLE challenge_review ALTER COLUMN achievement DROP DEFAULT;