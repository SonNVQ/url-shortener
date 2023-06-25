package DAL.Impl;

import DAL.DBContext;
import DAL.UrlDAO;
import Models.Url;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
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
            if (url.getUserId() != null) {
                ps.setInt(2, url.getUserId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setNString(3, url.getLink());
            ps.setNString(4, url.getTitle());
            if (url.getPasscode() == null || url.getPasscode().isEmpty()) {
                ps.setNull(5, Types.NVARCHAR);
            } else {
                ps.setNString(5, url.getPasscode());
            }
            if (url.getRedirectTime() != null) {
                ps.setInt(6, url.getRedirectTime());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            if (url.getRedirectMessage() == null || url.getRedirectMessage().isEmpty()) {
                ps.setNull(7, Types.NVARCHAR);
            } else {
                ps.setNString(7, url.getRedirectMessage());
            }
            ps.setTimestamp(8, Timestamp.valueOf(url.getCreatedTime()));
            if (url.getExpirationTime() != null) {
                ps.setTimestamp(9, Timestamp.valueOf(url.getExpirationTime()));
            } else {
                ps.setNull(9, Types.TIMESTAMP);
            }
            if (url.getIsBanned() != null) {
                ps.setBoolean(10, url.getIsBanned());
            } else {
                ps.setNull(10, Types.BOOLEAN);
            }
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
        String sql = "SELECT id, uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note "
                + "FROM urls WHERE uid = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, uid);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp expirationTimestamp = rs.getTimestamp("expiration_time");
                    LocalDateTime expirationTime = null;
                    if (expirationTimestamp != null) {
                        expirationTime = expirationTimestamp.toLocalDateTime();
                    }
                    Integer redirect_time = rs.getInt("redirect_time");
                    if (redirect_time == 0)
                        redirect_time = null;
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
                            .expirationTime(expirationTime)
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
            ps.setBoolean(9, url.getIsBanned());
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
    public Boolean isUidAvailable(String uid) {
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
