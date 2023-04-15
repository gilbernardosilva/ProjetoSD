package edu.ufp.inf.sd.rmi.Project.server.gamesession;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSessionRI extends Remote {
    void logout() throws RemoteException;
}
