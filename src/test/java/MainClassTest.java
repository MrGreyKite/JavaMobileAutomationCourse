import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MainClassTest {

    @DisplayName("Тест на одно число - равенство (позитивный тест)")
    @Test
    void testGetLocalNumber(){
        Assertions.assertEquals(14, MainClass.getLocalNumber());
    }

    @DisplayName("Тест на одно число - равенство (негативный тест)")
    @Test
    void testGetLocalNumberWrong() {
        Assertions.assertEquals(15, MainClass.getLocalNumber());
    }
}
