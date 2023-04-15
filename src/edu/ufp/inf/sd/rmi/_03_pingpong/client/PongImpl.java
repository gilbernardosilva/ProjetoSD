package edu.ufp.inf.sd.rmi._03_pingpong.client;

import edu.ufp.inf.sd.rmi._01_helloworld.server.HelloWorldRI;
import edu.ufp.inf.sd.rmi._03_pingpong.server.Ball;
import edu.ufp.inf.sd.rmi._03_pingpong.server.PingRI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


    public class PongImpl extends UnicastRemoteObject implements PongRI {


    public PongImpl() throws RemoteException {
        super();
    }


    @Override
    public void ping(Ball ball, PongRI pongRI) throws RemoteException {}

}
