package ru.sevmash.timesheetaccounting;

import net.datafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Locale;

@SpringBootApplication
public class TimesheetAccountingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TimesheetAccountingApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Faker getFaker(){
        return new Faker(new Locale("ru"));
    }
}
