package group9.group9;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;

@SpringBootTest
public class ReservationModelTests {
    @Test
    void gettersAndSettersWork() {
        ReservationModel temp = new ReservationModel();

        assertThat(temp).isNotNull();

        temp.setFullName("ekrem");
        temp.setPhoneNumber("0001113334");
        temp.setEmail("ekrem10@gmail.com");
        temp.setDate("2010-11-11");
        temp.setTime("12:12");
        temp.setNumOfGuests("10");
        temp.setHoliday("false");


        assertEquals(temp.getFullName(),"ekrem");
        assertEquals(temp.getPhoneNumber(),"0001113334");
        assertEquals(temp.getEmail(),"ekrem10@gmail.com");
        assertEquals(temp.getDate(),"2010-11-11");
        assertEquals(temp.getTime(),"12:12");
        assertEquals(temp.getNumOfGuests(),"10");
        assertEquals(temp.isHoliday(), "false");


    }
}
