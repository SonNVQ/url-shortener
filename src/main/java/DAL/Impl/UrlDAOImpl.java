package DAL.Impl;

import DAL.DBContext;
import DAL.UrlDAO;
import Models.Url;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenson
 */
@Default
public class UrlDAOImpl implements UrlDAO {

    @Inject
    private DBContext dbContext;

    public UrlDAOImpl() {
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
        String sql = "select id, uid, user_id, link, title, passcode, redirect_time, \n"
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note \n"
                + "from urls where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp expirationTimestamp = rs.getTimestamp("expiration_time");
                    LocalDateTime expirationTime = null;
                    if (expirationTimestamp != null) {
                        expirationTime = expirationTimestamp.toLocalDateTime();
                    }
                    Integer redirectTime = rs.getInt("redirect_time");
                    if (redirectTime == 0)
                        redirectTime = null;
                    Url url = Url.builder()
                            .id(id)
                            .uid(rs.getNString("uid"))
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(redirectTime)
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
                    Integer redirectTime = rs.getInt("redirect_time");
                    if (redirectTime == 0)
                        redirectTime = null;
                    Url url = Url.builder()
                            .id(rs.getInt("id"))
                            .uid(uid)
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(redirectTime)
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
    public List<Url> getUrlsPaging(int userId, int size, int page) {
        String sql = "SELECT id, uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note "
                + "FROM urls WHERE user_id = ? ORDER BY created_time DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = size * (page - 1);
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, offset);
            ps.setInt(3, size);
            List<Url> urls = new ArrayList<>();
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp expirationTimestamp = rs.getTimestamp("expiration_time");
                    LocalDateTime expirationTime = null;
                    if (expirationTimestamp != null) {
                        expirationTime = expirationTimestamp.toLocalDateTime();
                    }
                    Integer redirectTime = rs.getInt("redirect_time");
                    if (redirectTime == 0)
                        redirectTime = null;
                    Url url = Url.builder()
                            .id(rs.getInt("id"))
                            .uid(rs.getNString("uid"))
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(redirectTime)
                            .redirectMessage(rs.getNString("redirect_message"))
                            .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                            .expirationTime(expirationTime)
                            .isBanned(rs.getBoolean("is_banned"))
                            .note(rs.getNString("note"))
                            .adminNote(rs.getNString("admin_note"))
                            .build();
                    urls.add(url);
                }
            }
            return urls;
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Url> searchUrlPaging(int userId, String searchField, String searchText, Date searchFromDate, Date searchToDate, int size, int page) {
        String sql = "SELECT id, uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note "
                + "FROM urls "
                + "WHERE user_id = ? AND " + searchField + " LIKE CONCAT('%', ?, '%') "
                + "AND created_time BETWEEN ? AND ? "
                + "ORDER BY created_time DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = size * (page - 1);
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setNString(2, searchText);
            ps.setDate(3, searchFromDate);
            ps.setDate(4, searchToDate);
            ps.setInt(5, offset);
            ps.setInt(6, size);
            List<Url> urls = new ArrayList<>();
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp expirationTimestamp = rs.getTimestamp("expiration_time");
                    LocalDateTime expirationTime = null;
                    if (expirationTimestamp != null) {
                        expirationTime = expirationTimestamp.toLocalDateTime();
                    }
                    Integer redirectTime = rs.getInt("redirect_time");
                    if (redirectTime == 0)
                        redirectTime = null;
                    Url url = Url.builder()
                            .id(rs.getInt("id"))
                            .uid(rs.getNString("uid"))
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(redirectTime)
                            .redirectMessage(rs.getNString("redirect_message"))
                            .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                            .expirationTime(expirationTime)
                            .isBanned(rs.getBoolean("is_banned"))
                            .note(rs.getNString("note"))
                            .adminNote(rs.getNString("admin_note"))
                            .build();
                    urls.add(url);
                }
            }
            return urls;
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int countNumberOfRows(int userId) {
        String sql = "SELECT COUNT(*) AS total FROM urls WHERE user_id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setInt(1, userId);
            try ( ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int countNumberOfSearchingRows(int userId, String searchField, String searchText, Date searchFromDate, Date searchToDate) {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM urls "
                + "WHERE user_id = ? AND " + searchField + " LIKE CONCAT('%', ?, '%') "
                + "AND created_time BETWEEN ? AND ? ";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setInt(1, userId);
            ps.setNString(2, searchText);
            ps.setDate(3, searchFromDate);
            ps.setDate(4, searchToDate);
            try ( ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    @Override
    public List<Url> getAllUrlsPaging(int size, int page) {
        String sql = "SELECT id, uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note "
                + "FROM urls ORDER BY created_time DESC "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = size * (page - 1);
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, size);
            List<Url> urls = new ArrayList<>();
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp expirationTimestamp = rs.getTimestamp("expiration_time");
                    LocalDateTime expirationTime = null;
                    if (expirationTimestamp != null) {
                        expirationTime = expirationTimestamp.toLocalDateTime();
                    }
                    Integer redirectTime = rs.getInt("redirect_time");
                    if (redirectTime == 0)
                        redirectTime = null;
                    Url url = Url.builder()
                            .id(rs.getInt("id"))
                            .uid(rs.getNString("uid"))
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(redirectTime)
                            .redirectMessage(rs.getNString("redirect_message"))
                            .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                            .expirationTime(expirationTime)
                            .isBanned(rs.getBoolean("is_banned"))
                            .note(rs.getNString("note"))
                            .adminNote(rs.getNString("admin_note"))
                            .build();
                    urls.add(url);
                }
            }
            return urls;
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Url> searchAllUrlPaging(String searchField, String searchText, Date searchFromDate, Date searchToDate, int size, int page) {
        String sql = "SELECT id, uid, user_id, link, title, passcode, redirect_time, "
                + "redirect_message, created_time, expiration_time, is_banned, note, admin_note "
                + "FROM urls "
                + "WHERE " + searchField + " LIKE CONCAT('%', ?, '%') "
                + "AND created_time BETWEEN ? AND ? "
                + "ORDER BY created_time DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = size * (page - 1);
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setNString(1, searchText);
            ps.setDate(2, searchFromDate);
            ps.setDate(3, searchToDate);
            ps.setInt(4, offset);
            ps.setInt(5, size);
            List<Url> urls = new ArrayList<>();
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp expirationTimestamp = rs.getTimestamp("expiration_time");
                    LocalDateTime expirationTime = null;
                    if (expirationTimestamp != null) {
                        expirationTime = expirationTimestamp.toLocalDateTime();
                    }
                    Integer redirectTime = rs.getInt("redirect_time");
                    if (redirectTime == 0)
                        redirectTime = null;
                    Url url = Url.builder()
                            .id(rs.getInt("id"))
                            .uid(rs.getNString("uid"))
                            .userId(rs.getInt("user_id"))
                            .link(rs.getNString("link"))
                            .title(rs.getNString("title"))
                            .passcode(rs.getNString("passcode"))
                            .redirectTime(redirectTime)
                            .redirectMessage(rs.getNString("redirect_message"))
                            .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                            .expirationTime(expirationTime)
                            .isBanned(rs.getBoolean("is_banned"))
                            .note(rs.getNString("note"))
                            .adminNote(rs.getNString("admin_note"))
                            .build();
                    urls.add(url);
                }
            }
            return urls;
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public int countAllNumberOfRows() {
        String sql = "SELECT COUNT(*) AS total FROM urls";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql);) {
            try ( ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public int countAllNumberOfSearchingRows(String searchField, String searchText, Date searchFromDate, Date searchToDate) {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM urls "
                + "WHERE " + searchField + " LIKE CONCAT('%', ?, '%') "
                + "AND created_time BETWEEN ? AND ? ";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql);) {
            ps.setNString(1, searchText);
            ps.setDate(2, searchFromDate);
            ps.setDate(3, searchToDate);
            try ( ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    return rs.getInt("total");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UrlDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    @Override
    public Url updateUrl(Url url) {
        String sql = "update urls set user_id = ?, link = ?, title = ?, "
                + "passcode = ?, redirect_time = ?, redirect_message = ?, "
                + "expiration_time = ?, is_banned = ?, note = ?, admin_note = ? "
                + "where id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            if (url.getUserId() != null) {
                ps.setInt(1, url.getUserId());
            } else {
                ps.setNull(1, Types.INTEGER);
            }
            ps.setNString(2, url.getLink());
            if (url.getTitle() == null || url.getTitle().isEmpty()) {
                ps.setNull(3, Types.NVARCHAR);
            } else {
                ps.setNString(3, url.getTitle());
            }
            if (url.getPasscode() == null || url.getPasscode().isEmpty()) {
                ps.setNull(4, Types.NVARCHAR);
            } else {
                ps.setNString(4, url.getPasscode());
            }
            if (url.getRedirectTime() != null) {
                ps.setInt(5, url.getRedirectTime());
            } else {
                ps.setNull(5, Types.INTEGER);
            }
            if (url.getRedirectMessage() == null || url.getRedirectMessage().isEmpty()) {
                ps.setNull(6, Types.NVARCHAR);
            } else {
                ps.setNString(6, url.getRedirectMessage());
            }
            if (url.getExpirationTime() != null) {
                ps.setTimestamp(7, Timestamp.valueOf(url.getExpirationTime()));
            } else {
                ps.setNull(7, Types.TIMESTAMP);
            }
            if (url.getIsBanned() != null) {
                ps.setBoolean(8, url.getIsBanned());
            } else {
                ps.setNull(8, Types.BOOLEAN);
            }
            ps.setNString(9, url.getNote());
            ps.setNString(10, url.getAdminNote());
            ps.setInt(11, url.getId());

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

    @Override
    public Boolean banUrl(int id) {
        String sql = "UPDATE urls SET is_banned = 1 WHERE id = ?";
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
    public Boolean unbanUrl(int id) {
        String sql = "UPDATE urls SET is_banned = 0 WHERE id = ?";
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
    
    

}
