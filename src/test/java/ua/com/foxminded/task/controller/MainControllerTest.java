package ua.com.foxminded.task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ua.com.foxminded.task.config.TestConfig;
import ua.com.foxminded.task.config.spring.mvc.WebConfig;

@SpringJUnitWebConfig(classes = { WebConfig.class, TestConfig.class })
public class MainControllerTest {

    private MockMvc mockMvc;
    private Logger logger = LoggerFactory.getLogger(StudentController.class);
    private MainController mainController = new MainController(logger);

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(mainController).build();
    }

    @Test
    void whenRetriveHttpRequestSlash_thenExpectViewNameMainWithAttribute() throws Exception {
        String expectedViewName = "main";
        MvcResult mvcResult = this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(view().name(expectedViewName))
                .andReturn();
        String expectedTitle = "Thymeleaf+SpringMVC University";
        String actuallyTitle = mvcResult.getRequest().getAttribute("title").toString();
        Assert.assertEquals(expectedTitle, actuallyTitle);
    }

}
