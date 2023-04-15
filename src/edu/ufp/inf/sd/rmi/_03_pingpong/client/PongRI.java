package edu.ufp.inf.sd.rmi._03_pingpong.client;

import edu.ufp.inf.sd.rmi._03_pingpong.server.Ball;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface PongRI extends Remote {

    public void ping(Ball ball, PongRI pongRI) throws RemoteException;


}
