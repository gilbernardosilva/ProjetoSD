package edu.ufp.inf.sd.rmi.Project.server.gamesession;

import edu.ufp.inf.sd.rmi.Project.server.Lobby.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {

    void createLobby(Lobby lobby) throws RemoteException;

    public ArrayList<Lobby> getLobbies() throws RemoteException;


    void logout() throws RemoteException;

    void lobbyList() throws RemoteException;
}
