import com.fasterxml.jackson.databind.ObjectMapper;
import dao.domain.Ranger;
import exceptions.PasswordMismatchException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.Controller;
import service.RangerManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RangerManagerTest {

    private static Controller controller;
    private static RangerManager manager;
    private static ObjectMapper mapper = new ObjectMapper();
    private static String rangersPath = "src/test/resources/rangers/";

    private static String wrongEmail = "wrong user";
    private static String wrongPassword = "bad password";

    private static String correctEmail = "janedoe@gmail.com";
    private static String correctPassword = "password3";

    @BeforeAll
    static void setUp() throws SQLException, IOException {
        DatabaseSetup.setUp();
        controller = new Controller();
        manager = controller.getRangerManager();
    }

    @Test
    public void testLogInWrongUsername() throws SQLException {
        Exception expected = assertThrows(SQLException.class,
                () -> manager.logIn(wrongEmail, wrongPassword));
        assertEquals("User does not exit", expected.getMessage());
    }

    @Test
    public void testLogIngWrongPassword() throws SQLException {
        Exception expected = assertThrows(PasswordMismatchException.class,
                () -> manager.logIn(correctEmail, wrongPassword));
    }

    @Test
    public void testLogInCorrectParams() throws IOException, SQLException {
        Ranger expected = mapper.readValue(
                new String(Files.readAllBytes(Path.of(rangersPath + "ranger3.json"))),
                Ranger.class);
        assertEquals(expected, manager.logIn(correctEmail, correctPassword));
    }

}
