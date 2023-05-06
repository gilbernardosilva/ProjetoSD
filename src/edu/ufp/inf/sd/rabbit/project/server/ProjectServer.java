package edu.ufp.inf.sd.rabbit.project.server;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbit.project.database.DB;
import edu.ufp.inf.sd.rabbit.util.rabbitUtil.RabbitUtils;
import edu.ufp.inf.sd.rmi.project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;
import engine.Game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProjectServer {
    private transient Connection connection;
    private transient Channel channel;
    private final DB db;
    private List<Lobby> lobbyList = Collections.synchronizedList(new ArrayList<>());

    /*+ name of the queue */
    //public final static String QUEUE_NAME="hello_queue";
    public ProjectServer() throws IOException, TimeoutException {
        this.db = new DB();
        this.gameExchange();

    }

    public void gameExchange() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
        this.channel.exchangeDeclare("gameExchanger", "topic");
        String queueName = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(queueName, "gameExchanger", "lobby.server");

        DeliverCallback deliverCallbackTopic = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "MESSAGE RECEIVED SUCESSFULLY WE DID IT DANIEL:" + message);
            this.gameHandler(message);
        };
        this.channel.basicConsume(queueName, true, deliverCallbackTopic, consumerTag -> {
        });
    }



    public void gameHandler(String message) throws IOException {
        String[] Content = message.split(";");
        String routeKey = "lobby." + Content[0];

        this.channel.basicPublish("gameExchanger", routeKey, null, message.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new ProjectServer();
    }
}