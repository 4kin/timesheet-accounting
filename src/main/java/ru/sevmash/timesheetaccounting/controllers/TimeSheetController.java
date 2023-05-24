package ru.sevmash.timesheetaccounting.controllers;

import org.springframework.web.bind.annotation.*;
import ru.sevmash.timesheetaccounting.domain.TimeSheetDto;
import ru.sevmash.timesheetaccounting.services.TimeSheetService;

import java.util.List;

@RestController
public class TimeSheetController {

    private final TimeSheetService timeSheetService;

    public TimeSheetController(TimeSheetService timeSheetService
    ) {
        this.timeSheetService = timeSheetService;
    }

    @GetMapping("/person/{id}/time_sheet")
    public List<TimeSheetDto> getAllTimeSheetByPersonId(@PathVariable Long id) {
        return timeSheetService.getListTimeSheetByPersonId(id);
    }

    @GetMapping("/person/{id}/time_sheet/deleted")
    public List<TimeSheetDto> getAllTimeSheetDeletedByPersonId(@PathVariable Long id) {
        return timeSheetService.getListTimeSheetDeletedByPersonId(id);
    }

    @GetMapping("/time_sheets")
    public List<TimeSheetDto> getAllTimeShits() {
        return timeSheetService.getAll();
    }

    @GetMapping("/time/{id}")
    public TimeSheetDto getById(@PathVariable Long id) {
        return timeSheetService.getById(id);
    }

    @PostMapping("/person/{id}/time/new")
    public TimeSheetDto addNewTimeByPersonId(@PathVariable Long id, @RequestBody TimeSheetDto timeSheetDto) {
        return timeSheetService.addNewTime(id, timeSheetDto);
    }

    @PostMapping("/person/{id}/time/update")
    public TimeSheetDto updateTimeByPersonId(@PathVariable Long id, @RequestBody TimeSheetDto timeSheetDto) {
        return timeSheetService.updateTime(id, timeSheetDto);
    }

    @GetMapping("/person/{id}/time/{id_time}/delete")
    public TimeSheetDto deleteTimeByPersonId(@PathVariable Long id, @PathVariable Long id_time) {
        return timeSheetService.setDeletedTime(id, id_time);
    }

    @GetMapping("/person/{id}/time/{id_time}/restore")
    public TimeSheetDto restoreTimeByPersonId(@PathVariable Long id, @PathVariable Long id_time) {
        return timeSheetService.setUnDeletedTime(id, id_time);
    }
}
