CREATE TABLE users
(
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    exp               INTEGER      NOT NULL,
    auth_token        VARCHAR(128),
    email             VARCHAR(255) NOT NULL,
    nickname          VARCHAR(50)  NOT NULL,
    password          VARCHAR(128),
    profile_image_url VARCHAR(255),
    provider          ENUM('KAKAO', 'NAVER'),
    role              ENUM('ADMIN', 'MANAGER', 'USER') NOT NULL,
    created_at        DATETIME(0) NOT NULL,
    modified_at       DATETIME(0) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE challenge_group
(
    id               BIGINT        NOT NULL AUTO_INCREMENT,
    title            VARCHAR(100)  NOT NULL,
    content          VARCHAR(1000) NOT NULL,
    guide            VARCHAR(1000) NOT NULL,
    category         ENUM('ECHO', 'ETC', 'HEALTH', 'SHARE', 'VOLUNTEER') NOT NULL,
    join_start_date  DATE          NOT NULL,
    join_end_date    DATE          NOT NULL,
    cumulative_count INTEGER       NOT NULL,
    created_at       DATETIME(0) NOT NULL,
    modified_at      DATETIME(0) NOT NULL,
    PRIMARY KEY (id),
    INDEX            idx_challenge_group_join_start_date_join_end_date_category (join_start_date, join_end_date, category)
) ENGINE=InnoDB;

CREATE TABLE challenge
(
    id                 BIGINT  NOT NULL AUTO_INCREMENT,
    challenge_group_id BIGINT  NOT NULL,
    once_exp           INTEGER NOT NULL,
    success_exp        INTEGER NOT NULL,
    difficulty         INTEGER NOT NULL,
    required_count     INTEGER NOT NULL,
    active_period      INTEGER NOT NULL,
    created_at         DATETIME(0) NOT NULL,
    modified_at        DATETIME(0) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_challenge_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_group_image
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    challenge_group_id BIGINT       NOT NULL,
    image_url          VARCHAR(255) NOT NULL,
    created_at         DATETIME(0) NOT NULL,
    modified_at        DATETIME(0) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_challenge_group_image_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_group_user_exp
(
    id                 BIGINT  NOT NULL AUTO_INCREMENT,
    total_exp          INTEGER NOT NULL,
    challenge_group_id BIGINT  NOT NULL,
    user_id            BIGINT  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_challenge_group_user_exp_challenge_group_user UNIQUE (challenge_group_id, user_id),
    CONSTRAINT fk_challenge_group_user_exp_challenge_group FOREIGN KEY (challenge_group_id) REFERENCES challenge_group (id),
    CONSTRAINT fk_challenge_group_user_exp_users FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE user_challenge
(
    id                BIGINT NOT NULL AUTO_INCREMENT,
    challenge_id      BIGINT NOT NULL,
    user_id           BIGINT NOT NULL,
    active_start_date DATE   NOT NULL,
    active_end_date   DATE   NOT NULL,
    status            ENUM('FAIL', 'PROCEEDING', 'SUCCESS') NOT NULL,
    success_date      DATE,
    created_at        DATETIME(0) NOT NULL,
    modified_at       DATETIME(0) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_user_challenge_challenge_user UNIQUE (challenge_id, user_id),
    CONSTRAINT fk_user_challenge_challenge FOREIGN KEY (challenge_id) REFERENCES challenge (id),
    CONSTRAINT fk_user_challenge_users FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE challenge_review
(
    id                 BIGINT        NOT NULL AUTO_INCREMENT,
    challenge_group_id BIGINT        NOT NULL,
    user_challenge_id  BIGINT        NOT NULL,
    rating             INTEGER       NOT NULL,
    content            VARCHAR(1000) NOT NULL,
    created_at         DATETIME(0) NOT NULL,
    modified_at        DATETIME(0) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_challenge_review_user_challenge FOREIGN KEY (user_challenge_id) REFERENCES user_challenge (id),
    INDEX              idx_challenge_review_challenge_group_id (challenge_group_id)
) ENGINE=InnoDB;

CREATE TABLE challenge_verification
(
    id                BIGINT        NOT NULL AUTO_INCREMENT,
    user_challenge_id BIGINT        NOT NULL,
    content           VARCHAR(1000) NOT NULL,
    image_url         VARCHAR(255),
    status            ENUM('PRE_APPROVED', 'APPROVED', 'REJECTED'),
    created_at        DATETIME(0) NOT NULL,
    modified_at       DATETIME(0) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_challenge_verification_user_challenge FOREIGN KEY (user_challenge_id) REFERENCES user_challenge (id)
) ENGINE=InnoDB;

