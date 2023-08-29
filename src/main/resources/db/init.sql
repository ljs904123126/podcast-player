CREATE TABLE sys_user
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    name        TEXT,
    password    TEXT,
    eudic_token VARCHAR(255),
    create_time DATETIME
);

CREATE TABLE sys_podcasts
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    name             TEXT NOT NULL,
    rss              VARCHAR(255),
    create_time      DATETIME,
    last_update_time DATETIME
);
CREATE TABLE sys_episodes
(
    id            INTEGER PRIMARY KEY AUTOINCREMENT,
    podcast_id    INTEGER NOT NULL,
    duration      INTEGER,
    name          TEXT,
    audio_file    VARCHAR(255),
    srt_file      VARCHAR(255),
    describe_info TEXT,
    create_time   DATETIME
);

CREATE TABLE sys_episodes_record
(
    id               INTEGER PRIMARY KEY AUTOINCREMENT,
    episode_id       INTEGER NOT NULL,
    user_id          INTEGER NOT NULL,
    finished  TINYINT DEFAULT 0,
    duration         INTEGER,
    create_time      DATETIME,
    last_update_time DATETIME
);

CREATE TABLE sys_user_statment
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    episode_id  INTEGER,
    user_id     INTEGER NOT NULL,
    segment     TEXT,
    create_time DATETIME
);

CREATE TABLE sys_user_vocabulary
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id     INTEGER NOT NULL,
    episode_id  INTEGER,
    vocabulary  VARCHAR(255),
    segment     TEXT,
    create_time DATETIME
);


INSERT INTO sys_podcasts
(name, rss, create_time, last_update_time)
VALUES('podcast-1', NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');


INSERT INTO sys_podcasts
(name, rss, create_time, last_update_time)
VALUES('podcast-2', NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');


INSERT INTO sys_podcasts
(name, rss, create_time, last_update_time)
VALUES('podcast-3', NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');


INSERT INTO sys_episodes
( podcast_id, duration, name, audio_file, srt_file, describe_info, create_time)
VALUES( 1, 9999, 'podcast1-epis-1', NULL, NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');
INSERT INTO sys_episodes
( podcast_id, duration, name, audio_file, srt_file, describe_info, create_time)
VALUES( 1, 9999, 'podcast1-epis-2', NULL, NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');


INSERT INTO sys_episodes
( podcast_id, duration, name, audio_file, srt_file, describe_info, create_time)
VALUES( 2, 9999, 'podcast2-epis-1', NULL, NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');
INSERT INTO sys_episodes
( podcast_id, duration, name, audio_file, srt_file, describe_info, create_time)
VALUES( 2, 9999, 'podcast2-epis-2', NULL, NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');


INSERT INTO sys_episodes
( podcast_id, duration, name, audio_file, srt_file, describe_info, create_time)
VALUES( 3, 9999, 'podcast3-epis-1', NULL, NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');
INSERT INTO sys_episodes
( podcast_id, duration, name, audio_file, srt_file, describe_info, create_time)
VALUES( 3, 9999, 'podcast3-epis-2', NULL, NULL, '2023-08-29 15:33:33', '2023-08-29 15:33:33');