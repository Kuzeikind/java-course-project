package consoleui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Console extends BaseConsole {

    public Console() throws IOException, SQLException {
        super();
        System.out.println("Type help to see the list of available commands.");
    }

    private Map<String, String> COMMANDS = new HashMap<>() {{
        put("help", "Lists available commands.");
        put("login", "Authorizes a ranger.");
        put("info", "Shows current ranger's personal info.");
        put("recent", "Lists ranger's tasks, most recent ones first.");
        put("urgent", "Lists ranger's tasks, ones with the higher priority first.");
        put("unassigned", "Lists all tasks not assigned to any ranger.");
        put("finished", "Lists ranger's finished tasks.");
        put("details", "Shows detailed information about the specified task.");
        put("take", "Assigns the specified task to the current ranger. Can only take unassigned tasks.");
        put("approve", "Marks the specified task as finished. Only rangers of rank LEAD can approve tasks.");
        put("exit", "Exits the application.");
    }};

    /**
     * Event loop that listens for commands.
     */
    public void listen() {
        // Unauthorized loop.
        UNAUTHORIZED:
        while (true) {
            try {
                System.out.println("Enter your command:");
                String cmd = readCmd();
                switch (cmd) {
                    case "help":
                        showHelpMsg();
                        break;
                    case "login": //TODO unsuccessful login.
                        if (logIn()) {
                            break UNAUTHORIZED;
                        } else {
                            break;
                        }
                    case "info":
                    case "recent":
                    case "urgent":
                    case "unassigned":
                    case "finished":
                    case "details":
                    case "take":
                    case "approve":
                        showUnauthorizedMsg();
                        break;
                    case "exit":
                        showGoodByeMsg();
                        return;
                    default:
                        handleIllegalCmd();
                        break;
                }
            } catch(Exception e) {
                System.out.println("Something went wrong. Closing the application.");
                return;
            }
        }

        //Authorized loop.
        while (true) {
            try {
                String cmd = readCmd();
                switch (cmd) {
                    case "login":
                        logIn();
                        break;
                    case "info":
                        seeInfo();
                        break;
                    case "recent":
                        seeMostRecent();
                        break;
                    case "urgent":
                        seeUrgent();
                        break;
                    case "unassigned":
                        seeUnassigned();
                        break;
                    case "finished":
                        seeFinished();
                        break;
                    case "details":
                        seeTaskDetails();
                        break;
                    case "take":
                        takeTask();
                        break;
                    case "approve":
                        approve();
                        break;
                    case "exit":
                        showGoodByeMsg();
                        return;
                    default:
                        handleIllegalCmd();
                        break;
                }
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Something went wrong. Closing the application.");
                return;
            }
        }
    }

    private String readCmd() {
        String cmd = scanner.next();
        return cmd;
    }

    private void showUnauthorizedMsg() {
        System.out.println("This command requires authorisation. Use `login` command to log in.");
    }

    private void handleIllegalCmd() {
        System.out.println("Illegal command. Type help to see the list of available commands.");
    }

    private void showGoodByeMsg() {
        System.out.println("Good Bye!");
    }

    private void showHelpMsg() {
        System.out.println("List of available commands:");
        COMMANDS.entrySet().stream()
                .forEach(e -> System.out.printf("%s : %s\n", e.getKey(), e.getValue()));
    }

}
