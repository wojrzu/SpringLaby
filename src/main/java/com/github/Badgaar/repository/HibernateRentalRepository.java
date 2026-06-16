package com.github.Badgaar.repository;

import com.github.Badgaar.model.Rental;
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
public class HibernateRentalRepository implements IRentalRepository {

    @Setter
    private Session session;

    @Override
    public void add(Rental rental) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            session.merge(rental);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(Rental rental) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            session.merge(rental);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<Rental> getRentals() {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<Rental> rentals = session.createQuery("FROM Rental r JOIN FETCH r.user JOIN FETCH r.vehicle", Rental.class).list();
            ts.commit();
            return rentals;
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void remove(Rental rental) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            Rental toRemove = session.find(Rental.class, rental.rentalID);
            if (toRemove != null) session.remove(toRemove);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public Rental getRental(String rentalID) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            Rental rental = session.find(Rental.class, rentalID);
            ts.commit();
            return rental;
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
        return null;
    }
}