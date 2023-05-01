package edu.ufp.inf.sd.rmi.project.server.lobby;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class State implements Serializable {
    ArrayList<Integer> id = new ArrayList<>(4);
    int[] character = {0, 0, 0, 0};
    ArrayList<String> username = new ArrayList<>(4);

    int currentPlayerId;
    int x = 0;
    int y = 0;
    int unit;

    boolean endTurn = false;

    LobbyStatusEnum status;

    public State(ArrayList<Integer> id, int[] character, ArrayList<String> username) {
        this.id = id;
        this.character = character;
        this.username = username;
    }

    public State(int currentPlayerId, int x, int y) {
        this.currentPlayerId = currentPlayerId;
        this.x = x;
        this.y = y;

    }

    public State(boolean endTurn) {
        this.endTurn = endTurn;
    }

    public State(LobbyStatusEnum status) {
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

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }

    public void setCurrentPlayerId(int currentPlayerId) {
        this.currentPlayerId = currentPlayerId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public boolean isEndTurn() {
        return endTurn;
    }

    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }
}
