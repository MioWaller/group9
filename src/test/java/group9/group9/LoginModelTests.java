package group9.group9;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;

@SpringBootTest
public class LoginModelTests {
    @Test
    void gettersAndSettersWork() {
        LoginModel temp = new LoginModel();

        assertThat(temp).isNotNull();

        temp.setUsername("sparky10");
        temp.setPassword("123");

        assertEquals(temp.getUsername(), "sparky10");
        assertEquals(temp.getPassword(), "123");

    }
}
