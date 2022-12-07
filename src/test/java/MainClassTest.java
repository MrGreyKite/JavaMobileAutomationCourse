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

    @DisplayName("Тест на приватное поле класса (падающий)")
    @Test
    void testGetClassNumber(){
        Assertions.assertTrue(MainClass.getClassNumber() > 45, "Число в приватном поле класса должно быть больше 45");
    }

    @DisplayName("Тест на строку с двойным условием")
    @Test
    void testGetClassString(){
        Assertions.assertTrue((MainClass.getClassString().contains("Hello") | MainClass.getClassString().contains("world")), "Строка должна содержать Hello либо world либо оба");
    }
}
