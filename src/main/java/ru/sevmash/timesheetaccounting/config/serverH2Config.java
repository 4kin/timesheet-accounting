package ru.sevmash.timesheetaccounting.config;

import org.h2.tools.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;


@Configuration
public class serverH2Config {

    /**
     * Start internal H2 server so we can query the DB from IDE
     * jdbc:h2:tcp://localhost:9092/mem:testdb
     */
    @Bean(initMethod = "start", destroyMethod = "stop")
    public org.h2.tools.Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }
}
