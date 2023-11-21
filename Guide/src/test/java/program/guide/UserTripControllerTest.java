package program.guide;

import org.junit.jupiter.api.Test;

class UserTripControllerTest {
    JsonHandler jsonHandler = new JsonHandler();

    @Test
    void deleteUserTrip() {
        jsonHandler.writeUserToJson("1","5");
        jsonHandler.deleteUserFromJson("1");
    }






}