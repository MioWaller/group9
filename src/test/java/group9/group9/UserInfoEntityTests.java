package group9.group9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class UserInfoEntityTests {
    @Test
    void gettersAndSettersWork() {
        UserInfoEntity entity = new UserInfoEntity();

        assertThat(entity).isNotNull();

        entity.setFullName("fullName");
        entity.setEmail("group9@gmail.com");
        entity.setPhone("0000000000");
        entity.setAddress1("0000 group9 dr");
        entity.setAddress2("");
        entity.setCity("houston");
        entity.setState("TX");
        entity.setZipcode("00000");
        entity.setBonusPoints(10);
        entity.setHold(1);
        entity.setUserId(9);
        entity.setId(10);

        assertEquals(entity.getFullName(), "fullName");
        assertEquals(entity.getEmail(), "group9@gmail.com");
        assertEquals(entity.getPhone(), "0000000000");
        assertEquals(entity.getAddress1(), "0000 group9 dr");
        assertEquals(entity.getAddress2(), "");
        assertEquals(entity.getCity(), "houston");
        assertEquals(entity.getState(), "TX");
        assertEquals(entity.getZipcode(), "00000");
        assertEquals(entity.getBonusPoints(), 10);
        assertEquals(entity.getHold(), 1);
        assertEquals(entity.getUserId(), 9);
        assertEquals(entity.getId(), 10);
        assertEquals(entity.toString(), "UserInfoEntity [address1=" + "0000 group9 dr" + ", address2=" + "" + ", city=" + "houston" + ", email=" + "group9@gmail.com"
        + ", fullname=" + "fullName" + ", phone=" + "0000000000" + ", state=" + "TX" + ", userinfo_id=" + 10
        + ", zipcode=" + "00000" + "]" + "Bonus Points: " + 10 + "Hold: " + 1 + "$");
    }
}
