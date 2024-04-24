CREATE TABLE IF NOT EXISTS GITHUB_REPOSITORY_DATA
(
    id          VARCHAR(255) PRIMARY KEY,
    full_name   VARCHAR(255),
    description TEXT,
    clone_url   VARCHAR(255),
    stars       INT,
    created_at  TIMESTAMP
);