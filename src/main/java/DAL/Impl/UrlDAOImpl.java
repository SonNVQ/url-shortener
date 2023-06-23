package DAL.Impl;

import DAL.DBContext;
import DAL.UrlDAO;
import Models.Url;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenson
 */
public class UrlDAOImpl implements UrlDAO {

    private final DBContext dbContext;

    public UrlDAOImpl() {
        dbContext = new DBContextImpl();
    }

    @Override
    public Url addUrl(Url url) {
        String sql = "insert into urls(uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, url.getUid());
            ps.setInt(2, url.getUserId());
            ps.setNString(3, url.getLink());
            ps.setNString(4, url.getTitle());
            ps.setNString(5, url.getPasscode());
            ps.setInt(6, url.getRedirectTime());
            ps.setNString(7, url.getRedirectMessage());
            ps.setTimestamp(8, Timestamp.valueOf(url.getCreatedTime()));
            ps.setTimestamp(9, Timestamp.valueOf(url.getExpirationTime()));
            ps.setBoolean(10, url.isBanned());
            ps.setNString(11, url.getNote());
            ps.setNString(12, url.getAdminNote());
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                try ( ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        url.setId(rs.getInt(1));
                        return url;
                    }
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Url getUrlById(int id) {
        String sql = "select id, uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note"
                + "from urls where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Url url = Url.builder()
                            .id(id)
                            .uid(rs.getString("uid"))
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(rs.getInt("redirect_time"))
                            .redirectMessage(rs.getNString("redirect_message"))
                            .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                            .expirationTime(rs.getTimestamp("expiration_time").toLocalDateTime())
                            .isBanned(rs.getBoolean("is_banned"))
                            .note(rs.getNString("note"))
                            .adminNote(rs.getNString("admin_note"))
                            .build();
                    return url;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Url getUrlByUid(String uid) {
        String sql = "select id, uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note"
                + "from urls where uid = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, uid);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Url url = Url.builder()
                            .id(rs.getInt("id"))
                            .uid(uid)
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(rs.getInt("redirect_time"))
                            .redirectMessage(rs.getNString("redirect_message"))
                            .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                            .expirationTime(rs.getTimestamp("expiration_time").toLocalDateTime())
                            .isBanned(rs.getBoolean("is_banned"))
                            .note(rs.getNString("note"))
                            .adminNote(rs.getNString("admin_note"))
                            .build();
                    return url;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Url updateUrl(Url url) {
        String sql = "update urls user_id = ?, link = ?, title = ?, "
                + "passcode = ?, redirect_time = ?, redirect_message = ?, created_time = ?, "
                + "expiration_time = ?, is_banned = ?, note = ?, admin_note = ?"
                + "where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, url.getUserId());
            ps.setNString(2, url.getLink());
            ps.setNString(3, url.getTitle());
            ps.setNString(4, url.getPasscode());
            ps.setInt(5, url.getRedirectTime());
            ps.setNString(6, url.getRedirectMessage());
            ps.setTimestamp(7, Timestamp.valueOf(url.getCreatedTime()));
            ps.setTimestamp(8, Timestamp.valueOf(url.getExpirationTime()));
            ps.setBoolean(9, url.isBanned());
            ps.setNString(10, url.getNote());
            ps.setNString(11, url.getAdminNote());
            ps.setInt(12, url.getId());
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                try ( ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        url.setId(rs.getInt(1));
                        return url;
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Boolean deleteUrlById(int id) {
        String sql = "delete from urls where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Boolean isUrlIdAvailable(String uid) {
        String sql = "select uid from urls where uid = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, uid);
            try ( ResultSet rs = ps.executeQuery()) {
                return !rs.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
