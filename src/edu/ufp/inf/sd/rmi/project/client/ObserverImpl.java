package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import engine.Game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

    private LobbyRI lobby;
    private Game game;
    private Integer id;
    private String character;


    private String username;

    public ObserverImpl(LobbyRI lobby, String username, Game game) throws RemoteException {
        super();
        this.username = username;
        this.lobby = lobby;
        this.game = game;

    }

    public Integer getId() throws RemoteException {
        return id;
    }

    public void setId(Integer id) throws RemoteException {
        this.id = id;
    }

    @Override
    public LobbyRI getLobby() throws RemoteException {
        return lobby;
    }


    @Override
    public String getUsername() throws RemoteException {
        return username;
    }


}
