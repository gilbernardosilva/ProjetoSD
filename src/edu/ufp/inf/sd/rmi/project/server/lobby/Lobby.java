package edu.ufp.inf.sd.rmi.project.server.lobby;

import edu.ufp.inf.sd.rmi.project.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;


public class Lobby extends UnicastRemoteObject implements LobbyRI {

    private final UUID id;
    private List<ObserverRI> observers = Collections.synchronizedList(new ArrayList<>());
    private final LobbyMapEnum map;
    private LobbyStatusEnum lobbyStatus;
    private String state;
    private String owner;

    public Lobby(LobbyMapEnum map, String owner) throws RemoteException {
        super();
        this.id = UUID.randomUUID();
        this.map = map;
        this.owner = owner;
    }


    public UUID getId() {
        return id;
    }

    public List<ObserverRI> getObservers() {
        return observers;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        owner = owner;
    }

    public void setObservers(List<ObserverRI> observers) {
        this.observers = observers;
    }

    public LobbyStatusEnum getLobbyState() throws RemoteException {
        return lobbyStatus;
    }

    public void setLobbyState(LobbyStatusEnum lobbyState) {
        this.lobbyStatus = lobbyState;
    }

    @Override
    public List<ObserverRI> players() throws RemoteException {
        return observers;
    }

    @Override
    public UUID getID() throws RemoteException {
        return id;
    }

    @Override
    public LobbyMapEnum getMapName() throws RemoteException {
        return map;
    }

    @Override
    public void attach(ObserverRI observer) throws RemoteException {
    }


    @Override
    public void detach(ObserverRI observer) throws RemoteException {
        this.observers.remove(observer);
    }

    public int getCurrentPlayers() {
        return observers.size();
    }

    public int getMaxPlayers() {
        if (map.equals(LobbyMapEnum.FourCorners)) {
            return 4;
        }
        return 2;
    }


}
