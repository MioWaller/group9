package group9.group9;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TableEntityTests {
    @Test
    void gettersAndSettersWork() {
        TableEntity e = new TableEntity();

        assertThat(e).isNotNull();

        e.setTable_id(4);
        e.setCapacity(3);
        e.setReserved(false);

        assertEquals(e.getTable_id(), 4);
        assertEquals(e.getCapacity(), 3);
        assertEquals(e.isReserved(), false);
        assertEquals(e.toString(), "TableEntity [capacity=" + 3 + ", isReserved=" + false + ", table_id=" + 4 + "]");
    }
}
