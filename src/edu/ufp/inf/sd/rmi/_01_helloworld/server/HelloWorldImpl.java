package edu.ufp.inf.sd.rmi._01_helloworld.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HelloWorldImpl extends UnicastRemoteObject implements HelloWorldRI {


    public HelloWorldImpl() throws RemoteException {
        super();
    }

    @Override
    public void print(String msg) throws RemoteException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "someone called me with msg = {0}", new Object[]{msg});
    }
}
