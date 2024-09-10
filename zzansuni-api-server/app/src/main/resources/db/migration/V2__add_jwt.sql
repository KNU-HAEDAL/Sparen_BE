CREATE TABLE refresh_token (
    id CHAR(36) NOT NULL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    expired_at TIMESTAMP(0) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
