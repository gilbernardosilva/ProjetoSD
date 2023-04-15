package edu.ufp.inf.sd.rmi._05_observer.client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observable;
import java.util.Observer;


public interface ObserverRI extends Remote {
    public void update() throws RemoteException;

}
