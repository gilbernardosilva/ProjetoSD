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
        this.gameExchange();

    }

    public void gameExchange() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        this.connection = factory.newConnection();
        this.channel = this.connection.createChannel();
        this.channel.exchangeDeclare("gameExchange", "fanout");
        String queueName = this.channel.queueDeclare().getQueue();
        this.channel.queueBind(queueName, "gameExchange", "");

        DeliverCallback deliverCallbackFanout = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "MESSAGE RECEIVED SUCESSFULLY WE DID IT DANIEL:" + message);
            this.gameHandler(message);
        };
        this.channel.basicConsume(queueName, true, deliverCallbackFanout, consumerTag -> {
        });
    }


    // TODO: DANIEL FAZER GAMEHANDLER
    // TODO: Message vai receber operation;playerX;playerY
    // TODO: Exemplo: select;X;Y
    // TODO: vai ser necessário enviar a todos os jogadores o X e o Y, e a caso seja select, BattleAction()
    // TODO: caso seja cancel, irão cancelar a jogada do outro jogador
    // TODO: mesma logica quando passarmos a operation = endturn, vai avisar todos os jogadores para dar endTurn()
    public void gameHandler(String message) throws IOException {

    }

    public static void main(String[] args) throws IOException, TimeoutException {
        new ProjectServer();
    }
}