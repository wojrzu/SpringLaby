package com.github.Badgaar.repository;

import com.github.Badgaar.model.Rental;
import com.github.Badgaar.model.User;
import com.github.Badgaar.model.Vehicle;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("jdbc")
public class JDBCRentalRepository implements IRentalRepository {


    private final IUserRepository userRepository;
    private final IVehicleRepository vehicleRepository;
    private final DataSource dataSource;

    public JDBCRentalRepository(IUserRepository userRepository, IVehicleRepository vehicleRepository, DataSource dataSource) {
        this.userRepository = userRepository;
        this.vehicleRepository = vehicleRepository;
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
        String sql = """
                CREATE TABLE IF NOT EXISTS rental (
                    id TEXT PRIMARY KEY,
                    vehicle_id TEXT NOT NULL,
                    user_id TEXT NOT NULL,
                    rent_date TEXT,
                    return_date TEXT,
                    is_occupied BOOLEAN NOT NULL DEFAULT true,
                    has_ended BOOLEAN NOT NULL DEFAULT false,
                    FOREIGN KEY (vehicle_id) REFERENCES vehicle(id) ON DELETE CASCADE,
                    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
                )
                """;
        Connection connection = getConnection();
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    private Rental mapRow(ResultSet rs) throws SQLException {
        String userId = rs.getString("user_id");
        String vehicleId = rs.getString("vehicle_id");
        User user = userRepository.getUserById(userId);
        Vehicle vehicle = vehicleRepository.getVehicle(vehicleId);

        Rental rental = new Rental(rs.getString("id"), user, vehicle, rs.getBoolean("is_occupied"));
        rental.hasEnded = rs.getBoolean("has_ended");
        rental.rentDate = rs.getString("rent_date");
        rental.returnDate = rs.getString("return_date");
        return rental;
    }

    @Override
    public void add(Rental rental) {
        if (getRental(rental.rentalID) != null) {
            update(rental);
            return;
        }
        String sql = "INSERT INTO rental (id, vehicle_id, user_id, rent_date, is_occupied, has_ended) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, rental.rentalID);
            stmt.setString(2, rental.getVehicleId());
            stmt.setString(3, rental.getUserId());
            stmt.setString(4, rental.rentDate);
            stmt.setBoolean(5, rental.isOccupied);
            stmt.setBoolean(6, rental.hasEnded);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void update(Rental rental) {
        String sql = "UPDATE rental SET is_occupied = ?, has_ended = ?, return_date = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, rental.isOccupied);
            stmt.setBoolean(2, rental.hasEnded);
            stmt.setString(3, rental.returnDate);
            stmt.setString(4, rental.rentalID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public List<Rental> getRentals() {
        List<Rental> rentals = new ArrayList<>();
        String sql = "SELECT * FROM rental";
        Connection connection = getConnection();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                rentals.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
        return rentals;
    }

    @Override
    public void remove(Rental rental) {
        String sql = "DELETE FROM rental WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, rental.rentalID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Rental getRental(String rentalID) {
        String sql = "SELECT * FROM rental WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, rentalID);
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
}