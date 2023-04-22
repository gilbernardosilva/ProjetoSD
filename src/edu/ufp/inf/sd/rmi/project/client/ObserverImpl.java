package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import engine.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI{

    private LobbyRI lobby;
    private Game game;
    private String username;

    public ObserverImpl(LobbyRI lobby,String username) throws RemoteException {
        super();
        this.username = username;
        this.lobby = lobby;
        this.lobby.attach(this);
    }

    @Override
    public LobbyRI getLobby() {
        return lobby;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void update() throws RemoteException{

    }
}
