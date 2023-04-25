package edu.ufp.inf.sd.rmi.project.server.lobby;

import edu.ufp.inf.sd.rmi.project.client.ObserverRI;

import java.rmi.Remote;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface LobbyRI extends Remote {

    public List<ObserverRI> players() throws RemoteException;
    public UUID getID() throws RemoteException;
    public LobbyMapEnum getMapName() throws RemoteException;
    int getCurrentPlayers() throws RemoteException;
    int getMaxPlayers() throws RemoteException;
     LobbyStatusEnum getLobbyState() throws RemoteException;
     void attach(ObserverRI obs) throws RemoteException;
     void detach(ObserverRI obs) throws RemoteException;
}

