package edu.ufp.inf.sd.rmi.project.server.gamesession;

import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rmi.project.variables.User;
import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyStatusEnum;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

public class GameSessionImpl extends UnicastRemoteObject implements GameSessionRI, Serializable {

    private final DB db;
    private final User user;


    public GameSessionImpl(DB db, User user) throws RemoteException {
        super();
        this.db = db;
        this.user = user;
        this.db.addSession(user, this);
    }

    public LobbyRI createLobby(LobbyMapEnum map, GameSessionRI session) throws RemoteException {
        Lobby lobby = new Lobby(map,user.getUsername());
        lobby.setLobbyState(LobbyStatusEnum.PAUSED);
        this.db.addLobby(lobby);
        return lobby;
    }


    public List<LobbyRI> getLobbies() throws RemoteException {
        return this.db.getGameLobbies();
    }



    public LobbyRI getLobby(int index) throws RemoteException{
        return db.getLobby(index);
    }

    public void deleteLobby(UUID id) throws RemoteException{
        this.db.deleteLobby(id);
    }

    @Override
    public synchronized void logout() throws RemoteException {
        this.db.removeSession(user);
    }

    public User getUser() throws RemoteException {
        return user;
    }
    public void updateToken() throws RemoteException {
        if(!this.user.getToken().verify()) {
            this.user.getToken().updateToken(user.getUsername());
        }
    }
}
