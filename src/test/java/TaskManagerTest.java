import com.fasterxml.jackson.databind.ObjectMapper;
import dao.domain.Ranger;
import dao.domain.Task;
import exceptions.LowRankException;
import exceptions.TaskAlreadyTakenException;
import exceptions.TaskNotAssignedException;
import exceptions.TooManyTasksException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.Controller;
import service.TaskManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    private static Controller controller;
    private static TaskManager manager;
    private static List<Task> tasks;
    private static List<Ranger> rangers;

    private static ObjectMapper mapper = new ObjectMapper();
    private static String rangersPath = "src/test/resources/rangers/";
    private static String tasksPath = "src/test/resources/tasks/";

    @BeforeAll
    static void setUp() throws SQLException, IOException {
        DatabaseSetup.setUp();
        controller = new Controller();
        manager = controller.getTaskManager();
        tasks = readTasks();
        rangers = readRangers();
    }

    private static List<Ranger> readRangers() throws IOException {
        return Files.walk(Path.of(rangersPath))
                .map(path -> new String(Files.readAllBytes(path)))
                .map(str -> mapper.readValue(str, Ranger.class))
                .collect(Collectors.toList());
    }

    private static List<Task> readTasks() throws IOException {
        return Files.walk(Path.of(tasksPath))
                .map(path -> new String(Files.readAllBytes(path)))
                .map(str -> mapper.readValue(str, Task.class))
                .collect(Collectors.toList());
    }

    @Test
    public void testSeeRecent() {

    }

    @Test
    public void testSeeUrgent() {

    }

    @Test
    public void testSeeFinished() {

    }

    @Test
    public void testSeeUnassigned() {

    }

    @Test
    public void testSeeTaskDetailsBadTaskId(){
        assertThrows(SQLException.class,
                () -> manager.seeTaskDetails(100));
    }

    @Test
    public void testSeeTaskDetailsCorrectId() {

    }

    @Test
    public void testTakeAssignedTask() {
        Ranger r = rangers.get(0);
        assertThrows(TaskAlreadyTakenException.class,
                () -> manager.takeTask(r, 1));
    }

    @Test
    public void testTakeTooManyTasks() {
        Ranger r = rangers.get(2);
        assertThrows(TooManyTasksException.class,
                () -> manager.takeTask(r, 10));
    }

    @Test
    public void testTakeTaskSuccess() throws SQLException {
        Ranger r = rangers.get(3);
        manager.takeTask(r, 10);

        Task t = manager.seeTaskDetails(10);
        assertEquals(t.getId(), t.getAssignedTo());
    }

    @Test
    public void testApproveWithLowRank() {
        Ranger r = rangers.get(0);
        assertThrows(LowRankException.class,
                () -> manager.approveTask(r, 1));
    }

    @Test
    public void testApproveUnassignedTask() {
        Ranger r = rangers.get(3);
        assertThrows(TaskNotAssignedException.class,
                () -> manager.approveTask(r, 11));
    }

    @Test
    public void testApproveTaskSuccess() throws SQLException {
        Ranger r = rangers.get(3);
        manager.approveTask(r, 10);

        fail();
    }


}
