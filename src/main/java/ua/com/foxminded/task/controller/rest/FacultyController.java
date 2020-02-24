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
import ua.com.foxminded.task.domain.dto.FacultyDto;
import ua.com.foxminded.task.service.FacultyService;

@RestController("facultyRestController")
@RequestMapping("/api")
@Api(description = "Faculty management System", produces = "application/json", consumes = "application/json")
public class FacultyController {

    private Logger logger;
    private FacultyService facultyService;

    @Autowired
    public FacultyController(Logger logger, FacultyService facultyService) {
        this.logger = logger;
        this.facultyService = facultyService;
    }

    @GetMapping(path = "/faculties", produces = "application/json")
    @ApiOperation(value = "View a list of available faculties")
    public List<FacultyDto> getEntities() {
        logger.debug("getEntities()");
        return facultyService.findAllDto();
    }

    @GetMapping(path = "/faculties/{id}", produces = "application/json")
    @ApiOperation(value = "View the faculty by id")
    public FacultyDto getEntityById(@PathVariable("id") int id) {
        logger.debug("getEntityById()");
        return facultyService.findByIdDto(id);
    }

    @PostMapping(path = "/faculties", produces = "application/json")
    @ApiOperation(value = "Create (if id=0) or update the faculty")
    public FacultyDto saveEntity(@Valid @RequestBody FacultyDto facultyDto) {
        logger.debug("saveEntity()");
        if (facultyDto.getId() != 0) {
            facultyDto = facultyService.update(facultyDto);
        } else {
            facultyDto = facultyService.create(facultyDto);
        }
        return facultyDto;
    }

}
