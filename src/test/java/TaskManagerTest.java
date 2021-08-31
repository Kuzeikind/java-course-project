import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dao.domain.AbstractEntity;
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
import util.DateTimeDeserializer;
import util.JsonMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TaskManagerTest {

    private static Controller controller;
    private static TaskManager manager;
    private static Map<Long, Task> tasks;
    private static Map<Long, Ranger> rangers;

    private static ObjectMapper mapper = new ObjectMapper();
    private static String rangersPath = "src/test/resources/rangers/";
    private static String tasksPath = "src/test/resources/tasks/";

    @BeforeAll
    static void setUp() throws SQLException, IOException {
        DatabaseSetup.setUp();
        JsonMapper.writeAll();

        controller = new Controller();
        manager = controller.getTaskManager();

        tasks = readValues(tasksPath, Task.class);
        rangers = readValues(rangersPath, Ranger.class);

        // Configure the mapper to parse LocalDateTime
        SimpleModule mod = new SimpleModule();
        mod.addDeserializer(LocalDateTime.class, new DateTimeDeserializer());
        mapper.registerModule(mod);

    }

    private static <T extends AbstractEntity> Map<Long, T> readValues(String pathStr, Class<T> type)
            throws IOException {
        return Files.walk(Path.of(pathStr))
                .filter(path -> !Files.isDirectory(path))
                .filter(path -> path.toString().endsWith(".json"))
                .map(TaskManagerTest::fileToString)
                .map(json -> mapJsonToObj(json, type))
                .collect(Collectors.toMap(T::getId, Function.identity()));
    }

    private static String fileToString(Path path) {
        try {
            return new String(Files.readAllBytes(path));
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    private static <T> T mapJsonToObj(String json, Class<T> type){
        try {
            return mapper.readValue(json, type);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }

    @Test
    public void testSeeRecent() throws SQLException {
        long id = 3;
        Ranger r = rangers.get(Long.valueOf(id));
        List<Task> expected = tasks.entrySet().stream()
                .map(e -> e.getValue())
                .filter(t -> t.getAssignedTo() == id)
                .sorted((t1, t2) -> t2.getCreatedAt().compareTo(t1.getCreatedAt()))
                .collect(Collectors.toList());

        assertEquals(expected, manager.seeMostRecent(r));
    }

    @Test
    public void testSeeUrgent() throws SQLException {
        long id = 3;
        Ranger r = rangers.get(Long.valueOf(id));
        List<Task> expected = tasks.entrySet().stream()
                .map(e -> e.getValue())
                .filter(t -> t.getAssignedTo() == id)
                .sorted((t1, t2) -> t2.getPriority().compareTo(t1.getPriority()))
                .collect(Collectors.toList());

        assertEquals(expected, manager.seeUrgent(r));
    }

    @Test
    public void testSeeFinished() throws SQLException {
        long id = 1;
        Ranger r = rangers.get(Long.valueOf(id));
        List<Task> expected = tasks.entrySet().stream()
                .map(e -> e.getValue())
                .filter(t -> t.getAssignedTo() == id)
                .collect(Collectors.toList());

        assertEquals(expected, manager.seeFinished(r, 10));
    }

    @Test
    public void testSeeUnassigned() throws SQLException {
        List<Task> expected = tasks.entrySet().stream()
                .map(e -> e.getValue())
                .filter(t -> t.getAssignedTo() == Long.valueOf(0))
                .collect(Collectors.toList());

        assertEquals(expected, manager.seeUnassigned(10));
    }

    @Test
    public void testSeeTaskDetailsBadTaskId(){
        assertThrows(SQLException.class,
                () -> manager.seeTaskDetails(100));
    }

    @Test
    public void testSeeTaskDetailsCorrectId() throws SQLException {
        assertEquals(tasks.get(Long.valueOf(1)), manager.seeTaskDetails(1));
    }

    @Test
    public void testTakeAssignedTask() {
        Ranger r = rangers.get(Long.valueOf(1));
        assertThrows(TaskAlreadyTakenException.class,
                () -> manager.takeTask(r, 1));
    }

    @Test
    public void testTakeTooManyTasks() {
        Ranger r = rangers.get(Long.valueOf(3));
        assertThrows(TooManyTasksException.class,
                () -> manager.takeTask(r, 10));
    }

    @Test
    public void testTakeTaskSuccess() throws SQLException {
        Ranger r = rangers.get(Long.valueOf(4));
        manager.takeTask(r, 10);

        Task t = manager.seeTaskDetails(10);
        assertEquals(r.getId(), t.getAssignedTo());
    }

    @Test
    public void testApproveWithLowRank() {
        Ranger r = rangers.get(Long.valueOf(1));
        assertThrows(LowRankException.class,
                () -> manager.approveTask(r, 1));
    }

    @Test
    public void testApproveUnassignedTask() {
        Ranger r = rangers.get(Long.valueOf(4));
        assertThrows(TaskNotAssignedException.class,
                () -> manager.approveTask(r, 11));
    }

    @Test
    public void testApproveTaskSuccess() throws SQLException {
        Ranger r = rangers.get(Long.valueOf(4));
        manager.approveTask(r, 6);

        List<Task> expected = List.of(tasks.get(Long.valueOf(6)));
        assertEquals(expected, manager.seeFinished(r, 10));
    }

}
