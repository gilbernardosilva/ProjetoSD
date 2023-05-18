package edu.ufp.inf.sd.rabbit.project.server;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import edu.ufp.inf.sd.rabbit.project.database.DB;
import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FrontServer {
    private transient Connection connection;
    private transient Channel channel;
    private List<Lobby> lobbyList = Collections.synchronizedList(new ArrayList<>());

    /*+ name of the queue */
    //public final static String QUEUE_NAME="hello_queue";
    public FrontServer() throws IOException, TimeoutException {
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
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "MESSAGE RECEIVED:" + message);
            this.channel.basicPublish("", "serverQueues", null, message.getBytes(StandardCharsets.UTF_8));
        };
        this.channel.basicConsume(queueName, true, deliverCallbackTopic, consumerTag -> {
        });
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new FrontServer();
    }
}
