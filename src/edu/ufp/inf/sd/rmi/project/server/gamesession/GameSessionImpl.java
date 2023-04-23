package edu.ufp.inf.sd.rmi.project.server.gamesession;

import edu.ufp.inf.sd.rmi.project.client.ObserverImpl;
import edu.ufp.inf.sd.rmi.project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rmi.project.variables.User;
import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyStateEnum;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    private final DB db;
    private final User user;


    public GameSessionImpl(DB db, User user) throws RemoteException {
        super();
        this.db = db;
        this.user = user;
        this.db.addSession(user, this);
    }

    public int createLobby(LobbyMapEnum map, GameSessionRI session) throws RemoteException {
        Lobby lobby = new Lobby(map);
        lobby.setLobbyState(LobbyStateEnum.PAUSED);
        this.db.addLobby(lobby);
        List<Lobby> lobbies = this.db.getGameLobbies();
        return lobbies.indexOf(lobby);

    }

    public List<Lobby> getLobbies() throws RemoteException {
        return this.db.getGameLobbies();
    }

    public String lobbyList() throws RemoteException {
        List<Lobby> lobbies = this.db.getGameLobbies();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lobbies.size(); i++) {
            Lobby lobbylist = lobbies.get(i);
            sb.append(i).append(". ID: ").append(lobbylist.getID())
                    .append(", Players: ").append(lobbylist.getCurrentPlayers())
                    .append("/").append(lobbylist.getMaxPlayers())
                    .append(", Map: ").append(lobbylist.getMap())
                    .append(", State: ").append(lobbylist.getLobbyState())
                    .append("\n");
        }
        return sb.toString();
    }

    public LobbyRI getLobby(int index) throws RemoteException{
        List<Lobby> lobbies = this.db.getGameLobbies();
        return lobbies.get(index);
    }
    public int joinLobby(int index, GameSessionRI session) throws RemoteException {
        List<Lobby> lobbies = this.db.getGameLobbies();
        // Find the lobby with the chosen ID
        UUID id = lobbies.get(index).getID();
        Lobby chosenLobby = null;
        for (Lobby lobby : lobbies) {
            if (lobby.getID() == id) {
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
            System.out.println("Players: " + chosenLobby.getCurrentPlayers() + "/" + chosenLobby.getMaxPlayers());
            this.db.updateLobby(index, chosenLobby);
            if (chosenLobby.getCurrentPlayers() == chosenLobby.getMaxPlayers()) {
                chosenLobby.setLobbyState(LobbyStateEnum.ONGOING);
                this.db.updateLobby(index, chosenLobby);
                return 0;
            }
            return 0;
        }
    }

    @Override
    public synchronized void logout() throws RemoteException {
        this.db.removeSession(user);
    }
}
