package ua.com.foxminded.task.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.foxminded.task.config.TestConfig;
import ua.com.foxminded.task.config.spring.mvc.WebConfig;

@SpringJUnitWebConfig(classes = { WebConfig.class, TestConfig.class })
public class MainControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getAccount() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/")).andExpect(status().isOk()).andDo(print()).andExpect(view().name("main")).andReturn();
        String expectedTitle = "Thymeleaf+SpringMVC University";
        String actuallyTitle = result.getRequest().getAttribute("title").toString();
        Assert.assertEquals(actuallyTitle, expectedTitle);
    }

}
