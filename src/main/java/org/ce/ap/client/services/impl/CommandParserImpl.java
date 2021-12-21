package main.java.org.ce.ap.client.services.impl;

import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.client.services.CommandParser;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.client.services.ConsoleViewService;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.RegisterParameter;

import java.time.LocalDate;
import java.util.Scanner;

//TODO: Sanitize inputs
public class CommandParserImpl implements CommandParser {
    ConsoleViewService consoleViewService;
    ConnectionService connectionService;

    private MenuStatus menuStatus = MenuStatus.MAIN;
    private Scanner sc;

    public CommandParserImpl() {
        sc = new Scanner(System.in);
        //TODO: construct consoleviewerservice and connectionservice
    }

    public MenuStatus getMenuStatus() {
        return menuStatus;
    }

    @Override
    public int handleInput() throws IllegalArgumentException {
        switch (menuStatus) {
            case MAIN:
                //TODO: Use console view
                System.out.println("Enter 1 to Login, 2 to Register");
                int input = sc.nextInt();
                if (input != 1 && input != 2) {
                    throw new IllegalArgumentException("Invalid Input");
                }
                if (input == 1)
                    menuStatus = MenuStatus.REGISTER;
                else if (input == 2)
                    menuStatus = MenuStatus.LOGIN;
                break;
            case REGISTER:
                //TODO: Use console view
                System.out.println("Enter your username, password, firstname, lastname, biography and birthday date in that order.");
                String username = sc.nextLine();
                String password = sc.nextLine();
                String firstName = sc.nextLine();
                String lastName = sc.nextLine();
                String biography = sc.nextLine();
                LocalDate birthdayDate = LocalDate.parse(sc.nextLine());
                RegisterParameter param = new RegisterParameter(username, password, firstName, lastName, biography, birthdayDate);
                Request req = new Request("Register", "Registers a new account and logs in", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.out.println("Error, please try again");
                    return 0;
                }
                System.out.println("Succesfully registered.");
                menuStatus = MenuStatus.TIMELINE;
                break;
        }
        return 0;
    }

    @Override
    public Request parseString(String input) {
        return null;
    }
}
