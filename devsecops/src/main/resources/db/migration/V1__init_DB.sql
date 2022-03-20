CREATE TABLE scanning
(
    id        SERIAL,
    name      VARCHAR,
    status    VARCHAR,
    scan_time timestamp,
    result    VARCHAR,
    PRIMARY KEY (id)
)