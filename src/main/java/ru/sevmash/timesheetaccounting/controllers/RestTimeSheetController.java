package ru.sevmash.timesheetaccounting.controllers;

import org.springframework.web.bind.annotation.*;
import ru.sevmash.timesheetaccounting.domain.TimeSheetDto;
import ru.sevmash.timesheetaccounting.services.TimeSheetService;

import java.util.List;

@RestController
@RequestMapping({"/api/ts","/api/time_sheets"})
public class RestTimeSheetController {

    private final TimeSheetService timeSheetService;

    public RestTimeSheetController(TimeSheetService timeSheetService
    ) {
        this.timeSheetService = timeSheetService;
    }

    @GetMapping("/byPerson/{id}")
    public List<TimeSheetDto> getAllTimeSheetByPersonId(@PathVariable Long id) {
        return timeSheetService.getListTimeSheetByPersonId(id);
    }

    @GetMapping("/byPerson/{id}/deleted")
    public List<TimeSheetDto> getAllTimeSheetDeletedByPersonId(@PathVariable Long id) {
        return timeSheetService.getListTimeSheetDeletedByPersonId(id);
    }

    @GetMapping("/allPerson")
    public List<TimeSheetDto> getAllTimeShits() {
        return timeSheetService.getAll();
    }

    @GetMapping("/{id}")
    private TimeSheetDto getById(@PathVariable Long id) {
        return timeSheetService.getById(id);
    }

    @PostMapping("/person/{id}/addTime")
    public TimeSheetDto addNewTimeByPersonId(@PathVariable Long id, @RequestBody TimeSheetDto timeSheetDto) {
        return timeSheetService.addNewTime(id, timeSheetDto);
    }

    @PutMapping("/person/{id}/time/update")
    public TimeSheetDto updateTimeByPersonId(@PathVariable Long id, @RequestBody TimeSheetDto timeSheetDto) {
        return timeSheetService.updateTime(id, timeSheetDto);
    }

    @DeleteMapping("{id}")
    public TimeSheetDto deleteTimeByPersonId(@PathVariable Long id) {
        return timeSheetService.deleteTime(id);
    }

    @GetMapping("/{id}/restore")
    public TimeSheetDto restoreTime(@PathVariable Long id) {
        return timeSheetService.restoreDeletedTime(id);
    }
}
