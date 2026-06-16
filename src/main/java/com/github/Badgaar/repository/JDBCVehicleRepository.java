package com.github.Badgaar.repository;

import com.github.Badgaar.model.Vehicle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Profile("jdbc")
public class JDBCVehicleRepository implements IVehicleRepository {

    private final Gson gson = new Gson();
    private final Type attributesType = new TypeToken<Map<String, Object>>() {}.getType();
    private final DataSource dataSource;

    public JDBCVehicleRepository(DataSource dataSource) {
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
                CREATE TABLE IF NOT EXISTS vehicle (
                    id TEXT PRIMARY KEY,
                    category TEXT NOT NULL,
                    brand TEXT NOT NULL,
                    model TEXT NOT NULL,
                    year INTEGER NOT NULL,
                    price REAL NOT NULL,
                    plate TEXT NOT NULL,
                    is_rented INTEGER NOT NULL DEFAULT 0,
                    attributes TEXT
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

    private Vehicle mapRow(ResultSet rs) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.id = rs.getString("id");
        vehicle.category = rs.getString("category");
        vehicle.brand = rs.getString("brand");
        vehicle.model = rs.getString("model");
        vehicle.year = rs.getInt("year");
        vehicle.price = rs.getDouble("price");
        vehicle.plate = rs.getString("plate");
        vehicle.isRented = rs.getBoolean("is_rented");
        String attributesJson = rs.getString("attributes");
        if (attributesJson != null) {
            Map<String, Object> attrs = gson.fromJson(attributesJson, attributesType);
            vehicle.attributes = attrs != null ? attrs : new HashMap<>();
        }
        return vehicle;
    }

    @Override
    public List<Vehicle> getVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        String sql = "SELECT * FROM vehicle";
        Connection connection = getConnection();
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                vehicles.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
        return vehicles;
    }

    @Override
    public void add(Vehicle vehicle) {
        if (getVehicle(vehicle.id) != null) {
            update(vehicle);
            return;
        }
        String sql = "INSERT INTO vehicle (id, category, brand, model, year, price, plate, is_rented, attributes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?::jsonb)";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vehicle.id);
            stmt.setString(2, vehicle.category);
            stmt.setString(3, vehicle.brand);
            stmt.setString(4, vehicle.model);
            stmt.setInt(5, vehicle.year);
            stmt.setDouble(6, vehicle.price);
            stmt.setString(7, vehicle.plate);
            stmt.setBoolean(8, vehicle.isRented);
            stmt.setString(9, gson.toJson(vehicle.attributes != null ? vehicle.attributes : new HashMap<>()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public void remove(String id) {
        String sql = "DELETE FROM vehicle WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }

    @Override
    public Vehicle getVehicle(String id) {
        String sql = "SELECT * FROM vehicle WHERE id = ?";
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
    public void update(Vehicle vehicle) {
        String sql = "UPDATE vehicle SET category = ?, brand = ?, model = ?, year = ?, price = ?, plate = ?, is_rented = ?, attributes = ? WHERE id = ?";
        Connection connection = getConnection();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, vehicle.category);
            stmt.setString(2, vehicle.brand);
            stmt.setString(3, vehicle.model);
            stmt.setInt(4, vehicle.year);
            stmt.setDouble(5, vehicle.price);
            stmt.setString(6, vehicle.plate);
            stmt.setBoolean(7, vehicle.isRented);
            stmt.setString(8, gson.toJson(vehicle.attributes));
            stmt.setString(9, vehicle.id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            releaseConnection(connection);
        }
    }
}