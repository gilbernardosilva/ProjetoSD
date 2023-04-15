package edu.ufp.inf.sd.rmi._05_observer.server;

import edu.ufp.inf.sd.rmi._05_observer.client.ObserverGuiClient;
import edu.ufp.inf.sd.rmi._05_observer.client.ObserverRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Observer;


public class SubjectImpl extends UnicastRemoteObject implements SubjectRI {


    private State subjectState;
    private final ArrayList<ObserverRI> observers = new ArrayList<>();
    protected SubjectRI subjectRI;
    protected ObserverGuiClient chatframe;

    public SubjectImpl(State subjectState) throws RemoteException {
        super();
        this.subjectState = subjectState;
    }
    public SubjectImpl() throws RemoteException {
        super();
        subjectState = null;
    }


    public void notifyAllObservers() throws RemoteException {
        for (ObserverRI observer : observers) {
            observer.update();
        }
    }


    @Override
    public void attach(ObserverRI obsRI) throws RemoteException {
        observers.add(obsRI);
    }

    @Override
    public void detach(ObserverRI obsRI) throws RemoteException {
        observers.remove(obsRI);
    }

    @Override
    public State getState() throws RemoteException {
        return this.subjectState;
    }

    @Override
    public void setState(State state) throws RemoteException {
        this.subjectState = state;
        notifyAllObservers();
    }
}
