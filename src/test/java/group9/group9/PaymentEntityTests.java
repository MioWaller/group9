package group9.group9;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class PaymentEntityTests {
    @Test
    void gettersAndSettersWork() {
        PaymentEntity entity = new PaymentEntity();

        assertThat(entity).isNotNull();

        entity.setCardName("group9");
        entity.setCardNumber("0000 0000 0000 0000");
        entity.setExpDate("00/00");
        entity.setSecCode("000");
        entity.setPaymentID(10);
        entity.setUserId(9);
        
        assertEquals(entity.getCardName(), "group9");
        assertEquals(entity.getCardNumber(), "0000 0000 0000 0000");
        assertEquals(entity.getExpDate(), "00/00");
        assertEquals(entity.getSecCode(), "000");
        assertEquals(entity.getPaymentID(), 10);
        assertEquals(entity.getUserId(), 9);
        assertEquals(entity.toString(), "PaymentEntity [SecCode=" + "000" + ", cardName=" + "group9" + ", cardNumber=" + "0000 0000 0000 0000" + ", expDate="
        + "00/00" + ", paymentID=" + 10 + ", userid=" + 9 + "]");
    }
}
