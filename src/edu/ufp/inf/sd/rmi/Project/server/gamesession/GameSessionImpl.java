package edu.ufp.inf.sd.rmi.Project.server.gamesession;

import edu.ufp.inf.sd.rmi.Project.database.DB;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.Lobby;
import edu.ufp.inf.sd.rmi.Project.variables.User;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

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
    public ArrayList<Lobby> getLobbies() throws RemoteException{
        return this.db.getGameLobbies();
    }

    public void updateLobby(int index, Lobby lobby) throws RemoteException{
        this.db.updateLobby(index,lobby);
    }
    @Override
    public synchronized void logout() throws RemoteException {
        this.db.removeSession(this.user.getUsername());
    }
}
