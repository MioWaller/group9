package group9.group9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestTemplate;

public class ReservationEntityTests {
    @Test
    void gettersAndSettersWork() {
        ReservationEntity entity = new ReservationEntity();

        assertThat(entity).isNotNull();

        entity.setTableId(2);
        entity.setFullName("group9");
        entity.setPhoneNumber("0000000000");
        entity.setEmail("group9@gmail.com");
        entity.setDate("00/00/0000");
        entity.setTime("00:00 PM");
        entity.setNumOfGuests(3);
        entity.setHoliday(false);
        entity.setId(10);
        entity.setUserId(9);

        assertEquals(entity.getTableId(), 2);
        assertEquals(entity.getFullName(), "group9");
        assertEquals(entity.getPhoneNumber(), "0000000000");
        assertEquals(entity.getEmail(), "group9@gmail.com");
        assertEquals(entity.getDate(), "00/00/0000");
        assertEquals(entity.getTime(), "00:00 PM");
        assertEquals(entity.getNumOfGuests(), 3);
        assertEquals(entity.isHoliday(), false);
        assertEquals(entity.getId(), 10);
        assertEquals(entity.getUserId(), 9);
    }
    
}
