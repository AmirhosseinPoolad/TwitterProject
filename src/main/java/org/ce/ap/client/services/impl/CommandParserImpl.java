package main.java.org.ce.ap.client.services.impl;

import main.java.org.ce.ap.client.MenuStatus;
import main.java.org.ce.ap.client.services.CommandParser;
import main.java.org.ce.ap.client.services.ConnectionService;
import main.java.org.ce.ap.client.services.ConsoleViewService;
import main.java.org.ce.ap.server.jsonHandling.Request;
import main.java.org.ce.ap.server.jsonHandling.Response;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.RegisterParameter;
import main.java.org.ce.ap.server.jsonHandling.impl.parameter.SignInParameter;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Scanner;

//TODO: Sanitize inputs
public class CommandParserImpl implements CommandParser {
    ConsoleViewService consoleViewService;
    ConnectionService connectionService;

    private MenuStatus menuStatus = MenuStatus.MAIN;
    private Scanner sc;

    public CommandParserImpl(OutputStream out, InputStream in) {
        sc = new Scanner(System.in);
        connectionService = new ConnectionServiceImpl(out, in);
        //TODO: construct consoleviewerservice
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
                sc.nextLine(); //so that we discard the newline character
                if (input != 1 && input != 2) {
                    throw new IllegalArgumentException("Invalid Input");
                }
                if (input == 2)
                    menuStatus = MenuStatus.REGISTER;
                else if (input == 1)
                    menuStatus = MenuStatus.LOGIN;
                break;
            case REGISTER: {
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
                    menuStatus = MenuStatus.MAIN;
                    return 0;
                }
                System.out.println("Succesfully registered.");
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            case LOGIN: {
                System.out.println("Enter your username and password.");
                String username = sc.nextLine();
                String password = sc.nextLine();
                SignInParameter param = new SignInParameter(username, password);
                Request req = new Request("SignIn", "Logins to account", param);
                Response serverResponse = connectionService.sendToServer(req);
                if (serverResponse.getErrorCode() != 0) {
                    System.out.println("Error, please try again");
                    menuStatus = MenuStatus.MAIN;
                    return 0;
                }
                System.out.println("Succesfully logged in.");
                menuStatus = MenuStatus.TIMELINE;
                break;
            }
            default:
                break;
        }
        return 0;
    }

    @Override
    public Request parseString(String input) {
        return null;
    }
}
