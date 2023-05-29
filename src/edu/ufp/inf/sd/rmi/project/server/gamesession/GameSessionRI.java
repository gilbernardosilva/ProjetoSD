package edu.ufp.inf.sd.rmi.project.server.gamesession;

import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rmi.project.variables.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface GameSessionRI extends Remote {


    List<LobbyRI> getLobbies() throws RemoteException;

    void updateToken() throws RemoteException;

    User getUser() throws RemoteException;

    LobbyRI getLobby(int index) throws RemoteException;

    void logout() throws RemoteException;

    void deleteLobby(UUID id) throws RemoteException;
    LobbyRI createLobby(LobbyMapEnum map, GameSessionRI session) throws RemoteException;
}
