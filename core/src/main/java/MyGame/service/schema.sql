CREATE TABLE IF NOT EXISTS saves(
                                    slot INTEGER PRIMARY KEY,

                                    player_x REAL NOT NULL,
                                    player_y REAL NOT NULL,

                                    hp INTEGER NOT NULL,
                                    max_hp INTEGER NOT NULL,

                                    soul INTEGER NOT NULL,
                                    geo INTEGER NOT NULL,

                                    current_map TEXT NOT NULL,

                                    dash_unlocked INTEGER DEFAULT 0,
                                    wall_jump_unlocked INTEGER DEFAULT 0,
                                    double_jump_unlocked INTEGER DEFAULT 0,

                                    boss_defeated INTEGER DEFAULT 0,

                                    death_count INTEGER DEFAULT 0,
                                    kill_count INTEGER DEFAULT 0,

                                    play_time INTEGER DEFAULT 0,

                                    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
                                    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);
