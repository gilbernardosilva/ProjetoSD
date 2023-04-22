package edu.ufp.inf.sd.rmi.project.server.gamesession;
import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface GameSessionRI extends Remote {


    public Collection<Lobby> getLobbies() throws RemoteException;

    int joinLobby(int index, GameSessionRI session) throws RemoteException;


    void logout() throws RemoteException;

    String lobbyList() throws RemoteException;

    int createLobby(LobbyMapEnum map, GameSessionRI session) throws RemoteException;
}
