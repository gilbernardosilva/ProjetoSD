package edu.ufp.inf.sd.rmi.Project.server.gamefactory;

import edu.ufp.inf.sd.rmi.Project.database.DB;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionImpl;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.variables.User;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    private final DB db;

    public GameFactoryImpl(DB db) throws RemoteException {
        super();
        this.db = db;
    }

    @Override
    public GameSessionRI login(String username, String password) throws RemoteException {
        User user = this.db.getUser(username,password);
        GameSessionImpl gameSession = new GameSessionImpl(this.db, user);
        return gameSession;
    }

    @Override
    public GameSessionRI register(String username, String password) throws RemoteException {
        this.db.register(username, password);
        return this.login(username,password);
    }

    private GameSessionRI getSession(User user) throws RemoteException {

        return this.db.getSession(user.getUsername());
    }


}

