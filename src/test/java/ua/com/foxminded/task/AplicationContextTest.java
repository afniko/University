package ua.com.foxminded.task;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import ua.com.foxminded.task.config.TestConfig;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AplicationContextTest {

    @Test
    public void whenSpringAplicationContextIsBootstrapped_thenNoExceptions() {

    }
}
