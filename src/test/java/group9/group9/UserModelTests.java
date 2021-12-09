package group9.group9;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;

@SpringBootTest
public class UserModelTests {

    @Test
    void gettersAndSettersWork() {
        UserModel m = new UserModel();

        assertThat(m).isNotNull();

        m.setUsername("group9");
        m.setPassword("password");

        assertEquals(m.getUsername(), "group9");
        assertEquals(m.getPassword(), "password");

    }
    
}
