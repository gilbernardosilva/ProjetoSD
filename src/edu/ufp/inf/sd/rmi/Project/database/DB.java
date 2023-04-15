package edu.ufp.inf.sd.rmi.Project.database;


import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.variables.User;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

public class DB {

    private final ArrayList<User> users = new ArrayList<>();;
    private final Map<String, GameSessionRI> sessions = new HashMap<>();

    public DB() {
        System.out.println(System.getProperty("java.class.path"));
        users.add(new User("guest", "ufp","arroz"));
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
}


