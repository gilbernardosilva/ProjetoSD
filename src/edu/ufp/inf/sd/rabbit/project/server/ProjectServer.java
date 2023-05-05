package edu.ufp.inf.sd.rabbit.project.server;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbit.project.database.DB;
import edu.ufp.inf.sd.rabbit.util.rabbitUtil.RabbitUtils;
import edu.ufp.inf.sd.rmi.project.client.ObserverRI;
import edu.ufp.inf.sd.rmi.project.server.lobby.Lobby;

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
        this.loginExchange();
        this.lobbyListExchange();
        //this.gameExchange();
    }

    public void loginExchange() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
        this.channel.exchangeDeclare("loginExchange", "fanout");
        String queueName = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(queueName, "loginExchange", "");

        DeliverCallback deliverCallbackFanout = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "MESSAGE RECEIVED SUCESSFULLY WE DID IT DANIEL:" + message);
            this.auth(message);
        };
        this.channel.basicConsume(queueName, true, deliverCallbackFanout, consumerTag -> {
        });
    }

    public void auth(String message) throws IOException {
        String sucess = "success";
        String wrong = "wrong";

        String[] parsedMessage = message.split(",");
        if(Objects.equals(parsedMessage[2], "register")){
            if(this.db.userExists(parsedMessage[0], parsedMessage[1])){
                this.channel.basicPublish("",parsedMessage[0],null, wrong.getBytes(StandardCharsets.UTF_8));
            }else{
                this.db.register(parsedMessage[0], parsedMessage[1]);
                this.channel.basicPublish("",parsedMessage[0],null, sucess.getBytes(StandardCharsets.UTF_8));
            }
        }else if(Objects.equals(parsedMessage[2], "login")){
            if(this.db.userExists(parsedMessage[0], parsedMessage[1])){
                this.channel.basicPublish("",parsedMessage[0],null, sucess.getBytes(StandardCharsets.UTF_8));
            }else{
                this.channel.basicPublish("",parsedMessage[0],null, wrong.getBytes(StandardCharsets.UTF_8));
            }
        }
    }

    public void lobbyListExchange() throws IOException{
        this.channel.exchangeDeclare("lobbyListExchange", "fanout");
        String queueName = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(queueName, "lobbyListExchange", "");

        DeliverCallback deliverCallbackFanout = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "MESSAGE RECEIVED SUCESSFULLY WE DID IT DANIEL:" + message);
            this.getLobbies(message);
        };
        this.channel.basicConsume(queueName, true, deliverCallbackFanout, consumerTag -> {
        });

    }

    private void getLobbies(String message) throws IOException {
       String lobbyList = this.db.getGameLobbies().toString();
        this.channel.basicPublish("",message,null, lobbyList.getBytes(StandardCharsets.UTF_8));
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new ProjectServer();
    }
}