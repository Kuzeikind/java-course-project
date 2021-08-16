package consoleui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Console extends BaseConsole {

    public Console() throws IOException, SQLException {
        super();
    }

    /**
     * Event loop that listens for commands.
     */
    public void listen() {
        // Unauthorized loop.
        UNAUTHORIZED:
        while (true) {
            try {
                String cmd = readCmd();
                switch (cmd) {
                    case "login": //TODO unsuccessful login.
                        logIn();
                        break UNAUTHORIZED;
                    case "info":
                    case "recent":
                    case "urgent":
                    case "unassigned":
                    case "finished":
                    case "take":
                    case "mark":
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
                    case "take":
                        takeTask();
                        break;
                    case "mark":
                        markAsFinished();
                        break;
                    case "exit":
                        showGoodByeMsg();
                        return;
                    default:
                        handleIllegalCmd();
                        break;
                }
            } catch(Exception e) {

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
        System.out.println("Illegal cmd");
        showHelpMsg();
    }

    private void showGoodByeMsg() {
        System.out.println("Good Bye!");
    }

    private void showHelpMsg() {

    }

}
