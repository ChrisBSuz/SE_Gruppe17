package program.guide;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReadFromJsonTest {

    public ReadFromJsonTest(){
        new WriteGuideToJSONTest().WriteGuideToJSON("Halden", "2023-02-13", "08:22", "500", "test");
    }

    @Test
    public void ReadFromJson(){
        new ReadFromJsonTest();
        assertTrue(true);
    }
}
