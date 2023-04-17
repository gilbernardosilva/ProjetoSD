package edu.ufp.inf.sd.rmi.Project.server.gamesession;

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

    private DB db;
    private User user;


    public GameSessionImpl(DB db, User user) throws RemoteException {
        super();
        this.db = db;
        this.user = user;
        this.db.addSession(user.getUsername(), this);
    }

    @Override
    public void createLobby(Lobby lobby) throws RemoteException {
        this.db.addLobby(lobby);
    }

    public ArrayList<Lobby> getLobbies() throws RemoteException {
        return this.db.getGameLobbies();
    }


    public void lobbyList() throws RemoteException {
        ArrayList<Lobby> lobbies = this.db.getGameLobbies();
        for (int i = 0; i < lobbies.size(); i++) {
            Lobby lobbylist = lobbies.get(i);
            System.out.println((i) + ". ID: " + lobbylist.getId() + ", Players: " + lobbylist.getCurrentPlayers() + "/" + lobbylist.getPlayers() + ", Map: " + lobbylist.getMap() + ", State: " + lobbylist.getLobbyState());
        }
    }



    @Override
    public synchronized void logout() throws RemoteException {
        this.db.removeSession(this.user.getUsername());
    }
}
