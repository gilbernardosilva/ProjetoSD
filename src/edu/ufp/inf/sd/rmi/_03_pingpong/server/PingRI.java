package edu.ufp.inf.sd.rmi._03_pingpong.server;

import edu.ufp.inf.sd.rmi._03_pingpong.client.PongRI;

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface PingRI extends Remote {

    public void pong(Ball ball);
}
