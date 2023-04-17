package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.gamefactory.GameFactoryRI;
import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;
import engine.Game;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClient {

    private SetupContextRMI contextRMI;
    private GameFactoryRI stub;
    private GameSessionRI session;

    public static void main(String args[]) {
        ProjectClient client = new ProjectClient(args);
    }

    public ProjectClient(String args[]) {
        this.initContext(args);
        this.lookupService();
        this.login();
        //this.startGame();
    }

    private void startGame() {
        new Game(this.stub);
    }

    private void lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);
                this.stub = (GameFactoryRI) registry.lookup(serviceUrl);
            }
        } catch (RemoteException | NotBoundException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void login() {


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter 'login' or 'register': ");
        String action = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        switch (action) {
            case "login":
                try {
                    this.session = this.stub.login(username, password);
                    System.out.println("Login feito com sucexo!");
                    this.lobbyMenu(this.session);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "register":
                try {
                    this.stub.register(username, password);
                    this.session = this.stub.login(username, password);
                    System.out.println(this.session.toString());
                    this.lobbyMenu(this.session);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            default:
                System.out.println("Invalid action entered.");
                break;
        }
    }

    private void initContext(String args[]) {
        try {
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            this.contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void lobbyMenu(GameSessionRI session) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to the Lobby Menu!");
        System.out.println("Please choose an option:");
        System.out.println("1. Created Lobbies");
        System.out.println("2. Create New Lobby");

        int choice = input.nextInt();

        switch (choice) {
            case 1:
                System.out.println("You have chosen Created Lobbies.");
                System.out.println("Please choose a lobby to join by ID:");
                try {
                    System.out.print(this.session.lobbyList());
                    int chosenLobbyId = input.nextInt();
                    System.out.println("Joining lobby " + chosenLobbyId);
                    int joinResult = this.session.joinLobby(chosenLobbyId, session);
                    switch (joinResult) {
                        case 1:
                            System.out.println("Invalid lobby ID. Please try again.");
                            break;
                        case 2:
                            System.out.println("Match ongoing, you can't join.");
                            break;
                        case 0:
                            this.startGame();
                            break;
                    }
                    break;
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            case 2:
                System.out.println("You have chosen Create New Lobby.");
                System.out.println("How many players? (Enter 2 or 4)");
                int numPlayers = input.nextInt();
                if (numPlayers == 2 || numPlayers == 4) {
                    switch (numPlayers) {
                        case 4:
                            System.out.println("You have chosen FourCorners map.");
                            try {
                                if (this.session.createLobby(numPlayers, "FourCorners", this.session) == 0) {
                                    this.startGame();
                                }
                                break;
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        case 2:
                            System.out.println("You have chosen SmallVs map.");
                            try {
                                if (this.session.createLobby(numPlayers, "SmallVs", this.session) == 0) {
                                    this.startGame();
                                }
                                break;
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                            break;
                    }
                }
                System.out.println("Invalid choice. Please choose again.");
                break;
            default:
                System.out.println("Invalid choice. Please choose again.");
                break;
        }
    }
}
