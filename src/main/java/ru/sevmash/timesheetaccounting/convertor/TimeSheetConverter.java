package ru.sevmash.timesheetaccounting.convertor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.sevmash.timesheetaccounting.domain.TimeSheetDto;
import ru.sevmash.timesheetaccounting.domain.TimeSheetEntity;

@Component
public class TimeSheetConverter {

    private final ModelMapper modelMapper;

    public TimeSheetConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public TimeSheetDto toDto(TimeSheetEntity timeSheetEntity) {

//        return modelMapper.map(timeSheetEntity, TimeSheetDto.class);
//        TypeMap<TimeSheetEntity, TimeSheetDto> propertyMapper = modelMapper.createTypeMap(TimeSheetEntity.class, TimeSheetDto.class);
//        propertyMapper.addMappings(mapper -> mapper.skip(TimeSheetDto::getNotes));
//        TypeMap<TimeSheetEntity, TimeSheetDto> propertyMapper = modelMapper.createTypeMap(TimeSheetEntity.class, TimeSheetDto.class);
//        Converter<TimeSheetEntity, Long> timeSheetEntityLongConverter = p -> p.getPerson().getId();
        TimeSheetDto timeSheetDto = modelMapper.map(timeSheetEntity, TimeSheetDto.class);
        timeSheetDto.setPerson_id(timeSheetEntity.getPerson().getId());
        return timeSheetDto;
    }

    public TimeSheetEntity toEntity(TimeSheetDto timeSheetDto) {
        return modelMapper.map(timeSheetDto, TimeSheetEntity.class);
    }
}
