package edu.ufp.inf.sd.rmi.Project.client;

import edu.ufp.inf.sd.rmi.Project.server.gamefactory.GameFactoryRI;
import edu.ufp.inf.sd.rmi.Project.server.gamesession.GameSessionRI;
import edu.ufp.inf.sd.rmi.Project.variables.User;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;
import engine.Game;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectClient{

    private SetupContextRMI contextRMI;
    private GameFactoryRI stub;
    private GameSessionRI session;

    public static void main(String args[]) {
        ProjectClient client = new ProjectClient(args);
    }

    public ProjectClient(String args[] ){
       this.initContext(args);
       this.lookupService();
       this.login();
       //this.startGame();
    }

    private void startGame() {
        new Game(this.stub);
    }

    private void lookupService() {
        try {
            Registry registry=contextRMI.getRegistry();
            if (registry != null) {
                String serviceUrl=contextRMI.getServicesUrl(0);
                this.stub=(GameFactoryRI) registry.lookup(serviceUrl);
            }
        } catch (RemoteException | NotBoundException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private void login() {


        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter 'login' or 'register': ");
        String action = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        switch (action) {
            case "login":
                try {
                    this.session = this.stub.login(username,password);
                    System.out.println("Login feito com sucesso!");
                    this.startGame();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case "register":
                try {
                    this.stub.register(username,password);
                    this.session = this.stub.login(username,password);
                    this.startGame();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            default:
                System.out.println("Invalid action entered.");
                break;
        }
    }

    private void initContext(String args[]) {
        try {
            SetupContextRMI.printArgs(this.getClass().getName(), args);
            String registryIP=args[0];
            String registryPort=args[1];
            String serviceName=args[2];
            this.contextRMI=new SetupContextRMI(this.getClass(), registryIP, registryPort, new String[]{serviceName});
        } catch (RemoteException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

}
