package ru.sevmash.timesheetaccounting.bootstrap;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.domain.TimeSheetEntity;
import ru.sevmash.timesheetaccounting.domain.TypesOfTimeEmun;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;
import ru.sevmash.timesheetaccounting.repository.TimeSheetRepository;

import java.sql.Date;
import java.util.Calendar;
import java.util.Locale;

@Component
public class bootstrap implements CommandLineRunner {
    private final PersonRepository personRepository;
    private final TimeSheetRepository timeSheetRepository;

    public bootstrap(PersonRepository personRepository, TimeSheetRepository timeSheetRepository) {
        this.personRepository = personRepository;
        this.timeSheetRepository = timeSheetRepository;
    }

    @Override
    public void run(String... args) {
        System.out.println("Генерация и загрузка данных ...");
        loadPersons();
        System.out.println("Загруженно людей " + personRepository.findAll().size());
        System.out.println("Загруженно записей " + timeSheetRepository.findAll().size());
        System.out.println("... OK ...");

    }

    private TimeSheetEntity loadTimeSheets(PersonEntity person) {


        Faker faker = new Faker(new Locale.Builder().setLanguage("ru").setRegion("RU").build());
        TimeSheetEntity timeSheetEntity = new TimeSheetEntity();
        timeSheetEntity.setHours((byte) faker.number().numberBetween(1, 8));
        timeSheetEntity.setNotes(faker.lorem().maxLengthSentence(150));
        timeSheetEntity.setPerson(person);
        timeSheetEntity.setDeleted(faker.bool().bool());

        timeSheetEntity.setFileName(faker.file().fileName());
        timeSheetEntity.setTypes(TypesOfTimeEmun.randomType());

        java.util.Date date = new java.util.Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -2);
        java.util.Date stratDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 2);
        calendar.add(Calendar.MONTH, -1);
        java.util.Date endDate = calendar.getTime();
        timeSheetEntity.setDate(faker.date().between(stratDate, endDate));
        return timeSheetEntity;
    }

    private void loadPersons() {
        //TODO переписать на стримах
        for (int i = 1; i <= 10; i++) {
            getFakePerson();
        }
//        System.out.println("Person loaded");
    }

    private PersonEntity getFakePerson() {
        PersonEntity person = new PersonEntity();
        Faker faker = new Faker(new Locale.Builder().setLanguage("ru").setRegion("RU").build());
        String[] fio = faker.name().nameWithMiddle().split(" ");
        person.setFirstName(fio[2]);
        person.setMiddleName(fio[1]);
        person.setSecondName(fio[0]);
        person.setDeleted(faker.bool().bool());
        person.setDateOfBirth(new Date(faker.date().birthday(15, 78).getTime()));
        person.setPersonNumber(faker.number().numberBetween(1, 300));
        personRepository.save(person);
//        System.out.println(person);

        //TODO переписать на стримах
        for (int i = 1; i < 10; i++) {
            TimeSheetEntity timeSheetEntity = loadTimeSheets(person);
            timeSheetRepository.save(timeSheetEntity);
//            System.out.println(timeSheet);
        }

        return person;

    }
}
