package ru.sevmash.timesheetaccounting;

import net.datafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import ru.sevmash.timesheetaccounting.aspesct.LoggingAspect;

import java.util.Locale;

@SpringBootApplication
@EnableAspectJAutoProxy
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
        return new Faker(new Locale.Builder().setLanguage("ru").setRegion("RU").build());
    }

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
