package edu.ufp.inf.sd.rmi.project.server.lobby;
import edu.ufp.inf.sd.rmi.project.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;
import java.util.UUID;

public class Lobby extends UnicastRemoteObject implements LobbyRI {

    private final UUID id;

    private List<ObserverRI> observers = Collections.synchronizedList(new ArrayList<>());
    private LobbyMapEnum map;
    private LobbyStateEnum lobbyState;

    public Lobby(LobbyMapEnum map) throws RemoteException {
        super();
        this.id = UUID.randomUUID();
        this.map = map;
        this.lobbyState=LobbyStateEnum.PAUSED;
    }


    public void setObservers(List<ObserverRI> observers) {
        this.observers = observers;
    }

    public LobbyMapEnum getMap() {
        return map;
    }

    public void setMap(LobbyMapEnum map) {
        this.map = map;
    }

    public LobbyStateEnum getLobbyState() {
        return lobbyState;
    }

    public void setLobbyState(LobbyStateEnum lobbyState) {
        this.lobbyState = lobbyState;
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
    public LobbyMapEnum getMapname() throws RemoteException {
        return map;
    }

    @Override
    public void attach(ObserverRI observer) throws RemoteException {
        this.observers.add(observer);
    }

    @Override
    public void detach(ObserverRI observer) throws RemoteException {
        this.observers.remove(observer);
    }

    public int getCurrentPlayers() {
        return observers.size();
    }

    public int getMaxPlayers(){
        if(map.equals(LobbyMapEnum.FourCorners)){
            return 4;
        }
        return 2;
    }

}
