package com.github.Badgaar.repository;

import com.github.Badgaar.model.User;
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
public class HibernateUserRepository implements IUserRepository {

    @Setter
    private Session session;

    @Override
    public User getUser(String login) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            User user = session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            ts.commit();
            return user;
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getUserById(String id) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            User user = session.find(User.class, id);
            ts.commit();
            return user;
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<User> getUsers() {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            List<User> users = session.createQuery("FROM User", User.class).list();
            ts.commit();
            return users;
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void add(User user) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            session.merge(user);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void update(User user) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            session.merge(user);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String login) {
        Transaction ts = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            ts = session.beginTransaction();
            setSession(session);
            User user = session.createQuery("FROM User WHERE login = :login", User.class)
                    .setParameter("login", login)
                    .uniqueResult();
            if (user != null) session.remove(user);
            ts.commit();
        } catch (SessionException e) {
            if (ts != null && ts.isActive()) ts.rollback();
            e.printStackTrace();
        }
    }
}