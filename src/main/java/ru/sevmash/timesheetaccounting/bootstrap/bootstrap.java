package ru.sevmash.timesheetaccounting.bootstrap;

import net.datafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.sevmash.timesheetaccounting.domain.Person;
import ru.sevmash.timesheetaccounting.domain.TimeSheet;
import ru.sevmash.timesheetaccounting.domain.TypesOfTimeEmun;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;
import ru.sevmash.timesheetaccounting.repository.TimeSheetRepository;

import java.util.*;

@Component
public class bootstrap implements CommandLineRunner {
    private final PersonRepository personRepository;
    private final TimeSheetRepository timeSheetRepository;

    public bootstrap(PersonRepository personRepository, TimeSheetRepository timeSheetRepository) {
        this.personRepository = personRepository;
        this.timeSheetRepository = timeSheetRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("loadData");
        loadPersons();


    }

    private TimeSheet loadTimeSheets(Person person) {

        Faker faker = new Faker(new Locale("ru"));
        TimeSheet timeSheet = new TimeSheet();
        timeSheet.setHours((byte) faker.number().numberBetween(1, 8));
        timeSheet.setNotes(faker.lorem().maxLengthSentence(150));
        timeSheet.setPerson(person);
        timeSheet.setFileName(faker.file().fileName());
        timeSheet.setTypes(TypesOfTimeEmun.randomType());

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -2);
        Date stratDate = calendar.getTime();
        calendar.add(Calendar.YEAR, 2);
        calendar.add(Calendar.MONTH, -1);
        Date endDate = calendar.getTime();
        timeSheet.setDate(faker.date().between(stratDate, endDate));
        return timeSheet;
    }

    private void loadPersons() {
        //TODO переписать на стримах
        for (int i = 1; i <= 10; i++) {
            getFakePerson();
        }
        System.out.println("Person loaded");
    }

    private Person getFakePerson() {
        Person person = new Person();
        Faker faker = new Faker(new Locale("ru"));
        String[] fio = faker.name().nameWithMiddle().split(" ");
        person.setFirstName(fio[2]);
        person.setOtchestvo(fio[1]);
        person.setSecondName(fio[0]);
        person.setDateOfBirth(new Date(faker.date().birthday(15, 78).getTime()));
        person.setPersonNumber(faker.number().numberBetween(1, 5));
        personRepository.save(person);
        System.out.println(person);

        //TODO переписать на стримах
        for (int i = 1; i < 10; i++) {
            TimeSheet timeSheet = loadTimeSheets(person);
            timeSheetRepository.save(timeSheet);
            System.out.println(timeSheet);
        }

        return person;

    }
}
