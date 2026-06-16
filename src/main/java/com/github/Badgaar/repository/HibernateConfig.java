package com.github.Badgaar.repository;

import com.github.Badgaar.model.Rental;
import com.github.Badgaar.model.User;
import com.github.Badgaar.model.Vehicle;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    private static SessionFactory sessionFactory;

    private HibernateConfig() {}

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            String url = System.getenv("DB_URL");
            String user = System.getenv("DB_USER");
            String password = System.getenv("DB_PASSWORD");

            if (url == null || url.isBlank()) {
                throw new IllegalStateException("DB URL not set");
            }

            Configuration config = new Configuration();
            config.setProperty("hibernate.connection.url", url);

            if (user != null) config.setProperty("hibernate.connection.username", user);
            if (password != null) config.setProperty("hibernate.connection.password", password);

            config.setProperty("hibernate.hbm2ddl.auto", "update");
            config.setProperty("hibernate.show_sql", "false");

            config.addAnnotatedClass(User.class);
            config.addAnnotatedClass(Vehicle.class);
            config.addAnnotatedClass(Rental.class);

            sessionFactory = config.buildSessionFactory();
        }
        return sessionFactory;
    }

    public static void close() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }
}