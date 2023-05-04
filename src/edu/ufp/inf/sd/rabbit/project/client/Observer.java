package edu.ufp.inf.sd.rabbit.project.client;

import com.rabbitmq.client.*;
import edu.ufp.inf.sd.rabbit.project.server.gamefactory.GameFactoryImpl;
import edu.ufp.inf.sd.rabbit.project.server.gamefactory.GameFactoryRI;
import edu.ufp.inf.sd.rabbit.util.rabbitUtil.RabbitUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import edu.ufp.inf.sd.rmi.project.database.DB;
import engine.Game;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rui
 */
public class Observer {
    private String receivedMessage;

    private final ClientImpl clientImpl;

    private final Channel channel;

    private final String exchangeName;

    private final BuiltinExchangeType exchangeType;

    private final String messageFormat;

    private GameFactoryImpl stub;

    /**
     *
     * @param
     */
    public Observer(ClientImpl clientImpl, String host, int port, String user, String pass, String exchangeName, BuiltinExchangeType exchangeType, String messageFormat) throws IOException, TimeoutException {
        this.clientImpl = clientImpl;
        this.stub = new GameFactoryImpl(new DB());
        new Game(stub, clientImpl);

        Connection connection = RabbitUtils.newConnection2Server(host, port, user, pass);
        this.channel = RabbitUtils.createChannel2Server(connection);

        this.exchangeName = exchangeName;
        this.exchangeType=exchangeType;
        this.messageFormat = messageFormat;

        System.out.println(this);

        bindExchangeToChannelRabbitMQ();
        attachConsumerToChannelExchangeWithKey();
    }

    /**
     * Binds the channel to given exchange name and type.
     */
    private void bindExchangeToChannelRabbitMQ() throws IOException {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Declaring Exchange '" + this.exchangeName);

        //channel.queueDeclare(this.exchangeName, false, false, false, null);
        channel.exchangeDeclare(this.exchangeName, this.exchangeType);
    }

    /**
     * Creates a Consumer associated with an unnamed queue.
     */
    public void attachConsumerToChannelExchangeWithKey() {
        try {
            String queueName = channel.queueDeclare().getQueue();

            String routingKey="";
            channel.queueBind(queueName, this.exchangeName, routingKey);

            Logger.getLogger(this.getClass().getName()).log(Level.INFO, " Created consumerChannel bound to Exchange " + this.exchangeName + "...");

            /* Use a DeliverCallback lambda function instead of DefaultConsumer to receive messages from queue;
               DeliverCallback is an interface which provides a single method:
                void handle(String tag, Delivery delivery) throws IOException; */
            DeliverCallback deliverCallback=(consumerTag, delivery) -> {
                String message=new String(delivery.getBody(), messageFormat);

                //Store the received message
                setReceivedMessage(message);
                System.out.println(" [x] Consumer Tag [" + consumerTag + "] - Received '" + message + "'");

                //  Notify the game about the new message arrive
                try {
                    updateGame(message);
                } catch (InterruptedException | TimeoutException e) {
                    throw new RuntimeException(e);
                }
            };
            CancelCallback cancelCallback=consumerTag -> {
                System.out.println(" [x] Consumer Tag [" + consumerTag + "] - Cancel Callback invoked!");
            };

            channel.basicConsume(queueName, true, deliverCallback, cancelCallback);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, e.toString());
        }
    }

    /**
     * updates the game
     *
     * @param message
     * @throws IOException
     * @throws InterruptedException
     */
    private void updateGame(String message) throws IOException, InterruptedException, TimeoutException {
        //chamar funçao de update game no observerClient
        //this.clientImpl.updateGame(message);
        System.out.println(message);
    }

    /**
     * @return the most recent message received from the broker
     */
    public String getReceivedMessage() {
        return receivedMessage;
    }

    /**
     * @param receivedMessage the received message to set
     */
    public void setReceivedMessage(String receivedMessage) {
        this.receivedMessage=receivedMessage;
    }

    /**
     * Publish messages to existing exchange instead of the nameless one.
     * - The routingKey is empty ("") since the fanout exchange ignores it.
     * - Messages will be lost if no queue is bound to the exchange yet.
     * - Basic properties can be: etc...
     */
    public void sendMessage(String message) throws IOException {
        System.out.println(message);
        final String correlationID = UUID.randomUUID().toString();
        String routingKey="";

        System.out.println(message);

        String replyQueueName = channel.queueDeclare().getQueue();

        System.out.println(message);

        //Build properties with correlationID and replyQueueName
        AMQP.BasicProperties prop = new AMQP.BasicProperties.Builder().correlationId(correlationID).replyTo(replyQueueName).build();

        // Publish message
        System.out.println("ENVIEI A MENSAGEM " + message + " POR UMA QUEUE");
        channel.basicPublish(exchangeName, routingKey, prop, message.getBytes());
    }


}
