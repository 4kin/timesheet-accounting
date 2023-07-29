package ru.sevmash.timesheetaccounting.services;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.sevmash.timesheetaccounting.convertor.TimeSheetConverter;
import ru.sevmash.timesheetaccounting.domain.PersonEntity;
import ru.sevmash.timesheetaccounting.domain.TimeSheetDto;
import ru.sevmash.timesheetaccounting.domain.TimeSheetEntity;
import ru.sevmash.timesheetaccounting.repository.PersonRepository;
import ru.sevmash.timesheetaccounting.repository.TimeSheetRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TimeSheetService {
    private final TimeSheetRepository timeSheetRepository;
    private final TimeSheetConverter timeSheetConverter;
    private final PersonRepository personRepository;


    public TimeSheetService(TimeSheetRepository timeSheetRepository, TimeSheetConverter timeSheetConverter, PersonRepository personRepository) {
        this.timeSheetRepository = timeSheetRepository;
        this.timeSheetConverter = timeSheetConverter;
        this.personRepository = personRepository;
    }


    public List<TimeSheetDto> getListTimeSheetByPersonId(Long id) {
        return timeSheetRepository.findAllByPerson_IdAndDeletedIsTrue(id, Sort.by(Sort.Direction.ASC, "date")).stream().map(timeSheetConverter::toDto).collect(Collectors.toList());
//        return timeSheetRepository.findByPersonId(id, Sort.by("date")).stream().map(timeSheetConverter::toDto).collect(Collectors.toList());
    }

    public List<TimeSheetDto> getDeletedListTimeSheetByPersonId(Long id) {
        return timeSheetRepository
                .findAllByPerson_IdAndDeletedIsFalse(id, Sort.by(Sort.Direction.ASC, "date"))
                .stream()
                .map(timeSheetConverter::toDto)
                .collect(Collectors.toList());
    }

    public List<TimeSheetDto> getAll() {
//        return timeSheetRepository.findAll().stream().map(timeSheetConverter::toDto).collect(Collectors.toList());
        return timeSheetRepository
                .findAllByDeletedIsFalse()
                .stream()
                .map(timeSheetConverter::toDto)
                .collect(Collectors.toList());
    }

    public TimeSheetDto getById(Long id) {
        TimeSheetEntity timeSheetEntity = timeSheetRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Time id = " + id + " Not Found"));
        return timeSheetConverter.toDto(timeSheetEntity);
    }

    public TimeSheetDto addNewTime(Long id, TimeSheetDto timeSheetDto) {
        // новая запись
        return convert(id, timeSheetDto);
    }

    public TimeSheetDto updateTime(Long id, TimeSheetDto timeSheetDto) {
        Optional<TimeSheetEntity> optionalTimeSheetEntity = timeSheetRepository.findById(timeSheetDto.getId());

        if (optionalTimeSheetEntity.isPresent()) {
            return convert(id, timeSheetDto);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Time id = " + id + " Not Found");
        }
    }

    private TimeSheetDto convert(Long id, TimeSheetDto timeSheetDto) {
        TimeSheetEntity timeSheetEntity = timeSheetConverter.toEntity(timeSheetDto);
        timeSheetEntity.setDate(new Timestamp((new Date()).getTime()));

        PersonEntity personEntity = personRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person id = " + id + " Not Found"));
        timeSheetEntity.setPerson(personEntity);

        TimeSheetEntity savedTimeSheet = timeSheetRepository.save(timeSheetEntity);
        return timeSheetConverter.toDto(savedTimeSheet);
    }


    public TimeSheetDto deleteTime(Long id) {
        return deleteTimeSheetDtoById(id, true);
    }

    public TimeSheetDto restoreDeletedTime(Long id) {
        return deleteTimeSheetDtoById(id, false);
    }

    private TimeSheetDto deleteTimeSheetDtoById(Long idTime, boolean deleted) {
//        TimeSheetEntity timeSheetEntity = timeSheetRepository.findById(idTime).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Time not FOUND"));
//        timeSheetEntity.setDeleted(deleted);
//        return timeSheetConverter.toDto(timeSheetRepository.save(timeSheetEntity));
        timeSheetRepository.setTimeSheetEntityIsDeleted(idTime, deleted);
        TimeSheetEntity timeSheetEntity = timeSheetRepository.findById(idTime).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Time not FOUND"));
        return timeSheetConverter.toDto(timeSheetEntity);
    }

    public List<TimeSheetDto> getListTimeSheetDeletedByPersonId(Long id) {
        return timeSheetRepository.findAllByPerson_IdAndDeletedIsTrue(id, Sort.by("date")).stream().map(timeSheetConverter::toDto).collect(Collectors.toList());
    }
}
