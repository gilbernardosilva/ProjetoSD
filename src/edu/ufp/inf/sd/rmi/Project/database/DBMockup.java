package edu.ufp.inf.sd.rmi.Project.database;


import edu.ufp.inf.sd.rmi.Project.variables.User;

import java.util.ArrayList;

public class DBMockup {

    private final ArrayList<User> users;// = new ArrayList();

    public DBMockup() {
        users = new ArrayList();
        users.add(new User("guest", "ufp"));
    }

    public void register(String u, String p) {
        if (!exists(u, p)) {
            users.add(new User(u, p));
        }
    }

    public boolean exists(String u, String p) {
        for (User usr : this.users) {
            if (usr.getUname().compareTo(u) == 0 && usr.getPword().compareTo(p) == 0) {
                return true;
            }
        }
        return false;
        //return ((u.equalsIgnoreCase("guest") && p.equalsIgnoreCase("ufp")) ? true : false);
    }
}


