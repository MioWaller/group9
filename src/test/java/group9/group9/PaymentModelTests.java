package group9.group9;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;

@SpringBootTest
public class PaymentModelTests {
    @Test
    void gettersAndSettersWork() {

        PaymentModel temp = new PaymentModel();

        assertThat(temp).isNotNull();

        temp.setCardName("Ekrem Yavas");
        temp.setCardNumber("0123456789123456");
        temp.setExpDate("12-25");
        temp.setSecCode("232");


        assertEquals(temp.getCardName(),"Ekrem Yavas");
        assertEquals(temp.getCardNumber(),"0123456789123456");
        assertEquals(temp.getExpDate(),"12-25");
        assertEquals(temp.getSecCode(),"232");
        assertEquals(temp.toString(), "PaymentModel [SecCode=" + "232" + ", cardName=" + "Ekrem Yavas"
        + ", cardNumber=" + "0123456789123456"
        + ", expDate=" + "12-25" + "]");
        


    }
}
