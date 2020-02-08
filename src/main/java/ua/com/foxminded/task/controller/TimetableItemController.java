package ua.com.foxminded.task.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ua.com.foxminded.task.dao.exception.EntityAlreadyExistsException;
import ua.com.foxminded.task.dao.exception.EntityNotValidException;
import ua.com.foxminded.task.domain.dto.AuditoryDto;
import ua.com.foxminded.task.domain.dto.GroupDto;
import ua.com.foxminded.task.domain.dto.LectureDto;
import ua.com.foxminded.task.domain.dto.SubjectDto;
import ua.com.foxminded.task.domain.dto.TeacherDto;
import ua.com.foxminded.task.domain.dto.TimetableItemDto;
import ua.com.foxminded.task.service.AuditoryService;
import ua.com.foxminded.task.service.GroupService;
import ua.com.foxminded.task.service.LectureService;
import ua.com.foxminded.task.service.SubjectService;
import ua.com.foxminded.task.service.TeacherService;
import ua.com.foxminded.task.service.TimetableItemService;

@Controller
public class TimetableItemController {

    private static final String PATH_HTML_TIMETABLEITEM = "timetable-item/timetable-item";
    private static final String PATH_HTML_TIMETABLEITEMS = "timetable-item/timetable-items";
    private static final String PATH_HTML_TIMETABLEITEM_EDIT = "timetable-item/timetable-item_edit";
    private static final String ATTRIBUTE_HTML_TITLE = "title";
    private static final String ATTRIBUTE_HTML_TIMETABLEITEM = "timetableItemDto";
    private static final String ATTRIBUTE_HTML_TIMETABLEITEMS = "timetableItems";
    private static final String ATTRIBUTE_HTML_SUBJECTS = "subjects";
    private static final String ATTRIBUTE_HTML_AUDITORIES = "auditories";
    private static final String ATTRIBUTE_HTML_GROUPS = "groups";
    private static final String ATTRIBUTE_HTML_LECTURIES = "lecturies";
    private static final String ATTRIBUTE_HTML_TEACHERS = "teachers";
    private static final String ATTRIBUTE_HTML_ERROR_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_HTML_SUCCESS_MESSAGE = "successMessage";
    private Logger logger;
    private TimetableItemService timetableItemService;
    private SubjectService subjectService;
    private AuditoryService auditoryService;
    private GroupService groupService;
    private LectureService lectureService;
    private TeacherService teacherService;

    @Autowired
    public TimetableItemController(Logger logger, 
                                   TimetableItemService timetableItemService, 
                                   SubjectService subjectService, 
                                   AuditoryService auditoryService, 
                                   GroupService groupService,
                                   LectureService lectureService, 
                                   TeacherService teacherService) {
        this.logger = logger;
        this.timetableItemService = timetableItemService;
        this.subjectService = subjectService;
        this.auditoryService = auditoryService;
        this.groupService = groupService;
        this.lectureService = lectureService;
        this.teacherService = teacherService;
    }
    
    @GetMapping("/timetable-items")
    public String getEntities(Model model) {
        logger.debug("getEntities()");
        List<TimetableItemDto> timetableItems = timetableItemService.findAllDto();

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Timetable item");
        model.addAttribute(ATTRIBUTE_HTML_TIMETABLEITEMS, timetableItems);
        return PATH_HTML_TIMETABLEITEMS;
    }

    @GetMapping("/timetable-item")
    public String getEntityById(@RequestParam("id") String idString, Model model) {
        logger.debug("getEntityById()");
        String errorMessage = null;
        TimetableItemDto timetableItemDto = null;
        int id = 0;
        try {
            if (checkId(idString)) {
                id = Integer.valueOf(idString);
                timetableItemDto = timetableItemService.findByIdDto(id);
            } else {
                errorMessage = "You id is blank";
            }
        } catch (EntityNotFoundException e) {
            errorMessage = "Timetable item by id#" + id + " not found!";
        } catch (NumberFormatException e) {
            errorMessage = "Timetable item id# must be numeric!";
        }
        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Timetable item");
        model.addAttribute(ATTRIBUTE_HTML_TIMETABLEITEM, timetableItemDto);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_TIMETABLEITEM;
    }

