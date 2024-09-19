-- 평점 1~5이므로 `TINYINT`로 변경
ALTER TABLE challenge_review MODIFY COLUMN rating TINYINT NOT NULL;

-- 기존 인덱스 삭제
DROP INDEX idx_challenge_review_challenge_group_id ON challenge_review;

-- 새로운 복합 인덱스 생성
CREATE INDEX idx_challenge_review_challenge_group_id_rating ON challenge_review (challenge_group_id, rating);