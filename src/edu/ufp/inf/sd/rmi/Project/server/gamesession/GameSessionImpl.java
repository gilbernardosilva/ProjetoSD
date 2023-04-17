package edu.ufp.inf.sd.rmi.project.server.gamesession;

import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.variables.User;
import edu.ufp.inf.sd.rmi.Project.database.DB;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.Lobby;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.LobbyStateEnum;
import edu.ufp.inf.sd.rmi.Project.variables.User;

import java.util.Scanner;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    private final DB db;
    private final User user;


    public GameSessionImpl(DB db, User user) throws RemoteException {
        super();
        this.db = db;
        this.user = user;
        this.db.addSession(user.getUsername(), this);
    }


    public int createLobby(int numPlayers, String map, GameSessionRI session) throws RemoteException {
        ArrayList<GameSessionRI> listSession = new ArrayList<>();
        listSession.add(session);
        Lobby lobby = new Lobby(UUID.randomUUID(), listSession, numPlayers, map);
        try {
            lobby.setLobbyState(LobbyStateEnum.PAUSED);
            session.createLobby(lobby);
            this.db.addLobby(lobby);
            return 0;
            this.startGame();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

        public ArrayList<Lobby> getLobbies() throws RemoteException {
        return this.db.getGameLobbies();
    }


    public String lobbyList() throws RemoteException {
        ArrayList<Lobby> lobbies = this.db.getGameLobbies();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lobbies.size(); i++) {
            Lobby lobbylist = lobbies.get(i);
            sb.append(i).append(". ID: ").append(lobbylist.getId())
                    .append(", Players: ").append(lobbylist.getCurrentPlayers())
                    .append("/").append(lobbylist.getPlayers())
                    .append(", Map: ").append(lobbylist.getMap())
                    .append(", State: ").append(lobbylist.getLobbyState())
                    .append("\n");
        }
        return sb.toString();
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

    @Override
    public synchronized void logout() throws RemoteException {
        this.db.removeSession(this.user.getUsername());
    }
}
