package edu.ufp.inf.sd.rmi.project.server.Lobby;

import edu.ufp.inf.sd.rmi.project.server.gamesession.GameSessionRI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Lobby implements Serializable {
    private final UUID id;
    private ArrayList<GameSessionRI> playerlist;
    private int players;
    private int currentPlayers;
    private String map;
    private LobbyStateEnum lobbyState;

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public Lobby(UUID id, ArrayList<GameSessionRI> playerlist, int players, String map) {
        this.id = id;
        this.playerlist = playerlist;
        this.players = players;
        this.currentPlayers = 1;
        this.map = map;
    }

    public LobbyStateEnum getLobbyState() {
        return lobbyState;
    }

    public void setLobbyState(LobbyStateEnum lobbyState) {
        this.lobbyState = lobbyState;
    }

    public ArrayList<GameSessionRI> getPlayerlist() {
        return playerlist;
    }

    public void setPlayerlist(ArrayList<GameSessionRI> playerlist) {
        this.playerlist = playerlist;

    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public UUID getId() {
        return id;
    }

}
