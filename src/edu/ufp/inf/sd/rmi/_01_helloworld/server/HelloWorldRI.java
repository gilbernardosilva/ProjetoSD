package edu.ufp.inf.sd.rmi._01_helloworld.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface HelloWorldRI extends Remote {
    public void print(String msg) throws RemoteException;
}
