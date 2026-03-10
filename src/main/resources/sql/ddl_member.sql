-- ?? ??대? ???
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'PK',
    name VARCHAR(100) NOT NULL COMMENT '?대?',
    email VARCHAR(255) NOT NULL UNIQUE COMMENT '?대???濡洹몄?D)',
    password VARCHAR(255) NOT NULL COMMENT '??명? 鍮諛踰??,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '??깆쇱'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='?? ??대?';
