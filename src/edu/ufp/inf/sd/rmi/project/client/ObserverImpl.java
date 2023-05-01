package edu.ufp.inf.sd.rmi.project.client;

import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rmi.project.server.lobby.LobbyStatusEnum;
import edu.ufp.inf.sd.rmi.project.server.lobby.State;
import engine.Game;
import menus.PlayerSelectionMP;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ObserverImpl extends UnicastRemoteObject implements ObserverRI {

    private LobbyRI lobby;
    private Game game;
    private Integer id;
    private String character;

    private PlayerSelectionMP playerSelectionMP;
    private State lastObserverState;
    private State lastObserverGameState;
    private String username;

    public ObserverImpl(LobbyRI lobby, String username, Game game) throws RemoteException {
        super();
        this.username = username;
        this.lobby = lobby;
        this.game = game;
    }

    public State getLastObserverState() throws RemoteException {
        return lastObserverState;
    }

    public void setLastObserverState(State lastObserverState) throws RemoteException {
        this.lastObserverState = lastObserverState;
    }

    @Override
    public void update() throws RemoteException {
        this.lastObserverState = lobby.getState();
        if (this.lobby.getLobbyStatus() == LobbyStatusEnum.ONGOING) {
            playerSelectionMP.startGame(this.lastObserverState);
        } else {
            playerSelectionMP.updatePlayerSelection(this.lastObserverState);
        }
    }

    public void updateGame() throws RemoteException {
        this.lastObserverGameState = lobby.getGameState();
        players.Base ply = Game.player.get(Game.btl.currentplayer);
        if (!this.lastObserverGameState.isEndTurn()) {
            ply.selectx = this.lastObserverGameState.getX();
            ply.selecty = this.lastObserverGameState.getY();
            Game.btl.Action();
        } else if (this.lastObserverState.getCurrentPlayerId() == 0) {
            Game.btl.EndTurn();
            System.out.println("AJUDA CRL " + Game.btl.currentplayer);
        }
    }


    public Integer getId() throws RemoteException {
        return id;
    }

    public void setId(Integer id) throws RemoteException {
        this.id = id;
    }

    @Override
    public LobbyRI getLobby() throws RemoteException {
        return lobby;
    }

    public PlayerSelectionMP getPlayerSelectionMP() throws RemoteException {
        return playerSelectionMP;
    }

    public void setPlayerSelectionMP(PlayerSelectionMP playerSelectionMP) throws RemoteException {
        this.playerSelectionMP = playerSelectionMP;
    }

    @Override
    public String getUsername() throws RemoteException {
        return username;
    }

    public State getLastObserverGameState() throws RemoteException {
        return lastObserverGameState;
    }

    public void setLastObserverGameState(State lastObserverGameState) throws RemoteException {
        this.lastObserverGameState = lastObserverGameState;
    }
}
