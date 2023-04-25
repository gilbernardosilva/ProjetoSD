package edu.ufp.inf.sd.rmi.project.server.gamesession;

import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

public interface GameSessionRI extends Remote {


    List<LobbyRI> getLobbies() throws RemoteException;

    Lobby getLobby(int index) throws RemoteException;

    void logout() throws RemoteException;

    LobbyRI createLobby(LobbyMapEnum map, GameSessionRI session) throws RemoteException;
}
