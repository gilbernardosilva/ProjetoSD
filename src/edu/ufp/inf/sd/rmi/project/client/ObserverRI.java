package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.SubjectState;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ObserverRI extends Remote {
    String getUsername() throws RemoteException;
    LobbyRI getLobby() throws RemoteException;
    boolean isCoordinator() throws RemoteException;
    void setCoordinator(boolean coordinator) throws RemoteException;
    Integer getId() throws RemoteException;
    void setId(Integer id) throws RemoteException;

    void update(SubjectState state) throws RemoteException, InterruptedException;

    void setLastObserverState(SubjectState lastObserverState) throws RemoteException;

    SubjectState getLastObserverState() throws RemoteException;
}

