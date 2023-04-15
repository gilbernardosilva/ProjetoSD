package edu.ufp.inf.sd.rmi._03_pingpong.server;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;


public class PingImpl extends UnicastRemoteObject implements PingRI {


    public PingImpl() throws RemoteException {
        super();
    }

    @Override
    public void pong(Ball ball) {

    }
}
