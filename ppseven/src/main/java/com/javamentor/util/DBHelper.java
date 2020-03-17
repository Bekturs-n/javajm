package com.javamentor.util;

import com.javamentor.model.Role;
import com.javamentor.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

public class DBHelper {

    private static volatile DBHelper instance;
    private static SessionFactory sessionFactory;

    private DBHelper() {
    }

    public static DBHelper getInstance(){
        if(instance ==  null){
            synchronized (DBHelper.class) {
                if (instance == null) {
                    instance = new DBHelper();
                }
            }
        }
        return instance;
    }

    public SessionFactory getConfiguration() {
        if (sessionFactory == null) {
            sessionFactory = createSessionFactory();
        }
        return sessionFactory;
    }

    public static Configuration getMySQLConfiguration() {

        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Role.class);
        Properties properties = PropertyReader.getProperties("hibernate.properties");
        configuration.setProperty("hibernate.dialect", properties.getProperty("dialect"))
                .setProperty("hibernate.connection.driver_class", properties.getProperty("connection.driver_class"))
                .setProperty("hibernate.connection.url", properties.getProperty("connection.url"))
                .setProperty("hibernate.connection.username", properties.getProperty("connection.username"))
                .setProperty("hibernate.connection.password", properties.getProperty("connection.password"))
                .setProperty("hibernate.show_sql", properties.getProperty("show_sql"))
                .setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hbm2ddl.auto"));
        return configuration;
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = getMySQLConfiguration();
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}
