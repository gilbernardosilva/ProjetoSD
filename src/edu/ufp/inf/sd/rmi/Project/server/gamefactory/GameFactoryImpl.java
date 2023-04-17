package edu.ufp.inf.sd.rmi.Project.server.gamefactory;

import edu.ufp.inf.sd.rmi.Project.database.DB;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.Lobby;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.LobbyStateEnum;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionImpl;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.variables.User;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    private final DB db;

    public GameFactoryImpl(DB db) throws RemoteException {
        super();
        this.db = db;
    }

    @Override
    public GameSessionRI login(String username, String password) throws RemoteException {
        User user = this.db.getUser(username,password);
        GameSessionImpl gameSession = new GameSessionImpl(this.db, user);
        return gameSession;
    }

    @Override
    public GameSessionRI register(String username, String password) throws RemoteException {
        this.db.register(username, password);
        return this.login(username,password);
    }

    public int joinLobby(int index, GameSessionRI session) throws RemoteException {
        ArrayList<Lobby> lobbies = this.db.getGameLobbies();
        // Find the lobby with the chosen ID
        UUID id = lobbies.get(index).getId();
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
            return 1;
        }
        // the condition is true, so start the game
        if (LobbyStateEnum.ONGOING == chosenLobby.getLobbyState()) {
            System.out.println("Match ongoing, you can't join.");
            return 2;
        } else {
            chosenLobby.setCurrentPlayers(chosenLobby.getCurrentPlayers() + 1);
            ArrayList<GameSessionRI> lobbysessions = chosenLobby.getPlayerlist();
            lobbysessions.add(session);
            chosenLobby.setPlayerlist(lobbysessions);

            System.out.println("Players: " + chosenLobby.getCurrentPlayers() + "/" + chosenLobby.getPlayers());
            this.db.updateLobby(index, chosenLobby);
            if (chosenLobby.getCurrentPlayers() == chosenLobby.getPlayers()) {
                chosenLobby.setLobbyState(LobbyStateEnum.ONGOING);
                this.db.updateLobby(index, chosenLobby);
                return 0;
            }
            return 0;
        }
    }

    private GameSessionRI getSession(User user) throws RemoteException {

        return this.db.getSession(user.getUsername());
    }


}

