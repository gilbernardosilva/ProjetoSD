package edu.ufp.inf.sd.rmi.project.server.lobby;

import edu.ufp.inf.sd.rmi.project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.project.server.SubjectState;
import edu.ufp.inf.sd.rmi.project.variables.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Lobby extends UnicastRemoteObject implements LobbyRI {

    private final UUID id;
    private ArrayList<User> users;

    public ObserverRI observerRI;

    private List<ObserverRI> observers = Collections.synchronizedList(new ArrayList<>(Arrays.asList(null, null, null, null)));
    private LobbyMapEnum map;
    private LobbyStateEnum lobbyState;
    private SubjectState subjectState;

    public Lobby(LobbyMapEnum map) throws RemoteException {
        super();
        this.id = UUID.randomUUID();
        this.map = map;
        this.lobbyState = LobbyStateEnum.PAUSED;
        this.users = new ArrayList<>();


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
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Observer attached");
        if (getCurrentPlayers() == 0) {
            observer.setCoordinator(true);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Currently 0 players. Observer set as coordinator");
        }
        for (int i = 0; i < 4; i++) {
            if (this.observers.get(i) == null) {
                observer.setId(i);
                this.observers.set(i, observer);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Observer id is " + i);
                break;
            }
        }
        this.observers.add(observer);
    }


    //TODO: TEMOS DE IMPLEMENTAR AS COISAS PARA ATUALIZAR
    public void notifyAllObservers() throws RemoteException, InterruptedException {
        for (ObserverRI obs : observers) {
            if (obs != null)
                obs.update(this.subjectState);
        }
        //sessionRI.synchronizeServers();
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

    /**
     * gets state of the subject
     * @return subjectState
     * @throws RemoteException
     */
    public SubjectState getSubjectState() throws RemoteException {
        return subjectState;
    }


    public void setSubjectState(SubjectState subjectState) throws RemoteException, InterruptedException {
        this.subjectState = subjectState;
        notifyAllObservers();
    }
}
