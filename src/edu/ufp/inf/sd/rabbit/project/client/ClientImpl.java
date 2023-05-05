package edu.ufp.inf.sd.rabbit.project.client;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import edu.ufp.inf.sd.rabbit.project.server.gamefactory.GameFactoryImpl;
import edu.ufp.inf.sd.rabbit.project.server.lobby.LobbyRI;
import edu.ufp.inf.sd.rabbit.project.server.lobby.LobbyStatusEnum;
import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.server.lobby.State;
import engine.Game;
import menus.MenuHandler;
import menus.PlayerSelectionMP;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeoutException;

public class ClientImpl extends UnicastRemoteObject implements ClientRI, AutoCloseable {

    //queue name
    private Observer observer;
    private LobbyRI lobby;
    private Game game;
    private Integer id;
    private transient Connection connection;
    private transient Channel channel;
    private String character;
    private PlayerSelectionMP playerSelectionMP;
    private State lastObserverState;
    private State lastObserverGameState;
    private String username;

    public ClientImpl() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        //int port = 5672;
        String exchangeName = "AdvancedWars_exchange";
        channel.exchangeDeclare(exchangeName, "fanout");
        new Game(this.channel);
    }


    /**
     * sends state to update
     *
     * @param message new subject state
     */
    public void sendToServer(String message) throws IOException {
        System.out.println(this.observer);
        this.observer.sendMessage(message);
    }

    public Observer getObserver() throws RemoteException {
        return observer;
    }

    public void setObserver(Observer observer) throws RemoteException {
        this.observer = observer;
    }

    public State getLastObserverState() throws RemoteException {
        return lastObserverState;
    }

    public void setLastObserverState(State lastObserverState) throws RemoteException {
        this.lastObserverState = lastObserverState;
    }

    @Override
    public void update() throws RemoteException {
        this.lastObserverGameState = lobby.getState();
        if (this.lobby.getLobbyStatus() == LobbyStatusEnum.ONGOING) {
            playerSelectionMP.startGame(this.lastObserverState);
        } else {
            playerSelectionMP.updatePlayerSelection(this.lastObserverState);
        }
    }

    public void updateGame(String message) throws RemoteException {
        this.lastObserverGameState = lobby.getGameState();

        switch (message) {
            case "select":
                Game.btl.Action();
                break;
            case "cancel":
                Game.player.get(this.lastObserverGameState.getCurrentPlayerId()).Cancle();
                break;
            case "EndTurn":
                Game.btl.EndTurn();
                break;
            case "BuyUnit":
                Game.btl.Buyunit(this.lastObserverGameState.getCurrentPlayerId(), this.lastObserverGameState.getX(), this.lastObserverGameState.getY());
                MenuHandler.CloseMenu();
                break;
        }
    }

    public void sendGameState(String line, String entity) throws IOException {
        /*try (ClientImpl client = new ClientImpl()) {

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        sendToServer(line);
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

    public static void main(String[] args) throws IOException, TimeoutException {
        new ClientImpl();
    }

    @Override
    public void close() throws Exception {
    }
}
