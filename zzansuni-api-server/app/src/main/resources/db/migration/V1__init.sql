CREATE TABLE users
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    exp               INTEGER      NOT NULL,
    created_at        DATETIME(6)  NOT NULL,
    last_modified_at  DATETIME(6)  NOT NULL,
    auth_token        VARCHAR(255),
    email             VARCHAR(255) UNIQUE,
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
    cumulative_count INTEGER NOT NULL,
    created_at       DATETIME(6) NOT NULL,
    last_modified_at DATETIME(6) NOT NULL,
    content          VARCHAR(255) NOT NULL,
    guide            VARCHAR(255) NOT NULL,
    title            VARCHAR(255) NOT NULL,
    category         ENUM('ECHO', 'ETC', 'HEALTH', 'SHARE', 'VOLUNTEER') NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE challenge
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    difficulty         INTEGER NOT NULL,
    end_date           DATE NOT NULL,
    once_exp           INTEGER NOT NULL,
    required_count     INTEGER NOT NULL,
    start_date         DATE NOT NULL,
    success_exp        INTEGER NOT NULL,
    challenge_group_id BIGINT NOT NULL,
    created_at         DATETIME(6) NOT NULL,
    last_modified_at   DATETIME(6) NOT NULL,
    day_type           ENUM('DAY', 'MONTH', 'WEEK', 'YEAR') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_group_image
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    challenge_group_id BIGINT NOT NULL,
    created_at         DATETIME(6) NOT NULL,
    last_modified_at   DATETIME(6) NOT NULL,
    image_url          VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_group_image_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_group_user_exp
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    total_exp          INTEGER NOT NULL,
    challenge_group_id BIGINT NOT NULL,
    user_id            BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_group_user_exp_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id),
    CONSTRAINT FK_challenge_group_user_exp_users FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE user_challenge
(
    id               BIGINT NOT NULL AUTO_INCREMENT,
    challenge_id     BIGINT NOT NULL,
    created_at       DATETIME(6) NOT NULL,
    last_modified_at DATETIME(6) NOT NULL,
    user_id          BIGINT NOT NULL,
    status           ENUM('FAIL', 'PROCEEDING', 'SUCCESS') NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_user_challenge_challenge FOREIGN KEY (challenge_id) REFERENCES challenge (id),
    CONSTRAINT FK_user_challenge_users FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_review
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    rating             INTEGER NOT NULL,
    challenge_group_id BIGINT NOT NULL,
    created_at         DATETIME(6) NOT NULL,
    last_modified_at   DATETIME(6) NOT NULL,
    user_challenge_id  BIGINT NOT NULL,
    content            VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_review_user_challenge FOREIGN KEY (user_challenge_id) REFERENCES user_challenge (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_verification
(
    id                BIGINT NOT NULL AUTO_INCREMENT,
    created_at        DATETIME(6) NOT NULL,
    last_modified_at  DATETIME(6) NOT NULL,
    user_challenge_id BIGINT NOT NULL,
    content           VARCHAR(255) NOT NULL,
    image_url         VARCHAR(255),
    status            ENUM('APPROVED', 'REJECTED', 'WAITING'),
    PRIMARY KEY (id),
    CONSTRAINT FK_challenge_verification_user_challenge FOREIGN KEY (user_challenge_id) REFERENCES user_challenge (id)
) ENGINE=InnoDB;
