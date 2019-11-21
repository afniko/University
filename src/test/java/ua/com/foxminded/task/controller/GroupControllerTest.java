package ua.com.foxminded.task.controller;

import javax.servlet.ServletContext;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ua.com.foxminded.task.config.TestConfig;
import ua.com.foxminded.task.config.spring.mvc.WebConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebConfig.class })
@WebAppConfiguration
public class GroupControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void test() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        
//        Assert.assertNotNull(servletContext);
//        Assert.assertTrue(servletContext instanceof MockServletContext);
//        Assert.assertNotNull(webApplicationContext.getBean("groupController"));
    }

//    @Test
//    public void test2() {
//        mockMvc.perform(get)
//    }

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {

    }

}
