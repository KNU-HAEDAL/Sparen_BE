CREATE TABLE users
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    exp               INTEGER      NOT NULL,
    created_at        DATETIME(6),
    last_modified_at  DATETIME(6),
    auth_token        VARCHAR(255),
    email             VARCHAR(255),
    nickname          VARCHAR(255) NOT NULL,
    password          VARCHAR(255),
    profile_image_url VARCHAR(255),
    provider          ENUM('KAKAO', 'NAVER'),
    role              ENUM('ADMIN', 'MANAGER', 'USER') NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_group
(
    id               BIGINT NOT NULL AUTO_INCREMENT,
    cumulative_count INTEGER,
    created_at       DATETIME(6),
    last_modified_at DATETIME(6),
    content          VARCHAR(255),
    guide            VARCHAR(255),
    title            VARCHAR(255),
    category         ENUM('ECHO', 'ETC', 'HEALTH', 'SHARE', 'VOLUNTEER'),
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE challenge
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    difficulty         INTEGER,
    end_date           DATE,
    once_exp           INTEGER,
    required_count     INTEGER,
    start_date         DATE,
    success_exp        INTEGER,
    challenge_group_id BIGINT,
    created_at         DATETIME(6),
    last_modified_at   DATETIME(6),
    day_type           ENUM('DAY', 'MONTH', 'WEEK', 'YEAR'),
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_group_image
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    challenge_group_id BIGINT,
    created_at         DATETIME(6),
    last_modified_at   DATETIME(6),
    image_url          VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_group_image_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_group_user_exp
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    total_exp          INTEGER,
    challenge_group_id BIGINT,
    user_id            BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_group_user_exp_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id),
    CONSTRAINT FK_challenge_group_user_exp_users FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE user_challenge
(
    id               BIGINT NOT NULL AUTO_INCREMENT,
    challenge_id     BIGINT,
    created_at       DATETIME(6),
    last_modified_at DATETIME(6),
    user_id          BIGINT,
    status           ENUM('FAIL', 'PROCEEDING', 'SUCCESS'),
    PRIMARY KEY (id),
    CONSTRAINT FK_user_challenge_challenge FOREIGN KEY (challenge_id) REFERENCES challenge (id),
    CONSTRAINT FK_user_challenge_users FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_review
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    rating             INTEGER,
    challenge_group_id BIGINT,
    created_at         DATETIME(6),
    last_modified_at   DATETIME(6),
    user_challenge_id  BIGINT,
    content            VARCHAR(255),
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_review_user_challenge FOREIGN KEY (user_challenge_id) REFERENCES user_challenge (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_verification
(
    id                BIGINT NOT NULL AUTO_INCREMENT,
    created_at        DATETIME(6),
    last_modified_at  DATETIME(6),
    user_challenge_id BIGINT,
    content           VARCHAR(255),
    image_url         VARCHAR(255),
    status            ENUM('APPROVED', 'REJECTED', 'WAITING'),
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_verification_user_challenge FOREIGN KEY (user_challenge_id) REFERENCES user_challenge (id)
) ENGINE=InnoDB;
