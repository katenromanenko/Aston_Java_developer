package com.example.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.example.util.propertiesFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionDB {
    private static final String DRIVER = "jdbc.driverClassName";
    private static final String URL = "jdbc.url";
    private static final String USERNAME = "jdbc.user";
    private static final String PASSWORD = "jdbc.password";
    private static HikariDataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConnectionDB.class);

    static {
        try {
            Class.forName(propertiesFile.getProperty(DRIVER));
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertiesFile.getProperty(URL));
            config.setUsername(propertiesFile.getProperty(USERNAME));
            config.setPassword(propertiesFile.getProperty(PASSWORD));
            dataSource = new HikariDataSource(config);
        } catch (ClassNotFoundException e) {
            // Запись ошибки в лог с помощью SLF4j
            LOGGER.error("Ошибка при подключении к базе данных", e);
        }
    }


    public static HikariDataSource getHikariDataSource() {
        return dataSource;
    }
}