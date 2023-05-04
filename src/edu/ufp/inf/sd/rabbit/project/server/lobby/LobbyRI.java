package edu.ufp.inf.sd.rabbit.project.server.lobby;

import edu.ufp.inf.sd.rabbit.project.client.ClientRI;
import edu.ufp.inf.sd.rmi.project.server.lobby.State;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface LobbyRI extends Remote {

    List<ClientRI> players() throws RemoteException;

    State getState() throws RemoteException;

    void setState(State state, int id) throws RemoteException;

    UUID getID() throws RemoteException;

    State getGameState() throws RemoteException;

    void setGameState(State state) throws RemoteException;

    String notifyAllObserversGame() throws RemoteException;

    LobbyMapEnum getMapName() throws RemoteException;

    LobbyStatusEnum getLobbyStatus() throws RemoteException;

    void setLobbyStatus(LobbyStatusEnum lobbyStatus) throws RemoteException;

    int getCurrentPlayers() throws RemoteException;

    int getMaxPlayers() throws RemoteException;

    int getIndexObserver(String username) throws RemoteException;

    LobbyStatusEnum getLobbyState() throws RemoteException;

    void attach(ClientRI obs) throws RemoteException;

    void detach(ClientRI obs) throws RemoteException;

}