    @GetMapping("/timetable-item_edit")
    public String editGetEntity(@RequestParam(name = "id", required = false) String id, Model model) {
        logger.debug("editGetEntity(), id: {}", id);
        String errorMessage = null;
        TimetableItemDto timetableItemDto = new TimetableItemDto();
        try {
            if (checkId(id)) {
                timetableItemDto = timetableItemService.findByIdDto(Integer.valueOf(id));
            }
        } catch (EntityNotFoundException e) {
            errorMessage = "Problem with finding timetable item";
        }
        List<SubjectDto> subjects = subjectService.findAllDto();
        List<AuditoryDto> auditories = auditoryService.findAllDto();
        List<GroupDto> groups = groupService.findAllDto();
        List<LectureDto> lecturies = lectureService.findAllDto();
        List<TeacherDto> teachers = teacherService.findAllDto();

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Timetable item edit");
        model.addAttribute(ATTRIBUTE_HTML_TIMETABLEITEM, timetableItemDto);
        model.addAttribute(ATTRIBUTE_HTML_SUBJECTS, subjects);
        model.addAttribute(ATTRIBUTE_HTML_AUDITORIES, auditories);
        model.addAttribute(ATTRIBUTE_HTML_GROUPS, groups);
        model.addAttribute(ATTRIBUTE_HTML_LECTURIES, lecturies);
        model.addAttribute(ATTRIBUTE_HTML_TEACHERS, teachers);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        return PATH_HTML_TIMETABLEITEM_EDIT;
    }

    @PostMapping("/timetable-item_edit")
    public String editPostEntity(@Valid @ModelAttribute("timetableItemDto") TimetableItemDto timetableItemDto, 
                                 BindingResult bindingResult, 
                                 Model model) {
        logger.debug("editPost()");
        String errorMessage = null;
        String successMessage = null;
        List<SubjectDto> subjects = null;
        List<AuditoryDto> auditories = null;
        List<GroupDto> groups = null;
        List<LectureDto> lecturies = null;
        List<TeacherDto> teachers = null;
        String path = PATH_HTML_TIMETABLEITEM;

        if (!bindingResult.hasErrors()) {
            try {
                if (timetableItemDto.getId() != 0) {
                    timetableItemDto = timetableItemService.update(timetableItemDto);
                    successMessage = "Record timetable item was updated!";
                } else {
                    timetableItemDto = timetableItemService.create(timetableItemDto);
                    successMessage = "Record timetable item was created";
                }
            } catch (EntityAlreadyExistsException e) {
                errorMessage = "Record timetable item was not created! The record already exists!";
                path = PATH_HTML_TIMETABLEITEM_EDIT;
            } catch (EntityNotFoundException e) {
                errorMessage = "Timetable item " + timetableItemDto + " not found!";
                path = PATH_HTML_TIMETABLEITEM_EDIT;
            } catch (EntityNotValidException e) {
                errorMessage = "Record timetable item was not updated/created! The data is not valid!";
                path = PATH_HTML_TIMETABLEITEM_EDIT;
            }
        } else {
            errorMessage = "You enter incorrect data!";
            path = PATH_HTML_TIMETABLEITEM_EDIT;
            subjects = subjectService.findAllDto();
            auditories = auditoryService.findAllDto();
            groups = groupService.findAllDto();
            lecturies = lectureService.findAllDto();
            teachers = teacherService.findAllDto();
        }

        model.addAttribute(ATTRIBUTE_HTML_TITLE, "Timetable item edit");
        model.addAttribute(ATTRIBUTE_HTML_TIMETABLEITEM, timetableItemDto);
        model.addAttribute(ATTRIBUTE_HTML_SUBJECTS, subjects);
        model.addAttribute(ATTRIBUTE_HTML_AUDITORIES, auditories);
        model.addAttribute(ATTRIBUTE_HTML_GROUPS, groups);
        model.addAttribute(ATTRIBUTE_HTML_LECTURIES, lecturies);
        model.addAttribute(ATTRIBUTE_HTML_TEACHERS, teachers);
        model.addAttribute(ATTRIBUTE_HTML_ERROR_MESSAGE, errorMessage);
        model.addAttribute(ATTRIBUTE_HTML_SUCCESS_MESSAGE, successMessage);
        return path;
    }

    private boolean checkId(String id) {
        return StringUtils.isNoneBlank(id);
    }

}
