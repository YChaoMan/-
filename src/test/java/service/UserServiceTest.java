package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.service.UserService;

@ContextConfiguration(locations={"classpath:spring/spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private UserService userService;
    
//    @Test
    public void testQueryAll() {
//        userService.queryById(36);
    }
    
    @Test
    public void testQueryById() {
//        userService.queryById(36);
    }
    
}
