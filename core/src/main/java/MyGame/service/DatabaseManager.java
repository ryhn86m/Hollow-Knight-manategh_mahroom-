package MyGame.service;

import MyGame.model.enums.EnvironmentType;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:save/game.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initializeDatabase() {
        new File("save").mkdirs();
        String savesTable = """
    CREATE TABLE IF NOT EXISTS saves(
        slot INTEGER PRIMARY KEY CHECK (slot >= 1 AND slot <= 4),
        player_x REAL NOT NULL,
        player_y REAL NOT NULL,
        hp INTEGER NOT NULL,
        soul INTEGER NOT NULL,
        current_map TEXT NOT NULL,
        boss_defeated INTEGER NOT NULL,
        death_count INTEGER NOT NULL,
        kill_count INTEGER NOT NULL,


        elapsed_time REAL DEFAULT 0,


        charm_soul_catcher INTEGER DEFAULT 0,
        charm_dashmaster INTEGER DEFAULT 0,
        charm_unbreakable_strength INTEGER DEFAULT 0,
        charm_quick_slash INTEGER DEFAULT 0,
        charm_quick_focus INTEGER DEFAULT 0,
        charm_heavy_blow INTEGER DEFAULT 0,
        charm_sharp_shadow INTEGER DEFAULT 0,
        charm_void_heart INTEGER DEFAULT 0,


        boss_x REAL DEFAULT 0,
        boss_y REAL DEFAULT 0,
        boss_hp INTEGER DEFAULT 50,
        boss_phase_2 INTEGER DEFAULT 0
    );
    """;

        String achievementsTable = """
        CREATE TABLE IF NOT EXISTS achievements(
            id TEXT PRIMARY KEY,
            unlocked INTEGER NOT NULL DEFAULT 0
        );
        """;

        try (Connection con = connect();
             Statement st = con.createStatement()) {
            st.execute(savesTable);
            st.execute(achievementsTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void saveGame(SaveData data) {
        String sql = """
        INSERT INTO saves(
            slot, player_x, player_y, hp, soul, current_map, boss_defeated, death_count, kill_count, elapsed_time,
            charm_soul_catcher, charm_dashmaster, charm_unbreakable_strength, charm_quick_slash, charm_quick_focus, charm_heavy_blow, charm_sharp_shadow, charm_void_heart,
            boss_x, boss_y, boss_hp, boss_phase_2
        ) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        ON CONFLICT(slot) DO UPDATE SET
            player_x=excluded.player_x,
            player_y=excluded.player_y,
            hp=excluded.hp,
            soul=excluded.soul,
            current_map=excluded.current_map,
            boss_defeated=excluded.boss_defeated,
            death_count=excluded.death_count,
            kill_count=excluded.kill_count,
            elapsed_time=excluded.elapsed_time,
            charm_soul_catcher=excluded.charm_soul_catcher,
            charm_dashmaster=excluded.charm_dashmaster,
            charm_unbreakable_strength=excluded.charm_unbreakable_strength,
            charm_quick_slash=excluded.charm_quick_slash,
            charm_quick_focus=excluded.charm_quick_focus,
            charm_heavy_blow=excluded.charm_heavy_blow,
            charm_sharp_shadow=excluded.charm_sharp_shadow,
            charm_void_heart=excluded.charm_void_heart,
            boss_x=excluded.boss_x,
            boss_y=excluded.boss_y,
            boss_hp=excluded.boss_hp,
            boss_phase_2=excluded.boss_phase_2;
        """;

        try (Connection con = connect();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, data.getSlot());
            pstmt.setDouble(2, data.getPlayerX());
            pstmt.setDouble(3, data.getPlayerY());
            pstmt.setInt(4, data.getHp());
            pstmt.setInt(5, data.getSoul());
            pstmt.setString(6, data.getEnvironmentType().name());
            pstmt.setInt(7, data.getBossDefeated());
            pstmt.setInt(8, data.getDeathCount());
            pstmt.setInt(9, data.getKillCount());
            pstmt.setFloat(10, data.getElapsedTime());

            pstmt.setInt(11, data.getSoulCatcher());
            pstmt.setInt(12, data.getDashmaster());
            pstmt.setInt(13, data.getUnbreakableStrength());
            pstmt.setInt(14, data.getQuickSlash());
            pstmt.setInt(15, data.getQuickFocus());
            pstmt.setInt(16, data.getHeavyBlow());
            pstmt.setInt(17, data.getSharpShadow());
            pstmt.setInt(18, data.getVoidHeart());

            pstmt.setDouble(19, data.getBossX());
            pstmt.setDouble(20, data.getBossY());
            pstmt.setInt(21, data.getBossHp());
            pstmt.setInt(22, data.getBossPhase2());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void loadGame(int slot) {
        String sql = "SELECT * FROM saves WHERE slot = ?";

        try (Connection con = connect();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, slot);
            ResultSet rs = pstmt.executeQuery();



        } catch (SQLException e) {
            System.out.println("Error loading game: " + e.getMessage());
        }
    }
    public static SaveData getSave(int slot) {
        String sql = "SELECT * FROM saves WHERE slot = ?";

        try (Connection con = connect();
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            pstmt.setInt(1, slot);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            SaveData save = new SaveData();

            save.setSlot(slot);
            save.setPlayerX(rs.getDouble("player_x"));
            save.setPlayerY(rs.getDouble("player_y"));
            save.setHp(rs.getInt("hp"));
            save.setSoul(rs.getInt("soul"));
            save.setEnvironmentType(EnvironmentType.valueOf(rs.getString("current_map")));
            save.setBossDefeated(rs.getInt("boss_defeated"));
            save.setDeathCount(rs.getInt("death_count"));
            save.setKillCount(rs.getInt("kill_count"));
            save.setElapsedTime(rs.getFloat("elapsed_time"));

            save.setSoulCatcher(rs.getInt("charm_soul_catcher"));
            save.setDashmaster(rs.getInt("charm_dashmaster"));
            save.setUnbreakableStrength(rs.getInt("charm_unbreakable_strength"));
            save.setQuickSlash(rs.getInt("charm_quick_slash"));
            save.setQuickFocus(rs.getInt("charm_quick_focus"));
            save.setHeavyBlow(rs.getInt("charm_heavy_blow"));
            save.setSharpShadow(rs.getInt("charm_sharp_shadow"));
            save.setVoidHeart(rs.getInt("charm_void_heart"));

            save.setBossX(rs.getDouble("boss_x"));
            save.setBossY(rs.getDouble("boss_y"));
            save.setBossHp(rs.getInt("boss_hp"));
            save.setBossPhase2(rs.getInt("boss_phase_2"));

            return save;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveAchievement(String id, boolean unlocked) {
        String sql = "INSERT INTO achievements(id, unlocked) VALUES(?, ?) ON CONFLICT(id) DO UPDATE SET unlocked=excluded.unlocked;";
        try (Connection con = connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setInt(2, unlocked ? 1 : 0);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public static boolean isAchievementUnlocked(String id) {
        String sql = "SELECT unlocked FROM achievements WHERE id = ?";
        try (Connection con = connect(); PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return rs.getInt("unlocked") == 1;
        } catch (SQLException e) { e.printStackTrace(); }
        return false;
    }
    }

