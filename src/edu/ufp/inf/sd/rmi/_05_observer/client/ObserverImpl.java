package edu.ufp.inf.sd.rmi._05_observer.client;

import edu.ufp.inf.sd.rmi._05_observer.server.State;
import edu.ufp.inf.sd.rmi._05_observer.server.SubjectRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;



public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {


    private final String username;
    public SubjectRI subjectRI;
    private final ObserverGuiClient observerGuiClient;
    private String[] arg;
    private State lastObserverState;

    public ObserverImpl(String username, ObserverGuiClient observerGuiClient, SubjectRI subjectRI) throws RemoteException {
        this.username = username;
        this.observerGuiClient = observerGuiClient;
        this.subjectRI = subjectRI;
        this.subjectRI.attach(this);
    }

    public ObserverImpl(String username, ObserverGuiClient observerGuiClient, String[] arg) throws RemoteException {
        this.username = username;
        this.observerGuiClient = observerGuiClient;
        this.arg = arg;
    }


    @Override
    public void update() throws RemoteException {
        this.lastObserverState = subjectRI.getState();
        observerGuiClient.updateTextArea();
    }

    public State getLastObserverState() {
        return lastObserverState;
    }

    public void setLastObserverState(State lastObserverState) {
        this.lastObserverState = lastObserverState;
    }
}
