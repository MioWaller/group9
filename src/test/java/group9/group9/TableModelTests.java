package group9.group9;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;

@SpringBootTest
public class TableModelTests {
    @Test
    void gettersAndSettersWork() {
        TableModel m = new TableModel();


        assertThat(m).isNotNull();

        m.setCapacity(8);
        m.setReserved(false);

        assertEquals(m.getCapacity(), 8);
        assertEquals(m.isReserved(), false);

    }
}
