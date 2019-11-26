package ua.com.foxminded.task;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import ua.com.foxminded.task.config.TestConfig;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@WebAppConfiguration
public class SpringContextTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {

    }
}
