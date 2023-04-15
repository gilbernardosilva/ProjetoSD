package edu.ufp.inf.sd.rmi._04_digLib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DigLibFactoryImpl extends UnicastRemoteObject implements DigLibFactoryRI {


    public DigLibFactoryImpl() throws RemoteException {
        super();
    }

    DBMockup dbMockup = new DBMockup();

    @Override
    public boolean register(String username, String password) throws RemoteException {
        if (dbMockup.exists(username, password)) {
            return false;
        }
        dbMockup.register(username, password);
        return true;
    }

    @Override
    public DigLibSessionRI login(String username, String password) throws RemoteException {
        if (!dbMockup.exists(username, password)) {
            dbMockup.register(username, password);
        } else {
            DigLibSessionImpl digLibSessionRI = null;
            try {
                digLibSessionRI = new DigLibSessionImpl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return digLibSessionRI;
        }
        return null;
    }
}
