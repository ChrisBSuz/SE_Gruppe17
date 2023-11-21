package program.guide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest {
    JsonHandler jsonHandler = new JsonHandler();
    UserController userController = new UserController();


    @Test
    void searchForGuide() {
        String dateTo = "2023-02-13",
                dateFrom = "2023-02-13",
                dateIs = "2023-02-13";

        assertTrue(userController.fromDateToDate(dateFrom,dateTo,dateIs));
    }

    @Test
    void assignUserToGuide() {
        jsonHandler.writeUserToJson("1","100");
        assertTrue(true);
    }

    @Test
    void searchResult() {
        jsonHandler.writeGuideToJson("Halden", "2023-02-13", "07:00", "500", "dette er en test!!!!!");
        assertFalse(userController.searchResult("Halden", 0, "0000-00-00", "9999-99-99").isBlank());
    }
}