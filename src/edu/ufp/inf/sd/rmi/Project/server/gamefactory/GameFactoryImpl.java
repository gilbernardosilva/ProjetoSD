package edu.ufp.inf.sd.rmi.project.server.gamefactory;

import edu.ufp.inf.sd.rmi.Project.server.gamefactory.GameFactoryRI;
import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionImpl;
import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.project.variables.User;
import edu.ufp.inf.sd.rmi.Project.database.DB;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.Lobby;
import edu.ufp.inf.sd.rmi.Project.server.Lobby.LobbyStateEnum;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionImpl;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.variables.User;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

public class GameFactoryImpl extends UnicastRemoteObject implements GameFactoryRI {

    private final DB db;

    public GameFactoryImpl(DB db) throws RemoteException {
        super();
        this.db = db;
    }

    @Override
    public GameSessionRI login(User user) throws RemoteException {
        if (this.db.hasSession(user.getUsername())) {
            return null;
        }
        if (this.db.userExists(user)) {
            if (!user.getToken().verify()) {
                user.getToken().updateToken(user.getUsername());
            }
            return createGameSession(user);
        } else {
            throw new RemoteException("User Verification failed");
        }


        @Override
        public GameSessionRI register (User user) throws RemoteException {
            this.db.register(user);
            return login(user);
        }

        @Override
        public User getUser (String username, String password){
            for (User user : this.db.getUsers()) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    return user;
                }
            }
            return null;
        }

        private GameSessionRI createGameSession (User user) throws RemoteException {
            return new GameSessionImpl(db, user);
        }


        private GameSessionRI getSession (User user) throws RemoteException {

            return this.db.getSession(user.getUsername());
        }

    }

