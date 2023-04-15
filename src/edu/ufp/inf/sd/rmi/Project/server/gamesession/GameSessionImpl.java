package edu.ufp.inf.sd.rmi.Project.server.gamesession;

import edu.ufp.inf.sd.rmi.Project.database.DB;
import edu.ufp.inf.sd.rmi.Project.variables.User;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    private DB db;
    private User user;


    public GameSessionImpl(DB db, User user) throws RemoteException {
        super();
        this.db = db;
        this.user = user;
        this.db.addSession(user.getUsername(),this);
    }

    @Override
    public synchronized void logout() throws RemoteException {
        this.db.removeSession(this.user.getUsername());
    }
}
