package edu.ufp.inf.sd.rmi.project.database;


import edu.ufp.inf.sd.rmi.project.server.Lobby.Lobby;
import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.project.variables.User;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class DB {

    private final ArrayList<User> users = new ArrayList<>();;
    private final Map<String, GameSessionRI> sessions = new HashMap<>();
    private final ArrayList<Lobby> gameLobbies = new ArrayList<>();

    public DB() {
        System.out.println(System.getProperty("java.class.path"));
        users.add(new User("guest", "ufp"));
    }

    public void register(User user) {
        if (!userExists(user)) {
            users.add(user);
        }
    }

    public boolean userExists(User user) {
        for (User users : this.users) {
            if (user.getUsername().equals(users.getUsername()) && user.getPassword().equals(users.getPassword())) {
                return true;
            }
        }
        return false;
    }

    public User getUser(User user){
        for (User users : this.users) {
            if (users.getUsername().compareTo(user.getUsername()) == 0 && users.getPassword().compareTo(user.getPassword()) == 0) {
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

    public ArrayList<User> getUsers(){
        return this.users;
    }
    public Map<String, GameSessionRI> getSessions() {
        return sessions;
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