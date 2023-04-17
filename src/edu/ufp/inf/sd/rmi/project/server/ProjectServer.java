package edu.ufp.inf.sd.rmi.project.server;

import edu.ufp.inf.sd.rmi.project.database.DB;
import edu.ufp.inf.sd.rmi.project.server.gamefactory.GameFactoryImpl;
import edu.ufp.inf.sd.rmi.project.server.gamefactory.GameFactoryRI;
import edu.ufp.inf.sd.rmi.util.rmisetup.SetupContextRMI;


import java.rmi.RemoteException;
import java.rmi.registry.Registry;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ProjectServer {

    private SetupContextRMI contextRMI;
    private GameFactoryRI stub;

    public ProjectServer(String args[]) {
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

    private void rebindService() {
        try {
            Registry registry = contextRMI.getRegistry();
            if (registry != null) {
                this.stub = new GameFactoryImpl(new DB());
                String serviceUrl = contextRMI.getServicesUrl(0);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "going MAIL_TO_ADDR rebind service @ {0}", serviceUrl);
                registry.rebind(serviceUrl, this.stub);
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "service bound and running. :)");
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.INFO, "registry not bound (check IPs). :(");
            }
        } catch (RemoteException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        if (args != null && args.length < 3) {
            System.err.println("Error");
            System.exit(-1);
        } else {
            ProjectServer server = new ProjectServer(args);
            server.rebindService();
        }
    }

}
