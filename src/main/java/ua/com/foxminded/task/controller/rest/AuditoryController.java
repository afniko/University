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
import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.service.AuditoryService;

@RestController("auditoryRestController")
@RequestMapping("/api")
@Api(description = "Auditory management System", produces = "application/json", consumes = "application/json")
public class AuditoryController {

    private Logger logger;
    private AuditoryService auditoryService;

    @Autowired
    public AuditoryController(Logger logger, AuditoryService studentService) {
        this.logger = logger;
        this.auditoryService = studentService;
    }

    @GetMapping(path = "/auditories", produces = "application/json")
    @ApiOperation(value = "View a list of available auditories")
    public List<AuditoryDto> getEntities() {
        logger.debug("getEntities()");
        return auditoryService.findAllDto();
    }

    @GetMapping(path = "/auditories/{id}", produces = "application/json")
    @ApiOperation(value = "View the auditory by id")
    public AuditoryDto getEntityById(@PathVariable("id") int id) {
        logger.debug("getEntityById()");
        return auditoryService.findByIdDto(id);
    }

    @PostMapping(path = "/auditories", produces = "application/json")
    @ApiOperation(value = "Create (if id=0) or update a auditory")
    public AuditoryDto saveEntity(@Valid @RequestBody AuditoryDto auditoryDto) {
        logger.debug("saveEntity()");
        if (auditoryDto.getId() != 0) {
            auditoryDto = auditoryService.update(auditoryDto);
        } else {
            auditoryDto = auditoryService.create(auditoryDto);
        }
        return auditoryDto;
    }

}
