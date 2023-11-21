package program.guide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class guideControllerTest {
    JsonHandler jsonHandler = new JsonHandler();


    @Test
    void publishGuide() {
        jsonHandler.writeGuideToJson("Halden", "2023-02-13", "07:00", "500", "dette er en test!!!!!");
        assertTrue(true);
    }
}