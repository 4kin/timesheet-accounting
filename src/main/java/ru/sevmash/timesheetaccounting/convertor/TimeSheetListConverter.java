package ru.sevmash.timesheetaccounting.convertor;

import org.modelmapper.ModelMapper;
import ru.sevmash.timesheetaccounting.domain.TimeSheetDto;
import ru.sevmash.timesheetaccounting.domain.TimeSheetEntity;

import java.util.List;
import java.util.stream.Collectors;

public class TimeSheetListConverter {

    private final ModelMapper modelMapper;

    public TimeSheetListConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<TimeSheetDto> toDto(List<TimeSheetEntity> listTimeSheetEntity) {

        return listTimeSheetEntity.stream().map(
                tse -> modelMapper.map(tse, TimeSheetDto.class))
                .collect(Collectors.toList());

    }

    public TimeSheetEntity toEntity(TimeSheetDto timeSheetDto) {
        return modelMapper.map(timeSheetDto, TimeSheetEntity.class);
    }
}
