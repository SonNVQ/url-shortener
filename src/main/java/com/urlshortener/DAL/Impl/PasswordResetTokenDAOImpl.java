package com.urlshortener.DAL.Impl;

import com.urlshortener.DAL.DBContext;
import com.urlshortener.DAL.PasswordResetTokenDAO;
import com.urlshortener.Models.PasswordResetToken;
import jakarta.enterprise.inject.Default;
import jakarta.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nguyenson
 */
@Default
public class PasswordResetTokenDAOImpl implements PasswordResetTokenDAO {

    @Inject
    private DBContext dbContext;

    @Override
    public PasswordResetToken addToken(PasswordResetToken token) {
        String sql = "INSERT INTO password_reset_tokens(id, user_id, created_time, expiration_time)\n"
                + "VALUES\n"
                + "(\n"
                + "?  , -- id - varchar(36)\n"
                + "?   , -- user_id - int\n"
                + "?, -- created_time - datetime\n"
                + "?  -- expiration_time - datetime\n"
                + ")";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, token.getId());
            ps.setInt(2, token.getUserId());
            ps.setTimestamp(3, Timestamp.valueOf(token.getCreatedTime()));
            ps.setTimestamp(4, Timestamp.valueOf(token.getExpirationTime()));
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return token;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordResetTokenDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public PasswordResetToken getToken(String id) {
        String sql = "SELECT id, user_id, created_time, expiration_time \n"
                + "FROM password_reset_tokens\n"
                + "WHERE id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    PasswordResetToken token = PasswordResetToken.builder()
                            .id(rs.getString("id"))
                            .userId(rs.getInt("user_id"))
                            .createdTime(rs.getTimestamp("created_time").toLocalDateTime())
                            .expirationTime(rs.getTimestamp("expiration_time").toLocalDateTime())
                            .build();
                    return token;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordResetTokenDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Boolean deleteTokenById(String id) {
        String sql = "DELETE FROM password_reset_tokens WHERE id = ?";
        try ( Connection cn = dbContext.getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, id);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(PasswordResetTokenDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
