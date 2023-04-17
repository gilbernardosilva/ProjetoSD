package edu.ufp.inf.sd.rmi.project.database;


import edu.ufp.inf.sd.rmi.project.server.Lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.project.variables.User;

import java.util.*;

public class DB {

    private final ArrayList<User> users = new ArrayList<>();;
    private final Map<String, GameSessionRI> sessions = new HashMap<>();
    private final ArrayList<Lobby> gameLobbies = new ArrayList<>();



    public DB() {
        users.add(new User("guest", "ufp","arroz"));
        ArrayList<GameSessionRI> lobby = new ArrayList<>();
        lobby.add(this.getSession(getUser("guest","ufp").getUsername()));
        Lobby lobby1 =new Lobby(UUID.randomUUID(), lobby,2, "SmallVs" );
        gameLobbies.add(lobby1);
    }

    public void register(String username, String password) {
        if (!exists(username, password)) {
            users.add(new User(username, password,"register"));
        }
    }

    public boolean exists(String username, String password) {
        for (User users : this.users) {
            if (users.getUsername().compareTo(username) == 0 && users.getPassword().compareTo(password) == 0) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username,String password){
        for (User users : this.users) {
            if (users.getUsername().compareTo(username) == 0 && users.getPassword().compareTo(password) == 0) {
                return users;
            }
        }
        return null;
    }


    public void addSession(String username, GameSessionRI session) {
        this.sessions.put(username, session);
    }

    public void removeSession(String username) {
        this.sessions.remove(username);
    }

    public GameSessionRI getSession(String username) {

        return this.sessions.get(username);
    }

    public boolean hasSession(String username) {
        return this.sessions.containsKey(username);
    }


    public void addLobby(Lobby lobby){
        gameLobbies.add(lobby);
    }

    public void updateLobby(int index, Lobby lobby){
        gameLobbies.set(index,lobby);
    }
    public ArrayList<Lobby> getGameLobbies() {
        return gameLobbies;
    }


}


