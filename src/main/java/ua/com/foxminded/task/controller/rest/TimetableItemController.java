package ua.com.foxminded.task.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ua.com.foxminded.task.domain.dto.TimetableItemDto;
import ua.com.foxminded.task.service.TimetableItemService;

@RestController("timetableItemRestController")
@RequestMapping("/api")
@Api(description = "Timetable item management System", produces = "application/json", consumes = "application/json")
public class TimetableItemController {

    private Logger logger;
    private TimetableItemService timetableItemService;

    @Autowired
    public TimetableItemController(Logger logger, TimetableItemService timetableItemService) {
        this.logger = logger;
        this.timetableItemService = timetableItemService;
    }

    @GetMapping(path = "/timetable-items", produces = "application/json")
    @ApiOperation(value = "View a list of available timetable items")
    public List<TimetableItemDto> getEntities() {
        logger.debug("getEntities()");
        return timetableItemService.findAllDto();
    }

    @GetMapping(path = "/timetable-items/{id}", produces = "application/json")
    @ApiOperation(value = "View the timetable item by id")
    public TimetableItemDto getEntityById(@PathVariable("id") int id) {
        logger.debug("getEntityById()");
        return timetableItemService.findByIdDto(id);
    }

    @PostMapping(path = "/timetable-items", produces = "application/json")
    @ApiOperation(value = "Create (if id=0) or update the timetable item")
    public TimetableItemDto saveEntity(@Valid @RequestBody TimetableItemDto timetableItemDto) {
        logger.debug("saveEntity()");
        if (timetableItemDto.getId() != 0) {
            timetableItemDto = timetableItemService.update(timetableItemDto);
        } else {
            timetableItemDto = timetableItemService.create(timetableItemDto);
        }
        return timetableItemDto;
    }

}
