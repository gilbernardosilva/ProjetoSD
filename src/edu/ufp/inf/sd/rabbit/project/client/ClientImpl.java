package edu.ufp.inf.sd.rabbit.project.client;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import engine.Game;
import menus.MenuHandler;
import menus.PlayerSelectionMP;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.TimeoutException;

public class ClientImpl extends UnicastRemoteObject implements ClientRI, AutoCloseable {

    //queue name
    private Game game;
    private Integer id;

    private String character;
    private PlayerSelectionMP playerSelectionMP;

    private String username;

    public ClientImpl(String[] args) throws IOException, TimeoutException {
        new ProjectClient(args);
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new ClientImpl(args);
    }

    @Override
    public void close() throws Exception {
    }
}
