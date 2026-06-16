package com.github.Badgaar.repository;

import com.github.Badgaar.model.Vehicle;
import lombok.Setter;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("hibernate")
public class HibernateVehicleRepository implements IVehicleRepository {

    @Setter
    private Session session;

    @Override
    public List<Vehicle> getVehicles() {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<Vehicle> vehicles = session.createQuery("FROM Vehicle", Vehicle.class).list();
            ts.commit();
            return vehicles;
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void add(Vehicle vehicle) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            session.merge(vehicle);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String id) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            Vehicle vehicle = session.find(Vehicle.class, id);
            if (vehicle != null) session.remove(vehicle);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Vehicle getVehicle(String id) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            Vehicle vehicle = session.find(Vehicle.class, id);
            ts.commit();
            return vehicle;
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Vehicle vehicle) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            session.merge(vehicle);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }
}