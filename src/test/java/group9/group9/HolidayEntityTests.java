package group9.group9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HolidayEntityTests {
    @Test
    void gettersAndSettersWork() {
        HolidayEntity e = new HolidayEntity();

        assertThat(e).isNotNull();

        e.setDate("00/00/0000");
        e.setHoliday(false);
        e.setHoliday_id(2);
        e.setUserId(9);

        assertEquals(e.getDate(), "00/00/0000");
        assertEquals(e.isHoliday(), false);
        assertEquals(e.getHoliday_id(), 2);
        assertEquals(e.getUserId(), 9);
        assertEquals(e.toString(), "HolidayEntity [date=" + "00/00/0000" + ", holiday_id=" + 2 + ", isHoliday=" + false + "]");
    }
}
