import consoleui.Console;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        Console console = null;
        try {
            console = new Console();
            console.listen();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
