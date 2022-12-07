import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MainClassTest {

    @DisplayName("Тест на одно число")
    @Test
    void testGetLocalNumber(){
        Assertions.assertEquals(14, MainClass.getLocalNumber());
    }
}
