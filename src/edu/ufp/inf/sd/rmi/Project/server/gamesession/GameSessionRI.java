package edu.ufp.inf.sd.rmi.Project.server.gamesession;

import edu.ufp.inf.sd.rmi.Project.server.Lobby.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface GameSessionRI extends Remote {


    public ArrayList<Lobby> getLobbies() throws RemoteException;
    int joinLobby(int index, GameSessionRI session) throws RemoteException;


    void logout() throws RemoteException;

    String lobbyList() throws RemoteException;

    int createLobby(int numPlayers, String fourCorners, GameSessionRI session) throws RemoteException;
}
