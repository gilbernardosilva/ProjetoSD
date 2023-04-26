package edu.ufp.inf.sd.rmi.project.server.lobby;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class State implements Serializable {
    ArrayList<Integer> id = new ArrayList<>(4);
    ArrayList<String> character = new ArrayList<>(4);
    ArrayList<String> username = new ArrayList<>(4);


    public State(ArrayList<Integer> id, ArrayList<String> character, ArrayList<String> username) {
        this.id = id;
        this.character = character;
        this.username = username;
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public ArrayList<String> getCharacter() {
        return character;
    }

    public void setCharacter(ArrayList<String> character) {
        this.character = character;
    }

    public ArrayList<String> getUsername() {
        return username;
    }

    public void setUsername(ArrayList<String> username) {
        this.username = username;
    }
}
