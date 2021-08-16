package consoleui;

import service.Controller;

import java.util.Scanner;

public abstract class BaseConsole {
    private Controller ctrl = new Controller();
    protected Scanner scanner = new Scanner(System.in);

    protected void logIn() {}

    protected void seeInfo() {}

    protected void seeMostRecent() {}

    protected void seeUrgent() {}

    protected void seeFinished() {}

    protected void seeUnassigned() {}

    protected void seeTaskDetails() {}

    protected void takeTask() {}

    protected void markAsFinished() {}


}
