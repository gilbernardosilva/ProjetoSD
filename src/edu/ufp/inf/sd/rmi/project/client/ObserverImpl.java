package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.SubjectState;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import engine.Game;
import menus.PlayerSelection;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

    private LobbyRI lobby;
    private Game game;
    private Integer id;
    private SubjectState lastObserverState;

    private String character;
    private boolean isCoordinator;


    private String username;

    public ObserverImpl(LobbyRI lobby, String username) throws RemoteException {
        super();
        this.username = username;
        this.lobby = lobby;
        this.lobby.attach(this);

    }

    public Integer getId() throws RemoteException {
        return id;
    }

    public void setId(Integer id) throws RemoteException {
        this.id = id;
    }

    @Override
    public LobbyRI getLobby() throws RemoteException {
        return lobby;
    }

    @Override
    public boolean isCoordinator() throws RemoteException {
        return isCoordinator;
    }

    public void setCoordinator(boolean coordinator) throws RemoteException {
        isCoordinator = coordinator;
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    @Override
    public void update(SubjectState state) throws RemoteException, InterruptedException {
        this.lastObserverState = state;


    }

    /**
     * set a new observerState
     *
     * @param lastObserverState most current observer state
     * @throws RemoteException
     */
    @Override
    public void setLastObserverState(SubjectState lastObserverState) throws RemoteException {
        this.lastObserverState = lastObserverState;

    }

    /**
     * get most current observer state
     *
     * @return lastObserverState
     * @throws RemoteException
     */
    @Override
    public SubjectState getLastObserverState() throws RemoteException {
        return lastObserverState;
    }
}
