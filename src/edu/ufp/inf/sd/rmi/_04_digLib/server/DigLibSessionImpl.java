package edu.ufp.inf.sd.rmi._04_digLib.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DigLibSessionImpl extends UnicastRemoteObject implements DigLibSessionRI {


    public DigLibSessionImpl() throws RemoteException {
        super();
    }
    DBMockup dbMockup = new DBMockup();


    @Override
    public Book[] search(String title, String author) {
        return dbMockup.select(title,author);
    }

    @Override
    public void logout() {

    }
}
