package com.github.Badgaar.repository;

import com.github.Badgaar.model.Role;
import com.github.Badgaar.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
@Profile("jdbc")
public class JDBCUserRepository implements IUserRepository {

    private final DataSource dataSource;

    public JDBCUserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
        createTableIfNotExists();
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void releaseConnection(Connection connection) {
        DataSourceUtils.releaseConnection(connection, dataSource);
    }

    private void createTableIfNotExists() {
        String createSql = """
                CREATE TABLE IF NOT EXISTS users (
                    id TEXT PRIMARY KEY,
                    login TEXT NOT NULL UNIQUE,
                    password_hash TEXT NOT NULL,
                    role TEXT NOT NULL,
                    rented_vehicle_id TEXT
                )
                """;
        String alterSql = "ALTER TABLE users ADD COLUMN IF NOT EXISTS rented_vehicle_id TEXT";
        Connection connection = getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSql);
            stmt.execute(alterSql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();
        user.id = rs.getString("id");
        user.login = rs.getString("login");
        user.passwordHash = rs.getString("password_hash");
        user.role = Role.valueOf(rs.getString("role"));
        user.rentedVehicleID = rs.getString("rented_vehicle_id");
        return user;
    }

    @Override
    public User getUser(String login) {
        String sql = "SELECT * FROM users WHERE login = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
        return null;
    }

    @Override
    public User getUserById(String id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        Connection connection = getConnection();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
        return users;
    }

    @Override
    public void add(User user) {
        if (getUserById(user.id) != null) {
            update(user);
            return;
        }
        String sql = "INSERT INTO users (id, login, password_hash, role, rented_vehicle_id) VALUES (?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.id);
            stmt.setString(2, user.login);
            stmt.setString(3, user.passwordHash);
            stmt.setString(4, user.role.name());
            stmt.setString(5, user.rentedVehicleID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET login = ?, password_hash = ?, role = ?, rented_vehicle_id = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.login);
            stmt.setString(2, user.passwordHash);
            stmt.setString(3, user.role.name());
            stmt.setString(4, user.rentedVehicleID);
            stmt.setString(5, user.id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void remove(String login) {
        String sql = "DELETE FROM users WHERE login = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, login);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}