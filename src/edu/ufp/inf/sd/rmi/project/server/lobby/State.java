package edu.ufp.inf.sd.rmi.project.server.lobby;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class State implements Serializable {
    ArrayList<Integer> id = new ArrayList<>(4);
    int[] character = {0,0,0,0};
    ArrayList<String> username = new ArrayList<>(4);

    LobbyStatusEnum status;

    public State(ArrayList<Integer> id, int[] character, ArrayList<String> username) {
        this.id = id;
        this.character = character;
        this.username = username;
    }

    public State(LobbyStatusEnum status){
        this.status = status;
    }
    public LobbyStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LobbyStatusEnum status) {
        this.status = status;
    }

    public ArrayList<Integer> getId() {
        return id;
    }

    public void setId(ArrayList<Integer> id) {
        this.id = id;
    }

    public int[] getCharacter() {
        return character;
    }

    public void setCharacter(int[] character) {
        this.character = character;
    }

    public ArrayList<String> getUsername() {
        return username;
    }

    public void setUsername(ArrayList<String> username) {
        this.username = username;
    }
}
