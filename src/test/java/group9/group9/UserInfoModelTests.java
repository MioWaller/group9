package group9.group9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;

@SpringBootTest
public class UserInfoModelTests {

    @Test
    void gettersAndSettersWork() {
        UserInfoModel m = new UserInfoModel();

        assertThat(m).isNotNull();

        m.setPassword("password");

        assertEquals(m.getPassword(), "password");
    }
    
}
