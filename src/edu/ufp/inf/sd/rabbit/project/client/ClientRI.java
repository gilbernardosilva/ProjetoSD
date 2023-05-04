package edu.ufp.inf.sd.rabbit.project.client;

import edu.ufp.inf.sd.rabbit.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rmi.project.server.lobby.State;
import menus.PlayerSelectionMP;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientRI extends Remote {
    String getUsername() throws RemoteException;

    LobbyRI getLobby() throws RemoteException;

    Integer getId() throws RemoteException;

    void setId(Integer id) throws RemoteException;

    void update() throws RemoteException;

    void updateGame(String message) throws RemoteException;

    void sendToServer(String message) throws IOException;

    Observer getObserver();

    void setObserver(Observer observer);

    void sendGameState(String line, String entity) throws IOException;

    void setLastObserverGameState(State lastObserverGameState) throws RemoteException;

    State getLastObserverGameState() throws RemoteException;

    void setPlayerSelectionMP(PlayerSelectionMP playerSelectionMP) throws RemoteException;

    PlayerSelectionMP getPlayerSelectionMP() throws RemoteException;

    void setLastObserverState(State lastObserverState) throws RemoteException;

    State getLastObserverState() throws RemoteException;

    void close() throws Exception;
}


