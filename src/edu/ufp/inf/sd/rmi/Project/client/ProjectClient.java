package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.Lobby.Lobby;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.LobbyStateEnum;
import edu.ufp.inf.sd.rmi.Project.server.gamefactory.GameFactoryRI;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;
import engine.Game;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;
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
                    ArrayList<Lobby> lobbies = session.getLobbies();
                    for (int i = 0; i < lobbies.size(); i++) {
                        Lobby lobby = lobbies.get(i);
                        System.out.println((i) + ". ID: " + lobby.getId() + ", Players: " + lobby.getCurrentPlayers() + "/" + lobby.getPlayers() + ", Map: " + lobby.getMap() + ", State: " + lobby.getLobbyState());
                    }
                    int chosenLobbyId = input.nextInt();
                    // Find the lobby with the chosen ID
                    UUID id = lobbies.get(chosenLobbyId).getId();
                    Lobby chosenLobby = null;
                    for (Lobby lobby : lobbies) {
                        if (lobby.getId() == id) {
                            chosenLobby = lobby;
                            break;
                        }
                    }
                    // Check if a lobby was found
                    if (chosenLobby == null) {
                        System.out.println("Invalid lobby ID. Please try again.");
                        break;
                    }

                    // Join the chosen lobby
                    System.out.println("Joining lobby " + chosenLobbyId + " with UUID " + chosenLobby.getId() + "...");


                    // the condition is true, so start the game
                    if (LobbyStateEnum.ONGOING == chosenLobby.getLobbyState()) {
                        System.out.println("Match ongoing, you can't join.");
                    } else {

                        // Update lobby
                        chosenLobby.setCurrentPlayers(chosenLobby.getCurrentPlayers() + 1);
                        ArrayList<GameSessionRI> lobbysessions = chosenLobby.getPlayerlist();
                        lobbysessions.add(session);
                        chosenLobby.setPlayerlist(lobbysessions);

                        ArrayList<GameSessionRI> lobbysessions2 = chosenLobby.getPlayerlist();
                        System.out.println(lobbysessions2.listIterator());
                        System.out.println("Players: " + chosenLobby.getCurrentPlayers() + "/" + chosenLobby.getPlayers());
                        session.updateLobby(chosenLobbyId, chosenLobby);
                        if (chosenLobby.getCurrentPlayers() == chosenLobby.getPlayers()) {
                            chosenLobby.setLobbyState(LobbyStateEnum.ONGOING);
                            session.updateLobby(chosenLobbyId, chosenLobby);
                            this.startGame();
                        }
                        this.startGame();
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
                            // Code to handle FourCorners map
                            ArrayList<GameSessionRI> listSession = new ArrayList<>();
                            listSession.add(session);
                            Lobby lobby = new Lobby(UUID.randomUUID(), listSession, numPlayers, "FourCorners");
                            try {
                                lobby.setLobbyState(LobbyStateEnum.PAUSED);
                                session.createLobby(lobby);
                                this.startGame();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            break;
                        case 2:
                            System.out.println("You have chosen SmallVs map.");
                            // Code to handle SmallVs map
                            ArrayList<GameSessionRI> listSessionSmallVs = new ArrayList<>();
                            listSessionSmallVs.add(session);
                            Lobby lobbySmallVs = new Lobby(UUID.randomUUID(), listSessionSmallVs, numPlayers, "SmallVs");
                            try {
                                lobbySmallVs.setLobbyState(LobbyStateEnum.PAUSED);
                                session.createLobby(lobbySmallVs);
                                this.startGame();
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            break;
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
