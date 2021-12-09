package group9.group9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UserEntityTests {
    @Test
    void gettersAndSettersWork() {
        UserEntity entity = new UserEntity();

        assertThat(entity).isNotNull();

        entity.setUsername("group9_username");
        entity.setPassword("group9password");
        entity.setUserId(9);

        assertEquals(entity.getUsername(), "group9_username");
        assertEquals(entity.getPassword(), "group9password");
        assertEquals(entity.getId(), 9);
        assertEquals(entity.toString(), "UserEntity [password=" + "group9password" + ", user_id (aka. id) =" + 9 + ", username="
        + "group9_username" + "]");
    }
    
}
