package edu.ufp.inf.sd.rabbit.project.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import edu.ufp.inf.sd.rmi.project.server.gamefactory.GameFactoryRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;
import engine.Game;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClient {

    private SetupContextRMI contextRMI;
    private GameFactoryRI stub;
    private transient Connection connection;
    private transient Channel channel;

    private void lookupService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl = contextRMI.getServicesUrl(0);
                this.stub = (GameFactoryRI) registry.lookup(serviceUrl);

            }
        } catch (RemoteException | NotBoundException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void initContext(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
            String exchangeName = "AdvancedWars_exchange";
            channel.exchangeDeclare(exchangeName, "fanout");
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        //int port = 5672;
        System.out.println(args);

        System.out.println(Arrays.toString(args));
        try {
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP = args[0];
            String registryPort = args[1];
            String serviceName = args[2];
            this.contextRMI = new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void playService() {
      new Game(this.stub, channel);
    }

    public ProjectClient(String[] args) {

        this.initContext(args);
        this.lookupService();
        this.playService();
    }

    public static void main(String[] args, Channel channel) {
        new ProjectClient(args);
    }

}
