package ua.com.foxminded.task.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = { "ua.com.foxminded.task" })
@EnableAspectJAutoProxy
public class TestConfig {
}
