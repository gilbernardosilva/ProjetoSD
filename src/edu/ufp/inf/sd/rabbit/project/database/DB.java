package edu.ufp.inf.sd.rabbit.project.database;

import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyMapEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rmi.project.variables.User;

import java.lang.reflect.Array;
import java.rmi.RemoteException;
import java.util.*;


public class DB {

    private final ArrayList<User> users = new ArrayList<>();
    private final Map<User, GameSessionRI> sessions = new HashMap<>();
    private final Map<UUID,Lobby> gameLobbies = new HashMap<>();
    
    public DB() {
        users.add(new User("guest", "ufp"));
        users.add(new User("guest1", "ufp"));
        users.add(new User("guest2", "ufp"));
        try {
            Lobby um = new Lobby(LobbyMapEnum.FourCorners,"guest");
            Lobby dois = new Lobby(LobbyMapEnum.SmallVs,"guest1");
            Lobby tres  = new Lobby(LobbyMapEnum.SmallVs,"guest2");
            gameLobbies.put(um.getID(),um);
            gameLobbies.put(dois.getID(),dois);
            gameLobbies.put(tres.getID(),tres);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public List<LobbyRI> getGameLobbies() {
        return new ArrayList<>(this.gameLobbies.values());
    }

    public LobbyRI getLobby(int index){
        return getGameLobbies().get(index);
    }


    public void register(String username, String password) {
        if (!userExists(username, password)) {
            User user = new User(username, password);
            users.add(user);
        } else {
            System.out.println("error");
        }
    }

    public boolean userExists(String username, String password) {
        for (User users : this.users) {
            if (username.equals(users.getUsername()) && password.equals(users.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username,String password){
        for (User users : this.users) {
            if (users.getUsername().equals(username) && users.getPassword().equals(password)) {
                return users;
            }
        }
        return null;
    }



    public void addLobby(Lobby lobby) throws RemoteException {
        gameLobbies.put(lobby.getID(),lobby);
    }

    public void deleteLobby(UUID id){
        if(this.gameLobbies.containsKey(id)) {
            this.gameLobbies.remove(id);
        }else{
            System.out.println("Lobby does not exist");
        }
    }

    public void addSession(User user, GameSessionRI session) {
        this.sessions.put(user, session);
    }

    public void removeSession(User user) {
        this.sessions.remove(user);
    }

    public GameSessionRI getSession(User user) {
        return this.sessions.get(user);
    }

    public boolean hasSession(User user) {
        return this.sessions.containsKey(user);
    }

    public ArrayList<User> getUsers(){
        return this.users;
    }

    public Map<User, GameSessionRI> getSessions() {
        return sessions;
    }
}