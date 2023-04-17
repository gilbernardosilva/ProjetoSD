package edu.ufp.inf.sd.rmi.project.server.gamesession;

import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.variables.User;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI {

    private final DB db;
    private final User user;


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
